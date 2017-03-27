package level.tile;

import graphics.Screen;
import graphics.Sprite;

public class WoodTile extends Tile{
    public WoodTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen){
        screen.renderTile(x << 4 , y << 4, this);
    }
}
