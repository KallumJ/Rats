package objects;

import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import objects.rats.Rat;

public class ObjectInteractionChecker {

    public static void checkRatsMating(GameObject object1, GameObject object2) {

        // If both objects are peaceful rats
        if (object1 instanceof PeacefulRat && object2 instanceof PeacefulRat) {
            PeacefulRat rat1 = (PeacefulRat) object1;
            PeacefulRat rat2 = (PeacefulRat) object2;

            // If the rats are of different genders
            if (!(rat1.getGender().equals(rat2.getGender()))) {
                // If rat1 is male, he mates with rat2, else, the other way around
                if (rat1.getGender().equals("m")) {
                    rat1.mate(rat2);
                }
            }
        }

    }

    public static void checkDeathRat(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof DeathRat) {
            DeathRat deathRat = (DeathRat) firstObject;

            if (secondObject instanceof PeacefulRat || secondObject instanceof DeathRat) {
                Rat secondRat = (Rat) secondObject;

                deathRat.kill(secondRat);
            }
        }
    }

    public static void checkBomb(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof Bomb) {

            Bomb bomb = (Bomb) firstObject;
            bomb.activationOfBomb();
        }
    }

    public static void checkNoEntrySign(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof NoEntrySign) {
            if (secondObject instanceof PeacefulRat || secondObject instanceof DeathRat) {
                NoEntrySign noEntrySign = (NoEntrySign) firstObject;
                Rat victim = (Rat) secondObject;
                noEntrySign.activation(victim);
            }
        }
    }

    public static void checkFemaleSexChanger(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof FemaleSexChanger) {
            if (secondObject instanceof PeacefulRat) {
                FemaleSexChanger femaleSexChanger = (FemaleSexChanger) firstObject;
                PeacefulRat rat = (PeacefulRat) secondObject;

                femaleSexChanger.activationOfFemaleSexChanger(rat);
            }
        }

    }

    public static void checkMaleSexChanger(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof MaleSexChanger) {
            if (secondObject instanceof PeacefulRat) {

                MaleSexChanger maleSexChanger = (MaleSexChanger) firstObject;
                PeacefulRat rat = (PeacefulRat) secondObject;

                maleSexChanger.activation(rat);
            }
        }

    }

    public static void checkPoison(GameObject firstObject, GameObject secondObject) {
        if (firstObject instanceof Poison) {
            if (secondObject instanceof Rat) {
                Poison poison = (Poison) firstObject;
                Rat rat = (Rat) secondObject;

                poison.activation(rat);
            }
        }

    }
}
