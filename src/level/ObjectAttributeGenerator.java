package level;

import objects.*;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;

import java.util.Map;
import java.util.StringJoiner;

/**
 * A class to generate the file attribute for the provided object
 *
 * @author Kallum Jones 2005855
 */
public class ObjectAttributeGenerator {
    public static final String RAT_KEY = "rat";
    public static final String DEATH_RAT_KEY = "d_rat";
    public static final String BOMB_KEY = "bomb";
    public static final String GAS_KEY = "gas";
    public static final String STERILISATION_KEY = "sterile";
    public static final String POISON_KEY = "poison";
    public static final String SEX_CHANGE_KEY = "sex_ch";
    public static final String NO_ENTRY_SIGN_KEY = "xentry";

    /**
     * Empty private constructor method, preventing ObjectAttributeGenerator
     * from being instantiated as an object.
     */
    private ObjectAttributeGenerator() {
    }

    /**
     * A method to add the attribute string for the provided object to the
     * provided attribute map
     *
     * @param objectOnTile the object to add
     * @param attributes   the attribute map to add too
     */
    public static void addAttributeForObject(GameObject objectOnTile, Map<String, String> attributes) {
        if (objectOnTile instanceof PeacefulRat) {
            PeacefulRat peacefulRat = (PeacefulRat) objectOnTile;
            String attributeValue = generateRatAttribute(peacefulRat);

            attributes.put(RAT_KEY, attributeValue);
        } else if (objectOnTile instanceof DeathRat) {
            DeathRat deathRat = (DeathRat) objectOnTile;
            String attributeValue = generateDeathRatAttribute(deathRat);

            attributes.put(DEATH_RAT_KEY, attributeValue);
        } else if (objectOnTile instanceof Bomb) {
            Bomb bomb = (Bomb) objectOnTile;
            String attributeValue = generateBombAttribute(bomb);

            attributes.put(BOMB_KEY, attributeValue);
        } else if (objectOnTile instanceof Sterilisation) {
            Sterilisation sterilisation = (Sterilisation) objectOnTile;
            String attributeValue = generateSterilisationAttribute(sterilisation);

            attributes.put(STERILISATION_KEY, attributeValue);
        } else if (objectOnTile instanceof Poison) {
            Poison poison = (Poison) objectOnTile;
            String attributeValue = generatePoisonAttribute(poison);

            attributes.put(POISON_KEY, attributeValue);
        } else if (objectOnTile instanceof MaleSexChanger || objectOnTile instanceof FemaleSexChanger) {
            String attributeValue;
            if (objectOnTile instanceof MaleSexChanger) {
                MaleSexChanger maleSexChanger = (MaleSexChanger) objectOnTile;
                attributeValue = generateMaleSexChangeAttribute(maleSexChanger);
            } else {
                FemaleSexChanger femaleSexChanger = (FemaleSexChanger) objectOnTile;
                attributeValue = generateFemaleSexChangeAttribute(femaleSexChanger);
            }
            attributes.put(SEX_CHANGE_KEY, attributeValue);
        } else if (objectOnTile instanceof NoEntrySign) {
            NoEntrySign noEntrySign = (NoEntrySign) objectOnTile;
            String attributeValue = generateNoEntrySignAttribute(noEntrySign);

            attributes.put(NO_ENTRY_SIGN_KEY, attributeValue);
        } else if (objectOnTile instanceof Gas) {
            Gas gas = (Gas) objectOnTile;
            String attributeValue = generateGasAttribute(gas);

            attributes.put(GAS_KEY, attributeValue);
        }
    }

    /**
     * A method to generate the attribute string for Gas
     *
     * @param gas the gas
     * @return the attribute string for the provided gas
     */
    private static String generateGasAttribute(Gas gas) {
        StringJoiner attribute = new StringJoiner(" ");
        attribute.add(String.valueOf(gas.isActive()));

        return attribute.toString();
    }

    /**
     * A method to generate the attribute string for a NoEntrySign
     *
     * @param noEntrySign the no entry sign
     * @return the attribute string for the provided no entry sign
     */
    private static String generateNoEntrySignAttribute(NoEntrySign noEntrySign) {
        StringJoiner attribute = new StringJoiner(" ");

        attribute.add(String.valueOf(noEntrySign.getDamageDone()));
        attribute.add(String.valueOf(noEntrySign.getDurability()));

        return attribute.toString();
    }

    /**
     * A method to generate the attribute string for a FemaleSexChanger
     *
     * @param femaleSexChanger the female sex changer
     * @return the attribute string for the provided female sex changer
     */
    private static String generateFemaleSexChangeAttribute(FemaleSexChanger femaleSexChanger) {
        return "f";
    }

    /**
     * A method to generate the attribute for a MaleSexChanger
     *
     * @param maleSexChanger the male sex changer
     * @return the attribute string for the provided male sex changer
     */
    private static String generateMaleSexChangeAttribute(MaleSexChanger maleSexChanger) {
        return "m";
    }

    /**
     * A method to generate the attribute for a Poison object
     *
     * @param poison the poison
     * @return the attribute string for the provided poison object
     */
    private static String generatePoisonAttribute(Poison poison) {
        return "";
    }

    /**
     * A method to generate the attribute for a Sterilisation object
     *
     * @param sterilisation the sterilisation object
     * @return the attribute string for the provided sterilisation object.
     */
    private static String generateSterilisationAttribute(Sterilisation sterilisation) {
        StringJoiner attribute = new StringJoiner(" ");

        attribute.add(String.valueOf(sterilisation.getDuration()));
        attribute.add(String.valueOf(sterilisation.isActive()));

        return attribute.toString();
    }

    /**
     * A method to generate the attribute for a bomb
     *
     * @param bomb the bomb object
     * @return the attribute string for a bomb object
     */
    private static String generateBombAttribute(Bomb bomb) {
        StringJoiner attribute = new StringJoiner(" ");

        attribute.add(String.valueOf(bomb.getDuration()));
        attribute.add(String.valueOf(bomb.getTimeRemaining()));
        attribute.add(String.valueOf(bomb.isTimerStarted()));

        return attribute.toString();
    }

    /**
     * A method to generate the attribute for a death rat
     *
     * @param deathRat the death rat object
     * @return the attribute string for the death rat object
     */
    private static String generateDeathRatAttribute(DeathRat deathRat) {
        StringJoiner attribute = new StringJoiner(" ");

        attribute.add(
                LevelUtils.getStringFromDirection(
                        deathRat.getDirectionOfMovement()
                )
        );
        attribute.add(String.valueOf(deathRat.getNumberOfKills()));
        attribute.add(String.valueOf(deathRat.getNumberOfKills()));

        return attribute.toString();
    }

    /**
     * A method to generate the attribute for a peaceful rat
     *
     * @param peacefulRat the peaceful rat object
     * @return the attribute string for the peaceful rat object
     */
    private static String generateRatAttribute(PeacefulRat peacefulRat) {
        StringJoiner attribute = new StringJoiner(" ");

        attribute.add(String.valueOf(peacefulRat.isAdult()));
        attribute.add(String.valueOf(peacefulRat.isPregnant()));
        attribute.add(String.valueOf(peacefulRat.isSterile()));
        attribute.add(String.valueOf(peacefulRat.getGender()));
        attribute.add(String.valueOf(peacefulRat.getTimeToGiveBirth()));
        attribute.add(String.valueOf(peacefulRat.getTimeToDevelop()));
        attribute.add(
                LevelUtils.getStringFromDirection(
                        peacefulRat.getDirectionOfMovement()
                )
        );

        return attribute.toString();
    }

}
