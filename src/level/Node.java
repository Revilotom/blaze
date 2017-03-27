package level;

import util.Vector2i;

public class Node {

    public Vector2i tile;
    public Node parent;
    public double fCost, gCost, hCost;
    // Hcost is total distance as the crow flies
    // Gcost is the sum of the weights of all the nodes to get to the destination.

    public Node(Vector2i tile, Node parent, double gCost, double hCost){
        this.tile = tile;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }
}
