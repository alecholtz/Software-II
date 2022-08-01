package Interfaces;

import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * An interface for translating text from one language to another using a resource bundle.
 * @author AAlec Holtzapfel
 */
public interface TranslateText {
    //String returning function to change the text of a field
    String translateText(ResourceBundle rb, String originalText);
}
