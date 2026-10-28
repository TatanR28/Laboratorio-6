public class ArrayReferences {

    static void updateFirst(int[] values) {
        values[0] = 99;
    }

    static void replaceArray(int[] values) {
        values = new int[]{7, 8};
        System.out.println("Inside: " + values[0]);
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2};

        updateFirst(numbers);
        replaceArray(numbers);

        System.out.println("Outside: " + numbers[0] + ", " + numbers[1]);
    }
}
