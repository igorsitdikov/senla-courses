import interfaces.ILineStep;
import interfaces.IProductPart;

public class CreateMotherBoard implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        final MotherBoard motherBoard = new MotherBoard();
        System.out.println(String.format("%s is building!", motherBoard.toString()));
        return motherBoard;
    }
}
