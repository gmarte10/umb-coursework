package edu.umb.cs210.p5;

import dsa.LinkedQueue;
import stdlib.StdIn;
import stdlib.StdOut;

public class ArrayST<Key, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int N; // number of key-value pairs

    // Create a symbol table with INIT_CAPACITY.
    public ArrayST() {
        this(INIT_CAPACITY); // set to initial capacity
        this.N = 0; // assign N
    }

    // Create a symbol table with given capacity.
    public ArrayST(int capacity) {
        keys = (Key[]) new Object[capacity]; // keys are of size capacity
        // values are of size capacity(each key has a value)
        values = (Value[]) new Object[capacity];
    }

    // Return the number of key-value pairs in the table.
    public int size() {
        return N;
    }

    // Return true if the table is empty and false otherwise.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return true if the table contains key and false otherwise.
    public boolean contains(Key key) {
        // checks if no key was given
        if (key == null)
        {
            // throws exception
            throw new IllegalArgumentException("No key was given");
        }
        // returns if key was found or not
        return get(key) != null;
    }

    // Return the value associated with key, or null.
    public Value get(Key key) {
        // checks if no key was given
        if (key == null)
        {
            // throws exception
            throw new IllegalArgumentException("No key was given into get");
        }
        // goes through keys array
        for (int i = 0; i < N; i++)
        {
            // checks if key is inside
            if (key.equals(keys[i]))
            {
                // values array is parallel, so return value at same index
                return values[i];
            }
        }
        return null;
    }

    // Put the key-value pair into the table; remove key from table 
    // if value is null.
    public void put(Key key, Value value) {
        // checks if no key was given
        if (key == null)
        {
            // throws exception
            throw new IllegalArgumentException("No key was given into put");
        }
        // checks if no value was given
        if (value == null)
        {
            // deletes the entire key if no value was given
            delete(key);
            return;
        }

        // checks if key exists, and updates it
        for (int i = 0; i < N; i++)
        {
            if (key.equals(keys[i]))
            {
                // update value
                values[i] = value;
                return;
            }
        }
        // gets the length of values array
        int length = values.length;
        // checks if the size is greater than values length
        if (size() >= length)
        {
            // resize by 2X
            resize(size() * 2);
        }
        // insert new key and value at the end
        keys[N] = key;
        values[N] = value;
        // increase N
        N++;

    }

    // Remove key (and its value) from table.
    public void delete(Key key) {
        if (key == null)
        {
            return;
        }
        // goes through keys array
        for (int i = 0; i < N; i++)
        {
            // finds key
            if (key.equals(keys[i]))
            {
                // swap key with last key
                keys[i] = keys[N - 1];
                values[i] = values[N - 1];
                // delete key by making it into null
                keys[N - 1] = null;
                values[N - 1] = null;
                // decrease N
                N--;
            }
        }
        int length = keys.length;
        // if N == 1/4 array length, resize
        if (size() == length / 4 && size() > 0)
        {
            resize(length / 2);
        }
    }

    // Return all the keys in the table.
    public Iterable<Key> keys()  {
        // create a linked queue to store keys
        LinkedQueue<Key> queue = new LinkedQueue<Key>();
        for (int i = 0; i < N; i++)
        {
            // store the key at i
            queue.enqueue(keys[i]);
        }
        return queue;
    }

    // Resize the internal arrays to capacity.
    private void resize(int capacity) {
        Key[] tempk = (Key[]) new Object[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<String, Integer>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            st.put(s, ++count);
        }
        for (String s : args) {
            st.delete(s);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
