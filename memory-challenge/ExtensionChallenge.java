/** Reto de ampliacion: cambiar el estado de un objeto vs. devolver una referencia nueva. */
public class ExtensionChallenge {

    // Modifica el MISMO objeto recibido (cambia su estado).
    static void registerTwoVisits(PetProfile pet) {
        pet.registerVisit();
        pet.registerVisit();
    }

    // Crea una instancia nueva y la DEVUELVE para que el llamador la asigne.
    static PetProfile createNewProfile(String name) {
        return new PetProfile(name);
    }

    public static void main(String[] args) {
        PetProfile luna = new PetProfile("Luna");
        registerTwoVisits(luna);
        System.out.println("Luna visits: " + luna.getVisits());

        PetProfile created = createNewProfile("Kira");
        System.out.println("Created: " + created.getName()
                + " (" + created.getVisits() + ")");

        // Error B: ignorar el retorno -> el objeto nuevo se pierde
        createNewProfile("Perdida");
        System.out.println("luna sigue igual: " + luna.getName());

        // Microejercicio
        int[] values = {3, 6};
        int[] other = values;
        other[1] = 9;
        System.out.println(values[1]);
        System.out.println(values == other);
    }
}
