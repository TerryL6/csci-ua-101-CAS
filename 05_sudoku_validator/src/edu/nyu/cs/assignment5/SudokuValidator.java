package edu.nyu.cs.assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A program to play Sudoku and validate its correctness.
 * Complete the functions to perform the tasks indicated in the comments.
 * NOTE: Some of the functions should make use of the other functions, so read the provided notes!
 */
public class SudokuValidator {
    // Use this "global"-ish Scanner variable when getting keyboard input from the
    // user within any function; this avoids common problems using several different
    // Scanners within different functions.
    public static Scanner scn = new Scanner(System.in);

    /**
     * The main method is automatically called first in a Java program.
     * This method contains the main logic of the program that makes use of all
     * the other methods to solve the problem.
     * 
     * This main method makes use of the other methods to perform the tasks.
     * Those methods are:
     * - wonPuzzle() returns true if the puzzle is complete and valid
     * - getFilePathFromUser() asks the user for a CSV file
     * - printRemainingMoves() prints all empty fields in the puzzle
     * - remainingMoves() computes all empty fields in the puzzle
     * - getContentsOfFile() reads a CSV file and writes its content into a
     * two-dimensional array
     * - printPuzzle() prints the Sudoku puzzle to the command line
     * - printRemainingMoves() prints all remaining empty fields
     * - makeMove() adds a number to the puzzle if the number and position are valid
     * - validatePuzzle() checks if every number is at most once contained in every
     * row, column, and block
     *
     * @param args an array of any command-line arguments
     */
    public static void main(String[] args) throws Exception {
        // Get the filepath from user
        String filepath = getFilepathFromUser();

        // Get the puzzle contents from the file
        int[][] puzzle = getContentsOfFile(filepath);

        // System.out.println(puzzle[0][0]);
        // System.out.println(puzzle[5][6]);

        // Print the initial puzzle
        printPuzzle(puzzle);

        // Validate the puzzle
        if (validatePuzzle(puzzle)) {
            System.out.println("Puzzle is valid.");
        } else {
            System.out.println("Puzzle is invalid, exiting.");
            return;
        }

        // Continue until the puzzle is won or the user quits
        while (!wonPuzzle(puzzle)) {
            // Print the remaining moves
            printRemainingMoves(puzzle);

            // Break the loop if no remaining moves
            if (remainingMoves(puzzle).size() == 0) {
                break;
            }

            // Prompt the user for next move
            System.out.println("What is your next move?");
            String line = scn.nextLine();
            
            // Break the loop if the user quits
            if (line.equals("quit")) {
                break;
            } else {
                // Process the user's move
                String[] tokens = line.split(" ");
                try {
                    int row = Integer.parseInt(tokens[0]);
                    int col = Integer.parseInt(tokens[1]);
                    int value = Integer.parseInt(tokens[2]);

                    // Make the move and handle invalid moves
                    if (!makeMove(puzzle, row, col, value)) {
                        System.out.println("Try again!");
                    }
                } catch (Exception e) {
                    System.out.println("Did not understand command");
                }
            }

            // Print the updated puzzle
            printPuzzle(puzzle);
        }

        // Check if the puzzle is won or not
        if (wonPuzzle(puzzle)) {
            System.out.println("Congratulations!");
        } else {
            System.out.println("Condolences!");
        }
    }

    /** wonPuzzle
     * Checks if the Sudoku puzzle is solved successfully, which means there are no
     * remaining moves and the puzzle is valid. If these conditions are met, it
     * indicates that the user has won.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `remainingMoves()` AND `validatePuzzle()`!
     *
     * @param puzzle the Sudoku puzzle
     * @return true if the puzzle is successfully completed and valid, false otherwise
     */
    public static boolean wonPuzzle(int[][] puzzle) {
        return validatePuzzle(puzzle) && remainingMoves(puzzle).size() == 0;
    }

    /** getFilepathFromUser
     * Prompts the user to enter the path to the text file they want to analyze.
     * 
     * HINT:
     * - Use the "global"-ish Scanner variable `scn` to get the user's response,
     * rather than creating a new Scanner variable within this function.
     * - Do not close the "global"-ish Scanner so that you can use it in other
     * functions.
     *
     * @return the file path entered by the user, e.g. "sudoku_puzzle.csv"
     */
    public static String getFilepathFromUser() {
        System.out.println("What file would you like to open?");
        String filepath = scn.nextLine();
        return filepath;
    }

    /** printRemainingMoves
     * Prints out a list of the remaining moves.
     * If no moves are left, print "No moves left!", otherwise print 
     * "Remaining moves:" followed by all possible moves.
     *
     * Possible output:
     * ```
     * Remaining moves:
     * (0,0) (4,2) (5,6)
     * ```
     * or
     * ```
     * No moves left!
     * ```
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `remainingMoves()`!
     *
     * @param puzzle the Sudoku puzzle
     */
    public static void printRemainingMoves(int[][] puzzle) {
        ArrayList<int[]> coordinates = remainingMoves(puzzle);
        if (coordinates.size() > 0){
            System.out.println("Remaining moves:");
            for (int i = 0; i < coordinates.size(); i++){
                System.out.print("(" + coordinates.get(i)[0] + "," + coordinates.get(i)[1] + ") ");
            }
            System.out.println();
        } else{
            System.out.println("No moves left!");
        }
    }

    /**
     * Finds all of the remaining moves as an ArrayList of arrays, where each array
     * is of size 2. Any position in the Sudoku board where there is a 0 is a valid
     * move. Note that the moves should be sorted by smallest row and then smallest
     * column, i.e., lexicographical order.
     *
     * @param puzzle the Sudoku puzzle
     * @return ArrayList of all remaining moves
     */
    public static ArrayList<int[]> remainingMoves(int[][] puzzle) {
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == 0) {
                    int[] coord = { i, j };
                    coordinates.add(coord);
                }
            }
        }
        return coordinates;
    }

    /** getContentsOfFile
     * Opens the specified file and returns the text stored within. If the file
     * cannot
     * be opened, print out the message, "Oh no... can't find the file!"
     *
     * @param filename the path to a CSV file containing a Sudoku puzzle
     * @return a Sudoku puzzle represented by an int[][] array
     */
    public static int[][] getContentsOfFile(String filepath) {
        int[][] fullpuzzle = new int[9][9];
        try {
            Scanner fileScanner = new Scanner(new File(filepath));

            // Define a row number 
            int current_row = 0;
            while (fileScanner.hasNextLine()) {
                // The first row in string format
                String line = fileScanner.nextLine();

                // Now split the string into a string array
                String[] nums_in_string_format = line.split(",");

                // Now loop through the array and put this row into puzzle
                for(int col = 0; col < 9; col++){
                    int num = Integer.parseInt(nums_in_string_format[col]);
                    fullpuzzle[current_row][col] = num;
                }
                // Go to the next row
                current_row++;
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            // If the file cannot be opened, output a friendly message.
            System.out.println("Oh no... can't find the file!");
        }
        return fullpuzzle;
    }

    /** printPuzzle
     * Prints out the Sudoku puzzle to the command line with a line of "===" above
     * and vertical "|" between numbers
     * Refer to the example outputs for the expected format.
     *
     * - Use the given char[][] `board` and replace the X's by the puzzle numbers.
     * - If the number is 0, print an empty space (' ') instead of the number.
     * - Do not forget to convert the number into the correct ASCII character!
     * - The output must match exactly the format from the example outputs.
     *
     * @param puzzle the Sudoku puzzle
     */
    public static void printPuzzle(int[][] puzzle) {
        char[][] board = {
            "╔═══════════════════════════════════╗".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║═══════════╬═══════════╬═══════════║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║═══════════╬═══════════╬═══════════║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "║───┼───┼───║───┼───┼───║───┼───┼───║".toCharArray(),
            "║ X │ X │ X ║ X │ X │ X ║ X │ X │ X ║".toCharArray(),
            "╚═══════════════════════════════════╝".toCharArray()};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[2 * i + 1][4 * j + 2] = (char) (puzzle[i][j] + 48);
                // 0 should be " "
                if(puzzle[i][j] == 0){
                    board[2 * i + 1][4 * j + 2] = ' ';
                }
            }
        }

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }

    }

    /** makeMove
     * Tries to insert the given value into the puzzle. Validates that the given
     * position (row,col) is within the bounds of the puzzle, the position is not already
     * occupied, and the value is within the expected range.
     *
     * Also, use `validatePuzzle()` to ensure that this move does not break the
     * game. If it does, set the value in this position back to 0.
     *
     * @param puzzle the Sudoku puzzle
     * @param row    the row-index where the value should be placed
     * @param col    the column-index where the value should be placed
     * @param value  the value that should be placed at the given (row,col)
     * @return true if the move was successful, false otherwise
     */
    public static boolean makeMove(int[][] puzzle, int row, int col, int value) {
        // Condition to make the move
        if (row >= 0 && row < 9 && col >= 0 && col < 9 && value > 0 && value <=9 && puzzle[row][col] == 0 ){
            puzzle[row][col] = value;
        }
        // Move failed due to invalid input
        else{
            return false;
        }
        if (validatePuzzle(puzzle) == false){
            puzzle[row][col] = 0;
        }
        return validatePuzzle(puzzle);
    }

    /** validateCountData
     * Checks that none of the entries in `counts` is larger than one.
     * The entry at index 0 is ignored. The input array must have a length of 10.
     *
     * @param counts array of length 10
     * @return true if none of the entries is larger than 1 (ignoring index 0), false otherwise
     */
    public static boolean validateCountData(int[] counts) {
        assert (counts.length == 10);
        for(int i = 1; i < 10; i++){
            if(counts[i] > 1){
                return false;
            }
        }
        return true;
    }

    /** validateRow
     * Validates the given row by ensuring that no number other than 0 appears
     * more than once.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateCountData()`!
     *
     * @param puzzle the Sudoku puzzle
     * @param row    the row that should be validated
     * @return true if no number besides 0 appears more than once in the row, false otherwise
     */
    public static boolean validateRow(int[][] puzzle, int row) {
        int[] counts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < 9; i++) {
            //counts[1] means the number of occurances of number 1 in the puzzle
            counts[puzzle[row][i]] ++;
        }
        return validateCountData(counts);
    }

    /** validateColumn
     * Validates the given column by ensuring that no number other than 0 appears
     * more than once.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateCountData()`!
     *
     * @param puzzle the Sudoku puzzle
     * @param col    the column that should be validated
     * @return true if no number besides 0 appears more than once in the column, false otherwise
     */
    public static boolean validateColumn(int[][] puzzle, int col) {
        int[] counts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < 9; i++) {
            counts[puzzle[i][col]] ++;
        }
        return validateCountData(counts); // Dummy return statement. Replace this with the actual implementation.
    }

    /** validateBlock
     * Validates the given block by ensuring that no number other than 0 appears
     * more than once. 
     * 
     * Make sure each 3x3 block has at most one of any number other than 0.
     *
     * The `blockRow` and `blockCol` must be indices in the range [0,3), where
     * blockRow==0 corresponds to the top block row,
     * blockCol==0 corresponds to the left block column,
     * blockRow==2 corresponds to the bottom block row,
     * blockCol==2 corresponds to the right block column.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateCountData()`!
     *
     * @param puzzle   the Sudoku puzzle
     * @param blockRow the block row
     * @param blockCol the block column
     * @return true if no number besides 0 appears more than once in the block, otherwise false
     */
    public static boolean validateBlock(int[][] puzzle, int blockRow, int blockCol) {
        int[] counts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = blockRow * 3; i < blockRow * 3 + 3; i++) {
            for (int j = blockCol * 3; j < blockCol * 3 + 3; j++) {
                counts[puzzle[i][j]] ++;
            }
        }
        return validateCountData(counts);
    }

    /**
     * Validates all rows in the Sudoku puzzle.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateRow()`!
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all rows are valid, false otherwise
     */
    public static boolean validateRows(int[][] puzzle) {
        for(int i = 0; i < 9; i++){
            if(validateRow(puzzle, i) == false){
                return false;
            }
        }
        return true;
    }

    /** validateColumns
     * Validates all columns in the Sudoku puzzle.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateColumn()`!
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all columns are valid, false otherwise
     */
    public static boolean validateColumns(int[][] puzzle) {
        for(int i = 0; i < 9; i++){
            if(validateColumn(puzzle, i) == false){
                return false;
            }
        }
        return true;
    }

    /** validateBlocks
     * Validates all 3x3 blocks in the Sudoku puzzle.
     *
     * NOTE: THIS METHOD SHOULD MAKE USE OF `validateBlock()`!
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all blocks are valid, false otherwise
     */
    public static boolean validateBlocks(int[][] puzzle) {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(validateBlock(puzzle, i, j) == false){
                    return false;
                }
            }
        }
        return true;
    }

    /** validatePuzzle
     * Validates the whole Sudoku puzzle by validating all rows, columns and blocks.
     * A puzzle is considered valid if none of the non-zero entries appears twice
     * in its column, row, and block.
     *
     * @param puzzle the Sudoku puzzle
     * @return true if all rows, columns, and blocks are valid, false otherwise
     */
    public static boolean validatePuzzle(int[][] puzzle) {
        return validateRows(puzzle) && validateColumns(puzzle) && validateBlocks(puzzle);
    }

} // end of class
