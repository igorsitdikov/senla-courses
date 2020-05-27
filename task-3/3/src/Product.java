import interfaces.IProduct;
import interfaces.IProductPart;

public class Product implements IProduct {
    private Monitor monitor;
    private MotherBoard motherBoard;
    private Body body;

    @Override
    public void installFirstPart(final IProductPart iProductPart) {
        monitor = (Monitor) iProductPart;
        System.out.println(String.format("%s was installed", monitor.toString()));
    }

    @Override
    public void installSecondPart(final IProductPart iProductPart) {
        motherBoard = (MotherBoard) iProductPart;
        System.out.println(String.format("%s was installed", motherBoard.toString()));
    }

    @Override
    public void installThirdPart(final IProductPart iProductPart) {
        body = (Body) iProductPart;
        System.out.println(String.format("%s was installed", body.toString()));
    }
}
