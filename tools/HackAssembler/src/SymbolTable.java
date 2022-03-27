import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
    private HashMap<String, Integer> symbolTable;
    private HashSet<Integer> usedAddresses;
    private int currentIndex;

    SymbolTable() {
        this.symbolTable = new HashMap<>();

        this.symbolTable.put("SP", 0);
        this.symbolTable.put("LCL", 1);
        this.symbolTable.put("ARG", 2);
        this.symbolTable.put("THIS", 3);
        this.symbolTable.put("THAT", 4);
        this.symbolTable.put("R0", 0);
        this.symbolTable.put("R1", 1);
        this.symbolTable.put("R2", 2);
        this.symbolTable.put("R3", 3);
        this.symbolTable.put("R4", 4);
        this.symbolTable.put("R5", 5);
        this.symbolTable.put("R6", 6);
        this.symbolTable.put("R7", 7);
        this.symbolTable.put("R8", 8);
        this.symbolTable.put("R9", 9);
        this.symbolTable.put("R10", 10);
        this.symbolTable.put("R11", 11);
        this.symbolTable.put("R12", 12);
        this.symbolTable.put("R13", 13);
        this.symbolTable.put("R14", 14);
        this.symbolTable.put("R15", 15);
        this.symbolTable.put("SCREEN", 16384);
        this.symbolTable.put("KBD", 24576);

        this.usedAddresses = new HashSet<>();

        for (int i = 0; i < 16; ++i) {
            usedAddresses.add(i);
        }

        usedAddresses.add(16384);
        usedAddresses.add(24576);

        this.currentIndex = 16;
    }

    public HashMap<String, Integer> getSymbolTable() {
        return this.symbolTable;
    }

    public String getAddress(String symbol) {
        String addressInBinary = Integer.toBinaryString(symbolTable.get(symbol));
        StringBuilder returnString = new StringBuilder();
        if (addressInBinary.length() < 15) returnString.append("0".repeat(15 - addressInBinary.length()));
        return returnString.append(addressInBinary).toString();
    }

    private boolean isInt(String str) {
        try {
            int x = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addEntry(String symbol) {
        if (isInt(symbol)) {
            int address = Integer.parseInt(symbol);
            symbolTable.put(symbol, address);
            usedAddresses.add(address);
            return;
        }

        while (usedAddresses.contains(currentIndex)) ++currentIndex;
        symbolTable.put(symbol, currentIndex);
        usedAddresses.add(currentIndex);
    }

    public void addEntry(String symbol, int address) {
        this.symbolTable.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }
}
