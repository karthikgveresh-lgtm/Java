// The Singleton Pattern ensures that a class has only one instance and provides a global access point to that instance.





public class DemoSingleton {
    private static DemoSingleton instance;

    private DemoSingleton() {
        // Private constructor to prevent instantiation
    }

    public static DemoSingleton getInstance() {
        if (instance == null) {
            instance = new DemoSingleton();
        }
        return instance;
    }

    public void display() {
        System.out.println("Inside display method of Singleton class");
    }

    public static void main(String[] args) {
        DemoSingleton singleton1 = DemoSingleton.getInstance();
        singleton1.display();

        DemoSingleton singleton2 = DemoSingleton.getInstance();
        singleton2.display();

        // Check if both instances are the same
        System.out.println("Are both instances the same? " + (singleton1 == singleton2));
    }
}
