package application;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Global {

	private String whoseTurn = "Player 1"; //Indicate which player has a turn, initially it is the X player
	private Label gameStatus = new Label("Player 1's turn to play"); //Create and initialize a status label
	private Label numberOfDrawsLabel = new Label(); // displays the number of draws
	private boolean isPlayable = true; //Create a boolean to see if we can still play the game or it's over
	private ImageView backgroundImageView = new ImageView(); // holds the selected background
	private int numberOfMovesLeft = 9; // keeps track of the number of moves left for the random generator
	private int numberOfDraws = 0; // keeps track of the number of draws
	private HBox difficultyLevelRadioBox; // sets this box globally so the visibility can be changed depending on whether it's an all human game or not
	private HBox numberOfPlayersRadioBox = new HBox();  // access to the number of players radio button box to determine the setting
	private ImageView playersAvatarImageView[] = new ImageView[2]; // this is the ImageViews for the player avatars placed here to allow the image to be changed programattically
	private HBox[] avatarSelectionBoxs = new HBox[2]; // this is the avatar choice box set here to allow for altering the visibility
	private ArrayList<Combinations> winningCombinations = new ArrayList<>(); // this is the list of all possible winning combinations
	
	public Global() {
		for (int i = 0; i < 2; i++) {
			playersAvatarImageView[i] = new ImageView();
			avatarSelectionBoxs[i] = new HBox(); 
		}
	}
	
	/**
	 * @return the playersAvatarImageView
	 */
	public ImageView[] getPlayersAvatarImageView() {
		return playersAvatarImageView;
	}

	/**
	 * @param playersAvatarImageView the playersAvatarImageView to set
	 */
	public void setPlayersAvatarImageView(ImageView[] playersAvatarImageView) {
		this.playersAvatarImageView = playersAvatarImageView;
	}

	/**
	 * @return the avatarSelectionBoxs
	 */
	public HBox[] getAvatarSelectionBoxs() {
		return avatarSelectionBoxs;
	}

	/**
	 * @param avatarSelectionBoxs the avatarSelectionBoxs to set
	 */
	public void setAvatarSelectionBoxs(HBox[] avatarSelectionBoxs) {
		this.avatarSelectionBoxs = avatarSelectionBoxs;
	}

	/**
	 * @return the whoseTurn
	 */
	public String getWhoseTurn() {
		return whoseTurn;
	}

	/**
	 * @param whoseTurn the whoseTurn to set
	 */
	public void setWhoseTurn(String whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	/**
	 * @return the gameStatus
	 */
	public Label getGameStatus() {
		return gameStatus;
	}

	/**
	 * @param gameStatus the gameStatus to set
	 */
	public void setGameStatus(Label gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	/**
	 * @return the numberOfDrawsLabel
	 */
	public Label getNumberOfDrawsLabel() {
		return numberOfDrawsLabel;
	}

	/**
	 * @param numberOfDrawsLabel the numberOfDrawsLabel to set
	 */
	public void setNumberOfDrawsLabel(Label numberOfDrawsLabel) {
		this.numberOfDrawsLabel = numberOfDrawsLabel;
	}

	/**
	 * @return the isPlayable
	 */
	public boolean isPlayable() {
		return isPlayable;
	}

	/**
	 * @param isPlayable the isPlayable to set
	 */
	public void setPlayable(boolean isPlayable) {
		this.isPlayable = isPlayable;
	}

	/**
	 * @return the backgroundImageView
	 */
	public ImageView getBackgroundImageView() {
		return backgroundImageView;
	}

	/**
	 * @param backgroundImageView the backgroundImageView to set
	 */
	public void setBackgroundImageView(ImageView backgroundImageView) {
		this.backgroundImageView = backgroundImageView;
	}

	/**
	 * @return the numberOfMovesLeft
	 */
	public int getNumberOfMovesLeft() {
		return numberOfMovesLeft;
	}

	/**
	 * @param numberOfMovesLeft the numberOfMovesLeft to set
	 */
	public void setNumberOfMovesLeft(int numberOfMovesLeft) {
		this.numberOfMovesLeft = numberOfMovesLeft;
	}
	
	/**
	 * @return the numberOfDraws
	 */
	public int getNumberOfDraws() {
		return numberOfDraws;
	}

	/**
	 * @param numberOfDraws the numberOfDraws to set
	 */
	public void setNumberOfDraws(int numberOfDraws) {
		this.numberOfDraws = numberOfDraws;
		this.numberOfDrawsLabel.setText("Number of Draws: " + String.valueOf(numberOfDraws));
	}

	/**
	 * @return the difficultyLevelRadioBox
	 */
	public HBox getDifficultyLevelRadioBox() {
		return difficultyLevelRadioBox;
	}

	/**
	 * @param difficultyLevelRadioBox the difficultyLevelRadioBox to set
	 */
	public void setDifficultyLevelRadioBox(HBox difficultyLevelRadioBox) {
		this.difficultyLevelRadioBox = difficultyLevelRadioBox;
	}

	/**
	 * @return the numberOfPlayersRadioBox
	 */
	public HBox getNumberOfPlayersRadioBox() {
		return numberOfPlayersRadioBox;
	}

	/**
	 * @param numberOfPlayersRadioBox the numberOfPlayersRadioBox to set
	 */
	public void setNumberOfPlayersRadioBox(HBox numberOfPlayersRadioBox) {
		this.numberOfPlayersRadioBox = numberOfPlayersRadioBox;
	}
	
	/**
	 * @return the winningCombinations
	 */
	public ArrayList<Combinations> getWinningCombinations() {
		return winningCombinations;
	}

	/**
	 * @param winningCombinations the winningCombinations to set
	 */
	public void setWinningCombinations(ArrayList<Combinations> winningCombinations) {
		this.winningCombinations = winningCombinations;
	}
	
}
