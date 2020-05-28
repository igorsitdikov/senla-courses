import interfaces.IProduct;
import interfaces.IProductPart;

public class Laptop implements IProduct {
    private Monitor monitor;
    private MotherBoard motherBoard;
    private Body body;

    @Override
    public void installFirstPart(final IProductPart iProductPart) {
        monitor = (Monitor) iProductPart;
        System.out.printf("%s was installed%n", monitor.toString());
    }

    @Override
    public void installSecondPart(final IProductPart iProductPart) {
        motherBoard = (MotherBoard) iProductPart;
        System.out.printf("%s was installed%n", motherBoard.toString());
    }

    @Override
    public void installThirdPart(final IProductPart iProductPart) {
        body = (Body) iProductPart;
        System.out.printf("%s was installed%n", body.toString());
    }
}
