package level;

import javafx.scene.image.Image;
import objects.*;
import objects.GameObject;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

import java.util.Scanner;

/**
 * A class to read attributes and form the GameObject they represent
 * @author Kallum Jones 2005855
 */
public class TileAttributeReader {
    /**
     * A method to get the GameObject that the provided attribute represents
     * @param attributeName The name of the attribute read from file
     * @param attributeValue The value of the attribute read from file
     * @param tile The tile this attribute was read from
     * @return The constructed GameObject
     */
    public static GameObject getObjectFromAttribute(String attributeName, String attributeValue, Tile tile) {
        switch (attributeName) {
            case "rat":
                return readPeacefulRat(attributeValue, tile);
            case "d_rat":
                return readDeathRat(attributeValue, tile);
            case "bomb":
                return readBomb(attributeValue, tile);
            case "gas":
                // TODO: ADD GAS return readGas(attributeValue, tile);
                break;
            case "sterile":
                //TODO :: ADD STERILE return readSterile(attributeValue, tile);
                break;
            case "poison":
                return new Poison(tile);
            case "sex_ch":
                return readSexChange(attributeValue, tile);
            case "xentry":
                return readNoEntrySign(attributeValue, tile);
        }
        throw new RuntimeException(String.format("Name: %s, Value: %s is an invalid attribute", attributeName, attributeValue));
    }

    /**
     * A method to construct a NoEntrySign with the provided data
     * @param attributeValue the data to construct the NoEntrySign with
     * @param tile the Tile the NoEntrySign is on
     * @return The constructed NoEntrySign
     */
    private static NoEntrySign readNoEntrySign(String attributeValue, Tile tile) {
        Scanner scanner = new Scanner(attributeValue);
        int damage = scanner.nextInt();
        int durability = scanner.nextInt();

        // TODO: fix path to image
        return new NoEntrySign(tile, new Image("path_to_image"), damage, durability);
    }

    /**
     * A method to construct the relevant SexChanger object from the provided data
     * @param attributeValue the data to construct the SexChanger with
     * @param tile The tile the SexChanger is on
     * @return the constructed SexChanger
     */
    private static GameObject readSexChange(String attributeValue, Tile tile) {
        switch (attributeValue) {
            case "m":
                return new MaleSexChanger(tile);
            case "f":
                return new FemaleSexChanger(tile);
        }
        throw new RuntimeException("An invalid sex change gender has been read");
    }

    /**
     * A method to construct a Bomb object with the provided data
     * @param attributeValue the data to construct the Bomb with
     * @param tile the tile the Bomb is on
     * @return the constructed Bomb
     */
    private static Bomb readBomb(String attributeValue, Tile tile) {
        return new Bomb(tile, Integer.parseInt(attributeValue), true);
    }

    /**
     * A method to construct a DeathRat with the provided attribute data
     * @param attributeValue the data to construct a DeathRat with
     * @param tile the tile the DeathRat is on
     * @return the constructed DeathRat
     */
    private static DeathRat readDeathRat(String attributeValue, Tile tile) {
        //TODO: provide valid icon

        Scanner scanner = new Scanner(attributeValue);
        int speed = scanner.nextInt();
        Direction direction = getDirectionFromString(scanner.next());
        int numOfKills = scanner.nextInt();
        int killsTarget = scanner.nextInt();

        return new DeathRat(tile, speed, direction,numOfKills, killsTarget);
    }

    /**
     * A method to construct a PeacefulRat with the provided attribute data
     * @param attributeValue The data to construct a PeacefulRat with
     * @param tile The tile the PeacefulRat is on
     * @return The constructed PeacefulRat
     */
    public static PeacefulRat readPeacefulRat(String attributeValue, Tile tile) {
        Scanner scanner = new Scanner(attributeValue);
        boolean adult = scanner.nextBoolean();
        boolean pregnant = scanner.nextBoolean();
        boolean sterile = scanner.nextBoolean();
        String gender = scanner.next();
        int timeToBirth = scanner.nextInt();
        int timeToDevelop = scanner.nextInt();
        int speed = scanner.nextInt();
        Direction direction = getDirectionFromString(scanner.next());
        return new PeacefulRat(tile, sterile, adult, pregnant, gender, timeToBirth, timeToDevelop, speed, direction);
    }

    /**
     * A method to return the relevant Direction enum from a String
     * @param direction the direction String
     * @return the parsed Direction enum
     */
    private static Direction getDirectionFromString(String direction) {
        switch (direction) {
            case "down":
                return Direction.DOWN;
            case "up":
                return Direction.UP;
            case "left":
                return Direction.LEFT;
            case "right":
                return Direction.RIGHT;
            default:
                throw new RuntimeException("An invalid direction was read from file");
        }
    }
}
