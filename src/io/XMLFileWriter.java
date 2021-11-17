package io;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class to write XML files to the disk
 *
 * @author Kallum Jones 2005855
 */
public class XMLFileWriter {
    private final XMLEventWriter writer;
    private final XMLEventFactory eventFactory;
    private String rootNodeName;
    private final FileOutputStream fileOutputStream;

    /**
     * Constructs an XMLFileWriter object
     * @param file The file to write too
     * @param rootNode The name of the root node in this xml file
     */
    public XMLFileWriter(File file, String rootNode) {
        try {
            this.rootNodeName = rootNode;
            this.fileOutputStream = new FileOutputStream(file);
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            this.writer = outputFactory.createXMLEventWriter(this.fileOutputStream);
            this.eventFactory = XMLEventFactory.newInstance();

            // Start the document
            StartDocument startDocument = eventFactory.createStartDocument();
            writer.add(startDocument);

            StartElement rootElement = eventFactory.createStartElement("", "", rootNodeName);
            writer.add(rootElement);

        } catch (XMLStreamException | FileNotFoundException ex) {
            throw new RuntimeException(String.format("Unable to create an XML file %s with the root %s", file.getName(), rootNodeName), ex);
        }

    }

    /**
     * A method to save the file that has thus far been written, and close the writer
     */
    public void saveAndClose() {
        // End the document, and close the writer
        try {
            writer.add(this.eventFactory.createEndElement("", "", rootNodeName));
            writer.add(this.eventFactory.createEndDocument());
            this.writer.close();
            this.fileOutputStream.close();
        } catch (XMLStreamException | IOException ex) {
            throw new RuntimeException(String.format("Unable to save XML file with the root %s", rootNodeName), ex);
        }

    }

    /**
     * A method to write a provided XMLNode to the file
     * @param xmlNode the XMLNode to write to the file
     */
    public void writeNode(XMLNode xmlNode) {
        try {
            // Create list of attributes
            ArrayList<Attribute> attributesList = new ArrayList<>();
            if (xmlNode.hasAttributes()) {
                xmlNode.getAttributes().forEach((attribute, value) -> {
                    Attribute attributeNode = eventFactory.createAttribute(attribute, value);
                    attributesList.add(attributeNode);
                });
            }

            // Create and write the start of the element with the attributes
            StartElement startElement = eventFactory.createStartElement("", "", xmlNode.getNodeName(), attributesList.iterator(), null);
            writer.add(startElement);

            // Create and write the value of the element
            if (xmlNode.hasValue()) {
                Characters value = eventFactory.createCharacters(xmlNode.getNodeValue());
                writer.add(value);
            }

            // Write the children if the node has any
            if (xmlNode.hasChildren()) {
                for (XMLNode child : xmlNode.getChildren()) {
                    writeNode(child);
                }
            }

            // Create and write the end of the element
            EndElement endElement = eventFactory.createEndElement("", "", xmlNode.getNodeName());
            writer.add(endElement);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(String.format("Unable to write %s to the xml file", xmlNode.getNodeName()), ex);
        }

    }

}
