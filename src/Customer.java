/**
 * Customer extends Person (inheritance).
 */
public class Customer extends Person {
    private String phone;
    private String email;

    public Customer(String id, String name, String phone, String email) {
        super(id, name);
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    @Override
    public String getDetails() {
        return String.format("ID: %s | Name: %s | Phone: %s | Email: %s",
                id, name, phone, email);
    }

    // representation used when saving (not compact CSV — human-readable)
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("------------------------");
        return sb.toString();
    }

    // helper to create from block lines (assumes keys present)
    public static Customer fromBlock(java.util.List<String> block) {
        String id = "";
        String name = "";
        String phone = "";
        String email = "";
        for (String l : block) {
            l = l.trim();
            if (l.startsWith("ID:")) id = l.substring(3).trim();
            else if (l.startsWith("Name:")) name = l.substring(5).trim();
            else if (l.startsWith("Phone:")) phone = l.substring(6).trim();
            else if (l.startsWith("Email:")) email = l.substring(6).trim();
        }
        if (id.isEmpty()) return null;
        return new Customer(id, name, phone, email);
    }
}