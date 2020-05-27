public abstract class Flower {
    private final String name;
    private final Double price;

    public Flower(final String name, final Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
