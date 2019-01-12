// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #7

// Class QuestionNode can be used to create an information-storing 
// node for a game of 20 Questions (to be used with a QuestionTree)
public class QuestionNode {

   public String data;        // data stored in question node
   public QuestionNode left;  // reference to left question node
   public QuestionNode right; // reference to right question node
   
   // post: creates a leaf node
   //       with the provided data
   public QuestionNode(String data) {
      this(data, null, null);
   }

   // post: creates a branch node with the provided data and linkages
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}