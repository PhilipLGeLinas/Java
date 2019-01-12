import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Message {

    private String source;
    private String destination;
    private List<Packet> packets;

    public Message() {
        Scanner input = new Scanner(System.in);
        System.out.println("What is your source node?");
        this.source = input.nextLine();
        System.out.println("What is your destination node?");
        this.destination = input.nextLine();
        System.out.println("Please type your message below.");
        char[] charArray = input.nextLine().toCharArray();
        List<Packet> packetList = new ArrayList<>();
        for (char c: charArray)
            packetList.add(new Packet(c));
        this.packets = packetList;
    }

    public String getSource() {
        return this.source;
    }

    public String getDestination() {
        return this.destination;
    }

    public List<Packet> getPackets() {
        return this.packets;
    }

}
