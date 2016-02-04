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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToolboxController implements Initializable {
    private static final Logger logger = Logger.getLogger(ToolboxController.class.getName());

    @FXML
    private Accordion toolbox;

    @FXML
    private TitledPane input;

    @FXML
    private TitledPane output;

    //TODO Sprint 2
    //@FXML
    //private TitledPane filter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO found the bug on the drag and drop
        ListView<String> list1 = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(ModuleEnum.VCOA.toString());
        list1.setItems(items);
        list1.setPrefHeight(70);

        input.setContent(list1);
        makeListDraggable(list1);

        ListView<String> list2 = new ListView<>();
        items = FXCollections.observableArrayList(ModuleEnum.OUT.toString(), ModuleEnum.SCOP.toString());
        list2.setItems(items);
        list2.setPrefHeight(70);

        output.setContent(list2);
        makeListDraggable(list2);
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

            cell.setOnDragDone(event -> logger.log(Level.INFO, "done"));

            return cell;
        });
    }

}
