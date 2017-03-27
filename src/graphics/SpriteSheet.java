package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    public final int SIZE;
    public final int WIDTH, HEIGHT;
    public final int[] pixels;
    public static final SpriteSheet tiles = new SpriteSheet("res/textures/spriteSheet.png", 256);
    public static final SpriteSheet projectiles = new SpriteSheet("res/textures/projectile.png", 48);
    public static final SpriteSheet player = new SpriteSheet("res/textures/playerSheet.png", 96, 128);
    public static final SpriteSheet playerDown = new SpriteSheet(player, 0, 0, 3, 1, 32);
    public static final SpriteSheet playerUp = new SpriteSheet(player, 0, 3, 3, 1, 32);
    public static final SpriteSheet playerLeft = new SpriteSheet(player, 0, 1, 3, 1, 32);
    public static final SpriteSheet playerRight = new SpriteSheet(player, 0, 2, 3, 1, 32);
    public static final SpriteSheet misty = new SpriteSheet("res/textures/mistySheet.png", 96, 128);
    public static final SpriteSheet mistyDown = new SpriteSheet(misty, 0, 1, 3, 1, 32);
    public static final SpriteSheet mistyUp = new SpriteSheet(misty, 0, 0, 3, 1, 32);
    public static final SpriteSheet mistyLeft = new SpriteSheet(misty, 0, 2, 3, 1, 32);
    public static final SpriteSheet mistyRight = new SpriteSheet(misty, 0, 3, 3, 1, 32);
    private Sprite[] sprites;

    private SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize){
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        pixels = new int[w * h];
        WIDTH = w;
        HEIGHT = h;
        if(WIDTH == HEIGHT) SIZE = WIDTH;
        else
            SIZE = -100;

        for(int y0 = 0; y0 < h; y0++){
            int yp = yy + y0;
            for(int x0 = 0; x0 < w; x0++){
                int xp = xx + x0;

                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
            }
        }

        int frame = 0;
        sprites = new Sprite[width * height];

        for (int ya = 0; ya < height; ya ++){
            for (int xa = 0; xa < width; xa++){
                int[] spritePixels = new int[spriteSize * spriteSize];
                for(int y0 = 0; y0 < spriteSize; y0 ++){
                    for(int x0 = 0; x0 < spriteSize; x0++){
                        spritePixels[x0 + y0 * spriteSize] = pixels[x0 + xa * spriteSize + (y0 + ya *spriteSize) * WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, width, height);
                sprites[frame++] = sprite;
            }
        }

    }

    private SpriteSheet(String path, int size){
        this.path = path;
        this.SIZE = size;
        WIDTH = size;
        HEIGHT = size;
        pixels = new int[SIZE * SIZE];
        load(pixels, path);
    }

    private SpriteSheet(String path, int width, int height){
        this.path = path;
        this.SIZE = -1;
        this.WIDTH = width;
        this.HEIGHT = height;
        pixels = new int[WIDTH * HEIGHT];
        load(pixels, path);
    }

    public Sprite[] getSprites(){
        return sprites;

    }

    public static void load(int[] pixels, String path){
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            int wImage = image.getWidth();
            int hImage = image.getHeight();
            image.getRGB(0, 0, wImage, hImage, pixels, 0, wImage);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
