import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Parser {
    private final File selectedFile;
    private final String parentDirectory;
    private final String fileExtension;
    private List<String> allLines;
    private int currentLineNumber;

    public Parser(String filename) {
        currentLineNumber = 0;
        parentDirectory = getParentDirectory();
        selectedFile = new File(parentDirectory + "/" + filename);
        fileExtension = getFileExtension();

        if (!selectedFile.exists() || !selectedFile.exists() || !fileExtension.equals("vm")) {
            System.out.println("Error: Not a valid vm file or file does not exist.");
            System.exit(1);
        }

        try {
            allLines = removeComments(FileUtils.readLines(selectedFile, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parser() {
        currentLineNumber = 0;
        parentDirectory = getParentDirectory();

        JFileChooser jfc = new JFileChooser(parentDirectory);
        int returnValue = jfc.showOpenDialog(null);

        selectedFile = jfc.getSelectedFile();
        fileExtension = getFileExtension();

        if (returnValue != JFileChooser.APPROVE_OPTION || !fileExtension.equals("vm")) {
            System.out.println("Error: Not a valid vm file or file does not exist.");
            System.exit(1);
        }

        try {
            allLines = removeComments(FileUtils.readLines(selectedFile, "UTF-8"));
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
        return currentLineNumber < allLines.size();
    }

    /**
     * Get the current line of code to process
     *
     * @return the line of code at index current line number
     */
    public String getCurrentCommand() {
        return allLines.get(currentLineNumber);
    }

    /**
     * Get the command type of the current command
     * C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL
     *
     * @return the type of command of the current line
     */
    public String commandType() {
        String firstChar = getCurrentCommand().split(" ")[0];

        switch (firstChar) {
            case "push":
                return "C_PUSH";
            case "pop":
                return "C_POP";
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                return "C_ARITHMETIC";
            default:
                return "C_TYPE_ERROR";
        }
    }

    /**
     * Get the first argument of the command
     *
     * @return the second word in the command
     */
    public String arg1() {
        if (commandType().equals("C_RETURN")) return "ARG1_ERROR";
        if (commandType().equals("C_ARITHMETIC")) return getCurrentCommand().split(" ")[0];

        return getCurrentCommand().split(" ")[1];
    }

    /**
     * Get the second argument of the command
     *
     * @return the third word in the command
     */
    public int arg2() {
        Set<String> accepted = new HashSet<>(Arrays.asList("C_PUSH", "C_POP", "C_FUNCTION", "C_CALL"));
        if (!accepted.contains(commandType())) return 0;

        return Integer.parseInt(getCurrentCommand().split(" ")[2]);
    }


    /**
     * Set teh current command to a certain line number
     *
     * @param lineNumber assign current line number to value passed
     */
    public void setCurrentCommand(int lineNumber) {
        currentLineNumber = lineNumber;
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
        ++currentLineNumber;
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
        return FilenameUtils.getExtension(selectedFile.getAbsolutePath());
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

                sb.append(character);
            }

            String currentLine = sb.toString();
            if (!currentLine.isBlank()) commentsRemoved.add(currentLine);
        }

        return commentsRemoved;
    }

    /**
     * Print all the line of codes
     */
    public void printLines() {
        for (int i = 0; i < allLines.size(); ++i) System.out.println(i + " " + allLines.get(i));
    }

    public void printCommand() {
        String commandType = commandType();
        String arg1 = arg1();
        int arg2 = arg2();

        if (!commandType.equals("C_TYPE_ERROR")) System.out.print(commandType);
        if (!arg1.equals("ARG1_ERROR")) System.out.print(" " + arg1);
        if (!commandType().equals("C_ARITHMETIC")) System.out.print(" " + arg2);
        System.out.println();
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

    /**
     * Test the parser
     */
    public static void testParser() {
        Parser p = new Parser("StackArithmetic/StackTest/StackTest.vm");
        while (p.hasMoreCommands()) {
            p.printCommand();
            p.advance();
        }
    }

}
