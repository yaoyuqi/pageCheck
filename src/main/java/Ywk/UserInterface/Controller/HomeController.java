package Ywk.UserInterface.Controller;

import Ywk.Api.HltApi;
import Ywk.Data.Info;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import okhttp3.OkHttpClient;

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


    private TaskManage manage;

    private OkHttpClient client;


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

        pageChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3));
        pageChoiceBox.setValue(1);

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
        boolean pcSelected = checkPcCb.isSelected();
        boolean mobileSelected = checkMobileCb.isSelected();
        int type = 0;
        if (pcSelected && mobileSelected) {
            type = Info.TYPE_BOTH;
        } else if (pcSelected) {
            type = Info.TYPE_PC;
        } else if (mobileSelected) {
            type = Info.TYPE_MOBILE;
        } else {
            //TODO error
        }

        manage.setPageDepth(pageChoiceBox.getValue());

        manage.setType(type);


        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }

        manage.setSpeed(runSpeed);

        manage.setTaskStatus(TaskManage.TASK_STATUS_NEW_RUNNING);

        manage.setAutoUpdate(autoUploadCb.isSelected());


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
        boolean pcSelected = checkPcCb.isSelected();
        boolean mobileSelected = checkMobileCb.isSelected();
        int type = 0;
        if (pcSelected && mobileSelected) {
            type = Info.TYPE_BOTH;
        } else if (pcSelected) {
            type = Info.TYPE_PC;
        } else if (mobileSelected) {
            type = Info.TYPE_MOBILE;
        } else {
            //TODO error
        }

        manage.setType(type);

        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }

        manage.setPageDepth(pageChoiceBox.getValue());
        manage.setSpeed(runSpeed);
        manage.setTaskStatus(TaskManage.TASK_STATUS_RESUME_RUNNING);
        manage.setAutoUpdate(autoUploadCb.isSelected());

        Thread thread = new Thread(manage);
//        thread.setDaemon(true);
        thread.start();

    }

    /**
     * 更新总数量
     *
     * @param total
     */
    public void setTotal(final int total) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int newTotal = total;
                    if (manage.getType() == Info.TYPE_BOTH) {
                        newTotal = total * 2;
                    }
                    newTotal *= pageChoiceBox.getValue();
                    totalLabel.setText("" + newTotal);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新已检索数量
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

        if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW_RUNNING) {
            listPc.clear();
            listMobile.clear();
            manage.clearData();
        }
    }

    private void updateStopStatus() {
        startBtn.setDisable(false); //开始按钮 可用
        startBtn.setText("重新开始");
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(false);  //继续按钮 可用
        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用
    }


    private void updateFinishedStatus() {
        startBtn.setDisable(false); //开始按钮 可用
        startBtn.setText("开始");
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(false);  //继续按钮 可用
        checkPcCb.setDisable(false); //平台勾选 可用
        checkMobileCb.setDisable(false); //平台勾选 可用
        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用
        uploadBtn.setDisable(false); //上传按钮 可用
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
    }


    /**
     * 回调， 将爬取结果放回列表显示
     * 同时更新页面命中数
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
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    /**
     * 显示网络异常alert
     */
    public void alertNetError() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showAlert(Alert.AlertType.ERROR, "网络错误，请检查网络并上后重试");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "网络错误，请检查网络并上后重试");
        }


    }

    public void alertVital() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showAlert(Alert.AlertType.ERROR, "初始化失败，请重启软件");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "初始化失败，请重启软件");
        }
    }

    /**
     * 显示上传成功alert
     */
    public void uploadSuccess() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showAlert(Alert.AlertType.INFORMATION, "上传成功");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "上传成功");
        }
    }

    /**
     * 显示上传失败alert
     */
    public void alertUploadError() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showAlert(Alert.AlertType.ERROR, "结果上传出错，请稍后重试");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "结果上传出错，请稍后重试");
        }


    }


}
