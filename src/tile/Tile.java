package tile;

import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent a Tile on the board
 *
 * @author Kallum
 * @date 2022/02/18
 */
public class Tile {
    public final static int TILE_SIZE = 50;

    private final TileLocation tileLocation;
    private final TileType tileType;
    private final boolean hideRatsWithin;
    private final Map<Direction, Tile> adjacentTiles;
    private boolean isTraversable;
    private NamedNodeMap initialAttributes;

    /**
     * Constructs a Tile object with the provided data
     *
     * @param tileLocation the tile location, encoded as TileLocation
     * @param tileType     the type of the tile, as a TileType enum
     */
    public Tile(TileLocation tileLocation, TileType tileType) {
        this.tileLocation = tileLocation;
        this.tileType = tileType;

        this.isTraversable = this.tileType != TileType.GRASS;
        this.hideRatsWithin = this.tileType == TileType.TUNNEL;

        this.adjacentTiles = new HashMap<>();
    }

    /**
     * Returns the location of the tile
     *
     * @return the location of the tile as a TileLocation
     */
    public TileLocation getTileLocation() {
        return tileLocation;
    }

    /**
     * Returns the adjacent Tile
     *
     * @param direction the direction to check for an adjacent tile
     * @return The found tile
     */
    public Tile getAdjacentTile(Direction direction) {
        return adjacentTiles.get(direction);
    }

    public void setAdjacentTileIfPresent(Direction direction, Tile tile) {
        if (tile != null) {
            adjacentTiles.put(direction, tile);
        }
    }

    /**
     * Returns the tile type of this tile
     *
     * @return the tile type of this tile as a TileType enum
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Returns whether this tile is traversable
     *
     * @return true or false
     */
    public boolean isTraversable() {
        return isTraversable;
    }

    public void setTraversable(boolean traversable) {
        this.isTraversable = traversable;
    }

    /**
     * Returns this tiles X location
     *
     * @return this tiles X location
     */
    public double getX() {
        return this.getTileLocation().getX();
    }

    /**
     * Returns this tiles Y location
     *
     * @return this tiles Y location
     */
    public double getY() {
        return this.getTileLocation().getY();
    }

    /**
     * Returns this Tile's initial attributes
     *
     * @return this Tile's initial attributes
     */
    public NamedNodeMap getInitialAttributes() {
        return this.initialAttributes;
    }

    /**
     * Sets this Tile's initial attributes
     *
     * @param initialAttributes this initial attributes to set for this Tile
     */
    public void setInitialAttributes(NamedNodeMap initialAttributes) {
        this.initialAttributes = initialAttributes;
    }

    /**
     * Returns whether this Tile has initial attributes
     *
     * @return true or false
     */
    public boolean hasInitalAttributes() {
        return this.initialAttributes != null;
    }

    /**
     * Returns whether to hide rats within this tile or not
     *
     * @return true or false
     */
    public boolean doHideRatsWithin() {
        return hideRatsWithin;
    }
}
