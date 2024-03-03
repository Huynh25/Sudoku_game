/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tran Nguyen Nam Thuan CE171497
 */
public class Solver {
    private int[][] sodokuSolver;
    private int step;

    public Solver(int[][] sodokuSoler, int step) {
        this.sodokuSolver = sodokuSoler;
        this.step = step;
    }

    public int[][] getSodokuSolver() {
        return sodokuSolver;
    }

    public void setSodokuSolver(int[][] sodokuSolver) {
        this.sodokuSolver = sodokuSolver;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
