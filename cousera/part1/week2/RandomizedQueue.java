import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_INIT_SIZE = 10;
    private static final double OVERHEAD_RATE = 0.8;
    private int size;
    private Node<Item> first;
    private Node<Item> last;
    private Object[] list;
    private int head;
    private int tail;
    // construct an empty randomized queue
    public RandomizedQueue() {
        list = new Object[DEFAULT_INIT_SIZE];
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println(queue.dequeue());
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {throw new IllegalArgumentException();}
        if ((double) size / list.length >= OVERHEAD_RATE) {
            Object[] newList = new Object[list.length + DEFAULT_INIT_SIZE];
            for (int i = 0; i < list.length; i++) {
                newList[i] = list[i];
            }
            list = newList;
        }
        list[size] = item;
        size++;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.size == 0) throw new NoSuchElementException();
        int skipIndex = StdRandom.uniformInt(this.size);
        return (Item) list[skipIndex];
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0) throw new NoSuchElementException();
        int skipIndex = StdRandom.uniformInt(this.size);
        Object removeItem = list[skipIndex];
        Object[] newList = new Object[list.length];

        int newIndex = 0;
        for (int i = 0; i < list.length; i++) {
            if (i == skipIndex) {continue;}
            newList[newIndex++] = list[i];
        }
        list = newList;
        size--;
        return (Item) removeItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Itr();
    }

    private class Node<Item> {
        Node<Item> before;
        Item item;
        Node<Item> next;
    }

    private class Itr implements Iterator<Item> {
        private int cursor;

        Itr() {
            this.cursor = size - 1;
        }

        public boolean hasNext() {
            if (this.cursor < 0) {return false;}
            return this.cursor < size;
        }

        /**
         * @return
         */
        @Override
        public Item next() {
            if (this.cursor >= size) {throw new NoSuchElementException();}
            return (Item) list[this.cursor++];
        }

        /**
         *
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            // list[list.length-1] = null;
        }
    }

}