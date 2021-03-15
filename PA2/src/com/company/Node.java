package com.company;

public abstract class Node {

    private int id, stringDepth;
    private Node parent;
    private String edgeLabel;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStringDepth() {
        return this.stringDepth;
    }

    public void setStringDepth(int depth) {
        this.stringDepth = depth;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node p) {
        this.parent = p;
    }

    public String getEdgeLabel() {
        return this.edgeLabel;
    }

    public void setEdgeLabel(String el) {
        this.edgeLabel = el;
    }

    public int getSize() {
        return edgeLabel.length() * 2 + 16;
    }
}
