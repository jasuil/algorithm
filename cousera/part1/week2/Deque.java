import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> first;
    private Node<Item> last;
    // construct an empty deque
    public Deque() {
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
       // deque.iterator();//     ==> [1]
        deque.removeLast();//   ==> 1
       // deque.iterator();//     ==> []
        deque.addFirst(3);
       // deque.iterator();//     ==> [3]
        deque.addFirst(4);
      //  deque.iterator();//     ==> [4, 3]
        deque.removeLast() ;//  ==> 3
        //deque.iterator()  ;//   ==> [4, 3]

        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (this.first == null) {
            Node<Item> node = new Node<>();
            node.item = item;
            this.first = node;
            this.last = node;
        } else {
            Node<Item> old = this.first;
            Node<Item> node = new Node<>();
            node.item = item;
            this.first = node;
            node.next = old;
            old.before = node;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (this.last == null) {
            Node<Item> node = new Node<>();
            node.item = item;
            this.last = node;
            this.first = node;
        } else {
            Node<Item> old = this.last;
            Node<Item> node = new Node<>();
            node.item = item;
            this.last = node;
            node.before = old;
            old.next = node;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.first == null) throw new NoSuchElementException();
        Node<Item> old = this.first;
        Item oldItem = old.item;
        this.first = this.first.next;
        if (this.first != null) this.first.before = null;
        if (last == old) {
            last = null;
        }

        this.size--;
        return oldItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.last == null) throw new NoSuchElementException();
        Node<Item> old = this.last;
        Item oldItem = old.item;
        this.last = this.last.before;
        if (this.last != null) this.last.next = null;
        if (first == old) {
            first = null;
        }

        this.size--;
        return oldItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Item> {
        private Node<Item> cursor;

        Itr() {
            this.cursor = first;
        }

        public boolean hasNext() {
            if (this.cursor == null) {return false;}
            return this.cursor.item != null;
        }

        /**
         * @return
         */
        @Override
        public Item next() {
            if (this.cursor == null) throw new NoSuchElementException();
            Item item = this.cursor.item;
            this.cursor = this.cursor.next;
            return item;
        }

        /**
         *
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException ();
            /*if (last == null) {return;}
            Node<Item> old = last;
            last = last.before;
            last.next = null;
            if (first.item == old) {
                first = first.next;
                first.before = null;
            }
            size--;
            old = null;*/
        }

    }

    private class Node<Item> {
        Node<Item> before;
        Item item;
        Node<Item> next;
    }

}
