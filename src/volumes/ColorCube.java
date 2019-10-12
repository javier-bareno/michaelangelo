/*
 * ColorCube.java
 *
 * Created on November 21, 2005, 2:36 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package volumes;
import java.util.Vector;
import java.awt.*;
import java.awt.image.*;
import michaelangelo_eng.*;



/**
 *
 * @author bareno
 */
public class ColorCube implements Volume{

    private int x;

    private int y;

    private int z;

    private int bx;

    private int by;

    private int numSlices; // number of slices
    private Vector sliceList; // Vector of ColorSlice objects
    private Vector sliceHeight;
            
    
    /** Creates a new instance of ColorCube */
    public ColorCube(int nx, int ny, int nbx, int nby) {
        // create empty Volume;
        x = nx;
        y= ny;
        z = 0;
        bx = nbx;
        by = nby;
        numSlices =0;
        sliceList = new Vector();
        sliceHeight = new Vector();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public int getBx() {
        return bx;
    }
    public int getBy() {
        return by;
    }
    public int getNumSlices() {
        return numSlices;
    }
    
    public void addSlice(String bmpName, int sliceH){
        // adds a new slice to the list, updates total height z
            numSlices++;
            z += sliceH;
            sliceHeight.add(new Integer(sliceH)); 
            ColorSlice kk = new ColorSlice(bmpName, bx, by, sliceH);       
            sliceList.add(kk);
    }
    
    
    private int getColor(int px, int py, int s){
        if (s >= numSlices)
            return 0;
        ColorSlice slice =(ColorSlice) sliceList.get(s);
        int col1 = slice.getColor(px, py);      
        return (col1 & 0x00ffffff); // get rid of alpha
    }
    public boolean isAtomColor(Atom atom, int color){
        // first get atom in pixels
        int x = atom.getX();
        int y = atom.getY();
        int z = atom.getZ();
        int r = atom.getRadius();
        boolean ret = false;
        // Debug
        if (x>1000 & y > 1000) { // Debug purpose only
            int kk =0;
        }
        /*
        double Mpx = (x+r)*bx * 1. / this.x; 
        double mpx = (x-r)*bx * 1. / this.x;
        double Mpy = (y+r)*by * 1. / this.y;
        double mpy = (y-r)*by * 1. / this.y;
        int Mx = (new Double(Math.round(Mpx))).intValue();
        int mx = (new Double(Math.round(mpx))).intValue();
        int My = (new Double(Math.round(Mpy))).intValue();
        int my = (new Double(Math.round(mpy))).intValue();
        
        // TO DO: change so if out of bounds -> color is false
        // make sure in bounds
        if (Mx > bx-1) return false;
        if (My > by-1) return false;
        if (mx <0) return false;
        if (my <0) return false;
                     
        if (mx >=0  && my >= 0 && Mx <= bx && My <= by){
            int slice=-1;
            int totH = 0;
            for (int i=0; i < numSlices; i++){
                totH += ((Integer) sliceHeight.get(i)).intValue();
                if (z <= totH){                    
                    slice = i;
                    i = numSlices; // stops search
                }
            }
            ret = true;
            for (int i = mx; i <= Mx; i++){
                for (int j= my; j <=My; j++){
                    // then check color                    
                    int col = getColor(i, j, slice);
                    ret &= (col == color);
                }
            }            
        } */
        
        int slice=-1;
            int totH = 0;
            for (int i=0; i < numSlices; i++){
                totH += ((Integer) sliceHeight.get(i)).intValue();
                if (z <= totH){                    
                    slice = i;
                    i = numSlices; // stops search
                }
            }
            if (slice > -1){
                int px = (int) Math.round(1. * x * (bx-1) / this.x);
                int py = (int) Math.round(1. * y * (by-1) / this.y);
                int col = getColor(px, py, slice);
                ret = (col == color);
            }   
        return ret;
    }
    
    public boolean isIn(int x, int y, int z){
        boolean ans = true;
        ans &= ((0 <= x) && (x <= this.x));
        ans &= ((0 <= y) && (y <= this.y));
        ans &= ((0 <= z) && (z <= this.z));
        return ans;
    }
    public int colorOf(int x, int y, int z){
        if (isIn(x,y, z)){
            int slice=-1;
            double dpx = x*bx * 1. / this.x;    
            double dpy = y*by * 1. / this.y;
            int px = (new Double(Math.round(dpx))).intValue();
            int py = (new Double(Math.round(dpy))).intValue();
            int totH = 0;
            for (int i=0; i < numSlices; i++){
                totH += ((Integer) sliceHeight.get(i)).intValue();
                if (z <= totH){                    
                    slice = i;
                    i = numSlices;
                }
            }
            return getColor(px, py, slice);
            
        } else
            return 0;
        
        
    }
    public Box getBoundingBox(CoordSys coord){
        Cube aux = new Cube(0, 0, 0, x, y, z);
        return aux.getBoundingBox(coord);        
    }

    
    
}
