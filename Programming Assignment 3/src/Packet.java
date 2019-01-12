import java.util.Queue;

public class Packet {

    private char character;
    private int arrivalTime;
    private Queue<Node> path;

    public Packet(char character) {
        this.character = character;
        this.arrivalTime = 0;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Queue<Node> getPath() {
        return this.path;
    }

    public void setPath(Queue<Node> path) {
        this.path = path;
    }

    public void incrementArrivalTime() {
        this.arrivalTime++;
    }

    public void decrementArrivalTime() {
        this.arrivalTime--;
    }

    public char getCharacter() {
        return this.character;
    }

}
