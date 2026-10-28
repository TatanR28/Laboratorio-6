public class PrimitiveParameters {

    static void increase(int value) {
        value = value + 10;
        System.out.println("Inside: " + value);
    }

    public static void main(String[] args) {
        int quantity = 5;
        increase(quantity);
        System.out.println("Outside: " + quantity);
    }
}
