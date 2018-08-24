package Ywk.UserInterface.Controller;

import Ywk.Data.Info;
import Ywk.MainApp;
import Ywk.UserInterface.Model.InfoModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.awt.*;
import java.net.URI;

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

    public HomeController() {
    }

    public void setApp(MainApp app) {
        this.app = app;
        for (int i = 0; i < 10000; i++) {
            Info info = new Info();
            info.setKeyword("key" + i);
            info.setTime("2018-9-1");
            info.setLoc(new String[]{"1", "2", "3"});
            info.setType(1);
            listPc.add(new InfoModel(info));
        }

        for (int i = 0; i < 10000; i++) {
            Info info = new Info();
            info.setKeyword("key" + i);
            info.setTime("2018-9-1");
            info.setLoc(new String[]{"1", "2", "3"});
            info.setType(2);
            listMobile.add(new InfoModel(info));
        }


    }

    @FXML
    private void initialize() {
        listPcTv.setItems(listPc);
        listMobileTv.setItems(listMobile);


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
//                        System.out.println("double clicked!");
                        TableCell<InfoModel, String> cell = (TableCell<InfoModel, String>) event.getSource();

                        try {
                            InfoModel model = listPc.get(cell.getIndex());
                            Desktop.getDesktop().browse(new URI(model.getUrl()));
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }
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
//                        System.out.println("double clicked!");
                        TableCell<InfoModel, String> cell = (TableCell<InfoModel, String>) event.getSource();

                        try {
                            InfoModel model = listMobile.get(cell.getIndex());
                            Desktop.getDesktop().browse(new URI(model.getUrl()));
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }
                    }
                });
                return cell;
            }
        });

    }
}
