package jpp.digraph.graph;

public class CostEdge<N extends INode> implements ICostEdge<N> {

	public static void main(String[] args) {
		XYNode node = new XYNode("Node", 1, 2, "A very Cool Node");
		XYNode node2 = new XYNode("Node", 3, 4);
		System.out.println(node.equals(node2));

		CostEdge<XYNode> edge = new CostEdge<XYNode>("edge", node, node2, 12.0,"s");
		System.out.println(edge);

	}

	final String id;
	N source;
	N target;

	double cost;
	String decription;

	public CostEdge(String id, N source, N target, double cost) {
		super();
		if (id == null || id.equals(""))
			throw new IllegalArgumentException();
		this.id = id;
		this.source = source;
		this.target = target;
		this.cost = cost;
		this.decription = "";
	}

	public CostEdge(String id, N source, N target, double cost, String decription) {
		super();
		if (id == null || id.equals(""))
			throw new IllegalArgumentException();
		this.id = id;
		this.source = source;
		this.target = target;
		this.cost = cost;
		this.decription = decription;
	}

	public N getSource() {
		return source;
	}

	public N getTarget() {
		return target;
	}

	public double getCost() {
		return cost;
	}

	public String getDecription() {
		return decription;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getDescription() {
		return this.decription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostEdge<?> other = (CostEdge<?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString2() {
		String tostr = "  [CostEdge id(" + id + ") cost=" + cost;
		if (!this.decription.equals("")){
			tostr += ", decription: " + decription ;
		}
		tostr += ", source= " + source.getId() + ", target= " + target.getId() + "]";
		return tostr;
	}
	
	@Override
	public String toString() {
		String tostr = " [" + id + ",cost=" + cost;
		tostr += "," + source.getId() + "-" + target.getId() + "]\n";
		return tostr;
	}


}
