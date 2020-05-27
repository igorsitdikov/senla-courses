public class Bouquet {
    private final Integer size;
    private Flower[] flowers;

    public Bouquet(final Integer size) {
        this.size = size;
        flowers = new Flower[0];
    }

    public void addFlower(final Flower flower) {
        if (flowers.length < size) {
            expandArray();
            flowers[flowers.length - 1] = flower;
            System.out.println(String.format("The %s was added to the bouquet.", flower.getName()));
        } else {
            System.out.println("The bouquet is full!");
        }
    }

    private void expandArray() {
        final Flower[] flowersBuffer = new Flower[flowers.length + 1];
        System.arraycopy(flowers, 0, flowersBuffer, 0, flowers.length);
        flowers = flowersBuffer;
    }

    public void cost() {
        Double price = 0.0;
        if (flowers.length == size) {
            for (final Flower flower : flowers) {
                price += flower.getPrice();
            }
            System.out.println(String.format("The bouquet price is %.2f", price));
        } else {
            System.out.println("The bouquet is not full! Add more flowers!");
        }
    }
}
