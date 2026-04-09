interface  A{
    int add(int a, int b);
    int sub(int a, int b);
}

class B implements A{
    @Override
    public int add(int a, int b) {
        System.out.println("Inside B add method");
        int X;
        X = a+b;
        return X;
    }

    @Override
    public int sub(int a, int b) {
        int Y;
        Y = a-b;
        return Y;
    }
}

class C implements A{
    @Override
    public int add(int a, int b){
        return a+b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }
}

public class DemoInterface2 {
    public static void main(String[] args) {
        A a = new B();
        System.out.println(a.add(5, 3));
        System.out.println(a.sub(5, 3));
    }
}
