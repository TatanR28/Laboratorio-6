/** Fragmentos de la practica 6.4: String, arreglos, null y reasignacion de alias. */
public class ReferenceExtras {

    static final class Pet {
        private String name;
        Pet(String name) { this.name = name; }
        String getName() { return name; }
        void rename(String newName) { name = newName; }
    }

    public static void main(String[] args) {
        // 1) == frente a equals()
        String firstText = new String("Luna");
        String secondText = new String("Luna");
        System.out.println("String ==     : " + (firstText == secondText));
        System.out.println("String equals : " + firstText.equals(secondText));

        // 2) Arreglos
        int[] firstNumbers = {2, 4, 6};
        int[] sameArray = firstNumbers;
        int[] otherArray = {2, 4, 6};
        sameArray[0] = 99;
        System.out.println(firstNumbers[0]);
        System.out.println(otherArray[0]);
        System.out.println(firstNumbers == sameArray);
        System.out.println(firstNumbers == otherArray);

        // 3) Ejercicio: reasignar un alias
        Pet first = new Pet("Luna");
        Pet alias = first;
        Pet independent = new Pet("Luna");
        alias.rename("Sol");
        Pet third = first;
        System.out.println("third: " + third.getName());
        alias = new Pet("Nova");
        alias.rename("Max");
        System.out.println("first=" + first.getName()
                + " third=" + third.getName()
                + " independent=" + independent.getName()
                + " alias=" + alias.getName());

        // 4) Referencia nula
        Pet missing = null;
        if (missing != null) {
            System.out.println(missing.getName());
        } else {
            System.out.println("missing es null: no se invoca getName()");
        }
        try {
            System.out.println(missing.getName());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException capturada");
        }
    }
}
