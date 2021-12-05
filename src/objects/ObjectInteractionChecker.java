package objects;

import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import objects.rats.Rat;

/**
 * A class to check for interactions between 2 provided objects
 *
 * @author Kallum Jones 2005855
 */
public class ObjectInteractionChecker {

	/**
	 * Empty private constructor method, preventing ObjectInteractionChecker
	 * from being instantiated as an object.
	 */
	private ObjectInteractionChecker() {
	}

	/**
	 * A method to check whether the two provided objects are 2 rats mating
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkRatsMating(GameObject firstObject,
									   GameObject secondObject) {

		// If both objects are peaceful rats
		if (firstObject instanceof PeacefulRat && secondObject instanceof PeacefulRat) {
			PeacefulRat rat1 = (PeacefulRat) firstObject;
			PeacefulRat rat2 = (PeacefulRat) secondObject;

			// If the rats are of different genders
			if (!(rat1.getGender().equals(rat2.getGender()))) {
				// If rat1 is male, he mates with rat2, else, the other way
				// around
				if (rat1.getGender().equals("m")) {
					rat1.mate(rat2);
				}
			}
		}

	}

	/**
	 * A method to check whether the two provided objects is a death rat
	 * killing
	 * another rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkDeathRat(GameObject firstObject,
									 GameObject secondObject) {
		if (firstObject instanceof DeathRat) {
			DeathRat deathRat = (DeathRat) firstObject;

			if (secondObject instanceof PeacefulRat || secondObject instanceof DeathRat) {
				Rat secondRat = (Rat) secondObject;

				deathRat.kill(secondRat);
			}
		}
	}

	/**
	 * A method to check whether the two provided objects is a bomb being
	 * activated
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkBomb(GameObject firstObject,
								 GameObject secondObject) {
		if (firstObject instanceof Bomb) {
			if (secondObject instanceof Rat) {

				Bomb bomb = (Bomb) firstObject;
				bomb.activationOfBomb();
			}
		}
	}

	/**
	 * A method to check whether the two provided objects is a NoEntrySign
	 * blocking a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkNoEntrySign(GameObject firstObject,
										GameObject secondObject) {
		if (firstObject instanceof NoEntrySignCounter) {
			if (secondObject instanceof Rat) {
				NoEntrySignCounter noEntrySignEffect =
						(NoEntrySignCounter) firstObject;
				Rat victim = (Rat) secondObject;
				noEntrySignEffect.blockPath(victim);
			}
		}
	}

	/**
	 * A method to check whether the two provided objects is a female sex
	 * changer activating on a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkFemaleSexChanger(GameObject firstObject,
											 GameObject secondObject) {
		if (firstObject instanceof FemaleSexChanger) {
			if (secondObject instanceof PeacefulRat) {
				FemaleSexChanger femaleSexChanger =
						(FemaleSexChanger) firstObject;
				PeacefulRat rat = (PeacefulRat) secondObject;

				femaleSexChanger.activationOfFemaleSexChanger(rat);
			}
		}

	}

	/**
	 * A method to check whether the two provided objects is a male sex changer
	 * activating on a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkMaleSexChanger(GameObject firstObject,
										   GameObject secondObject) {
		if (firstObject instanceof MaleSexChanger) {
			if (secondObject instanceof PeacefulRat) {

				MaleSexChanger maleSexChanger = (MaleSexChanger) firstObject;
				PeacefulRat rat = (PeacefulRat) secondObject;

				maleSexChanger.beMale(rat);
			}
		}

	}

	/**
	 * A method to check whether the two provided objects is a poison killing a
	 * rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkPoison(GameObject firstObject,
								   GameObject secondObject) {
		if (firstObject instanceof Poison) {
			if (secondObject instanceof Rat) {
				Poison poison = (Poison) firstObject;
				Rat rat = (Rat) secondObject;

				poison.givePoison(rat);
			}
		}

	}

	/**
	 * A method to check whether the two provided objects is a sterilisation
	 * sterilising a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkSterilisation(GameObject firstObject,
										  GameObject secondObject) {
		if (firstObject instanceof Sterilisation) {
			if (secondObject instanceof PeacefulRat) {
				Sterilisation sterilisation = (Sterilisation) firstObject;
				PeacefulRat rat = (PeacefulRat) secondObject;

				sterilisation.beSterile(rat);
			}
		}

	}

	/**
	 * A method to check whether the two provided objects is a sterilisation
	 * effect sterilising a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkSterilisationEffect(GameObject firstObject,
												GameObject secondObject) {
		if (firstObject instanceof SterilisationEffect) {
			if (secondObject instanceof PeacefulRat) {
				SterilisationEffect sterilisationEffect =
						(SterilisationEffect) firstObject;
				PeacefulRat rat = (PeacefulRat) secondObject;

				sterilisationEffect.beSterile(rat);
			}
		}
	}

	/**
	 * A method to check whether the two provided objects is a gas and a rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkGas(GameObject firstObject,
								GameObject secondObject) {
		if (firstObject instanceof Gas) {
			if (secondObject instanceof Rat) {
				Gas gas = (Gas) firstObject;

				gas.activateGas();
			}
		}
	}

	/**
	 * A method to check whether the two provided objects is a gasEffect and a
	 * rat
	 *
	 * @param firstObject  the first object
	 * @param secondObject the second object
	 */
	public static void checkGasEffect(GameObject firstObject,
									  GameObject secondObject) {
		if (firstObject instanceof GasEffect) {
			if (secondObject instanceof Rat) {
				GasEffect gasEffect = (GasEffect) firstObject;
				Rat rat = (Rat) secondObject;

				gasEffect.enterGas(rat);
			}
		}
	}

}
