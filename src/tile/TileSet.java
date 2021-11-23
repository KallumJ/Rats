package tile;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model a set of tiles on the game board
 *
 * @author Kallum Jones 2005855
 */
public class TileSet {
    private final ArrayList<ArrayList<Tile>> tileSet = new ArrayList<>();

    /**
     * A method to put a tile in the set at the specified position
     * @param tileType The type of the tile to generate
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     */
    public void putTile(TileType tileType, int x, int y) {
        generateTileRowIfNotPresent(y);
        tileSet.get(y).add(x, new Tile(new TileLocation(x, y), tileType));
    }

    /**
     * A method to generate a row of tiles if it is not present already
     * @param y the y co-ordinate of the row
     */
    private void generateTileRowIfNotPresent(int y) {
        // If the provided co-ordinate is out of bounds, put a new row there
        try {
            tileSet.get(y);
        } catch (IndexOutOfBoundsException ex) {
            tileSet.add(y, new ArrayList<>());
        }
    }

    /**
     * A method to put a tile in the set at the specified position
     * @param tile The tile to insert
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     */
    public void putTile(Tile tile, int x, int y) {
        generateTileRowIfNotPresent(y);
        tileSet.get(y).add(x, tile);
    }

    /**
     * A method to get the tile at the specified position
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     * @return the Tile found at the specified co-ordinate, null if none found
     */
    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        try {
            return tileSet.get(y).get(x);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * A method to return a List of all the tiles in the set
     * @return a List of all the tiles in the set
     */
    public List<Tile> getAllTiles() {
        ArrayList<Tile> allTiles = new ArrayList<>();

        for (ArrayList<Tile> tileRow : tileSet) {
            allTiles.addAll(tileRow);
        }

        return allTiles;
    }

    /**
     * A method to get the height of the tile set
     * @return the height of the tile set
     */
    public int getHeight() {
        return tileSet.size();
    }

    /**
     * A method to get the width of the tile set
     * @return the width of the tile set
     */
    public int getWidth() {
        return tileSet.get(0).size();
    }
}
