import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Translator {
    private final Parser parser;
    private final List<String> translatedLines;
    private int currentLine;
    private int continueIndex;
    private String currentReturnString;
    private final Stack<String> functionCalls;
    private String currentFileName;

    public Translator(String filename) {
        parser = new Parser(filename);
        translatedLines = new ArrayList<>();
        functionCalls = new Stack<>();
        continueIndex = 0;
        currentLine = 0;
        currentReturnString = "null";
        currentFileName = "";
        writeInit();
        translate();
    }

    public Translator() {
        parser = new Parser();
        translatedLines = new ArrayList<>();
        functionCalls = new Stack<>();
        currentLine = 0;
        currentReturnString = "null";
        currentFileName = "";
        continueIndex = 0;
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
                case "C_ARITHMETIC":
                    writeArithmetic(parser.arg1());
                    break;
                case "C_PUSH":
                    writePushPop("C_PUSH", parser.arg1(), parser.arg2());
                    break;
                case "C_POP":
                    writePushPop("C_POP", parser.arg1(), parser.arg2());
                    break;
                case "C_LABEL":
                    writeLabel(parser.arg1());
                    break;
                case "C_GOTO":
                    writeGoto(parser.arg1());
                    break;
                case "C_IF":
                    writeIfGoto(parser.arg1());
                    break;
                case "C_FUNCTION":
                    writeFunction(parser.arg1(), parser.arg2());
                    break;
                case "C_CALL":
                    writeCall(parser.arg1(), parser.arg2());
                    break;
                case "C_RETURN":
                    writeReturn();
                    break;
                case "CHANGE_FILE":
                    changeFile(parser.arg1());
                    break;
                default:
                    System.exit(2);
                    break;
            }
            parser.advance();
        }
        parser.resetCurrentCommand();
    }

    private void changeFile(String filename) {
        currentFileName = filename;
    }

    private void writeInit() {
        if (parser.getOutputFileName().equals("SimpleFunction")) return;
        write("// initializing pointers", true);
        // TODO: Write init
        write("@256");
        write("D=A");
        write("@SP");
        write("M=D");
        write("");
        if (parser.hasInit()) writeCall("Sys.init", 0);
        write("");
    }

    /**
     * Writes to the output file the assembly code that implements the given arithmetic-logical command
     *
     * @param command the arithmetic command
     */
    private void writeArithmetic(String command) {
        writeComment();
        switch (command) {
            case "add":
                writeAddSub("add");
                break;
            case "sub":
                writeAddSub("sub");
                break;
            case "eq":
                writeComparison("eq");
                break;
            case "lt":
                writeComparison("lt");
                break;
            case "gt":
                writeComparison("gt");
                break;
            case "not":
                writeLogic("!");
                break;
            case "neg":
                writeLogic("-");
                break;
            case "and":
                writeLogic("&");
                break;
            case "or":
                writeLogic("|");
                break;
        }
        write("");
    }

    /**
     * Writes to the output file the assembly code that implements the given push or pop command
     *
     * @param command the push or pop command
     * @param segment the segment of the memory to push or pop to
     * @param index   the index in memory
     */
    private void writePushPop(String command, String segment, int index) {
        writeComment();
        if (command.equals("C_PUSH")) {
            switch (segment) {
                case "constant":
                    pushConstant(index);
                    break;
                case "local":
                    pushToSegmentOf("LCL", index);
                    break;
                case "argument":
                    pushToSegmentOf("ARG", index);
                    break;
                case "this":
                    pushToSegmentOf("THIS", index);
                    break;
                case "that":
                    pushToSegmentOf("THAT", index);
                    break;
                case "temp":
                    pushToSegmentOf("TEMP", index);
                    break;
                case "static":
                    pushToStatic(index);
                    break;
                case "pointer":
                    pushPointer(index);
                    break;
            }
        } else if (command.equals("C_POP")) {
            switch (segment) {
                case "local":
                    popToSegmentOf("LCL", index);
                    break;
                case "argument":
                    popToSegmentOf("ARG", index);
                    break;
                case "this":
                    popToSegmentOf("THIS", index);
                    break;
                case "that":
                    popToSegmentOf("THAT", index);
                    break;
                case "temp":
                    popToSegmentOf("TEMP", index);
                    break;
                case "static":
                    popToStatic(index);
                    break;
                case "pointer":
                    popPointer(index);
                    break;
            }
        }
        write("");
    }

    private void pushConstant(int index) {
        write("@" + index);
        write("D=A");
        write("@" + "SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");
    }

    private void pushToSegmentOf(String segment, int index) {
        write("@" + index);
        write("D=A");
        if (segment.equals("TEMP")) {
            write("@5");
            write("D=A+D");
        } else {
            write("@" + segment);
            write("D=D+M");
        }
        write("@R13");
        write("M=D");
        write("@R13");
        write("A=M");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");
    }

    private void popToSegmentOf(String segment, int index) {
        write("@" + index);
        write("D=A");
        if (segment.equals("TEMP")) {
            write("@5");
            write("D=A+D");
        } else {
            write("@" + segment);
            write("D=D+M");
        }
        write("@R13");
        write("M=D");
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        write("@R13");
        write("A=M");
        write("M=D");
    }

    private String getStaticVariable(int index) {
        return currentFileName + "." + index;
    }

    private void pushToStatic(int index) {
        write("@" + getStaticVariable(index));
        write("D=M");
        write("@" + "SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");
    }

    private void popToStatic(int index) {
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        write("@" + getStaticVariable(index));
        write("M=D");
    }

    private void pushPointer(int index) {
        if (index == 0) write("@3");
        else write("@4");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");
    }

    private void popPointer(int index) {
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        if (index == 0) {
            write("@" + "3");
            write("M=D");
        } else {
            write("@" + "4");
            write("M=D");
        }
    }

    private void writeLogic(String type) {
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        if (type.equals("!") || type.equals("-")) {
            write("M=" + type + "M");
            write("@SP");
            write("M=M+1");
            return;
        }
        write("D=M");
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("M=D" + type + "M");
        write("@SP");
        write("M=M+1");
    }

    private void writeAddSub(String type) {
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        if (type.equals("add")) write("M=D+M");
        else if (type.equals("sub")) write("M=M-D");
        write("@SP");
        write("M=M+1");
    }

    private void writeComparison(String type) {
        // Can probably refactor this, but this works

        String t = type.toUpperCase();
        String ti = t + continueIndex;

        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=D-M");

        write("");
        write("@" + ti);
        write("D;J" + t);

        // Is it equal, 0 if equal
        if (type.equals("lt") || type.equals("gt")) {
            write("");
            write("@JEQ" + continueIndex);
            write("D;JEQ");
        }

        write("");
        write("@N" + ti);
        write("0;JEQ");

        write("");
        write("(" + ti + ")", true);
        write("@" + "SP");
        write("A=M");
        if (type.equals("lt") || type.equals("gt")) write("M=0");
        else write("M=-1");
        write("@CONTINUE" + continueIndex);
        write("0;JEQ");

        // Is it equal, 0 if equal
        if (type.equals("lt") || type.equals("gt")) {
            write("");
            write("(JEQ" + continueIndex + ")", true);
            write("@" + "SP");
            write("A=M");
            write("M=0");
            write("@CONTINUE" + continueIndex);
            write("0;JEQ");
        }

        write("");
        write("(N" + ti + ")", true);
        write("@" + "SP");
        write("A=M");
        if (type.equals("lt") || type.equals("gt")) write("M=-1");
        else write("M=0");
        write("@CONTINUE" + continueIndex);
        write("0;JEQ");

        write("");
        write("(CONTINUE" + getContinueIndex() + ")", true);
        write("@SP");
        write("M=M+1");
    }

    private void writeReturn() {
        writeComment();

        write("// endFrame (R13) = LCL");
        write("@LCL");
        write("D=M");
        write("@R13");
        write("M=D");

        write("// retAddr (R14) = *(endFrame - 5)");
        write("@5");
        write("D=A");
        write("@R13");
        write("A=M-D");
        write("D=M");
        write("@R14");
        write("M=D");

        write("// ARG = pop");
        write("@SP");
        write("M=M-1");
        write("A=M");
        write("D=M");
        write("@ARG");
        write("A=M");
        write("M=D");
        write("@ARG");
        write("D=M+1");
        write("@SP");
        write("M=D");

        write("// restore THAT");
        write("@1");
        write("D=A");
        write("@R13");
        write("A=M-D");
        write("D=M");
        write("@THAT");
        write("M=D");

        write("// restore THIS");
        write("@2");
        write("D=A");
        write("@R13");
        write("A=M-D");
        write("D=M");
        write("@THIS");
        write("M=D");

        write("// restore ARG");
        write("@3");
        write("D=A");
        write("@R13");
        write("A=M-D");
        write("D=M");
        write("@ARG");
        write("M=D");

        write("// restore LCL");
        write("@4");
        write("D=A");
        write("@R13");
        write("A=M-D");
        write("D=M");
        write("@LCL");
        write("M=D");

        write("// goto retAddr");
        write("@R14");
        write("A=M");
        write("0;JMP");

        write("");
    }

    private void writeCall(String functionToCall, int nArg) {
        // TODO: Fix writeCall
        writeComment();

        functionCalls.add(functionToCall);
        currentReturnString = functionCalls.peek() + "$ret." + getContinueIndex();

        write("// push returnAddress");
        write("@" + getCurrentReturnString());
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        write("// push LCL");
        write("@LCL");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        write("// push ARG");
        write("@ARG");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        write("// push THIS");
        write("@THIS");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        write("// push THAT");
        write("@THAT");
        write("D=M");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        write("// reposition ARG");
        write("@SP");
        write("D=M");
        write("@" + nArg);
        write("D=D-A");
        write("@5");
        write("D=D-A");
        write("@ARG");
        write("M=D");

        write("// reposition LCL");
        write("@SP");
        write("D=M");
        write("@LCL");
        write("M=D");

        write("// goto functionName");
        write("@" + functionToCall);
        write("0;JMP");

        write("// label for return address");
        write("(" + getCurrentReturnString() + ")", true);

        write("");
    }

    private void writeFunction(String functionName, int nArgs) {
        // This is cheating a bit, but it works
        writeComment();

        write("(" + functionName + ")", true);
        for (int i = 0; i < nArgs; ++i) pushConstant(0);

        write("");
    }

    private void writeIfGoto(String label) {
        writeComment();
        write("@SP");
        write("M=M-1");
        write("@SP");
        write("A=M");
        write("D=M");
        write("@" + label);
        write("D;JGT");
        write("D;JLT");
        write("");
    }

    private void writeGoto(String label) {
        writeComment();
        write("@" + label);
        write("0;JMP");
        write("");
    }

    private void writeLabel(String label) {
        writeComment();
        write("(" + label + ")", true);
        write("");
    }

    private void writeComment() {
        translatedLines.add("// " + parser.getCurrentCommand());
    }

    private void write(String line) {
        translatedLines.add("   " + line);
        if (line.equals("") || line.contains("//")) return;
        // System.out.println(currentLine + " " + line);
        ++currentLine;
    }

    private void write(String line, boolean label) {
        translatedLines.add(line);
    }

    private String getCurrentReturnString() {
        return currentReturnString;
    }

    private int getContinueIndex() {
        return continueIndex++;
    }

    public void generateAssemblyCode(String filename) {
        try {
            FileWriter fw = new FileWriter(parser.getCurrentDirectory() + "/" + filename + ".asm");
            for (String str : translatedLines) fw.write(str + System.lineSeparator());
            fw.close();
            System.out.println("Converted to .asm file: " + parser.getCurrentDirectory() + "/" + filename + ".asm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateAssemblyCode() {
        generateAssemblyCode(parser.getOutputFileName());
    }
}
