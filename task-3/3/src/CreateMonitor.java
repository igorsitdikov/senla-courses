import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateMonitor implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final Monitor monitor = new Monitor();
        System.out.println(String.format("%s is building!", monitor.toString()));
        return monitor;
    }
}
