import interfaces.IAssemblyLine;
import interfaces.ILineStep;

public class Transporter {
    public static void main(final String[] args) {
        final ILineStep bodyLineStep = new CreateBody();
        final ILineStep monitorLineStep = new CreateMonitor();
        final ILineStep motherBoardLineStep = new CreateMotherBoard();
        final Laptop laptop = new Laptop();
        final IAssemblyLine iAssemblyLine = new AssemblyLineImpl(bodyLineStep, monitorLineStep, motherBoardLineStep);
        iAssemblyLine.assembleProduct(laptop);
        System.out.println("Finish!");
    }
}
