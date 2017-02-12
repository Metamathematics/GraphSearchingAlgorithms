package jpp.digraph.graph;

/**
 * Interface fuer eine Kante zwischen zwei Knoten vom Typ N.
 *
 * @param <N> Typ der Knoten
 */
public interface IEdge<N extends INode> {
    
    /**
     * gibt die ID einer Kante zurück.
     * 
     * @return ID
     */
    public String getId();
	
    /**
     * Gibt die Beschreibung einer Kante zurueck.
     * 
     * @return Beschreibung
     */
    public String getDescription();
    
    /**
     * Gibt den Quellknoten zurueck.
     * 
     * @return Startknoten
     */
    public N getSource();
    
    /**
     * Gibt den Zielknoten zurueck.
     * 
     * @return Zielknoten
     */
    public N getTarget();
}