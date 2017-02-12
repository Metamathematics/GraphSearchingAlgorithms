package jpp.digraph.graph;
 
/**
 * Interface fuer einen Knoten.
 */
public interface INode {
	
    /**
     * gibt die ID eines Knoten zurueck.
     * 
     * @return ID
     */
    public String getId();
 
    /**
     * gibt die Beschreibung eines Knoten zurueck.
     * 
     * @return Beschreibung
     */
    public String getDescription();
}