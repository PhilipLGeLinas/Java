// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #8

import java.io.PrintStream;
import java.util.*;

// Class HuffmanTree can be used to support the encoding and
// decoding of a text file from UTF-8 to binary and vice versa
public class HuffmanTree {

   private HuffmanNode overallRoot; // the top node of the huffman tree
   private Scanner console; // reads input from the user
   
   // post: creates a huffman tree using the given 
   //       frequencies of characters from a UTF-8 file
   public HuffmanTree(int[] count) {
      PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            queue.add(new HuffmanNode(i, count[i]));
         }
      }
      queue.add(new HuffmanNode(count.length, 1)); // node representing end of file
      while (queue.size() > 1) {
         HuffmanNode left = queue.remove();
         HuffmanNode right = queue.remove();
         if (queue.size() == 0) {
            this.overallRoot = new HuffmanNode(left.value + right.value, left, right);
         } else {
            HuffmanNode node = new HuffmanNode(left.value + right.value, left, right);
            queue.add(node);
         }
      }
   }
   
   // post: reconstructs the huffman
   //       tree from an encoded file
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode();
      while (input.hasNextLine()) {
         HuffmanNode currentRoot = overallRoot;
         int key = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
               if (currentRoot.left == null) {
                  currentRoot.left = new HuffmanNode(key);
                  currentRoot = currentRoot.left;
               } else {
                  currentRoot = currentRoot.left;
               }
            } else {
               if (currentRoot.right == null) {
                  currentRoot.right = new HuffmanNode(key);
                  currentRoot = currentRoot.right;
               } else {
                  currentRoot = currentRoot.right;
               }
            }
         }
         
      }
   }
   
   // post: runs the write method using the given 
   //       PrintStream the overallRoot, and an empty string
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   // post: writes the tree to an output file in standard format
   private void write(PrintStream output, HuffmanNode root, String binary) {
      if (root.left == null) {
         output.println(root.key);
         output.println(binary);
      } else {
         write(output, root.left, binary + "0");
         write(output, root.right, binary + "1");
      }
   }
   
   // post: decodes a binary file into UTF-8 format
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode currentRoot = overallRoot;
      int currentKey = eof + 1;
      while (currentKey != eof) {
         while (currentRoot.left != null) {
            int bit = input.readBit();
            if (bit == 0) {
               currentRoot = currentRoot.left;
            } else {
               currentRoot = currentRoot.right;
            }
         }
         if (currentRoot.key != eof) {
            output.write(currentRoot.key);
            currentRoot = overallRoot;
         } else {
            currentKey--;
         }
      }
   }
}