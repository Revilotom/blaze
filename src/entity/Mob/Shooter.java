package entity.Mob;

import entity.Entity;
import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import util.Vector2i;

import java.util.List;

public class Shooter extends Mob{

    private boolean walking = false;
    private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.mistyDown, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.mistyUp, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.mistyLeft, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mistyRight, 32, 32, 3);
    private AnimatedSprite animSprite = down;
    private int time = 0;
    private int xA = 0, yA = 0;

    public Shooter(int x, int y){
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = Sprite.playerDown;
    }

    public void update() {
        time++;
        if (time % (random.nextInt(50) + 30) == 0) {
            xA = random.nextInt(3) - 1;
            yA = random.nextInt(3) - 1;
            if (random.nextInt(3) == 0) {
                xA = 0;
                yA = 0;
            }
        }
        shootNearest();
    }

    public void shootNearest(){
        List<Entity> entities = level.getEntities(this, 500);
        entities.add(level.getClientPlayer());
        double min = 100000;
        Entity closest = null;

        for (Entity entity: entities){
            double distance  = level.getDistance(new Vector2i((int)x, (int)y),new Vector2i((int)entity.getX(), (int)entity.getY()) );
            if(distance < min){
                min = distance;
                closest = entity;
            }
        }

        if (closest != null){
            double dx = closest.getX() - x;
            double dy = closest.getY() - y;
            double d = Math.atan2(dy, dx);
            if (random.nextBoolean())
                shoot(x, y, d);
        }



        if(walking) {
            animSprite.update();
        }
        else{
            animSprite.setFrame(0);
        }

        if(yA < 0){
            dir = direction.UP;
            animSprite = up;
        }
        if(yA > 0) {
            dir = direction.DOWN;
            animSprite = down;
        }
        if(xA > 0) {
            dir = direction.RIGHT;
            animSprite = right;
        }
        if(xA < 0){
            dir = direction.LEFT;
            animSprite = left;
        }
        if(xA != 0 || yA != 0){
            walking = true;
            //move(xA, yA);
        }
        else
            walking = false;
    }


    public void render (Screen screen){
        screen.renderMob((int)x -16, (int)y - 12, animSprite.getSprite());
    }

}
