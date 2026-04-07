/**
 * 
 */


 class Figure{
	int x, y;
	Figure(int x, int y){
		this.x = x;
		this.y = y;
	}
	int area() {
		return 0;
	}
}
 
 class Rectangle extends Figure{
	 Rectangle(int x, int y){
		 super(x,y);
	 }
	 @Override
	 int area() {
		
		 int p;
		 p = x* y;
		 return p;
	 }
 }
 
 
 class Triangle extends Figure{
	 Triangle(int x, int y){
		 super(x, y);
	 }
	 @Override
	 int area() {
		 int p = x*y/2;
		 return p;
	 }
 }
 
 
 
public class DemoClass {
	public static void main(String[] args) {
		Rectangle R = new Rectangle(10, 20);
		int res = R.area();
		System.out.println("The area is " + res);
		
		Triangle T = new Triangle(10, 10);
		 int rs = T.area();
		 System.out.println("Triangle area is :" + rs);
		
	}

}
