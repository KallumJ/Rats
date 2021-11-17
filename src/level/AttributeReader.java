package level;

import javafx.scene.image.Image;
import objects.*;
import objects.Object;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

import java.util.Scanner;

public class AttributeReader {
    public static Object getObjectFromAttribute(String attributeName, String attributeValue, Tile tile) {
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

    private static NoEntrySign readNoEntrySign(String attributeValue, Tile tile) {
        Scanner scanner = new Scanner(attributeValue);
        int damage = scanner.nextInt();
        int durability = scanner.nextInt();

        // TODO: fix path to image
        return new NoEntrySign(tile, new Image("path_to_image"), damage, durability);
    }

    private static Object readSexChange(String attributeValue, Tile tile) {
        switch (attributeValue) {
            case "m":
                return new MaleSexChanger(tile);
            case "f":
                return new FemaleSexChanger(tile);
        }
        throw new RuntimeException("An invalid sex change gender has been read");
    }


    private static Bomb readBomb(String attributeValue, Tile tile) {
        return new Bomb(tile, Integer.parseInt(attributeValue), true);
    }

    private static DeathRat readDeathRat(String attributeValue, Tile tile) {
        //TODO: provide valid icon

        Scanner scanner = new Scanner(attributeValue);
        int speed = scanner.nextInt();
        Direction direction = getDirectionFromString(scanner.nextLine());
        int numOfKills = scanner.nextInt();
        int killsTarget = scanner.nextInt();

        return new DeathRat(tile, new Image("path_to_image"), speed, direction,numOfKills, killsTarget);
    }

    public static PeacefulRat readPeacefulRat(String attributeValue, Tile tile) {
        Scanner scanner = new Scanner(attributeValue);
        boolean adult = scanner.nextBoolean();
        boolean pregnant = scanner.nextBoolean();
        int minBabies = scanner.nextInt();
        int maxBabies = scanner.nextInt();
        boolean sterile = scanner.nextBoolean();
        String gender = scanner.nextLine();
        int timeToBirth = scanner.nextInt();
        int timeToDevelop = scanner.nextInt();
        int speed = scanner.nextInt();
        Direction direction = getDirectionFromString(scanner.nextLine());
        return new PeacefulRat(tile, sterile, adult, pregnant, gender, timeToBirth, timeToDevelop, speed, direction);
    }

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
