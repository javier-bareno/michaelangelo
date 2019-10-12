/*
 * Sphere.java
 *
 * Created on November 17, 2005, 9:15 PM
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
public class Sphere implements Volume {
    
    private int ox, oy, oz, rad;
    
    /** Creates a new instance of Sphere */
    public Sphere(int x, int y, int z, int R) {
        ox = x;
        oy = y;
        oz = z;
        rad = R;
    }
    
    public boolean isIn(int x, int y, int z){
        int tx = x-ox, ty= y-oy, tz = z-oz;        
        double r2 = (tx*tx + ty*ty + tz*tz);
        return (r2 <= (rad*rad));
    }
    
    public Box getBoundingBox(CoordSys coordSys){
        // get coords of center in coordSys;
        
        Cube boundingCube = new Cube(ox -rad, oy - rad, oz - rad,
                ox + rad, oy + rad, oz + rad);    
        return (boundingCube.getBoundingBox(coordSys));
    }
    
    public int colorOf(int x, int y, int z){
        return 0;
    }
}
