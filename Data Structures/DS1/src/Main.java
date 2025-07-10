import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        StringStack<String> stack = new StringStackImpl<>();
        StringQueue<String> queue = new StringQueueImpl<>();

        try {
            System.out.println(stack.isEmpty());
            stack.printStack(System.out);
            stack.push("Giorgos");
            stack.printStack(System.out);
            stack.push("Vasiliki");
            stack.printStack(System.out);
            stack.push("AUEB");
            stack.printStack(System.out);
            stack.push("SUPERMARKET");
            stack.printStack(System.out);
            System.out.println(stack.isEmpty());
            System.out.println(stack.size());

            System.out.println(stack.pop());
            stack.printStack(System.out);
            System.out.println(stack.pop());
            stack.printStack(System.out);
            System.out.println(stack.pop());
            stack.printStack(System.out);
            System.out.println(stack.pop());
            stack.printStack(System.out);
            System.out.println(stack.size());
            System.out.println();
            System.out.println("---------------------------------");
            System.out.println();
            System.out.println(queue.isEmpty());
            queue.printQueue(System.out);
            queue.put("Giorgos");
            queue.printQueue(System.out);
            System.out.println(queue.peek());
            queue.put("Vasiliki");
            queue.printQueue(System.out);
            queue.put("AUEB");
            queue.printQueue(System.out);
            System.out.println(queue.peek());
            queue.put("SUPERMARKET");
            queue.printQueue(System.out);
            System.out.println(queue.isEmpty());
            System.out.println(queue.size());

            System.out.println(queue.get());
            queue.printQueue(System.out);
            System.out.println(queue.peek());
            System.out.println(queue.get());
            queue.printQueue(System.out);
            System.out.println(queue.get());
            queue.printQueue(System.out);
            System.out.println(queue.get());
            queue.printQueue(System.out);
            System.out.println(queue.size());

        }catch (NoSuchElementException e){
            System.out.println("\nStack is empty.");
        }
    }
}