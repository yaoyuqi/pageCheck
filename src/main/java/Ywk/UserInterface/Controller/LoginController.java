package Ywk.UserInterface.Controller;

import Ywk.Api.HltApi;
import Ywk.Data.IdentityWrapper;
import Ywk.Data.KeywordGenerator;
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
//    @FXML
//    private TextField identity;

    public void setApp(MainApp app) {
        this.app = app;
    }

    @FXML
    private void login() {
        String user = account.getText();
        String password = passwordField.getText();

        user = "lymczs";
        password = "qwe123";
        if (user.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("账户或密码不能为空");
            alert.showAndWait();
        } else {
            createDialog();
            HltApi api = HltApi.getInstance();
            api.login(user, password, this);
        }

    }

    public void loginResult(boolean isOk) {
        try {
            Platform.runLater(() -> {
                if (!isOk) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("登录失败");
                    alert.setHeaderText(null);
                    alert.show();
                } else {
                    HltApi api = HltApi.getInstance();
                    api.identity(this);
                    api.words(this);
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


    }

    public void apiInitFinished() {
        if (IdentityWrapper.getInstance().isInit()
                && KeywordGenerator.getInstance().isInit()
        ) {
            try {
                Platform.runLater(() -> {
                    dialogStage.close();
                    app.gotoMain();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void vitalError() {
        HomeController.showAlert(Alert.AlertType.ERROR, "初始化失败，请重启软件");
    }
}
