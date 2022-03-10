import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Translator {
    private final Parser parser;
    private final List<String> translatedLines;
    private int currentLine;
    private int continueIndex;

    public Translator(String filename) {
        parser = new Parser(filename);
        translatedLines = new ArrayList<>();
        continueIndex = 0;
        currentLine = 0;
        translate();
    }

    public Translator() {
        parser = new Parser();
        translatedLines = new ArrayList<>();
        currentLine = 0;
        continueIndex = 0;
        translate();
    }

    /**
     * Write to the list of Strings depending on what type of command it is
     * The mapping available is: SP, LCL, ARG, THIS, THAT, TEMP, R13, R14, R15
     */
    private void translate() {
        while (parser.hasMoreCommands()) {
            if (parser.commandType().equals("C_ARITHMETIC")) writeArithmetic(parser.arg1());
            else if (parser.commandType().equals("C_PUSH")) writePush("C_PUSH", parser.arg1(), parser.arg2());
            else if (parser.commandType().equals("C_POP")) writePush("C_POP", parser.arg1(), parser.arg2());
            else System.exit(69);
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
        includeComment();

        switch (command) {
            case "add":
                addArithmetic("add");
                break;
            case "sub":
                addArithmetic("sub");
            case "eq":
                addComparison("eq");
                break;
            case "lt":
                addComparison("lt");
                break;
            case "gt":
                addComparison("gt");
                break;
            case "not":
                addLogic("!");
                break;
            case "neg":
                addLogic("-");
                break;
            case "and":
                addLogic("&");
                break;
            case "or":
                addLogic("|");
                break;
            default:
                break;
        }

        emptyLine();
    }

    private void addLogic(String type) {
        decrementPointer();
        goToAddressAtPointer();
        if (type.equals("!") || type.equals("-")) {
            addLine("M=" + type + "M");
            incrementPointer();
            return;
        }
        getValueAtPointer();
        decrementPointer();
        goToAddressAtPointer();
        addLine("M=D" + type + "M");
        incrementPointer();
    }

    private void addArithmetic(String type) {
        decrementPointer();
        getValueAtPointer();
        decrementPointer();
        addLine("@SP");
        addLine("A=M");
        if (type.equals("add")) addLine("M=M+D");
        else if (type.equals("sub")) addLine("M=D-M");
        incrementPointer();
    }

    private void addComparison(String type) {
        String t = type.toUpperCase();
        String ti = t + continueIndex;

        decrementPointer();
        getValueAtPointer();
        decrementPointer();
        goToAddressAtPointer();
        if (type.equals("lt")) addLine("D=M-D");
        else addLine("D=D-M");

        emptyLine();
        addLine("@" + ti);
        addLine("D;J" + t);

        emptyLine();
        addLine("@N" + ti);
        addLine("0;J" + t);

        emptyLine();
        addLine("(" + ti + ")");
        goToAddressAtPointer();
        addLine("M=-1");
        currentContinue();

        emptyLine();
        addLine("(N" + ti + ")");
        goToAddressAtPointer();
        addLine("M=0");
        currentContinue();

        emptyLine();
        addContinue();
        incrementPointer();
    }

    /**
     * Writes to the output file the assembly code that implements the given push or pop command
     *
     * @param command the push or pop command
     * @param segment the segment of the memory to push or pop to
     * @param index   the index in memory
     */
    private void writePush(String command, String segment, int index) {
        includeComment();

        if (segment.equals("constant")) {
            getValue(index);
            addCurrentDToM();
            incrementPointer();
        }

        emptyLine();
    }


    private void includeComment() {
        translatedLines.add("// " + parser.getCurrentCommand());
    }

    private void emptyLine() {
        translatedLines.add("");
    }

    private void addLine(String line) {
        translatedLines.add(line);
        ++currentLine;
    }

    private void currentContinue() {
        addLine("@CONTINUE" + continueIndex);
        addLine("0;JEQ");
    }

    private void addContinue() {
        addLine("(CONTINUE" + continueIndex + ")");
        ++continueIndex;
    }
    private void getValue(int index) {
        addLine("@" + index);
        addLine("D=A");
    }

    private void addCurrentDToM() {
        addLine("@SP");
        addLine("A=M");
        addLine("M=D");
    }

    private void incrementPointer() {
        addLine("@SP");
        addLine("M=M+1");
    }

    private void decrementPointer() {
        addLine("@SP");
        addLine("M=M-1");
    }

    private void goToAddressAtPointer() {
        addLine("@SP");
        addLine("A=M");
    }

    private void getValueAtPointer() {
        goToAddressAtPointer();
        addLine("D=M");
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

    public static void testTranslator() {
        Translator t = new Translator("StackArithmetic/StackTest/StackTest.vm");
        for (String line : t.translatedLines) System.out.println(line);
        System.out.println(t.currentLine);
        t.generateAssemblyCode();
    }
}
