package Players.jrf6023;

import Interface.Coordinate;

import java.util.*;

import Players.allStar.*;

/**
 * @author jon frey
 */
public class BoardGraph implements Graph< Dot > {

    /**
     * nodeMap - internal structure of the graph, mapping Coordinate to Dot.
     * startNodes1 - Dots with a coordinate in the first column
     * endNodes1 - Dots with a coordinate in the last column
     * startNodes2 - Dots with a coordinate in the first row
     * endNodes2 - Dots with a coordinate in the last row
     * dim - Dimension param of the internal graph
     */
    private final Map<Coordinate, Dot > nodeMap;
    private ArrayList<Dot> startNodes1;
    private ArrayList<Dot> endNodes1;
    private ArrayList<Dot> startNodes2;
    private ArrayList<Dot> endNodes2;
    private int dim;
    /**
     * Create an empty graph.
     */
    public BoardGraph(int dim) {
        this.nodeMap = new HashMap<>();
        this.startNodes1 = new ArrayList<>();
        this.startNodes2 = new ArrayList<>();
        this.endNodes1 = new ArrayList<>();
        this.endNodes2 = new ArrayList<>();
        this.dim = dim;
    }

    /** {@inheritDoc} */
    @Override
    public Iterable< Dot > getNodes() {

        Iterator< Dot > underlying = this.nodeMap.values().iterator();
        return () -> new Iterator< Dot >() {
            @Override
            public boolean hasNext() {
                return underlying.hasNext();
            }

            @Override
            public Dot next() {
                return underlying.next();
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNode( Coordinate nodeName ) {
        return this.nodeMap.containsKey( nodeName );
    }

    /** {@inheritDoc} */
    @Override
    public Dot getNode( Coordinate nodeName ) {
        return this.nodeMap.get( nodeName );
    }

    /** {@inheritDoc} */
    @Override
    public Coordinate getNodeName( Dot node ) {
        for ( Coordinate key: this.nodeMap.keySet() ) {
            if ( this.nodeMap.get( key ).equals( node ) ) {
                return key;
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void addNeighbor( Dot node, Dot neighbor ) {
        node.addNeighbor( neighbor );
    }

    /** {@inheritDoc} */
    @Override
    public List< Dot > getNeighbors( Dot node ) {
        return node.getedgeSet();
    }

    public ArrayList<Dot> getStartNodes1() {
        return startNodes1;
    }

    public ArrayList<Dot> getStartNodes2() {
        return startNodes2;
    }

    public ArrayList<Dot> getEndNodes1() {
        return endNodes1;
    }

    public ArrayList<Dot> getEndNodes2() {
        return endNodes2;
    }

    /** {@inheritDoc} */
    /**
     * Sorts new nodes into specific lists if they meet the criteria of the lists.
     */
    @Override
    public Dot makeNode( Coordinate name , int team) {
        Dot result = new Dot( name , team);
        this.nodeMap.put( name, result );
        if(team == 2){
            if(name.getRow() == 0){
                startNodes2.add(result);
            }
            else if(name.getRow() == dim*2){
                endNodes2.add(result);
            }
        }
        else if(team == 1){
            if(name.getCol() == 0){
                startNodes1.add(result);
            }
            else if(name.getCol() == dim*2){
                endNodes1.add(result);
            }
        }
        return result;
    }

    /**
     * Generate a Coordinate associated with the graph. The Coordinate
     * is comprised of one line for each node in the graph, which is
     * unconventionally large for a method overriding the
     * {@link Object#toString()} method.
     *
     * @return Coordinate associated with the graph.
     */
    @Override
    public String toString() {
        String result = "";
        for ( Coordinate place : this.nodeMap.keySet() ) {
            result = result + this.nodeMap.get( place ) + "\n";
        }
        return result;
    }

}
