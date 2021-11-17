package io;

import java.util.List;
import java.util.Map;

/**
 * A class to model an XML node for writing to files
 *
 * @author Kallum Jones 2005855
 */
public class XMLNode {
    private final String nodeName;
    private final String nodeValue;
    private final Map<String, String> attributes;
    private final List<XMLNode> children;

    /**
     * Constructs an XMLNode with the provided data
     * @param nodeName The name of the node, required
     * @param nodeValue The value of the node, optional, provide null if not present
     * @param attributes The attributes of the node as a map of strings, optional, provide null if not present
     * @param children The children of the node as a list of XMLNode, optional, provide null if not present
     */
    public XMLNode(String nodeName, String nodeValue, Map<String, String> attributes, List<XMLNode> children) {
        this.nodeName = nodeName;
        this.nodeValue = nodeValue;
        this.attributes = attributes;
        this.children = children;
    }

    /**
     * Returns the XMLNode name
     * @return the XMLNode's name as String
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Returns the XMLNode's value
     * @return the XMLNode's value as a String
     */
    public String getNodeValue() {
        return nodeValue;
    }

    /**
     * Returns the XMLNode's attributes
     * @return the XMLNode's attributes, as a Map of strings
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Returns the XMLNode's children
     * @return the XMLNode's children as a List of XMLNode
     */
    public List<XMLNode> getChildren() {
        return children;
    }

    /**
     * Returns whether this node has children
     * @return true or false
     */
    public boolean hasChildren() {
        return this.children != null;
    }

    /**
     * Returns whether this node has attributes
     * @return true or false
     */
    public boolean hasAttributes() {
        return this.attributes != null;
    }

    /**
     * Returns whether this node has a value
     * @return true or false
     */
    public boolean hasValue() {
        return this.nodeValue != null;
    }
}