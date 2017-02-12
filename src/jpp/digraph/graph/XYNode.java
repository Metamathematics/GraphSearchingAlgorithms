package jpp.digraph.graph;

public class XYNode implements IXYNode {

	public static void main(String[] args) {
		XYNode node = new XYNode("First Node", 1, 2, "A very Cool Node");
		XYNode node2 = new XYNode("First Node", 3, 4);
		System.out.println(node.equals(node2));
		
	}

	int x;
	int y;
	final String id;
	String decription;

	public XYNode(String id, int x, int y, String decription) {
		super();
		if (id == null || id.equals(""))
			throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
		this.id = id;
		this.decription = decription;
	}

	public XYNode(String id, int x, int y) {
		super();
		if (id == null || id.equals(""))
			throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
		this.id = id;
		this.decription = "";
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
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
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
		XYNode other = (XYNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

/*
	@Override
	public String toString() {
		String tostr = "  XYNode id(" + id + ")xy(" + x + "," + y + ")";
		if (!this.decription.equals("")){
			tostr += ", decription: " + decription;
		}
		
		return tostr;
	}
	*/
	@Override
	public String toString() {
		return id;
	}

}
