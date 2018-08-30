package Ywk.UserInterface.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class KeywordsController {

    @FXML
    private TableView<String> tv;

    @FXML
    private TableColumn<String, String> wordColumn;

    private ObservableList<String> customList = FXCollections.observableArrayList();

    private Stage dialogStage;

    @FXML
    private Button submitBtn;

    @FXML
    private Button cancelBtn;

    private boolean okClicked = false;

    @FXML
    private void initialize() {

        tv.setItems(customList);
        wordColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> param) {
                return new SimpleStringProperty(param.getValue());
            }
        });

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCustomList(List<String> list) {
//        for (String e :
//                list) {
//
//        }
        customList.clear();
        customList.addAll(list);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleSubmit() {
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }
}
