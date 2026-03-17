import java.util.*;

abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public abstract void display();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 1, 2000); }
    public void display() { System.out.println(type + " | Beds: " + beds + " | ₹" + price); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 2, 3500); }
    public void display() { System.out.println(type + " | Beds: " + beds + " | ₹" + price); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 3, 6000); }
    public void display() { System.out.println(type + " | Beds: " + beds + " | ₹" + price); }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        inventory.put(type, getAvailability(type) + change);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " Available: " + e.getValue());
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        RoomInventory inv = new RoomInventory();

        inv.addRoom(r1.getType(), 5);
        inv.addRoom(r2.getType(), 3);
        inv.addRoom(r3.getType(), 2);

        System.out.println("=== Inventory ===");
        inv.displayInventory();

        inv.updateAvailability("Single", -1);

        System.out.println("=== After Booking ===");
        inv.displayInventory();
    }
}