// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #8

// Class HuffmanNode can be used to create an information-storing
// node (to be used with a HuffmanTree)
public class HuffmanNode implements Comparable<HuffmanNode> {

   public int key;           // coded key for the given character
   public int value;         // number of occurrences of the given character
   public HuffmanNode left;  // reference to left huffman node
   public HuffmanNode right; // reference to right huffman node
   
   
   // post: creates a nullified 
   //       huffman node
   public HuffmanNode() {
      this(-1, -1, null, null);
   }
   
   // post: creates a huffman node
   //       with the given key
   public HuffmanNode(int key) {
      this(key, -1, null, null);
   }
   
   // post: creates a huffman node with
   //       the given key & value
   public HuffmanNode(int key, int value) {
      this(key, value, null, null);
   }
   
   // post: creates a huffman node with the given value & linkages
   public HuffmanNode(int value, HuffmanNode left, HuffmanNode right) {
      this(-1, value, left, right);
   }

   // post: creates a huffman node with the given key, value, & linkages
   public HuffmanNode(int key, int value, HuffmanNode left, HuffmanNode right) {
      this.key = key;
      this.value = value;
      this.left = left;
      this.right = right;
   }
   
   // post: compares the values stored in two huffman nodes
   //       by returning their numerical difference
   public int compareTo(HuffmanNode other) {
      return this.value - other.value;
   }
}