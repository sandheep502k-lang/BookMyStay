import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String id, guest, roomType;

    public Reservation(String id, String guest, String roomType) {
        this.id = id;
        this.guest = guest;
        this.roomType = roomType;
    }
}

class RoomInventory implements Serializable {
    Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingHistory implements Serializable {
    List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public void display() {
        for (Reservation r : history)
            System.out.println(r.id + " | " + r.guest + " | " + r.roomType);
    }
}

class PersistenceService {

    private static final String FILE = "hotel.dat";

    public static void save(RoomInventory inv, BookingHistory bh) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(FILE))) {
            o.writeObject(inv);
            o.writeObject(bh);
            System.out.println("State Saved");
        } catch (Exception e) {
            System.out.println("Save Failed");
        }
    }

    public static Object[] load() {
        try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(FILE))) {
            RoomInventory inv = (RoomInventory) i.readObject();
            BookingHistory bh = (BookingHistory) i.readObject();
            System.out.println("State Loaded");
            return new Object[]{inv, bh};
        } catch (Exception e) {
            System.out.println("No previous state, starting fresh");
            return new Object[]{new RoomInventory(), new BookingHistory()};
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        Object[] data = PersistenceService.load();
        RoomInventory inv = (RoomInventory) data[0];
        BookingHistory bh = (BookingHistory) data[1];

        if (inv.inventory.isEmpty()) {
            inv.addRoom("Single", 2);
            bh.add(new Reservation("R1", "Alice", "Single"));
        }

        inv.display();
        bh.display();

        PersistenceService.save(inv, bh);
    }
}