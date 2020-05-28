public class ArrayUtils {
    public static Flower[] expandArray(final Flower[] array) {
        final Flower[] flowersBuffer = new Flower[array.length + 1];
        System.arraycopy(array, 0, flowersBuffer, 0, array.length);
        return flowersBuffer;
    }
}
