package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * Kysytään käyttäjältä, halutaanko sovellusta sulkea.
 * @author Matti Savolainen - savomaaa@student.jyu.fi
 * @version 16.2.2023
 *
 */
public class SuljeKyselyGUIController implements ModalControllerInterface<String>, Initializable {

        @FXML
        private Button buttonCancel;

        @FXML
        private Button buttonOk;

        @FXML
        void buttonCancel() {
            ModalController.closeStage(buttonCancel);
        }

        @FXML
        void buttonOk() {
            ModalController.closeStage(buttonOk);
            Platform.exit();
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
