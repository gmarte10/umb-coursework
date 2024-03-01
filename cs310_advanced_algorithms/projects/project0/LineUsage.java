package gc.algorithm.exercises;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LineUsage {
    private Map<String, Integer> map;

    public LineUsage() {
        map = new HashMap<>();
    }

    // pairs a username with the appropriate count
    public void addObservation(String username) {
        if (!map.containsKey(username)) {
            map.put(username, 1);
        }
        else {
            Integer update = map.get(username) + 1;
            map.replace(username, update);
        }
    }

    // finds the username with the max count and returns it as a Usage object
    public Usage findMaxUsage() {
        Usage max = new Usage(0, "NONE");
        Set<String> users = map.keySet();
        for (String temp : users) {
            int currCount = map.get(temp);
            if (currCount > max.getCount()) {
                max = new Usage(currCount, temp);
            }
        }
        return max;
    }

    // used to find equality between two LineUsage objects
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        LineUsage luOther = (LineUsage) other;
        return luOther.map.equals(map) && luOther.findMaxUsage() == findMaxUsage();
    }
}
