package Ywk.UserInterface.Controller;

import Ywk.MainApp;
import javafx.fxml.FXML;

public class LoginController {

    private MainApp app;

    public void setApp(MainApp app) {
        this.app = app;
    }

    @FXML
    private void login() {
        app.gotoMain();
    }

    private void initialize() {

    }
}
