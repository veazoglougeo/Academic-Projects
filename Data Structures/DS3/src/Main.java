public class Main {
    public static void main(String[] args) {
        Rectangle rect1 = new Rectangle(0, 10, 4, 7);
        Rectangle rect2 = new Rectangle(4, 7, 0, 8);
        System.out.println(rect2.intersects(rect1));
    }
}