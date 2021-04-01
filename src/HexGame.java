import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class HexGame {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final UnionFind.Cell<Players> LEFT = new UnionFind.Cell<>(Players.BLUE);
    private static final UnionFind.Cell<Players> RIGHT = new UnionFind.Cell<>(Players.BLUE);
    private static final UnionFind.Cell<Players> TOP = new UnionFind.Cell<>(Players.RED);
    private static final UnionFind.Cell<Players> BOTTOM = new UnionFind.Cell<>(Players.RED);

    public static int NUM_ROWS = 11;
    public static int NUM_COLS = 11;

    public static ArrayList<UnionFind.Cell<Players>> board;

    public enum Players{
        BLUE,
        RED,
        NONE
    }



    public static boolean makeMove(Players player, int location) {
        if(location < 0) return false;
        if(location > (NUM_COLS * NUM_ROWS) - 1) return false;
        if(board.get(location).getValue() != Players.NONE) return false;
        board.get(location).setValue(player);
        groupWithLikeNeighbors(board.get(location), location);
        return true;
    }

    private static void groupWithLikeNeighbors(UnionFind.Cell<Players> cell, int location){
        Players color = cell.getValue();
        boolean isTopEdge = location < NUM_COLS;
        boolean isBottomEdge = location >= ((NUM_COLS * NUM_ROWS) - NUM_COLS);
        boolean isLeftEdge = location % NUM_ROWS == 0;
        boolean isRightEdge = location % NUM_ROWS == (NUM_ROWS-1);

        if(isTopEdge){
            if(color == Players.RED)
                UnionFind.union(TOP, cell); //if top and red union with top.
        }else {
            if(color == board.get(location-NUM_COLS).getValue()) {
                UnionFind.union(board.get(location - NUM_COLS), cell); //union with top left cell
            }
            if(!isRightEdge && board.get((location-NUM_COLS) + 1).getValue() == color){
                UnionFind.union(board.get((location - NUM_COLS) + 1), cell); //union with top right cell
            }
        }

        if(isBottomEdge){
            if(color == Players.RED){
                UnionFind.union(BOTTOM, cell);//if bottom and red union with bottom
            }
        }else {
            if(color == board.get(location + NUM_COLS).getValue()) {
                UnionFind.union(board.get(location + NUM_COLS), cell); //union with Bottom right cell
            }
            if(!isLeftEdge && board.get((location + NUM_COLS) - 1).getValue() == color){
                UnionFind.union(board.get((location + NUM_COLS) - 1), cell); //union with bottom left cell
            }
        }

        if(isLeftEdge){
            if(color == Players.BLUE) {
                UnionFind.union(LEFT, cell); //if left and blue union with left
            }
        }else{
            if(color == board.get(location -1).getValue()) {
                UnionFind.union(board.get(location-1), cell); //union with left neighbor
            }
        }

        if(isRightEdge){
            if(color == Players.BLUE) {
                UnionFind.union(RIGHT, cell); //if right and blue union with right
            }
        }else{
            if(color == board.get(location + 1).getValue()) {
                UnionFind.union(board.get(location+1), cell); //union with right neighbor
            }
        }



    }

    public static Players checkWin(){
        if(UnionFind.find(LEFT) == UnionFind.find(RIGHT))
            return Players.BLUE;
        else if(UnionFind.find(TOP) == UnionFind.find(BOTTOM))
            return Players.RED;
        else return Players.NONE;
    }

    public static void initializeBoard(){
        board = new ArrayList<>();
        for (int i = 0; i < (NUM_COLS * NUM_ROWS); i++) {
            board.add(new UnionFind.Cell<>(Players.NONE));
        }
        LEFT.updateGroupID(LEFT);
        RIGHT.updateGroupID(RIGHT);
        BOTTOM.updateGroupID(BOTTOM);
        TOP.updateGroupID(TOP);
    }

    public static void displayBoard(){
        int arrayIndex = 0;
        int indentRow = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if(board.get(arrayIndex).getValue() == Players.BLUE){
                    stringBuilder.append(ANSI_BLUE + "B " + ANSI_RESET);
                } else if(board.get(arrayIndex).getValue() == Players.RED){
                    stringBuilder.append(ANSI_RED + "R " + ANSI_RESET);
                }
                else{
                    stringBuilder.append(ANSI_WHITE + "0 " + ANSI_RESET);
                }
                arrayIndex ++;
            }
            stringBuilder.append("\n");
            indentRow++;
            stringBuilder.append(" ".repeat(Math.max(0, indentRow)));


        }
        System.out.println(stringBuilder.toString());
    }

    public static void gamePlay(Scanner scanner){
        Players playerTurn = Players.BLUE;
        int numTurns = 0;
        while (scanner.hasNext()){
            int location = scanner.nextInt();
            makeMove(playerTurn, location-1 );

            numTurns ++;
            if(checkWin() != Players.NONE){
                break;
            }

            if(playerTurn == Players.BLUE){
                playerTurn = Players.RED;
            }else{
                playerTurn = Players.BLUE;
            }

        }
        StringBuilder stringBuilder = new StringBuilder();
        if(checkWin() == Players.BLUE){
            stringBuilder.append("Blue won after ");
        }else if (checkWin() == Players.RED){
            stringBuilder.append("Red won after ");
        }else{
            stringBuilder.append("No one won after ");
        }
        stringBuilder.append(numTurns);
        stringBuilder.append(" turns.");
        System.out.println(stringBuilder.toString() + "\n");
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file1 = new File("moves.txt");
        Scanner scanner = new Scanner(file1);
        File file2 = new File("moves2.txt");
        Scanner scanner1 = new Scanner(file2);

        System.out.println("First Game:");
        initializeBoard();
        gamePlay(scanner);
        displayBoard();

        System.out.println("Second Game: ");
        initializeBoard();
        gamePlay(scanner1);
        displayBoard();

    }
}

