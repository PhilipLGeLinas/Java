package com.company;

public class SuffixTree {

    private InternalNode root;

    public SuffixTree() {
        InternalNode in = new InternalNode();
        in.setEdgeLabel("");
        in.setParent(null);
        in.setStringDepth(0);
        in.setSuffixLink(in);
        this.root = in;
    }

    public InternalNode getRoot() {
        return this.root;
    }
}
