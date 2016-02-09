package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ToolboxController implements Initializable {
    private static final Logger logger = Logger.getLogger(ToolboxController.class.getName());

    @FXML
    private TreeItem<String> input;
    @FXML
    private TreeItem<String> output;
    @FXML
    private TreeItem<String> filter;

    private TreeItem<String> root = new TreeItem<>("Modules");

    @FXML private TreeView<String> toolbox;


	private Consumer<String> onDragDone = null;
	public void setOnDragDone(Consumer<String> onDragDone) {
		this.onDragDone = onDragDone;
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = FXCollections.observableArrayList(ModuleEnum.VCOA.toString(),
                ModuleEnum.VCA.toString());
        loadTreeItems(input,items);

        items = FXCollections.observableArrayList(ModuleEnum.OUT.toString(), ModuleEnum.SCOP.toString());
        loadTreeItems(output,items);

        items = FXCollections.observableArrayList(ModuleEnum.REP.toString(), ModuleEnum.EG.toString());
        loadTreeItems(filter,items);
        
        root.setExpanded(true);
        toolbox.setRoot(root);
    }

    public void loadTreeItems(TreeItem<String> item, ObservableList<String> rootItems) {
        item.setExpanded(true);
        for (String itemString : rootItems) {
            item.getChildren().add(new TreeItem<>(itemString));
            makeListDraggable();
        }
        root.getChildren().add(item);
    }

    private void makeListDraggable(){
        toolbox.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> cell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            super.updateItem(item, empty);
                            setText(item);
                        }
                    }
                };

                cell.setOnDragDetected(event -> {
                    if (!cell.isEmpty()) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent cc = new ClipboardContent();
                        cc.putString(cell.getItem());
                        db.setContent(cc);
                    }
                });

                cell.setOnDragDone(event -> {
                    if (onDragDone != null) {
                        onDragDone.accept(cell.getItem());
                    }
                });
                return cell;
            }

        });
    }

}
