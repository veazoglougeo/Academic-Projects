public class Point{
    int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public double distanceTo(Point z){
        int dx = z.x - x;
        int dy = z.y - y;
        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy, 2));
    }
    public int squareDistanceTo(Point z){
        return (int) Math.pow(distanceTo(z), 2);
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
