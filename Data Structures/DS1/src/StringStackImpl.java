import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl<T> implements StringStack<T>{
    private Node<T> top = null;
    private int size = 0;

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(T item) {
        Node<T> newNode = new Node<T>(item);
        if (!isEmpty()) newNode.setNext(top);
        top = newNode;
        size ++;
    }

    @Override
    public T pop() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }else{
            T popped = top.getData();
            top = top.getNext();
            size --;
            return popped;
        }
    }

    @Override
    public T peek() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }else{
            System.out.print("Peek: ");
            return top.getData();
        }
    }

    @Override
    public void printStack(PrintStream stream) {
        if(isEmpty()){
            System.out.print("\nStack is empty.\n");
        }else{
            Node<T> printNode = top;
            System.out.println("<Top>");
            while(printNode != null){
                stream.println(printNode.getData());
                printNode = printNode.getNext();
            }
            System.out.println("<Bottom>\n");
        }

    }

    @Override
    public int size() {
        System.out.print("\nSize: ");
        return size;
    }
}
