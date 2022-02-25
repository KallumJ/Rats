package display.menus.editor;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import objects.GameObjectType;
import util.TextUtils;

import java.util.HashSet;
import java.util.Iterator;

/**
 * A class to model a group of checkboxes to select what items are available in the level.
 */
public class ObjectSelectionGroup {

    private final VBox container;
    private final HashSet<GameObjectType> selectedObjects;

    /**
     * Constructs an ObjectSelectionGroup.
     */
    public ObjectSelectionGroup() {
        this.container = new VBox();
        this.selectedObjects = new HashSet<>();

        for (GameObjectType object : GameObjectType.values()) {
            if (!object.equals(GameObjectType.MALE_RAT) && !object.equals(GameObjectType.FEMALE_RAT)) {
                CheckBox checkBox = new CheckBox();
                checkBox.setText(TextUtils.removeUnderscores(object.toString()));
                checkBox.setTextFill(Color.WHITE);

                // All objects are selected by default
                selectedObjects.add(object);
                checkBox.setSelected(true);


                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    boolean selected = newValue;

                    // If this box is selected, add it to the set,
                    // if its deselected, remove it
                    if (selected) {
                        selectedObjects.add(object);
                    } else {
                        selectedObjects.remove(object);
                    }
                });
                container.getChildren().add(checkBox);
            }
        }
    }

    /**
     * A method to get the container with all the controls.
     *
     * @return the object selection group.
     */
    public VBox getObjectSelectionGroup() {
        return container;
    }

    /**
     * A method to set the CheckBox values.
     *
     * @param selectedObjects.
     */
    public void setSelectedObjectsValues(HashSet<GameObjectType> selectedObjects) {
        Iterator<Node> values = this.container.getChildren().iterator();
        while (values.hasNext()) {
            CheckBox checkBox = (CheckBox) values.next();
            checkBox.setSelected(false); // default to false
            Iterator<GameObjectType> objectTypes = selectedObjects.iterator();
            while (objectTypes.hasNext()) {
                String objectTypeName = objectTypes.next().toString().replace("_", " ");
                if (checkBox.getText().equals(objectTypeName)) {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    /**
     * A method to get the set of selected objects
     *
     * @return the set of selected objects
     */
    public HashSet<GameObjectType> getSelectedObjects() {
        return selectedObjects;
    }
}
