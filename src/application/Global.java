package application;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Global {

	private ImageView player1AvatarImageView = new ImageView(); // link to the avatar ImageView object for player1
	private ImageView player2AvatarImageView = new ImageView(); // link to the avatar ImageView object for player2
	private HBox avatarSelectionMenu; // creating this as a global variable so i can change the visibility
	
	/**
	 * @return the player1AvatarImageView
	 */
	public ImageView getPlayer1AvatarImageView() {
		return player1AvatarImageView;
	}
	
	/**
	 * @param player1AvatarImageView the player1AvatarImageView to set
	 */
	public void setPlayer1AvatarImageView(ImageView player1AvatarImageView) {
		this.player1AvatarImageView = player1AvatarImageView;
	}
	
	/**
	 * @return the player2AvatarImageView
	 */
	public ImageView getPlayer2AvatarImageView() {
		return player2AvatarImageView;
	}
	
	/**
	 * @param player2AvatarImageView the player2AvatarImageView to set
	 */
	public void setPlayer2AvatarImageView(ImageView player2AvatarImageView) {
		this.player2AvatarImageView = player2AvatarImageView;
	}
	
	/**
	 * @return the avatarSelectionMenu
	 */
	public HBox getAvatarSelectionMenu() {
		return avatarSelectionMenu;
	}

	/**
	 * @param avatarSelectionMenu the avatarSelectionMenu to set
	 */
	public void setAvatarSelectionMenu(HBox avatarSelectionMenu) {
		this.avatarSelectionMenu = avatarSelectionMenu;
	}
	
}
