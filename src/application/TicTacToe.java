package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToe extends Application {
	//Indicate which player has a turn, initially it is the X player
	private char whoseTurn = 'X';
	
	// Create and initialize cell
	private Cell[][] cells = new Cell[3][3];
	
	//Create and initialize a status label
	private Label lblStatus = new Label("X's turn to play");
	
	//Create a boolean to see if we can still play the game or it's over
	private boolean isPlayable = true;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws Exception {
		// Pane to hold cell
		GridPane pane = new GridPane();
		for (int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				pane.add(cells[x][y] = new Cell(), y, x);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		lblStatus.setFont(Font.font(24));
		borderPane.setBottom(lblStatus);
		
		//Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 600, 640);
		primaryStage.setTitle("TicTacToe");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	/**
	 * Determine if the cells are all occupied
	 * @return
	 */
	public boolean isFull() {
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 3; x++)
				if (cells[x][y].getToken() == ' ')
					return false;
		return true;
	}
	
	/**
	 * Determine if the player with the specified token wins
	 * @param args
	 */
	public boolean isWon(char token) {
		// check all rows
		for (int x = 0; x < 3; x++) 
			if (cells[x][0].getToken() == token
					&& cells[x][1].getToken() == token
					&& cells[x][2].getToken() == token)
				return true;
		// check all columns
		for (int y = 0; y < 3; y++)
			if (cells[0][y].getToken() == token
					&& cells[1][y].getToken() == token
					&& cells[2][y].getToken() == token)
				return true;
		// check diagonals
		if (cells[0][0].getToken() == token
				&& cells[1][1].getToken() == token
				&& cells[2][2].getToken() == token)
			return true;
		if (cells[2][0].getToken() == token
				&& cells[1][1].getToken() == token
				&& cells[0][2].getToken() == token)
			return true;
		return false;
		
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
		
		private Text text = new Text();
		private char token = ' ';
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
			//text.setFont(Font.font(72));
			setAlignment(Pos.CENTER);
			imageView = new ImageView(image);
			//getChildren().addAll(border, text);
			getChildren().addAll(border, imageView);
		}
		
		/**
		 * return token value
		 * @return
		 */
		public char getToken() {
			return token;
		}
		
		/**
		 * sets the token value in the cell
		 * @param token
		 */
		public void setToken(char token) {
			this.token = token;
			if (token == 'X') {
				drawX();
			}
			else if (token == 'O') {
				drawO();
			}
		}		

		/**
		 * set the text in the cell to X
		 */
		private void drawX() {
			image = new Image("file:.\\resources\\x.gif", 150, 150, true, true);
			imageView.setImage(image);
			//text.setText("X");
		}
		
		/**
		 * set the text in the cell to O
		 */
		private void drawO() {
			image = new Image("file:.\\resources\\o.gif", 150, 150, true, true);
			imageView.setImage(image);
			//text.setText("O");
		}
		
		
		/**
		 * Handle mouse click events
		 */
		private void handleMouseClick() {
			//If the cell is empty and the game is not over
			if (token == ' ' && whoseTurn != ' ' && isPlayable) {
				setToken(whoseTurn); // set token in the cell
			}
			// Check game status
			if (isWon(whoseTurn)) {
				lblStatus.setText(whoseTurn + " won!  The game is over.");
				whoseTurn = ' ';
				isPlayable = false;
			}
			else if (isFull()) {
				lblStatus.setText("Draw! The game is over.");
				isPlayable = false;
			}
			else if (isPlayable){
				// Change the turn
				whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
				lblStatus .setText(whoseTurn + "'s turn");
			}
		}

	}


}
