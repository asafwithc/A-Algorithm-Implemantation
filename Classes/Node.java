//-----------------------------------------------------
// Title: Node class.
// Author: Asaf Emin Gündüz
// Description: This class holds the every needed information about
//              each tile in our map.
//-----------------------------------------------------

public class Node implements Comparable {
    public Node parent;
    public int x, y;
    public double g;
    public double h;
    public char letter;

    Node(Node parent, int xpos, int ypos, double g, double h, char letter) {
        this.parent = parent;
        this.x = xpos;
        this.y = ypos;
        this.g = g;
        this.h = h;
        this.letter = letter;
    }

    //--------------------------------------------------------
    // Summary: Compare by f value (g + h) and override Comparable's method to
    //          use Collections.sort further in the code.
    //--------------------------------------------------------

    @Override
    public int compareTo(Object o) {
        Node obj = (Node) o;
        return (int)((this.g + this.h) - (obj.g + obj.h));
    }
}
