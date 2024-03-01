package gc.advalgorith.hw2;
import java.util.ArrayList;
import java.util.List;

public class TestArrayList {

    public static void main( String [ ] args ) {
        List<String> array = new ArrayList<String>();
        array.add("apple");
        array.add("banana");
        // Loop through the collection by index
        for ( int i = 0; i < array.size( ); i++ )  {
            System.out.println( array.get( i ) );
        }
        // Using the fact that Collections are Iterable
        for (String s: array) {
            System.out.println(s);
        }
        ((ArrayList<String>) array).trimToSize();
    }
}
