package jpp.digraph.exceptions;

import jpp.digraph.graph.IEdge;
import jpp.digraph.graph.INode;

public class InvalidEdgeException extends DiGraphException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IEdge<? extends INode> edge;

	public InvalidEdgeException(IEdge<? extends INode> edge) {
		this.edge = edge;
	}

	public InvalidEdgeException(String message, IEdge<? extends INode> edge) {
		super(message);
		this.edge = edge;
	}

	public IEdge<? extends INode> getEdge() {
		return this.edge;
	}
}
