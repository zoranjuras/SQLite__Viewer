class Problem {
    public static void main(String[] args) {
        String operator = args[0];
        switch (operator) {
            case "+" -> System.out.println(Integer.parseInt(args[1]) + Integer.parseInt(args[2]));
            case "-" -> System.out.println(Integer.parseInt(args[1]) - Integer.parseInt(args[2]));
            case "*" -> System.out.println(Integer.parseInt(args[1]) * Integer.parseInt(args[2]));
            default -> System.out.println("Unknown operator");
        }
    }
}