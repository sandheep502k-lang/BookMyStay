import java.util.*;

class Reservation {
    String id;
    String guest;
    String roomType;

    public Reservation(String id, String guest, String roomType) {
        this.id = id;
        this.guest = guest;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println(inventory);
    }
}

class BookingHistory {
    Map<String, Reservation> active = new HashMap<>();
    Set<String> cancelled = new HashSet<>();

    public void add(Reservation r) {
        active.put(r.id, r);
    }

    public Reservation get(String id) {
        return active.get(id);
    }

    public void cancel(String id) {
        active.remove(id);
        cancelled.add(id);
    }

    public boolean isCancelled(String id) {
        return cancelled.contains(id);
    }
}

class CancellationService {

    private BookingHistory history;
    private RoomInventory inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public void cancel(String reservationId) {

        if (history.get(reservationId) == null || history.isCancelled(reservationId)) {
            System.out.println("Cancellation Failed: Invalid or already cancelled");
            return;
        }

        Reservation r = history.get(reservationId);

        rollbackStack.push(reservationId);

        inventory.increment(r.roomType);

        history.cancel(reservationId);

        System.out.println("Cancelled: " + reservationId + " -> " + r.roomType);
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inv = new RoomInventory();
        inv.addRoom("Single", 1);

        BookingHistory history = new BookingHistory();
        history.add(new Reservation("R1", "Alice", "Single"));

        CancellationService cs = new CancellationService(history, inv);

        cs.cancel("R1");     // success
        cs.cancel("R1");     // fail

        inv.display();
    }
}