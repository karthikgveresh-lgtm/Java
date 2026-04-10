// The Abstract Factory Pattern provides an interface to create families of related 
// objects without specifying their concrete classes.

interface Payment {
    void pay();
}

interface Refund {
    void refund();
}

class RazorpayPayment implements Payment {
    @Override
    public void pay() {
        System.out.println("Razorpay Payment");
    }
}

class RazorpayRefund implements Refund {
    @Override
    public void refund() {
        System.out.println("Razorpay Refund");
    }
}

interface PaymentFactory {
    Payment createPayment();
    Refund createRefund();
}

class RazorpayFactory implements PaymentFactory {
    @Override
    public Payment createPayment() {
        return new RazorpayPayment();
    }

    @Override
    public Refund createRefund() {
        return new RazorpayRefund();
    }
}

public class AbstractFactory {
    public static void main(String[] args) {
        PaymentFactory factory = new RazorpayFactory();
        Payment payment = factory.createPayment();
        Refund refund = factory.createRefund();
        payment.pay();
        refund.refund();
    }
}
