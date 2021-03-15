package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int NID = 0;
    private static double INCount = 0, INSD = 0;
    private static ArrayList<Character> BWT;
    private static String sequenceHolder;
    private static int[] stats; // internal nodes, leaves, total number of nodes,
    // size of the tree (in bytes), average string-depth of an internal node, string-depth of the deepest internal node

    public static void main(String[] args) throws FileNotFoundException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("$ ");
            String[] input = scanner.nextLine().split("[\\s\\t]+");

            File sequenceFile = new File(input[0]);
            scanner = new Scanner(sequenceFile);
            scanner.nextLine();
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine())
                sb.append(scanner.nextLine());
            sb.append("$");
            String sequence = sb.toString();
            sequenceHolder = sequence;

            File alphabetFile = new File(input[1]);
            scanner = new Scanner(alphabetFile);
            sb = new StringBuilder();
            while(scanner.hasNextLine())
                sb.append(scanner.nextLine());
            String[] alphabet = sb.toString().split("[\\s\\t]+");

            SuffixTree st = buildSuffixTree(sequence, alphabet);
            printDFS(st.getRoot());
        }
    }

    public static SuffixTree buildSuffixTree(String sequence, String[] alphabet) {
        SuffixTree st = new SuffixTree();

        while (!sequence.equals("")) {
            findPath(st.getRoot(), sequence);
            sequence = sequence.substring(1);
        }

        return st;
    }

    public static void findPath(InternalNode node, String str) {
        if (node.getChildren().containsKey(str.charAt(0))) {
            Node child = node.getChild(str.charAt(0));
            String edgeLabel = child.getEdgeLabel();
            StringBuilder sb = new StringBuilder();
            while (true) {
                if (edgeLabel.equals("")) {
                    findPath((InternalNode) child, str);
                } else {
                    if (str.charAt(0) == edgeLabel.charAt(0)) {
                        sb.append(edgeLabel.charAt(0));
                        edgeLabel = edgeLabel.substring(1);
                        str = str.substring(1);
                    } else {
                        child.setEdgeLabel(edgeLabel);
                        InternalNode newNode = new InternalNode();
                        newNode.setChild(edgeLabel.charAt(0), child);
                        newNode.setParent(node);
                        child.setParent(newNode);
                        String newEdgeLabel = sb.toString();
                        newNode.setEdgeLabel(newEdgeLabel);
                        newNode.setStringDepth(node.getStringDepth() + newEdgeLabel.length());
                        node.setChild(newEdgeLabel.charAt(0), newNode);
                        LeafNode ln = new LeafNode();
                        ln.setId(NID++);
                        ln.setParent(newNode);
                        ln.setEdgeLabel(str);
                        ln.setStringDepth(newNode.getStringDepth() + str.length());
                        newNode.setChild(str.charAt(0), ln);
                        return;
                    }
                }
            }
        } else {
            LeafNode ln = new LeafNode();
            ln.setEdgeLabel(str);
            ln.setId(NID++);
            ln.setParent(node);
            ln.setStringDepth(node.getStringDepth() + str.length());
            node.setChild(str.charAt(0), ln);
        }
    }

    public static void printDFS(Node root) {
        BWT = new ArrayList<>();
        stats = new int[5];
        printDFSHelper(root);
        INSD /= INCount;
        System.out.println("BWT: " + BWT.toString());
        System.out.println("Stats:");
        System.out.println("    Internal Nodes: " + stats[0]);
        System.out.println("    Leaf Nodes: " + stats[1]);
        System.out.println("    Total Nodes: " + stats[2]);
        System.out.println("    Tree Size (bytes): " + stats[3]);
        System.out.println("    Average Internal Node String Depth: " + Math.floor((INSD / INCount) * 100) / 100);
        System.out.println("    Deepest Internal Node String Depth: " + stats[4]);

    }

    public static void printDFSHelper(Node node) {
        stats[2]++; stats[3] += node.getSize();
        if (node instanceof InternalNode) {
            node.setId(NID++);
            INCount += 1.0;
            stats[0]++;
            INSD += node.getStringDepth();
            if (stats[4] < node.getStringDepth())
                stats[4] = node.getStringDepth();
            System.out.print("Internal Node #: ");
        } else {
            stats[1]++;
            System.out.print("    Leaf Node #: ");
        }
        System.out.println(node.getId() + ", String Depth: " + node.getStringDepth() + ", Edge Label: " + node.getEdgeLabel());
        if (node instanceof InternalNode) {
            for (Node n : ((InternalNode) node).getChildren().values()) {
                printDFSHelper(n);
            }
        }
        if (node instanceof LeafNode) {
            int index = node.getId() - 1;
            if (index < 0)
                index += sequenceHolder.length();
            BWT.add(sequenceHolder.charAt(index));
        }
    }
}
