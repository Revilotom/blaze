package entity.Mob;

import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.SpriteSheet;

import java.util.List;

public class Chaser extends Mob {

    private boolean walking = false;
    private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.mistyDown, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.mistyUp, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.mistyLeft, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mistyRight, 32, 32, 3);
    private AnimatedSprite animSprite = down;
    private int xA = 0, yA = 0;


    public Chaser(int x, int y){
        sprite = animSprite.getSprite();
        this.x = x << 4;
        this.y = y << 4;
    }


    public void update(){
        move();
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

    }

    private void move() {

        yA = 0;
        xA = 0;
        List<Player> players = level.getPlayersWithinRadius(this, 500);
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (x < (int)player.getX()) {
                xA = 1;
            }
            if (x > (int)player.getX()) {
                xA = -1;
            }
            if (y < (int)player.getY()) {
                yA = 1;
            }
            if (y > (int)player.getY()) {
                yA = -1;
            }
        }


        if(xA != 0 || yA != 0){
            walking = true;
            move(xA, yA);
        }
        else
            walking = false;
    }

    public void render(Screen screen){

        sprite = animSprite.getSprite();
        screen.renderMob((int)x -16, (int)y -16, this);
    }
}
