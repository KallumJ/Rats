package io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to read elements and their respective values from a given XML file
 *
 * @author Kallum Jones 2005855
 */
public class XMLFileReader {
    private final Element rootElement;

    /**
     * Constructs an XMLFileReader object
     * @param file The file this XMLFileReader will read from
     */
    public XMLFileReader(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            this.rootElement = document.getDocumentElement();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            throw new RuntimeException("Failed to load the the provided XML file", ex);
        }
    }

    /**
     * A method to get each subsequent element in the list from the position of the previous
     *
     * @param elements The list of elements to go drilldown through
     * @return The final element in the list as an Element object
     */
    public Element drilldownToElement(String... elements) {
        try {
            Element current = this.rootElement;

            // Go through every level of elements, and get the next element in the list
            for (String element : elements) {
                current = (Element) current.getElementsByTagName(element).item(0);
            }
            return current;
        } catch (NullPointerException ex) {
            throw new RuntimeException("The provided drilldown path is invalid", ex);
        }

    }

    public XMLNode getAsXMLNode() {
        return getAsXMLNodeRecurse(this.rootElement);
    }

    private XMLNode getAsXMLNodeRecurse(Node element) {

        // Get the children that are elements
        List<XMLNode> children = new ArrayList<>();
        if (element.hasChildNodes()) {
            for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                Node child = element.getChildNodes().item(i);

                // Exclude nodes marked #text, as these arent relevant
                if (!child.getNodeName().contains("#")) {
                    children.add(getAsXMLNodeRecurse(child));
                }
            }
        }

        // If no children are found, make children null
        if (children.isEmpty()) {
            children = null;
        }

        // Get all the attributes for a node, if there are none, null
        Map<String, String> attributes = new HashMap<>();
        if (element.hasAttributes()) {
            for (int i = 0; i < element.getAttributes().getLength(); i++) {
               Node attribute = element.getAttributes().item(i);
               attributes.put(attribute.getNodeName(), attribute.getNodeValue());
            }
        } else {
            attributes = null;
        }

        // If the node has an actual value, get it, if not, null.
        String nodeValue = null;
        String firstChildValue = element.getFirstChild().getNodeValue();
        if (firstChildValue != null && !firstChildValue.contains("\n")) {
            nodeValue = element.getFirstChild().getNodeValue();
        }

        return new XMLNode(element.getNodeName(), nodeValue, attributes, children);
    }

}
