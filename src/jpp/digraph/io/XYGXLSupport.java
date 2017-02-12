package jpp.digraph.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jpp.digraph.exceptions.EdgeNotExistsException;
import jpp.digraph.exceptions.InvalidEdgeException;
import jpp.digraph.graph.CostEdge;
import jpp.digraph.graph.DiGraph;
import jpp.digraph.graph.XYNode;

public class XYGXLSupport extends GXLSupport<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>> {

	public static void main(String[] args)
			throws InvalidEdgeException, ParserConfigurationException, IOException, SAXException, TransformerException {

		InputStream is = null;
		try {
			is = new FileInputStream("/HOME/s323895/Downloads/sample.gxl");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		DiGraph graph = new XYGXLSupport().read(is);

		System.out.println(graph);
		is.close();

		// --------------------------------------------------------
		OutputStream os = new FileOutputStream("/HOME/s323895/Downloads/sampleWrite.gxl");
		new XYGXLSupport().write(graph, os);
		os.close();
	}

	@Override
	public DiGraph<XYNode, CostEdge<XYNode>> createGraph() {
		DiGraph graph = null;
		try {
			graph = new DiGraph(nodes, edges);
		} catch (InvalidEdgeException e) {
			e.getStackTrace();
		}

		//System.out.println("Ngraph:" + graph.getNodeCount() + "\nEgraph:" + graph.getEdgeCount() + "\n");

		return graph;
	}

	@Override
	public XYNode createNode(Node element) {

		Element eNode = (Element) element;
		String nodeId = eNode.getAttribute("id");

		NodeList nodeAttr = ((Element) eNode).getElementsByTagName("attr");
		String description = null;
		Integer x = null;
		Integer y = null;

		for (int i = 0; i < nodeAttr.getLength(); ++i) {
			Element eAttr = (Element) nodeAttr.item(i);
			NodeList tempInt = eAttr.getElementsByTagName("int");
			NodeList tempString = eAttr.getElementsByTagName("string");
			if (eAttr.getAttribute("name").equals("x") && tempInt.getLength() != 0) {
				x = Integer.parseInt(tempInt.item(0).getTextContent());
			}
			if (eAttr.getAttribute("name").equals("y") && tempInt.getLength() != 0) {
				y = Integer.parseInt(tempInt.item(0).getTextContent());
			}

			if (eAttr.getAttribute("name").equals("description") && tempString.getLength() != 0) {
				description = tempString.item(0).getTextContent();
			}
		}

		if (description == null) {
			return new XYNode(nodeId, x, y);
		}
		return new XYNode(nodeId, x, y, description);
	}

	@Override
	public CostEdge<XYNode> createEdge(Node element) {
		Element eEdge = (Element) element;

		String id = eEdge.getAttribute("id");
		String from = eEdge.getAttribute("from");
		String to = eEdge.getAttribute("to");

		NodeList edgeAttr = ((Element) eEdge).getElementsByTagName("attr");
		String description = null;
		Double cost = null;

		for (int i = 0; i < edgeAttr.getLength(); ++i) {
			Element eAttr = (Element) edgeAttr.item(i);
			NodeList tempInt = eAttr.getElementsByTagName("float");
			NodeList tempString = eAttr.getElementsByTagName("string");

			if (eAttr.getAttribute("name").equals("cost") && tempInt.getLength() != 0) {
				cost = Double.parseDouble(tempInt.item(0).getTextContent());
			}
			if (eAttr.getAttribute("name").equals("description") && tempString.getLength() != 0) {
				description = tempString.item(0).getTextContent();
			}
		}

		XYNode source = findNodeById(from);
		XYNode target = findNodeById(to);
		if (description == null) {
			return new CostEdge<XYNode>(id, source, target, cost);
		}
		return new CostEdge<XYNode>(id, source, target, cost, description);
	}

	public XYNode findNodeById(String id) {
		for (XYNode node : nodes) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public Element createElement(Document doc, XYNode node) {
		Element nodeName = doc.createElement("node");
		nodeName.setAttribute("id", node.getId());

		Element description = doc.createElement("attr");
		nodeName.appendChild(description);
		description.setAttribute("name", "description");
		Element descrValue = doc.createElement("string");
		descrValue.setTextContent(node.getDescription());
		description.appendChild(descrValue);

		Element x = doc.createElement("attr");
		nodeName.appendChild(x);
		x.setAttribute("name", "x");
		Element xValue = doc.createElement("int");
		xValue.setTextContent(String.valueOf(node.getX()));
		x.appendChild(xValue);

		Element y = doc.createElement("attr");
		nodeName.appendChild(y);
		y.setAttribute("name", "y");
		Element yValue = doc.createElement("int");
		yValue.setTextContent(String.valueOf(node.getY()));
		y.appendChild(yValue);

		return nodeName;
	}

	@Override
	public Element createElement(Document doc, CostEdge<XYNode> edge) {
		Element edgeName = doc.createElement("edge");
		edgeName.setAttribute("id", edge.getId());
		edgeName.setAttribute("from", edge.getSource().getId());
		edgeName.setAttribute("to", edge.getTarget().getId());

		Element description = doc.createElement("attr");
		edgeName.appendChild(description);
		description.setAttribute("name", "description");
		Element descrValue = doc.createElement("string");
		descrValue.setTextContent(edge.getDescription());
		description.appendChild(descrValue);

		Element cost = doc.createElement("attr");
		edgeName.appendChild(cost);
		cost.setAttribute("name", "cost");
		Element costValue = doc.createElement("float");
		costValue.setTextContent(String.valueOf(edge.getCost()));
		cost.appendChild(costValue);

		return edgeName;
	}

}
