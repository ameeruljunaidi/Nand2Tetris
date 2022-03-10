public class VMTranslator {
    public static void main(String[] args) {
        Translator a = (args.length == 0) ? new Translator() : new Translator(args[0]);
        a.generateAssemblyCode();
    }
}
