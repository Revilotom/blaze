package entity.Mob;

import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.SpriteSheet;

public class Dummy extends Mob {


    private boolean walking = false;
    private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.mistyDown, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.mistyUp, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.mistyLeft, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mistyRight, 32, 32, 3);
    private AnimatedSprite animSprite = down;
    private int time = 0;
    private int xA = 0, yA = 0;


    public Dummy(int x, int y){
        this.x = x << 4;
        this.y = y << 4;
    }

    @Override
    public void update() {

        time ++;
        if (time % (random.nextInt(50) + 30) == 0){
            xA = random.nextInt(3) -1;
            yA = random.nextInt(3) -1;
            if (random.nextInt(3) == 0){
                xA = 0;
                yA = 0;
            }
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
            move(xA, yA);
        }
        else
            walking = false;

    }

    @Override
    public void render(Screen screen) {
        screen.renderMob((int)x - 16, (int)y - 16, animSprite.getSprite());
    }
}
