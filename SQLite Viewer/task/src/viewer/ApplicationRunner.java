package viewer;

public class ApplicationRunner {
    public static void main(String[] args) {
        new SQLiteViewer();
        Object o = new Object();
        System.out.println(o.getClass());
    }
}
