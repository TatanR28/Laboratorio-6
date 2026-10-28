public class MemoryChallenge {

    private static void update(PetProfile current) {
        current.registerVisit();

        current = new PetProfile("Nova");
        current.registerVisit();

        System.out.println("Inside: " + current.getName()
                + " (" + current.getVisits() + ")");
    }

    private static PetProfile createReplacement(PetProfile previous) {
        return new PetProfile("Max");
    }

    public static void main(String[] args) {
        PetProfile pet = new PetProfile("Luna");
        PetProfile alias = pet;

        update(pet);

        System.out.println("Outside: " + pet.getName()
                + " (" + pet.getVisits() + ")");

        PetProfile replacement = createReplacement(pet);

        System.out.println("Same reference: " + (pet == alias));
        System.out.println("Replacement: " + replacement.getName()
                + " (" + replacement.getVisits() + ")");
    }
}
