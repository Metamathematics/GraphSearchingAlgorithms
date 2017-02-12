package jpp.digraph.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import jpp.digraph.exceptions.EdgeNotExistsException;
import jpp.digraph.exceptions.InvalidEdgeException;
import jpp.digraph.exceptions.NodeNotExistsException;

public class DiGraph<N extends INode, E extends IEdge<N>> implements IDiGraph<N, E> {

	public static void main(String[] args) throws InvalidEdgeException, NodeNotExistsException {

		Collection<XYNode> nodesCol = new ArrayList<XYNode>();
		XYNode node1 = new XYNode("1", 2, 3);
		XYNode node2 = new XYNode("2", 4, 5);
		XYNode node3 = new XYNode("3", 6, 7);
		XYNode node4 = new XYNode("4", 10, 11);
		nodesCol.add(node1);
		nodesCol.add(node2);
		nodesCol.add(node3);

		Collection<CostEdge<XYNode>> edgesCol = new ArrayList<CostEdge<XYNode>>();
		CostEdge<XYNode> edge1 = new CostEdge<XYNode>("1-2", node1, node2, 2.0);
		CostEdge<XYNode> edge2 = new CostEdge<XYNode>("1-3", node1, node3, 3.0);
		CostEdge<XYNode> edge3 = new CostEdge<XYNode>("1-4", node1, node4, 8.0);
		edgesCol.add(edge1);
		edgesCol.add(edge2);

		DiGraph<XYNode, CostEdge<XYNode>> graph = new DiGraph<XYNode, CostEdge<XYNode>>(nodesCol, edgesCol);

		graph.addNode(node4);
		graph.addEdge(edge3);

		System.out.println(graph);
		System.out.println(graph.getSuccessors(node2));

	}

	private Collection<N> nodes = getNewNodeCollection();
	private Collection<E> edges = getNewEdgeCollection();

	public DiGraph(Collection<N> nodes, Collection<E> edges) throws InvalidEdgeException {
		// this.nodes = getChoosenCollection(nodes);
		setNodes(nodes);
		/*
		 * for (E tempEdge : edges) { ifInvalidEdgeThrowException(tempEdge); }
		 */
		// this.edges = getChoosenCollection(edges);
		setEdges(edges);
	}

	public DiGraph(Collection<N> nodes) {
		// this.nodes = getChoosenCollection(nodes);
		setNodes(nodes);
	}

	public DiGraph() {
	}

	@Override
	public int getNodeCount() {
		return nodes.size();
	}

	@Override
	public int getEdgeCount() {
		return edges.size();
	}

	@Override
	public Collection<N> getNodes() {
		return nodes;
	}

	@Override
	public Collection<E> getEdges() {
		return edges;
	}

	@Override
	public void removeNodes() {
		if (edges != null)
			edges.clear();
		if (nodes != null)
			nodes.clear();
	}

	@Override
	public void removeEdges() {
		if (edges != null)
			edges.clear();
	}

	@Override
	public void setNodes(Collection<N> nodes) {
		removeEdges();
		removeNodes();
		// this.nodes = getChoosenCollection(nodes);
		this.nodes = getNewNodeCollection();
		for (N node : nodes) {
			addNode(node);
		}
	}

	@Override
	public void setEdges(Collection<E> edges) throws InvalidEdgeException {
		removeEdges();
		for (E tempEdge : edges) {
			ifInvalidEdgeThrowException(tempEdge);
		}
		// this.edges = getChoosenCollection(edges);
		this.edges = getNewEdgeCollection();
		for (E edge : edges) {
			addEdge(edge);
		}
	}

	@Override
	public void setGraph(Collection<N> nodes, Collection<E> edges) throws InvalidEdgeException {
		setNodes(nodes);
		setEdges(edges);
	}

	@Override
	public boolean containsNode(N node) {
		return nodes.contains(node);
	}

	@Override
	public boolean containsEdge(E edge) {
		return edges.contains(edge);
	}

	@Override
	public boolean containsEdge(N source, N target) {
		for (E tempEdge : this.edges) {
			if (tempEdge.getSource().equals(source) && tempEdge.getTarget().equals(target)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addNode(N node) {
		if (!containsNode(node))
			this.nodes.add(node);
	}

	@Override
	public void addEdge(E edge) throws InvalidEdgeException {

		ifInvalidEdgeThrowException(edge);

		if (!containsEdge(edge)) {
			edges.add(edge);
		}
	}

	@Override
	public void removeNode(N node) throws NodeNotExistsException {
		ifNodeNotExistThrowException(node);
		ArrayList<E> edgesToDelete = new ArrayList<E>();

		for (E tempEdge : this.edges) {
			if (tempEdge.getSource().equals(node) || tempEdge.getTarget().equals(node)) {
				edgesToDelete.add(tempEdge);
			}
		}

		for (E deleteEdge : edgesToDelete) {
			this.edges.remove(deleteEdge);
		}

		this.nodes.remove(node);
	}

	@Override
	public void removeEdge(E edge) throws InvalidEdgeException, EdgeNotExistsException {

		ifEdgeNotExistThrowException(edge);
		ifInvalidEdgeThrowException(edge);

		this.edges.remove(edge);
	}

	@Override
	public Collection<N> getPredecessors(N node) throws NodeNotExistsException {

		ifNodeNotExistThrowException(node);

		Collection<N> predecessorNodes = getNewNodeCollection();
		for (E tempEdge : getPredecessorEdges(node)) {
			predecessorNodes.add(tempEdge.getSource());
		}
		return predecessorNodes;
	}

	@Override
	public Collection<E> getPredecessorEdges(N node) throws NodeNotExistsException {

		ifNodeNotExistThrowException(node);

		Collection<E> predecessorEdges = getNewEdgeCollection();
		for (E tempEdge : this.edges) {
			if (tempEdge.getTarget().equals(node)) {
				predecessorEdges.add(tempEdge);
			}
		}
		return predecessorEdges;
	}

	@Override
	public Collection<N> getSuccessors(N node) throws NodeNotExistsException {

		ifNodeNotExistThrowException(node);

		Collection<N> successorNodes = getNewNodeCollection();

		for (E tempEdge : getSuccessorEdges(node)) {
			if (!successorNodes.contains(tempEdge.getTarget())) {
				successorNodes.add(tempEdge.getTarget());
			}
		}
		return successorNodes;
	}

	@Override
	public Collection<E> getSuccessorEdges(N node) throws NodeNotExistsException {

		ifNodeNotExistThrowException(node);

		Collection<E> successorEdges = getNewEdgeCollection();
		for (E tempEdge : this.edges) {
			if (tempEdge.getSource().equals(node)) {
				successorEdges.add(tempEdge);
			}
		}
		return successorEdges;
	}

	@Override
	public Collection<E> getEdgesBetween(N source, N target) throws NodeNotExistsException {

		ifNodeNotExistThrowException(source);
		ifNodeNotExistThrowException(target);
		Collection<E> edgesBetween = getNewEdgeCollection();
		for (E tempEdge : this.edges) {
			if (tempEdge.getSource().equals(source) && tempEdge.getTarget().equals(target)) {
				edgesBetween.add(tempEdge);
			}
		}
		return edgesBetween;
	}

	@Override
	public String toString() {
		return "DiGraph [\nnodes=" + nodes + "\nedges=" + edges + "\n]";
	}

	// This methods allow to control easily the Exception that are thrown in
	// this class
	public void ifInvalidEdgeThrowException(E edge) throws InvalidEdgeException {
		if (!containsNode(edge.getSource()) || !containsNode(edge.getTarget()))
			throw new InvalidEdgeException(edge);
	}

	public void ifNodeNotExistThrowException(N node) throws NodeNotExistsException {
		if (!containsNode(node))
			throw new NodeNotExistsException(node);
	}

	public void ifEdgeNotExistThrowException(E edge) throws EdgeNotExistsException {
		if (!containsEdge(edge))
			throw new EdgeNotExistsException(edge);
	}

	/*
	 * Collectiontype control methods
	 */

	public Collection<N> getNewNodeCollection() {
		return getChoosenCollection(new ArrayList<N>());
	}

	public Collection<E> getNewEdgeCollection() {
		return getChoosenCollection(new ArrayList<E>());
	}

	public <C> Collection<C> getChoosenCollection(Collection<C> col) {
		/*
		 * With this method its easy to control the Type of the collection used
		 * in the Class with simply changing the Type of the collection in the
		 * return statement
		 */
		return new ArrayList<C>(col);
	}

}
