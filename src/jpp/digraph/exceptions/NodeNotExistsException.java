package jpp.digraph.exceptions;

import jpp.digraph.graph.INode;

public class NodeNotExistsException extends DiGraphException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6966073781612685288L;
	private INode node;

	public NodeNotExistsException(INode node) {
		this.node = node;
	}

	public NodeNotExistsException(String message, INode node) {
		super(message);
		this.node = node;
	}

	public INode getNode() {
		return this.node;
	}
}
