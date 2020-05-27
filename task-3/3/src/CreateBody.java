import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateBody implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final Body body = new Body();
        System.out.println(String.format("%s is building!", body.toString()));
        return body;
    }
}
