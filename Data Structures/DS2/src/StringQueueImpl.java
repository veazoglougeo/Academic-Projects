import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueImpl<T> implements  StringQueue<T>{
    private Node<T> head = null;
    private Node<T> tail = null;
    int size = 0;

    @Override
    public boolean isEmpty() {
        return tail == null;
    }

    @Override
    public void put(T item) {
        Node<T> newNode = new Node<T>(item);
        if (isEmpty()){
            head = tail = newNode;
        }else{
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        System.out.println("\nPut: " + newNode.getData());
    }

    @Override
    public T get() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }else{
            T dequeued = head.getData();
            head = head.getNext();
            size--;
            System.out.print("\nDequeued: ");
            return dequeued;
        }
    }

    @Override
    public T peek() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }else{
            System.out.print("Peek: ");
            return head.getData();
        }
    }

    @Override
    public void printQueue(PrintStream stream) {
        if(isEmpty()){
            System.out.print("\nQueue is empty.\n");
        }else {
            Node<T> current = head;
            System.out.println("<Head>");
            while (current != null) {
                stream.println(current.getData());
                current = current.getNext();
            }
            System.out.println("<Tail>\n");
        }
    }

    @Override
    public int size() {
        System.out.print("\nSize: ");
        return size;
    }
}
