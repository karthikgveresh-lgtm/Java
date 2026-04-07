 abstract class Figure{
        int x, y;

        public Figure(int x, int y) {
            this.x = x;
            this.y = y;
        }
        abstract  int area();
    }

    class Rectangle extends Figure{

         Rectangle(int x, int y) {
            super(x, y);
        }

        @Override
        int area(){
            int p;
            p = x* y;
            return p;
        }
        
    }

public class DemoAbstractClass {
    public static void main(String[] args) {
        Rectangle R = new Rectangle(10, 20);
        int res = R.area();
        System.out.println("Area of the rectangle: " + res);
    }

}
