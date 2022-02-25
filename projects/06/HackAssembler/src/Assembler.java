import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Assembler {
    private Parser parser;
    private SymbolTable symbolTable;
    private List<String> translatedLines;

    public Assembler(String filename) {
        this.parser = new Parser(filename);
        this.symbolTable = new SymbolTable();
        this.translatedLines = new ArrayList<>();
        firstPass();
        secondPass();
    }

    public Assembler() {
        this.parser = new Parser();
        this.symbolTable = new SymbolTable();
        this.translatedLines = new ArrayList<>();
        firstPass();
        secondPass();
    }

    /**
     * Get the current parser object
     *
     * @return the parser
     */
    public Parser getParser() {
        return parser;
    }

    /**
     * Get the current symbol table object
     *
     * @return the symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * The first pass to go through to get all the L commands symbol and put it in the symbol table
     */
    private void firstPass() {
        int lineNumber = 0;
        while (parser.hasMoreCommands()) {
            if (!symbolTable.contains(parser.getSymbol()) && parser.commandType().equals("L_COMMAND")) {
                symbolTable.addEntry(parser.getSymbol(), lineNumber);
            }

            if (!parser.commandType().equals("L_COMMAND")) ++lineNumber;
            parser.advance();
        }

        parser.resetCurrentCommand();
    }

    /**
     * The second pass is when we actually generate the binary code
     */
    private void secondPass() {
        while (parser.hasMoreCommands()) {
            if (parser.commandType().equals("A_COMMAND")) {
                processACommand();
            } else if (parser.commandType().equals("C_COMMAND")) {
                processCCommand();
            }
            parser.advance();
        }

        parser.resetCurrentCommand();
    }

    /**
     * Process the A command
     */
    private void processACommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("0");

        if (!symbolTable.contains(parser.getSymbol())) {
            symbolTable.addEntry(parser.getSymbol());
        }

        sb.append(symbolTable.getAddress(parser.getSymbol()));
        translatedLines.add(sb.toString());
    }

    /**
     * Process the C command
     */
    private void processCCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("111");

        String compCodeBin = Code.getCompCode(parser.getComp());
        String destCodeBin = Code.getDestinationCode(parser.getDestination());
        String jumpCodeBin = Code.getJumpCode(parser.getJump());

        sb.append(compCodeBin).append(destCodeBin).append(jumpCodeBin);

        translatedLines.add(sb.toString());
    }


    public void generateMachineCode(String filename) {
        try {
            FileWriter fw = new FileWriter(parser.getCurrentDirectory() + "/" + filename + ".hack");
            for (String str : translatedLines) fw.write(str + System.lineSeparator());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateMachineCode() {
        generateMachineCode(parser.getFileName());
    }

    /**
     * Print the translated code
     */
    public void printTranslated() {
        for (String line : translatedLines) System.out.println(line);
    }

    /**
     * Test symbol table
     */
    public void testSymbolTable() {
        for (String key : symbolTable.getSymbolTable().keySet()) {
            System.out.println("Symbol: " + key + ", Address: " + symbolTable.getSymbolTable().get(key));
        }
    }
}
