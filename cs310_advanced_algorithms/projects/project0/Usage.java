package gc.algorithm.exercises;

public class Usage {
    
    private int count;
    private String username;

    public Usage(int count, String username) {
        this.count = count;
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public String getUsername() {
        return username;
    }

    // find equality between two Usage objects
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Usage temp = (Usage) other;
        return temp.getUsername().equals(getUsername()) && temp.getCount() == getCount();
    }

    public static void main(String[] args) {
        Usage usage = new Usage(1, "ALIMONY");
        System.out.println("count = " + usage.getCount());
        System.out.println("username = " + usage.getUsername());
    }
}
