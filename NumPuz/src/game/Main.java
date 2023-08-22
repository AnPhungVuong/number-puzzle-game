package game;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * 
 * This main class to start the Application
 * 
 * @author Phung An Vuong
 * @since java 15
 *
 */
public class Main extends Application {
	
	private final int width = 662;
	private final int high = 388;
	@Override
	public void start(Stage primaryStage) {
		try {
			// Set the application stage
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/game.png")));
			primaryStage.setTitle("Phung An Vuong's NumPuz"); // Set the stage title
			// Set a scene with a text in the stage
			GameController theController = new GameController();
			primaryStage.setScene(new Scene(theController.getBorderPane(), width, high));
			primaryStage.show(); // Display the stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
