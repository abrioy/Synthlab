package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ToolboxController implements Initializable {
    private static final Logger logger = Logger.getLogger(ToolboxController.class.getName());


    @FXML private TitledPane input;
    @FXML private TitledPane output;

    //TODO try a treeView ?
    @FXML private Accordion toolbox;


	private Consumer<String> onDragDone = null;
	public void setOnDragDone(Consumer<String> onDragDone) {
		this.onDragDone = onDragDone;
	}

    @FXML
    private TitledPane filter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO found the bug on the drag and drop
        toolbox.setExpandedPane(input);

        ListView<String> list1 = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(ModuleEnum.VCOA.toString(),
                ModuleEnum.VCA.toString());
        list1.setItems(items);

        input.setContent(list1);
        makeListDraggable(list1);

        ListView<String> list2 = new ListView<>();
        items = FXCollections.observableArrayList(ModuleEnum.OUT.toString(), ModuleEnum.SCOP.toString());
        list2.setItems(items);

        output.setContent(list2);
        makeListDraggable(list2);

        //TODO sprint 2
        ListView<String> list3 = new ListView<>();

        filter.setContent(list3);
    }

    private void makeListDraggable(ListView<String> list){

        list.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
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
				if(onDragDone != null) {
					onDragDone.accept(cell.getItem());
				}
			});

            return cell;
        });
    }

}
