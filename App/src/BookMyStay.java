import java.util.*;

class Reservation {
    String guest;
    String roomType;

    public Reservation(String guest, String roomType) {
        this.guest = guest;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean allocate(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }
}

class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.poll();
            }

            if (inventory.allocate(r.roomType)) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + r.roomType + " for " + r.guest);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed for " + r.guest);
            }
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) throws InterruptedException {

        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single"));
        queue.add(new Reservation("Bob", "Single"));
        queue.add(new Reservation("Charlie", "Single"));

        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 1);

        Thread t1 = new Thread(new BookingProcessor(queue, inventory));
        Thread t2 = new Thread(new BookingProcessor(queue, inventory));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}