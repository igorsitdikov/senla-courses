public class Service {

    public static void compute() {
        final Integer first = Randomizer.createRandomNumber();
        final Integer second = Randomizer.createRandomNumber();
        final Integer third = Randomizer.createRandomNumber();
        final Integer sequence = Concatenator.concatNumbers(first, second);
        final Integer difference = Calculator.subtract(sequence, third);
        System.out.printf("Difference : %d%n", difference);
    }
}
