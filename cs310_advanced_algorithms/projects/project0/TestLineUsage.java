package gc.algorithm.exercises;

public class TestLineUsage {
    public static void main(String[] args) {
        LineUsage lineUsage = new LineUsage();
        lineUsage.addObservation("OPERATOR");
        lineUsage.addObservation("USERMGR");
        lineUsage.addObservation("OPERATOR");
        Usage x = lineUsage.findMaxUsage();
        System.out.println(x.getUsername());
        System.out.println(x.getCount());
    }
}
