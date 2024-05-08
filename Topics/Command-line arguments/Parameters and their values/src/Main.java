class Problem {
    public static void main(String[] args) {
        for (int i = 0; i < args.length - 1; i+=2) {
            printArgument(args[i], args[i + 1]);
        }
    }

    private static void printArgument(String s1, String s2) {
        System.out.printf("%s=%s\n", s1, s2);
    }
}