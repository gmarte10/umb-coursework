package gc.advalgorith.hw2;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Set<String> setH = new HashSet<>();
        Set<String> setT = new TreeSet<>();

        String a = "apple";
        String p = "pear";

        setH.add(a);
        setH.add(p);
        setT.add(a);
        setT.add(p);

        System.out.println(setH.equals(setT));
    }
}
