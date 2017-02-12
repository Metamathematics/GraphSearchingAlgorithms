package jpp.digraph.search;

import jpp.digraph.graph.ICostEdge;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.INode;

public class Dijkstra<G extends IDiGraph<N, E>, N extends INode, E extends ICostEdge<N>>
		extends AbstractAStar<G, N, E> {

	@Override
	public double h(INode node, INode target) {
		return 0;
	}
	
	
}
