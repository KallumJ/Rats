package objects;

/**
 * An interface to give objects that need to have their Timeline's stopped, a
 * stop method
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.21
 */
public interface ObjectStoppable {
	/**
	 * Stops any timelines running in this object
	 */
	void stop();
}
