
import java.util.Comparator;

/**
 * Single-link List. Uses {@link Node} for list nodes.
 */
public class List<T> implements ListInterface<T> {

    private edu.aueb.ds.lab4.Node<T> head = null;
    private edu.aueb.ds.lab4.Node<T> tail = null;

    /**
     * Default constructor
     */
    public List() {
    }

    /**
     * Determine whether list is empty
     *
     * @return true if list is empty
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserts the data at the front of the list
     *
     * @param data the inserted data
     */
    @Override
    public void insertAtFront(T data) {
        edu.aueb.ds.lab4.Node<T> n = new edu.aueb.ds.lab4.Node<>(data);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.setNext(head);
            head = n;
        }
    }

    /**
     * Inserts the data at the end of the list
     *
     * @param data the inserted item
     */
    @Override
    public void insertAtBack(T data) {
        edu.aueb.ds.lab4.Node<T> n = new edu.aueb.ds.lab4.Node<>(data);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            tail.setNext(n);
            tail = n;
        }
    }

    /**
     * Returns and removes the data from the list head
     *
     * @return the data contained in the list head
     * @throws EmptyListException if the list is empty
     */
    @Override
    public T removeFromFront() throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException();

        T data = head.getData();

        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();

        return data;
    }

    /**
     * Returns and removes the data from the list tail
     *
     * @return the data contained in the list tail
     * @throws EmptyListException if the list is empty
     */
    @Override
    public T removeFromBack() throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException();

        T data = tail.getData();

        if (head == tail)
            head = tail = null;
        else {
            edu.aueb.ds.lab4.Node<T> iterator = head;
            while (iterator.getNext() != tail)
                iterator = iterator.getNext();

            iterator.setNext(null);
            tail = iterator;
        }

        return data;
    }

    /**
     * Returns list as String
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "List is empty :(";
        }

        edu.aueb.ds.lab4.Node current = head;

        StringBuilder ret = new StringBuilder();

        // while not at end of list, output current node's data
        ret.append("\n\nHEAD -> ");

        while (current != null) {
            ret.append(current.data.toString());

            if (current.getNext() != null)
                ret.append(" -> ");

            current = current.next;
        }

        ret.append(" <- TAIL");

        return ret.toString();
    }

    /**
     * Sorts the list
     *
     * @param comparator
     */
    @Override
    public void sort(Comparator<T> comparator) {
        // No need to sort if list is empty or has a single element
        if (head == null || head == tail)
            return;

        edu.aueb.ds.lab4.Node<T> newHead = null;
        edu.aueb.ds.lab4.Node<T> newTail = null;

        while (head != null) {
            // get next item
            edu.aueb.ds.lab4.Node<T> swap = head;

            // move head
            head = head.getNext();

            // move swap to new-sorted list
            if (newHead == null) {
                newHead = newTail = swap;
                swap.setNext(null);
            } else {
                edu.aueb.ds.lab4.Node<T> prev = null;
                edu.aueb.ds.lab4.Node<T> iterator = newHead;

                // iterate newList until we get to a point where our data is smaller or reach the end
                while (iterator != null && comparator.compare(iterator.getData(), swap.getData()) >= 0) {
                    prev = iterator;
                    iterator = iterator.getNext();
                }

                // reached the point where we should place the node

                // if prev == null then its the head of the list
                if (prev == null)
                    newHead = swap;
                else
                    prev.setNext(swap);

                // if iterator == null then its the tail of the list
                swap.setNext(iterator);
                if(iterator == null)
                    newTail = swap;
            }
        }

        head = newHead;
        tail = newTail;
    }
}
