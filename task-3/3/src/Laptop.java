import interfaces.IProduct;
import interfaces.IProductPart;

public class Laptop implements IProduct {
    private IProductPart monitor;
    private IProductPart motherBoard;
    private IProductPart body;

    @Override
    public void installFirstPart(final IProductPart iProductPart) {
        monitor = iProductPart;
        System.out.printf("%s was installed%n", monitor.toString());
    }

    @Override
    public void installSecondPart(final IProductPart iProductPart) {
        motherBoard = iProductPart;
        System.out.printf("%s was installed%n", motherBoard.toString());
    }

    @Override
    public void installThirdPart(final IProductPart iProductPart) {
        body = iProductPart;
        System.out.printf("%s was installed%n", body.toString());
    }
}
