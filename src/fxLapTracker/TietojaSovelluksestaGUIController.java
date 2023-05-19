package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
//import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * Näyttää käyttäjälle tietoja sovelluksesta.
 * @author Matti Savolainen - savomaaa@student.jyu.fi
 * @version 16.2.2023
 *
 */
public class TietojaSovelluksestaGUIController implements ModalControllerInterface<String>, Initializable {

    @FXML
    private Button buttonOk;
    
    @FXML
    private void buttonOk() {
        ModalController.closeStage(buttonOk);
    }
    
    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //
    }

    @Override
    public void setDefault(String oletus) {
        //
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
}
