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
import javafx.scene.input.DragEvent;
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
                ModuleEnum.VCOA.getLongName(),
                ModuleEnum.BRUI.getLongName(),
                ModuleEnum.KEYB.getLongName(),
                ModuleEnum.SEQ.getLongName()
                );

        loadTreeItems(rootInput, items);
        items = FXCollections.observableArrayList(
                ModuleEnum.OUT.getLongName(),
                ModuleEnum.SCOP.getLongName()
        );
        loadTreeItems(rootOutput, items);

        items = FXCollections.observableArrayList(
                ModuleEnum.VCA.getLongName(),
                ModuleEnum.REP.getLongName(),
                ModuleEnum.EG.getLongName(),
                ModuleEnum.VCFLP.getLongName(),
                ModuleEnum.VCFHP.getLongName(),
                ModuleEnum.MIX.getLongName()
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
                    if (!cell.isEmpty() && !ModuleEnum.getNameFromLong(cell.getItem()).equals("")) {
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
        input.relocate(0, 0);
        if (rootInput.isExpanded()) {
            input.setPrefHeight((rootInput.getChildren().size() + 1) * 25);
        } else {
            input.setPrefHeight(25);
        }
        output.relocate(0, input.getPrefHeight());
        if (rootOutput.isExpanded()) {
            output.setPrefHeight((rootOutput.getChildren().size() + 1) * 25);
        } else {
            output.setPrefHeight(25);
        }
        filter.relocate(0, input.getPrefHeight() + output.getPrefHeight());
        if (rootFilter.isExpanded()) {
            filter.setPrefHeight((rootFilter.getChildren().size() + 1) * 25);
        } else {
            filter.setPrefHeight(25);
        }
    }

}
