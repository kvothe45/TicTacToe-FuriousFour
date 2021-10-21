package application;


import java.util.Random;

import application.TicTacToe.Cell;

public class Player {
	
	private String token; // String to hold the token this player has
	private boolean isComputer; // boolean to say whether this player is a computer or not
	private String hardnessLevel; // if it is a computer, what level of hardness will he play at 
	private Cell[][] boardState; // holds the current state of the board for the computer to use if necessary
	private int numberOfMovesLeft = 9;
	
	public Player (String token, boolean isComputer) {
		this.token = token;
		this.isComputer = isComputer;
		this.hardnessLevel = "easy";
	}
	
	public Player (String token, boolean isComputer, String hardnessLevel) {
		this.token = token;
		this.isComputer = isComputer;
		this.hardnessLevel = hardnessLevel.toLowerCase();
	};

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
	 * @param boardState the boardState to set
	 */
	public void setBoardState(Cell[][] boardState) {
		this.boardState = boardState;
	}
	
	/**
	 * @param numberOfMovesLeft the numberOfMovesLeft to set
	 */
	public void setNumberOfMovesLeft(int numberOfMovesLeft) {
		this.numberOfMovesLeft = numberOfMovesLeft;
	}
	
	public int[] computerMove() {
		int[] choice = {0,0}; //x and y coordinates for the computer's move
		switch(hardnessLevel) {
		case "easy":
			return easyPlay();
		case "medium":
			break;
		case "hard":
			break;
		}
		
		return choice;
	}
	
	public int[] easyPlay() {
		int[] choice = {0,0};
		Cell[][] tempBoardCells = boardState;
		Random random = new Random();
		int computerMove = random.nextInt(numberOfMovesLeft);
		int count = 0;
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++) {
				if (boardState[x][y].getToken().equals(" ")){
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


}