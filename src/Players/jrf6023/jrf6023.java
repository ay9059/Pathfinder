//
// Stubbed Player class
// NOTE: extra classes that help your player must be in the same package.
//

package Players.jrf6023;

import Interface.*;
import java.util.HashSet;
import java.util.Set;


/**
 * This is the implemented player module class to represent an instance of a player.
 * Implements hasWonGame, initPlayer, and lastMove, as to pass the part1 tests in configFiles.part1
 *
 * @author jon frey
 */
public class jrf6023 implements PlayerModulePart1 {
    /**
     * board - internal board representation
     */
    private BoardGraph board;
    private int team;

    /**
     * Loops through start and end nodes checking if there is a path from one to the other.
     * uses a depth-first search algorithm.
     * @param team player ID, either 1 or 2
     * @return true if a path was found from one start node to an end node.
     */
    @Override
    public boolean hasWonGame( int team ) {
        boolean canReach = false;
        if(team == 1){
            for(Dot sDot:board.getStartNodes1()){
               for(Dot eDot:board.getEndNodes1()){
                   if(canReachDFS(sDot,eDot)){
                       canReach = true;
                   }
               }
            }
        }
        else if(team ==2){
            for(Dot sDot:board.getStartNodes2()){
                for(Dot eDot:board.getEndNodes2()){
                    if(canReachDFS(sDot,eDot)){
                        canReach = true;
                    }
                }
            }
        }
        return canReach;
    }

    /**
     * Possible path test, depth-first algorithm.
     * @param startNode Node to start from
     * @param finishNode Final node
     * @return True if the final node could be reached, false otherwise.
     */
    public boolean canReachDFS(Dot startNode, Dot finishNode ) {
        Set< Dot > visited = new HashSet<>();
        visited.add( startNode );

        visitDFS( startNode, visited );
        return visited.contains( finishNode );
    }

    /**
     * helper function for canReachDFS, fills out visited list with each specific node
     * and its neighbors.
     * @param node Node from which to pull neighbors
     * @param visited list of accesible nodes from the start node in canReachDFS
     */
    private void visitDFS(Dot node, Set< Dot > visited ) {

        board.getNeighbors( node ).forEach( nbr -> {
            if ( !visited.contains( nbr ) ) {
                visited.add( nbr );
                visitDFS( nbr, visited );
            }
        } );
    }

    /**
     * Instantiates the player class given a dimension and team. Generates internal graph
     * representation of game board, populating with team 2 and team 1 dots on either
     * even rows and odd columns, or even columns and odd rows, respectively.
     * @param dim Number of base nodes this player has, creates board with dimensions
     *            (2*dim) +1 by (2*dim) + 1
     * @param team Team that the player belongs to, either 1 or 2
     */
    @Override
    public void initPlayer( int dim, int team ) {
        this.team = team;
        this.board = new BoardGraph(dim);
        int bDim = dim*2;
        // i is the row, p is the column
        for(int i = 0; i < bDim +1; i++){
            for( int p = 0; p< bDim+1; p++){
                // if row is even and column is odd, blue dot is put (team 2)
                if(i%2 == 0 && p%2 != 0) {
                    board.makeNode(new Coordinate(i,p),2);
                }
                // if row is odd and column is even, red dot is put (team 1)
                else if (i%2 != 0 && p%2 == 0){
                    board.makeNode(new Coordinate(i,p), 1);
                }
            }
        }

    }

    /**
     * Given the last move to occur, the internal graph representation is updated with the
     * new information contained in the PlayerMove passed in as a param. Depending on team,
     * either connects vertically or horizontally, the Dots on either side of the coordinate given
     * within playerMove
     * @param playerMove Move that has just been made on the game board
     */
    @Override
    public void lastMove( PlayerMove playerMove ) {
        int row = playerMove.getCoordinate().getRow();
        int col = playerMove.getCoordinate().getCol();
        int team = playerMove.getPlayerId();
        if(row%2 == 0 && col%2 == 0){
            if(team == 1){
                Dot n1 = board.getNode(new Coordinate(row+1,col));
                Dot n2 = board.getNode(new Coordinate(row -1,col));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
            else {
                Dot n1 = board.getNode(new Coordinate(row, col + 1));
                Dot n2 = board.getNode(new Coordinate(row, col - 1));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
        }
        else{
            if(team == 1){
                Dot n1 = board.getNode(new Coordinate(row,col+1));
                Dot n2 = board.getNode(new Coordinate(row ,col-1));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
            else {
                Dot n1 = board.getNode(new Coordinate(row+1, col));
                Dot n2 = board.getNode(new Coordinate(row-1, col));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
        }

    }

    @Override
    public void otherPlayerInvalidated() {

    }

    @Override
    public PlayerMove move() {
        // Use A* with a TreeMap of a pathNode class with values of Coord(name),
        // TODO: Make pathNode class with needed fields and accessors
        // Total distance to end, and path weighting
        // Draw up another graph with nodes at turning points, final points, important points etc
        // TODO: Create criteria for node creation for A* algorithm graph
        // FOR DISTANCE FROM END, depending on team, just use linear distance of coordinate to
        // end so for team 1, final column (2*dim + 1) - current column then add that
        // FOR NODE DISTANCE, use linear difference in coordinates, just test if col or row are the same
        // and whichever is true, find difference in row or col respectively.
        // TODO: Use graph of pathNodes

        return null;
    }
}
