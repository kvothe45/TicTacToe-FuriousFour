package application;

import java.awt.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class PlayerPane extends Player {

	
	/**
	 * Constructor from Player superclass
	 * @param token
	 * @param isComputer
	 */
	public PlayerPane(Global global, String token, boolean isComputer) {
		super(global, token, isComputer);
	}

	
	private VBox sidePaneBox; // this is the side pane box for the player
	private Image avatarImage; // This is the avatar selected for the given plaer
	//private ImageView avatarImageView;// = new ImageView(); // this is the Imageview which will be displayed in the pane
	private int humanWins = 0; // number of human wins initially set to 0
	private int humanlosses = 0; // number of human losses initially set to 0
	private int computerWins = 0; // number of computer wins initially set to 0
	private int computerLosses = 0;  // number of computer losses initially set to 0
	private Label humanWinsLbl = new Label(); // display label human wins
	private Label humanLossesLbl = new Label(); // display label human losses
	private Label computerWinsLbl = new Label(); // display label computer wins
	private Label computerLossesLbl = new Label(); // display label computer losses
	private Label overallWinsLbl = new Label(); // display label overall wins
	private Label overallLossesLbl = new Label(); // display label overall losses
	private HBox avatarSelectionMenu; // creating this as a global variable so i can change the visibility
	
	
	/**
	 * Override the parent classes setComputer method to change the visibility
	 * of the avatar image selection and to change the avatar image to whatever
	 * the current hardness level dictates
	 */
	@Override
	public void setComputer(boolean isComputer) {
		super.setComputer(isComputer);
		if (super.isComputer()) {
			avatarSelectionMenu.setVisible(false);
			setComputerAvatarImage(super.getHardnessLevel());
		} else {
			avatarSelectionMenu.setVisible(true);
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
		updateAvatarImageView(avatarImage);
	}

	public VBox createSidePane() {
	
		StackPane avatarPane = new StackPane(); // pane created to center the avatar
		String fileString; 
		if (super.getToken().equals("X")) 
			fileString ="file:.\\resources\\player_avatars\\vampire_smiley.png"; 
		else 
			fileString = "file:.\\resources\\player_avatars\\ghost_skull.png"; 
		avatarImage = new Image(fileString, 180, 180, true, true);
		updateAvatarImageView(avatarImage);
		//avatarImageView.setImage(avatarImage);
		if (super.getToken().equals("X")) 
			avatarPane.getChildren().add(super.global.getPlayer1AvatarImageView()); 
		else 
			avatarPane.getChildren().add(super.global.getPlayer2AvatarImageView());
		//avatarPane.getChildren().add(avatarImageView);
		avatarSelectionMenu = createAvatarChoiceBox(); // the selection for the avatar image
		Label playerLbl = new Label();
		if (super.getToken().equals("X"))
			playerLbl.setText("Player 1");
		else 
			playerLbl.setText("Player 2");
		playerLbl.setFont(Font.font(24));
		StackPane labelPane = new StackPane();
		labelPane.getChildren().add(playerLbl);
		labelPane.setPadding(new Insets(5,0,5,0));
		updateHumanAndOverallWins(humanWins);
		updateHumanAndOverallLosses(humanlosses);
		updateComputerAndOverallWins(computerWins);
		updateComputerAndOverallLosses(computerLosses);
		humanWinsLbl.setPadding(new Insets(5,0,5,5));
		humanWinsLbl.setFont(Font.font(12));
		humanLossesLbl.setPadding(new Insets(5,0,5,5));
		humanLossesLbl.setFont(Font.font(12));
		computerWinsLbl.setPadding(new Insets(5,0,5,5));
		computerWinsLbl.setFont(Font.font(12));
		computerLossesLbl.setPadding(new Insets(5,0,5,5));
		computerLossesLbl.setFont(Font.font(12));
		overallWinsLbl.setPadding(new Insets(5,0,5,5));
		overallWinsLbl.setFont(Font.font(12));
		overallLossesLbl.setPadding(new Insets(5,0,5,5));
		overallLossesLbl.setFont(Font.font(12));
		
		VBox sidePaneBox = new VBox();
		sidePaneBox.setAlignment(Pos.CENTER);
		sidePaneBox.getChildren().addAll(avatarPane, labelPane, avatarSelectionMenu,
				humanWinsLbl, humanLossesLbl, computerWinsLbl, computerLossesLbl, 
				overallWinsLbl, overallLossesLbl);
				
		
		this.sidePaneBox = sidePaneBox;
		return sidePaneBox;
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
		if (super.getToken().equals("X"))
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
				if (super.getToken().equals("X"))
					fileString = "file:.\\resources\\player_avatars\\vampire_smiley.png";
				else 
					fileString = "file:.\\resources\\player_avatars\\ghose_skull.png";
				
		}

		avatarImage = new Image(fileString, 180, 180, true, true);
		updateAvatarImageView(avatarImage);
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
	public int getHumanWins() {
		return humanWins;
	}

	/**
	 * sets the human wins field as well as update the display text
	 * for human wins and overall wins
	 * @param humanWins the humanWins to set
	 */
	public void setHumanWins(int humanWins) {
		this.humanWins = humanWins;
		updateHumanAndOverallWins(humanWins);
	}
	
	/**
	 * updates the human and overall wins Text
	 * @param humanWins
	 */
	private void updateHumanAndOverallWins(int humanWins) {
		humanWinsLbl.setText("Human Wins: " + String.valueOf(humanWins));
		String overallWinsString = String.valueOf(humanWins + computerWins);
		overallWinsLbl.setText("Overall Wins: " + overallWinsString);
	}

	/**
	 * @return the humanlosses
	 */
	public int getHumanlosses() {
		return humanlosses;
	}

	/**
	 * sets the human losses field as well as update the display text
	 * for human losses and overall losses
	 * @param humanlosses the humanlosses to set
	 */
	public void setHumanlosses(int humanlosses) {
		this.humanlosses = humanlosses;
		updateHumanAndOverallLosses(humanlosses);
	}
	
	/**
	 * updates the human and overall losses Text
	 * @param humanLosses
	 */
	private void updateHumanAndOverallLosses(int humanLosses) {
		humanLossesLbl.setText("Human Losses :" + String.valueOf(humanlosses));
		String overallLossesString = String.valueOf(humanlosses + computerLosses);
		overallLossesLbl.setText("Overall Losses: " + overallLossesString );
	}

	/**
	 * @return the computerWins
	 */
	public int getComputerWins() {
		return computerWins;
	}

	/**
	 * sets the computer wins field as well as update the display text
	 * for computer wins and overall wins
	 * @param computerWins the computerWins to set
	 */
	public void setComputerWins(int computerWins) {
		this.computerWins = computerWins;
		updateComputerAndOverallWins(computerWins);		
	}
	
	/**
	 * updates the computer and overall wins Text
	 * @param computerWins
	 */
	private void updateComputerAndOverallWins(int computerWins) {
		computerWinsLbl.setText("Computer Wins: " + String.valueOf(computerWins));
		String overallWinsString = String.valueOf(humanWins + computerWins);
		overallWinsLbl.setText("Overall Wins: " + overallWinsString);
	}

	/**
	 * @return the computerLosses
	 */
	public int getComputerLosses() {
		return computerLosses;
	}

	/**
	 * sets the computer losses field as well as update the display text
	 * for computer losses and overall losses
	 * @param computerLosses the computerLosses to set
	 */
	public void setComputerLosses(int computerLosses) {
		this.computerLosses = computerLosses;
		updateComputerAndOverallLosses(computerLosses);		
	}
	
	/**
	 * updates teh computer and overall losses Text
	 * @param computerLosses
	 */
	private void updateComputerAndOverallLosses(int computerLosses) {
		computerLossesLbl.setText("Computer Losses: " + String.valueOf(computerLosses));
		String overallLossesString = String.valueOf(humanlosses + computerLosses);
		overallLossesLbl.setText("Overall Losses: " + overallLossesString );
	}
	
	private void updateAvatarImageView(Image avatarImage) {
		if (super.getToken().equals("X")) 
			super.global.getPlayer1AvatarImageView().setImage(avatarImage); 
		else 
			super.global.getPlayer2AvatarImageView().setImage(avatarImage);
	}

	
}
