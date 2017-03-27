package graphics;

import entity.Mob.Chaser;
import entity.Mob.Mob;
import entity.Mob.Player;
import entity.Mob.Star;
import level.tile.Tile;

import java.util.Random;

public class Screen {

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public final int width;
    public final int height;
    public final int[] pixels;
    private final int MAP_SIZE = 8;
    public final int MAP_SIZE_MASK = MAP_SIZE -1;
    public final int TILE_SIZE = 16;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private int xOffset;
    private int yOffset;
    private Random random = new Random();

    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    public void renderMob(int xP, int yP, Sprite sprite){
        xP -= xOffset;
        yP -= yOffset;

        for (int y = 0; y < 32; y++){
            int yA = y + yP;
            for(int x = 0; x < 32; x++){
                int xA = x + xP;
                if(xA < -32 || xA >= width || yA < 0 || yA >= height) break;
                if(xA < 0) xA = 0;

                int colour = sprite.pixels[x + y * 32];
                if (colour != 0xFFFF00FF)
                    pixels[xA + yA * width ] = colour ;
            }
        }

    }

    public void renderMob(int xP, int yP, Mob mob){
        xP -= xOffset;
        yP -= yOffset;

        for (int y = 0; y < 32; y++){
            int yA = y + yP;
            for(int x = 0; x < 32; x++){
                int xA = x + xP;
                if(xA < -32 || xA >= width || yA < 0 || yA >= height) break;
                if(xA < 0) xA = 0;
                int colour = mob.getSprite().pixels[x + y * 32];
                if (mob instanceof Chaser && colour == 0xFFFFF86F){
                    colour = 0xFF548AFF;
                }
                if (mob instanceof Star && colour == 0xFFFFF86F){
                    colour = 0xFFff8080;
                }
                if (colour != 0xFFFF00FF)
                    pixels[xA + yA * width ] = colour ;
            }
        }

    }


    public void renderTile(int xP, int yP, Tile tile){

        xP -= xOffset;
        yP -= yOffset;

        for (int y = 0; y < tile.sprite.SIZE; y++){
            int yA = y + yP;
            for(int x = 0; x < tile.sprite.SIZE; x++){
                int xA = x + xP;
                if(xA < -tile.sprite.SIZE || xA >= width || yA < 0 || yA >= height) break;
                if(xA < 0) xA = 0;
                pixels[xA + yA * width ] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void renderProjectile(int xP, int yP, Sprite sprite){

        xP -= xOffset;
        yP -= yOffset;

        for (int y = 0; y < sprite.SIZE; y++){
            int yA = y + yP;
            for(int x = 0; x < sprite.SIZE; x++){
                int xA = x + xP;
                if(xA < -sprite.SIZE || xA >= width || yA < 0 || yA >= height) break;
                if(xA < 0) xA = 0;
                int colour = sprite.pixels[x + y * sprite.SIZE];
                if (colour != 0xFFFF00FF)
                    pixels[xA + yA * width ] = colour;
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = 0; y < sprite.getHeight(); y++){
            int yA = y + yp;
            for(int x = 0; x < sprite.getWidth(); x++){
                int xA = x + xp;
                if(xA < 0 || xA >= width || yA < 0 || yA >= height) continue;
                pixels[xA + yA * width] = sprite.pixels[x + y * sprite.getWidth()];
            }
        }
    }

    public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = 0; y < sheet.HEIGHT; y++){
            int yA = y + yp;
            for(int x = 0; x < sheet.WIDTH; x++){
                int xA = x + xp;
                if(xA < 0 || xA >= width || yA < 0 || yA >= height) continue;
                pixels[xA + yA * width] = sheet.pixels[x + y * sheet.WIDTH];
            }
        }
    }

    public void setOffset(int xOffset, int yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
