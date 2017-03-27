package util;

import java.util.Objects;

public class Vector2i {
    private int x, y;

    public Vector2i(int x, int y) {
        set(x, y);
    }

    public Vector2i(){
        set(0, 0);
    }

    public Vector2i(Vector2i vector){
        set(vector.x, vector.y);
    }

    public Vector2i add(Vector2i vector){
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector2i subtract(Vector2i vector){
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }


    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void test(){
        Vector2i position = new Vector2i(80, 40);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Vector2i)) return false;
        Vector2i vector = (Vector2i)object;
        if (vector.getX() == x && vector.getY() == y){
            return true;
        }
        return false;
    }
}
