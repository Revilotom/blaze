package entity.projectile;
import entity.spawner.ParticleSpawner;
import graphics.Screen;
import graphics.Sprite;


public class FirstProjectile extends Projectile {

    public static final int FIRE_RATE = 5;


    public FirstProjectile(double xOrigin, double yOrigin, double dir) {
        super(xOrigin, yOrigin, dir);
        range = 2000;
        damage = 20;
        speed = 5;
        sprite = Sprite.grass;
        nx = Math.cos(angle) * speed;
        ny = Math.sin(angle) * speed;
    }

    public void update(){
        move();
    }

    private void move(){
        if(!level.tileCollision((int)(x + nx),(int)( y + ny), 5, 0, 6 )) {
            if (distance() > range) {
                removeEntity();
            }
            x += nx;
            y += ny;
        }
       else {
            level.addEntity(new ParticleSpawner((int)x , (int) y + 4, 50, 10, level));
            removeEntity();
        }
    }

    private double distance(){
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }

    public void render(Screen screen){
        screen.renderProjectile((int)x - 5, (int)y - 6, Sprite.leek);
    }

}
