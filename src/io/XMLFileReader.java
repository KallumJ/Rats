package io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

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
        Element current = this.rootElement;
        for (String element : elements) {
            current = (Element) current.getElementsByTagName(element).item(0);
        }
        return current;
    }
}
