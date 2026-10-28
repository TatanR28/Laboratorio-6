public class MemoryChallengeCheck {

    public static void main(String[] args) {
        PetProfile original = new PetProfile("Luna");
        PetProfile alias = original;

        alias.registerVisit();

        if (original.getVisits() != 1) {
            throw new AssertionError("Shared object was not updated");
        }

        PetProfile independent = new PetProfile("Luna");

        if (original == independent) {
            throw new AssertionError("Distinct objects share an identity");
        }

        if (independent.getVisits() != 0) {
            throw new AssertionError("Independent state was unexpectedly modified");
        }

        System.out.println("All reference checks passed");
    }
}
