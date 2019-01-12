import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class PA3_main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("What is the relative path of your file?");
        String fileName = "/Users/philiplgelinas/233Rep_Philip/Program Assignment 3/src/" + input.nextLine();

        List<String> values = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        while (reader.ready()) {
            String string = reader.readLine();
            if (string.split(" ").length == 1) {
                values.add(string);
            } else {
                edges.add(string);
            }
        }

        Map<String, Node> nodes = new HashMap<>();
        constructNodes(nodes, values);
        addEdges(nodes, edges);

        Message message = new Message();
        Queue<Node> shortestPath = findShortestPath(nodes, message.getSource(), message.getDestination());

        transmitMessage(nodes, message, shortestPath);
    }

    public static void constructNodes(Map<String, Node> nodes, List<String> values) {

        for (String s: values) {
            Node node = new Node(s);
            nodes.put(s, node);
        }
    }

    public static void addEdges(Map<String, Node> nodes, List<String> edges) {

        for (String edge: edges) {
            String[] strings = edge.split(" ");
            String source = strings[0];
            String destination = strings[1];
            int weight = Integer.parseInt(strings[2]);
            nodes.get(source).getConnections().put(nodes.get(destination), weight);
        }
    }

    public static Queue<Node> findShortestPath(Map<String, Node> nodes, String source, String destination) {

        Node sourceNode = nodes.get(source);
        Queue<Node> result = new LinkedList<>();
        Queue<Node> pq = new PriorityQueue<>(new NodeComparator());
        pq.add(sourceNode);
        List<String> visited = new ArrayList<>();

        while (!visited.contains(destination)) {
            sourceNode = pq.remove();
            while (visited.contains(sourceNode.getValue()))
                sourceNode = pq.remove();
            Map<Node, Integer> connections = sourceNode.getConnections();
            for (Node connection: connections.keySet())
                connection.setWeight(connections.get(connection));
            for (Node connection: connections.keySet())
                pq.add(connection);
            visited.add(sourceNode.getValue());
            result.add(sourceNode);
        }
        return result;
    }

    public static void transmitMessage(Map<String, Node> nodes, Message message, Queue<Node> shortestPath) {

        List<Packet> result = new ArrayList<>();
        List<Packet> packets = message.getPackets();
        int messageLength = packets.size();
        Node sourceNode = nodes.get(message.getSource());

        while (result.size() < messageLength) {
            if (packets.size() > 0) {
                Packet p = packets.remove(0);
                Queue<Node> copy = new LinkedList<>(shortestPath);
                p.setPath(copy);
                p.getPath().remove();
                p.setArrivalTime(p.getPath().peek().getWeight() * p.getPath().peek().getLoadFactor() + 1);
                sourceNode.getQueue().add(p);
                sourceNode.incrementLoadFactor();
            }
            for (Node node: shortestPath) {
                Queue<Packet> packetQueue = node.getQueue();
                for (Packet packet: packetQueue) {
                    packet.decrementArrivalTime();
                    if (packet.getArrivalTime() <= 0) {
                        node.decrementLoadFactor();
                        packetQueue.remove(packet);
                        if (packet.getPath().size() == 1) {
                            result.add(packet);
                        } else {
                            Queue<Node> path = packet.getPath();
                            Node nextNode = path.remove();
                            nextNode.getQueue().add(packet);
                            packet.setArrivalTime(path.peek().getWeight() * path.peek().getLoadFactor());
                            nextNode.incrementLoadFactor();
                        }
                    }
                }
            }
        }
        for (Packet p: result)
            System.out.print(p.getCharacter());
    }
}
