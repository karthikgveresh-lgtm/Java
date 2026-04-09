interface  A{
    void m1();
    void m2();
}

interface B{
    void m3();
    void m4();
}

class C implements A, B{
    @Override
    public void m1() {
        System.out.println("Inside m1 method");
    }

    @Override
    public void m2() {
        System.out.println("Inside m2 method");
    }

    @Override
    public void m3() {
        System.out.println("Inside m3 method");
    }

    @Override
    public void m4() {
        System.out.println("Inside m4 method");
    }
}

public class DemoInterface3 {
    public static void main(String[] args) {
        C c = new C();
        c.m1();
        c.m2();
        c.m3();
        c.m4();
        A a = new C();
        a.m1();
        B b = new C();
        b.m3();

    }
    
}
