import interfaces.IAssemblyLine;
import interfaces.ILineStep;

public class Transporter {
    public static void main(final String[] args) {
        final ILineStep bodyLineStep = new CreateBody();
        final ILineStep monitorLineStep = new CreateMonitor();
        final ILineStep motherBoardLineStep = new CreateMotherBoard();
        final Laptop laptop = new Laptop();
        final IAssemblyLine iAssemblyLine =
            new AssemblyLineImpl(monitorLineStep.buildProductPart(), motherBoardLineStep.buildProductPart(),
                                 bodyLineStep.buildProductPart());
        iAssemblyLine.assembleProduct(laptop);
        System.out.println("Finish!");
    }
}
