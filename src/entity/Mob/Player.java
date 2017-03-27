package entity.Mob;

import entity.projectile.FirstProjectile;
import entity.projectile.Projectile;
import game.Game;
import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.Keyboard;
import input.Mouse;

public class Player extends Mob {

    private final Keyboard input;
    private Sprite sprite;
    private int anim;
    private boolean walking = false;
    private int fireRate;
    private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.playerDown, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.playerUp, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.playerLeft, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.playerRight, 32, 32, 3);

    private AnimatedSprite animSprite = down;


    public Player(Keyboard input){
        this.input = input;
        this.sprite = Sprite.playerUp;
    }

    public Player(int x , int y, Keyboard input){
        this.x = x;
        this.y = y;
        this.input = input;
        this.fireRate = FirstProjectile.FIRE_RATE;
    }

    private void clear(){
        for (int i = 0; i < level.getProjectiles().size(); i++){
            Projectile p = level.getProjectiles().get(i);
            if (p.isRemoved()){
                level.getProjectiles().remove(i);
            }
        }
    }

    public void update(){

        double xA = 0, yA = 0;
        double speed = 1.3;
        if(walking) {
            animSprite.update();
        }
        else{
            animSprite.setFrame(1);
        }
        if(fireRate > 0 )
            fireRate --;
        if(anim < 7500 )
            anim++;
        else
            anim = 0;
        if(input.up){
            yA -= speed;
            animSprite = up;
        }
        if(input.down) {
            yA += speed;
            animSprite = down;
        }
        if(input.right) {
            xA += speed;
            animSprite = right;
        }
        if(input.left){
            xA -= speed;
            animSprite = left;
        }
        updateShooting();

        if(xA != 0 || yA != 0){
            walking = true;
            move(xA, yA);
        }
        else
            walking = false;
        clear();
    }

    private void updateShooting(){
        if (Mouse.getMouseB() == 1 && fireRate <= 0){
            double dx = Mouse.getMouseX() - (Game.getWindowWidth() / 2);
            double dy = Mouse.getMouseY() - (Game.getWindowHeight() / 2);
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);
            fireRate = FirstProjectile.FIRE_RATE;
        }
    }

    public void render(Screen screen){

        sprite = animSprite.getSprite();

        screen.renderMob((int)x - 16, (int)y - 16, sprite);

    }

}
