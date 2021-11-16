package tile;

import org.w3c.dom.NamedNodeMap;

import java.util.Map;

/**
 *
 * @author fahds
 */
public class Tile {
    private TileLocation tileLocation;
    private TileType tileType;
    private boolean isTraversable;
    private boolean hideRatsWithin;
    private NamedNodeMap initialAttributes;

    public Tile(TileLocation tileLocation, TileType tileType) {
        this.tileLocation = tileLocation;
        this.tileType = tileType;

        this.isTraversable = this.tileType != TileType.GRASS;
        this.hideRatsWithin = this.tileType == TileType.TUNNEL;
    }

    public TileLocation getTileLocation() {
        return tileLocation;
    }

    public Tile getAdjacentTile(Direction x){
        return this;
    }

    public TileType getTileType() {
        return tileType;
    }

    public Boolean isTraversable() {
         return isTraversable;
    }

    public double getCenterX() {
        return this.getTileLocation().getX();
    }

    public double getCenterY() {
        return this.getTileLocation().getY();
    }

    public NamedNodeMap getInitialAttributes() {
        return this.initialAttributes;
    }

    public void setInitialAttributes(NamedNodeMap initialAttributes) {
        this.initialAttributes = initialAttributes;
    }

    public boolean hasInitalAttributes() {
        return this.initialAttributes != null;
    }
}
