public class Service {
    public void calculateCost(final Flower[] flowers, final Integer bouquetSize) {
        Double price = 0.0;
        if (flowers.length == bouquetSize) {
            for (final Flower flower : flowers) {
                price += flower.getPrice();
            }
            System.out.printf("The bouquet price is %.2f%n", price);
        } else {
            System.out.println("The bouquet is not full! Add more flowers!");
        }
    }
}
