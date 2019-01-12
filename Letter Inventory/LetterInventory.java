// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #1

// Class LetterInventory can be used to store a modifiable inventory of letters.
public class LetterInventory {

   private int[] inventory; // an inventory of the counts for each letter
   private int size;        // the current number of values stored in the inventory
   private String display;  // a character representation of the inventory
   public static final int ALPHABET_LENGTH = 26;
   
   // post: creates a new inventory of size 26
   public LetterInventory(String data) {
      inventory = new int[ALPHABET_LENGTH];
      size = 0;
      for (int i = 0; i < data.length(); i++) {
         char current = Character.toLowerCase(data.charAt(i));
         if (current >= 'a' && current <= 'a' + 25) {
            inventory[current - 'a']++;
            size++;
         }
      }
   } 

   // post: returns the current number of 
   //       values stored in the inventory
   public int size() {
      return size;
   }

   // post: returns true if the inventory 
   //       is empty and false otherwise
   public boolean isEmpty() {
      return size == 0;
   }

   // pre:  the letter passed must be between a and z, not case-sensitive
   //       (throws IllegalArgumentException if not)
   // post: returns the number of occurrences of the letter
   public int get(char letter) {
      char lowerCase = Character.toLowerCase(letter);
      if (lowerCase >= 'a' && lowerCase < 'a' + ALPHABET_LENGTH) {
         return inventory[Character.toLowerCase(letter) - 'a'];
      } else {
         throw new IllegalArgumentException();
      }
   }
   
   // post: creates a list of the characters 
   //       stored in the inventory
   public String toString() {
      display = "[";
      if (size > 0) {
         for (int i = 0; i < ALPHABET_LENGTH; i++) {
            for (int j = 0; j < inventory[i]; j++) {
               display += (char)('a' + i);
            }
         }
      }
      display += "]";
      return display;
   }
   
   // pre:  letter passed must be between a and z
   //       (throws IllegalArgumentException if not)
   // post: sets the quantity of the letter passed
   //       equal to the value
   public void set(char letter, int value) {
      int number = (int)(Character.toLowerCase(letter));
      if (value > inventory[number - 'a']) {
         size += value - inventory[number - 'a'];
      } else {
         size -= inventory[number - 'a'] - value;
      }
      if (number >= 'a' && number < 'a' + ALPHABET_LENGTH) {
         inventory[number - 'a'] = value;
      } else {
         throw new IllegalArgumentException();
      }
   }
   
   // post: creates and returns a new LetterInventory that represents  
   //       the sum of this inventory and the other inventory
   public LetterInventory add(LetterInventory other) {
      LetterInventory newInventory = new LetterInventory(toString());
      for (int i = 0; i < ALPHABET_LENGTH; i++) {
         newInventory.inventory[i] += other.inventory[i];
      }
      newInventory.size += other.size();
      return newInventory;
   }
   
   // post: creates and returns a new inventory that represents the 
   //       difference of this letter inventory and the other inventory
   //       (if this causes any negative count, returns null)
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory newInventory2 = new LetterInventory(toString());
      for (int i = 0; i < ALPHABET_LENGTH; i++) {
         newInventory2.inventory[i] -= other.inventory[i];
      }
      newInventory2.size -= other.size();
      for (int i = 0; i < ALPHABET_LENGTH; i++) {
         if (newInventory2.inventory[i] < 0) {
            return null;
         }
      }
      return newInventory2;
   }
}