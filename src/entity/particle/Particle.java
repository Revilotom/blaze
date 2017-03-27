package entity.particle;

import entity.Entity;
import graphics.Screen;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Particle extends Entity {
    private List<Particle> particles = new ArrayList();
    private Sprite sprite;
    private int life;
    private int count = 0;
    protected double xA, yA, zA, xx, yy, zz;
    // zA is acceleration due to gravity

    public Particle(int x, int y, int life){
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        if (random.nextBoolean())
            sprite = Sprite.whiteParticle;
        else
            sprite = Sprite.greenParticle;
        this.xA = random.nextGaussian();
        this.yA = random.nextGaussian();
        this.zz = random.nextFloat() + 1.6;
        this.life = life + random.nextInt(20) - 10;
    }


    public void update(){
        count ++;
        if(count >= 7500) count = 0;
        if(count >= life) removeEntity();
        zA -= 0.1;
        if(zz < 0){
            zz = 0;
            zA *= -0.5;
            xA *= 0.5;
            yA *= 0.5;
        }

        move((xx + xA),(yy + yA) + (zz + zA));
    }

    public  boolean collision(double x, double y){
        for(int c = 0; c < 4; c++) {
            double xt = (x - c % 2 * 16) / 16;
            double yt = (y - c / 2 * 16) / 16;
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);

            if (level.getTile(ix, iy).solid()) return true;
        }

        return false;
    }

    private void move(double x, double y) {
        if (collision(x, y)){
            zA *= -0.5;
            xA *= -0.5;
            yA *= -0.5;
        }
        xx += xA;
        yy += yA;
        zz += zA;
    }


    public void render(Screen screen){
        screen.renderSprite((int) xx -1 ,(int) yy - (int)zz -1, sprite, true);
    }
}
