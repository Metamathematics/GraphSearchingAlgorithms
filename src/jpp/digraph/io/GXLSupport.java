package jpp.digraph.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
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

import jpp.digraph.exceptions.InvalidEdgeException;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.IEdge;
import jpp.digraph.graph.INode;

public abstract class GXLSupport<G extends IDiGraph<N, E>, N extends INode, E extends IEdge<N>>
		implements IGXLSupport<G, N, E> {

	public List<N> nodes = new LinkedList<N>();
	public List<E> edges = new LinkedList<E>();

	@Override
	public G read(InputStream is) throws ParserConfigurationException, IOException, SAXException, InvalidEdgeException {
		
		this.nodes.clear();
		this.edges.clear();
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		Node root = doc.getDocumentElement();

		Node graph = ((Element) root).getElementsByTagName("graph").item(0);
		NodeList listOfNodes = ((Element) graph).getElementsByTagName("node");

		for (int i = 0; i < listOfNodes.getLength(); ++i) {
			N node = createNode(listOfNodes.item(i));
			nodes.add(node);
			// System.out.println(nodes.get(nodes.size()-1));
		}

		NodeList listOfEdges = ((Element) graph).getElementsByTagName("edge");

		for (int i = 0; i < listOfEdges.getLength(); ++i) {
			E edge = createEdge(listOfEdges.item(i));
			edges.add(edge);
			// System.out.println(edges.get(edges.size()-1));
		}

		//System.out.println("Nxml:" + nodes.size());
		//System.out.println("Exml:" + edges.size());
		return createGraph();
	}

	@Override
	public void write(G graph, OutputStream os)
			throws ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
		// TODO Auto-generated method stub
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = doc.createElement("gxl");
		doc.appendChild(root);
		Element graphName = doc.createElement("graph");
		graphName.setAttribute("id", "id1");
		root.appendChild(graphName);
		
		Collection<N> nodes = graph.getNodes();
		Collection<E> edges = graph.getEdges();

		for (N node : nodes) {
			Element xmlNode = createElement(doc, node);
			graphName.appendChild(xmlNode);
		}
		for (E edge : edges) {
			Element xmlEdge = createElement(doc, edge);
			graphName.appendChild(xmlEdge);
		}

		DOMSource domSource = new DOMSource(doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		transformer.transform(domSource, new StreamResult(os));
	}

	@Override
	public abstract G createGraph();

	@Override
	public abstract N createNode(Node element);

	@Override
	public abstract E createEdge(Node element);

	@Override
	public abstract Element createElement(Document doc, N node);

	@Override
	public abstract Element createElement(Document doc, E edge);

}
