package graphics;

import entity.projectile.FirstProjectile;

public class Sprite {

    public final int SIZE;


    private int height,width;
    private int x, y;
    public int[] pixels;
    protected SpriteSheet sheet;
    // Environment

    public static final Sprite grass = new Sprite("res/blocks/grass_top.png");
    public static final Sprite flower= new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static final Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static final Sprite tree = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static final Sprite grass1 = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static final Sprite bricks =  new Sprite("res/blocks/brick.png");
    public static final Sprite blueBricks =  new Sprite("res/blocks/stonebrick.png");;
    public static final Sprite brownBricks =  new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite paleWood =  new Sprite(16, 3, 1, SpriteSheet.tiles);
    public static final Sprite darkWood =  new Sprite(16, 4, 1, SpriteSheet.tiles);
    public static final Sprite mud =  new Sprite(16, 5, 1, SpriteSheet.tiles);
    public static final Sprite moss =  new Sprite(16, 5, 0, SpriteSheet.tiles);
    public static final Sprite voidSprite = new Sprite(0);

    public static final Sprite planks = new Sprite("res/blocks/sanity/spruce_planks.png");

    // Player

    public static final Sprite playerUp = new Sprite(32, 5, 0, SpriteSheet.tiles);
    public static final Sprite playerDown = new Sprite(32, 3, 0, SpriteSheet.tiles);
    public static final Sprite playerLeft = new Sprite(32, 4, 0, SpriteSheet.tiles);
    public static final Sprite playerRight = flip(playerLeft);

    public static final Sprite playerUp1 = new Sprite(32, 5, 1, SpriteSheet.tiles);
    public static final Sprite playerDown1 = new Sprite(32, 3, 1, SpriteSheet.tiles);
    public static final Sprite playerLeft1 = new Sprite(32, 4, 1, SpriteSheet.tiles);
    public static final Sprite playerRight1 = flip(playerLeft1);

    public static final Sprite playerUp2 = new Sprite(32, 5, 2, SpriteSheet.tiles);
    public static final Sprite playerDown2 = new Sprite(32, 3, 2, SpriteSheet.tiles);
    public static final Sprite playerLeft2 = new Sprite(32, 4, 2, SpriteSheet.tiles);
    public static final Sprite playerRigaht2 = flip(playerLeft2);

    // projectile

    public static final Sprite leek = new Sprite(16, 0, 0, SpriteSheet.projectiles);

    // whiteParticle

    public static final Sprite whiteParticle = new Sprite(2, 0xFFFFFFFF);
    public static final Sprite greenParticle = new Sprite(2, 0xFF32CD32);


    protected Sprite (int[] pixels, int width, int height){
        this.SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = pixels;

    }

    protected Sprite (int width ,int height, SpriteSheet sheet){
        this.SIZE = (width == height) ? width : -1;
        this.sheet = sheet;
        this.width = width;
        this.height = height;
    }

    private Sprite(int size, int x, int y, SpriteSheet sheet){
        this.SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.width = size;
        this.height = size;
        this.sheet = sheet;
        load();
    }

    private Sprite(String path){
        this.SIZE = 16;
        this.width = SIZE;
        this.height = SIZE;
        this.pixels = new int[SIZE * SIZE];
        SpriteSheet.load(pixels, path);
    }

    private Sprite(int width, int height, int colour){
        this.SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColour(colour);
    }

    private Sprite(int size, int colour){
        this.SIZE = size;
        this.width = SIZE;
        this.height = SIZE;
        pixels = new int[SIZE * SIZE];
        setColour(colour);
    }
    private Sprite(int colour){
        this.SIZE = 16;
        this.width = SIZE;
        this.height = SIZE;
        this.pixels = new int[16 * 16];
        setColour(colour);

    }

    private static Sprite flip(Sprite sprite){
        int[] newPixels = new int[sprite.SIZE * sprite.SIZE];
        for (int y = 0; y < sprite.SIZE; y++){
            for (int x = 0; x < sprite.SIZE; x++){
                int xx = sprite.SIZE - x;
                newPixels[x + y * sprite.SIZE ] = sprite.pixels[xx + y * sprite.SIZE -1];
            }
        }
        Sprite sprite1 = new Sprite(sprite.SIZE, sprite.x, sprite.y);
        sprite1.pixels = newPixels;
        return sprite1;
    }


    private void setColour(int colour){
        for (int i = 0; i < this.width * this.height; i ++){
            pixels[i] = colour;
        }
    }


    private void load(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixels[x + y * width] = sheet.pixels[(x + this.x)  + (y + this.y) * sheet.WIDTH];
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
