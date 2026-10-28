/** Variantes A-D de MemoryDemo y ejercicio de reasignar a null. */
public class MemoryVariants {
    static final class Pet {
        String name;
        Pet(String name) { this.name = name; }
    }

    // Variante A: reasignar ANTES de modificar
    static void changeA(Pet local) {
        local = new Pet("Otra");
        local.name = "Sol";
        System.out.println("A Dentro: " + local.name);
    }

    // Variante B: sin reasignacion
    static void changeB(Pet local) {
        local.name = "Sol";
        System.out.println("B Dentro: " + local.name);
    }

    // Variante C: retornar un objeto nuevo
    static Pet createReplacement() {
        return new Pet("Nueva");
    }

    // Ejercicio: reasignar el parametro a null
    static void clearLocalReference(Object value) {
        value = null;
    }

    public static void main(String[] args) {
        Pet a = new Pet("Luna");
        changeA(a);
        System.out.println("A Fuera: " + a.name);

        Pet b = new Pet("Luna");
        changeB(b);
        System.out.println("B Fuera: " + b.name);

        Pet c = new Pet("Luna");
        c = createReplacement();
        System.out.println("C: " + c.name);

        Pet d = new Pet("Luna");
        Pet alias = d;
        changeB(d);
        System.out.println("D alias: " + alias.name);

        Object thing = new Object();
        clearLocalReference(thing);
        System.out.println("thing quedo en null? " + (thing == null));
    }
}
