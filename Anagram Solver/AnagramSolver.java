// Philip GeLinas
// CSE 143 AS
// with Michael Gofman
// Assignment #6

import java.util.*;

// Class AnagramSolver can be used to print a 
// list of possible anagrams of a given word
public class AnagramSolver {
                                             // a map of each word to a
   private Map<String, LetterInventory> map; //LetterInventory version
   private List<String> storage; // a list of potential words to be displayed
   private List<String> displayArray; // a list of words that are displayed
   private List<String> list; // a list of words in the given dictionary
   
	// post: creates a new anagram solver, 
	//       using the given list as a dictionary
   public AnagramSolver(List<String> list) {
      map = new HashMap<String, LetterInventory>();
      storage = new ArrayList<String>();
      displayArray = new ArrayList<String>();
      this.list = list;
   }
   
	// pre : the 'max' value must be at least 0
	//       (throws IllegalArgumentException if not)
	// post: finds and prints combinations of words that 
	//       are anagrams of the word or phrase passed,
	//       including at most 'max' words (unlimited if max is 0)
   public void print(String phrase, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      } else if (max == 0) {
         max--;
      }
      String phraseArray[] = phrase.split("[ ]+");
      phrase = "";
      for (String value : phraseArray) {
         phrase += value;
      }
      LetterInventory phraseInventory = new LetterInventory(phrase);
      List<String> miniList = new ArrayList<String>();
      for (String word : list) {
         LetterInventory inventory = new LetterInventory(word);
         if (phraseInventory.subtract(inventory) != null) {
            map.put(word, inventory);
            miniList.add(word);
         }
      }
      build(phraseInventory, max, miniList);
      int size = displayArray.size();
      for (int i = 0; i < size; i++) {
         System.out.println(displayArray.get(0));
         displayArray.remove(0);
      }
   }

   // post: builds a list of anagrams of the given word or phrase
   private void build(LetterInventory phraseInventory, int max, List<String> miniList) {
      if (phraseInventory.isEmpty()) {
         String display = "[" + storage.get(0);
         if (!storage.isEmpty()) {
            for (int i = 1; i < storage.size(); i++) {
               display += ", " + storage.get(i);
            }
         }  
         display += "]";
         displayArray.add(display);
      } else {
         for (String word : miniList) {
            if (phraseInventory.subtract(map.get(word)) != null && max != 0) {
               storage.add(word);
               build(phraseInventory.subtract(map.get(word)), max - 1, miniList);
               storage.remove(word);
            }
         }
      }
   }
}