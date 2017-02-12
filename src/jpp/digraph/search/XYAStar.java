package jpp.digraph.search;

import jpp.digraph.graph.ICostEdge;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.INode;
import jpp.digraph.graph.IXYNode;
import jpp.digraph.graph.XYNode;

public class XYAStar<G extends IDiGraph<N, E>, N extends INode, E extends ICostEdge<N>> extends AbstractAStar<G, N, E> {

	public double h(IXYNode node, IXYNode target) {
		return euclideanDistance(node.getX(), node.getY(), target.getX(), target.getY());
	}

	public double euclideanDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	@Override
	public double h(INode node, INode target) {
		return h((IXYNode)node, (IXYNode)target);
	}

}
