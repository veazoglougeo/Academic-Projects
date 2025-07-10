import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Thiseas {
    static String[][] maze;  //static variables shared among all instances of the class.
    static String[] dimensions;
    static int rows;
    static int columns;
    static String[] E;
    static int eRow;
    static int eColumn;
    static String[] inputLine;
    static StringStack<String> stack = new StringStackImpl<>();
    static String visited = "V";
    static String entrance = "E";
    static String path = "0";

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException{
        try {
            createMaze(args[0]);  //call createMaze function with input file as argument.
            if(!maze[eRow][eColumn].equals(entrance)){  //if the coordinates of entrance E are incorrect, throw an exception to print the error message
                throw new ArrayIndexOutOfBoundsException();  // and terminate the program
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Given dimensions and coordinates are incorrect.");
            return;
        }
        findExit();  //call findExit function to find the path

    }
    public static void createMaze(String file) {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            line = bufferedReader.readLine();
            while (line != null) {
                dimensions = line.split(" ");
                rows = Integer.parseInt(dimensions[0]);  //save number of rows
                columns = Integer.parseInt(dimensions[1]);  //save number of columns
                maze = new String[rows][columns];
                line = bufferedReader.readLine();
                E = line.split(" ");
                eRow = Integer.parseInt(E[0]);  //save Y axis of entrance E
                eColumn = Integer.parseInt(E[1]);  //save X axis of entrance E
                for (int i = 0; i < rows; i++) {
                    line = bufferedReader.readLine();
                    for (int j = 0; j < columns; j++) {
                        if (line != null) {
                            inputLine = line.split(" ");
                            maze[i][j] = inputLine[j];  //add elements to the 2d array which represents the given maze
                        }
                    }
                }
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
            }
            bufferedReader.close();
        } catch (NullPointerException e) {
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean mazeSolved(int row, int column){  //check whether the maze is solved  or not by checking if the path reached a corner
        return maze[row][column].equals(path) && (row == 0 || row == maze.length - 1 || column == 0 || column == maze[0].length - 1);
    }
    public static void pushCoordinates(int row, int column){  //push coordinates to the stack. We push the X axis first and the Y axis second
        stack.push(String.valueOf(row));
        stack.push(String.valueOf(column));
    }
    public static int[] popCoordinates(){
        int[] coordinates = new int[2];
        coordinates[1] = Integer.parseInt(stack.pop());  //X axis
        coordinates[0] = Integer.parseInt(stack.pop());  //Y axis
        return coordinates;
    }
    public static void nextPath(int row, int column){  //check neighbors to continue tracing the path
        try {
            if (row + 1 < maze.length && maze[row + 1][column].equals(path)) {
                pushCoordinates(row + 1, column);
            }
            if (row - 1 > 0 && maze[row - 1][column].equals(path)) {
                pushCoordinates(row - 1, column);
            }
            if (column + 1 < maze[0].length && maze[row][column + 1].equals(path)) {
                pushCoordinates(row, column + 1);
            }
            if (column - 1 > 0 && maze[row][column - 1].equals(path)) {
                pushCoordinates(row, column - 1);
            }
        }catch(NullPointerException e){
            System.out.println("Given dimensions are incorrect.");
        }
    }
    public static void findExit(){
        int[] coordinates;
        pushCoordinates(eRow,eColumn);  //push entrance coordinates to the stack.
        while(!stack.isEmpty()){
            coordinates = popCoordinates();  //stack gets empty and coordinates are passed to the corresponding array
            if(mazeSolved(coordinates[0],coordinates[1])){  //check if coordinates satisfy the requirements for an exit
                System.out.println("Maze solved. Exit is at: " + coordinates[0] + " " + coordinates[1]);
                return;  //if they do print them and terminate the program
            }else if(!maze[coordinates[0]][coordinates[1]].equals(visited)){
                maze[coordinates[0]][coordinates[1]] = visited; //if they don't check neighbors and mark visited coordinates by changing them.
                nextPath(coordinates[0], coordinates[1]);  //stack gets filled again
            }
        }
        System.out.println("Given maze can not be solved.");
    }
}