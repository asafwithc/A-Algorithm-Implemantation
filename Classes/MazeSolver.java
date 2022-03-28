import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//-----------------------------------------------------
// Title: Algorithm implementation class
// Author: Asaf Emin Gündüz
// ID: 15802094188
// Section: 1
// Assignment: 1
// Description: This class implements A* algorithm to the given map.
//              With A* algorithm, we can find the shortest path to the
//              given coordinates.
//-----------------------------------------------------


class MazeSolver {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private final char[][] maze;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;

    //--------------------------------------------------------
    // I Used A* algorithm for solving this homework.
    //
    // A* algorithm simply is a optimized pathfinding algorithm.
    //
    // +++HOW IT WORKS?+++
    // --We add neighbours of the current node (this.now) to the open
    // list and put it in the closed list if it has the smallest f value.
    //
    // --The node with smallest f value will be our current node (this.now).
    //
    // --If current node's coordinates are equal to the coordinates we are looking for,
    // we will put each parent to the path list.
    //
    //--If open list is empty then it means there is no path reaches to our goal coordinates.
    // so it returns null.
    //
    //
    //+++WHAT IS 'F' VALUE?+++
    // -- It lets us find the most efficient neighbour in the open list.
    //
    // -- 'f' value is the sum of 'g' and 'h'.
    //
    // -- 'g' value is vertical / horizontal distance between current node and start node.
    //
    // -- 'h' value is assumed distance between current node and end coordinates.
    //--------------------------------------------------------

    public MazeSolver(char[][] maze, int xstart, int ystart) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0, this.maze[ystart][xstart]);
        this.xstart = xstart;
        this.ystart = ystart;
    }

    //-----------------------------------
    // Finds path to given xend / yend.
    //-----------------------------------

    public List<Node> findPath(int _xend, int _yend) {
        xend = _xend;
        yend = _yend;
        closed.add(now);
        addNeighborsToOpenList();
        while (now.x != xend || now.y != yend) {
            if (open.isEmpty()) { // If there left no other nodes we can examine, return null.
                return null;
            }
            now = open.get(0); // because openList is already sorted this code lets us get the node with the lowest f score
            open.remove(0); // remove it
            closed.add(now); // and add to the closed list
            addNeighborsToOpenList();
        }
        path.add(0, now); // Add all of current node's (this.now) parents to a path List<>.
        while (now.x != xstart || now.y != ystart) {
            now = now.parent;
            path.add(0, now);
        }
        return path;
    }

    //-------------------------------------------------
    // Looks for the given node in the given List<>. Returns true if found.
    //-------------------------------------------------

    private static boolean findNeighborInList(List<Node> array, Node node) {
        for(Node n : array){
            if(n.x == node.x && n.y == node.y){
                return true;
            }
        }
        return false;
    }

    //--------------------------------------------------------
    //  Calculates the distance between current node's coordinates and
    // Xend / Yend ('h' value). Includes horizontal (dy) / vertical (dx) cost.
    //--------------------------------------------------------


    private double distance(int dx, int dy) {
        return Math.abs(now.x + dx - xend) + Math.abs(now.y + dy - yend);
    }

    //--------------------------------------------------------
    // Adds every valid current node's (this.now) neighbours to the open list.
    //
    // Neighbours: Four squares around an index of the 2d maze.
    //--------------------------------------------------------

    private void addNeighborsToOpenList() {
        Node node;
        int[][] positions = {{0,1}, {0,-1}, {1, 0}, {-1,0}};

        for (int i = 0; i < positions.length ; i++) {
            int x = positions[i][0];
            int y = positions[i][1];
            node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y), ' ');
            if (isWalkable(x, y) && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // check if the node already put in the list
                node.g = node.parent.g + 1; // Horizontal/vertical cost = 1
                node.letter = this.maze[this.now.y + y][this.now.x + x]; // Assigns node, its letter
                this.open.add(node);
            }
        }
        Collections.sort(this.open); // Sorting the list to find the closest node to the xend / yend
    }

    //--------------------------------------------------------
    // Checks if the given coordinate is walkable or not.
    //--------------------------------------------------------

    private boolean isWalkable(int x, int y){
        boolean res = this.now.x + x >= 0 && this.now.x + x < this.maze[0].length // check maze boundaries
                &&  this.now.y + y >= 0 && this.now.y + y < this.maze.length
                &&  this.maze[this.now.y + y][this.now.x + x] != '|' // check if its wall or not
                &&  this.maze[this.now.y + y][this.now.x + x] != '-'
                &&  this.maze[this.now.y + y][this.now.x + x] != '+';

        return res;
    }

}