// The Factory Design Pattern is a creational design pattern that provides an 
// interface for creating objects, but lets the subclass or factory method decide which object to instantiate.

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square");
    }
}

class DemoFactoryDesign {
    public static Shape getShape(String type) {
        if (type.equals("CIRCLE")) return new Circle();
        if (type.equals("SQUARE")) return new Square();
        Shape s = DemoFactoryDesign.getShape("CIRCLE");
        s.draw();
        return null;
    }
}




