/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tran Gia Huy - CE170732 (https://github.com/ThomasTran17)
 */
public class Coordinates {

    int x, y;
    int value = 0;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Coordinates(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
