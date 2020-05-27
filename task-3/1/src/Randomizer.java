import java.util.Random;

public class Randomizer {
    private final Integer THREE_DIGIT = 100;
    private final Random random = new Random();

    private Integer createRandomNumber() {
        final int randomThreeDigitNumber = THREE_DIGIT + random.nextInt(999 - THREE_DIGIT);
        System.out.println(String.format("Number is %d", randomThreeDigitNumber));
        return randomThreeDigitNumber;
    }

    private Integer concatNumbers(Integer first, Integer second) {
        return first * 1000 + second;
    }

    private Integer calculateDifference(Integer first, Integer second) {
        return first - second;
    }

    public void compute() {
        final Integer first = createRandomNumber();
        final Integer second = createRandomNumber();
        final Integer third = createRandomNumber();
        final Integer sequence = concatNumbers(first, second);
        final Integer difference = calculateDifference(sequence, third);
        System.out.println(String.format("Difference : %d", difference));
    }
}
