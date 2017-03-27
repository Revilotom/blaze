package entity.Mob;
import entity.Entity;
import entity.projectile.FirstProjectile;
import entity.projectile.Projectile;
import graphics.Screen;
import graphics.Sprite;

public abstract class Mob extends Entity{
    protected boolean moving = false;

    protected enum direction{
        UP, DOWN, LEFT, RIGHT
    }

    protected direction dir;

    void move(double xA, double yA){

        if(xA > 0) dir = direction.RIGHT;
        if(xA < 0) dir = direction.LEFT;
        if(yA > 0) dir = direction.DOWN;
        if(yA < 0) dir = direction.UP;

        if (xA != 0 && yA != 0){
            move(xA, 0);
            move(0, yA);
            return;
        }


        while(xA != 0){
            if (Math.abs(xA) > 1){
                if(!collision(abs(xA), yA)) {
                    this.x += abs(xA);
                }
                xA -= abs(xA);
            }
            else{
                if(!collision(abs(xA), yA)) {
                    this.x += xA;
                }
                xA = 0;
            }
        }

        while(yA != 0){
            if (Math.abs(yA) > 1){
                if(!collision(xA, abs(yA))) {
                    this.y += abs(yA);
                }
                yA -= abs(yA);
            }
            else{
                if(!collision(xA, abs(yA))) {
                    this.y += yA;
                }
                yA = 0;
            }
        }

    }

    private int abs(double num){
        if (num < 0) return -1;
        return 1;
    }

    public abstract void update();

    public abstract void render(Screen screen);

    protected void shoot(double x, double y, double dir){
        Projectile p = new FirstProjectile(x, y, dir);
        level.addEntity(p);
    }

    private  boolean collision(double xA, double yA){
        for(int c = 0; c < 4; c++) {
            double xt = ((x + xA) - c % 2 * 15) / 16;
            double yt = ((y + yA) - c / 2 * 15) /16;
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);

            if (level.getTile(ix, iy).solid()) return true;
        }
        return false;
    }
}
