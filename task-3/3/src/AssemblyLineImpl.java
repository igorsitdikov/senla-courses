import interfaces.IAssemblyLine;
import interfaces.IProduct;
import interfaces.IProductPart;

public class AssemblyLineImpl implements IAssemblyLine {
    private IProductPart productPartFirst;
    private IProductPart productPartSecond;
    private IProductPart productPartThird;

    public AssemblyLineImpl(final IProductPart productPartFirst,
                            final IProductPart productPartSecond,
                            final IProductPart productPartThird) {
        this.productPartFirst = productPartFirst;
        this.productPartSecond = productPartSecond;
        this.productPartThird = productPartThird;
    }

    @Override
    public IProduct assembleProduct(final IProduct iProduct) {
        System.out.println("Start of the product creating!");
        iProduct.installFirstPart(productPartFirst);
        iProduct.installSecondPart(productPartSecond);
        iProduct.installThirdPart(productPartThird);
        System.out.println("The product was created!");
        return iProduct;
    }
}
