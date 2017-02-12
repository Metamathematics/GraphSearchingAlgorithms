package jpp.digraph.graph;

/**
 * Interface fuer eine bewertete Kante.
 *
 * @param <N> Knotentyp
 */
public interface ICostEdge<N extends INode> extends IEdge<N> {

    /**
     * Gibt die Kosten der Kante zurueck.
     * 
     * @return Kosten
     */
    public double getCost();
  
}