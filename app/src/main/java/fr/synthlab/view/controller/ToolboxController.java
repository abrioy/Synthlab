package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ToolboxController implements Initializable {
    private static final Logger logger = Logger.getLogger(ToolboxController.class.getName());
    @FXML
    private TreeView<String> input;
    @FXML
    private TreeView<String> output;
    @FXML
    private TreeView<String> filter;

    @FXML
    private TreeItem<String> rootInput;
    @FXML
    private TreeItem<String> rootOutput;
    @FXML
    private TreeItem<String> rootFilter;

    @FXML
    private ColorPicker colorPicker;

    private static Color color;

    private Consumer<DragEvent> onDragDone = null;

    public void setOnDragDone(Consumer<DragEvent> onDragDone) {
        this.onDragDone = onDragDone;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootInput.expandedProperty().addListener(listener -> drag(input));
        rootOutput.expandedProperty().addListener(listener -> drag(output));
        rootFilter.expandedProperty().addListener(listener -> drag(filter));

        ObservableList<String> items = FXCollections.observableArrayList(
                ModuleTypes.VCOA.getLongName(),
                ModuleTypes.BRUI.getLongName(),
                ModuleTypes.KEYB.getLongName()
                );

        loadTreeItems(rootInput, items);
        items = FXCollections.observableArrayList(
                ModuleTypes.OUT.getLongName(),
                ModuleTypes.SCOP.getLongName()
        );
        loadTreeItems(rootOutput, items);

        items = FXCollections.observableArrayList(
                ModuleTypes.VCA.getLongName(),
                ModuleTypes.REP.getLongName(),
                ModuleTypes.EG.getLongName(),
                ModuleTypes.VCFLP.getLongName(),
                ModuleTypes.VCFHP.getLongName(),
                ModuleTypes.MIX.getLongName()
        );
        loadTreeItems(rootFilter, items);

        rootInput.setExpanded(true);
        input.setRoot(rootInput);
        rootOutput.setExpanded(true);
        output.setRoot(rootOutput);
        rootInput.setExpanded(true);
        filter.setRoot(rootFilter);

        input.focusedProperty().addListener(listener -> {
            if(!input.focusedProperty().get()) {
                input.getSelectionModel().clearSelection();
            }
        });

        output.focusedProperty().addListener(listener -> {
            if(!output.focusedProperty().get()) {
                output.getSelectionModel().clearSelection();
            }
        });

        filter.focusedProperty().addListener(listener -> {
            if(!filter.focusedProperty().get()) {
                filter.getSelectionModel().clearSelection();
            }
        });

        drag(filter);

        colorPicker.valueProperty().addListener(listener -> colorChange());

        colorPicker.setValue(Color.DARKRED);

        color = colorPicker.getValue();
    }

    private void colorChange() {
        color = colorPicker.getValue();
        if (!colorPicker.getCustomColors().contains(color)){
            colorPicker.getCustomColors().add(color);
        }
    }

    public void loadTreeItems(TreeItem<String> item, ObservableList<String> rootItems) {
        item.setExpanded(true);
        for (String itemString : rootItems) {
            item.getChildren().add(new TreeItem<>(itemString));
        }
    }

    private void makeListDraggable(TreeView<String> item) {
        item.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> cell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        if (item != null) {
                            super.updateItem(item, empty);
                            setText(item);
                        }
                    }
                };

                cell.setOnDragDetected(event -> {
                    if (!cell.isEmpty() && !ModuleTypes.getNameFromLong(cell.getItem()).equals("")) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent cc = new ClipboardContent();
                        cc.putString(cell.getItem());
                        db.setContent(cc);
                    }
                });
                cell.setOnDragDone(event -> {
                    if (onDragDone != null) {
                        onDragDone.accept(event);
                    }
                });
                return cell;
            }
        });
    }

    private void drag(TreeView<String> draggable) {
        makeListDraggable(draggable);
        input.relocate(0, colorPicker.getPrefHeight());
        if (rootInput.isExpanded()) {
            input.setPrefHeight((rootInput.getChildren().size() + 1) * 25);
        } else {
            input.setPrefHeight(26);
        }
        output.relocate(0, colorPicker.getPrefHeight()+input.getPrefHeight());
        if (rootOutput.isExpanded()) {
            output.setPrefHeight((rootOutput.getChildren().size() + 1) * 25);
        } else {
            output.setPrefHeight(26);
        }
        filter.relocate(0, colorPicker.getPrefHeight()+input.getPrefHeight() + output.getPrefHeight());
        if (rootFilter.isExpanded()) {
            filter.setPrefHeight((rootFilter.getChildren().size() + 1) * 25);
        } else {
            filter.setPrefHeight(26);
        }
    }

    public static Color getColor(){
        return color;
    }

}
