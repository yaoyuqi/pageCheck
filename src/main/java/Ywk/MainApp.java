package Ywk;

import Ywk.Api.HltApi;
import Ywk.Client.HttpClientWrapper;
import Ywk.Data.KeywordGenerator;
import Ywk.UserInterface.Controller.HomeController;
import Ywk.UserInterface.Controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        client = HttpClientWrapper.getClient();

        HltApi.getInstance(client);

        this.primaryStage = primaryStage;


        primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/icons/logo128.png")));

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/Login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setApp(this);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("91海量发万词霸屏");
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

            //TODO Test
//            setTestData();

            controller.setApp(this);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setTestData() {
//        String[] marks = {"Ydvqmc",
//                "2goenm",
//                "Ngrqxz",
//                "K6aykb",
//                "Tvwbby",
//                "2jtjhm",
//                "3aqf9i",
//                "Eka49r",
//                "R5y4gn",
//                "Olmdc5",
//                "A7vc5e",
//                "Xm9rge",
//                "8nr8uq",
//                "R6jerq",
//                "Tknu3w",
//                "Omtcby",
//                "Npkpdm",
//                "Ygwd9a",
//                "Am1nxu",
//                "U3s4ai",
//                "Ergell",
//                "0ylmly",
//                "Olhkxu",
//                "Yuvpqa",
//                "3okzm6",
//                "Bufjiy",
//                "Jlgsxc"};
//
//        IdentityWrapper identityWrapper = IdentityWrapper.getInstance();
//
//        identityWrapper.initList(Arrays.stream(marks).map(item -> {
//            IdentityData.DataBean bean = new IdentityData.DataBean();
//            bean.setIdentity(item);
//            bean.setName("产品" + item);
//            return bean;
//        }).collect(Collectors.toList()));

        String[] keywords = {
                "抽水机租赁",
                "印刷杂志书刊厂家",
                "红色水泥瓦机",
                "速冻油条培训",
                "浓缩猪骨汤调料",
                "咬合调整培训",
                "魔芋瑰肉",
                "刮痧拔罐培训",
                "低频隔震台",
                "百叶玻璃定制",
                "儿童书刊印刷",
                "支护网编织机",
                "矿用螺旋洗砂机",
                "商品黑天鹅价格",
                "纳米齿雕技术培训",
                "挤压式3LPE防腐管道",
                "学生无风险创业",
                "手冲咖啡培训",
                "拖板运输半挂车",
                "一站式护理皮肤",
                "定制开发WAP网站",
                "艾叶打粉机",
                "洗牙美牙培训",
                "DFPB穿线管",
                "甘蔗切断机",
                "模特培训通过率",
                "平衡狗粮厂家",
                "蓝孔雀标本",
                "办公家具拆解",
                "期刊印刷厂"
        };

        KeywordGenerator generator = KeywordGenerator.getInstance();

        generator.setWords(new String[]{""}, keywords, new String[]{""});


    }
}
