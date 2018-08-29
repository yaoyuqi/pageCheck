package Ywk;

import Ywk.Api.HltApi;
import Ywk.UserInterface.Controller.HomeController;
import Ywk.UserInterface.Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;

import java.io.IOException;

public class MainApp extends Application {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private Stage primaryStage;

    private OkHttpClient client;

    public MainApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        client = (new OkHttpClient.Builder()).build();

        HltApi.getInstance(client);

        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/Login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setApp(this);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("海量推万词霸屏");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void gotoMain() {
        try {
//            String identity = "xcWj2";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Main.fxml"));
            AnchorPane pane = loader.load();

            //全屏
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primaryStage.setX((bounds.getMaxX() - SCREEN_WIDTH) / 2);
            primaryStage.setY((bounds.getMaxY() - SCREEN_HEIGHT) / 2);
//            primaryStage.setWidth(bounds.getWidth());
//            primaryStage.setHeight(bounds.getHeight());

            Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
            HomeController controller = loader.getController();
            controller.setClient(client);
            controller.setApp(this);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
