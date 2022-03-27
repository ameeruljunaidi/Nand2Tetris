public class HackAssembler {
    public static void main(String[] args) {
        Assembler a = (args.length == 0) ? new Assembler() : new Assembler(args[0]);
        a.generateMachineCode();
    }
}