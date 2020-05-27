import interfaces.IAssemblyLine;
import interfaces.ILineStep;

public class Transporter {
    public static void main(final String[] args) {
        final ILineStep bodyLineStep = new CreateBody();
        final ILineStep monitorLineStep = new CreateMonitor();
        final ILineStep motherBoardLineStep = new CreateMotherBoard();

        final IAssemblyLine iAssemblyLine = new Laptop((Monitor) monitorLineStep.buildProductPart(), (MotherBoard) motherBoardLineStep.buildProductPart(), (Body) bodyLineStep.buildProductPart());
        iAssemblyLine.assembleProduct(new Product());
        System.out.println("Finish!");
    }
}
