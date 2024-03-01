package gc.advalgorith.hw2;

import java.util.Iterator;

public class TestBag {
    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        String a = "apple";
        String p = "pear";
        bag.add(a);
        bag.add(p);
        bag.add(a);
        int count = 0;
        Iterator<String> iterator = bag.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(a)) {
                count++;
            }
        }
        System.out.println("number of apples in bag: " + count);
    }
}
