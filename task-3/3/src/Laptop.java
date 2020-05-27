import interfaces.IAssemblyLine;
import interfaces.IProduct;

public class Laptop implements IAssemblyLine {
    private Monitor monitor;
    private MotherBoard motherBoard;
    private Body body;

    public Laptop(final Monitor monitor, final MotherBoard motherBoard, final Body body) {
        this.monitor = monitor;
        this.motherBoard = motherBoard;
        this.body = body;
    }

    @Override
    public IProduct assembleProduct(final IProduct iProduct) {
        System.out.println("Start of laptop creating!");
        iProduct.installFirstPart(monitor);
        iProduct.installSecondPart(motherBoard);
        iProduct.installThirdPart(body);
        System.out.println("Laptop was created!");
        return iProduct;
    }
}
