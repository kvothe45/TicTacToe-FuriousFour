package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
import javafx.stage.Stage;


public class TicTacToe extends Application {
	
	private String whoseTurn = "Player 1"; //Indicate which player has a turn, initially it is the X player
	private Cell[][] board = new Cell[3][3]; // Create and initialize cell
	private Label gameStatus = new Label("Player 1's turn to play"); //Create and initialize a status label
	private boolean isPlayable = true; //Create a boolean to see if we can still play the game or it's over
	private ImageView backgroundImageView = new ImageView(); // holds the selected background
	private Player player1, player2; // sets up the two player objects
	private int numberOfMovesLeft = 9;
	

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws Exception {		
		
		player1 = new Player("X", false);
		player2 = new Player("O", true);
		StackPane visibleLayers = createGameLayers(); // This has all the visible layers for the game
		Scene scene = new Scene(visibleLayers, 1000, 740); //Create a scene and place it in the stage
		
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
		
		GridPane boardPane = createBoard();  // used to create and hold the board
		BorderPane gamePane = new BorderPane(); // creates the overall board layout
		
		gamePane.setCenter(boardPane);
		
		gameStatus.setFont(Font.font(24));
		gamePane.setBottom(gameStatus);
		
		
		StackPane leftPane = new StackPane();  // this pane will be for player 1
		Rectangle leftRectangle = rectangleCreator(200, 600); // this rectangle will define the area
		leftPane.getChildren().add(leftRectangle);
		gamePane.setLeft(leftPane);
		
		StackPane rightPane  = new StackPane(); // this pane will be for player 2
		Rectangle rightRectangle = rectangleCreator(200, 600); // this rectangle will define the area
		rightPane.getChildren().add(rightRectangle);
		gamePane.setRight(rightPane);
		
		StackPane topPane  = new StackPane(); //The top stackpane will hold all the menu items
		Rectangle topRectangle = rectangleCreator(1000,100); // this rectangle will define the area
		topPane.getChildren().add(topRectangle);
		VBox menuRowsVBox = new VBox();
		menuRowsVBox .setSpacing(10);
		HBox menuRow1HBox = new HBox();
		ChoiceBox<String> backgroundChoices = new ChoiceBox<>();
		backgroundChoices.getItems().addAll("Forest Sunset", "Fractal 1", "Fractal 2",
				"Fractal 3", "Fractal 4", "Fractal 5", "Fractal 6", "White");
		//Set the default value
		backgroundChoices.setValue("White");
		Button backgroundSwitchButton = new Button("Switch Background");		
		backgroundSwitchButton.setOnAction(e -> actionChoice(backgroundChoices));
		menuRow1HBox.getChildren().addAll(backgroundChoices,backgroundSwitchButton);
		menuRowsVBox .getChildren().addAll(menuRow1HBox);
		topPane.getChildren().add(menuRowsVBox );
		gamePane.setTop(topPane);
		
		return gamePane;
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
	 * method to pull the actual ImageView from the selection and return it so 
	 * that the main stage can set it.
	 * @param backgroundChoices
	 * @return
	 */
	private void actionChoice(ChoiceBox<String> backgroundChoices) {
		String choice = backgroundChoices.getValue();
		String fileString;
		switch (choice) {
		case "Forest Sunset": fileString = "file:.\\resources\\forest_sunset.png";
			break;
		case "Fractal 1": fileString = "file:.\\resources\\fractal1.png";
			break;
		case "Fractal 2": fileString = "file:.\\resources\\fractal2.png";
			break;
		case "Fractal 3": fileString = "file:.\\resources\\fractal3.png";
			break;
		case "Fractal 4": fileString = "file:.\\resources\\fractal4.png";
			break;
		case "Fractal 5": fileString = "file:.\\resources\\fractal5.png";
			break;
		case "Fractal 6": fileString = "file:.\\resources\\fractal6.png";
			break;
		case "White": fileString = "file:.\\resources\\white.png";
			break;
		default: fileString = "file:.\\resources\\white.png";
	}
		Image backgroundImage = new Image(fileString);
		backgroundImageView.setImage(backgroundImage);
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
	private void checkBoard(Player player) {
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
			this.setOnMouseClicked(e -> handleMouseClick());
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
		}		

		/**
		 * set the text in the cell to X
		 */
		private void drawX() {
			image = new Image("file:.\\resources\\x.png", 150, 150, true, true);
			imageView.setImage(image);
		}
		
		/**
		 * set the text in the cell to O
		 */
		private void drawO() {
			image = new Image("file:.\\resources\\o.png", 150, 150, true, true);
			imageView.setImage(image);
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
				checkNextPlayer();
			}
		}

	}


}
