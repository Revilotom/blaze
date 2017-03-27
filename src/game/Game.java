package game;

import entity.Mob.Player;
import graphics.Screen;
import graphics.Sprite;
import input.Keyboard;
import input.Mouse;
import level.Level;
import level.tile.TileCoordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1L;
    private Thread thread;
    private final JFrame frame;
    private boolean running = false;
    private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private final Screen screen;
    private static final String title = "Blaze";
    private final Keyboard key;
    private final Level level;
    private final Player player;
    private static int width = 300;
    private static int height = 200;

    public static int getWindowHeight() {
        return height * scale;
    }
    public static int getWindowWidth() {
        return width * scale;
    }

    private static int scale = 5;
    private final double ups = 60;

    private Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();
        Mouse mouse = new Mouse();
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        level = Level.spawn;
        TileCoordinate playerSpawn = new TileCoordinate(64);
        player = new Player(playerSpawn.getX(), playerSpawn.getY(), key);
        level.addEntity(player);
    }

    private synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    private synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void update() {
        key.update();
        level.update();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;

        level.render((int)xScroll, (int)yScroll, screen);
        //screen.renderSheet(40, 40, SpriteSheet.playerDown, false);
        screen.renderSprite(0, 0,Sprite.whiteParticle, true);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1_000_000_000 / ups;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();

        while (running) {

            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;

            while(delta >= 1){
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer >= 1000){
                timer += 1000;
                frame.setTitle(title +  " | " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }
    public static void setScale(int scale) {
        Game.scale = scale;
    }

    public static int getScale() {
        return scale;
    }
}
