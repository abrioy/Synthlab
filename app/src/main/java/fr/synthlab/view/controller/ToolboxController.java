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

/**
 * controller for toolbox.
 */
public class ToolboxController implements Initializable {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ToolboxController.class.getName());
    /**
     * tree for module.
     */
    @FXML
    private TreeView<String> treeView;
    /**
     * tree item for module.
     */
    @FXML
    private TreeItem<String> treeItemRoot;
    /**
     * choose color for next cable.
     */
    @FXML
    private ColorPicker colorPicker;

    /**
     * current color for next cable.
     */
    private static Color color = Color.BLACK;

    /**
     * event to manage drag and drop module.
     */
    private Consumer<DragEvent> onDragDone = null;

    /**
     * setter onDragDone module.
     * @param newOnDragDone to set
     */
    public final void setOnDragDone(final Consumer<DragEvent> newOnDragDone) {
        onDragDone = newOnDragDone;
    }

    @Override
    public final void initialize(
            final URL location, final ResourceBundle resources) {
        treeItemRoot.expandedProperty().addListener(
                listener -> makeListDraggable(treeView));

        TreeItem<String> rootInput = new TreeItem<>("Input");
        TreeItem<String> rootOutput = new TreeItem<>("Output");
        TreeItem<String> rootFilter = new TreeItem<>("Filter");
        treeItemRoot.getChildren().addAll(rootInput, rootOutput, rootFilter);

        List<TreeItem<String>> list = new ArrayList<>();
        list.add(new TreeItem<>(ModuleType.VCOA.getLongName()));
        list.add(new TreeItem<>(ModuleType.BRUI.getLongName()));
        list.add(new TreeItem<>(ModuleType.KEYB.getLongName()));
        list.add(new TreeItem<>(ModuleType.SEQ.getLongName()));
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
        rootFilter.getChildren().addAll(list);

        treeView.setRoot(treeItemRoot);

        treeItemRoot.setExpanded(true);
        rootInput.setExpanded(true);
        rootOutput.setExpanded(true);
        rootFilter.setExpanded(true);
        treeView.setShowRoot(false);

        int length = 0;
        for (TreeItem item : treeItemRoot.getChildren()) {
            length += (1 + item.getChildren().size());
        }
        treeView.setPrefHeight(length * 25);

        colorPicker.valueProperty().addListener(listener -> colorChange());

        colorPicker.setValue(Color.DARKRED);

        color = colorPicker.getValue();
    }

    /**
     * change color for next cable.
     */
    private void colorChange() {
        color = colorPicker.getValue();
        if (!colorPicker.getCustomColors().contains(color)) {
            colorPicker.getCustomColors().add(color);
        }
    }

    /**
     * make tree item drag and drop.
     * @param item item to make drag and drop
     */
    private void makeListDraggable(final TreeView<String> item) {
        item.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(
                    final TreeView<String> stringTreeView) {
                TreeCell<String> cell = new TreeCell<String>() {
                    protected void updateItem(
                            final String item, final boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };
                cell.setOnDragDetected(event -> {
                    if (!cell.isEmpty()
                            && !ModuleType.getNameFromLong(
                            cell.getItem()).equals("")) {
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

    /**
     * @return color for next cable
     */
    public static Color getColor() {
        return color;
    }
}
