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

    private TreeItem<String> input = new TreeItem<>("Input");
    private TreeItem<String> output = new TreeItem<>("output");
    private TreeItem<String> filter = new TreeItem<>("Filter");

    @FXML
    private TreeItem<String> root;

    @FXML private TreeView<String> toolbox;

    private ObservableList<String> items1;
    private ObservableList<String> items2;
    private ObservableList<String> items3;

	private Consumer<String> onDragDone = null;
	public void setOnDragDone(Consumer<String> onDragDone) {
		this.onDragDone = onDragDone;
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        input.expandedProperty().addListener(listener -> drag(input));
        output.expandedProperty().addListener(listener -> drag(output));
        filter.expandedProperty().addListener(listener -> drag(filter));

        items1 = FXCollections.observableArrayList(
				ModuleEnum.VCOA.toString(),
                ModuleEnum.VCA.toString()
		);
        loadTreeItems(input,items1);

        items2 = FXCollections.observableArrayList(
				ModuleEnum.OUT.toString(),
				ModuleEnum.SCOP.toString()
		);
        loadTreeItems(output,items2);

        items3 = FXCollections.observableArrayList(
				ModuleEnum.REP.toString(),
				ModuleEnum.EG.toString(),
				ModuleEnum.VCFLP.toString()
		);
        loadTreeItems(filter,items3);
        
        root.setExpanded(true);
        toolbox.setRoot(root);
    }

    public void loadTreeItems(TreeItem<String> item, ObservableList<String> rootItems) {
        item.setExpanded(true);
        for (String itemString : rootItems) {
            item.getChildren().add(new TreeItem<>(itemString));
            //makeListDraggable(itemString, item);
        }
        root.getChildren().add(item);
    }

    private void makeListDraggable(){
        init();
        toolbox.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> cell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        if (item != null) {
                            super.updateItem(item, empty);
                            if (input.isExpanded()){
                                super.updateItem(item, empty);
                                setText(item);
                            }
                            else if  (output.isExpanded()){
                                super.updateItem(item, empty);
                                setText(item);
                            }
                            else if  (filter.isExpanded()){
                                super.updateItem(item, empty);
                                setText(item);
                            }
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

    private void init() {
        toolbox = new TreeView<>();
        toolbox.setRoot(root);
    }

    private void drag(TreeItem<String> item){
        System.out.println("echo");
        //if (item.isExpanded()) {
            makeListDraggable();
        /*}
        else {
            //toolbox.getCellFactory(
        }*/
    }

}
