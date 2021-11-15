package io;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class XMLFileWriter {
    private final XMLEventWriter writer;
    private final XMLEventFactory eventFactory;
    private String rootNodeName;
    private final File file;

    public XMLFileWriter(File file, String rootNode) {
        try {
            this.rootNodeName = rootNode;
            this.file = file;
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            this.writer = outputFactory.createXMLEventWriter(new FileOutputStream(this.file));
            this.eventFactory = XMLEventFactory.newInstance();

            StartDocument startDocument = eventFactory.createStartDocument();
            addNode(startDocument);

            StartElement rootElement = eventFactory.createStartElement("", "", rootNodeName);
            addNode(rootElement);

        } catch (XMLStreamException | FileNotFoundException ex) {
            throw new RuntimeException(String.format("Unable to create an XML file %s with the root %s", file.getName(), rootNodeName), ex);
        }

    }

    public void saveAndClose() {
        try {
            addNode(this.eventFactory.createEndElement("", "", rootNodeName));
            addNode(this.eventFactory.createEndDocument());
            this.writer.close();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(String.format("Unable to save XML file %s with the root %s", file.getName(), rootNodeName), ex);
        }

    }

    private void addNode(XMLEvent node)  {
        try {
            XMLEvent newLine = eventFactory.createDTD("\n");
            writer.add(node);
            writer.add(newLine);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(String.format("Unable to add node %s to file %s", node, file.getName()), ex);
        }

    }

    public void writeNode(String nodeName, String nodeValue, Map<String, String> attributes) {

        try {
            XMLEvent tab = eventFactory.createDTD("\t");

            // Create list of attributes
            ArrayList<Attribute> attributesList = new ArrayList<>();
            attributes.forEach((attribute, value) -> {
                Attribute attributeNode = eventFactory.createAttribute(attribute, value);
                attributesList.add(attributeNode);
            });

            // Create and write the start of the element with the attributes
            StartElement startElement = eventFactory.createStartElement("", "", nodeName, attributesList.iterator(), null);
            addNode(tab);
            addNode(startElement);

            // Create and write the value of the element
            Characters value = eventFactory.createCharacters(nodeValue);
            writer.add(value);

            // Create and write the end of the element
            EndElement endElement = eventFactory.createEndElement("", "", nodeName);
            addNode(endElement);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(String.format("Unable to write %s to the xml file", nodeValue), ex);
        }

    }

}
