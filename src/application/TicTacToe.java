/**
 * Names:  Andrew Hoffman, Chase Revia, Robert Elmore, Ralph E. Beard IV
 * Course #:  1174
 * Date:  
 * Assignment Name: Group Project Tic Tac Toe
 */

package application;


import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TicTacToe extends Application {
	
	private PlayerPane player1, player2; // sets up the two player objects
	private Cell[][] board = new Cell[3][3]; // holds the current state of the board for the computer to use if necessary
	private String whoseTurn = "Player 1"; //Indicate which player has a turn, initially it is the X player
	private Label gameStatus = new Label("Player 1's turn to play"); //Create and initialize a status label
	private Label numberOfDrawsLabel = new Label(); // displays the number of draws
	private boolean isPlayable = true; //Create a boolean to see if we can still play the game or it's over
	private ImageView backgroundImageView = new ImageView(); // holds the selected background
	private ImageView[] playerTokenImageView = new ImageView[9]; //The ImageView to set to token type
	private int numberOfMovesLeft = 9; // keeps track of the number of moves left for the random generator
	private int numberOfDraws = 0; // keeps track of the number of draws
	private HBox difficultyLevelRadioBox; // sets this box globally so the visibility can be changed depending on whether it's an all human game or not
	private HBox numberOfPlayersRadioBox = new HBox();  // access to the number of players radio button box to determine the setting
	private ImageView playersAvatarImageView[] = new ImageView[2]; // this is the ImageViews for the player avatars placed here to allow the image to be changed programattically
	private HBox[] avatarSelectionBoxs = new HBox[2]; // this is the avatar choice box set here to allow for altering the visibility
	private ArrayList<Combinations> winningCombinations = new ArrayList<>(); // this is the list of all possible winning combinations
	
	

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws Exception {		
		
		for (int i = 0; i < 2; i++) {
			playersAvatarImageView[i] = new ImageView();
			avatarSelectionBoxs[i] = new HBox(); 
		}
		for (int i = 0; i < 9; i++) {
			playerTokenImageView[i]= new ImageView(); 
		}
		
		player1 = new PlayerPane("X", false);
		player2 = new PlayerPane("O", false);
		
		StackPane visibleLayers = createGameLayers(); // This has all the visible layers for the game
		Scene scene = new Scene(visibleLayers, 1050, 740); //Create a scene and place it in the stage
		
		primaryStage.setTitle("TicTacToe");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	/**
	 * method used to create the bottom (base) display layer
	 * that everything else will stack on top of
	 * @return
	 */
	private StackPane createGameLayers() {
		
		BorderPane gamePane = createGamePane(); // this is the layer with all the game actions in it
		StackPane gameLayers = new StackPane(); // this is the pane that holds the background image first then all the other layers on top
		
		gameLayers.getChildren().addAll(backgroundImageView, gamePane);
		
		return gameLayers;
	}
	
	/**
	 * This creates the working gaming area layout that the
	 * board, menus, and information will be displayed on
	 * @return
	 */
	private BorderPane createGamePane() {
		
		BorderPane gamePane = new BorderPane(); // creates the overall board layout

		// creates and populates the top border pane
		StackPane topPane  = new StackPane(); //The top stackpane will hold all the menu items
		Rectangle topRectangle = rectangleCreator(1050,100); // this rectangle will define the area
		topPane.getChildren().add(topRectangle);
		VBox menuRowsVBox = createMenus();
		topPane.getChildren().add(menuRowsVBox );
		gamePane.setTop(topPane);
		
		// creates and populates the center border pane
		GridPane boardPane = createBoard();  // used to create and hold the board
		gamePane.setCenter(boardPane);
		
		// creates and populates the left border pane
		StackPane leftPane = new StackPane();  // this pane will be for player 1
		//Rectangle leftRectangle = rectangleCreator(225, 600); // this rectangle will define the area
		//player1.createSidePane();
		leftPane.getChildren().addAll(player1);
		gamePane.setLeft(leftPane);
		
		//creates and populates the right border pane
		StackPane rightPane  = new StackPane(); // this pane will be for player 2
		//Rectangle rightRectangle = rectangleCreator(225, 600); // this rectangle will define the area
		//player2.createSidePane();
		rightPane.getChildren().add(player2);
		gamePane.setRight(rightPane);
		
		// creates and populates the bottom border pane
		gameStatus.setFont(Font.font(24));
		StackPane bottomStackPane = new StackPane();
		bottomStackPane.getChildren().add(gameStatus);
		StackPane.setAlignment(gameStatus, Pos.CENTER);
		gamePane.setBottom(bottomStackPane);
		
		
		
		return gamePane;
	}
	
	/**
	 * method to create the rows of menus
	 * @return
	 */
	private VBox createMenus() {
		VBox menuRowsVBox = new VBox();
		menuRowsVBox .setSpacing(10);
		HBox menuRow1HBox = new HBox();

		HBox backgroundChoiceBox = createBackgroundChoiceBox();
		numberOfPlayersRadioBox = createNumberOfPlayersSelection();
		difficultyLevelRadioBox = createDifficultySelectionBox();
		difficultyLevelRadioBox.setVisible(false);
		HBox tokenTypeBox = createTokenChoiceBox();
		
		menuRow1HBox.getChildren().addAll(backgroundChoiceBox, numberOfPlayersRadioBox, 
				difficultyLevelRadioBox, tokenTypeBox);
		
		HBox menuRow2HBox = new HBox();
		
		numberOfDrawsLabel.setText("Number of Draws: " + String.valueOf(numberOfDraws));
		numberOfDrawsLabel.setPadding(new Insets(0,0,0,10));
		Button newGameButton = new Button("New Game");
		newGameButton.setOnMouseClicked(e -> {
			newGame();
		});
		Button resetAllButton = new Button("Reset All");
		resetAllButton.setOnMouseClicked(e -> {
			resetAll();
		});

		menuRow2HBox.setSpacing(10);
		menuRow2HBox.getChildren().addAll(numberOfDrawsLabel, newGameButton, resetAllButton);
		
		menuRowsVBox .getChildren().addAll(menuRow1HBox, menuRow2HBox);
		
		return menuRowsVBox;
	}
	
	/**
	 * This method starts a new game.  It does not reset any of the stats.
	 * After the board is cleared it checks if player 1 is a computer player
	 * or not.  If it is then it proceeds to make the first move.
	 */
	private void newGame() {
		clearBoard();
		numberOfMovesLeft = 9;
		gameStatus.setText("Player 1's turn to play");
		if (player1.isComputer())
			checkNextPlayer();
	}
	
	private void resetAll() {
		clearBoard();
		numberOfMovesLeft = 9;
		gameStatus.setText("Player 1's turn to play");
		numberOfDraws = 0;
		numberOfDrawsLabel.setText("Number of Draws: " + String.valueOf(numberOfDraws));
		for (int i = 0; i < 4; i++) {
			player1.setWinsLosses(i, 0);
			player2.setWinsLosses(i, 0);
		}
	}
	
	/**
	 * this creates the choice box to set the player from 
	 * Xs and Os to cats and dogs respectively
	 * @return
	 */
	private HBox createTokenChoiceBox() {
		ChoiceBox<String> tokenChoices = new ChoiceBox<>();
		tokenChoices.getItems().addAll("Primary", "Alternate");
		//Set the default value
		tokenChoices.setValue("Primary");
		Button tokenSwitchButton = new Button("Switch Token");		
		tokenSwitchButton.setOnAction(e -> tokenActionChoice(tokenChoices));
		
		HBox backgroundChoiceBox = new HBox();
		backgroundChoiceBox.setPadding(new Insets(0,0,0,5));
		backgroundChoiceBox.setSpacing(5);
		backgroundChoiceBox.getChildren().addAll(tokenChoices, tokenSwitchButton);
		return backgroundChoiceBox;
	}
	
	/**
	 * This method acts when the button is pressed and resets the images
	 * @param tokenChoices
	 */
	private void tokenActionChoice(ChoiceBox<String> tokenChoices) {
		String choice = tokenChoices.getValue();
		player1.changeTokenImage(choice);
		player2.changeTokenImage(choice);
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				board[x][y].resetImage();
	}
	
	/**
	 * This creates the space and populates it for the background
	 * selection checklist as well as the button that sets the 
	 * new background in place.
	 * @return
	 */
	private HBox createBackgroundChoiceBox() {
		ChoiceBox<String> backgroundChoices = new ChoiceBox<>();
		backgroundChoices.getItems().addAll("Abstract Orange", "Blue Disco", "Flower", 
				"Green Frosted Glass", "Pink Wind", "Sand Blur", 
				"Simple Blue", "Simple Green", "White");
		//Set the default value
		backgroundChoices.setValue("White");
		Button backgroundSwitchButton = new Button("Switch Background");		
		backgroundSwitchButton.setOnAction(e -> actionChoice(backgroundChoices));
		
		HBox backgroundChoiceBox = new HBox();
		backgroundChoiceBox.setPadding(new Insets(0,0,0,10));
		backgroundChoiceBox.setSpacing(5);
		backgroundChoiceBox.getChildren().addAll(backgroundChoices, backgroundSwitchButton);
		return backgroundChoiceBox;
	}
	
	/**
	 * method to pull the actual ImageView from the selection and return it so 
	 * that the main stage can set it.
	 * @param backgroundChoices
	 * @return
	 */
	private void actionChoice(ChoiceBox<String> backgroundChoices) {
		String choice = backgroundChoices.getValue();
		String fileString;
		switch (choice) {
			case "Abstract Orange": 
				fileString = "file:.\\resources\\backgrounds\\abstract_orange.jpg";
				break;
			case "Blue Disco": 
				fileString = "file:.\\resources\\backgrounds\\blue_disco.jpg";
				break;
			case "Flower": 
				fileString = "file:.\\resources\\backgrounds\\flower.jpg";
				break;
			case "Green Frosted Glass": 
				fileString = "file:.\\resources\\backgrounds\\green_frosted_glass.jpg";
				break;
			case "Pink Wind": 
				fileString = "file:.\\resources\\backgrounds\\pink_wind.jpg";
				break;
			case "Sand Blur": 
				fileString = "file:.\\resources\\backgrounds\\sand_blur.jpg";
				break;
			case "Simple Blue": 
				fileString = "file:.\\resources\\backgrounds\\blue_simple.jpg";
				break;
			case "Simple Green": 
				fileString = "file:.\\resources\\backgrounds\\green_simple.jpg";
				break;
			case "White": fileString = "file:.\\resources\\white.png";
				break;
			default: fileString = "file:.\\resources\\white.png";
		}
		Image backgroundImage = new Image(fileString, 1100, 740, false, true);
		backgroundImageView.setImage(backgroundImage);
	}
	
	/**
	 * This method creates the radio buttons for number of human players.
	 * When the choice is switched the board clears and starts all
	 * stats over fresh.  If the number of human players is 0 then 
	 * it initiates the first move.  Default setting is 2 human players.
	 * @return
	 */
	private HBox createNumberOfPlayersSelection() {
		RadioButton noHumans, oneHuman, twoHumans; //radio buttons for number of players
		noHumans = new RadioButton("0");
		oneHuman = new RadioButton("1");
		twoHumans = new RadioButton("2");
		
		ToggleGroup numberOfPlayersGroup = new ToggleGroup();
		noHumans.setToggleGroup(numberOfPlayersGroup);
		oneHuman.setToggleGroup(numberOfPlayersGroup);
		twoHumans.setToggleGroup(numberOfPlayersGroup);
		
		noHumans.setFont(Font.font(12));
		oneHuman.setFont(Font.font(12));
		twoHumans.setFont(Font.font(12));
		twoHumans.setSelected(true);
		
		noHumans.setOnAction(e -> {
			clearBoard();
			if (player2.isComputer()) {
				player1.setHardnessLevel(player2.getHardnessLevel());
				player1.setComputer(true);
			}
			else {
				player1.setHardnessLevel("easy");
				player2.setHardnessLevel("easy");
				player1.setComputer(true);
				player2.setComputer(true);
			}
			numberOfMovesLeft = 9;
			checkNextPlayer();
			difficultyLevelRadioBox.setVisible(true);
				
		}); 
		oneHuman.setOnAction(e -> {
			clearBoard();
			if (player2.isComputer()) {
				player2.setHardnessLevel(player2.getHardnessLevel());
				player1.setComputer(false);
			} else {
				player2.setHardnessLevel("easy");
				player2.setComputer(true);
				((RadioButton) difficultyLevelRadioBox.getChildren().get(1)).setSelected(true);
			}
			numberOfMovesLeft = 9;
			difficultyLevelRadioBox.setVisible(true);
			
		});
		twoHumans.setOnAction(e -> {
			clearBoard();
			player1.setComputer(false);
			player2.setComputer(false);
			numberOfMovesLeft = 9;
			difficultyLevelRadioBox.setVisible(false);
			((RadioButton) difficultyLevelRadioBox.getChildren().get(1)).setSelected(true);
		});
		
		Text numberOfPlayersText = new Text("Number of Players: ");
		numberOfPlayersText.setFont(Font.font(12));
		HBox numberOfPlayersBox = new HBox();
		numberOfPlayersBox.setPadding(new Insets(5,0,0,20));
		numberOfPlayersBox.setSpacing(5);
		numberOfPlayersBox.getChildren().addAll(numberOfPlayersText, noHumans, 
				oneHuman, twoHumans);
		return numberOfPlayersBox;
	}
	
	private HBox createDifficultySelectionBox() {
		
		RadioButton easyButton, mediumButton, hardButton;
		easyButton = new RadioButton("easy");
		mediumButton = new RadioButton("medium");
		hardButton = new RadioButton("hard");
		
		ToggleGroup difficultyLevelGroup = new ToggleGroup();
		easyButton.setToggleGroup(difficultyLevelGroup);
		mediumButton.setToggleGroup(difficultyLevelGroup);
		hardButton.setToggleGroup(difficultyLevelGroup);
		
		easyButton.setFont(Font.font(12));
		mediumButton.setFont(Font.font(12));
		hardButton.setFont(Font.font(12));
		easyButton.setSelected(true);
		
		easyButton.setOnAction(e -> {
			updateHardnessLevel(easyButton.getText());
			clearBoard();
			numberOfMovesLeft = 9;
			if (player1.isComputer())
				checkNextPlayer();			
		});
		mediumButton.setOnAction(e -> {
			updateHardnessLevel(mediumButton.getText());
			clearBoard();
			numberOfMovesLeft = 9;
			if (player1.isComputer())
				checkNextPlayer();			
		});
		hardButton.setOnAction(e -> {
			updateHardnessLevel(hardButton.getText());
			clearBoard();
			numberOfMovesLeft = 9;
			if (player1.isComputer())
				checkNextPlayer();			
		});
		
		Text difficultyLevelText = new Text("Difficulty Level: ");
		difficultyLevelText.setFont(Font.font(12));
		HBox difficultyLevelBox = new HBox();
		difficultyLevelBox.setPadding(new Insets(5, 0, 0, 20));
		difficultyLevelBox.setSpacing(5);
		difficultyLevelBox.getChildren().addAll(difficultyLevelText, easyButton, 
				mediumButton, hardButton);
		return difficultyLevelBox;
	}
	
	private void updateHardnessLevel(String hardnessLevel) {
		if (player1.isComputer()) {
			player1.setHardnessLevel(hardnessLevel);
		}
		if (player2.isComputer()) {
			player2.setHardnessLevel(hardnessLevel);
		}
		
	}
	
	/**
	 * This creates the visible playing board and 
	 * assigns the positions to the board array
	 * @return
	 */
	private GridPane createBoard() {
		// Pane to hold cell
		GridPane boardPane = new GridPane();
		int cellLinearPosition = 0;
		for (int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++) {
				boardPane.add(board[x][y] = new Cell(x, y, cellLinearPosition), y, x);
				cellLinearPosition++;
			}
		createWinningCombinations();
		return boardPane;
	}
	
	/**
	 * method to fill all winning combinations in an ArrayList
	 */
	private void createWinningCombinations() {
		//horizontal winning combinations
		for (int x = 0; x < 3; x++)
		    winningCombinations.add(new Combinations(board[x][0], board[x][1], board[x][2]));
		// vertical winning combinations
		for (int y = 0; y < 3; y++)
			winningCombinations.add(new Combinations(board[0][y], board[1][y], board[2][y]));
		// diagonal winning combinations
		winningCombinations.add(new Combinations(board[0][0], board[1][1], board[2][2]));
		winningCombinations.add(new Combinations(board[0][2], board[1][1], board[2][0]));		
	}

	/**
	 * Factory of sorts to create a static type of rectangle for the purposes
	 * of this assignment
	 * @param xDimension
	 * @param yDimension
	 * @return
	 */
	public Rectangle rectangleCreator(int xDimension, int yDimension) {
		Rectangle rectangle = new Rectangle(xDimension, yDimension);
		rectangle.setFill(null);
		rectangle.setStroke(Color.BLACK);

		return rectangle;
	}
	
	private void clearBoard() {
		for (int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				board[x][y].setToken(" ");
		isPlayable = true;
		whoseTurn = "Player 1";
	}
	
	/**
	 * Determine if the cells are all occupied
	 * @return
	 */
	public boolean isFull() {
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				if (board[x][y].getToken().equals(" "))
					return false;
		return true;
	}
	
	private void setWinningStats(String player) {
		if (player.equals("Player 1")) {
			if (!player1.isComputer())
				player1.setWinsLosses(0,(player1.getWinsLosses()[0] + 1));
			else
				player1.setWinsLosses(2,(player1.getWinsLosses()[2] + 1));
			if (!player2.isComputer())
				player2.setWinsLosses(1,(player2.getWinsLosses()[1] + 1));
			else
				player2.setWinsLosses(3,(player2.getWinsLosses()[3] + 1));;
		} else {
			if (!player2.isComputer())
				player2.setWinsLosses(0,(player2.getWinsLosses()[0] + 1));
			else
				player2.setWinsLosses(2,(player2.getWinsLosses()[2] + 1));
			if (!player1.isComputer())
				player1.setWinsLosses(1,(player1.getWinsLosses()[1] + 1));
			else
				player1.setWinsLosses(3,(player1.getWinsLosses()[3] + 1));
		}
	}
	
	/**
	 * Determine if the player with the specified token wins
	 * @param args
	 */
	public boolean isWon() {
		for (Combinations winningCombo: winningCombinations) {
			if (winningCombo.isGameWon()) {
				if (whoseTurn.equals("Player 1")) {
					setWinningStats("Player 1");
				} else {
					setWinningStats("Player 2");
				}
					
				return true;
			}
				
		}
			return false;		
	}
	
	/**
	 * it's a method for the system to check the player after the 
	 * current one to see if they are a computer player and to 
	 * initiate a move for them
	 */
	private void checkNextPlayer() {
		if (whoseTurn.equals("Player 1")) {
			if (player1.isComputer()) {
				checkBoard(player1);
				checkGameStatus();
			}	
		}
		else {
			if ( player2.isComputer()) {
				checkBoard(player2);
				checkGameStatus();
			}
		}
		
	}
	
	/**
	 * this method is to set the status for the player that won and stop the game
	 * @param player
	 */
	private void playerWon(String player) {
		gameStatus.setText(player + " won!  The game is over.");
		setWinningStats(player);
		whoseTurn = " ";
		isPlayable = false;
	}
	
	/**
	 * this is a re-hash of the handle event to determine if the game is 
	 * over or if we just need to switch players
	 */
	private void checkGameStatus() {
		// Check game status
		switch(gameState()) {
		case 1:
			playerWon("Player 1");
			break;
		case -1:
			playerWon("Player 2");
			break;
		case 0:
			gameStatus.setText("Draw! The game is over.");
			isPlayable = false;
			numberOfDraws = numberOfDraws + 1;
			numberOfDrawsLabel.setText("Number of Draws: " + String.valueOf(numberOfDraws));
			break;
		default:
			// Change the turn
			if (whoseTurn.equals("Player 1"))
				whoseTurn = "Player 2";
			else 
				whoseTurn = "Player 1";
			gameStatus.setText(whoseTurn + 
					"'s turn.  Number of moves left are " + numberOfMovesLeft);
			checkNextPlayer();
		}
	}
	
	/**
	 * method to use the Player AI methods to make the computer's next move
	 * @param player
	 */
	private void checkBoard(PlayerPane player) {
		int[] computerMove = player.computerMove();
		board[computerMove[0]][computerMove[1]].setToken(player.getToken());
		numberOfMovesLeft = numberOfMovesLeft - 1;
	}
	
	/**
	 * This will check on the current state of the game as far as
	 * if X wins return 1 (max)
	 * if Y wins return -1 (min)
	 * if it's a draw return 0
	 * if it's none of these it will return 9 because it's as good
	 * a number as any
	 * @return
	 */
	public int gameState() {
		if (whoWon().equals("X"))
			return 1;
		else if (whoWon().equals("O"))
			return -1;
		else if (isFull())
			return 0;
		
		return 9;
	}
	
	/**
	 * Determine if the player with the specified token wins
	 * @param args
	 */
	private String whoWon() {
		for (Combinations winningCombo: winningCombinations) {
			if (winningCombo.isGameWon())
				return winningCombo.getCells()[0].getToken();
		}
		return " ";		
	}
	
	/**
	 * base method to start the program and redirect to launch
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/**
	 * inner class to deal with everything that happens in the cell
	 * and 
	 * @author texas
	 *
	 */
	public class Cell extends StackPane {
		
		private String token = " ";
		private Rectangle border;
		private int xCoordinate, yCoordinate, cellLinearPosition;
		
		public Cell() {
			createCell();
			this.setOnMouseClicked(e -> {
				handleMouseClick();	
				if (isPlayable)
					checkNextPlayer();
			});
		}
		
		public Cell(int xCoordinate, int yCoordinate, int cellLinearPosition) {
			this.cellLinearPosition = cellLinearPosition;
			this.xCoordinate = xCoordinate;
			this.yCoordinate = yCoordinate;
			createCell();
			this.setOnMouseClicked(e -> {
				handleMouseClick();	
				if (isPlayable)
					checkNextPlayer();
			});
		}
		
		
		
		/**
		 * Creates the visible cell that we will be clicking on
		 * and set the font size of the letter that will be placed
		 * in the square
		 */
		private void createCell() {
			border = new Rectangle(200, 200);
			border.setFill(null);
			border.setStroke(Color.BLACK);
			setAlignment(Pos.CENTER);
			getChildren().addAll(border, playerTokenImageView[cellLinearPosition]);
		}
		
		/**
		 * return token value
		 * @return
		 */
		public String getToken() {
			return token;
		}
		
		/**
		 * This method is used to reset the token image
		 * when it is changed from primary to 
		 * alternate.
		 */
		public void resetImage() {
			setToken(token);
		}
		
		/**
		 * This sets the value of the token without drawing the image. 
		 * It is used to speed up the min/max algorithm since drawing
		 * isn't needed for computation.
		 * @param token
		 */
		public void setTokenValue(String token) {
			this.token = token;
		}
		
		/**
		 * sets the token value in the cell
		 * @param token
		 */
		public void setToken(String token) {
			this.token = token;
			if (token.equals("X")) {
				drawX();
			}
			else if (token.equals("O")) {
				drawO();
			}
			else if (token.equals(" ")) {
				clearCell();
			}
		}		

		/**
		 * set the text in the cell to X
		 */
		private void drawX() {
			playerTokenImageView[cellLinearPosition].setImage(player1.getTokenImage());
		}
		
		/**
		 * set the text in the cell to O
		 */
		private void drawO() {
			playerTokenImageView[cellLinearPosition].setImage(player2.getTokenImage());
		}
		
		/**
		 * clears the image from the cell
		 */
		private void clearCell() {
			playerTokenImageView[cellLinearPosition].setImage(null);
		}
		
		
		/**
		 * Handle mouse click events
		 */
		private void handleMouseClick() {
			//If the cell is empty and the game is not over
			if (token.equals(" ") && !whoseTurn.equals(" ") && isPlayable) {
				// set token in the cell
				if (whoseTurn.equals("Player 1"))
					setToken(player1.getToken());
				else
					setToken(player2.getToken());
				numberOfMovesLeft = numberOfMovesLeft - 1;
				// Check game status
				if (isWon()) {
					gameStatus.setText(whoseTurn + " won!  The game is over.");
					whoseTurn = " ";
					isPlayable = false;
				}
				else if (isFull()) {
					gameStatus.setText("Draw! The game is over.");
					isPlayable = false;
					numberOfDraws = numberOfDraws + 1;
					numberOfDrawsLabel.setText("Number of Draws: " + String.valueOf(numberOfDraws));
				}
				else if (isPlayable){
					// Change the turn
					if (whoseTurn.equals("Player 1"))
						whoseTurn = "Player 2";
					else 
						whoseTurn = "Player 1";
					gameStatus.setText(whoseTurn + "'s turn.  Number of moves left are " + numberOfMovesLeft);
				}
			}
			
		}
		
		/**
		 * @return the xCoordinate
		 */
		public int getxCoordinate() {
			return xCoordinate;
		}

		/**
		 * @return the yCoordinate
		 */
		public int getyCoordinate() {
			return yCoordinate;
		}

	}
	
	
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/**
	 * Inner class to create a player object.  Because the object is identified on the board
	 * by one of the two side boxes it will extend VBox.  This main class just handles
	 * the player attributes
	 *
	 */
	public class Player extends VBox {
		
		private String token; // String to hold the token this player has
		private boolean isComputer; // boolean to say whether this player is a computer or not
		private String hardnessLevel; // if it is a computer, what level of hardness will he play at
		private Image tokenImage; // The token image that will be used by this player
		
		public Player (String token, boolean isComputer) {
			this.token = token;
			this.isComputer = isComputer;
			this.hardnessLevel = "easy";
			if (token.equals("X"))
				tokenImage = new Image("file:.\\resources\\tokens\\x.png", 150, 150, true, true);
			else
				tokenImage = new Image("file:.\\resources\\tokens\\o.png", 150, 150, true, true);
		}
		
		public int[] computerMove() {
			int[] choice = {0,0}; //x and y coordinates for the computer's move
			switch(hardnessLevel) {
				case "easy":
					choice = easyPlay();
					break;
				case "medium":
					choice = mediumPlay();
					break;
				case "hard":
					choice = hardPlay();
					break;
			}
			
			return choice;
		}
		
		/**
		 * This method randomly chooses an integer from 0 to
		 * number of empty spaces left.  Whatever the integer
		 * returned it just places the token in the empty spot
		 * identified
		 * @return
		 */
		public int[] easyPlay() {
			int[] choice = {0,0};
			Random random = new Random();
			int computerMove = random.nextInt(numberOfMovesLeft);
			int count = 0;
			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++) {
					if (board[x][y].getToken().equals(" ")){
						if (count == computerMove ) {
							choice[0] = x;
							choice[1] = y;
							return choice;
						} else
							count++;
					}
				}
			return choice;
			
		}
		
		/**
		 * This method will first go through all winning combination sets 
		 * to see if two of the opposite player's tokens are side-by-side
		 * and if the other spot is empty.  If it is then it will place
		 * its token in that spot, otherwise it will randomly pick a place
		 * on the board by calling the easyPlay() method.
		 * @return
		 */
		public int[] mediumPlay() {
			for (Combinations combinations: winningCombinations) {
				if (combinations.consecutiveCellsFilled()[0] != 3)
					return combinations.consecutiveCellsFilled();
			}
			return easyPlay();
		}
		
		/**
		 * This method will call the minMax() method to return the optimal place
		 * on the board given the current board scenario.
		 * @return
		 */
		public int[] hardPlay() {
			int[] choice = {0,0};
			int bestScore = 0;
			for (int x = 0; x < 3; x ++) {
				for (int y = 0; y < 3; y++) {
					if (board[x][y].getToken().equals(" ")) {
						board[x][y].setToken(token);
						int score;
						if (token.equals("X")) {
							bestScore = -1;
							score = minMax(-1, 1, false);
						} else {
							bestScore = 1;
							score = minMax(-1, 1, true);
						}
						board[x][y].setToken(" ");
						if (token.equals("X")) {
							if (score > bestScore) {
								bestScore = score;
								choice[0] = x;
								choice[1] = y;
							} 
						} else if (score < bestScore) {
							bestScore = score;
							choice[0] = x;
							choice[1] = y;
						}
					}
				}
			}
			
			return choice;
		}
		
		/**
		 * This is the min/max algorithm.  Usually the board or current position would
		 * be the first parameter, but since the board is a parameter passed in when
		 * creating the class we can just use that field directly since we aren't altering
		 * it.  This will actually go through all possible permutations of the board
		 * and return the best, or equivalently best, possible route to take (i.e. if the 
		 * corner is the best first move it calculates then all four corners are equally as
		 * good so it will just choose the first corner on the board).  The algorithm
		 * was gotten from Sebastian Lague (Lague, 2018).
		 * 
		 * Sebastian Lague (2018, April 20) Algorithms Explained – minimax and alpha-beta pruning
		 * https://www.youtube.com/watch?v=l-hh51ncgDI&list=PLm5TVZCxQmUyx7DCe0iVkaJVWdaANtsv8&index=2
		 * @param depth
		 * @param minimum
		 * @param maximum
		 * @param maximizingPlayer
		 * @return
		 */
		private int minMax(int minimum, int maximum, boolean maximizingPlayer) {
			int bestScore = 0;
			int gameStateResults = gameState();
			if (gameStateResults != 9) {
				return gameStateResults;
			}
			if (maximizingPlayer) {
				bestScore = minimum;
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y ++) {
						if (board[x][y].getToken().equals(" ")) {
							//board[x][y].setToken("X");
							board[x][y].setTokenValue("X");
							int score = minMax(minimum, maximum, false);
							//board[x][y].setToken(" ");
							board[x][y].setTokenValue(" ");
							bestScore = Math.max(score, bestScore);
							if (maximum <= bestScore) // this keeps it from going on when it's found as good of a result as it's going to find
								break;
						}
					}
				}
			} else {
				bestScore = maximum;
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 3; y ++) {
						if (board[x][y].getToken().equals(" ")) {
							//board[x][y].setToken("O");
							board[x][y].setTokenValue("O");
							int score = minMax(minimum, maximum, true);
							//board[x][y].setToken(" ");
							board[x][y].setTokenValue(" ");
							bestScore = Math.min(score, bestScore);
							if (bestScore <= minimum) // this keeps it from going on when it's found as good of a result as it's going to find
								break;
						}
					}
				}
				
			}
			
			return bestScore;
		}

	/*--------------------------Getters / Setters and their helper methods   ---------------------------*/
		
		/**
		 * @return the token
		 */
		public String getToken() {
			return token;
		}

		/**
		 * @param token the token to set
		 */
		public void setToken(String token) {
			this.token = token;
		}

		/**
		 * @return the isComputer
		 */
		public boolean isComputer() {
			return isComputer;
		}

		/**
		 * @param isComputer the isComputer to set
		 */
		public void setComputer(boolean isComputer) {
			this.isComputer = isComputer;
		}

		/**
		 * @return the hardnessLevel
		 */
		public String getHardnessLevel() {
			return hardnessLevel;
		}

		/**
		 * @param hardnessLevel the hardnessLevel to set
		 */
		public void setHardnessLevel(String hardnessLevel) {
			this.hardnessLevel = hardnessLevel;
		}
		
		/**
		 * @return the tokenImage
		 */
		public Image getTokenImage() {
			return tokenImage;
		}

		/**
		 * @param tokenImage the tokenImage to set
		 */
		public void setTokenImage(Image tokenImage) {
			this.tokenImage = tokenImage;
		}

		/**
		 * method used to change the image file based on the choicebox selection.
		 * @param imageType
		 */
		public void changeTokenImage(String imageType) {
			if (token.equals("X")) {
				if (imageType.equals("Primary"))
					tokenImage = new Image("file:.\\resources\\tokens\\x.png", 150, 150, true, true);
				else
					tokenImage = new Image("file:.\\resources\\tokens\\cat.png", 150, 150, true, true);
			} else {
				if (imageType.equals("Primary"))
					tokenImage = new Image("file:.\\resources\\tokens\\o.png", 150, 150, true, true);
				else
					tokenImage = new Image("file:.\\resources\\tokens\\dog.png", 150, 150, true, true);
			}
			
		}

	}
	
	
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/**
	 * This class extends the Player class and handles all the visuals for the player
	 * VBox.  This is the object that is actually created in the game as it
	 * has all the elements of it's parent and this.
	 *
	 */
	public class PlayerPane extends Player {

		
		private VBox sidePaneBox; // this is the side pane box for the player
		private Image avatarImage; // This is the avatar selected for the given player
		private int[] winsLosses = new int[4]; // this stores the number of wins/losses for the player (human wins = 0, human losses = 1, computer wins = 2, computer losses = 3)
		private Label[] playerStatusLabels = new Label[6];  // this holds the labels for status for player wins/losses  (human wins/losses, computer wins/losses, overall wins/losses)
		private int player = 0;
		
		/**
		 * Constructor from Player superclass
		 * @param token
		 * @param isComputer
		 */
		public PlayerPane(String token, boolean isComputer) {
			super(token, isComputer);
			if (!super.getToken().equals("X"))
				player = 1;
			for (int i = 0; i < 6; i++) {
				playerStatusLabels[i] = new Label(); 
				playerStatusLabels[i].setPadding(new Insets(5,0,5,5));
				playerStatusLabels[i].setFont(Font.font(12));
			}
			createSidePane();
		}
		
		/**
		 * Override the parent classes setComputer method to change the visibility
		 * of the avatar image selection and to change the avatar image to whatever
		 * the current hardness level dictates
		 */
		@Override
		public void setComputer(boolean isComputer) {
			super.setComputer(isComputer);
			avatarSelectionBoxs[player].setVisible(!isComputer);
			if (!isComputer) {
				String fileString;
				if (player == 0)
					fileString = "file:.\\resources\\player_avatars\\vampire_smiley.png";
				else 
					fileString = "file:.\\resources\\player_avatars\\ghost_skull.png";
				avatarImage = new Image(fileString, 180, 180, true, true);
				playersAvatarImageView[player].setImage(avatarImage);
			}
		}
		
		/**
		 * Overrides the parent class so that when the hardness
		 * level changes, the avatar changes too
		 */
		@Override
		public void setHardnessLevel(String hardnessLevel) {
			super.setHardnessLevel(hardnessLevel);
			setComputerAvatarImage(hardnessLevel);
		}
		
		/**
		 * This method is to reduce code.  This just sets the avatar for 
		 * the computer based on the hardness level given.
		 * @param hardnessLevel
		 */
		private void setComputerAvatarImage(String hardnessLevel) {
			String fileName;
			switch (hardnessLevel) {
				case "easy":
					fileName = "file:.\\resources\\computer_avatars\\easy.jpg";
					break;
				case "medium":
					fileName = "file:.\\resources\\computer_avatars\\medium.png";
					break;
				case "hard":
					fileName = "file:.\\resources\\computer_avatars\\hard.png";
					break;
				default:
					fileName = "file:.\\resources\\computer_avatars\\easy.jpg";
			}
			avatarImage = new Image(fileName, 180, 180, true, true);
			playersAvatarImageView[player].setImage(avatarImage);
		}

		/**
		 * This method creates the side pane for the given player
		 * and sets all the elements correctly for it.
		 */
		public void createSidePane() {

			StackPane avatarPane = new StackPane(); // pane created to center the avatar
			String fileString; 
			if (player == 0)  
				fileString ="file:.\\resources\\player_avatars\\vampire_smiley.png";
			else 
				fileString = "file:.\\resources\\player_avatars\\ghost_skull.png";
			avatarImage = new Image(fileString, 180, 180, true, true);
			playersAvatarImageView[player].setImage(avatarImage);
			avatarPane.getChildren().add(playersAvatarImageView[player]);
			avatarSelectionBoxs[player] = createAvatarChoiceBox(); 
			Label playerLbl = new Label();
			if (player == 0)
				playerLbl.setText("Player 1");
			else 
				playerLbl.setText("Player 2");
			playerLbl.setFont(Font.font(24));
			StackPane labelPane = new StackPane();
			labelPane.getChildren().add(playerLbl);
			labelPane.setPadding(new Insets(5,0,5,0));
			for (int i = 0; i < 4; i++) {
				updateWinsLosses(i);
			}		
			
			this.setAlignment(Pos.CENTER);
			this.getChildren().addAll(avatarPane, labelPane, avatarSelectionBoxs[player]);
			for(int i = 0; i < 6; i++) {
				this.getChildren().add(playerStatusLabels[i]);
			}
		}
		
		/**
		 * This method creates the selection box and button for the player
		 * choosing their avatar.  These are only human player choices. 
		 * @return
		 */
		private HBox createAvatarChoiceBox() {
			ChoiceBox<String> avatarChoices = new ChoiceBox<>();
			avatarChoices.getItems().addAll("Anime Man", "Archer", "Bunny", 
					"Ghost Skull", "Goblin", "Maze Man", "Penguin", "Snowman", 
					"Steampunk", "Sugar Skull", "Vampire Smiley", "Witch");
			//Set the default value
			if (player == 0)
				avatarChoices.setValue("Vamire Smiley");
			else 
				avatarChoices.setValue("Ghost Skull");		
			Button avatarSwitchButton = new Button("Switch Avatar");		
			avatarSwitchButton.setOnAction(e -> avatarActionChoice(avatarChoices));
			
			HBox avatarChoiceBox = new HBox();
			avatarChoiceBox.setPadding(new Insets(0,0,0,5));
			avatarChoiceBox.setSpacing(5);
			avatarChoiceBox.getChildren().addAll(avatarChoices, avatarSwitchButton);
			return avatarChoiceBox;
		}

		/**
		 * This method listens for the switch avatar button to be 
		 * pressed.  If it is then the avatar will change based on the
		 * choice given.
		 * @param avatarChoices
		 */
		private void avatarActionChoice(ChoiceBox<String> avatarChoices) {
			String choice = avatarChoices.getValue();
			String fileString;
			switch (choice) {
				case "Anime Man": 
					fileString = "file:.\\resources\\player_avatars\\anime_man.png";
					break;
				case "Archer": 
					fileString = "file:.\\resources\\player_avatars\\archer.png";
					break;
				case "Bunny": 
					fileString = "file:.\\resources\\player_avatars\\bunny.png";
					break;
				case "Ghost Skull": 
					fileString = "file:.\\resources\\player_avatars\\ghost_skull.png";
					break;
				case "Goblin": 
					fileString = "file:.\\resources\\player_avatars\\goblin.png";
					break;
				case "Maze Man": 
					fileString = "file:.\\resources\\player_avatars\\maze_man.png";
					break;
				case "Penguin": 
					fileString = "file:.\\resources\\player_avatars\\penguin.png";
					break;
				case "Snowman": 
					fileString = "file:.\\resources\\player_avatars\\snowman.png";
					break;
				case "Steampunk": 
					fileString = "file:.\\resources\\player_avatars\\steampunk_woman.png";
					break;
				case "Sugar Skull": 
					fileString = "file:.\\resources\\player_avatars\\sugar_skull.png";
					break;
				case "Vampire Smiley": 
					fileString = "file:.\\resources\\player_avatars\\vampire_smiley.png";
					break;
				case "Witch": 
					fileString = "file:.\\resources\\player_avatars\\witch.jpg";
					break;
				default: 
					if (player == 0)
						fileString = "file:.\\resources\\player_avatars\\vampire_smiley.png";
					else 
						fileString = "file:.\\resources\\player_avatars\\ghost_skull.png";
					
			}

			avatarImage = new Image(fileString, 180, 180, true, true);
			playersAvatarImageView[player].setImage(avatarImage);
		}
		
	/*--------------------------Getters / Setters and their helper methods   ---------------------------*/
		
		/**
		 * @return the sidePaneBox
		 */
		public VBox getSidePaneBox() {
			return sidePaneBox;
		}

		/**
		 * @return the humanWins
		 */
		public int[] getWinsLosses() {
			return winsLosses;
		}

		/**
		 * sets the value for the given index
		 * 0 = human wins
		 * 1 = human losses
		 * 2 = computer wins
		 * 3 = computer losses
		 * @param humanWins the humanWins to set
		 */
		public void setWinsLosses(int index, int value) {
			winsLosses[index] = value;
			updateWinsLosses(index);
		}
		
		/**
		 * updates the wins and losses text
		 * @param humanWins
		 */
		private void updateWinsLosses(int index) {
			switch(index) {
			case 0:
				playerStatusLabels[index].setText("Human Wins: " + 
						String.valueOf(winsLosses[index]));
				break;
			case 1:
				playerStatusLabels[index].setText("Human Losses: " + 
						String.valueOf(winsLosses[index]));
				break;
			case 2:
				playerStatusLabels[index].setText("Computer Wins: " + 
						String.valueOf(winsLosses[index]));
				break;
			case 3:
				playerStatusLabels[index].setText("Computer Losses: " + 
						String.valueOf(winsLosses[index]));
				break;
			}
			
			int overallValue = winsLosses[0] + winsLosses[2];
			String overallValueString = String.valueOf(overallValue);
			playerStatusLabels[4].setText("Overall Wins: " + overallValueString);
			overallValue = winsLosses[1] + winsLosses[3];
			overallValueString = String.valueOf(overallValue);
			playerStatusLabels[5].setText("Overall Losses: " + overallValueString);
		}


		
	}

	
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------*/
	/**
	 * This is the inner class that will deal with holding each combination 
	 * of wins in the game.  There will be 8 of these objects held in 
	 * and ArrayList in the main game.
	 * 
	 */
	public class Combinations {
		
		private Cell[] cells;
		
		/**
		 * @return the cells
		 */
		public Cell[] getCells() {
			return cells;
		}

		public Combinations(Cell... cells) {
			this.cells = cells;
		}
		
		public boolean isGameWon() {
			if (cells[0].getToken().equals(" "))
				return false;
			return cells[0].getToken().equals(cells[1].getToken()) && cells[0].getToken().equals(cells[2].getToken());
		}
		
		public int[] consecutiveCellsFilled() {
			int[] coordiantes = {3,3};
			if (!(cells[0].getToken().equals(" ") && cells[1].getToken().equals(" ") && cells[2].getToken().equals(" "))) {
				if (cells[0].getToken().equals(cells[1].getToken()) || cells[1].getToken().equals(cells[2].getToken())) {
					if (!cells[1].getToken().equals(" ") && cells[2].getToken().equals(" ")) {
						coordiantes[0] = cells[2].getxCoordinate();
						coordiantes[1] = cells[2].getyCoordinate();
					}
					else if (!cells[1].getToken().equals(" ") && cells[0].getToken().equals(" ")) {
						coordiantes[0] = cells[0].getxCoordinate();
						coordiantes[1] = cells[0].getyCoordinate();
					}
				}
			}
			return coordiantes;
		}
		

	}



}
