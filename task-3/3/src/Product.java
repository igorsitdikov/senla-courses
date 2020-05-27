import interfaces.IProduct;
import interfaces.IProductPart;

public class Product implements IProduct {

    @Override
    public void installFirstPart(final IProductPart iProductPart) {
        final Monitor monitor = (Monitor) iProductPart;
        System.out.println(String.format("%s was installed", monitor.toString()));
    }

    @Override
    public void installSecondPart(final IProductPart iProductPart) {
        final MotherBoard motherBoard = (MotherBoard) iProductPart;
        System.out.println(String.format("%s was installed", motherBoard.toString()));
    }

    @Override
    public void installThirdPart(final IProductPart iProductPart) {
        final Body body = (Body) iProductPart;
        System.out.println(String.format("%s was installed", body.toString()));
    }
}
