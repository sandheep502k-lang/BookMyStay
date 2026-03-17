import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public void showQueue() {
        for (Reservation r : queue) {
            r.display();
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        BookingQueue bq = new BookingQueue();

        bq.addRequest(new Reservation("Alice", "Single"));
        bq.addRequest(new Reservation("Bob", "Double"));
        bq.addRequest(new Reservation("Charlie", "Suite"));

        System.out.println("=== Booking Requests (FIFO Order) ===");
        bq.showQueue();
    }
}