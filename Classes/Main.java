import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

//-----------------------------------------------------
// Title: Main class.
// Author: Asaf Emin Gündüz
// Description: Initializes maze.
//              Finds all Es on the maze.
//              Finds paths to those Es.
//              Prints them.
//-----------------------------------------------------

public class Main {

    public static void main(String[] args) throws Exception {
        //--------------------------------------------------
        // Reads the file and copies the data to a 2d array
        Scanner scan = new Scanner(System.in);

        String fileName = scan.nextLine();
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        char[][] maze = new char[11][16];
        int row = 0;

        while((line = br.readLine()) != null){
            for(int i=0; i<line.length(); i++){
                maze[row][i] = line.charAt(i);
            }
            row++;
        }
        //-----------------------------------------------------



        List<int[]> listOfE = findE(maze); //Holds the coordinates of all Es
        List<String> listOfPaths = new ArrayList<>(); //Initialization of holder of our all paths

        for(int i=0; i<listOfE.size(); i++){ // For looping through all of the target points.

            MazeSolver as = new MazeSolver(maze, 0, 1);
            List<Node> path = as.findPath(listOfE.get(i)[0], listOfE.get(i)[1]); // Finds path to the target point.

            String str = "";

            if (path != null) {
                for(Node node : path){
                    str += node.letter; // Add char's of the nodes we walked through to a string.
                }
                listOfPaths.add(str); // Adds the string to a List<>.
            }
        }

        Collections.sort(listOfPaths, new Comparator<String>(){ // Sorts the paths (string length ascending order). Found this code block on internet.
            public int compare(String o1, String o2)
            {
                return o1.length() - o2.length();
            }
        });

        if(listOfPaths.size() != 0){
            System.out.println(listOfPaths.size() + " treasures are found.");
            System.out.println("Paths are: ");

            int num = 1;
            for(String str : listOfPaths){
                System.out.println((num++) + ")" + str);
            }
        }else{
            System.out.println("0 treasures are found.");
        }
    }

    //------------------------------------------------------------------------
    // find coordinates of all Es in the given maze and stores it in a List<>
    //------------------------------------------------------------------------

    public static List<int[]> findE(char[][] map){
        List<int[]> list = new ArrayList<>();
        for(int i=0; i < map.length; i++){
            for(int j=0; j < map[0].length; j++){
                if(map[i][j] == 'E'){
                    int[] arr = {j, i};
                    list.add(arr);
                }
            }
        }
        return list;
    }
}
