import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String msg) {
        super(msg);
    }
}

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type))
            throw new InvalidBookingException("Invalid room type: " + type);
        return inventory.get(type);
    }

    public void bookRoom(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0)
            throw new InvalidBookingException("No rooms available for: " + type);

        inventory.put(type, available - 1);
    }
}

class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void process(String guest, String roomType) {
        try {
            inventory.bookRoom(roomType);
            System.out.println("Booking Confirmed: " + guest + " -> " + roomType);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inv = new RoomInventory();
        inv.addRoom("Single", 1);
        inv.addRoom("Suite", 0);

        BookingService service = new BookingService(inv);

        service.process("Alice", "Single");   // success
        service.process("Bob", "Suite");      // no availability
        service.process("Charlie", "Deluxe"); // invalid type
    }
}