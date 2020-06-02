import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateMonitor implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final Monitor monitor = new Monitor();
        System.out.printf("%s is building!%n", monitor.toString());
        return monitor;
    }
}
