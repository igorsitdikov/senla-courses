import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateMotherBoard implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final MotherBoard motherBoard = new MotherBoard();
        System.out.printf("%s is building!%n", motherBoard.toString());
        return motherBoard;
    }
}
