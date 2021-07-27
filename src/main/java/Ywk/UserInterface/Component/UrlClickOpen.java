package Ywk.UserInterface.Component;

import Ywk.UserInterface.Model.InfoModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class UrlClickOpen implements Callback<TableColumn<InfoModel, String>, TableCell<InfoModel, String>> {
    private final InfoModel model;

    public UrlClickOpen(InfoModel model) {
        this.model = model;
    }

    @Override
    public TableCell<InfoModel, String> call(TableColumn<InfoModel, String> infoModelStringTableColumn) {
        var cell = new TableCell<InfoModel, String>() {
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
//                try {
//                    String keyword = URLEncoder.encode(model.getKeyword(), StandardCharsets.UTF_8.toString());
//                    String url = model.getInfo().getPlatform().nextPageUrlBrowse(keyword, model.getPage());
//                    app.getHostServices().showDocument(url);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

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
        });
        return cell;
    }
}
