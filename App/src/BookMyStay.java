import java.util.*;

class Service {
    String name;
    double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> map = new HashMap<>();

    public void addService(String reservationId, Service s) {
        map.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(s);
    }

    public double getTotalCost(String reservationId) {
        double total = 0;
        for (Service s : map.getOrDefault(reservationId, new ArrayList<>()))
            total += s.price;
        return total;
    }

    public void display(String reservationId) {
        List<Service> list = map.get(reservationId);
        if (list == null) return;

        for (Service s : list)
            System.out.println(s.name + " ₹" + s.price);

        System.out.println("Total Add-On Cost: ₹" + getTotalCost(reservationId));
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "R101";

        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1200));
        manager.addService(reservationId, new Service("Spa", 1500));

        System.out.println("=== Add-On Services ===");
        manager.display(reservationId);
    }
}