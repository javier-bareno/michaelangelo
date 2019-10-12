/*
 * Cube.java
 *
 * Created on November 18, 2005, 8:55 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package volumes;

import michaelangelo_eng.*;

/**
 *
 * @author bareno
 */
public class Cube implements Volume{
    // describes a box in absolute coords by two oposing corners
    
    private int x1, y1, z1;
    private int x2, y2, z2;
    
    
    /** Creates a new instance of Cube */
    public Cube(int xn1, int yn1, int zn1, int xn2, int yn2, int zn2) {
        x1 = xn1; x2 = xn2;
        y1 = yn1; y2 = yn2;
        z2 = zn2; z1 = zn1;
    }
    public Cube(Box box){
        x1 = box.getMinA(); x2 =box.getMaxA();     
        y1 = box.getMinB(); y2 =box.getMaxB();
        z1 = box.getMinC(); z2 =box.getMaxC();
    }
    
    public boolean isIn(int x, int y, int z){
        boolean ans = true;
        ans &= ((x1 <= x) && (x <= x2));
        ans &= ((y1 <= y) && (y <= y2));
        ans &= ((z1 <= z) && (z <= z2));
        return ans;
    }
    public int colorOf(int x, int y, int z){
        return 0;
    }
    public Box getBoundingBox(CoordSys coord){
        // calculate the corners in coordsys
        
        int maxA; int minA;
        int ca = coord.getA(x1, y1, z1);
        maxA = minA = ca;
        ca = coord.getA(x2, y1, z1);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x1, y2, z1);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x2, y2, z1);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x1, y1, z2);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x2, y1, z2);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x1, y2, z2);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        ca = coord.getA(x2, y2, z2);
        maxA = Math.max(maxA, ca); minA = Math.min(minA, ca);
        
        int maxB; int minB;
        int cb = coord.getB(x1, y1, z1);
        maxB = minB = cb;
        cb = coord.getB(x2, y1, z1);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x1, y2, z1);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x2, y2, z1);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x1, y1, z2);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x2, y1, z2);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x1, y2, z2);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        cb = coord.getB(x2, y2, z2);
        maxB = Math.max(maxB, cb); minB = Math.min(minB, cb);
        
        int maxC; int minC;
        int cc = coord.getC(x1, y1, z1);
        maxC = minC = cc;
        cc = coord.getC(x2, y1, z1);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x1, y2, z1);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x2, y2, z1);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x1, y1, z2);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x2, y1, z2);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x1, y2, z2);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        cc = coord.getC(x2, y2, z2);
        maxC = Math.max(maxC, cc); minC = Math.min(minC, cc);
        
        return new Box(minA, minB, minC, maxA, maxB, maxC);
    }
}
