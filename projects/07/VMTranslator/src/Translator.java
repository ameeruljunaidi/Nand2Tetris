import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Translator {
    private final Parser parser;
    private final List<String> translatedLines;

    public Translator(String filename) {
        parser = new Parser(filename);
        translatedLines = new ArrayList<>();
        translate();
    }

    public Translator() {
        parser = new Parser();
        translatedLines = new ArrayList<>();
        translate();
    }

    private void translate() {
        while (parser.hasMoreCommands()) {
            if (parser.getCurrentCommand().equals("C_ARITHMETIC")) writeArithmetic(parser.arg1());
            else if (parser.getCurrentCommand().equals("C_PUSH")) writePushPop("C_PUSH", parser.arg1(), parser.arg2());
            else if (parser.getCurrentCommand().equals("C_POP")) writePushPop("C_POP", parser.arg1(), parser.arg2());
            else System.exit(234);
            parser.advance();
        }
        parser.resetCurrentCommand();
    }

    /**
     * Writes to the output file the assembly code that implements the given arithmetic-logical command
     *
     * @param command the arithmetic command
     */
    private void writeArithmetic(String command) {
        translatedLines.add("//" + parser.getCurrentCommand());

        // TODO

        translatedLines.add("");
    }

    /**
     * Writes to the output file the assembly code that implements the given push or pop command
     *
     * @param command the push or pop command
     * @param segment the segment of the memory to push or pop to
     * @param index   the index in memory
     */
    private void writePushPop(String command, String segment, int index) {
        translatedLines.add("//" + parser.getCurrentCommand());

        // TODO

        translatedLines.add("");
    }


    public void generateAssemblyCode(String filename) {
        try {
            FileWriter fw = new FileWriter(parser.getCurrentDirectory() + "/" + filename + ".asm");
            for (String str : translatedLines) fw.write(str + System.lineSeparator());
            fw.close();
            System.out.println("Converted to .asm file: " + filename + ".asm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateAssemblyCode() {
        generateAssemblyCode(parser.getFileName());
    }
}
