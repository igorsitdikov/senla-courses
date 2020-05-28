public class Bouquet {
    private Integer size;
    private Flower[] flowers;

    public Bouquet(final Integer size) {
        this.size = size;
        flowers = new Flower[0];
    }

    public void addFlower(final Flower flower) {
        if (flowers.length < size) {
            flowers = ArrayUtils.expandArray(flowers);
            flowers[flowers.length - 1] = flower;
            System.out.printf("The %s was added to the bouquet.%n", flower.toString());
        } else {
            System.out.println("The bouquet is full!");
        }
    }

    public Integer getSize() {
        return size;
    }

    public Flower[] getFlowers() {
        return flowers;
    }
}
