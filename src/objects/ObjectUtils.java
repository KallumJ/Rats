package objects;

import level.LevelData;
import level.ObjectAttributeGenerator;
import objects.rats.DeathRat;
import tile.Tile;

/**
 * A class to provide utility methods for working with GameObjects
 *
 * @author Kallum Jones 2005855
 */
public class ObjectUtils {
    private static final String NO_ASSIGNED_IMAGE = "There is no image assigned to %s";
    private static final String NO_CONSTRUCTOR_ASSIGNED = "There is no object constructor assigned to %s";

    /**
     * A method to get the image URL for the provided object type
     * @param type the type of object
     * @return the object's image URL
     */
    public static String getObjectImageUrl(GameObjectType type) {
        switch (type) {
            case BOMB:
                return "file:resources/bomb.png";
            case STERILISATION:
                return "file:resources/sterilistation.png";
            case NO_ENTRY_SIGN:
                return "file:resources/noEntrySign.png";
            case POISON:
                return "file:resources/poison.png";
            case FEMALE_SEX_CHANGER:
                return "file:resources/femaleChange.png";
            case MALE_SEX_CHANGER:
                return "file:resources/maleChange.png";
            case DEATH_RAT:
                return "file:resources/deathRat.png";
            case GAS:
                return "file:resources/GAS.png";
            default:
                throw new IllegalArgumentException(
                        String.format(NO_ASSIGNED_IMAGE, type)
                );
        }
    }

    /**
     * A method to get the default object for the provided type
     * @param standingOn The tile the object is standing on
     * @param type the type of the object
     * @param levelData the level data for this object
     * @return The constructed object
     */
    public static GameObject getObjectFromType(Tile standingOn, GameObjectType type, LevelData levelData) {
        switch (type) {
            case BOMB:
                return new Bomb(
                        standingOn, Bomb.DEFAULT_LENGTH, false
                );
            case STERILISATION:
                return new Sterilisation(
                        standingOn, Sterilisation.DEFAULT_DURATION
                );
            case NO_ENTRY_SIGN:
                return new NoEntrySign(
                        standingOn, NoEntrySign.DEFAULT_DAMAGE_DONE,
                        NoEntrySign.DEFAULT_DURABILITY
                );
            case POISON:
                return new Poison(standingOn);
            case FEMALE_SEX_CHANGER:
                return new FemaleSexChanger(standingOn);
            case MALE_SEX_CHANGER:
                return new MaleSexChanger(standingOn);
            case DEATH_RAT:
                return new DeathRat(
                        standingOn, levelData.getLevelProperties().getDeathRatSpeed(),
                        DeathRat.DEFAULT_DIRECTION, DeathRat.DEFAULT_NUM_OF_KILLS,
                        DeathRat.DEFAULT_KILLS_TARGET
                );
            case GAS:
                return new Gas(standingOn,
                        Gas.DEFAULT_DURATION, Gas.DEFAULT_RANGE
                );
            default:
                throw new IllegalArgumentException(
                        String.format(NO_CONSTRUCTOR_ASSIGNED, type)
                );
        }
    }

    /*public static String getStringForItem(GameObjectType gameObjectType) {
        switch (gameObjectType) {
            case BOMB:
                return ObjectAttributeGenerator.BOMB_KEY;
            case DEATH_RAT:
                return ObjectAttributeGenerator.DEATH_RAT_KEY;
                case
        }
    }*/

}
