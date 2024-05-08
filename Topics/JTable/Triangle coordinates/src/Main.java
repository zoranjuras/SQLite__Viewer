import javax.swing.table.DefaultTableModel;
import java.util.Scanner;

class TriangleTable {
    public static void main(String[] args) {
        // implement me

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("x");
        model.addColumn("y");
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            Integer[] row = new Integer[] {scanner.nextInt(), scanner.nextInt()};
            model.addRow(row);
        }

        // do not remove the code below
        TableModelTest.test(model);
    }
}