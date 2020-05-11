//
// Stubbed Player class
//
// NOTE: extra classes that help your player must be in the same package.
//

package Players.ay9059;
import Interface.Coordinate;
import Interface.PlayerModulePart1;
import Interface.PlayerMove;

import java.util.*;


/**
 * This student class implements PlayerModulePart1 interface which includes the hasWonGame.
 * The main purpose of this class is to use a data representation to represent the GUI version of
 * Path Builder game.
 *
 * @author Abhishek Yadav
 */
public class ay9059 implements PlayerModulePart1 {

    //Constructs a 2D Array to represent rows and columns
    private char[][] Board;

    //Creates a new HashSet of visited
    private HashSet<Coordinate> visited = new HashSet<>();


    // Overrides do not need new javadocs
    @Override
    public boolean hasWonGame(int id) {


        if (id == 1) {

            ArrayList<Coordinate> queue = new ArrayList<>();
            for (int row = 1; row <= Board.length - 2; row += 2) {
                queue.add(new Coordinate(row, 0));

            }


            while (!queue.isEmpty()) {

                Coordinate current = queue.remove(0);
                if (current.getCol() == Board.length - 1) {
                    return true;

                }


                ArrayList<Coordinate> neighbors = getNeighbors(Board, current, id);

                for (Coordinate coordinate : neighbors) {

                    if (!visited.contains(coordinate)) {
                        queue.add(coordinate);
                        visited.add(new Coordinate(current.getRow(), current.getCol()));
                    }
                }


            }


        } else {

            ArrayList<Coordinate> queue = new ArrayList<>();

            //Iterates through the loop and adds it to the queue
            for (int column = 1; column <= Board.length - 2; column += 2) {
                queue.add(new Coordinate(0, column));

            }


            while (!queue.isEmpty()) {
                Coordinate current = queue.remove(0);
                if (current.getRow() == Board.length - 1) {
                    return true;


                }


                ArrayList<Coordinate> neighbors = getNeighbors(Board, current, id);
                for (Coordinate coordinate : neighbors) {
                    if (!visited.contains(coordinate)) {
                        queue.add(coordinate);
                        visited.add(new Coordinate(current.getRow(), current.getCol()));
                    }
                }

            }

        }


        return false;
    }


    /**
     * Returns the neighbors of the current coordinate that is passed. The neighbors are added only if they
     * are of the same color as the coordinate.
     *
     * @param Board Refers to the 2D Array
     * @param coordinate the coordinate object for which the neighbors are checked
     * @param id refers to the red or blue player
     * @return an array containing valid neighbors
     */
    private ArrayList<Coordinate> getNeighbors(char[][] Board, Coordinate coordinate, int id) {
        ArrayList<Coordinate> neighbors = new ArrayList<>();
        if (ValidPoint(Board, coordinate.getRow() - 1, coordinate.getCol(), id)) {


            neighbors.add(new Coordinate(coordinate.getRow() - 1, coordinate.getCol()));


        }
        if (ValidPoint(Board, coordinate.getRow() + 1, coordinate.getCol(), id)) {


            neighbors.add(new Coordinate(coordinate.getRow() + 1, coordinate.getCol()));


        }
        if (ValidPoint(Board, coordinate.getRow(), coordinate.getCol() + 1, id)) {

            neighbors.add(new Coordinate(coordinate.getRow(), coordinate.getCol() + 1));


        }
        if (ValidPoint(Board, coordinate.getRow(), coordinate.getCol() - 1, id)) {

            neighbors.add(new Coordinate(coordinate.getRow(), coordinate.getCol() - 1));

        }
        return neighbors;

    }


    /**
     * Checks if the row and column are within and bounds and if it is, makes sure it is of the same color as
     * the parent.
     * @param Board 2D array referring to the Board
     * @param row the row
     * @param column the column
     * @param id Refers to the player red or blue
     * @return true or false
     */
    private boolean ValidPoint(char[][] Board, int row, int column, int id) {

        if (id == 1) {
            return (!(row < 0 || row >= Board.length || column < 0 || column >= Board.length) && (Board[row][column] == 'R'));


        } else {
            return (!(row < 0 || row >= Board.length || column < 0 || column >= Board.length) && (Board[row][column] == 'B')
            );
        }
    }





    /**
     * This class makes use of 2D array where the first array is used to represent row and the
     * second one is used to represent the column. If a line has been placed by a blue player,
     * B is added to the Board, similarly R for red. The dots on the Board are also represented by B and R.
     *
     * Method called to initialize player module
     *
     * @param dim      size of the smaller dimension of the playing area for one player
     *                 The grid nodes for that player is size dim * (dim + 1)
     * @param playerId either 1 or 2 for this player
     */
    @Override
    public void initPlayer(int dim, int playerId) {



        System.out.println("initPlayer called");


        Board = new char[(2 * dim) + 1][(2 * dim) + 1];

        for (int i = 0; i <= Board.length - 1; i++) {
            System.out.println();
            for (int j = 0; j <= Board.length - 1; j++) {


                if (i % 2 == 1) {
                    if (j % 2 == 0) {
                        Board[i][j] = 'R';
                        System.out.print('R');
                    } else {
                        ;
                        System.out.print(' ');

                    }
                } else {
                    if (j % 2 != 0) {
                        Board[i][j] = 'B';
                        System.out.print('B');
                    } else {

                        System.out.print(' ');


                    }


                }

            }

        }

    }


    /**
     * This method is called after every move of the game. It is used to
     * keep the internal game state.
     *
     * @param playerMove PlayerMove which represents the most recent move
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        System.out.println("Last move executed");
        int id = playerMove.getPlayerId();

        if (id == 1) {
            int row = playerMove.getCoordinate().getRow();
            int column = playerMove.getCoordinate().getCol();

            Board[row][column] = 'R';

        } else if (id == 2) {
            int row = playerMove.getCoordinate().getRow();
            int column = playerMove.getCoordinate().getCol();

            Board[row][column] = 'B';

        }


    }

    @Override
    public void otherPlayerInvalidated() {
        //for part2

    }

    @Override
    public PlayerMove move() {

        // TODO: use internal documentation to describe your algorithm.

        // TODO: you will need to add private methods as helpers.
        // TODO: you may also need to add other .java files to your package.


        return null;
    }

}
