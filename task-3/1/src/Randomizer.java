import java.util.Random;

public class Randomizer {
    private static final Integer THREE_DIGIT_MIN = 100;
    private static final Integer THREE_DIGIT_MAX = 999;
    private Random random = new Random();

    public Integer createRandomNumber() {
        final int randomThreeDigitNumber = THREE_DIGIT_MIN + random.nextInt(THREE_DIGIT_MAX - THREE_DIGIT_MIN);
        System.out.printf("Number is %d%n", randomThreeDigitNumber);
        return randomThreeDigitNumber;
    }

}
