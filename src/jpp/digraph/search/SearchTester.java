package jpp.digraph.search;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jpp.digraph.exceptions.InvalidEdgeException;
import jpp.digraph.exceptions.NodeNotExistsException;
import jpp.digraph.graph.CostEdge;
import jpp.digraph.graph.DiGraph;
import jpp.digraph.graph.XYNode;
import jpp.digraph.io.XYGXLSupport;

public class SearchTester {

	public static void main(String[] args) throws InvalidEdgeException, ParserConfigurationException, IOException,
			SAXException, NodeNotExistsException {

		InputStream is = null;
		try {
			is = new FileInputStream("/HOME/s323895/Downloads/sample.gxl");
			// is = new
			// FileInputStream("C:/Users/Sergio/Desktop/JPP/sample.gxl");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		DiGraph<XYNode, CostEdge<XYNode>> graph = new XYGXLSupport().read(is);

		// System.out.println(graph);
		is.close();

		List<XYNode> nodes = new ArrayList(graph.getNodes());

		IDiGraphSearch<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>> search = new BFS();
		
		  for (XYNode nodeSource : graph.getNodes()) { for (XYNode nodeTarget :
		 graph.getNodes()) { List<CostEdge<XYNode>> way = search.search(graph,
		  nodeSource, nodeTarget); System.out.println("Source " +
		  nodeSource.getId() + ",Target " + nodeTarget.getId() + ": " + way); }
		  }
		 
/*
		XYNode sourceNode = nodes.get(0);
		XYNode targetNode = nodes.get(17);
		List<CostEdge<XYNode>> way = search.search(graph, sourceNode, targetNode);
		System.out.println(sourceNode + " to " + targetNode + " \n" + way);
		//System.out.println(search.getListeners());
*/
	}

	public static void main2(String[] args) {
		Map<String, Integer> map = new HashMap();

		map.put("s1", 1);
		map.put("s2", 2);
		map.put("s3", 3);
		System.out.println(map);
		map.put("s3", 4);
		map.put("s3", 7);
		map.put("s4", 4);
		map.put("s5", 4);

		System.out.println(map);
	}

}
