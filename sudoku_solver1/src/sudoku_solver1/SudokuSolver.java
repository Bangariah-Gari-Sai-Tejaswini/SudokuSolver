package sudoku_solver1;
import java.util.Scanner;
public class SudokuSolver {
	public boolean isSafe(char[][] board, int row, int col, int number) {
        // Check the column
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == (char) (number + '0')) {
                return false;
            }
        }

        // Check the row
        for (int j = 0; j < board.length; j++) {
            if (board[row][j] == (char) (number + '0')) {
                return false;
            }
        }

        // Check the 3x3 grid
        int sr = 3 * (row / 3);
        int sc = 3 * (col / 3);

        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (board[i][j] == (char) (number + '0')) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean helper(char[][] board, int row, int col) {
        if (row == board.length) {
            return true;
        }

        int nrow = 0;
        int ncol = 0;

        if (col == board.length - 1) {
            nrow = row + 1;
            ncol = 0;
        } else {
            nrow = row;
            ncol = col + 1;
        }

        if (board[row][col] != '.') {
            return helper(board, nrow, ncol);
        } else {
            // Fill the cell
            for (int i = 1; i <= 9; i++) {
                if (isSafe(board, row, col, i)) {
                    board[row][col] = (char) (i + '0');
                    if (helper(board, nrow, ncol)) {
                        return true;
                    } else {
                        board[row][col] = '.'; // Reset on backtrack
                    }
                }
            }
        }
        return false;
    }

    public void solveSudoku(char[][] board) {
        helper(board, 0, 0);
    }

    public boolean isValidBoard(char[][] board) {
        // Check for valid Sudoku board (no duplicates in rows, columns, and 3x3 grids)
        for (int i = 0; i < 9; i++) {
            boolean[] rowCheck = new boolean[9];
            boolean[] colCheck = new boolean[9];
            for (int j = 0; j < 9; j++) {
                // Check row
                if (board[i][j] != '.') {
                    if (rowCheck[board[i][j] - '1']) {
                        return false; // Duplicate found
                    }
                    rowCheck[board[i][j] - '1'] = true;
                }

                // Check column
                if (board[j][i] != '.') {
                    if (colCheck[board[j][i] - '1']) {
                        return false; // Duplicate found
                    }
                    colCheck[board[j][i] - '1'] = true;
                }
            }
        }
        return true; // Board is valid
    }

    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();
        char[][] board = new char[9][9];
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your Sudoku puzzle row by row (use '.' for empty cells):");
        for (int i = 0; i < 9; i++) {
            String input = scanner.nextLine();
            board[i] = input.toCharArray();
        }

        if (!solver.isValidBoard(board)) {
            System.out.println("The provided Sudoku board is invalid.");
        } else {
            solver.solveSudoku(board);
            System.out.println("Solved Sudoku:");
            solver.printBoard(board);
        }
        
        scanner.close();
    }

    public void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(row);
        }
    }
}
