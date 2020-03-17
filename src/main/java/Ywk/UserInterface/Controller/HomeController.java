package Ywk.UserInterface.Controller;

import Ywk.Data.Info;
import Ywk.Data.KeywordGenerator;
import Ywk.Data.SearchPlatform;
import Ywk.MainApp;
import Ywk.PageCheck.TaskManage;
import Ywk.PageCheck.TaskStatus;
import Ywk.PageCheck.UploadStatus;
import Ywk.UserInterface.Model.InfoModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeController {

//    private static final int MAX_SHOW = 1000;

    private MainApp app;

    @FXML
    private Label totalLabel;
    private SimpleIntegerProperty totalProperty = new SimpleIntegerProperty(0);

    @FXML
    private Label checkedLabel;
    private SimpleIntegerProperty checkedProperty = new SimpleIntegerProperty(0);

    @FXML
    private Label showedPcLabel;
    private SimpleIntegerProperty pcBingoProperty = new SimpleIntegerProperty(0);


    @FXML
    private Label showedMobileLabel;
    private SimpleIntegerProperty mobileBingoProperty = new SimpleIntegerProperty(0);


    @FXML
    private Label shenmaLabel;
    private SimpleIntegerProperty shenmaBingoProperty = new SimpleIntegerProperty(0);


    @FXML
    private Label toutiaoLabel;
    private SimpleIntegerProperty toutiaoBingoProperty = new SimpleIntegerProperty(0);


    @FXML
    private CheckBox checkPcCb;
    @FXML
    private CheckBox checkMobileCb;

    @FXML
    private CheckBox checkSMCb;


    @FXML
    private CheckBox checkTTCb;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startBtn;


    @FXML
    private Button resumeBtn;

    @FXML
    private Button stopBtn;

//    @FXML
//    private Label runnedLabel;
//
//    @FXML
//    private Label estimateLabel;
//
//    @FXML
//    private ProgressBar progressBar;
//
//    @FXML
//    private ListView<String> infoLv;

    @FXML
    private TableView<InfoModel> listPcTv;

    @FXML
    private TableView<InfoModel> listMobileTv;

    @FXML
    private TableView<InfoModel> listShenmaTv;

    @FXML
    private TableView<InfoModel> listToutiaoTv;


    private ObservableList<InfoModel> listPc = FXCollections.observableArrayList();
    private ObservableList<InfoModel> listMobile = FXCollections.observableArrayList();
    private ObservableList<InfoModel> listShenma = FXCollections.observableArrayList();
    private ObservableList<InfoModel> listToutiao = FXCollections.observableArrayList();

    @FXML
    private TableColumn<InfoModel, String> pcKeywordsCl;
    @FXML
    private TableColumn<InfoModel, String> pcProductCl;
    @FXML
    private TableColumn<InfoModel, String> pcPageCl;
    @FXML
    private TableColumn<InfoModel, String> pcLocCl;
    @FXML
    private TableColumn<InfoModel, String> pcCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> pcOpenCl;

    @FXML
    private TableColumn<InfoModel, String> mobileKeywordsCl;
    @FXML
    private TableColumn<InfoModel, String> mobileProductCl;
    @FXML
    private TableColumn<InfoModel, String> mobilePageCl;
    @FXML
    private TableColumn<InfoModel, String> mobileLocCl;
    @FXML
    private TableColumn<InfoModel, String> mobileCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> mobileOpenCl;


    @FXML
    private TableColumn<InfoModel, String> shenmaKeywordsCl;
    @FXML
    private TableColumn<InfoModel, String> shenmaProductCl;
    @FXML
    private TableColumn<InfoModel, String> shenmaPageCl;
    @FXML
    private TableColumn<InfoModel, String> shenmaLocCl;
    @FXML
    private TableColumn<InfoModel, String> shenmaCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> shenmaOpenCl;


    @FXML
    private TableColumn<InfoModel, String> toutiaoKeywordsCl;
    @FXML
    private TableColumn<InfoModel, String> toutiaoProductCl;
    @FXML
    private TableColumn<InfoModel, String> toutiaoPageCl;
    @FXML
    private TableColumn<InfoModel, String> toutiaoLocCl;
    @FXML
    private TableColumn<InfoModel, String> toutiaoCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> toutiaoOpenCl;

    @FXML
    private CheckBox autoUploadCb;

    @FXML
    private Button uploadBtn;

    @FXML
    private ChoiceBox<Integer> pageChoiceBox;

    @FXML
    private RadioButton chooseMainRb;

    @FXML
    private RadioButton choosePrefixMainRb;

    @FXML
    private RadioButton choosePrefixMainSuffixRb;

    @FXML
    private RadioButton chooseMainSuffixRb;

    @FXML
    private RadioButton chooseCustomRb;

    @FXML
    private Button selectTxtBtn;

    @FXML
    private TextField maxTf;

    private TaskManage task;


    private List<String> customList = new LinkedList<>();

    private List<SearchPlatform> selectedPlatforms = new ArrayList<>();

    public static void showAlert(Alert.AlertType type, String message) {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(() -> {
                    Alert alert = new Alert(type);
                    alert.setContentText(message);
                    alert.setHeaderText(null);
                    alert.show();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(type);
            alert.setContentText(message);
            alert.setHeaderText(null);
            alert.show();
        }


    }

    public void initTaskManager() {
        task = new TaskManage(this);
        this.updateTotal();
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void initFinished() {
        alertInitFinished();
    }

    @FXML
    private void initialize() {
        /*
        设定页面的默认项目
         */
        totalLabel.textProperty().bind(totalProperty.asString());
        checkedLabel.textProperty().bind(checkedProperty.asString());
        showedPcLabel.textProperty().bind(pcBingoProperty.asString());
        showedMobileLabel.textProperty().bind(mobileBingoProperty.asString());
        shenmaLabel.textProperty().bind(shenmaBingoProperty.asString());
        toutiaoLabel.textProperty().bind(toutiaoBingoProperty.asString());

        listPcTv.setItems(listPc);
        listMobileTv.setItems(listMobile);
        listShenmaTv.setItems(listShenma);
        listToutiaoTv.setItems(listToutiao);

        uploadBtn.setDisable(true);
        autoUploadCb.setSelected(true);

        speedSlider.setValue(50.0);
        pageDepthSelectSet();
        searchPlatformSelectSet();

        keywordsRadioSet();
        tableCellSet();

        initTaskManager();

    }

    private void tableCellSet() {
        pcKeywordsCl.setSortable(false);
        pcProductCl.setSortable(false);
        pcPageCl.setSortable(false);
        pcOpenCl.setSortable(false);
        pcCheckTimeCl.setSortable(false);
        pcLocCl.setSortable(false);
        pcCheckTimeCl.setMinWidth(80);
        pcKeywordsCl.setMinWidth(150);
        pcProductCl.setMinWidth(80);

        mobileKeywordsCl.setSortable(false);
        mobileProductCl.setSortable(false);
        mobilePageCl.setSortable(false);
        mobileLocCl.setSortable(false);
        mobileCheckTimeCl.setSortable(false);
        mobileOpenCl.setSortable(false);
        mobileCheckTimeCl.setMinWidth(80);
        mobileKeywordsCl.setMinWidth(150);
        mobileProductCl.setMinWidth(80);

        shenmaKeywordsCl.setSortable(false);
        shenmaProductCl.setSortable(false);
        shenmaPageCl.setSortable(false);
        shenmaLocCl.setSortable(false);
        shenmaCheckTimeCl.setSortable(false);
        shenmaOpenCl.setSortable(false);
        shenmaCheckTimeCl.setMinWidth(80);
        shenmaKeywordsCl.setMinWidth(150);
        shenmaProductCl.setMinWidth(80);


        toutiaoKeywordsCl.setSortable(false);
        toutiaoProductCl.setSortable(false);
        toutiaoPageCl.setSortable(false);
        toutiaoLocCl.setSortable(false);
        toutiaoCheckTimeCl.setSortable(false);
        toutiaoOpenCl.setSortable(false);
        toutiaoCheckTimeCl.setMinWidth(80);
        toutiaoKeywordsCl.setMinWidth(150);
        toutiaoProductCl.setMinWidth(80);

        Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>> keywordCallback =
                (param) -> param.getValue().keywordProperty();

        Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>> pageCallback =
                (param) -> param.getValue().pageProperty();

        Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>> locCallback =
                (param) -> param.getValue().locProperty();


        Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>> timeCallback =
                (param) -> param.getValue().timeProperty();

        Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>> productCallback =
                (param) -> param.getValue().productProperty();


        pcKeywordsCl.setCellValueFactory(keywordCallback);
        pcProductCl.setCellValueFactory(productCallback);
        pcPageCl.setCellValueFactory(pageCallback);
        pcLocCl.setCellValueFactory(locCallback);
        pcCheckTimeCl.setCellValueFactory(timeCallback);
        pcOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));
        pcOpenCl.setCellFactory(urlClickOpenCallback(listPc, SearchPlatform.BAIDU));


        mobileKeywordsCl.setCellValueFactory(keywordCallback);
        mobileProductCl.setCellValueFactory(productCallback);
        mobilePageCl.setCellValueFactory(pageCallback);
        mobileLocCl.setCellValueFactory(locCallback);
        mobileCheckTimeCl.setCellValueFactory(timeCallback);
        mobileOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));
        mobileOpenCl.setCellFactory(urlClickOpenCallback(listMobile, SearchPlatform.BAIDU_MOBILE));


        shenmaKeywordsCl.setCellValueFactory(keywordCallback);
        shenmaProductCl.setCellValueFactory(productCallback);
        shenmaPageCl.setCellValueFactory(pageCallback);
        shenmaLocCl.setCellValueFactory(locCallback);
        shenmaCheckTimeCl.setCellValueFactory(timeCallback);
        shenmaOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));
        shenmaOpenCl.setCellFactory(urlClickOpenCallback(listShenma, SearchPlatform.SHENMA));

        toutiaoKeywordsCl.setCellValueFactory(keywordCallback);
        toutiaoProductCl.setCellValueFactory(productCallback);
        toutiaoPageCl.setCellValueFactory(pageCallback);
        toutiaoLocCl.setCellValueFactory(locCallback);
        toutiaoCheckTimeCl.setCellValueFactory(timeCallback);
        toutiaoOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));
        toutiaoOpenCl.setCellFactory(urlClickOpenCallback(listToutiao, SearchPlatform.TOUTIAO));
    }

    private Callback<TableColumn<InfoModel, String>, TableCell<InfoModel, String>> urlClickOpenCallback(ObservableList<InfoModel> list, SearchPlatform platform) {
        return (TableColumn<InfoModel, String> infoModelStringTableColumn) -> {
            TableCell<InfoModel, String> cell = new TableCell<InfoModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }

            };

            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                System.out.println("double clicked!");
                TableCell<InfoModel, String> tableCell = (TableCell<InfoModel, String>) mouseEvent.getSource();
                InfoModel model = list.get(tableCell.getIndex());
                if (model != null) {
                    String url = platform.nextPageUrl(model.getKeyword(), model.getPage());
                    app.getHostServices().showDocument(url);
                }
            });
            return cell;
        };
    }

    private void searchPlatformSelectSet() {
        ChangeListener<Boolean> cbChangeListener = (observable, oldValue, newValue) -> {
            updatePlatFormSelect();
            updateTotal();
        };

        checkPcCb.selectedProperty().addListener(cbChangeListener);
        checkMobileCb.selectedProperty().addListener(cbChangeListener);
        checkSMCb.selectedProperty().addListener(cbChangeListener);
        checkTTCb.selectedProperty().addListener(cbChangeListener);
    }

    private void pageDepthSelectSet() {
        final ObservableList<Integer> choice = FXCollections.observableArrayList(1, 2, 3);
        pageChoiceBox.setItems(choice);
        pageChoiceBox.setValue(1);
        pageChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    task.setPageDepth(choice.get(newValue.intValue()));
                    updateTotal();
                });

    }

    private void keywordsRadioSet() {
        chooseMainRb.setUserData(KeywordGenerator.MixType.MAIN);
        choosePrefixMainRb.setUserData(KeywordGenerator.MixType.PREFIX_MAIN);
        choosePrefixMainSuffixRb.setUserData(KeywordGenerator.MixType.PREFIX_MAIN_SUFFIX);
        chooseMainSuffixRb.setUserData(KeywordGenerator.MixType.MAIN_SUFFIX);
        chooseCustomRb.setUserData(KeywordGenerator.MixType.CUSTOM);

        final ToggleGroup group = new ToggleGroup();


        chooseMainRb.setToggleGroup(group);
        choosePrefixMainRb.setToggleGroup(group);
        choosePrefixMainSuffixRb.setToggleGroup(group);
        chooseMainSuffixRb.setToggleGroup(group);
        chooseCustomRb.setToggleGroup(group);

        choosePrefixMainSuffixRb.setSelected(true);

        chooseMainRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task.setKeywordType(KeywordGenerator.MixType.MAIN);
                updateTotal();
            }
        });

        choosePrefixMainRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task.setKeywordType(KeywordGenerator.MixType.PREFIX_MAIN);
                updateTotal();
            }

        });

        choosePrefixMainSuffixRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task.setKeywordType(KeywordGenerator.MixType.PREFIX_MAIN_SUFFIX);
                updateTotal();
            }

        });

        chooseMainSuffixRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task.setKeywordType(KeywordGenerator.MixType.MAIN_SUFFIX);
                updateTotal();
            }

        });


        chooseCustomRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task.setKeywordType(KeywordGenerator.MixType.CUSTOM);
                selectTxtBtn.setVisible(true);
                updateTotal();
            } else {
                selectTxtBtn.setVisible(false);
                updateTotal();
            }
        });

    }

    @FXML
    private void handleNewStart() {
        task.newTask();
        listPc.clear();
        listMobile.clear();
        listShenma.clear();
        listToutiao.clear();

        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }

        task.setSpeed(runSpeed);

        task.setAutoUpdate(autoUploadCb.isSelected());

        task.setKeywordMax(Integer.parseInt(maxTf.getText()));

        //new设置， 放在子线程里更新不上
        task.start();

        Thread thread = new Thread(task);
//        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleStop() {
        task.stopAll();
        showAlert(Alert.AlertType.INFORMATION, "已停止 \n");
    }

    @FXML
    private void handleResume() {
        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }
        task.setSpeed(runSpeed);
        task.setKeywordMax(Integer.parseInt(maxTf.getText()));
        task.setAutoUpdate(autoUploadCb.isSelected());
        task.start();
        Thread thread = new Thread(task);
        thread.start();

    }

    /**
     * 更新总数量
     */
    public void updateTotal() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(() -> totalProperty.setValue(task.getTotal()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            totalProperty.setValue(task.getTotal());
        }

    }

    /**
     * 更新已检索数量
     *
     * @param ran
     */
    public void updateRanCnt(int ran) {
        try {
            Platform.runLater(() -> checkedProperty.setValue(ran));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新上词数
     */
    public void updateBingoCnt(int pc, int mobile, int shenma, int toutiao) {
        try {
            Platform.runLater(() -> {
                pcBingoProperty.setValue(pc);
                mobileBingoProperty.setValue(mobile);
                shenmaBingoProperty.setValue(shenma);
                toutiaoBingoProperty.setValue(toutiao);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpload() {
        task.uploadResult();
    }

    /**
     * 更新状态
     */
    public void updateTaskStatus() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(() -> updateStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            updateStatus();
        }

    }

    private void updateStatus() {
        System.out.println("current status is " + task.getTaskStatus());
        if (task.getTaskStatus() == TaskStatus.NEW) {
            updateNewStatus();
        } else if (task.getTaskStatus() == TaskStatus.RUNNING) {
            updateRunningStatus();
        } else if (task.getTaskStatus() == TaskStatus.PAUSE) {
            updatePauseStatus();
        } else if (task.getTaskStatus() == TaskStatus.FINISHED) {
            updateFinishedStatus();
        } else if (task.getUploadStatus() == UploadStatus.UPLOADING) {
            updateUploadingStatus();
        } else if (task.getUploadStatus() == UploadStatus.FAIL
                || task.getUploadStatus() == UploadStatus.SUCCESS) {
            updateUploadFinishedStatus();
            resumeBtn.setDisable(task.getTaskStatus() == TaskStatus.FINISHED); //继续按钮
        }
    }

    private void updateNewStatus() {
        startBtn.setDisable(false);//开始按钮 可用
        stopBtn.setDisable(true); //结束按钮 不可用
        resumeBtn.setDisable(true); //继续按钮 不可用

        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        checkSMCb.setDisable(false); //平台勾选 可用
        checkTTCb.setDisable(false); //平台勾选 可用

        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用

        maxTf.setDisable(false);
        chooseCustomRb.setDisable(false);
        chooseMainRb.setDisable(false);
        chooseMainSuffixRb.setDisable(false);
        choosePrefixMainSuffixRb.setDisable(false);
        choosePrefixMainRb.setDisable(false);

        listPc.clear();
        listMobile.clear();
        listShenma.clear();
        listToutiao.clear();
        task.newTask();
    }

    private void updateUploadFinishedStatus() {

        startBtn.setDisable(false);//开始按钮 可用
        stopBtn.setDisable(true); //结束按钮 不可用

        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用

        maxTf.setDisable(false);
        chooseCustomRb.setDisable(false);
        chooseMainRb.setDisable(false);
        chooseMainSuffixRb.setDisable(false);
        choosePrefixMainSuffixRb.setDisable(false);
        choosePrefixMainRb.setDisable(false);
        selectTxtBtn.setDisable(false);

    }

    private void updateRunningStatus() {
        startBtn.setDisable(true); //开始按钮 不可用
        stopBtn.setDisable(false); //结束按钮 可用
        resumeBtn.setDisable(true); //继续按钮 不可用
        checkPcCb.setDisable(true); //平台勾选 不可用
        checkMobileCb.setDisable(true); //平台勾选 不可用
        autoUploadCb.setDisable(true); //自动上传 不可用
        pageChoiceBox.setDisable(true); //检索深度不可用
        uploadBtn.setDisable(true); //上传按钮 不可用
        maxTf.setDisable(true);
        chooseCustomRb.setDisable(true);
        chooseMainRb.setDisable(true);
        chooseMainSuffixRb.setDisable(true);
        choosePrefixMainSuffixRb.setDisable(true);
        choosePrefixMainRb.setDisable(true);
        selectTxtBtn.setDisable(true);
    }

    private void updatePauseStatus() {
        startBtn.setDisable(false); //开始按钮 可用
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(false);  //继续按钮 可用
        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用

        maxTf.setDisable(false);
        chooseCustomRb.setDisable(false);
        chooseMainRb.setDisable(false);
        chooseMainSuffixRb.setDisable(false);
        choosePrefixMainSuffixRb.setDisable(false);
        choosePrefixMainRb.setDisable(false);
        selectTxtBtn.setDisable(false);
    }

    private void updateFinishedStatus() {
        startBtn.setDisable(false); //开始按钮 可用
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(true);  //继续按钮 不可用
        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用

        maxTf.setDisable(false);
        chooseCustomRb.setDisable(false);
        chooseMainRb.setDisable(false);
        chooseMainSuffixRb.setDisable(false);
        choosePrefixMainSuffixRb.setDisable(false);
        choosePrefixMainRb.setDisable(false);
        selectTxtBtn.setDisable(false);

    }

    private void updateUploadingStatus() {
        //全部不可用
        startBtn.setDisable(true); //开始按钮 可用
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(true);  //继续按钮 可用
        checkPcCb.setDisable(true); //平台勾选 可用
        checkMobileCb.setDisable(true); //平台勾选 可用
        autoUploadCb.setDisable(true); //自动上传 可用
        pageChoiceBox.setDisable(true); //检索深度 可用
        uploadBtn.setDisable(true); //上传按钮 可用

        maxTf.setDisable(true);
        chooseCustomRb.setDisable(true);
        chooseMainRb.setDisable(true);
        chooseMainSuffixRb.setDisable(true);
        choosePrefixMainSuffixRb.setDisable(true);
        choosePrefixMainRb.setDisable(true);
        selectTxtBtn.setDisable(true);

    }

    /**
     * 回调， 将爬取结果放回列表显示
     * 同时更新页面命中数
     *
     * @param info
     */
    public synchronized void addResult(Info info) {

        switch (info.getPlatform()) {
            case BAIDU:
                listPc.add(0, new InfoModel(info));
                break;
            case BAIDU_MOBILE:
                listMobile.add(0, new InfoModel(info));
                break;
            case SHENMA:
                listShenma.add(0, new InfoModel(info));
                break;
            case TOUTIAO:
                listToutiao.add(0, new InfoModel(info));
                break;
        }
    }

    /**
     * 显示网络异常alert
     */
    public void alertNetError() {
        showAlert(Alert.AlertType.ERROR, "网络错误，请检查网络并上后重试");
    }

    public void alertVital() {
        showAlert(Alert.AlertType.ERROR, "初始化失败，请重启软件");
    }

    public void alertInitFinished() {
        showAlert(Alert.AlertType.INFORMATION, "初始化完毕");
    }

    /**
     * 显示上传成功alert
     */
    public void uploadSuccess() {
        showAlert(Alert.AlertType.INFORMATION, "上传成功");
    }

    /**
     * 显示上传失败alert
     */
    public void alertUploadError() {
        showAlert(Alert.AlertType.ERROR, "结果上传出错，请稍后重试");


    }

    @FXML
    private void handleCustomKeywords() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择关键词");
        File file = fileChooser.showOpenDialog(app.getPrimaryStage());
        if (file != null) {
            readFile(file);
        }

    }

    private void readFile(File file) {
        Platform.runLater(() -> {
            try {

                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);

                String line;
                while ((line = br.readLine()) != null) {
                    customList.add(line.trim());
                }
                br.close();
                fileReader.close();
                if (!showCustomKeywordDialog(customList)) {
                    customList.clear();
                }
                task.setCustomKeywords(customList);
                updateTotal();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean showCustomKeywordDialog(List<String> list) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/KeywordsList.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("自定义关键词");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(app.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);

            KeywordsController keywordsController = loader.getController();
            keywordsController.setDialogStage(stage);
            keywordsController.setCustomList(list);
            stage.showAndWait();
            return keywordsController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updatePlatFormSelect() {
        selectedPlatforms.clear();
        if (checkPcCb.isSelected()) {
            selectedPlatforms.add(SearchPlatform.BAIDU);
        }
        if (checkMobileCb.isSelected()) {
            selectedPlatforms.add(SearchPlatform.BAIDU_MOBILE);
        }
        if (checkSMCb.isSelected()) {
            selectedPlatforms.add(SearchPlatform.SHENMA);
        }
        if (checkTTCb.isSelected()) {
            selectedPlatforms.add(SearchPlatform.TOUTIAO);
        }
    }

}
