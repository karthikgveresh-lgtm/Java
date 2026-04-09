interface  A{
    void m1();
    void m2();
    void m3();
}
  abstract class B implements  A{
    @Override
    public  void m1(){
        System.out.println("Method 1");
    }

    @Override
    public void m2() {
        System.out.println("Inside B Method 2");
    }

    abstract void fun();
 }
class C extends B{
    @Override
    public void m1() {
        System.out.println("Inside c Method 1");
    }

    @Override
    public void m3() {
        System.out.println("Method 3");
    }

    @Override
    void fun() {
        System.out.println("Inside fun of c");
    }
}

public class DemoInterface {
    public static void main(String[] args) {
        C c = new C();
        c.m1();
        c.m2();
        c.m3();
        c.fun();
        A a = new C() ;
        a.m1();
        a.m2();
        a.m3();
        B b = new C();
        b.m1();
        b.m2();
        b.m3();
        b.fun();           
    }
}
