package Ywk.UserInterface.Controller;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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


    private TaskManage manage;

    public HomeController() {
    }

    public void setApp(MainApp app, String identity) {
        this.app = app;
        manage = new TaskManage(identity);
        manage.setController(this);
    }

    @FXML
    private void initialize() {
        listPcTv.setItems(listPc);
        listMobileTv.setItems(listMobile);
//        updateTaskStatus();

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

                            try {
                                String url = model.getKeyword();
                                url = URLEncoder.encode(url, "UTF-8");
                                url = BaiduCapture.makeUrl(Info.TYPE_PC, url);
                                System.out.println(url);
                                app.getHostServices().showDocument(url);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
//                            String url = model.getTime();
//                            app.getHostServices().showDocument("http://www.baidu.com?wd=" + url);
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
                        try {
                            String url = model.getKeyword();
                            url = URLEncoder.encode(url, "UTF-8");
                            url = BaiduCapture.makeUrl(Info.TYPE_MOBILE, url);
                            System.out.println(url);
                            app.getHostServices().showDocument(url);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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

        startBtn.setDisable(true);
        stopBtn.setDisable(false);

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
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (manage.getTaskStatus() == TaskManage.TASK_STATUS_NEW) {
                        startBtn.setDisable(false);
                        stopBtn.setDisable(true);
                        resumeBtn.setDisable(true);
                        checkPcCb.setDisable(false);
                        checkMobileCb.setDisable(false);
                    } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_RUNNING) {
                        startBtn.setDisable(true);
                        stopBtn.setDisable(false);
                        resumeBtn.setDisable(false);
                        resumeBtn.setText("暂停");

                        checkPcCb.setDisable(true);
                        checkMobileCb.setDisable(true);
                    } else if (manage.getTaskStatus() == TaskManage.TASK_STATUS_FINISHED) {
                        startBtn.setDisable(false);
                        startBtn.setText("重新开始");
                        stopBtn.setDisable(true);
                        resumeBtn.setDisable(true);
                        checkPcCb.setDisable(false);
                        checkMobileCb.setDisable(false);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addResult(Info info) {
        if (info.getType() == Info.TYPE_PC) {

            listPc.add(new InfoModel(info));
        } else {
            listMobile.add(new InfoModel(info));
        }
    }


}
