package level.tile;

public class TileCoordinate {

    private int x, y;

    public TileCoordinate(int y){
        int TILE_SIZE = 16;
        this.x = 24 * TILE_SIZE;
        this.y = 64 * TILE_SIZE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[] xy(){
        int[] r = new int[2];
        r[0] = x;
        r[1] = y;
        return r;
    }
}

