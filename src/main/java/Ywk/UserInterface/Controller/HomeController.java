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
    private TableColumn<InfoModel, String> pcLocCl;
    @FXML
    private TableColumn<InfoModel, String> pcCheckTimeCl;
    @FXML
    private TableColumn<InfoModel, String> pcOpenCl;

    @FXML
    private TableColumn<InfoModel, String> mobileKeywordsCl;
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


    private TaskManage manage;

    private OkHttpClient client;

    public HomeController() {
    }

    public void setApp(MainApp app) {
        this.app = app;
//        manage = new TaskManage(identity);
        manage = new TaskManage();
        manage.setController(this);


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
        System.out.println("identity=" + identity);
    }

    @FXML
    private void initialize() {
        listPcTv.setItems(listPc);
        listMobileTv.setItems(listMobile);

        uploadBtn.setDisable(true);
        autoUploadCb.setSelected(true);

        speedSlider.setValue(50.0);

        HltApi api = HltApi.getInstance();
        api.identity(this);

        pcKeywordsCl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InfoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InfoModel, String> param) {
                return param.getValue().keywordProperty();
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
                            String url = BaiduCapture.makeUrl(Info.TYPE_PC, model.getKeyword());
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
                        String url = BaiduCapture.makeUrl(Info.TYPE_MOBILE, model.getKeyword());
                        app.getHostServices().showDocument(url);

                    }
                });
                return cell;
            }
        });

    }


    @FXML
    private void HandleStart() {
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

        manage.setSpeed(runSpeed);


        manage.setTaskStatus(TaskManage.TASK_STATUS_NEW);

        //new设置， 放在子线程里更新不上


        Thread thread = new Thread(manage);
        thread.start();


    }

    @FXML
    private void handleStop() {
        manage.stopAll();
        manage.setTaskStatus(TaskManage.TASK_STATUS_STOPPED);

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

        manage.setSpeed(runSpeed);


        manage.setTaskStatus(TaskManage.TASK_STATUS_RESUME);


        Thread thread = new Thread(manage);
        thread.start();

    }

    public void setTotal(final int total) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int newTotal = total;
                    if (manage.getType() == Info.TYPE_BOTH) {
                        newTotal = total * 2;
                    }
                    totalLabel.setText("" + newTotal);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public void updateTaskStatus() {

        if (manage.getTaskStatus() == TaskManage.TASK_STATUS_RUNNING
                || manage.getTaskStatus() == TaskManage.TASK_STATUS_FINISHED
                ) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (manage.getTaskStatus() == TaskManage.TASK_STATUS_RUNNING) {
                            startBtn.setDisable(true);
                            stopBtn.setDisable(false);
                            resumeBtn.setDisable(true);
                            checkPcCb.setDisable(true);
                            checkMobileCb.setDisable(true);
                            autoUploadCb.setDisable(true);
                            uploadBtn.setDisable(true);
                        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_FINISHED) {
                            startBtn.setDisable(false);
                            startBtn.setText("开始");
                            stopBtn.setDisable(true);
                            resumeBtn.setDisable(true);
                            checkPcCb.setDisable(false);
                            checkMobileCb.setDisable(false);
                            autoUploadCb.setDisable(true);
                            uploadBtn.setDisable(false);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW) {
            checkedLabel.setText("0");
            manage.setRunned(0);

            manage.setMobileBingo(0);
            showedPcLabel.setText("0");
            manage.setPcBingo(0);
            showedMobileLabel.setText("0");

            startBtn.setDisable(false);
            stopBtn.setDisable(true);
            resumeBtn.setDisable(true);
            checkPcCb.setDisable(false);
            checkMobileCb.setDisable(false);
            autoUploadCb.setDisable(false);
            uploadBtn.setDisable(false);

            listPc.clear();
            listMobile.clear();
        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_STOPPED) {
            startBtn.setDisable(false);
            startBtn.setText("重新开始");
            stopBtn.setDisable(true);
            resumeBtn.setDisable(false);
            checkPcCb.setDisable(false);
            checkMobileCb.setDisable(false);

            autoUploadCb.setDisable(true);
            uploadBtn.setDisable(false);

        } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_RESUME) {
            //new设置， 放在子线程里更新不上
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
            resumeBtn.setDisable(true);
            checkPcCb.setDisable(false);
            checkMobileCb.setDisable(false);
            autoUploadCb.setDisable(true);
            uploadBtn.setDisable(false);

        }
    }

    public synchronized void addResult(Info info) {
        if (info.getType() == Info.TYPE_PC) {

            listPc.add(new InfoModel(info));
        } else {
            listMobile.add(new InfoModel(info));
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


}
