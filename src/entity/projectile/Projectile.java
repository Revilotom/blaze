package entity.projectile;

import entity.Entity;
import graphics.Sprite;

public abstract class Projectile extends Entity {

    final double xOrigin;
    final double yOrigin;
    final double angle;
    double x;
    double y;
    protected double distance;
    Sprite sprite;
    double nx;
    double ny;
    double speed;
    protected double rateOfFire;
    double range;
    double damage;

    Projectile(double x, double y, double dir) {
        xOrigin = x;
        yOrigin = y;
        angle = dir;
        this.x = x;
        this.y = y;
    }
}
