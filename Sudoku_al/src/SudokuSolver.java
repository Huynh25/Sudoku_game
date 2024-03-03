
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class SudokuSolver {

    private Random random = new Random();
    private int numCellsToRemove = 45; // Số ô sẽ được xóa
    private static final int N = 9;
    public int[][] grid;
    private List<Solver> solutions = new ArrayList<>();
    public int highPoint = 0;
    public Stack steps = new Stack();

    public SudokuSolver() {
        grid = new int[N][N];
    }

    public SudokuSolver(int numCellsToRemove) {
        this.numCellsToRemove = numCellsToRemove;
        grid = new int[N][N];
    }

    public boolean checkInput(int input, int x, int y) {
        boolean isFirstEncounter = true;
        boolean checkingSuccess = false;
        int step = steps.size();
        for (Solver solution : solutions) {
            
            if (solution.getSodokuSolver()[x][y] == input) {
                if (!isFirstEncounter) {
                    step--;
                } else {
                    step = steps.size();
                }

                if (solution.getStep() == step) {

                    if (isFirstEncounter) {
                        steps.add(new Coordinates(x, y));
                        isFirstEncounter = false;
                    }

                    solution.setStep(steps.size());
                    checkingSuccess = true;
                }

            }
        }
        return checkingSuccess;
    }

    public Coordinates undo() {
        Coordinates coor = null;
        for (Solver solution : solutions) {
            if (solution.getStep() == steps.size()) {
                if (coor == null && !steps.isEmpty()) {
                    coor = (Coordinates) steps.pop();
                }
                solution.setStep(steps.size());

            }
        }
        return coor;
    }

    public Coordinates hint() {
        for (Solver solution : solutions) {
            if (solution.getStep() == steps.size()) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (grid[i][j] == 0) {
                            return new Coordinates(i, j, solution.getSodokuSolver()[i][j]);
                        }
                    }
                }
            }
        }
        return null;
    }

// Kiểm tra xem số có hợp lệ để đặt vào ô (x, y) không
    private boolean isValid(int x, int y, int num) {
        // Kiểm tra hàng và cột
        for (int i = 0; i < N; i++) {
            if (grid[x][i] == num || grid[i][y] == num) {
                return false;
            }
        }

        // Kiểm tra trong ô 3x3
        int startX = x - x % 3;
        int startY = y - y % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startX + i][startY + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Giải thuật Backtracking để điền vào các ô còn trống
    private boolean solveSudoku() {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (grid[row][col] == 0) {
                    List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                    for (int k = 0; k < N; k++) {
                        Collections.shuffle(numbers); // Xáo trộn danh sách
                        int number = k;
                        if (!numbers.isEmpty()) {
                            number = numbers.get(0);
                        }
                        if (isValid(row, col, number)) {
                            grid[row][col] = number;
                            if (solveSudoku()) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                        numbers.remove(0);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // In ma trận Sudoku
    public void printSudoku() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Xóa một số ô trong ma trận
    private void removeCells() {
        int cellsRemoved = 0;

        while (cellsRemoved < numCellsToRemove) {
            int row = random.nextInt(N);
            int col = random.nextInt(N);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                cellsRemoved++;
            }
        }
    }

    public void printAllSolutions() {
        if (solutions.isEmpty()) {
            System.out.println("Không có lời giải cho ma trận Sudoku này.");
        } else {
            System.out.println("Tất cả các lời giải hợp lệ:");
            System.out.println("Có " + solutions.size() + " cách giải: ");
            for (Solver solution : solutions) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        System.out.print(solution.getSodokuSolver()[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

    // Giải thuật Backtracking để lưu tất cả các lời giải hợp lệ
    private void solveAllSolutions() {
        solutions.clear();
        solveWithAllSolutions(0, 0);
    }

    private void solveWithAllSolutions(int row, int col) {
        if (row == N) {
            int[][] solution = new int[N][N];
            for (int i = 0; i < N; i++) {
                System.arraycopy(grid[i], 0, solution[i], 0, N);
            }
            solutions.add(new Solver(solution, 0));
            return;
        }

        if (col == N) {
            solveWithAllSolutions(row + 1, 0);
            return;
        }

        if (grid[row][col] == 0) {
            for (int num = 1; num <= N; num++) {
                if (isValid(row, col, num)) {
                    grid[row][col] = num;
                    solveWithAllSolutions(row, col + 1);
                    grid[row][col] = 0;
                }
            }
        } else {
            solveWithAllSolutions(row, col + 1);
        }
    }

    public void playGame() {
        steps.clear();
        highPoint = 0;
        solveSudoku();
        removeCells();
        solveAllSolutions();
//        if (solutions.size() < 10) {
//            playGame();
//        }
        printAllSolutions();
    }
    
}
