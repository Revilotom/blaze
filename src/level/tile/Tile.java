package level.tile;

import graphics.Screen;
import graphics.Sprite;

public class Tile {

    public final Sprite sprite;

    Tile(Sprite sprite){
        this.sprite = sprite;
    }

    public static final Tile grass = new GrassTile(Sprite.grass);
    public static final Tile flower = new FlowerTile(Sprite.flower);
    public static final Tile rock = new RockTile(Sprite.rock);
    public static final Tile voidTile = new VoidTile(Sprite.voidSprite);
    public static final Tile tree = new TreeTile(Sprite.tree);
    public static final Tile grass1 = new GrassTile(Sprite.grass1);
    public static final Tile orangeBricks = new BricksTile(Sprite.bricks);
    public static final Tile blueBricks = new BricksTile(Sprite.blueBricks);
    public static final Tile brownBricks = new BricksTile(Sprite.brownBricks);
    public static final Tile paleWood = new WoodTile(Sprite.planks);
    public static final Tile darkWood = new WoodTile(Sprite.darkWood);
    public static final Tile mud = new MudTile(Sprite.mud);
    public static final Tile moss = new GrassTile(Sprite.moss);

    public void render(int x, int y, Screen screen){

    }

    public boolean solid(){
        return false;
    }
}
