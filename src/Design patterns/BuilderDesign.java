// The Builder Pattern is used to construct complex objects step by step, allowing you to create different representations 
// of an object using the same construction process.

class User {
    private final String name;
    private final int age;
    private final String city;

    // Private constructor
    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.city = builder.city;
    }

    // Getters (optional but good practice)
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    // Builder Class
    public static class Builder {
        private final String name; // required
        private int age;           // optional
        private String city;       // optional

        public Builder(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            this.name = name;
        }

        public Builder age(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + ", city='" + city + "'}";
    }
}

// Main class (file name = BuilderDesign.java)
public class BuilderDesign {
    public static void main(String[] args) {

        User user = new User.Builder("Karthik")
                .age(22)
                .city("Bangalore")
                .build();

        System.out.println(user);
    }
}