public class Rectangle {
    int xmin, xmax, ymin, ymax;
    Point g1, g2, g3, g4; //represent corners for distanceTo method.
    int width;
    int height;
    public Rectangle(){}
    public Rectangle(int xmin, int xmax, int ymin, int ymax){
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        g1 = new Point(xmin, ymin);
        g2 = new Point(xmin, ymax);
        g3 = new Point(xmax, ymin);
        g4 = new Point(xmax, ymax);
        width = xmax() - xmin();
        height = ymax() - ymin();
    }
    public int xmin(){
        return xmin;
    }
    public int xmax(){
        return xmax;
    }
    public int ymin(){
        return ymin;
    }
    public int ymax(){
        return ymax;
    }

    public boolean contains(Point p){
        return (p.x() >= xmin && p.x() <= xmax) && (p.y() >= ymin && p.y() <= ymax);
    }
    public boolean intersects(Rectangle that) {
        int that_width = that.xmax() - that.xmin();
        int that_height = that.ymax() - that.ymin();
        if ((this.ymin() + this.height) < that.ymin() || (that.ymin() + that_height) < this.height) {
            return false;
        }
        if ((this.xmin() + this.width) < that.xmin() || (that.xmin() + that_width) < this.width) {
            return false;
        }
        return true;
    }
    public double distanceTo(Point p){
        double distance = 0.0;
        if (contains(p)) return distance; //condition 1
        else if ((p.x() < xmin()) && (p.y() < ymin())){ //condition 2
            distance = p.distanceTo(g1);
        }
        else if ((p.x() < xmin()) && (p.y() > ymax())){ //condition 3
            distance = p.distanceTo(g2);
        }
        else if ((p.x() > xmax()) && (p.y() < ymin())){ //condition 4
            distance = p.distanceTo(g3);
        }
        else if((p.x() > xmax()) && (p.y() > ymax())){ //condition 5
            distance = p.distanceTo(g4);
        }
        else if((p.x() >= xmin()) && (p.y() <= xmax())){
            if (p.y() < ymin()){ //condition 6
                distance = p.y() - ymin();
            }
            else if(p.y() > ymax()){ //condition 7
                distance = p.y() - ymax();
            }
        }
        else if((p.y() >= ymin()) && (p.y() <= ymax())){
            if (p.x() < xmin()){ //condition 8
                distance = p.x() - xmin();
            }
            else if (p.x() > xmax()){ //condition 9
                distance = p.x() - xmax();
            }
        }
        return distance;
    }
    public int squaredDistanceTo(Point p){
        return (int) Math.pow(  distanceTo(p), 2);
    }
    public String toString(){
        return "[" + xmin() + ", " + xmax() +"] x [" + ymin() + ", " + ymax() + "]";
    }
}
