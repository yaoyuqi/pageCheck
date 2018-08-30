package Ywk.UserInterface.Controller;

import Ywk.Api.HltApi;
import Ywk.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    private MainApp app;
    private Stage dialogStage;
    @FXML
    private TextField account;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField identity;

    public void setApp(MainApp app) {
        this.app = app;
    }

    @FXML
    private void login() {
//        String username = account.getText();
//        String password = passwordField.getText();

//        if (username.isEmpty() || password.isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("请输入账户和密码");
//            alert.showAndWait();
//        } else {
//            createDialog();
//            HltApi api = HltApi.getInstance();
//            api.login(username, password, this);
//
//
////            app.gotoMain();
//        }

        String identifier = identity.getText();
        if (identifier.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("查询码不能为空");
            alert.showAndWait();
        } else {
            createDialog();
            HltApi api = HltApi.getInstance();
            api.login(identifier, this);
//            app.gotoMain();
        }

    }

    public void loginResult(boolean isOk) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dialogStage.close();
                    if (isOk) {
                        app.gotoMain();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("登录失败");
                        alert.setHeaderText(null);
                        alert.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createDialog() {
        dialogStage = new Stage(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(app.getPrimaryStage());

        //设定位置
        dialogStage.setResizable(false);
        BorderPane pane = new BorderPane();


        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
//        indicator.getLayoutY();
        Scene scene = new Scene(pane, 100, 100, Color.TRANSPARENT);
//        pane.setLayoutX(200);
//        pane.setLayoutY(200);

        Label label = new Label();
        label.setText("正在处理中");

        VBox parent = new VBox();
        parent.setSpacing(5);
        parent.setAlignment(Pos.CENTER);
        parent.setMinSize(100, 100);
        parent.getChildren().add(indicator);
        parent.getChildren().add(label);

        pane.setTop(parent);

        dialogStage.setScene(scene);

        dialogStage.show();
    }

    @FXML
    private void initialize() {
//        account.setText("ywk7YRUGBrl");
//        passwordField.setText("123456");

//        identity.setText("ie8qnb");
    }
}
