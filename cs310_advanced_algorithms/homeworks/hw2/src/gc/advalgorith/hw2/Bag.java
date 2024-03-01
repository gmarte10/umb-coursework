package gc.advalgorith.hw2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Bag<Item> {
    private List<Item> list;

    public Bag() {
        list = new LinkedList<>();
    }

    public void add(Item x) {
        list.add(x);
    }

    public Iterator<Item> iterator() {
        return list.iterator();
    }
}
