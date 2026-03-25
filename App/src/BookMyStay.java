import java.util.*;

class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

class BookingReportService {

    public void showAll(List<Reservation> list) {
        for (Reservation r : list) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }
    }

    public void summary(List<Reservation> list) {
        Map<String, Integer> count = new HashMap<>();

        for (Reservation r : list) {
            count.put(r.roomType, count.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("=== Summary ===");
        for (String k : count.keySet()) {
            System.out.println(k + " bookings: " + count.get(k));
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.add(new Reservation("R1", "Alice", "Single"));
        history.add(new Reservation("R2", "Bob", "Suite"));
        history.add(new Reservation("R3", "Charlie", "Single"));

        BookingReportService report = new BookingReportService();

        report.showAll(history.getAll());
        report.summary(history.getAll());
    }
}