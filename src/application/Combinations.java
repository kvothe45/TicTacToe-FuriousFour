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
	
	public int[] consecutiveCellsFilled() {
		int[] coordiantes = {3,3};
		if (!(cells[0].getToken().equals(" ") && cells[1].getToken().equals(" ") && cells[2].getToken().equals(" "))) {
			if (cells[0].getToken().equals(cells[1].getToken()) || cells[1].getToken().equals(cells[2].getToken())) {
				if (cells[2].getToken().equals(" ")) {
					coordiantes[0] = cells[2].getxCoordinate();
					coordiantes[1] = cells[2].getyCoordinate();
				}
				else if (cells[0].getToken().equals(" ")) {
					coordiantes[0] = cells[0].getxCoordinate();
					coordiantes[1] = cells[0].getyCoordinate();
				}
			}
		}
		return coordiantes;
	}
	

}
