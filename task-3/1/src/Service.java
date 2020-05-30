public class Service {

    public static void compute() {
        Randomizer randomizer = new Randomizer();
        final Integer first = randomizer.createRandomNumber();
        final Integer second = randomizer.createRandomNumber();
        final Integer third = randomizer.createRandomNumber();
        final Integer sequence = Concatenator.concatNumbers(first, second);
        final Integer difference = Calculator.subtract(sequence, third);
        System.out.printf("Difference : %d%n", difference);
    }
}
