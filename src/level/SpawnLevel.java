package level;

import entity.Mob.Chaser;
import entity.Mob.Dummy;
import entity.Mob.Shooter;
import entity.Mob.Star;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SpawnLevel extends Level{

    public SpawnLevel(int width, int height) {
        super(width, height);
    }

    public SpawnLevel() {
        super("res/levels/spawn.png");
    }

    protected void loadLevel(String path){
        try{
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int [w * h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Could not load file level file!!!");
        }

        for (int i = 0; i < 5; i++) {
            addEntity(new Dummy(24 + random.nextInt(5), 64 + random.nextInt(5)));
        }
//        addEntity(new Chaser(10, 64));
        addEntity(new Star(15, 64));
        addEntity(new Shooter(22 + random.nextInt(5), 65+ random.nextInt(5)));

    }

    protected void generateLevel(){

    }
}
