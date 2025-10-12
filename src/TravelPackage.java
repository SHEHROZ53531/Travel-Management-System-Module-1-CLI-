/**
 * Travel package (destination + duration + price).
 */
public class TravelPackage {
    private String id;
    private String destination;
    private int durationDays;
    private double price;

    public TravelPackage(String id, String destination, int durationDays, double price) {
        this.id = id;
        this.destination = destination;
        this.durationDays = durationDays;
        this.price = price;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }
    public int getDurationDays() { return durationDays; }
    public double getPrice() { return price; }

    public String getDisplay() {
        return String.format("ID: %s | Destination: %s | Duration: %d days | Price: %.2f",
                id, destination, durationDays, price);
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Destination: ").append(destination).append("\n");
        sb.append("DurationDays: ").append(durationDays).append("\n");
        sb.append("Price: ").append(price).append("\n");
        sb.append("------------------------");
        return sb.toString();
    }

    public static TravelPackage fromBlock(java.util.List<String> block) {
        String id = "";
        String destination = "";
        int duration = 0;
        double price = 0.0;
        for (String l : block) {
            l = l.trim();
            if (l.startsWith("ID:")) id = l.substring(3).trim();
            else if (l.startsWith("Destination:")) destination = l.substring(12).trim();
            else if (l.startsWith("DurationDays:")) {
                try { duration = Integer.parseInt(l.substring(13).trim()); } catch (Exception ignored) {}
            } else if (l.startsWith("Price:")) {
                try { price = Double.parseDouble(l.substring(6).trim()); } catch (Exception ignored) {}
            }
        }
        if (id.isEmpty()) return null;
        return new TravelPackage(id, destination, duration, price);
    }
}
