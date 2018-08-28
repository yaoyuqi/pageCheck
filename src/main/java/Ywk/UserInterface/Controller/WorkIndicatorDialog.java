package Ywk.UserInterface.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public class WorkIndicatorDialog<P> {
    private final ProgressIndicator progressIndicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
    private final Stage dialog = new Stage(StageStyle.UNDECORATED);
    private final Label label = new Label();
    private final Group root = new Group();
    private final Scene scene = new Scene(root, 330, 120, Color.WHITE);
    private final BorderPane mainPane = new BorderPane();
    private final VBox vBox = new VBox();
    public ObservableList<Integer> resultNotificationList = FXCollections.observableArrayList();
    public Integer resultValue;
    private Task animationWorker;
    private Task<Integer> taskWorker;

    public WorkIndicatorDialog(Window owner, String label) {
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);

        dialog.setResizable(false);
        this.label.setText(label);
    }

    public void addTaskEndNotification(Consumer<Integer> c) {
        resultNotificationList.addListener((ListChangeListener<? super Integer>) n -> {
            resultNotificationList.clear();
            c.accept(resultValue);
        });
    }

    public void exec(P parameter, ToIntFunction func) {

    }
}
