// Philip GeLinas
// CSE 143 AS 
// with Michael Gofman
// Assignment #3

import java.util.*;

// Class AssassinManager allows you to manage a game of Assassin.
public class AssassinManager {
   private AssassinNode killRingFront;
   private AssassinNode graveyardFront;
   
   // pre : the list must contain at least one name
   //       (throws an IllegalArgumentException if not)
   // post: adds the names from the list into the kill ring
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      } else {
         killRingFront = new AssassinNode(names.get(0));
         AssassinNode killRingNode = killRingFront;
         for (int i = 1; i < names.size(); i++) {
            killRingNode.next = new AssassinNode(names.get(i));
            killRingNode = killRingNode.next;
         }
      }
   }
   
   // post: prints the names of the people in the kill ring
   public void printKillRing() {
      AssassinNode killRingNode = killRingFront;
      AssassinNode graveyardNode = killRingNode;
      if (killRingNode.next == null) {
         System.out.println("    " + killRingNode.name + " is stalking " + killRingNode.name);
      } else {
         while (killRingNode.next != null) {
            System.out.println("    " + killRingNode.name + " is stalking " + killRingNode.next.name);
            killRingNode = killRingNode.next;
         }
         System.out.println("    " + killRingNode.name + " is stalking " + graveyardNode.name);
      }
   }
   
   // post: prints the names of the people in the graveyard
   public void printGraveyard () {
      AssassinNode killRingNode = graveyardFront;
      while (killRingNode != null) {
         System.out.println("    " + killRingNode.name + " was killed by " + killRingNode.killer);
         killRingNode = killRingNode.next;
      }
   }
   
   // post: returns whether or not the given name
   //       exists in the kill ring
   public boolean killRingContains(String name) {
      boolean contains = false;
      AssassinNode killRingNode = killRingFront;
      if (killRingFront.name.equalsIgnoreCase(name)) {
         contains = true;
      } else {
         while (killRingNode.next != null) {
            if (killRingNode.next.name.equalsIgnoreCase(name)) {
               contains = true;
            }
            killRingNode = killRingNode.next;
         }
      }
      return contains;
   }
   
   // post: returns whether or not the given name
   //       exists in the graveyard
   public boolean graveyardContains(String name) {
      boolean contains = false;
      AssassinNode killRingNode = graveyardFront;
      if (killRingNode == null) {
         contains = false;
      } else if (graveyardFront.name.equalsIgnoreCase(name)) {
         contains = true;
      } else {
         while (killRingNode.next != null) {
            if (killRingNode.next.name.equalsIgnoreCase(name)) {
               contains = true;
            }
            killRingNode = killRingNode.next;
         }
      }
      return contains;
   }
   
   // post: returns whether or not the game is over
   public boolean gameOver() {
      return killRingFront.next == null;
   }
   
   // post: returns the winner of the game
   //       (null if the game is incomplete)
   public String winner() {
      if (killRingFront.next == null) {
         return killRingFront.name;
      } else {
         return null;
      }
   }
   
   // pre : the name entered must be part of the current kill ring 
   //       (throws an IllegalArgumentException if not) and the game 
   //       must not be over (throws an IllegalStateException if not)
   // post: transfers the given name from 
   //       the kill ring to the graveyard
   public void kill(String name) {
      AssassinNode killRingNode = killRingFront;
      AssassinNode graveyardNode = graveyardFront;
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      } else if (gameOver()) {
         throw new IllegalStateException();
      } else {
         if (killRingNode.name.equalsIgnoreCase(name)) {
            AssassinNode lastNode = killRingFront;
            while (lastNode.next != null) {
               lastNode = lastNode.next;
            }
            killRingNode.killer = lastNode.name;
            graveyardFront = killRingNode;
            killRingFront = killRingFront.next;
            if (graveyardFront == null) {
               graveyardFront.next = null;
            } else {
               graveyardFront.next = graveyardNode;
            }
         } else {
            while (killRingNode.next != null) {
               if (killRingNode.next.name.equalsIgnoreCase(name)) {
                  killRingNode.next.killer = killRingNode.name;
                     graveyardFront = killRingNode.next;
                     if (killRingNode.next.next != null) {
                        killRingNode.next = killRingNode.next.next;
                     } else {
                        killRingNode.next = null;
                     }
                     graveyardFront.next = graveyardNode;
               } else {
                  killRingNode = killRingNode.next;
               }
            }
         }
      }
   }
}