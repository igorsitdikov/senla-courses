import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;

public class AssemblyLineImpl implements IAssemblyLine {
    private ILineStep productPartFirst;
    private ILineStep productPartSecond;
    private ILineStep productPartThird;

    public AssemblyLineImpl(final ILineStep productPartFirst,
                            final ILineStep productPartSecond,
                            final ILineStep productPartThird) {
        this.productPartFirst = productPartFirst;
        this.productPartSecond = productPartSecond;
        this.productPartThird = productPartThird;
    }

    @Override
    public IProduct assembleProduct(final IProduct iProduct) {
        System.out.println("Start of the product creating!");
        iProduct.installFirstPart(productPartFirst.buildProductPart());
        iProduct.installSecondPart(productPartSecond.buildProductPart());
        iProduct.installThirdPart(productPartThird.buildProductPart());
        System.out.println("The product was created!");
        return iProduct;
    }
}
