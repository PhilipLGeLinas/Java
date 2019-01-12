// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #7

import java.util.Scanner;
import java.io.PrintStream;

// Class QuestionTree can be used to 
// support a game of 20 Questions
public class QuestionTree {

   private QuestionNode overallRoot; // the top node of the question tree
   private Scanner console; // reads input from the user
   
   // post: creates a question tree with 
   //       only the word "computer"
   public QuestionTree() {
      console = new Scanner(System.in);
      overallRoot = new QuestionNode("computer");
   }
   
   // pre : file used must be in standard format and legal
   // post: runs the read method using an input 
   //       scanner and the overallRoot
   public void read(Scanner input) {
      input.nextLine();
      overallRoot.data = input.nextLine();
      read(input, overallRoot);
   }
      
   // pre : file used must be in standard format and legal
   // post: replaces the current tree with another tree 
   //       from a separate file
   private void read(Scanner input, QuestionNode root) {
      if(input.hasNextLine()) {
         String line = input.nextLine();
         if(line.equals("Q:")) {
            root.left = new QuestionNode(input.nextLine());
            read(input, root.left);
            if(input.hasNextLine()) {
               line = input.nextLine();
               if(line.equals("Q:")) {
                  root.right = new QuestionNode(input.nextLine());
                  read(input, root.right);
               } else if (line.equals("A:")) {
                  if(root.left == null) {
                     root.left = new QuestionNode(input.nextLine());
                  } else {
                     root.right = new QuestionNode(input.nextLine());
                  }
               }
            }
         } else if (line.equals("A:")) {
            root.left = new QuestionNode(input.nextLine());
            input.nextLine();
            root.right = new QuestionNode(input.nextLine());
         }
      }
   }
   
   // post: runs the write method using the 
   //       PrintStream and overallRoot
   public void write(PrintStream output) {
      write(output, overallRoot);
   }
   
   // post: stores the current tree to an 
   //       output file in standard format
   private void write(PrintStream output, QuestionNode root) {
      if(root.data != null) {
         if (root.left != null) {
            output.println("Q:");
            output.println(root.data);
            write(output, root.left);
            write(output, root.right);
         } else {
            output.println("A:");
            output.println(root.data);
         }
      }
   }
   
   // post: runs the askQuestions method 
   //       using the overallRoot
   public void askQuestions() {
      askQuestions(overallRoot);
   }
   
   // post: asks the user yes/no questions until correctly guessed
   //       or until failure (a failed guess results in the user
   //       adding a new object and question to enhance the tree)
   private void askQuestions(QuestionNode root) {
      if(root.left == null && root.right == null) {
         gameEnd(root);
      } else {
         System.out.print(root.data + " (y/n)? ");
         String response = console.nextLine();
         if (response.equals("y")) {
            askQuestions(root.left);
         } else {
            askQuestions(root.right);
         }
      }
   }
   
   // post: makes a guess at the end of the game and prompts the user to
   //       add a new object and question if the guess is incorrect
   private void gameEnd(QuestionNode root) {
      System.out.print("Would your object happen to be " + root.data + "? (y/n)? ");
      String response = console.nextLine();
      if(response.equals("y")) {
         System.out.println("Great, I got it right!");
      } else {
         System.out.print("What is the name of your object? ");
         String object = console.nextLine();
         System.out.print("Please give me a yes/no question that");
         System.out.print("\ndistinguishes between your object");
         System.out.print("\nand mine--> ");
         String question = console.nextLine();
         QuestionNode currentData = new QuestionNode(root.data);
         root.data = question;
         System.out.print("And what is the answer for your object? (y/n)? ");
         response = console.nextLine();
         if(response.equals("y")) {
            root.left = new QuestionNode(object);
            root.right = currentData;
         } else {
            root.right = new QuestionNode(object);
            root.left = currentData;
         }
      }
   }
   
   // post: asks the user a question, forcing and answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}