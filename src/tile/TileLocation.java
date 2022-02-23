package tile;

/**
 * A class to model the location for a tile.
 *
 * @author Kallum Jones 2005855
 * @date 2022/02/20
 *
 */
public class TileLocation {
    private final int x;
    private final int y;

    /**
     * Constructs the location for a tile.
     *
     * @param x the x co-ordinate.
     * @param y the y co-ordinate.
     */
    public TileLocation(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Returns the x co-ordinate.
     *
     * @return the x co-ordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y co-ordinate.
     *
     * @return the y co-ordinate.
     */
    public int getY() {
        return y;
    }
}
