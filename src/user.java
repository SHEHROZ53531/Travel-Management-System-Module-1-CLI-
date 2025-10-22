import java.util.List;

/**
 * User class for storing authentication credentials (Username and Password).
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(username).append("\n");
        sb.append("Password: ").append(password).append("\n");
        sb.append("------------------------");
        return sb.toString();
    }

    public static User fromBlock(List<String> block) {
        String user = "";
        String pass = "";
        for (String l : block) {
            l = l.trim();
            if (l.startsWith("Username:")) user = l.substring(9).trim();
            else if (l.startsWith("Password:")) pass = l.substring(9).trim();
        }
        if (user.isEmpty() || pass.isEmpty()) return null;
        return new User(user, pass);
    }
}