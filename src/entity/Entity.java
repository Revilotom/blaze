package entity;

import graphics.Screen;
import graphics.Sprite;
import level.Level;

import java.util.Random;

public class Entity {

    // Postion on map not screen
    protected double x, y;
    private boolean removed;
    protected Level level = Level.spawn;
    protected static final Random random = new Random();
    protected Sprite sprite;

    public void update(){

    }

    public void render(Screen screen){

    }

    protected void removeEntity(){

        this.removed = true;
    }

    public void init(Level level){
        this.level = level;
    }

    public boolean isRemoved(){
        return this.removed;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
