/**
 * Represents a Game class that implements the Contract interface.
 * Provides various actions for managing items, movement, size, and undoing actions.
 * @author Ivy Li
 * @version 11/15/2024
 */
import java.util.ArrayList;
import java.util.Map;

public class Game implements Contract {

    private ArrayList < String > itemList; // List of items the character owns 
    private double size; //Size of character
    private String currentLocation; // Current location of the character
    private String lastAction; // The last performed action
    private String lastItem; // The last interacted item
    private String lastLocation; //The last location the charater was at
    Map < String, String > itemMessages; // Descriptions of specific items

    /**
     * Initializes the Game object with default values and pre-exisited item messages.
     */
    public Game() {
        this.itemList = new ArrayList < > ();
        this.size = 1.0;
        this.currentLocation = "Home";
        this.lastAction = "";
        this.lastItem = "";
        this.itemMessages = Map.of(
            "water", "The water is dirty! Don't drink!",
            "table", "The table is cracked in the middle.",
            "window", "The glass of the window is broken.",
            "chair", "One leg of the chair is about to break!",
            "vegetables", "Hmmm...those vegetables do not look fresh."
        );
    }

    /**
     * Adds an item to the inventory if it does not already exist.
     * @param item The name of the item to grab.
     * @throws RuntimeException if the item is already in the inventory.
     */
    public void grab(String item) {
        if (!itemList.contains(item)) {
            lastItem = item;
            itemList.add(item);
            lastAction = "grab";
            System.out.println(item + " added!");
        } else {
            throw new RuntimeException("This item already exists. Choose another item.");
        }
    }

    /**
     * Removes an item from the player's inventory if it exists.
     * @param item The name of the item to drop.
     * @return The dropped item.
     * @throws RuntimeException if the item is not in the inventory.
     */
    public String drop(String item) {
        if (itemList.contains(item)) {
            lastItem = item;
            itemList.remove(item);
            System.out.println(item + " dropped!");
            lastAction = "drop";
            return item;
        } else {
            throw new RuntimeException("This item does not exist. Choose another item.");
        }
    }

    /**
     * Print a description of the items if it exists in the inventory. 
     * Either print pre-set messege or default message depending on the item
     * @param item The name of the item to examine.
     * @throws RuntimeException if the item is not in the inventory.
     */
    public void examine(String item) {
        if (itemList.contains(item)) {
            System.out.println("Examining...");
            String message = itemMessages.getOrDefault(item, "Nothing special about " + item);
            System.out.println(message);
            lastAction = "examine";
        } else {
            throw new RuntimeException("You do not have this item. Choose another item.");
        }
    }

    /**
     * Uses an item from the player's inventory and removes it.
     * @param item The name of the item to use.
     * @throws RuntimeException if the item is not in the inventory.
     */
    public void use(String item) {
        if (itemList.contains(item)) {
            itemList.remove(item);
            System.out.println(item + " used!");
            lastAction = "use";
        } else {
            throw new RuntimeException("This item does not exist. Choose another item.");
        }
    }

    /**
     * The character walk in south, north, east, or west direction.
     * @param direction The direction to walk in.
     * @return true if the action is successful.
     * @throws RuntimeException if the direction is not one of the four above.
     */
    public boolean walk(String direction) {
        if (direction.equals("south") ||
            direction.equals("north") ||
            direction.equals("east") ||
            direction.equals("west")) {

            lastLocation = currentLocation;
            currentLocation = direction;
            lastAction = "walk";
            System.out.println("You walk " + direction);
            return true;
        } else {
            throw new RuntimeException("Direction not recognized. Please walk in north, east, south, or west direction.");

        }
    }

    /**
     * Changes the character's current location to specific coordinates by flying if x and y are within the range -100 to 100.
     * @param x The x-coordinate to fly to.
     * @param y The y-coordinate to fly to.
     * @return true if the action is successful.
     * @throws RuntimeException if the x and y are greater or equal to 100 or less than or equal to -100; 
     */
    public boolean fly(int x, int y) {
        if ((x <= 100 && x > -100) && (y <= 100 && y >= -100)) {
            lastLocation = currentLocation;
            currentLocation = "(" + x + ", " + y + ")";
            lastAction = "fly";
            System.out.println("You fly to " + currentLocation);
            return true;
        } else {
            throw new RuntimeException("Please make sure x and y are in the range of -100 to 100.");
        }
    }

    /**
     * Reduces the player's size by half.
     * @return The new size of the player.
     */
    public Number shrink() {
        size *= 0.5;
        System.out.println("You shrinked 50%! Current size is " + size);
        lastAction = "shrink";
        return size;
    }

    /**
     * Doubles the player's size.
     * @return The new size of the player.
     */
    public Number grow() {
        size *= 2.0;
        System.out.println("Your size is doubled! Current size is " + size);
        lastAction = "grow";
        return size;
    }

    /**
     * Indicates that the player is resting.
     */
    public void rest() {
        System.out.println("You are resting.");
        lastAction = "rest";
    }

    /**
     * Undoes the last performed action, if possible.
     */
    public void undo() {
        if ("grab".equals(lastAction)) {
            itemList.remove(lastItem);
            System.out.println("You undo the grab action.");
        } else if ("drop".equals(lastAction)) {
            itemList.add(lastItem);
            System.out.println("You undo the drop action.");
        } else if ("examine".equals(lastAction)) {
            System.out.println("You cannot undo the examine action.");
        } else if ("use".equals(lastAction)) {
            itemList.add(lastItem);
            System.out.println("You undo the use action.");
        } else if ("walk".equals(lastAction)) {
            currentLocation = lastLocation;
            System.out.println("You undo the walk action. Your current location is " + currentLocation);
        } else if ("fly".equals(lastAction)) {
            currentLocation = lastLocation;
            System.out.println("You undo the fly action. Your current loacation is " + currentLocation);
        } else if ("shrink".equals(lastAction)) {
            size /= 0.5;
            System.out.println("You undo shrinking. New size: " + size);
        } else if ("grow".equals(lastAction)) {
            size /= 2;
            System.out.println("You undo growing. New size: " + size);
        } else if ("rest".equals(lastAction)) {
            System.out.println("You cannot undo the rest action.");
        } else {
            System.out.println("No action to undo.");
        }
    }

    /**
     * The main method tests the functionality of the Game class
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game character1 = new Game();
        character1.grab("pen");
        character1.drop("pen");
        character1.undo();
        character1.drop("pen");
        character1.grab("water");
        character1.examine("water");
        character1.shrink();
        character1.undo();
        character1.rest();
        character1.undo();
        character1.fly(-10, 1);
        character1.walk("south");
        character1.undo();
    }
}