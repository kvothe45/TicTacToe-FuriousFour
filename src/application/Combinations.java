package application;

import application.TicTacToe.Cell;

public class Combinations {
	
	private Cell[] cells;
	
	public Combinations(Global global, Cell... cells) {
		this.cells = cells;
	}
	
	public boolean isGameWon() {
		if (cells[0].getToken().equals(" "))
			return false;
		return cells[0].getToken().equals(cells[1].getToken()) && cells[0].getToken().equals(cells[2].getToken());
	}
	

}
