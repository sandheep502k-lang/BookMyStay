import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;
    private Set<String> allocatedIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void processBookings() {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();

            if (inventory.getAvailability(r.roomType) > 0) {

                String roomId = generateRoomId(r.roomType);

                allocatedIds.add(roomId);
                roomAllocations
                        .computeIfAbsent(r.roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrement(r.roomType);

                System.out.println("Confirmed: " + r.guestName +
                        " -> " + r.roomType +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Failed (No Availability): " + r.guestName);
            }
        }
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedIds.contains(id));
        return id;
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single"));
        queue.add(new Reservation("Bob", "Single"));
        queue.add(new Reservation("Charlie", "Suite"));

        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Suite", 1);

        BookingService service = new BookingService(queue, inventory);
        service.processBookings();
    }
}