package tile;

import java.util.ArrayList;

public class TileSet {
    private final ArrayList<ArrayList<Tile>> tileSet = new ArrayList<>();

    public void putTile(TileType tileType, int x, int y) {
        try {
            tileSet.get(y);
        } catch (IndexOutOfBoundsException ex) {
            tileSet.add(y, new ArrayList<>());
        }

        tileSet.get(y).add(x, new Tile(new TileLocation(x, y), tileType));
    }

    public void putTile(Tile tile, int x, int y) {
        try {
            tileSet.get(y);
        } catch (IndexOutOfBoundsException ex) {
            tileSet.add(y, new ArrayList<>());
        }
        tileSet.get(y).add(x, tile);
    }

    public Tile getTile(int x, int y) {
        return tileSet.get(y).get(x);
    }

    public ArrayList<Tile> getAllTiles() {
        ArrayList<Tile> allTiles = new ArrayList<>();

        for (ArrayList<Tile> tileRow : tileSet) {
            allTiles.addAll(tileRow);
        }

        return allTiles;
    }
}
