/**Guess_Word is a hang-man-like game in which players can select a difficult and start a new game, guess letters until the word from the WordList file is completed or the player runs out of guess, save and load previous games, and all in one window and GUI basis.
 * @author Dawson C. Branch
 * @version 1.0.0
 * @since 1.0.0
 * 
 * Possible Update: Create a score system in which right guesses net points, 
 * wins net completion bonus points, perfect wins (no missed guesses) net 
 * more bonus points, incorrect guesses cause deductions in score, losses 
 * cause more deductions, score gain multipliers depending on difficulty, 
 * score gets saved along with other data, words are no longer revealed 
 * after quitting, mystery word is revealed upon (causing a loss), and with 
 * graphical changes in GUI made to accommodate these changes
 */
package Guess_Word;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**Main Guess_Word class that launches the application via loading the FXMLDocument
 * @author Dawson C. Branch
 * @version 1.0.0
 * @since 1.0.0
 */
public class Guess_Word extends Application {
    /**start javadoc test
     * 
     * @param stage Something Something
     * @throws Exception Something more
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}