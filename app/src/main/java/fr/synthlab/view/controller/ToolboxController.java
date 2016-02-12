package fr.synthlab.view.controller;

import fr.synthlab.model.module.ModuleType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ToolboxController implements Initializable {
    private static final Logger logger = Logger.getLogger(ToolboxController.class.getName());
    @FXML
    private TreeView<String> treeView;

    @FXML
    private TreeItem<String> treeItemRoot;

    @FXML
    private ColorPicker colorPicker;

    private static Color color;

    private Consumer<DragEvent> onDragDone = null;

    public void setOnDragDone(Consumer<DragEvent> onDragDone) {
        this.onDragDone = onDragDone;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        treeItemRoot.expandedProperty().addListener(listener -> makeListDraggable(treeView));

        TreeItem<String> rootInput = new TreeItem<>("Input");
        TreeItem<String> rootOutput= new TreeItem<>("Output ");
        TreeItem<String> rootFilter = new TreeItem<>("Filter");
        treeItemRoot.getChildren().addAll(rootInput, rootOutput, rootFilter);

        List<TreeItem<String>> list = new ArrayList<>();
        list.add(new TreeItem<>(ModuleType.VCOA.getLongName()));
        list.add(new TreeItem<>(ModuleType.BRUI.getLongName()));
        list.add(new TreeItem<>(ModuleType.KEYB.getLongName()));
        rootInput.getChildren().addAll(list);

        list.clear();
        list.add(new TreeItem<>(ModuleType.OUT.getLongName()));
        list.add(new TreeItem<>(ModuleType.SCOP.getLongName()));
        rootOutput.getChildren().addAll(list);

        list.clear();
        list.add(new TreeItem<>(ModuleType.VCA.getLongName()));
        list.add(new TreeItem<>(ModuleType.REP.getLongName()));
        list.add(new TreeItem<>(ModuleType.EG.getLongName()));
        list.add(new TreeItem<>(ModuleType.VCFLP.getLongName()));
        list.add(new TreeItem<>(ModuleType.VCFHP.getLongName()));
        list.add(new TreeItem<>(ModuleType.MIX.getLongName()));
        list.add(new TreeItem<>(ModuleType.SEQ.getLongName()));
        rootFilter.getChildren().addAll(list);

        treeView.setRoot(treeItemRoot);

        treeItemRoot.setExpanded(true);
        rootInput.setExpanded(true);
        rootOutput.setExpanded(true);
        rootFilter.setExpanded(true);
        treeView.setShowRoot(false);

        int length = 0;
        for(TreeItem item : treeItemRoot.getChildren()){
            length += (1+item.getChildren().size());
        }
        treeView.setPrefHeight(length * 25);

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

    private void makeListDraggable(TreeView<String> item) {
        item.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> cell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };
                cell.setOnDragDetected(event -> {
                    if (!cell.isEmpty() && !ModuleType.getNameFromLong(cell.getItem()).equals("")) {
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

    public static Color getColor(){
        return color;
    }

}
