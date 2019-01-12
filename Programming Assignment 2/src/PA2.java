import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class PA2 {

	//PA #2 TODO: finds the smallest tree in a given forest, allowing for a single skip
	//Finds the smallest tree (by weight) in the supplied forest.  
	//Note that this function accepts a second optional parameter of an index to skip.  
	//Use this index to allow your function to also find the 2nd smallest tree in the 
	//forest.
	//DO NOT change the first findSmallestTree function. Only work in the second one!
	public static int findSmallestTree(List<HuffmanTree<Character>> forest)
	{
		return findSmallestTree(forest, -1); //find the real smallest
	}
	public static int findSmallestTree(List<HuffmanTree<Character>> forest, int index_to_ignore) 
	{
		int smallestWeight = Integer.MAX_VALUE;
		for(int i = 0; i < forest.size(); i++) {
			if(i != index_to_ignore) {
				int weight = forest.get(i).getWeight();
				if(weight < smallestWeight) {
					smallestWeight = weight;
				}
			}
		}
		return smallestWeight; //find the smallest except the index to ignore.
	}

	//PA #2 TODO: Generates a Huffman character tree from the supplied text
	//Builds a Huffman Tree from the supplied list of strings.
	//This function implement's Huffman's Algorithm as specified in page 
	//435 of the book.	
	public static HuffmanTree<Character> huffmanTreeFromText(List<String> data) {
		//In order for your tree to be the same as mine, you must take care 
		//to do the following:
		//1.	When merging the two smallest subtrees, make sure to place the 
		//      smallest tree on the left side!
		//2.	Have the newly created tree take the spot of the smallest 
		//		tree in the forest(e.g. list.set(smallest_index, merged_tree) ).
		//3.	Use list.remove(second_smallest_index) to remove 
		//      the other tree from the forest.	
		//The lines below are just an example. They are NOT part of the code.
		
		// Construct a map of characters to their frequencies in the txt file
		Map<Character, Integer> map = new HashMap<>();
		for(String line : data) {
			char[] charizard = line.toCharArray();
			for(char c : charizard) {
				if(!map.containsKey(c)) {
					map.put(c, 1);
				} else {
					map.put(c, map.get(c).intValue() + 1);
				}
			}
		}
		
		// Construct a list of leaf nodes
		List<HuffmanNode<Character>> nodes = new ArrayList<>();
		for(char c : map.keySet()) {
			nodes.add(new HuffmanLeafNode<Character>(c, map.get(c)));
		}
		
		HuffmanNode<Character> smallestNode;
		HuffmanNode<Character> secondSmallestNode;
		
		// Iterate through leaf nodes and piece together the smallest weighted nodes
		while(nodes.size() > 1) {
			smallestNode = nodes.get(0).getWeight() <= nodes.get(1).getWeight() ? 
					nodes.get(0) : nodes.get(1);
			secondSmallestNode = smallestNode.equals(nodes.get(0)) ? 
					nodes.get(1) : nodes.get(0);
			for(int i = 2; i < nodes.size(); i++) {
				HuffmanNode<Character> node = nodes.get(i);
				int weight = node.getWeight();
				if(weight < smallestNode.getWeight()) {
					secondSmallestNode = smallestNode;
					smallestNode = node;
				} else if(weight < secondSmallestNode.getWeight()) {
					secondSmallestNode = node;
				}
			}
			nodes.add(new HuffmanInternalNode<Character>(smallestNode, secondSmallestNode));
			nodes.remove(smallestNode);
			nodes.remove(secondSmallestNode);
		}
		return new HuffmanTree<Character>((HuffmanInternalNode<Character>)nodes.get(0));
				
		//note that root is a HuffmanNode instance. This type cast would only work 
		//if you are sure that root is not a leaf node.
		//Vice versa, for this assignment, you might need to force type cast a HuffmanNode
		//to a HuffmanLeafNode when you are sure that what you are getting is a HuffmanLeafNode.
		//The line below is just an example on how to do forced casting. It is NOT part of the code.
		//HuffmanInternalNode<Character> i_root = (HuffmanInternalNode<Character>) root;  
		//return null;
	}

	//PA #2 TODO: Generates a Huffman character tree from the supplied encoding map
	//NOTE: I used a recursive helper function to solve this!
	public static HuffmanTree<Character> huffmanTreeFromMap(Map<Character, String> huffmanMap) {
		//Generates a Huffman Tree based on the supplied Huffman Map.Recall that a 
		//Huffman Map contains a series of codes(e.g. 'a' = > 001).Each digit(0, 1) 
		//in a given code corresponds to a left branch for 0 and right branch for 1.
		
		HuffmanInternalNode<Character> root = new HuffmanInternalNode<Character>(null, null);
		for(char c : huffmanMap.keySet()) {
			root = huffmanTreeFromMapHelper(root, c, huffmanMap.get(c));
		}
		return new HuffmanTree<Character>(root);
	}
	
	public static HuffmanInternalNode<Character> huffmanTreeFromMapHelper(HuffmanInternalNode<Character> node, char value, String bits) {
		if(bits.length() > 1) {
			if(bits.charAt(0) == '0') {
				if(node.getLeftChild() == null)
					node.setLeftChild(new HuffmanInternalNode<Character>(null, null));
				node.setLeftChild(huffmanTreeFromMapHelper((HuffmanInternalNode<Character>)node.getLeftChild(), value, bits.substring(1)));
			} else {
				if(node.getRightChild() == null)
					node.setRightChild(new HuffmanInternalNode<Character>(null, null));
				node.setRightChild(huffmanTreeFromMapHelper((HuffmanInternalNode<Character>)node.getRightChild(), value, bits.substring(1)));
			}
		} else {
			if(bits.charAt(0) == '0') {
				node.setLeftChild(new HuffmanLeafNode<Character>(value, -1));
			} else {
				node.setRightChild(new HuffmanLeafNode<Character>(value, -1));
			}
		}
		return node;
	}

	//PA #2 TODO: Generates a Huffman encoding map from the supplied Huffman tree
	//NOTE: I used a recursive helper function to solve this!
	public static Map<Character, String> huffmanEncodingMapFromTree(HuffmanTree<Character> tree) {
		//Generates a Huffman Map based on the supplied Huffman Tree.  Again, recall 
		//that a Huffman Map contains a series of codes(e.g. 'a' = > 001).Each digit(0, 1) 
		//in a given code corresponds to a left branch for 0 and right branch for 1.  
		//As such, a given code represents a pre-order traversal of that bit of the 
		//tree.  I used recursion to solve this problem.
		
		Map<Character, String> result = new HashMap<>();
		return huffmanEncodingMapFromTreeHelper((HuffmanInternalNode<Character>)tree.getRoot(), result, "");
	}
	
	public static Map<Character, String> huffmanEncodingMapFromTreeHelper(HuffmanInternalNode<Character> root, Map<Character, String> result, String location) {
		if(root.getLeftChild().isLeaf()) {
			HuffmanLeafNode<Character> leaf = (HuffmanLeafNode<Character>)root.getLeftChild();
			result.put(leaf.getValue(), location + "0");
		}
		if(root.getRightChild().isLeaf()) {
			HuffmanLeafNode<Character> leaf = (HuffmanLeafNode<Character>)root.getRightChild();
			result.put(leaf.getValue(), location + "1");
		}
		if(!root.getLeftChild().isLeaf() && root.getLeftChild() != null)
			result = huffmanEncodingMapFromTreeHelper((HuffmanInternalNode<Character>)root.getLeftChild(), result, location + "0");
		if(!root.getRightChild().isLeaf() && root.getRightChild() != null)
			result = huffmanEncodingMapFromTreeHelper((HuffmanInternalNode<Character>)root.getRightChild(), result, location + "1");
		return result;
	}

	//PA #2 TODO: Writes an encoding map to file.  Needed for decompression.
	public static void writeEncodingMapToFile(Map<Character, String> huffmanMap, String file_name) throws IOException {
		//Writes the supplied encoding map to a file.  My map file has one 
		//association per line (e.g. 'a' and 001).  Each association is separated by 
		//a sentinel value.  In my case, I went with a double pipe (||).
		BufferedWriter writer = new BufferedWriter(new FileWriter(file_name));
		for(char c : huffmanMap.keySet()) {
			writer.write(c + "||" + huffmanMap.get(c));
			writer.newLine();
		}
		writer.close();
	}

	//PA #2 TODO: Reads an encoding map from a file.  Needed for decompression.
	public static Map<Character, String> readEncodingMapFromFile(String file_name) throws IOException {
		//Creates a Huffman Map from the supplied file.Essentially, this is the 
		//inverse of writeEncodingMapToFile. Use String.split() function - note that
		//the split() function takes a Regular Expression as an input, not a "string" itself. 
		//To separate based on "||", the argument for the function should be: split("\\|\\|")

		Map<Character, String> result = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(file_name));
		while(reader.ready()) {
			String[] keyAndValue = reader.readLine().split("\\|\\|");
			result.put(keyAndValue[0].charAt(0), keyAndValue[1]);
		}
		reader.close();
		return result;
	}

	//PA #2 TODO: Converts a list of bits (bool) back into readable text using the supplied Huffman map
	public static String decodeBits(List<Boolean> bits, Map<Character, String> huffmanMap) {
		//Uses the supplied Huffman Map to convert the list of bools (bits) back into text.
		//To solve this problem, I converted the Huffman Map into a Huffman Tree and used 
		//tree traversals to convert the bits back into text.
		
		//Use a StringBuilder to append results. 
		StringBuilder result = new StringBuilder();
		HuffmanTree<Character> tree = huffmanTreeFromMap(huffmanMap);
		HuffmanInternalNode<Character> root = (HuffmanInternalNode<Character>)tree.getRoot();
		for(Boolean bit : bits) {
			if(bit) {
				if(root.getRightChild().isLeaf()) {
					HuffmanLeafNode<Character> leaf = (HuffmanLeafNode<Character>)root.getRightChild();
					result.append(leaf.getValue());
					root = (HuffmanInternalNode<Character>)tree.getRoot();
				} else {
					root = (HuffmanInternalNode<Character>)root.getRightChild();
				}
			} else {
				if(root.getLeftChild().isLeaf()) {
					HuffmanLeafNode<Character> leaf = (HuffmanLeafNode<Character>)root.getLeftChild();
					result.append(leaf.getValue());
					root = (HuffmanInternalNode<Character>)tree.getRoot();
				} else {
					root = (HuffmanInternalNode<Character>)root.getLeftChild();
				}
			}
		}
		return result.toString();
	}

	//PA #2 TODO: Using the supplied Huffman map compression, converts the supplied text into a series of bits (boolean values)
	public static List<Boolean> toBinary(List<String> text, Map<Character, String> huffmanMap) {
		List<Boolean> result = new ArrayList<>();
		StringBuilder bobTheBuilder = new StringBuilder();
		for(String line : text)
			bobTheBuilder.append(line);
		String allText = bobTheBuilder.toString();
		for(char c : allText.toCharArray()) {
			String bits = huffmanMap.get(c);
			for(char bit : bits.toCharArray()) {
				Boolean b = (bit == '0') ? false : true;
				result.add(b);
			}
		}
		return result;
	}

}
