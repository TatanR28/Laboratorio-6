public class MethodStack {

    public static void main(String[] args) {
        int initialValue = 4;
        int result = calculate(initialValue);

        System.out.println("Final result: " + result);
    }

    static int calculate(int value) {
        int doubled = doubleValue(value);
        return doubled + 3;
    }

    static int doubleValue(int number) {
        return number * 2;
    }
}
