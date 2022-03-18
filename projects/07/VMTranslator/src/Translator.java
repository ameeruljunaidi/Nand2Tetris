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
        // TODO: Write init
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
            write("@TEMP");
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
        return parser.getFileName() + "." + index;
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
        write("@" + "SP");
        write("A=M");
        write("D=M");
        write("@SP");
        write("M=M-1");
        write("@" + "SP");
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

        // endFrame (R13) = LCL
        write("@LCL");
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
        write("@ARG");
        write("A=M");
        write("M=D");
        write("@ARG");
        write("D=M");
        write("@SP");
        write("M=D+1");

        // restore THAT
        write("@1");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@THAT");
        write("M=D");

        // restore THIS
        write("@2");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@THIS");
        write("M=D");

        // restore ARG
        write("@3");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@ARG");
        write("M=D");

        // restore LCL
        write("@4");
        write("D=A");
        write("@R13");
        write("D=M-D");
        write("A=D");
        write("D=M");
        write("@LCL");
        write("M=D");

        // TODO: goto retAddr

        write("");
    }

    private void writeCall(String arg1, int arg2) {
        int currentContinueIndex = getContinueIndex();
        String returnString = calleeName + "$ret." + currentContinueIndex;

        writeComment();

        // push returnAddress
        write("@" + returnString);
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        // push LCL
        write("@LCL");
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        // push ARG
        write("@ARG");
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        // push THIS
        write("@THIS");
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        // push THAT
        write("@THAT");
        write("D=A");
        write("@SP");
        write("A=M");
        write("M=D");
        write("@SP");
        write("M=M+1");

        // reposition ARG
        write("@SP");
        write("D=A");
        write("@5");
        write("D=D-A");
        write("@" + arg2);
        write("D=D-A");
        write("@ARG");
        write("M=D");

        // reposition LCL
        write("@SP");
        write("D=M");
        write("@LCL");
        write("M=D");

        // goto functionName
        write("@" + calleeName);
        write("0;JMP");

        // label for return address
        write("(" + returnString + ")");

        write("");
    }

    private void writeFunction(String functionName, int nArgs) {
        writeComment();
        write("(" + calleeName + ")", true);
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
        write("0;JEQ");
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
        ++currentLine;
    }

    private void write(String line, boolean label) {
        translatedLines.add(line);
        ++currentLine;
    }

    private int getContinueIndex() {
        return continueIndex++;
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
