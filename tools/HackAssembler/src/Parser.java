import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final File selectedFile;
    private final String parentDirectory;
    private final String fileExtension;
    private List<String> allLines;
    private int currentLineNumber;

    public Parser(String filename) {
        this.currentLineNumber = 0;
        this.parentDirectory = getParentDirectory();
        this.selectedFile = new File(filename);
        this.fileExtension = getFileExtension();

        if (!this.selectedFile.exists() || !this.selectedFile.exists() || !this.fileExtension.equals("asm")) {
            System.out.println("Error: Not a valid asm file or file does not exist.");
            System.exit(1);
        }

        try {
            this.allLines = removeComments(FileUtils.readLines(selectedFile, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parser() {
        this.currentLineNumber = 0;
        this.parentDirectory = getParentDirectory();

        JFileChooser jfc = new JFileChooser(this.parentDirectory);
        int returnValue = jfc.showOpenDialog(null);

        this.selectedFile = jfc.getSelectedFile();
        this.fileExtension = getFileExtension();

        if (returnValue != JFileChooser.APPROVE_OPTION || !this.fileExtension.equals("asm")) {
            System.out.println("Error: Not a valid asm file or file does not exist.");
            System.exit(1);
        }

        try {
            this.allLines = removeComments(FileUtils.readLines(selectedFile, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if there is more commands to process
     *
     * @return true if there are more lines of codes
     */
    public boolean hasMoreCommands() {
        return this.currentLineNumber < this.allLines.size();
    }

    /**
     * Get the current line of code to process
     *
     * @return the line of code at index current line number
     */
    public String getCurrentCommand() {
        return this.allLines.get(this.currentLineNumber);
    }

    /**
     * Set teh current command to a certain line number
     *
     * @param lineNumber assign current line number to value passed
     */
    public void setCurrentCommand(int lineNumber) {
        this.currentLineNumber = lineNumber;
    }

    /**
     * Reset the current commands
     */
    public void resetCurrentCommand() {
        setCurrentCommand(0);
    }

    /**
     * Go to next command
     */
    public void advance() {
        ++this.currentLineNumber;
    }

    /**
     * Get the command type of the current command
     *
     * @return the type of command of the current line
     */
    public String commandType() {
        char firstChar = getCurrentCommand().toCharArray()[0];

        if (firstChar == '@') {
            return "A_COMMAND";
        } else if (firstChar == '(') {
            for (char character : getCurrentCommand().toCharArray()) if (character == ')') return "L_COMMAND";
        } else {
            if (getCurrentCommand().contains("=") || getCurrentCommand().contains(";")) return "C_COMMAND";
        }
        return "COMMAND_ERROR";
    }

    /**
     * Get the symbol if it is an A command
     *
     * @return the symbol
     */
    public String getSymbol() {
        if (commandType().equals("A_COMMAND")) {
            return getCurrentCommand().substring(1);
        } else if (commandType().equals("L_COMMAND")) {
            return getCurrentCommand().substring(1, getCurrentCommand().indexOf(')'));
        }

        return "NO SYMBOL";
    }

    /**
     * Get destination if it is a C command
     *
     * @return the destination
     */
    public String getDestination() {
        if (commandType().equals("C_COMMAND") && getCurrentCommand().contains("=")) {
            return getCurrentCommand().substring(0, getCurrentCommand().indexOf("="));
        }
        return "null";
    }

    /**
     * Get computation if it is a C command
     *
     * @return get computation
     */
    public String getComp() {
        if (commandType().equals("C_COMMAND") && getCurrentCommand().contains(";")) {
            return getCurrentCommand().substring(0, getCurrentCommand().indexOf(";"));
        } else if (commandType().equals("C_COMMAND") && getCurrentCommand().contains("=")) {
            return getCurrentCommand().substring(getCurrentCommand().indexOf("=") + 1);
        }

        return "NO COMP";
    }

    /**
     * Get the jump code if it is a C command
     *
     * @return the jump code
     */
    public String getJump() {
        if (commandType().equals("C_COMMAND") && getCurrentCommand().contains(";")) {
            return getCurrentCommand().substring(getCurrentCommand().indexOf(";") + 1);
        }

        return "null";
    }

    /**
     * Helper function to get the parent directory of the current directory
     * This is just to determine where the popup box is going to show up in the directory
     *
     * @return the parent directory of the current directory
     */
    private String getParentDirectory() {
        String currentDirectory = System.getProperty("user.dir");
        File currentFile = new File(currentDirectory);
        return currentFile.getParent();
    }

    /**
     * Get the current file extension
     *
     * @return the current file extension
     */
    private String getFileExtension() {
        return FilenameUtils.getExtension(this.selectedFile.getAbsolutePath());
    }

    /**
     * Remove comments from line
     *
     * @param codes the list of strings to parse through
     * @return the list of strings with the comments removes
     */
    private List<String> removeComments(List<String> codes) {
        List<String> commentsRemoved = new ArrayList<>();

        for (String line : codes) {
            StringBuilder sb = new StringBuilder();
            int commentCharCount = 0;

            for (char character : line.toCharArray()) {

                if (character == '/') {
                    ++commentCharCount;
                    if (commentCharCount == 2) break;
                    continue;
                }

                if (!Character.isWhitespace(character)) {
                    sb.append(character);
                }
            }

            String currentLine = sb.toString();
            if (!currentLine.isBlank()) commentsRemoved.add(currentLine);
        }

        return commentsRemoved;
    }

    /**
     * Test the parser
     */
    public void testParser() {
        List<String> tester = new ArrayList<>();

        while (hasMoreCommands()) {
            StringBuilder currentLine = new StringBuilder();
            if (commandType().equals("A_COMMAND")) {
                currentLine.append("@").append(getSymbol());
            } else if (commandType().equals("C_COMMAND") && getCurrentCommand().contains(";")) {
                currentLine.append(getDestination()).append(";").append(getJump());
            } else if (commandType().equals("C_COMMAND") && getCurrentCommand().contains("=")) {
                currentLine.append(getDestination()).append("=").append(getComp());
            } else if (commandType().equals("L_COMMAND")) {
                currentLine.append("(").append(getSymbol()).append(")");
            }

            tester.add(currentLine.toString());
            advance();
        }

        resetCurrentCommand();

        for (int i = 0; i < this.allLines.size(); ++i) {
            // System.out.println(i + " " + this.allLines.get(i));
            // System.out.println(i + " " + tester.get(i));

            if (!this.allLines.get(i).equals(tester.get(i))) {
                System.out.println("Test Parser Failed At Line: " + i);
                return;
            }
        }

        System.out.println("Test Parser Passed");
    }

    /**
     * Print all the line of codes
     */
    public void printLines() {
        for (int i = 0; i < allLines.size(); ++i) System.out.println(i + " " + allLines.get(i));
    }

    /**
     * Use this for output later
     *
     * @return the current path without the filename
     */
    public String getCurrentDirectory() {
        return selectedFile.getParent();
    }

    /**
     * Get the file name for the output
     *
     * @return the file name
     */
    public String getFileName() {
        return FilenameUtils.removeExtension(selectedFile.getName());
    }
}
