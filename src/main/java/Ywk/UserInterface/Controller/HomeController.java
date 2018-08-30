package Ywk.UserInterface.Controller;

import Ywk.Api.HltApi;
import Ywk.Data.Info;
import Ywk.Data.Keyword;
import Ywk.MainApp;
import Ywk.PageCheck.BaiduCapture;
import Ywk.PageCheck.TaskManage;
import Ywk.UserInterface.Model.InfoModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import okhttp3.OkHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HomeController {

    private static final int MAX_SHOW = 1000;

    private MainApp app;

    @FXML
    private Label totalLabel;

    @FXML
    private Label checkedLabel;

    @FXML
    private Label showedPcLabel;

    @FXML
    private Label showedMobileLabel;

    @FXML
    private CheckBox checkPcCb;

    @FXML
    private CheckBox checkMobileCb;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startBtn;

    @FXML
    private Button resumeBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Label runnedLabel;

    @FXML
    private Label estimateLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ListView<String> infoLv;

    @FXML
    private TableView<InfoModel> listPcTv;

    @FXML
    private TableView<InfoModel> listMobileTv;


    private ObservableList<InfoModel> listPc = FXCollections.observableArrayList();
    private ObservableList<InfoModel> listMobile = FXCollections.observableArrayList();

    @FXML
    private TableColumn<InfoModel, String> pcKeywordsCl;
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
    private TableColumn<InfoModel, String> mobilePageCl;
    @FXML
    private TableColumn<InfoModel, String> mobileLocCl;
    @FXML
    private TableColumn<InfoModel, String> mobileCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> mobileOpenCl;

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


    private TaskManage manage;

    private OkHttpClient client;

    private List<String> customList = new LinkedList<>();


    public HomeController() {
        manage = new TaskManage();


    }

    public void setApp(MainApp app) {
        this.app = app;
        manage = new TaskManage();
        manage.setController(this);
        manage.setTaskStatus(TaskManage.TASK_STATUS_NEW);
        manage.prepareKeyword();

//        for (int i = 0; i < 100000; i++) {
//            Info info = new Info();
//            info.setType(1);
//            info.setLoc(new String[]{"1", "2", "3"});
//            info.setTime("2018-12-1 1:1:1");
//            info.setKeyword("肯尼亚CICC" + i);
//            listPc.add(new InfoModel(info));
//        }
//
//        for (int i = 0; i < 100000; i++) {
//            Info info = new Info();
//            info.setType(2);
//            info.setLoc(new String[]{"1", "2", "3"});
//            info.setTime("2018-12-1 1:1:1");
//            info.setKeyword("肯尼亚CICC" + i);
//            listMobile.add(new InfoModel(info));
//        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public void setIdentity(String identity) {
        manage.setIdentity(identity);
//        manage.setIdentity("xcWj2");
        System.out.println("identity=" + identity);
    }

    @FXML
    private void initialize() {
        listPcTv.setItems(listPc);
        listMobileTv.setItems(listMobile);

        uploadBtn.setDisable(true);
        autoUploadCb.setSelected(true);

        speedSlider.setValue(50.0);


        final ObservableList<Integer> choice = FXCollections.observableArrayList(1, 2, 3);
        pageChoiceBox.setItems(choice);
        pageChoiceBox.setValue(1);
        pageChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    manage.setPageDepth(choice.get(newValue.intValue()));
                    updateTotal();
                });


        checkPcCb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updatePlatFormSelect();
            updateTotal();
        });

        checkMobileCb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updatePlatFormSelect();
            updateTotal();
        });

        chooseMainRb.setUserData(Keyword.MixType.MAIN);
        choosePrefixMainRb.setUserData(Keyword.MixType.PREFIX_MAIN);
        choosePrefixMainSuffixRb.setUserData(Keyword.MixType.PREFIX_MAIN_SUFFIX);
        chooseMainSuffixRb.setUserData(Keyword.MixType.MAIN_SUFFIX);
        chooseCustomRb.setUserData(Keyword.MixType.CUSTOM);

        final ToggleGroup group = new ToggleGroup();


        chooseMainRb.setToggleGroup(group);
        choosePrefixMainRb.setToggleGroup(group);
        choosePrefixMainSuffixRb.setToggleGroup(group);
        chooseMainSuffixRb.setToggleGroup(group);
        chooseCustomRb.setToggleGroup(group);

        choosePrefixMainSuffixRb.setSelected(true);

        chooseMainRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                manage.setKeywordType(Keyword.MixType.MAIN);
                updateTotal();
            }
        });

        choosePrefixMainRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                manage.setKeywordType(Keyword.MixType.PREFIX_MAIN);
                updateTotal();
            }

        });

        choosePrefixMainSuffixRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                manage.setKeywordType(Keyword.MixType.PREFIX_MAIN_SUFFIX);
                updateTotal();
            }

        });

        chooseMainSuffixRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                manage.setKeywordType(Keyword.MixType.MAIN_SUFFIX);
                updateTotal();
            }

        });


        chooseCustomRb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                manage.setKeywordType(Keyword.MixType.CUSTOM);
                selectTxtBtn.setVisible(true);
                updateTotal();
            } else {
                selectTxtBtn.setVisible(false);
                updateTotal();
            }
        });

        HltApi api = HltApi.getInstance();
        api.identity(this);

        pcKeywordsCl.setSortable(false);


        pcPageCl.setSortable(false);
        pcOpenCl.setSortable(false);
        pcCheckTimeCl.setSortable(false);
        pcLocCl.setSortable(false);

        pcCheckTimeCl.setMinWidth(80);
        pcKeywordsCl.setMinWidth(150);


        mobileKeywordsCl.setSortable(false);
        mobilePageCl.setSortable(false);
        mobileLocCl.setSortable(false);
        mobileCheckTimeCl.setSortable(false);
        mobileOpenCl.setSortable(false);

        mobileCheckTimeCl.setMinWidth(80);
        mobileKeywordsCl.setMinWidth(150);


        pcKeywordsCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().keywordProperty();
            }
        });

        pcPageCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().pageProperty();
            }
        });

        pcLocCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().locProperty();
            }
        });

        pcCheckTimeCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().timeProperty();
            }
        });


        pcOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));

        pcOpenCl.setCellFactory(new Callback<TableColumn<InfoModel, String>, TableCell<InfoModel, String>>() {
            @Override
            public TableCell<InfoModel, String> call(TableColumn<InfoModel, String> param) {
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

                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("double clicked!");
                        TableCell<InfoModel, String> cell = (TableCell<InfoModel, String>) event.getSource();

                        InfoModel model = listPc.get(cell.getIndex());

//                        if (model != null && Desktop.isDesktopSupported()) {
//                            try {
//                                Desktop.getDesktop().browse(new URI(model.getUrl()));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
                        if (model != null) {
                            String url = BaiduCapture.makeUrl(Info.TYPE_PC, model.getKeyword(), model.getPage());
                            app.getHostServices().showDocument(url);
                        }

//
                    }
                });
                return cell;
            }
        });

        mobileKeywordsCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().keywordProperty();
            }
        });

        mobilePageCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().pageProperty();
            }
        });

        mobileLocCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().locProperty();
            }
        });

        mobileCheckTimeCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().timeProperty();
            }
        });

        mobileOpenCl.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));

        mobileOpenCl.setCellFactory(new Callback<TableColumn<InfoModel, String>, TableCell<InfoModel, String>>() {
            @Override
            public TableCell<InfoModel, String> call(TableColumn<InfoModel, String> param) {
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

                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("double clicked!");
                        TableCell<InfoModel, String> cell = (TableCell<InfoModel, String>) event.getSource();

                        InfoModel model = listMobile.get(cell.getIndex());
                        String url = BaiduCapture.makeUrl(Info.TYPE_MOBILE, model.getKeyword(), model.getPage());
                        app.getHostServices().showDocument(url);

                    }
                });
                return cell;
            }
        });

    }


    @FXML
    private void handleStart() {
        manage.setTaskStatus(TaskManage.TASK_STATUS_NEW);


        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }

        manage.setSpeed(runSpeed);

        manage.setTaskStatus(TaskManage.TASK_STATUS_NEW_RUNNING);

        manage.setAutoUpdate(autoUploadCb.isSelected());

        manage.setKeywordMax(Integer.parseInt(maxTf.getText()));


        //new设置， 放在子线程里更新不上


        Thread thread = new Thread(manage);
//        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleStop() {
        manage.stopAll();
        manage.setTaskStatus(TaskManage.TASK_STATUS_STOPPED);
        showAlert(Alert.AlertType.INFORMATION, "已停止 \n");
    }

    @FXML
    private void handleResume() {

        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }

        manage.setSpeed(runSpeed);
        manage.setKeywordMax(Integer.parseInt(maxTf.getText()));
        manage.setTaskStatus(TaskManage.TASK_STATUS_RESUME_RUNNING);
        manage.setAutoUpdate(autoUploadCb.isSelected());

        Thread thread = new Thread(manage);
//        thread.setDaemon(true);
        thread.start();

    }

    /**
     * 更新总数量
     */
    public void updateTotal() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        totalLabel.setText("" + manage.getTotal());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            totalLabel.setText("" + manage.getTotal());
        }

    }


    /**
     * 更新已检索数量
     *
     * @param runned
     */
    public void updateRunned(int runned) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    checkedLabel.setText("" + runned);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpload() {

        manage.setTaskStatus(manage.getTaskStatus() == TaskManage.TASK_STATUS_STOPPED ?
                TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING
                : TaskManage.TASK_UPLOAD_FINISHED_RUNNING);
        manage.uploadResult();

    }

    /**
     * 更新状态
     */
    public void updateTaskStatus() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateStatus();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            updateStatus();
        }

    }

    private void updateStatus() {
        System.out.println("current status is " + manage.getTaskStatus());
        if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW) {
            updateNewStatus();
        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW_RUNNING
                || manage.getTaskStatus() == TaskManage.TASK_STATUS_RESUME_RUNNING
                ) {
            updateRunningStatus();
        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_STOPPED) {
            updateStopStatus();
        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_FINISHED) {
            updateFinishedStatus();
        } else if (manage.getTaskStatus() == TaskManage.TASK_UPLOAD_FINISHED_RUNNING
                || manage.getTaskStatus() == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING) {
            updateUploadingStatus();
        } else if (manage.getTaskStatus() == TaskManage.TASK_UPLOAD_FINISHED_FINISHED
                || manage.getTaskStatus() == TaskManage.TASK_UPLOAD_UNFINISHED_FINISHED) {
            updateUploadFinishedStatus();
            resumeBtn.setDisable(manage.getTaskStatus() == TaskManage.TASK_UPLOAD_FINISHED_FINISHED); //继续按钮
        }
    }

    private void updateNewStatus() {
        checkedLabel.setText("0");
        manage.setRunned(0);

        manage.setMobileBingo(0);
        showedPcLabel.setText("0");
        manage.setPcBingo(0);
        showedMobileLabel.setText("0");

        startBtn.setDisable(false);//开始按钮 可用
        stopBtn.setDisable(true); //结束按钮 不可用
        resumeBtn.setDisable(true); //继续按钮 不可用
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

        listPc.clear();
        listMobile.clear();
        manage.clearData();
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

        if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW_RUNNING) {
            listPc.clear();
            listMobile.clear();
            manage.clearData();
        }
    }

    private void updateStopStatus() {
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
        if (info.getType() == Info.TYPE_PC) {
            listPc.add(0, new InfoModel(info));
            if (listPc.size() > MAX_SHOW) {
                listPc.remove(MAX_SHOW - 1, listPc.size() - 1);
            }
        } else {
            listMobile.add(0, new InfoModel(info));
            listMobile.remove(MAX_SHOW - 1, listPc.size() - 1);
        }

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showedMobileLabel.setText(manage.getMobileBingo() + "");
                    showedPcLabel.setText(manage.getPcBingo() + "");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(type);
                        alert.setContentText(message);
                        alert.setHeaderText(null);
                        alert.show();
                    }
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

    /**
     * 显示网络异常alert
     */
    public void alertNetError() {
        showAlert(Alert.AlertType.ERROR, "网络错误，请检查网络并上后重试");
    }

    public void alertVital() {
        showAlert(Alert.AlertType.ERROR, "初始化失败，请重启软件");
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
//                Desktop desktop = Desktop.getDesktop();
//                desktop.open(file);
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
                manage.setCustomKeywords(customList);
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
        boolean pcSelected = checkPcCb.isSelected();
        boolean mobileSelected = checkMobileCb.isSelected();
        int type = 0;
        if (pcSelected && mobileSelected) {
            type = Info.TYPE_BOTH;
        } else if (pcSelected) {
            type = Info.TYPE_PC;
        } else if (mobileSelected) {
            type = Info.TYPE_MOBILE;
        }

        manage.setType(type);
    }


}
