package jpp.digraph.search;

import java.util.Collection;
import java.util.List;

import jpp.digraph.exceptions.NodeNotExistsException;
import jpp.digraph.graph.ICostEdge;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.INode;

/**
 * Interface fuer Suchalgorithmus auf IDiGraph.
 * 
 * @param <G> Graphentyp
 * @param <N> Knotentyp
 * @param <E> Pfeiltyp
 */
public interface IDiGraphSearch<G extends IDiGraph<N, E>, N extends INode, E extends ICostEdge<N>> {

    /**
     * Gibt einen Weg vom Startknoten zum Zielknoten zurueck.
     * 
     * @param graph Graph
     * @param source Startknoten
     * @param target Zielknoten
     * @return Weg als Liste von Pfeilen, null wenn kein Weg existiert
     * @throws NodeNotExistsException Ein Knoten existiert nicht
     */
    public List<E> search(G graph, N source, N target)
            throws NodeNotExistsException;

    /**
     * Fuegt einen IDiGraphSearchListener hinzu.
     * 
     * @param listener Listener
     */
    public void addListener(IDiGraphSearchListener<N, E> listener);

    /**
     * Entfernt einen Listener.
     * 
     * @param listener Listener
     */
    public void removeListener(IDiGraphSearchListener<N, E> listener);

    /**
     * Gibt die Listener zurueck.
     * 
     * @return Collection der Listener
     */
    public Collection<IDiGraphSearchListener<N, E>> getListeners();

}
