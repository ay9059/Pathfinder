//
// Stubbed Player class
// NOTE: extra classes that help your player must be in the same package.
//

package Players.allStar;

import Interface.*;

import java.util.*;


/**
 * This is the implemented player module class to represent an instance of a player.
 * Implements hasWonGame, initPlayer, and lastMove, as to pass the part1 tests in configFiles.part1
 *
 * @author Abhishek Yadav
 * @author jon frey
 */
public class allStar implements PlayerModulePart1,PlayerModulePart2 {
    /**
     * board - internal board representation
     */
    private BoardGraph board;
    private int team;

    /**
     * Loops through start and end nodes checking if there is a path from one to the other.
     * uses a depth-first search algorithm.
     *
     * @param team player ID, either 1 or 2
     * @return true if a path was found from one start node to an end node.
     */
    @Override
    public boolean hasWonGame(int team) {
        boolean canReach = false;
        if (team == 1) {
            for (Dot sDot : board.getStartNodes1()) {
                for (Dot eDot : board.getEndNodes1()) {
                    if (canReachDFS(sDot, eDot)) {
                        canReach = true;
                    }
                }
            }
        } else if (team == 2) {
            for (Dot sDot : board.getStartNodes2()) {
                for (Dot eDot : board.getEndNodes2()) {
                    if (canReachDFS(sDot, eDot)) {
                        canReach = true;
                    }
                }
            }
        }
        return canReach;
    }

    /**
     * Possible path test, depth-first algorithm.
     *
     * @param startNode  Node to start from
     * @param finishNode Final node
     * @return True if the final node could be reached, false otherwise.
     */
    private boolean canReachDFS(Dot startNode, Dot finishNode) {
        Set<Dot> visited = new HashSet<>();
        visited.add(startNode);

        visitDFS(startNode, visited);
        return visited.contains(finishNode);
    }

    /**
     * helper function for canReachDFS, fills out visited list with each specific node
     * and its neighbors.
     *
     * @param node    Node from which to pull neighbors
     * @param visited list of accesible nodes from the start node in canReachDFS
     */
    private void visitDFS(Dot node, Set<Dot> visited) {

        board.getNeighbors(node).forEach(nbr -> {
            if (!visited.contains(nbr)) {
                visited.add(nbr);
                visitDFS(nbr, visited);
            }
        });
    }

    /**
     * Instantiates the player class given a dimension and team. Generates internal graph
     * representation of game board, populating with team 2 and team 1 dots on either
     * even rows and odd columns, or even columns and odd rows, respectively.
     *
     * @param dim  Number of base nodes this player has, creates board with dimensions
     *             (2*dim) +1 by (2*dim) + 1
     * @param team Team that the player belongs to, either 1 or 2
     */
    @Override
    public void initPlayer(int dim, int team) {
        this.team = team;
        this.board = new BoardGraph(dim);
        int bDim = (dim * 2);
        // i is the row, p is the column
        for (int i = 0; i <= bDim ; i++) {
            for (int p = 0; p <= bDim ; p++) {
                // if row is even and column is odd, blue dot is put (team 2)
                if (i % 2 == 0 && p % 2 != 0) {
                    board.makeNode(new Coordinate(i, p), 2, (bDim - i));
                }
                // if row is odd and column is even, red dot is put (team 1)
                else if (i % 2 != 0 && p % 2 == 0) {
                    board.makeNode(new Coordinate(i, p), 1, (bDim - p));
                }
            }
        }

    }

    /**
     * Given the last move to occur, the internal graph representation is updated with the
     * new information contained in the PlayerMove passed in as a param. Depending on team,
     * either connects vertically or horizontally, the Dots on either side of the coordinate given
     * within playerMove
     *
     * @param playerMove Move that has just been made on the game board
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        int row = playerMove.getCoordinate().getRow();
        int col = playerMove.getCoordinate().getCol();
        int team = playerMove.getPlayerId();
        Dot n1;
        int minweight = 1000;
        if (row % 2 == 0 && col % 2 == 0) {
            if (team == 1) {
                n1 = board.getNode(new Coordinate(row + 1, col));
                Dot n2 = board.getNode(new Coordinate(row - 1, col));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            } else {
                n1 = board.getNode(new Coordinate(row, col + 1));
                Dot n2 = board.getNode(new Coordinate(row, col - 1));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
        } else {
            if (team == 1) {
                n1 = board.getNode(new Coordinate(row, col + 1));
                Dot n2 = board.getNode(new Coordinate(row, col - 1));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            } else {
                n1 = board.getNode(new Coordinate(row + 1, col));
                Dot n2 = board.getNode(new Coordinate(row - 1, col));
                n1.addNeighbor(n2);
                n2.addNeighbor(n1);
            }
        }
        // Updates weight of line
        List<Dot> line = getLine(n1);
        for(Dot d : line){
            if(d.getWeight() < minweight){
                minweight = d.getWeight();
            }
        }
        for(Dot d: line){
            d.setWeight(minweight);
            d.setWeight_copy(minweight);
        }
    }

    /**
     * Performs a search along connected nodes and returns the list representing a connected line of dots
     * @param node Dot in a line
     * @return List of dots
     */
    private List<Dot> getLine(Dot node){
        List<Dot> visited = new ArrayList<>();
        visited.add(node);
        lineWeightHelper(node, visited);
        if (visited.size()==1){
            return new ArrayList<>();
        }
        return visited;
    }

    /**
     * Helper function for getLine search
     * @param node Dot in line
     * @param visited list of dots already visited in the line
     */
    private void lineWeightHelper(Dot node, List<Dot> visited){
        board.getNeighbors(node).forEach(nbr -> {
            if (!visited.contains(nbr)) {
                visited.add(nbr);
                lineWeightHelper(nbr, visited);
            }
        });
    }

    /**
     * Function not needed
     */
    @Override
    public void otherPlayerInvalidated() {
    }

    /**
     * This function returns all legal moves in the board
     * @return List of all legal moves
     */
    @Override
    public List allLegalMoves() {
        int bDim = board.getDim()*2;
        List<PlayerMove> moves = new ArrayList<>();
        Set<Coordinate> coords = new HashSet<>();
        for (int i = 0; i <= bDim ; i++) {
            for (int p = 0; p <= bDim ; p++) {
                // if row is even and column is even
                if (i % 2 == 0 && p % 2 == 0) {
                    coords.add(new Coordinate(i,p));
                }
                // if row is odd and column is odd
                else if (i % 2 != 0 && p % 2 != 0) {
                    coords.add(new Coordinate(i,p));
                }
            }
        }
        for(Coordinate coord : coords){
            if(isOpen(coord)){
                moves.add(new PlayerMove(coord,team));
            }
        }
        return moves;
    }

    /**
     * Checks if a space at a given coordinate is open or not
     * @param coord coord in question
     * @return true if open, false if not
     */
    private boolean isOpen(Coordinate coord){
        int row = coord.getRow();
        int col = coord.getCol();
        boolean open = true;
        if(row != 0 && row != board.getEndNodes2().get(0).getplace().getRow()){
            if(board.getNode(new Coordinate(row +1,col)).getedgeSet().contains(board.getNode(new Coordinate(row-1,col)))){
                open = false;
            }
        }
        if(col != 0 && col != board.getEndNodes1().get(0).getplace().getCol()){
            if(board.getNode(new Coordinate(row,col+1)).getedgeSet().contains(board.getNode(new Coordinate(row,col-1)))){
                open = false;
            }
        }
        return open;
    }

    /**
     * Performs a search as if to find the best move, returns size of best moveset
     * @param i Team ID from which to search
     * @return size of the best move list
     */
    @Override
    public int fewestSegmentsToVictory(int i) {
        List<List<Dot>> bestPaths = new ArrayList<>();
        if(i == 1) {
            for (Dot node : board.getStartNodes1()) {
                List<Dot> temp = baeStarSearch(node);
                if (temp.size() != 0) {
                    bestPaths.add(temp);
                }
            }
        }
        else{
            for (Dot node : board.getStartNodes2()) {
                List<Dot> temp = baeStarSearch(node);
                if (temp.size() != 0) {
                    bestPaths.add(temp);
                }
            }
        }
        bestPaths.sort(new pathCompare());
        // get moves to win using the A* search and the pathToCoords function
        List<Dot> bestpath_dots = bestPaths.get(0);
        List<Coordinate> bestpath_coords = pathToCoords(bestpath_dots);
        return bestpath_coords.size();
    }

    /**
     * Comparator for Dots, compares based on weight
     */
    static class DotCompare implements Comparator<Dot> {
        @Override
        public int compare(Dot a, Dot b) {
            if(a.getWeight() == b.getWeight()){
                return 0;
            }else{
                return a.getWeight() - b.getWeight();
            }
        }
    }

    /**
     * Comparator for paths, compares based on first item's weight
     */
    static class pathCompare implements Comparator<List<Dot>> {
        @Override
        public int compare(List<Dot> a, List<Dot> b) {
            return a.get(0).getWeight() - b.get(0).getWeight();
        }
    }

    /**
     * This is the brains and the brawns for allStar class. This method is used to figure out the best
     * course of action when playing pathBuilder. This method makes a call to the searching algorithm
     * for all starting nodes on the board and figures out the no. of moves each player is away from winning. It uses
     * this calculation to either block the other player's path or try and get closer to the end Node.
     *
     * @return a PlayerMove object that refers to where this instance of Player has put a line.
     */
    @Override
    public PlayerMove move() {

        // Do a search for each team, and find the shortest for each team
        List<List<Dot>> bestPaths1 = new ArrayList<>();
        List<List<Dot>> bestPaths2 = new ArrayList<>();
        for(Dot node : board.getStartNodes1()) {
            List<Dot> temp = baeStarSearch(node);
            if(temp.size() != 0){
                bestPaths1.add(temp);
            }
        }
        for(Dot node : board.getStartNodes2()) {
            List<Dot> temp = baeStarSearch(node);
            if(temp.size() != 0){
                bestPaths2.add(temp);
            }        }
        bestPaths1.sort(new pathCompare());
        bestPaths2.sort(new pathCompare());
        // get moves to win using the A* search and the pathToCoords function
        List<Dot> p1_bestpath_dots = bestPaths1.get(0);
        List<Dot> p2_bestpath_dots = bestPaths2.get(0);
        List<Coordinate> p1 = pathToCoords(p1_bestpath_dots);
        List<Coordinate> p2 = pathToCoords(p2_bestpath_dots);
        PlayerMove move1_1 = new PlayerMove(p1.get(p1.size()-1),1);
        PlayerMove move1_2 = new PlayerMove(p1.get(p1.size()-1),2);
        PlayerMove move2_1 = new PlayerMove(p2.get(p2.size()-1), 1);
        PlayerMove move2_2 = new PlayerMove(p2.get(p2.size()-1), 2);
        int lim = board.getDim();

        if (this.team == 1) {
            //if the other players moves remaining to win is less than or equal to the dimension of the board
            if (p2.size() <= lim) {
                if(p1.size() == 1){
                    return move1_1;
                }else{
                    return move2_1;
                }
            } else {
                return move1_1;
            }
        }
        // Team 2
        else {
            //if the other players moves remaining to win is less than or equal to the dimension of the board
            if (p1.size() <=lim) {
                if(p2.size() == 1){
                    return move2_2;
                }else{
                    return move1_2;
                }
            } else {
                return move2_2;
            }
        }
    }

    /**
     * Jon
     * Only add coord in between nodes if there is no existing link
     * Testing with list {(0,3),(2,3),(4,3)}
     *
     * @param path Given list of dots creating a path from start to finish
     * @return List of coordinates of potential moves
     */
    private List<Coordinate> pathToCoords(List<Dot> path) {
        List<Coordinate> coords = new ArrayList<>();
        int i = 0;
        while(i < path.size()-1){
            Dot curr = path.get(i);
            // only add to coords if the next dot path.get(i+1) is a valid neighbor of curr
            Dot next = path.get(i+1);
            int rowCurr = curr.getplace().getRow();
            int colCurr = curr.getplace().getCol();
            int rowNext = path.get(i+1).getplace().getRow();
            int colNext = path.get(i+1).getplace().getCol();
            //Only continue if the next is not connected to the current
            if(!(curr.getedgeSet().contains(next))){
                if (rowCurr == rowNext) {
                    coords.add(new Coordinate(rowCurr, (colCurr + colNext) / 2));
                } else {
                    coords.add(new Coordinate((rowCurr + rowNext) / 2, colCurr));
                }
            }
            i += 1;
        }
        return coords;
    }


    /**
     * baeStarSearch method has to be called for each starting node of its player to return a path.
     *
     * @param startNode starting point for baeStarSearch algorithm
     * @return a list representing the path.
     */
    private List<Dot> baeStarSearch(Dot startNode) {
        HashMap<Dot, Dot> predecessors = new HashMap<>();
        List<Dot> path = new ArrayList<>();
        List<Dot> prioQ = new ArrayList<>();
        prioQ.add(startNode);
        predecessors.put(startNode, startNode);
        while (!prioQ.isEmpty()) {
            prioQ.sort(new DotCompare());
            Dot currentDot = prioQ.get(0);
            prioQ.remove(currentDot);
            // Checking if we are at the goal
            if(board.getEndNodes1().contains(currentDot) || board.getEndNodes2().contains(currentDot)){
                // Helper to build path using Predecessor list and final node
                path = buildPath(predecessors,startNode,currentDot);
                return path;
            }
            //the direction in which the valid neighbours are checked depends on the player ID
            for (Dot neighbour : getValidNeighbors(currentDot)) {
                if (predecessors.containsKey(neighbour)) {
                    continue;
                }
                if(neighbour.getedgeSet().contains(currentDot)){
                    Dot temp = new Dot(neighbour.getplace(),startNode.getTeam(),currentDot.getWeight());
                    prioQ.add(new Dot(temp,board));
                }else{
                    Dot temp = new Dot(neighbour.getplace(), startNode.getTeam(),neighbour.getWeight()+currentDot.getWeight() + 1);
                    prioQ.add(new Dot(temp,board));
                }
                predecessors.put(neighbour, currentDot);
            }
        }
        return path;
    }


    /**
     * buildPath method is used after baeStarSearch to return a list containing dot objects that iterates through
     * the predecessors maps and adds it to the pathlist until the end dot is reached. To keep the order of the
     * path accurate, the while loop iterates using the endNode and getting its predecessors.
     *
     * @param predecessors HashMap containing the two dots that are linked to one another
     * @param startDot the Starting dot for our path
     * @param endDot the ending dot for our path
     * @return a list containing dot objects
     */
    private List<Dot> buildPath(HashMap<Dot,Dot> predecessors, Dot startDot, Dot endDot) {
        ArrayList<Dot> path_list = new ArrayList<>();
        Dot curr = endDot;
        //iterate through the predecessors until the start node is reached
        while(curr != startDot){
            path_list.add(curr);
            curr = predecessors.get(curr);
        }
        path_list.add(startDot);
        return path_list;
    }


    /**
     * This method returns a total of 4 possible neighbors in the directions North, South, East and West.
     * A Dot is considered a 'valid neighbor' if and only if there is no obstruction when going from the Dot
     * specified in the paramter to the Dot that is being checked for validity.
     *
     * @param place the 'place' from which the four directions N, S, W, and E are checked
     * @return a List containing Dot objects.
     */
    private List<Dot> getValidNeighbors(Dot place) {

        //Get graphical neighbors that are valid
        //Test for validity of the neighbors
        int row = place.getplace().getRow();
        int col = place.getplace().getCol();
        List<Dot> neighbors = new ArrayList<>();
        int team = place.getTeam();
        // Red Team
        if(team == 1){
            // East Direction
            // Do I have an east neighbor? (Graphically)
            if(col != board.getDim()*2){
                Dot NE = board.getNode(new Coordinate(row  - 1,
                                col +1));
                Dot SE = board.getNode(new Coordinate(row +1, col +1));
                // Is this an open connection?
                if(!NE.getedgeSet().contains(SE)){
                    // the connection is open, now add a node
                    neighbors.add(board.getNode(new Coordinate(row, col + 2)));
                }
            }
            if(!board.getStartNodes1().contains(place)) {
                // West Direction
                // Do I have a west neighbor?
                if (col != 0) {
                    Dot NW = board.getNode(new Coordinate(row - 1,
                                    col - 1));
                    Dot SW = board.getNode(new Coordinate(row + 1, col - 1));
                    // Is this an open connection?
                    if (!NW.getedgeSet().contains(SW)) {
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row, col - 2)));
                    }
                }
                // North Direction
                // Do I have a north neighbor?
                if (row != 1) {
                    Dot NE = board.getNode(new Coordinate(row - 1,
                                    col + 1));
                    Dot NW =board.getNode(new Coordinate(row - 1, col - 1));
                    // Is this an open connection?
                    if (!NE.getedgeSet().contains(NW)) {
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row - 2, col)));
                    }
                }
                // South Direction
                // Do I have a south neighbor?
                if (row != board.getDim() * 2 - 1) {
                    Dot SE = board.getNode(new Coordinate(row + 1,
                                    col + 1));
                    Dot SW = board.getNode(new Coordinate(row + 1, col - 1));
                    // Is this an open connection?
                    if (!(SE.getedgeSet().contains(SW))){
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row + 2, col)));
                    }
                }
            }
        }
        //Blue Team
        else{
            if(!board.getStartNodes2().contains(place)) {
                // East Direction
                // Do I have an east neighbor? (Graphically)
                if (col != board.getDim() * 2 - 1) {
                    // Is this an open connection?
                    if (!(board.getNode(new Coordinate(row + 1,
                            col + 1)).getedgeSet().contains(board.getNode(new Coordinate(row - 1, col + 1))))) {
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row, col + 2)));
                    }
                }
                // West Direction
                // Do I have an West neighbor? (Graphically)
                if (col != 1) {
                    // Is this an open connection?
                    if (!(board.getNode(new Coordinate(row - 1,
                            col - 1)).getedgeSet().contains(board.getNode(new Coordinate(row + 1, col - 1))))) {
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row, col - 2)));
                    }
                }
                // North Direction
                // Do I have a north neighbor?
                if (row != 0) {
                    // Is this an open connection?
                    if (!(board.getNode(new Coordinate(row - 1,
                            col + 1)).getedgeSet().contains(board.getNode(new Coordinate(row - 1, col - 1))))) {
                        // the connection is open, now add a node
                        neighbors.add(board.getNode(new Coordinate(row - 2, col)));
                    }
                }
            }
            // South Direction
            // Do I have a South neighbor?
            if(row != board.getDim()*2+1){
                // Is this an open connection?
                if(!(board.getNode(new Coordinate(row  +1,
                        col +1)).getedgeSet().contains(board.getNode(new Coordinate(row +1, col -1))))){
                    // the connection is open, now add a node
                    neighbors.add(board.getNode(new Coordinate(row + 2, col)));
                }
            }
        }
        return neighbors;
    }
}
