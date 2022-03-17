import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Translator {
    private final Parser parser;
    private final List<String> translatedLines;
    private int currentLine;
    private int continueIndex;
    private String calleeName;
    private String callerName;

    public Translator(String filename) {
        parser = new Parser(filename);
        translatedLines = new ArrayList<>();
        continueIndex = 0;
        currentLine = 0;
        calleeName = "Main";
        writeInit();
        translate();
    }

    public Translator() {
        parser = new Parser();
        translatedLines = new ArrayList<>();
        currentLine = 0;
        continueIndex = 0;
        calleeName = "Main";
        writeInit();
        translate();
    }

    /**
     * Write to the list of Strings depending on what type of command it is
     * The mapping available is: SP, LCL, ARG, THIS, THAT, TEMP, R13, R14, R15
     */
    private void translate() {
        while (parser.hasMoreCommands()) {
            switch (parser.commandType()) {
                case "C_ARITHMETIC" -> writeArithmetic(parser.arg1());
                case "C_PUSH" -> writePushPop("C_PUSH", parser.arg1(), parser.arg2());
                case "C_POP" -> writePushPop("C_POP", parser.arg1(), parser.arg2());
                case "C_LABEL" -> writeLabel(parser.arg1());
                case "C_GOTO" -> writeGoto(parser.arg1());
                case "C_IF" -> writeIfGoto(parser.arg1());
                case "C_FUNCTION" -> writeFunction(parser.arg1(), parser.arg2());
                case "C_CALL" -> writeCall(parser.arg1(), parser.arg2());
                case "C_RETURN" -> writeReturn();
                default -> System.exit(2);
            }
            parser.advance();
        }
        parser.resetCurrentCommand();
    }

    private void writeInit() {
        write("// initializing pointers", true);
        // TODO
        emptyLine();
    }

    /**
     * Writes to the output file the assembly code that implements the given arithmetic-logical command
     *
     * @param command the arithmetic command
     */
    private void writeArithmetic(String command) {
        includeComment();
        switch (command) {
            case "add" -> writeAddSub("add");
            case "sub" -> writeAddSub("sub");
            case "eq" -> writeComparison("eq");
            case "lt" -> writeComparison("lt");
            case "gt" -> writeComparison("gt");
            case "not" -> writeLogic("!");
            case "neg" -> writeLogic("-");
            case "and" -> writeLogic("&");
            case "or" -> writeLogic("|");
        }
        emptyLine();
    }

    /**
     * Writes to the output file the assembly code that implements the given push or pop command
     *
     * @param command the push or pop command
     * @param segment the segment of the memory to push or pop to
     * @param index   the index in memory
     */
    private void writePushPop(String command, String segment, int index) {
        includeComment();
        if (command.equals("C_PUSH")) {
            switch (segment) {
                case "constant" -> pushConstant(index);
                case "local" -> pushToSegmentOf("LCL", index);
                case "argument" -> pushToSegmentOf("ARG", index);
                case "this" -> pushToSegmentOf("THIS", index);
                case "that" -> pushToSegmentOf("THAT", index);
                case "temp" -> pushToSegmentOf("TEMP", index);
                case "static" -> pushToStatic(index);
                case "pointer" -> pushPointer(index);
            }
        } else if (command.equals("C_POP")) {
            switch (segment) {
                case "local" -> popToSegmentOf("LCL", index);
                case "argument" -> popToSegmentOf("ARG", index);
                case "this" -> popToSegmentOf("THIS", index);
                case "that" -> popToSegmentOf("THAT", index);
                case "temp" -> popToSegmentOf("TEMP", index);
                case "static" -> popToStatic(index);
                case "pointer" -> popPointer(index);
            }
        }
        emptyLine();
    }

    private void pushConstant(int index) {
        getConstant(index);
        storeValueToAddressPointedAt("SP");
        incrementStackPointer();
    }

    private void pushToSegmentOf(String segment, int index) {
        storeSegmentAddressIndexedAt("R13", segment, index);
        goToAddressPointedAt("R13");
        getValueAtAddress();
        storeValueToAddressPointedAt("SP");
        incrementStackPointer();
    }

    private void popToSegmentOf(String segment, int index) {
        storeSegmentAddressIndexedAt("R13", segment, index);
        getTopValue();
        storeValueToAddressPointedAt("R13");
    }

    private String getStaticVariable(int index) {
        return parser.getFileName() + "." + index;
    }

    private void pushToStatic(int index) {
        write("@" + getStaticVariable(index));
        getValueAtAddress();
        storeValueToAddressPointedAt("SP");
        incrementStackPointer();
    }

    private void popToStatic(int index) {
        getTopValue();
        storeValueToAddress(getStaticVariable(index));
    }

    private void pushPointer(int index) {
        if (index == 0) write("@3");
        else write("@4");
        getValueAtAddress();
        storeValueToAddressPointedAt("SP");
        incrementStackPointer();
    }

    private void popPointer(int index) {
        getTopValue();
        if (index == 0) storeValueToAddress("3");
        else storeValueToAddress("4");
    }


    /**
     * Set the A register to the location of segment at index
     *
     * @param at      the temp memory location
     * @param segment the segment to go to
     * @param index   the index location
     */
    private void storeSegmentAddressIndexedAt(String at, String segment, int index) {
        getConstant(index);
        if (segment.equals("TEMP")) {
            write("@5");
            write("D=A+D");
        } else {
            write("@" + segment);
            write("D=D+M");
        }
        write("@" + at);
        write("M=D");
    }

    private void writeLogic(String type) {
        decrementStackPointer();
        goToAddressAtStackPointer();
        if (type.equals("!") || type.equals("-")) {
            write("M=" + type + "M");
            incrementStackPointer();
            return;
        }
        getValueAtAddress();
        decrementStackPointer();
        goToAddressAtStackPointer();
        write("M=D" + type + "M");
        incrementStackPointer();
    }

    private void writeAddSub(String type) {
        getTopValueAndGoToNextTopAddress();
        if (type.equals("add")) write("M=D+M");
        else if (type.equals("sub")) write("M=M-D");
        incrementStackPointer();
    }

    private void writeComparison(String type) {
        // Can probably refactor this, but this works

        String t = type.toUpperCase();
        String ti = t + continueIndex;

        getTopValueAndGoToNextTopAddress();
        write("D=D-M");

        emptyLine();
        write("@" + ti);
        write("D;J" + t);

        // Is it equal, 0 if equal
        if (type.equals("lt") || type.equals("gt")) {
            emptyLine();
            write("@JEQ" + continueIndex);
            write("D;JEQ");
        }

        emptyLine();
        write("@N" + ti);
        write("0;JEQ");

        emptyLine();
        write("(" + ti + ")", true);
        goToAddressAtStackPointer();
        if (type.equals("lt") || type.equals("gt")) write("M=0");
        else write("M=-1");
        currentContinue();

        // Is it equal, 0 if equal
        if (type.equals("lt") || type.equals("gt")) {
            emptyLine();
            write("(JEQ" + continueIndex + ")", true);
            goToAddressAtStackPointer();
            write("M=0");
            currentContinue();
        }

        emptyLine();
        write("(N" + ti + ")", true);
        goToAddressAtStackPointer();
        if (type.equals("lt") || type.equals("gt")) write("M=-1");
        else write("M=0");
        currentContinue();

        emptyLine();
        addContinue();
        incrementStackPointer();
    }

    private void writeReturn() {
        includeComment();

        // endFrame (R13) = LCL
        write("@R1");
        write("D=M");
        write("@R13");
        write("M=D");

        // retAddr (R14) = *(endFrame - 5)
        write("@5");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("@R14");
        write("M=D");

        // ARG = pop
        write("@SP");
        write("M=M-1");
        write("A=M");
        write("D=M");
        write("@R2");
        write("A=M");
        write("M=D");
        write("@R2");
        write("D=M");
        write("@SP");
        write("M=D+1");

        // restore THAT
        write("@R1");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@R4");
        write("M=D");

        // restore THIS
        write("@R2");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@R3");
        write("M=D");

        // restore ARG
        write("@R3");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@R2");
        write("M=D");

        // restore LCL
        write("@R4");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@R1");
        write("M=D");

        // TODO: goto retAddr

        emptyLine();
    }

    private void writeCall(String arg1, int arg2) {
        includeComment();
        // TODO
        emptyLine();
    }

    private void writeFunction(String functionName, int nArgs) {
        this.callerName = calleeName;
        this.calleeName = functionName;
        includeComment();
        write("(" + calleeName + ")", true);
        for (int i = 0; i < nArgs; ++i) pushConstant(0);
        emptyLine();
    }

    private void writeIfGoto(String label) {
        includeComment();
        getTopValue();
        goToLabel(label);
        write("D;JGT");
        write("D;JLT");
        emptyLine();
    }

    private void writeGoto(String label) {
        includeComment();
        goToLabel(label);
        write("0;JEQ");
        emptyLine();
    }

    private void writeLabel(String label) {
        includeComment();
        write("(" + label + ")", true);
        emptyLine();
    }

    /**
     * Useful when comparing two topmost on the stack
     * Will have address of second most top pointer on stack
     */
    private void getTopValueAndGoToNextTopAddress() {
        getTopValue();
        decrementStackPointer();
        goToAddressAtStackPointer();
    }

    private void getTopValue() {
        decrementStackPointer();
        goToAddressAtStackPointer();
        getValueAtAddress();
    }

    /**
     * Include a line for comment to make debuging easier
     */
    private void includeComment() {
        translatedLines.add("// " + parser.getCurrentCommand());
    }

    /**
     * Add an empty line to asm file
     */
    private void emptyLine() {
        translatedLines.add("");
    }

    /**
     * Write a new assembly code to the file
     *
     * @param line the String to write onto asm file
     */
    private void write(String line) {
        translatedLines.add("   " + line);
        ++currentLine;
    }

    private void write(String line, boolean label) {
        translatedLines.add(line);
        ++currentLine;
    }

    /**
     * Create a jump directive after comparison check
     */
    private void currentContinue() {
        write("@CONTINUE" + continueIndex);
        write("0;JEQ");
    }

    /**
     * Add continue block line depending on which block of code we're working on
     */
    private void addContinue() {
        write("(CONTINUE" + getContinueIndex() + ")", true);
    }

    private int getContinueIndex() {
        return continueIndex++;
    }

    /**
     * Get the constant value
     *
     * @param constant the number to get
     */
    private void getConstant(int constant) {
        write("@" + constant);
        write("D=A");
    }

    /**
     * Set the current M register to current D register
     * Need to make sure D register has the right value
     */
    private void storeValueToAddressPointedAt(String at) {
        write("@" + at);
        write("A=M");
        write("M=D");
    }

    private void storeValueToAddress(String at) {
        write("@" + at);
        write("M=D");
    }

    /**
     * Increment the stack pointer
     */
    private void incrementStackPointer() {
        write("@SP");
        write("M=M+1");
    }

    /**
     * Decrement the stack pointer
     */
    private void decrementStackPointer() {
        write("@SP");
        write("M=M-1");
    }

    private void goToAddressPointedAt(String variable) {
        write("@" + variable);
        write("A=M");
    }

    private void goToLabel(String label) {
        write("@" + label);
    }

    /**
     * Go to the address at the pointer
     * Put address at SP to A register
     */
    private void goToAddressAtStackPointer() {
        goToAddressPointedAt("SP");
    }

    /**
     * Go to the address at pointer and get the value at the pointer
     * Put M-value at A register to D register
     */
    private void getValueAtAddress() {
        write("D=M");
    }

    /**
     * Generate asm file with custom name
     *
     * @param filename the custom file name to give
     */
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

    /**
     * Generate asm file with default name
     */
    public void generateAssemblyCode() {
        generateAssemblyCode(parser.getFileName());
    }

    public static void testTranslator() {
        Translator t = new Translator("MemoryAccess/PointerTest/PointerTest.vm");
        for (String line : t.translatedLines) System.out.println(line);
        System.out.println(t.currentLine);
        t.generateAssemblyCode();
    }

}
