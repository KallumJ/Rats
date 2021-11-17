package io;

import java.util.List;
import java.util.Map;

public class XMLNode {
    private final String nodeName;
    private final String nodeValue;
    private final Map<String, String> attributes;
    private final List<XMLNode> children;

    public XMLNode(String nodeName, String nodeValue, Map<String, String> attributes, List<XMLNode> children) {
        this.nodeName = nodeName;
        this.nodeValue = nodeValue;
        this.attributes = attributes;
        this.children = children;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<XMLNode> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return this.children != null;
    }

    public boolean hasAttributes() {
        return this.attributes != null;
    }
}
