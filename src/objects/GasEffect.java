package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.Rat;
import tile.Tile;
import tile.TileType;

/**
 * @author fahds
 */
public class GasEffect extends GameObject implements ObjectStoppable {

	private final int duration;
	private final Gas sourceGas;
	private Timeline disappearTimer;

	public GasEffect(Tile standingOn, int duration, Gas sourceGas) {
		super(standingOn);

		this.duration = duration;
		this.sourceGas = sourceGas;

		Image gasEffectImage =
				new Image(ObjectUtils.getObjectImageUrl(GameObjectType.GAS));

		if (standingOn.getTileType().equals(TileType.PATH)) {
			super.setIcon(gasEffectImage);
		} else {
			super.setIcon(null);
		}


		GameObject.getBoard().addObject(this);
	}

	/**
	 * A method to check when the gas effect stops
	 */
	public void disappear() {

		disappearTimer = new Timeline(new KeyFrame(Duration.seconds(duration),
				event -> removeEffect()));
		disappearTimer.play();
	}

	/**
	 * A method to check when the gas has been placed on the board
	 */
	public void enterGas(Rat rat) {
		if (!(sourceGas.getRatsInGas().contains(rat))) {
			sourceGas.startChoking(rat);
		}
	}

	/**
	 * Stops any timelines running in this object
	 */
	@Override
	public void stop() {
		if (disappearTimer != null) {
			disappearTimer.pause();
		}
	}

	/**
	 * A method to remove the effect of the gas
	 */
	private void removeEffect() {
		sourceGas.getGasEffects().remove(this);
		if (GameObject.getBoard() != null) {
			GameObject.getBoard().removeObject(this);
		}
	}
}
