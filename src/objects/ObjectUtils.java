package objects;

import level.LevelData;
import objects.rats.DeathRat;
import tile.Direction;
import tile.Tile;

public class ObjectUtils {

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
            default:
                throw new IllegalArgumentException("There is no image assigned to " + type);
        }
    }

    public static GameObject getObjectFromType(Tile standingOn, GameObjectType type, LevelData levelData) {
        switch (type) {
            case BOMB:
                return new Bomb(standingOn, Bomb.DEFAULT_LENGTH, false);
            case STERILISATION:
                return new Sterilisation(standingOn, Sterilisation.DEFAULT_DURATION);
            case NO_ENTRY_SIGN:
                return new NoEntrySign(standingOn, NoEntrySign.DEFAULT_DAMAGE_DONE, NoEntrySign.DEFAULT_DURABILITY);
            case POISON:
                return new Poison(standingOn);
            case FEMALE_SEX_CHANGER:
                return new FemaleSexChanger(standingOn);
            case MALE_SEX_CHANGER:
                return new MaleSexChanger(standingOn);
            case DEATH_RAT:
                return new DeathRat(standingOn, levelData.getLevelProperties().getDeathRatSpeed(), DeathRat.DEFAULT_DIRECTION, DeathRat.DEFAULT_NUM_OF_KILLS, DeathRat.DEFAULT_KILLS_TARGET);
            default:
                throw new IllegalArgumentException("There is no object constructor assigned to " + type);
        }
    }

}
