package Players.jrf6023;

import Interface.Coordinate;

import java.util.*;

/**
 * Class representing a node in a graph. The node's place, a string,
 * is the only value it contains. edgeSet are stored internally
 * in a collection.
 *
 * This class does not define equals or hashCode. It is making the
 * assumption that each node is described by exactly one Node object.
 *
 * @author atd Aaron T Deever
 * @author jeh James E Heliotis
 */
public class Dot {

    /*
     *  place associated with this node.
     */
    private Coordinate place;
    /*
     * edgeSet of this node are stored as a list (adjacency list).
     */
    private List < Dot > edgeSet;

    private int team;

    /**
     * Constructor.  Initialized with an empty list of edgeSet.
     *
     * @param place string representing the place associated with the node.
     */
    Dot( Coordinate place , int team) {
        this.place = place;
        this.edgeSet = new ArrayList<>();
        this.team = team;
    }

    public int getTeam() {
        return team;
    }

    /**
     * Get the String place associated with this object.
     *
     * @return place.
     */
    public Coordinate getplace() {
        return this.place;
    }

    /**
     * Add a neighbor to this node.  Checks if already present, and does not
     * duplicate in this case.
     *
     * @param n node to add as neighbor.
     */
    public void addNeighbor( Dot n ) {
        if ( !this.edgeSet.contains( n ) ) {
            this.edgeSet.add( n );
        }
    }

    /**
     * Return the adjacency list for this node containing all
     * of its edgeSet.
     *
     * @return an immutable version of the list of edgeSet of the given node
     */
    List< Dot > getedgeSet() {
        return edgeSet;
    }

    /**
     * Generate a string associated with the node, including the
     * place of the node followed by the places of its edgeSet.
     *
     * @return string associated with the node.
     */
    @Override
    public String toString() {
        String result = place.toString();
        for(Dot elem : edgeSet){
            result += " " + elem.place.toString();
        }
        return result;

    }

    /**
     * Compare this node to some other object.
     * @param o the object being compared to this node
     * @return true iff the other object is also a Dot
     *         with the same place.
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        return place.equals( ((Dot)o).place );
    }

    /**
     * Compute a hash code for this node.
     * @return a hash code computed from this node's place
     */
    @Override
    public int hashCode() {
        return this.place.hashCode();
    }
}
