import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        System.out.printf("The input is %d. The result of the multiplication is %d.", input, multiply(input));
    }

    public static int multiply(int input) {
        return (int) Math.pow(input, 3);
    }
}