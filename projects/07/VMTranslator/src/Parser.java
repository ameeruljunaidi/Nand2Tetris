import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    private final File selectedSource;
    private final List<File> selectedSources;
    private List<String> allLines;
    private int currentLineNumber;
    private final boolean isDirectory;
    private final boolean hasInit;

    public Parser(String source) {
        currentLineNumber = 0;
        selectedSource = new File(source);
        selectedSources = new ArrayList<>();
        isDirectory = selectedSource.isDirectory();
        if (isDirectory) getFilesInDirectory(selectedSource);
        else selectedSources.add(selectedSource);
        allLines = new ArrayList<>();
        checkFileType();
        readFiles();
        hasInit = findInit();
    }

    public Parser() {
        currentLineNumber = 0;
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        selectedSource = jfc.getSelectedFile();
        selectedSources = new ArrayList<>();
        isDirectory = selectedSource.isDirectory();
        if (isDirectory) getFilesInDirectory(selectedSource);
        else selectedSources.add(selectedSource);
        checkFilesChooser(returnValue);
        readFiles();
        hasInit = findInit();
    }

    private boolean findInit() {
        for (File file : selectedSources) {
            if (file.getName().equals("Sys.vm")) return true;
        }
        return false;
    }

    public boolean hasInit() {
        return hasInit;
    }

    private void checkFilesChooser(int returnValue) {
        for (File file : selectedSources) {
            if (returnValue != JFileChooser.APPROVE_OPTION || !getFileExtension(file).equals("vm")) {
                System.out.println("Error: Not a valid vm file or file does not exist.");
                System.exit(1);
            }
        }
    }

    private void checkFileType() {
        for (File file : selectedSources) {
            if (!file.exists() || !getFileExtension(file).equals("vm")) {
                System.out.println("Error: Not a valid vm file or file does not exist.");
                System.exit(1);
            }
        }
    }

    private void getFilesInDirectory(File source) {
        File[] files = source.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isFile() && getFileExtension(file).equals("vm")) {
                selectedSources.add(file);
            }
        }
    }

    private void readFiles() {
        for (File file : selectedSources) {
            readFile(file);
        }
    }

    private void readFile(File source) {
        try {
            allLines.add("CHANGE_FILE " + FilenameUtils.removeExtension(source.getName()));
            allLines.addAll(removeComments(FileUtils.readLines(source, "UTF-8")));
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
            case "label":
                return "C_LABEL";
            case "goto":
                return "C_GOTO";
            case "if-goto":
                return "C_IF";
            case "function":
                return "C_FUNCTION";
            case "call":
                return "C_CALL";
            case "return":
                return "C_RETURN";
            case "CHANGE_FILE":
                return "CHANGE_FILE";
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
        return Integer.parseInt(getCurrentCommand().split(" ")[2].trim());
    }


    /**
     * Set the current command to a certain line number
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
    private String getFileExtension(File sourceFile) {
        return FilenameUtils.getExtension(sourceFile.getAbsolutePath());
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
            if (!currentLine.isEmpty()) commentsRemoved.add(currentLine);
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
        if (!isDirectory) return selectedSource.getParent();
        else return selectedSource.getPath();
    }

    /**
     * Get the file name for the output
     *
     * @return the file name
     */
    public String getOutputFileName() {
        if (!isDirectory) return FilenameUtils.removeExtension(selectedSource.getName());
        else return selectedSource.getName();
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
