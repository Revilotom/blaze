package entity.Mob;

import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.SpriteSheet;
import level.Node;
import util.Vector2i;

import java.util.List;

public class Star extends Mob {
    private boolean walking = false;
    private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.mistyDown, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.mistyUp, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.mistyLeft, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mistyRight, 32, 32, 3);
    private AnimatedSprite animSprite = down;
    private int xA = 0, yA = 0;
    private List<Node> path = null;
    private int time = 0;

    public Star(int x, int y){
        sprite = animSprite.getSprite();
        this.x = x << 4;
        this.y = y << 4;
    }


    public void update(){
        move();
        time++;
        if(walking) {
            animSprite.update();
        }
        else{
            animSprite.setFrame(0);
        }
        if(yA < 0){
            dir = Mob.direction.UP;
            animSprite = up;
        }
        if(yA > 0) {
            dir = Mob.direction.DOWN;
            animSprite = down;
        }
        if(xA > 0) {
            dir = Mob.direction.RIGHT;
            animSprite = right;
        }
        if(xA < 0){
            dir = Mob.direction.LEFT;
            animSprite = left;
        }
    }

    private void move() {
        yA = 0;
        xA = 0;


            int xx = (int)getX();
            int yy = (int)getY();
            int px = (int)level.getClientPlayer().getX();
            int py = (int)level.getClientPlayer().getY();
            Vector2i start = new Vector2i(xx >> 4, yy >> 4);
            Vector2i goal = new Vector2i(px >> 4, py >> 4);
            path = level.findPath(start, goal);
            if (path != null) {
                if (path.size() > 0) {
                    Vector2i vec = path.get(path.size() - 1).tile;
                    if (x < (vec.getX() << 4)) xA++;
                    if (x > (vec.getX() << 4)) xA--;
                    if (y < (vec.getY() << 4)) yA++;
                    if (y > (vec.getY() << 4)) yA--;
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
