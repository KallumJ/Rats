package level;

import objects.*;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

import java.util.Scanner;

/**
 * A class to read attributes and form the GameObject they represent
 *
 * @author Kallum Jones 2005855
 */
public class TileAttributeReader {
    private static final String INVALID_ATTRIBUTE = "Name: %s, Value: %s is an invalid attribute";
    private static final String INVALID_SEX_CHANGER = "An invalid sex change gender has been read";
    private static final String INVALID_GENDER = "%s is an invalid gender";
    private static final String INVALID_DIRECTION = "%s is an invalid direction";

    /**
     * A method to get the GameObject that the provided attribute represents
     *
     * @param attributeName  The name of the attribute read from file
     * @param attributeValue The value of the attribute read from file
     * @param tile           The tile this attribute was read from
     * @return The constructed GameObject
     */
    public static GameObject getObjectFromAttribute(String attributeName, String attributeValue, Tile tile, LevelProperties levelProperties) {
        switch (attributeName) {
            case ObjectAttributeGenerator.RAT_KEY:
                return readPeacefulRat(attributeValue, tile, levelProperties);
            case ObjectAttributeGenerator.DEATH_RAT_KEY:
                return readDeathRat(attributeValue, tile, levelProperties);
            case ObjectAttributeGenerator.BOMB_KEY:
                return readBomb(attributeValue, tile);
            case ObjectAttributeGenerator.GAS_KEY:
                // TODO: ADD GAS return readGas(attributeValue, tile);
                break;
            case ObjectAttributeGenerator.STERILISATION_KEY:
                return readSterile(attributeValue, tile);
            case ObjectAttributeGenerator.POISON_KEY:
                return new Poison(tile);
            case ObjectAttributeGenerator.SEX_CHANGE_KEY:
                return readSexChange(attributeValue, tile);
            case ObjectAttributeGenerator.NO_ENTRY_SIGN_KEY:
                return readNoEntrySign(attributeValue, tile);
        }
        throw new RuntimeException(
                String.format(
                        INVALID_ATTRIBUTE, attributeName, attributeValue
                )
        );
    }

    private static GameObject readSterile(String attributeValue, Tile tile) {
        return new Sterilisation(tile, Integer.parseInt(attributeValue));
    }

    /**
     * A method to construct a NoEntrySign with the provided data
     *
     * @param attributeValue the data to construct the NoEntrySign with
     * @param tile           the Tile the NoEntrySign is on
     * @return The constructed NoEntrySign
     */
    private static NoEntrySign readNoEntrySign(String attributeValue, Tile tile) {
        Scanner scanner = new Scanner(attributeValue);
        int damage = scanner.nextInt();
        int durability = scanner.nextInt();

        return new NoEntrySign(tile, damage, durability);
    }

    /**
     * A method to construct the relevant SexChanger object from the provided data
     *
     * @param attributeValue the data to construct the SexChanger with
     * @param tile           The tile the SexChanger is on
     * @return the constructed SexChanger
     */
    private static GameObject readSexChange(String attributeValue, Tile tile) {
        switch (attributeValue) {
            case "m":
                return new MaleSexChanger(tile);
            case "f":
                return new FemaleSexChanger(tile);
        }
        throw new RuntimeException(INVALID_SEX_CHANGER);
    }

    /**
     * A method to construct a Bomb object with the provided data
     *
     * @param attributeValue the data to construct the Bomb with
     * @param tile           the tile the Bomb is on
     * @return the constructed Bomb
     */
    private static Bomb readBomb(String attributeValue, Tile tile) {
        return new Bomb(tile, Integer.parseInt(attributeValue), false);
    }

    /**
     * A method to construct a DeathRat with the provided attribute data
     *
     * @param attributeValue the data to construct a DeathRat with
     * @param tile           the tile the DeathRat is on
     * @return the constructed DeathRat
     */
    private static DeathRat readDeathRat(String attributeValue, Tile tile, LevelProperties levelProperties) {
        Scanner scanner = new Scanner(attributeValue);
        Direction direction = getDirectionFromString(scanner.next());
        int numOfKills = scanner.nextInt();
        int killsTarget = scanner.nextInt();

        return new DeathRat(
                tile, levelProperties.getDeathRatSpeed(),
                direction, numOfKills, killsTarget
        );
    }

    /**
     * A method to construct a PeacefulRat with the provided attribute data
     *
     * @param attributeValue The data to construct a PeacefulRat with
     * @param tile           The tile the PeacefulRat is on
     * @return The constructed PeacefulRat
     */
    public static PeacefulRat readPeacefulRat(String attributeValue, Tile tile, LevelProperties levelProperties) {
        Scanner scanner = new Scanner(attributeValue);
        boolean adult = scanner.nextBoolean();
        boolean pregnant = scanner.nextBoolean();
        boolean sterile = scanner.nextBoolean();
        String gender = scanner.next();

        if (!(gender.equals("f") || gender.equals("m"))) {
            throw new IllegalArgumentException(
                    String.format(
                            INVALID_GENDER, gender
                    )
            );
        }

        int timeToBirth = scanner.nextInt();
        int timeToDevelop = scanner.nextInt();
        Direction direction = getDirectionFromString(scanner.next());

        // If the rat is an adult, give them adult speed, if not, baby speed
        int speed = adult ?
                levelProperties.getAdultRatSpeed() :
                levelProperties.getBabyRatSpeed();

        return new PeacefulRat(tile, sterile, adult, pregnant, gender,
                timeToBirth, timeToDevelop, speed, direction
        );
    }

    /**
     * A method to return the relevant Direction enum from a String
     *
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
                throw new RuntimeException(INVALID_DIRECTION);
        }
    }
}
