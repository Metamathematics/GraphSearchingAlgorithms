package jpp.digraph.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jpp.digraph.exceptions.NodeNotExistsException;
import jpp.digraph.graph.ICostEdge;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.INode;

public abstract class AbstractAStar<G extends IDiGraph<N, E>, N extends INode, E extends ICostEdge<N>>
		implements IDiGraphSearch<G, N, E> {

	Collection<IDiGraphSearchListener<N, E>> listenersList = new LinkedList<IDiGraphSearchListener<N, E>>();

	public abstract double h(INode node, INode target);

	@Override
	public List<E> search(G graph, N source, N target) throws NodeNotExistsException {
		if (!graph.containsNode(source))
			throw new NodeNotExistsException(source);
		if (!graph.containsNode(target))
			throw new NodeNotExistsException(target);
		if (source.equals(target))
			return new LinkedList<E>();

		List<N> open = new LinkedList<N>();
		List<N> closed = new LinkedList<N>();
		// List<List<E>> expansion = new LinkedList<List<E>>();
		Map<N, List<E>> ways = new HashMap<N, List<E>>();

		Map<N, Double> g = new HashMap<N, Double>();
		Map<N, Double> h = new HashMap<N, Double>();

		// ----------------------------------------------

		open.add(source);
		g.put(source, 0.0);
		h.put(source, h(source, target));
		ways.put(source, new LinkedList<E>());

		while (!open.isEmpty()) {
<<<<<<< .mine
			N min = returnMinNode(open, g, h);
			
			SearchListener<N, E> listener = new SearchListener<N, E>();//-----------
=======

>>>>>>> .r19769
<<<<<<< .mine
			listener.onExpandNode(min, ways.get(min));//----------------------------
			addListener(listener);//------------------------------------------------
			
=======
			N min = returnMinNode(open, g, h);
			// ---------------------------
			for (IDiGraphSearchListener<N, E> listener : listenersList) {
				listener.onExpandNode(min, ways.get(min));
			}

>>>>>>> .r19769
			if (min.equals(target))
				return ways.get(target);

			closed.add(min);
			for (E sucEdge : graph.getSuccessorEdges(min)) {
				N sucNode = sucEdge.getTarget();
				double gCost = g.get(min) + sucEdge.getCost();

				if (!open.contains(sucNode) && !closed.contains(sucNode)) {
					open.add(sucNode);
					g.put(sucNode, gCost);
					h.put(sucNode, h(sucNode, target));
					saveWay(ways, sucEdge);
				} else if (gCost < g.get(sucNode)) {
					g.put(sucNode, gCost);
					h.put(sucNode, h(sucNode, target));
					saveWay(ways, sucEdge);
					if (closed.contains(sucNode)) {
						open.add(sucNode);
						closed.remove(sucNode);
					}
				}

			}

		}

		return null;
	}

	public void saveWay(Map<N, List<E>> ways, E edge) {
		List<E> sucWay = new LinkedList<E>(ways.get(edge.getSource()));
		sucWay.add(edge);
		ways.put(edge.getTarget(), sucWay);
	}

	public N returnMinNode(List<N> open, Map<N, Double> g, Map<N, Double> h) {
		N minNode = open.get(0);
		double min = g.get(minNode) + h.get(minNode);

		for (N tempNode : open) {
			if (g.get(tempNode) + h.get(tempNode) < min) {
				minNode = tempNode;
			}
		}
		open.remove(minNode);
		return minNode;
	}

	@Override
	public void addListener(IDiGraphSearchListener<N, E> listener) {
		listenersList.add(listener);
	}

	@Override
	public void removeListener(IDiGraphSearchListener<N, E> listener) {
		listenersList.remove(listener);
	}

	@Override
	public Collection<IDiGraphSearchListener<N, E>> getListeners() {
		return listenersList;
	}

}
