/**
 * Simple abstract class to demonstrate abstraction.
 */
public abstract class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    // required by subclasses to provide displayable details
    public abstract String getDetails();
}