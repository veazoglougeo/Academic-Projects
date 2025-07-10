public class TreeNode {
    Point item;
    TreeNode l;
    TreeNode r;

    public TreeNode(Point p){
        this.item = p;
    }


    public TreeNode getLeft(){
        return l;
    }
    public TreeNode getRight(){
        return r;
    }
    public Point getPoint(){
        return item;
    }
    public void setLeft(TreeNode leftNode){
        l = leftNode;
    }
    public void setRight(TreeNode rightNode){
        r = rightNode;
    }
}
