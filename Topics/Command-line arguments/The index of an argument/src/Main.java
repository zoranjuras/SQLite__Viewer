class Problem {
    public static void main(String[] args) {
        boolean found = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("test")) {
                found = true;
                System.out.println(i);
            }
        }
        if (!found) System.out.println(-1);
    }
}