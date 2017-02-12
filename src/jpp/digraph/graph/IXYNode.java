package jpp.digraph.graph;

/**
 * Interface eines Knoten mit X und Y Koordinaten.
 */
public interface IXYNode extends INode {

    /**
     * Gibt die X-Koordinate zurueck.
     * 
     * @return X-Koordinate
     */
    public int getX();
    
    /**
     * Gibt die Y-Koordinate zurueck.
     * 
     * @return Y-Koordinate
     */
    public int getY();
}