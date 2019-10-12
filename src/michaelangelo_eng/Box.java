/*
 * Box.java
 *
 * Created on November 16, 2005, 3:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;

/**
 *
 * @author bareno
 */
public class Box {
    private int minA; private int maxA;
    private int minB; private int maxB;
    private int minC; private int maxC;
    
    public Box(int mx, int my, int mz, int Mx, int My, int Mz){
        minA = mx;
        maxA = Mx;
        minB = my;
        maxB = My;
        minC = mz;
        maxC = Mz;
    }

    public int getMinA() {
        return minA;
    }

    public void setMinA(int minA) {
        this.minA = minA;
    }

    public int getMaxA() {
        return maxA;
    }

    public void setMaxA(int maxA) {
        this.maxA = maxA;
    }

    public int getMinB() {
        return minB;
    }

    public void setMinB(int minB) {
        this.minB = minB;
    }

    public int getMaxB() {
        return maxB;
    }

    public void setMaxB(int maxB) {
        this.maxB = maxB;
    }

    public int getMinC() {
        return minC;
    }

    public void setMinC(int minC) {
        this.minC = minC;
    }

    public int getMaxC() {
        return maxC;
    }

    public void setMaxC(int maxC) {
        this.maxC = maxC;
    }
    
}
