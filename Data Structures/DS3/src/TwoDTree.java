import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TwoDTree {
    private static TreeNode head; //root of the tree
    private int size;
    private static boolean isInserted;

    public TwoDTree() {
        head = null;
    }

    public static void main(String[] args) {
        TwoDTree tree = new TwoDTree();
        input(args[0], tree);
        Point point;
        Scanner scanner = new Scanner(System.in);
        Rectangle rectangle;
        List<Point> containedPoints = new List<>();
        int action, x, y, xmin, xmax, ymin, ymax;
        boolean flag = true;
        boolean flag2 = true;
        while (flag) {
            System.out.println(
                    "\n --Menu-- \n" +
                            "0. Exit program. \n" +
                            "1. Compute the size of the tree. \n" +
                            "2. Insert a new point. \n" +
                            "3. Search if a given point exists in the tree. \n" +
                            "4. Provide a query rectangle. \n" +
                            "5. Provide a query point.");
            System.out.print("\nAction: ");
            action = scanner.nextInt();
            while (action < 0 || action > 5) {
                System.out.println("\nInput must be 0-5.\n");
                System.out.print("Action: ");
                action = scanner.nextInt();
            }
            switch (action) {
                case 0: {
                    System.out.println("\nExiting..");
                    System.exit(0);
                }
                case 1: {
                    System.out.println("\nTree size: " + tree.size());
                    break;
                }
                case 2: {
                    System.out.println("\nType in the desired coordinates.");
                    System.out.print("X coordinate: ");
                    x = scanner.nextInt();
                    while (x < 0 || x > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("X coordinate: ");
                        x = scanner.nextInt();
                    }
                    System.out.print("Y coordinate: ");
                    y = scanner.nextInt();
                    while (y < 0 || y > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("Y coordinate: ");
                        y = scanner.nextInt();
                    }
                    point = new Point(x, y);
                    tree.insert(point);
                    if (tree.isInserted()) {
                        System.out.println("\nPoint " + point.toString() + " successfully inserted.");
                    }
                    break;
                }
                case 3: {
                    System.out.println("\nType in desired coordinates.");
                    System.out.print("X coordinate: ");
                    x = scanner.nextInt();
                    while (x < 0 || x > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("X coordinate: ");
                        x = scanner.nextInt();
                    }
                    System.out.print("Y coordinate: ");
                    y = scanner.nextInt();
                    while (y < 0 || y > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("Y coordinate: ");
                        y = scanner.nextInt();
                    }
                    point = new Point(x, y);
                    if (tree.search(point)) {
                        System.out.println("\nGiven point " + point.toString() + " already exists.");
                    } else {
                        System.out.println("\nGiven point " + point.toString() + " does not exist.");
                    }
                    break;
                }
                case 4: {
                    System.out.println("\nType in the desired coordinates.");
                    System.out.print("Minimum X coordinate: ");
                    xmin = scanner.nextInt();
                    while (xmin < 0 || xmin > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("Minimum X coordinate: ");
                        xmin = scanner.nextInt();
                    }
                    System.out.print("Maximum X coordinate: ");
                    xmax = scanner.nextInt();
                    while (true) {
                        if (xmax < 0 || xmax > 100) {
                            System.out.println("\nInput must be 0-100.\n");
                            System.out.print("Maximum X coordinate: ");
                            xmax = scanner.nextInt();
                        }
                        else if (xmax < xmin) {
                            System.out.println("\nMaximum coordinate must be equal or bigger than minimum coordinate.\n");
                            System.out.print("Maximum X coordinate: ");
                            xmax = scanner.nextInt();
                        }else {
                            break;
                        }
                    }
                    System.out.print("Minimum Y coordinate: ");
                    ymin = scanner.nextInt();
                    while (ymin < 0 || ymin > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("Minimum Y coordinate: ");
                        ymin = scanner.nextInt();
                    }
                    System.out.print("Maximum Y coordinate: ");
                    ymax = scanner.nextInt();
                    while (true) {
                        if (ymax < 0 || ymax > 100) {
                            System.out.println("\nInput must be 0-100.\n");
                            System.out.print("Maximum Y coordinate: ");
                            ymax = scanner.nextInt();
                        }
                        else if (ymax < ymin) {
                            System.out.println("\nMaximum coordinate must be equal or bigger than minimum coordinate.\n");
                            System.out.print("Maximum Y coordinate: ");
                            ymax = scanner.nextInt();
                        }else {
                            break;
                        }
                    }
                    rectangle = new Rectangle(xmin, xmax, ymin, ymax);
                    containedPoints = tree.rangeSearch(rectangle);
                    System.out.println(containedPoints.toString());
                    break;
                }
                case 5: {
                    System.out.println("\nType in desired coordinates.");
                    System.out.print("X coordinate: ");
                    x = scanner.nextInt();
                    while (x < 0 || x > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("X coordinate: ");
                        x = scanner.nextInt();
                    }
                    System.out.print("Y coordinate: ");
                    y = scanner.nextInt();
                    while (y < 0 || y > 100) {
                        System.out.println("\nInput must be 0-100\n");
                        System.out.print("Y coordinate: ");
                        y = scanner.nextInt();
                    }
                    point = new Point(x, y);
                    System.out.println("\nClosest tree-point to desired point " + point.toString() + " is : " + tree.nearestNeighbor(point));
                    break;
                }
            }
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point item) {
        if (head == null) {
            head = new TreeNode(item);
            size++;
        } else {
            if (search(item)) {
                System.out.println("\nDuplicate points detected. Point " + item.toString() + " can not be inserted twice.");
                isInserted = false;
            } else {
                TreeNode current = head;
                int counter = 0;
                boolean flag = true;
                while (flag) {
                    if (counter % 2 == 0) {
                        if (current.getPoint().x() > item.x()) {
                            if (current.getLeft() == null) {
                                flag = false;
                                current.setLeft(new TreeNode(item));
                                size++;
                            } else {
                                current = current.getLeft();
                            }
                        } else {
                            if (current.getRight() == null) {
                                flag = false;
                                current.setRight(new TreeNode(item));
                                size++;
                            } else {
                                current = current.getRight();
                            }
                        }
                    } else {
                        if (current.getPoint().y() > item.y()) {
                            if (current.getLeft() == null) {
                                flag = false;
                                current.setLeft(new TreeNode(item));
                                size++;
                            } else {
                                current = current.getLeft();
                            }
                        } else {
                            if (current.getRight() == null) {
                                flag = false;
                                current.setRight(new TreeNode(item));
                                size++;
                            } else {
                                current.getRight();
                            }
                        }
                    }
                    counter++;
                }
                isInserted = true;
            }
        }
    }
    public boolean isInserted(){
        return isInserted;
    }

    public boolean search(Point p) {
        if (isEmpty()) {
            return false;
        }
        if ((head.getPoint().x() == p.x()) && (head.getPoint().y() == p.y())) {
            return true;
        }
        int counter = 0;
        boolean flag = true;
        TreeNode current = head;
        while (flag) {
            if (counter % 2 == 0) {
                if (current.getPoint().x() > p.x()) {
                    if (current.getLeft() == null) {
                        flag = false;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) {
                        flag = false;
                    } else {
                        current = current.getRight();
                    }
                }
            } else {
                if (current.getPoint().y() > p.y()) {
                    if (current.getLeft() == null) {
                        flag = false;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) {
                        flag = false;
                    } else {
                        current = current.getRight();
                    }
                }
            }
            if (current.getPoint().x() == p.x() && current.getPoint().y() == p.y()) {
                return true;
            }
            counter++;
        }
        return false;
    }

    public Point nearestNeighbor(Point p) {
        if (head == null) {
            return null;
        }
        return nearestNeighbor(head, p, head.getPoint());
    }

    private static Point nearestNeighbor(TreeNode node, Point target, Point closest) {
        if (node == null) {
            return null;
        }
        if (node.getPoint().distanceTo(target) < closest.distanceTo(target)) {
            closest = node.getPoint();
        }
        if (node.getLeft() != null && node.getLeft().getPoint().distanceTo(target) < closest.distanceTo(target)) {
            closest = nearestNeighbor(node.getLeft(), target, closest);
        }
        if (node.getRight() != null && node.getRight().getPoint().distanceTo(target) < closest.distanceTo(target)) {
            closest = nearestNeighbor(node.getRight(), target, closest);
        }
        return closest;
    }

    public List<Point> rangeSearch(Rectangle rect) {
        List<Point> list = new List<>();
        rangeSearch(rect, head, list);
        return list;
    }

    private void rangeSearch(Rectangle rect, TreeNode node, List<Point> list) {
        if (node == null) {
            return;
        }

        Rectangle nodeRect = new Rectangle(node.getPoint().x(), node.getPoint().y(), 0, 0);
        if (!nodeRect.intersects(rect)) {
            return;
        }

        if (rect.contains(node.getPoint())) {
            list.insertAtFront(node.getPoint());
        }

        rangeSearch(rect, node.getLeft(), list);
        rangeSearch(rect, node.getRight(), list);
    }

    public static void input(String path, TwoDTree tree){
        BufferedReader reader;
        StringTokenizer tokenizer;
        Point point;
        int numOfPoints;
        int x;
        int y;
        String line;
        try{
            reader = new BufferedReader((new FileReader(path)));
            line = reader.readLine();
            numOfPoints = Integer.parseInt(line);
            line = reader.readLine();
            while (line != null) {
                tokenizer = new StringTokenizer(line, " ");
                x = Integer.parseInt(tokenizer.nextToken());
                y = Integer.parseInt(tokenizer.nextToken());
                if (x < 0 || x > 100 || y < 0 || y > 100) {
                    System.out.println("Out of range coordinates detected. Range is [0, 100].");
                    System.exit(0);
                } else {
                    point = new Point(x, y);
                    if (tree.search(point)){
                        System.out.println("\nDuplicate points detected. Point " + point.toString() + " can not be inserted twice. Check input file.");
                        System.exit(0);
                    }else{
                        tree.insert(point);
                    }
                }

                line = reader.readLine();
            }
            if(tree.size() != numOfPoints){
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Given number of points does not match.");
            System.exit(0);
        }
    }
}