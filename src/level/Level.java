package level;
import entity.Entity;
import entity.Mob.Player;
import entity.particle.Particle;
import entity.projectile.Projectile;
import graphics.Screen;
import level.tile.Tile;
import util.Vector2i;

import java.util.*;

public class Level {

    int width;
    int height;
    int[] tileInts;
    int[] tiles;
    private final List<Entity> entities = new ArrayList();
    private final List<Projectile> projectiles = new ArrayList();
    private final List<Particle> particles = new ArrayList();
    private final List<Player> players = new ArrayList<>();
    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.fCost > n2.fCost)
                return 1;
            if (n1.fCost < n2.fCost)
                return -1;
            return 0;
        }
    };

    protected Random random = new Random();
    public static final Level spawn = new SpawnLevel();
    public static final Level randomLevel = new RandomLevel(100, 100);

    Level(int width, int height){
        this.width = width;
        this.height = height;
        tileInts = new int[width * height];
        generateLevel();
    }

    Level(String path){
        loadLevel(path);
        generateLevel();
    }

    public void update(){

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update();
        }
        for (int i = 0; i < players.size(); i++){
            players.get(i).update();
        }
        remove();
    }

    private void remove(){
        for (int i = 0; i < particles.size(); i++) {
            if(particles.get(i).isRemoved()) particles.remove(i);
        }
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).isRemoved()) entities.remove(i);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) projectiles.remove(i);
        }
        for(int i = 0; i < players.size(); i ++){
            if (players.get(i).isRemoved()) players.remove(i);
        }
    }


    protected void loadLevel(String path){

    }

    protected void generateLevel(){

    }

    public List<Projectile> getProjectiles(){
        return projectiles;
    }

    public void addEntity(Entity e){
        e.init(this);
        if( e instanceof Particle){
            particles.add((Particle) e);
        }

        else if(e instanceof Projectile) {
            projectiles.add((Projectile) e);

        }

        else if (e instanceof  Player){
            players.add((Player) e);
        }

        else {
            entities.add(e);
        }
    }

    public void render(int xScroll, int yScroll, Screen screen){
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4 ;
        int x1 = (xScroll + screen.width + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;

        for (int y = y0; y < y1; y++){
            for(int x = x0; x < x1; x++){
                getTile(x, y).render(x, y, screen);
            }
        }
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(screen);
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }
        for (int i = 0; i < players.size(); i++){
            players.get(i).render(screen);
        }
    }

    public  boolean tileCollision(int x, int y,  int size, int xOffset, int yOffset){
        for(int c = 0; c < 4; c++) {
            int xt = (x - c % 2 * size + xOffset) >> 4;
            int yt = (y - c / 2 * size + yOffset) >> 4;

            if (getTile(xt, yt).solid()) return true;
        }

        return false;
    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height)
            return Tile.voidTile;
        if (tiles[x + y * width] == 0xFF00FF00)
            return Tile.grass;
        if (tiles[x + y * width] == 0xFFFFFF00)
            return Tile.flower;
        if (tiles[x + y * width] == 0xFF7F7F00)
            return Tile.rock;
        if (tiles[x + y  * width] == 0xFF58CE64)
            return Tile.tree;
        if (tiles[x + y  * width] == 0xFFC5CC86)
            return Tile.grass1;
        if (tiles[x + y * width] == 0xFFFF8432)
            return  Tile.orangeBricks;
        if (tiles[x + y * width] == 0xFF413F75)
            return  Tile.blueBricks;
        if (tiles[x + y * width] == 0xFF825332)
            return Tile.brownBricks;
        if (tiles[x + y * width] == 0xFFDB9B5F)
            return Tile.paleWood;
        if (tiles[x + y * width] == 0xFF76492E)
            return Tile.darkWood;
        if (tiles[x + y * width] == 0xFF8C2522)
            return Tile.mud;
        if (tiles[x + y * width] == 0xFF50A766)
            return Tile.moss;
        return Tile.voidTile;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public Player getPlayerAt(int index){
        return players.get(index);
    }

    public Player getClientPlayer(){
        return players.get(0);
    }

    public List<Node> findPath(Vector2i start, Vector2i goal){
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        open.add(current);
        while (open.size() > 0){
            Collections.sort(open, nodeSorter);
            current = open.get(0);
            if (current.tile.equals(goal)){
                List<Node> path = new ArrayList<>();
                path.add(current);
                while (current.parent != null){
                    path.add(current.parent);
                    current = current.parent;
                }
                open.clear();
                closed.clear();
                if (path.size() > 0)
                    path.remove(path.size()-1);
                return path;
            }
            open.remove(current);
            closed.add(current);
            for (int i = 0; i < 9; i++){
                if (i == 4) continue;
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile at = getTile(x + xi, y + yi);
                if (at == null) continue;
                if (at.solid()) continue;
                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + getDistance(current.tile, a);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if (vecInList(closed, a) && gCost >= node.gCost) continue;
                if(!vecInList(open, a) || gCost < node.gCost) open.add(node);
            }
        }
        closed.clear();
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector){
        for (Node n:list) {
            if (n.tile.equals(vector)){
                return true;
            }
        }
        return false;
    }

    public double getDistance(Vector2i tile, Vector2i goal){
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public List<Entity> getEntities(Entity e, int radius){
        double eX = e.getX();
        double eY = e.getY();
        List<Entity> result = new ArrayList<>();
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            if (e.equals(entity)) continue;
            double x  = entity.getX();
            double y = entity.getY();
            double dx = eX - x;
            double dy = eY - y;
            double distance = Math.sqrt(dx*dx + dy*dy);
            if (distance <= radius)
                result.add(entity);
        }
        return  result;
    }

    public List<Player> getPlayersWithinRadius(Entity e, int radius){
        double eX = e.getX();
        double eY = e.getY();
        List<Player> result = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            double x  = p.getX();
            double y = p.getY();
            double dx = eX - x;
            double dy = eY - y;
            double distance = Math.sqrt(dx*dx + dy*dy);
            if (distance <= radius)
                result.add(p);
        }
        return  result;
    }

}
