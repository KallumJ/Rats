package tile;

/**
 *
 * @author fahds
 */
public class Tile {
    private TileLocation tileLocation;
    private TileType tileType;
    private boolean isTraversable;
    private boolean hideRatsWithin;

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
    
}
