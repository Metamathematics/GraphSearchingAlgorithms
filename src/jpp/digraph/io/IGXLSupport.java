package jpp.digraph.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import jpp.digraph.exceptions.InvalidEdgeException;
import jpp.digraph.graph.IDiGraph;
import jpp.digraph.graph.IEdge;
import jpp.digraph.graph.INode;

/**
 * Interface fuer die GXL-Ein-/Ausgabe eines IDiGraph<N,E>.
 *
 * @param <G> Graphtyp
 * @param <N> Knotentyp
 * @param <E> Pfeiltyp
 */
public interface IGXLSupport<G extends IDiGraph<N, E>,
        N extends INode, E extends IEdge<N>> {
    
    /**
     * Einlesen eines mittels GXL definierten Graphen und
     * Erzeugen der Datenstruktur vom Typ G.
     * 
     * @param is InputStream
     * @return Datenstruktur vom Typ G
     * 
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException IOException
     * @throws SAXException SAXException
     * @throws InvalidEdgeException InvalidEdgeException
     */
    public G read(InputStream is) throws ParserConfigurationException,
            IOException, SAXException, InvalidEdgeException;
        
    /**
     * Schreiben einer Datenstruktur von Typ G auf einem 
     * OutputStream mittels GXL.
     * 
     * @param graph Datenstruktur vom Typ G
     * @param os OutputStram
     * 
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException IOException
     * @throws TransformerConfigurationException 
     *         TransformerConfigurationException
     * @throws TransformerException TransformerException
     */
    public void write(G graph, OutputStream os) throws
            ParserConfigurationException, IOException,
            TransformerConfigurationException, TransformerException;
        
    /**
     * Abstrakte Methode welche eine neue leere Graph-Datenstruktur
     * vom Typ G zurueckgeben soll.
     *  
     * @return Datenstruktur vom Typ G
     */
    public G createGraph();
    
    /**
     * Abstrakte Methode welche aus einem org.w3c.dom.Node einen
     * Knoten vom Typ N erzeugen soll.
     * 
     * @param element org.w3c.dom.Node
     * @return Knoten vom Typ N
     */
    public N createNode(Node element);
    
    /**
     * Abstrakte Methode welche aus einem org.w3c.dom.Node
     * einen Pfeil vom Typ E erzeugen soll.
     * 
     * @param element org.w3c.dom.Node
     * @return Pfeil vom Typ E
     */
    public E createEdge(Node element);
    
    /**
     * Abstrakte Methode welche aus einem Knoten vom Typ N
     * mittels doc ein org.w3c.dom.Element erzeugen soll.
     * 
     * @param doc org.w3c.dom.Document
     * @param node Knoten vom Typ N
     * @return org.w3c.dom.Element
     */
    public Element createElement(Document doc, N node);
    
    /**
     * Abstrakte Methode welche aus einem Pfeil vom Typ E
     * mittels doc ein org.w3c.dom.Element erzeugen soll.
     *  
     * @param doc org.w3c.dom.Document
     * @param edge Pfeil vom Typ E
     * @return org.w3c.dom.Element
     */
    public Element createElement(Document doc, E edge);
}