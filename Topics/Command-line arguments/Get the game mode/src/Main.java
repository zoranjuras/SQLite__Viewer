class Problem {
    public static void main(String[] args) {
        boolean modeFound = false;
        for (int i = 0; i < args.length; i+=2) {
            if (args[i].equals("mode")) {
                modeFound = true;
                System.out.println(args[i + 1]);
                break;
            }
        }
        if (!modeFound) {
            System.out.println("default");
        }
    }
}