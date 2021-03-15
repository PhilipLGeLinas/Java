package com.company;

import java.util.TreeMap;

public class InternalNode extends Node {

    private InternalNode suffixLink;
    private TreeMap<Character, Node> children;

    public InternalNode() {
        super();
        this.children = new TreeMap<>();
    }

    public InternalNode getSuffixLink() {
        return this.suffixLink;
    }

    public void setSuffixLink(InternalNode sl) {
        this.suffixLink = sl;
    }

    public TreeMap<Character, Node> getChildren() {
        return this.children;
    }

    public Node getChild(char c) {
        return this.children.get(c);
    }

    public void setChild(char c, Node n) {
        this.children.put(c, n);
    }
}
