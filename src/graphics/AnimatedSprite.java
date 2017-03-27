package graphics;

public class AnimatedSprite extends Sprite {

    private int frame = 0;
    private Sprite sprite;
    private int rate  = 5;
    private int time = 0;
    private int length = -1;

    public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
        super(width, height, sheet);
        this.length = length;
        this.sprite = sheet.getSprites()[0];
        if(length > sheet.getSprites().length){
            System.err.println("Error: animation length is too long");
        }

    }

    public void update(){
        time++;
        if(time % rate == 0) {
            if (frame >= length -1) frame = 0;
            else frame++;
            sprite = sheet.getSprites()[frame];
        }
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public void setRate(int frames){
        rate = frames;
    }

    public void setFrame(int index) {
        if (index > sheet.getSprites().length -1 || index < 0){
            System.err.println("Frame number: " + index + " does not exist!");
            return;
        }
        sprite = sheet.getSprites()[index];
    }
}
