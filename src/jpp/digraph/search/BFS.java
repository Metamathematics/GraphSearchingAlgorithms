package jpp.digraph.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import jpp.digraph.exceptions.NodeNotExistsException;
import jpp.digraph.graph.ICostEdge;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.INode;

public class BFS<G extends IDiGraph<N, E>, N extends INode, E extends ICostEdge<N>> implements IDiGraphSearch<G, N, E> {

	Collection<IDiGraphSearchListener<N, E>> listenersList = new LinkedList<IDiGraphSearchListener<N, E>>();

	public BFS() {
	}

	@Override
	public List<E> search(G graph, N source, N target) throws NodeNotExistsException {
		if (!graph.containsNode(source))
			throw new NodeNotExistsException(source);
		if (!graph.containsNode(target))
			throw new NodeNotExistsException(target);
		if (source.equals(target))
			return new LinkedList<E>();

		Map<N, List<E>> ways = new HashMap<N, List<E>>();
		Queue<N> queue = new LinkedList<N>();
		List<N> visitedNodes = new LinkedList<N>();

		queue.add(source);
		visitedNodes.add(source);
		ways.put(source, new LinkedList<E>());

		while (!queue.isEmpty()) {

			N c = queue.poll();
			//---------------------------
			for (IDiGraphSearchListener<N, E> listener : listenersList){
			listener.onExpandNode(c, ways.get(c));
			}

			if (c.equals(target)) {
				return ways.get(c);
			}
			for (E sucEdge : graph.getSuccessorEdges(c)) {
				N s = sucEdge.getTarget();
				if (!visitedNodes.contains(s)) {
					visitedNodes.add(s);
					queue.add(s);
					saveWay(ways, sucEdge);
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
