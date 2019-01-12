// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #4

import java.util.*;

// Class HangmanManager allows you to keep track of a game of evil hangman
public class HangmanManager {

   private int correctLetters;    // the number of a guessed letter in the word
   private int guessCount;        // the number of guesses remaining
   private Set<String> words;     // the current set of word available for use
   private Map<String, Set<String>> map; // connects letters guessed to useable words
   private SortedSet<Character> sortedLetters; // a set of the guessed letters
   private String currentPattern; // the current combo of letters guessed, separated by dashes
   

   // pre : length and max must be at least 1 and at least 0, respectively
   //       (throws IllegalArgumentException if not)
   // post: initializes the state of the game, constructing a set of words
   //       of the given length and removing duplicates
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      } else {
         map = new TreeMap<String, Set<String>>();
         words = new TreeSet<String>();
         sortedLetters = new TreeSet<Character>();
         currentPattern = "-";
         guessCount = max;
         for (String s : dictionary) {
            if (s.length() == length) {
               if (!words.contains(s)) {
               words.add(s);
               }
            }
         }
         for (int i = 1; i < length; i++) {
            currentPattern+= " -";
         }
      }
   }
   
   // post: gives the user access to the current set of words
   public Set<String> words() {
      return words;
   }
   
   // post: returns the amount of remaining guesses
   public int guessesLeft() {
      return guessCount;
   }
   
   // post: returns the set of letters already guessed
   public SortedSet<Character> guesses() {
      return sortedLetters;
   }
   
   // pre : the current set of words must not be empty
   //       (throws an IllegalStateException if it is)
   // post: returns the current pattern of characters
   //       that have been correctly guessed and dashes
   //       for characters that have not yet been guessed
   public String pattern() {
      if (words.isEmpty()) {
         throw new IllegalStateException();
      } else {
         return currentPattern;
      }
   }
   
   // pre : number of guesses left must be at least 1 and the set of words
   //       must be nonempty (throws an IllegalStateException if not)
   //       the character being guessed must not have been guessed previously
   //       (throws an IllegalArgumentException if so)
   // post: records the user's guess and determines the set
   //       of words to use, returns that number of occurrences
   //       of the guessed letter in the new pattern and updates
   //       the number of guesses left
   public int record(char guess) {
      if (guessCount < 1 || words.isEmpty()) {
         throw new IllegalStateException();
      } else if (!words.isEmpty() && sortedLetters.contains(guess)) {
         throw new IllegalArgumentException();
      } else {
         sortedLetters.add(guess);
         groupWords();
         Set<String> currentSet = new TreeSet<String>();
         for (String currentKey : map.keySet()) {
            if (map.get(currentKey).size() > currentSet.size()) {
               currentSet.clear();
               currentSet.addAll(map.get(currentKey));
               words = currentSet;
               currentPattern = currentKey;
            }
         }
         if (!guessCount(guess)) {
            guessCount--;
         }
         correctLetters(guess);
         return correctLetters;
      }
   }
   
   // post: links each remaining word in the dictionary to a sequence
   //       of letters and dashes based on the user's guesses
   private void groupWords() {
      map.clear();
      for (String word : words) {
         String key = "";
         if (sortedLetters.contains(word.charAt(0))) {
            key += word.charAt(0);
         } else {
            key += "-";
         }
         for (int i = 1; i < word.length(); i++) {
            if (sortedLetters.contains(word.charAt(i))) {
               key += " " + word.charAt(i);
            } else {
               key += " -";
            }
         }
         if (!map.containsKey(key)) {
            map.put(key, new TreeSet<String>());
            map.get(key).add(word);
         } else {
            map.get(key).add(word);
         }
      }
   }
   
   // post: determines how many of the letter guessed
   //       occur in the word
   private void correctLetters(char guess) {
      correctLetters = 0;
      for (int i = 0; i < currentPattern.length(); i++) {
         if (currentPattern.charAt(i) == guess) {
            correctLetters++;
         }
      }
   }
   
   // post: determines whether the letter guessed occurs in the word
   private boolean guessCount(char guess) {
      for (int i = 0; i < currentPattern.length(); i++) {
         if (currentPattern.charAt(i) == guess) {
            return true;
         }
      }
      return false;
   }
}
