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

    public String getType() { return type; }

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

    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void search(List<Room> rooms) {
        for (Room r : rooms) {
            int available = inventory.getAvailability(r.getType());
            if (available > 0) {
                r.display();
                System.out.println("Available: " + available);
            }
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        List<Room> rooms = Arrays.asList(
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        );

        RoomInventory inv = new RoomInventory();
        inv.addRoom("Single", 5);
        inv.addRoom("Double", 0);
        inv.addRoom("Suite", 2);

        SearchService service = new SearchService(inv);

        System.out.println("=== Available Rooms ===");
        service.search(rooms);
    }
}