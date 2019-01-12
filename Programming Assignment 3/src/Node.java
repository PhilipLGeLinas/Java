import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Node {

    private int loadFactor;
    private int weight;
    private String value;
    private Map<Node, Integer> connections;
    private Queue<Packet> queue;

    public Node(String s) {
        this.loadFactor = 0;
        this.weight = 0;
        this.value = s;
        this.connections = new HashMap<>();
        this.queue = new LinkedList<>();
    }

    public String getValue() {
        return this.value;
    }

    public Map<Node, Integer> getConnections() {
        return this.connections;
    }

    public Queue<Packet> getQueue() {
        return this.queue;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLoadFactor() {
        return this.loadFactor;
    }

    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    public void incrementLoadFactor() {
        this.loadFactor++;
    }

    public void decrementLoadFactor() {
        this.loadFactor--;
    }

}
