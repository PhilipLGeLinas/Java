import java.beans.VetoableChangeListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Graph {

	HashMap<Integer, Vertex> _vertices = new HashMap<>();
	
	public void addVertex(Vertex vertex)
	{
		_vertices.put(vertex.getId(), vertex);
	}
	
	//MA #8 TODO: IMPLEMENT!
	public HashMap<Vertex, Integer> computeShortestPath(Vertex start)
	{
		// Holds known distances
		HashMap<Vertex, Integer> distances = new HashMap<>();
		
		// Underlying heap
		PriorityQueue<Vertex> dijkstra_queue = new PriorityQueue<>(new PathWeightComparator());
		
		// Reset start's path weight.
		start.setPathWeight(0);

		// Make sure that the starting vertex is in the graph.
		// Push on the starting vertex.
		dijkstra_queue.add(start);
			
		// While queue not empty,
		// push on outgoing edges that haven't been discovered.
		// Top of heap not known (in distances)? Make it known.
		// Push on outgoing edges. Not known? Add it to the heap.
		while (!dijkstra_queue.isEmpty()) {
			start = dijkstra_queue.peek();
			dijkstra_queue.remove(start);
			HashMap<Vertex, Integer> edges = start.getEdges();
			for (Vertex v: edges.keySet()) {
				Vertex temp = new Vertex(v);
				temp.setPathWeight(start.getPathWeight() + edges.get(temp));
				dijkstra_queue.add(temp);
			}
			if (!distances.containsKey(start))
				distances.put(start, start.getPathWeight());
		}
		return distances;
	}
}

