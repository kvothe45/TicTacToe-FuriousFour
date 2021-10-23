package application;

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
	
	private String whoseTurn = "Player 1"; //Indicate which player has a turn, initially it is the X player
	private Cell[][] board = new Cell[3][3]; // Create and initialize cell
	private Label gameStatus = new Label("Player 1's turn to play"); //Create and initialize a status label
	private boolean isPlayable = true; //Create a boolean to see if we can still play the game or it's over
	private ImageView backgroundImageView = new ImageView(); // holds the selected background
	private ImageView player1AvatarImageView = new ImageView(); // link to the avatar ImageView object for player1
	private ImageView player2AvatarImageView = new ImageView(); // link to the avatar ImageView object for player2
	private PlayerPane player1, player2; // sets up the two player objects
	private int numberOfMovesLeft = 9; // keeps track of the number of moves left for the random generator
	HBox difficultyLevelRadioBox; // sets this box globally so the visibility can be changed depending on whether it's an all human game or not
	private Global global = new Global();
	HBox numberOfPlayersRadioBox = new HBox();
	

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws Exception {		
		
		player1 = new PlayerPane(global, "X", false);
		//player1.setAvatarImageView(player1AvatarImageView);
		player2 = new PlayerPane(global, "O", false);
		//player2.setAvatarImageView(player2AvatarImageView);
		StackPane visibleLayers = createGameLayers(); // This has all the visible layers for the game
		Scene scene = new Scene(visibleLayers, 1025, 740); //Create a scene and place it in the stage
		
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
		
		gameLayers.getChildren().addAll(backgroundImageView , gamePane);
		
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
		Rectangle topRectangle = rectangleCreator(1025,100); // this rectangle will define the area
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
		leftPane.getChildren().addAll(player1.createSidePane());
		gamePane.setLeft(leftPane);
		
		//creates and populates the right border pane
		StackPane rightPane  = new StackPane(); // this pane will be for player 2
		//Rectangle rightRectangle = rectangleCreator(225, 600); // this rectangle will define the area
		rightPane.getChildren().add(player2.createSidePane());
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
		
		menuRow1HBox.getChildren().addAll(backgroundChoiceBox, numberOfPlayersRadioBox, 
				difficultyLevelRadioBox);
		menuRowsVBox .getChildren().addAll(menuRow1HBox);
		
		return menuRowsVBox;
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
		backgroundChoiceBox.setPadding(new Insets(0,0,0,5));
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
			player1 = new PlayerPane(global, "X", true);
			player2 = new PlayerPane(global, "O", true);
			numberOfMovesLeft = 9;
			player1.setNumberOfMovesLeft(numberOfMovesLeft);
			player2.setNumberOfMovesLeft(numberOfMovesLeft);
			checkNextPlayer();
			difficultyLevelRadioBox.setVisible(true);
		}); 
		oneHuman.setOnAction(e -> {
			clearBoard();
			player1 = new PlayerPane(global, "X", false);
			player2 = new PlayerPane(global, "O", true);
			numberOfMovesLeft = 9;
			player2.setNumberOfMovesLeft(numberOfMovesLeft);
			difficultyLevelRadioBox.setVisible(true);
			player2.setHardnessLevel("easy");
		});
		twoHumans.setOnAction(e -> {
			clearBoard();
			player1 = new PlayerPane(global, "X", false);
			player2 = new PlayerPane(global, "O", false);
			numberOfMovesLeft = 9;
			difficultyLevelRadioBox.setVisible(false);
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
			player1.setNumberOfMovesLeft(numberOfMovesLeft);
			player2.setNumberOfMovesLeft(numberOfMovesLeft);
			if (player1.isComputer())
				checkNextPlayer();			
		});
		mediumButton.setOnAction(e -> {
			updateHardnessLevel(mediumButton.getText());
			clearBoard();
			numberOfMovesLeft = 9;
			player1.setNumberOfMovesLeft(numberOfMovesLeft);
			player2.setNumberOfMovesLeft(numberOfMovesLeft);
			if (player1.isComputer())
				checkNextPlayer();			
		});
		hardButton.setOnAction(e -> {
			updateHardnessLevel(hardButton.getText());
			clearBoard();
			numberOfMovesLeft = 9;
			player1.setNumberOfMovesLeft(numberOfMovesLeft);
			player2.setNumberOfMovesLeft(numberOfMovesLeft);
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
		for (int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				boardPane.add(board[x][y] = new Cell(), y, x);
		return boardPane;
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
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
				if (board[x][y].getToken().equals(" "))
					return false;
		return true;
	}
	
	/**
	 * Determine if the player with the specified token wins
	 * @param args
	 */
	public boolean isWon(String whoseTurn) {
		
		String token;
		if (whoseTurn.equals("Player 1"))
			token = player1.getToken();
		else
			token = player2.getToken();
		
		// check all rows
		for (int x = 0; x < 3; x++) 
			if (board[x][0].getToken().equals(token)
					&& board[x][1].getToken().equals(token)
					&& board[x][2].getToken().equals(token))
				return true;
		// check all columns
		for (int y = 0; y < 3; y++)
			if (board[0][y].getToken().equals(token)
					&& board[1][y].getToken().equals(token)
					&& board[2][y].getToken().equals(token))
				return true;
		// check diagonals
		if (board[0][0].getToken().equals(token)
				&& board[1][1].getToken().equals(token)
				&& board[2][2].getToken().equals(token))
			return true;
		if (board[2][0].getToken().equals(token)
				&& board[1][1].getToken().equals(token)
				&& board[0][2].getToken().equals(token))
			return true;
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
	 * this is a re-hash of the handle event to determine if the game is 
	 * over or if we just need to switch players
	 */
	private void checkGameStatus() {
		// Check game status
		if (isWon(whoseTurn)) {
			gameStatus.setText(whoseTurn + " won!  The game is over.");
			whoseTurn = " ";
			isPlayable = false;
		}
		else if (isFull()) {
			gameStatus.setText("Draw! The game is over.");
			isPlayable = false;
		}
		else if (isPlayable){
			// Change the turn
			if (whoseTurn.equals("Player 1"))
				whoseTurn = "Player 2";
			else 
				whoseTurn = "Player 1";
			gameStatus .setText(whoseTurn + "'s turn.  Number of moves left are " + numberOfMovesLeft);
			checkNextPlayer();
		}
	}
	
	/**
	 * method to use the Player AI methods to make the computer's next move
	 * @param player
	 */
	private void checkBoard(PlayerPane player) {
		player.setBoardState(board);
		player.setNumberOfMovesLeft(numberOfMovesLeft);
		int[] computerMove = player.computerMove();
		board[computerMove[0]][computerMove[1]].setToken(player.getToken());
		numberOfMovesLeft--;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * inner class to deal with everything that happens in the cell
	 * and 
	 * @author texas
	 *
	 */
	public class Cell extends StackPane {
		
		//private Text text = new Text();
		private String token = " ";
		private Rectangle border;
		private Image image;
		private ImageView imageView;
		
		public Cell() {
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
			imageView = new ImageView(image);
			getChildren().addAll(border, imageView);
		}
		
		/**
		 * return token value
		 * @return
		 */
		public String getToken() {
			return token;
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
			image = new Image("file:.\\resources\\tokens\\x.png", 150, 150, true, true);
			imageView.setImage(image);
		}
		
		/**
		 * set the text in the cell to O
		 */
		private void drawO() {
			image = new Image("file:.\\resources\\tokens\\o.png", 150, 150, true, true);
			imageView.setImage(image);
		}
		
		/**
		 * clears the image from the cell
		 */
		private void clearCell() {
			imageView.setImage(null);
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
				numberOfMovesLeft--;
			}
			// Check game status
			if (isWon(whoseTurn)) {
				gameStatus.setText(whoseTurn + " won!  The game is over.");
				whoseTurn = " ";
				isPlayable = false;
			}
			else if (isFull()) {
				gameStatus.setText("Draw! The game is over.");
				isPlayable = false;
			}
			else if (isPlayable){
				// Change the turn
				if (whoseTurn.equals("Player 1"))
					whoseTurn = "Player 2";
				else 
					whoseTurn = "Player 1";
				gameStatus .setText(whoseTurn + "'s turn.  Number of moves left are " + numberOfMovesLeft);
			}
		}

	}


}
