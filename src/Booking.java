/**
 * Booking links a customer (by id) and a package (by id).
 */
public class Booking {
    private String id;
    private String customerId;
    private String packageId;
    private String date; // stored as simple string YYYY-MM-DD

    public Booking(String id, String customerId, String packageId, String date) {
        this.id = id;
        this.customerId = customerId;
        this.packageId = packageId;
        this.date = date;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getPackageId() { return packageId; }
    public String getDate() { return date; }

    public String getDisplay(java.util.List<Customer> customers, java.util.List<TravelPackage> packages) {
        String custName = customerId;
        String pkgDest = packageId;
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) { custName = c.getName(); break; }
        }
        for (TravelPackage p : packages) {
            if (p.getId().equals(packageId)) { pkgDest = p.getDestination(); break; }
        }
        return String.format("Booking ID: %s | Customer: %s | Package: %s | Date: %s",
                id, custName, pkgDest, date);
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ID: ").append(id).append("\n");
        sb.append("CustomerID: ").append(customerId).append("\n");
        sb.append("PackageID: ").append(packageId).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("------------------------");
        return sb.toString();
    }

    public static Booking fromBlock(java.util.List<String> block) {
        String id = "";
        String cid = "";
        String pid = "";
        String date = "";
        for (String l : block) {
            l = l.trim();
            if (l.startsWith("Booking ID:")) id = l.substring(11).trim();
            else if (l.startsWith("CustomerID:")) cid = l.substring(11).trim();
            else if (l.startsWith("PackageID:")) pid = l.substring(10).trim();
            else if (l.startsWith("Date:")) date = l.substring(5).trim();
        }
        if (id.isEmpty()) return null;
        return new Booking(id, cid, pid, date);
    }
}
