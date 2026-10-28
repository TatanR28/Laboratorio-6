public class ExecutionFlow {

    public static void main(String[] args) {
        int base = 7;
        int bonus = 5;

        int total = add(base, bonus);

        System.out.println("Total: " + total);
    }

    static int add(int first, int second) {
        return first + second;
    }
}
