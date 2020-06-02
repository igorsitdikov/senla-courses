import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateBody implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final Body body = new Body();
        System.out.printf("%s is building!%n", body.toString());
        return body;
    }
}
