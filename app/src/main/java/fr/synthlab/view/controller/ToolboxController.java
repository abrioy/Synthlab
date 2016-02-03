package fr.synthlab.view.controller;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    //@FXML
    //private TitledPane filter;

    private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListView<String> list1 = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList("VCOA");
        list1.setItems(items);
        list1.setPrefHeight(70);

        input.setContent(list1);


        list1.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>(){
                @Override
                public void updateItem(String item , boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnDragDetected(event -> {
                if (! cell.isEmpty()) {
                    Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(cell.getItem());
                    db.setContent(cc);
                    dragSource.set(cell);
                }
            });

            cell.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            });

            cell.setOnDragDone(event -> logger.log(Level.INFO, "done"));

            return cell ;
        });


        ListView<String> list2 = new ListView<String>();
        items = FXCollections.observableArrayList("OUT", "SCOPE");
        list2.setItems(items);
        list2.setPrefHeight(70);

        output.setContent(list2);
    }

}
