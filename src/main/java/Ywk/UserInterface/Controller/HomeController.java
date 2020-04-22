package Ywk.UserInterface.Controller;

import Ywk.Client.CookiesStore;
import Ywk.Client.PlatformWrapper;
import Ywk.Client.SearchPlatform;
import Ywk.Data.Info;
import Ywk.Data.KeywordGenerator;
import Ywk.MainApp;
import Ywk.PageCheck.ContentChecker;
import Ywk.PageCheck.TaskManage;
import Ywk.PageCheck.TaskStatus;
import Ywk.PageCheck.UploadStatus;
import Ywk.UserInterface.Model.InfoModel;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements ContentChecker.PageValidate {
    private MainApp app;

    @FXML
    private Label totalLabel;
    private SimpleIntegerProperty totalProperty = new SimpleIntegerProperty(0);

    @FXML
    private Label checkedLabel;
    private SimpleIntegerProperty checkedProperty = new SimpleIntegerProperty(0);

    private Map<Integer, IntegerProperty> bingoCntProperties = new HashMap<>();

    @FXML
    private VBox searchPlatformsPlaceholder;

    @FXML
    private GridPane bingCntPlaceholder;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startBtn;


    @FXML
    private Button resumeBtn;

    @FXML
    private Button stopBtn;

//    @FXML
//    private TableView<InfoModel> listPcTv;
//
//    @FXML
//    private TableView<InfoModel> listMobileTv;
//
//    @FXML
//    private TableView<InfoModel> listShenmaTv;
//
//    @FXML
//    private TableView<InfoModel> listToutiaoTv;

    @FXML
    private TabPane tabPlaceholder;
    private Map<Integer, ObservableList<InfoModel>> listData = new HashMap<>();

//
//    private ObservableList<InfoModel> listPc = FXCollections.observableArrayList();
//    private ObservableList<InfoModel> listMobile = FXCollections.observableArrayList();
//    private ObservableList<InfoModel> listShenma = FXCollections.observableArrayList();
//    private ObservableList<InfoModel> listToutiao = FXCollections.observableArrayList();

//    @FXML
//    private TableColumn<InfoModel, String> pcKeywordsCl;
//    @FXML
//    private TableColumn<InfoModel, String> pcProductCl;
//    @FXML
//    private TableColumn<InfoModel, String> pcPageCl;
//    @FXML
//    private TableColumn<InfoModel, String> pcLocCl;
//    @FXML
//    private TableColumn<InfoModel, String> pcCheckTimeCl;
//    @FXML
//    private TableColumn<InfoModel, String> pcOpenCl;
//
//    @FXML
//    private TableColumn<InfoModel, String> mobileKeywordsCl;
//    @FXML
//    private TableColumn<InfoModel, String> mobileProductCl;
//    @FXML
//    private TableColumn<InfoModel, String> mobilePageCl;
//    @FXML
//    private TableColumn<InfoModel, String> mobileLocCl;
//    @FXML
//    private TableColumn<InfoModel, String> mobileCheckTimeCl;
//    @FXML
//    private TableColumn<InfoModel, String> mobileOpenCl;
//
//
//    @FXML
//    private TableColumn<InfoModel, String> shenmaKeywordsCl;
//    @FXML
//    private TableColumn<InfoModel, String> shenmaProductCl;
//    @FXML
//    private TableColumn<InfoModel, String> shenmaPageCl;
//    @FXML
//    private TableColumn<InfoModel, String> shenmaLocCl;
//    @FXML
//    private TableColumn<InfoModel, String> shenmaCheckTimeCl;
//    @FXML
//    private TableColumn<InfoModel, String> shenmaOpenCl;
//
//
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoKeywordsCl;
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoProductCl;
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoPageCl;
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoLocCl;
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoCheckTimeCl;
//    @FXML
//    private TableColumn<InfoModel, String> toutiaoOpenCl;

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

    private final static int MAX_SHOW = 1000;


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


    public void setApp(MainApp app) {
        this.app = app;
    }

    public void initFinished() {
        alertInitFinished();
    }

    @FXML
    private void initialize() {
        task = new TaskManage(this);

        initCntLabel();


        uploadBtn.setDisable(true);
        autoUploadCb.setSelected(true);

        speedSlider.setValue(50.0);
        pageDepthSelectSet();
        searchPlatformSelectSet();
        keywordsRadioSet();
        initResultTabs();


        this.updateTotal();

    }

    private void initResultTabs() {
        for (SearchPlatform platform : PlatformWrapper.getInstance().getList()) {
            ObservableList<InfoModel> list = FXCollections.observableArrayList();
            listData.put(platform.getId(), list);
            TableView<InfoModel> tableView = new TableView<>();
            tableView.setItems(list);

            TableColumn<InfoModel, String> keywordCell = new TableColumn<>("关键词");
//            TableColumn<InfoModel, String> productCell = new TableColumn<>("产品");
            TableColumn<InfoModel, String> pageCell = new TableColumn<>("页数");
            TableColumn<InfoModel, String> locCell = new TableColumn<>("位置");
            TableColumn<InfoModel, String> checkTimeCell = new TableColumn<>("查询时间");
            TableColumn<InfoModel, String> browseCell = new TableColumn<>("查看页面");

            keywordCell.setSortable(false);
//            productCell.setSortable(false);
            pageCell.setSortable(false);
            browseCell.setSortable(false);
            checkTimeCell.setSortable(false);
            locCell.setSortable(false);
            checkTimeCell.setMinWidth(80);
            keywordCell.setMinWidth(150);
//            productCell.setMinWidth(80);

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


            keywordCell.setCellValueFactory(keywordCallback);
//            productCell.setCellValueFactory(productCallback);
            pageCell.setCellValueFactory(pageCallback);
            locCell.setCellValueFactory(locCallback);
            checkTimeCell.setCellValueFactory(timeCallback);
            browseCell.setCellValueFactory(cellData -> new SimpleStringProperty("点击查看"));
            browseCell.setCellFactory(urlClickOpenCallback(list, platform));

//            tableView.getColumns().setAll(keywordCell, productCell, pageCell, locCell, checkTimeCell, browseCell);
            tableView.getColumns().setAll(keywordCell, pageCell, locCell, checkTimeCell, browseCell);
            Tab tab = new Tab();
            tab.setText(platform.getName());
            tabPlaceholder.getTabs().add(tab);

            tab.setContent(tableView);
        }
    }

    private void initCntLabel() {
         /*
        设定页面的默认项目
         */
        totalLabel.textProperty().bind(totalProperty.asString());
        checkedLabel.textProperty().bind(checkedProperty.asString());

        int row = 2;
        bingCntPlaceholder.getColumnConstraints().add(new ColumnConstraints(130));
        bingCntPlaceholder.getColumnConstraints().add(new ColumnConstraints(130));
        for (SearchPlatform platform : PlatformWrapper.getInstance().getList()) {
            Label label = new Label(platform.getName() + "上屏");
            GridPane.setConstraints(label, 0, row);
            SimpleIntegerProperty property = new SimpleIntegerProperty(0);
            bingoCntProperties.put(platform.getId(), property);
            Label cntLabel = new Label();
            cntLabel.textProperty().bind(property.asString());

            GridPane.setConstraints(cntLabel, 1, row);
            row++;

            bingCntPlaceholder.getChildren().addAll(label, cntLabel);
        }

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
                    try {
                        String keyword = URLEncoder.encode(model.getKeyword(), StandardCharsets.UTF_8.toString());
                        String url = platform.nextPageUrlBrowse(keyword, model.getPage());
                        app.getHostServices().showDocument(url);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

//                    Desktop desktop = Desktop.getDesktop();
//                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
//                        try {
//                            desktop.browse(new URI(url));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
                }
            });
            return cell;
        };
    }

    private void searchPlatformSelectSet() {
        ChangeListener<Boolean> cbChangeListener = (observable, oldValue, newValue) -> {
            updateTotal();
        };

        for (SearchPlatform platform : PlatformWrapper.getInstance().getList()) {
            CheckBox cb = new CheckBox(platform.getName());
            cb.selectedProperty().addListener(cbChangeListener);
            searchPlatformsPlaceholder.getChildren().add(cb);
        }

        CheckBox cb = (CheckBox) searchPlatformsPlaceholder.getChildren().get(0);
        cb.setSelected(true);
    }

    private List<SearchPlatform> getSelectedPlatforms() {
        return searchPlatformsPlaceholder.getChildren().stream().filter(node -> {
            CheckBox cb = (CheckBox) node;
            return cb.isSelected();
        }).map(node -> {
            String name = ((CheckBox) node).getText();
            return PlatformWrapper.getInstance().getList().stream()
                    .filter(platform -> platform.getName().equals(name)).findAny();
        })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    private void pageDepthSelectSet() {
        List<Integer> pages = new ArrayList<>(PlatformWrapper.getInstance().getPageDepthMax());

        for (int i = 0; i < PlatformWrapper.getInstance().getPageDepthMax(); i++) {
            pages.add(i + 1);
        }
        final ObservableList<Integer> choice = FXCollections.observableArrayList(pages);
        pageChoiceBox.setItems(choice);
        pageChoiceBox.setValue(pages.get(0));
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

        for (Map.Entry<Integer, IntegerProperty> bingo : bingoCntProperties.entrySet()) {
            bingo.getValue().setValue(task.bingCntRetrieve(bingo.getKey()));
        }

        for (int key : listData.keySet()) {
            listData.get(key).clear();
        }

        int runSpeed = ((Double) speedSlider.getValue()).intValue() % 10;
        if (runSpeed == 0) {
            runSpeed = 1;
        }


        task.setSpeed(runSpeed);

        task.setAutoUpdate(autoUploadCb.isSelected());

        task.setKeywordMax(Integer.parseInt(maxTf.getText()));
        task.setAvailablePlatforms(getSelectedPlatforms());

        //new设置， 放在子线程里更新不上
        task.start();

        Thread thread = new Thread(task);
//        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleStop() {
        task.stopAll();

        updateTaskStatus();
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
        updateTaskStatus();

        Thread thread = new Thread(task);
        thread.start();


    }

    /**
     * 更新总数量
     */
    private void updateTotal() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.runLater(() -> {
                    updateTotalStatus();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            updateTotalStatus();
        }

    }

    private void updateTotalStatus() {
        int total = task.getTotal() * getSelectedPlatforms().size();
        totalProperty.setValue(total);
        if (total == 0) {
            startBtn.setDisable(true);
            resumeBtn.setDisable(true);
        } else if (task.getTaskStatus() != TaskStatus.RUNNING) {
            startBtn.setDisable(false);
            if (task.getTaskStatus() == TaskStatus.PAUSE) {
                resumeBtn.setDisable(false);

            }
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
    public void updateBingoCnt(int id, int cnt) {
        try {
            Platform.runLater(() -> {
                bingoCntProperties.get(id).setValue(cnt);
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
            if (task.getUploadStatus() == UploadStatus.UPLOADING) {
                updateUploadingStatus();
            } else if (task.getUploadStatus() == UploadStatus.FAIL
                    || task.getUploadStatus() == UploadStatus.SUCCESS) {
                updateUploadFinishedStatus();
                uploadBtn.setDisable(task.getUploadStatus() == UploadStatus.SUCCESS);
                resumeBtn.setDisable(task.getTaskStatus() == TaskStatus.FINISHED); //继续按钮
            } else {
                updatePauseStatus();
            }
        } else if (task.getTaskStatus() == TaskStatus.FINISHED) {
            if (task.getUploadStatus() == UploadStatus.UPLOADING) {
                updateUploadingStatus();
            } else if (task.getUploadStatus() == UploadStatus.FAIL
                    || task.getUploadStatus() == UploadStatus.SUCCESS) {
                updateUploadFinishedStatus();
                resumeBtn.setDisable(task.getTaskStatus() == TaskStatus.FINISHED); //继续按钮
            } else {
                updateFinishedStatus();
            }

        }
    }

    private void updateNewStatus() {
        startBtn.setDisable(false);//开始按钮 可用
        stopBtn.setDisable(true); //结束按钮 不可用
        resumeBtn.setDisable(true); //继续按钮 不可用
        uploadBtn.setDisable(true); //上传按钮  不可用


        //平台勾选 可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(false);
        });


        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用

        maxTf.setDisable(false);
        chooseCustomRb.setDisable(false);
        chooseMainRb.setDisable(false);
        chooseMainSuffixRb.setDisable(false);
        choosePrefixMainSuffixRb.setDisable(false);
        choosePrefixMainRb.setDisable(false);

        for (int key : listData.keySet()) {
            listData.get(key).clear();
        }

        task.newTask();
    }

    private void updateUploadFinishedStatus() {

        startBtn.setDisable(false);//开始按钮 可用
        resumeBtn.setDisable(false); //继续按钮 可用
        stopBtn.setDisable(true); //结束按钮 不可用
        uploadBtn.setDisable(false); //上传按钮 可用


        //平台勾选 可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(false);
        });

        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用

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
        uploadBtn.setDisable(true); //上传按钮  不可用

        //平台勾选 不可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(true);
        });

        autoUploadCb.setDisable(true); //自动上传 不可用
        pageChoiceBox.setDisable(true); //检索深度不可用
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
        uploadBtn.setDisable(false); //上传按钮 可用

        //平台勾选 可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(false);
        });

        autoUploadCb.setDisable(false); //自动上传 可用
        pageChoiceBox.setDisable(false); //检索深度 可用

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

        //平台勾选 可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(false);
        });

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
        startBtn.setDisable(true); //开始按钮 不可用
        stopBtn.setDisable(true);  //结束按钮 不可用
        resumeBtn.setDisable(true);  //继续按钮 不可用
        uploadBtn.setDisable(true); //上传按钮 不可用


        //平台勾选 可用
        searchPlatformsPlaceholder.getChildren().forEach(action -> {
            CheckBox cb = (CheckBox) action;
            cb.setDisable(false);
        });

        autoUploadCb.setDisable(true); //自动上传 不可用
        pageChoiceBox.setDisable(true); //检索深度 不可用

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
        int id = info.getPlatform().getId();
        ObservableList<InfoModel> list = listData.get(id);

        list.add(0, new InfoModel(info));

        if (list.size() > MAX_SHOW) {
            list.remove(MAX_SHOW - 1, list.size() - 1);
        }

        updateBingoCnt(id, task.bingCntRetrieve(id));
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

    public void openWebview(String url) {
        Platform.runLater(() -> {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();

            engine.getLoadWorker().stateProperty().addListener(((observableValue, state, t1) -> {
                if (t1 == Worker.State.SUCCEEDED) {
//                    getCookiesFromWebview(url);
                }
            }));

            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            engine.load(url);
            Stage stage = new Stage();
            stage.setTitle("安全验证");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(app.getPrimaryStage());

            VBox vBox = new VBox(webView);
            Scene scene = new Scene(vBox, 960, 600);

            stage.setScene(scene);
            stage.showAndWait();


        });

    }

    @Override
    public void validate(String url) {
        task.stopAll();
        openWebview(url);
    }


    private void getCookiesFromWebview(String url) {
        CookieManager cookieManager = (CookieManager) CookieHandler.getDefault();

        try {
//            Field f = cookieManager.getClass().getDeclaredField("store");
//            f.setAccessible(true);
//            Object cookieStore = f.get(cookieManager);
//            Field bucketsField = Class.forName("com.sun.webkit.network.CookieStore").getDeclaredField("buckets");
//            bucketsField.setAccessible(true);
//            Map buckets = (Map) bucketsField.get(cookieStore);
//
//            for (Object o : buckets.entrySet()) {
//                Map.Entry entry = (Map.Entry) o;
//                String domain = (String) entry.getKey();
//                Map cookies = (Map) entry.getValue();
//                Cookie cookie = Cookie.parse(domain, cookies.values())
//            }


            URI uri = new URI(url);
            List<Cookie> cookies = cookieManager
                    .getCookieStore().get(uri).stream().map(httpCookie -> {
                        System.out.println(httpCookie.toString());
                        return Cookie.parse(HttpUrl.get(uri), httpCookie.toString());
                    })
                    .collect(Collectors.toList());

            CookiesStore.getCookieStore().put(uri.getHost(), cookies);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


    }
}
