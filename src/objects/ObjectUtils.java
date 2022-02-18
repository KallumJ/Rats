package objects;

import level.LevelData;
import level.ObjectAttributeGenerator;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import objects.rats.ZombieRat;
import tile.Direction;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to provide utility methods for working with GameObjects.
 *
 * @author Kallum Jones 2005855
 */
public class ObjectUtils {
	private static final String NO_ASSIGNED_IMAGE = "There is no image " +
			"assigned to %s";
	private static final String NO_CONSTRUCTOR_ASSIGNED = "There is no object "
			+ "constructor assigned to %s";
	private static final String NO_STRING_ASSIGNED = "There is no string " +
			"assigned for object type %s";
	private static final String NO_TYPE_ASSIGNED = "There is no type assigned "
			+ "for string %s";

	/**
	 * Empty private constructor method, preventing ObjectUtils from being
	 * instantiated as an object.
	 */
	private ObjectUtils() {
	}

	/**
	 * A method to get the image URL for the provided object type
	 *
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
				return "file:resources/GasGif.gif";
			case PORTAL:
				return "file:resources/portal.gif";
			case ZOMBIE_RAT:
				return "file:resources/zombieRat.png";
			case MALE_RAT:
				return "file:resources/maleRat.png";
			case FEMALE_RAT:
				return "file:resources/femaleRat.png";
			default:
				throw new IllegalArgumentException(String.format(NO_ASSIGNED_IMAGE, type));
		}
	}

	/**
	 * A method to get the default object for the provided type
	 *
	 * @param standingOn The tile the object is standing on
	 * @param type       the type of the object
	 * @param levelData  the level data for this object
	 * @return The constructed object
	 */
	public static GameObject getObjectFromType(Tile standingOn,
											   GameObjectType type,
											   LevelData levelData) {
		switch (type) {
			case BOMB:
				return new Bomb(standingOn, Bomb.DEFAULT_DURATION,
						Bomb.DEFAULT_DURATION, false);
			case STERILISATION:
				return new Sterilisation(standingOn,
						Sterilisation.DEFAULT_DURATION, false);
			case NO_ENTRY_SIGN:
				return new NoEntrySign(standingOn,
						NoEntrySign.DEFAULT_DAMAGE_DONE,
						NoEntrySign.DEFAULT_DURABILITY);
			case POISON:
				return new Poison(standingOn);
			case FEMALE_SEX_CHANGER:
				return new FemaleSexChanger(standingOn);
			case MALE_SEX_CHANGER:
				return new MaleSexChanger(standingOn);
			case DEATH_RAT:
				return new DeathRat(standingOn, levelData.getLevelProperties()
						.getDeathRatSpeed(), DeathRat.DEFAULT_DIRECTION,
						DeathRat.DEFAULT_NUM_OF_KILLS,
						DeathRat.DEFAULT_KILLS_TARGET);
			case GAS:
				return new Gas(standingOn, Gas.DEFAULT_ACTIVATION);
			case PORTAL:
				return new Portal(standingOn);
			case FEMALE_RAT:
				return new PeacefulRat(standingOn, false, true, false, "f", 9, 15, 1000, Direction.UP);
			case MALE_RAT:
				return new PeacefulRat(standingOn, false, true, false, "m", 9, 15, 1000, Direction.UP);
			case ZOMBIE_RAT:
				return new ZombieRat(standingOn,1000,Direction.UP,20);
			default:
				throw new IllegalArgumentException(String.format(NO_CONSTRUCTOR_ASSIGNED, type));
		}
	}

	/**
	 * A method to get the string for a given item
	 *
	 * @param gameObjectType the item type
	 * @return the string
	 */
	public static String getStringForItem(GameObjectType gameObjectType) {
		switch (gameObjectType) {
			case BOMB:
				return ObjectAttributeGenerator.BOMB_KEY;
			case DEATH_RAT:
				return ObjectAttributeGenerator.DEATH_RAT_KEY;
			case GAS:
				return ObjectAttributeGenerator.GAS_KEY;
			case POISON:
				return ObjectAttributeGenerator.POISON_KEY;
			case MALE_SEX_CHANGER:
				return "msex";
			case STERILISATION:
				return ObjectAttributeGenerator.STERILISATION_KEY;
			case NO_ENTRY_SIGN:
				return ObjectAttributeGenerator.NO_ENTRY_SIGN_KEY;
			case FEMALE_SEX_CHANGER:
				return "fsex";
			case PORTAL:
				return ObjectAttributeGenerator.PORTAL_KEY;
			case ZOMBIE_RAT:
				return ObjectAttributeGenerator.ZOMBIE_RAT_KEY;
			default:
				throw new IllegalArgumentException(String.format(NO_STRING_ASSIGNED, gameObjectType));
		}
	}

	/**
	 * A method to get the item type for a given string
	 *
	 * @param itemString the string
	 * @return the item type
	 */
	public static GameObjectType getTypeFromString(String itemString) {
		switch (itemString) {
			case ObjectAttributeGenerator.BOMB_KEY:
				return GameObjectType.BOMB;
			case ObjectAttributeGenerator.DEATH_RAT_KEY:
				return GameObjectType.DEATH_RAT;
			case ObjectAttributeGenerator.GAS_KEY:
				return GameObjectType.GAS;
			case "msex":
				return GameObjectType.MALE_SEX_CHANGER;
			case ObjectAttributeGenerator.STERILISATION_KEY:
				return GameObjectType.STERILISATION;
			case ObjectAttributeGenerator.NO_ENTRY_SIGN_KEY:
				return GameObjectType.NO_ENTRY_SIGN;
			case "fsex":
				return GameObjectType.FEMALE_SEX_CHANGER;
			case ObjectAttributeGenerator.POISON_KEY:
				return GameObjectType.POISON;
			default:
				throw new IllegalArgumentException(String.format(NO_TYPE_ASSIGNED, itemString));
		}
	}

	/**
	 * Returns a list of all objects that are items in game (i.e, no male or female rats)
	 * @return a list of all objects that are items in game
	 */
	public static GameObjectType[] getAllObjectsList() {
		List<GameObjectType> objects = new ArrayList<>();

		for (GameObjectType object : GameObjectType.values()) {
			if (object != GameObjectType.MALE_RAT && object != GameObjectType.FEMALE_RAT) {
				objects.add(object);
			}
		}

		return objects.toArray(new GameObjectType[0]);
	}
}
