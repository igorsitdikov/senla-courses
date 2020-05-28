public class BouquetMaker {
    public void createBouquet () {
        final Flower roseWhite = new Rose("White rose", 5.30);
        final Flower roseRed = new Rose("Red rose", 3.99);
        final Flower dandelion = new Dandelion("Dandelion", 0.91);
        final Flower geranium = new Geranium("Geranium", 13.43);
        final Flower gladiolus = new Gladiolus("Gladiolus", 2.40);
        final Flower chamomile = new Chamomile("Chamomile", 1.70);

        final Bouquet bouquet = new Bouquet(6);

        bouquet.addFlower(roseWhite);
        bouquet.addFlower(roseRed);
        bouquet.addFlower(dandelion);
        bouquet.addFlower(geranium);
        bouquet.addFlower(gladiolus);
        bouquet.addFlower(chamomile);

        final Service service = new Service();
        service.calculateCost(bouquet.getFlowers(), bouquet.getSize());
    }
}
