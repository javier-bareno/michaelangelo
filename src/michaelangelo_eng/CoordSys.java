/*
 * CoordSys.java
 *
 * Created on November 17, 2005, 3:23 PM
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
public class CoordSys {
    private int ox; private int oy; private int oz;
    private int ax; private int ay; private int az;
    private int bx; private int by; private int bz;
    private int cx; private int cy; private int cz;
    
    // trans from canon
    private float xa, xb, xc, ya, yb, yc, za, zb, zc;
    
    /** Creates a new instance of CoordSys */
    public CoordSys(int nox, int noy, int noz, int nax, int nay, int naz, int nbx,
            int nby, int nbz, int ncx, int ncy, int ncz){
        ox = nox; oy = noy; oz = noz;
        ax = nax; ay = nay; az = naz;
        bx = nbx; by = nby; bz = nbz;
        cx = ncx; cy = ncy; cz = ncz;
        
        float det = ax*by*cz+ az*bx*cy + ay*bz*cx;
        det -= (az*by*cx + ax*bz*cy + ay*bx*cz);
        
        xa = (by*cz - cy*bz) / det;
        xb = (cy*az - cz*ay) / det;
        xc = (ay*bz - az*by) / det;
        ya = (cx*bz - cz*bx) / det;
        yb = (ax*cz - az*cx) / det;
        yc = (bx*az - ax*bz) / det;
        za = (bx*cy - by*cx) / det;
        zb = (cx*ay - cy*ax) / det;
        zc = (ax*by - ay*bx) / det;
    }
    
    public int getX(int a, int b, int c){
        // takes vect a,b,c in coordsys, returnss x in canon
        int x = ox + a * ax + b *bx + c * cx;
        return x;
    }
    public int getY(int a, int b, int c){
        // takes vect a,b,c in coordsys, returnss y in canon
        return (oy + a * ay + b *by + c * cy);
    }
    public int getZ(int a, int b, int c){
        // takes vect a,b,c in coordsys, returnss x in canon
        return ( oz + a * az + b *bz + c * cz);
    }
    
    public int getA(int x, int y, int z){
        // gets vector x y z in canon, returns a in coordsys 
        int tx = x - ox; int ty = y -oy; int tz = z - oz;
        float fa = x * xa + y * ya + z * za;
        return (int) Math.round(fa);
    }
    public int getB(int x, int y, int z){
        // gets vector x y z in canon, returns b in coordsys 
        int tx = x - ox; int ty = y -oy; int tz = z - oz;
        float fb = x * xb + y * yb + z * zb;
        return (int) Math.round(fb);
    }
    public int getC(int x, int y, int z){
        // gets vector x y z in canon, returns c in coordsys 
        int tx = x - ox; int ty = y -oy; int tz = z - oz;
        float fc = x * xc + y * yc + z * zc;
        return (int) Math.round(fc);
    }
    
    public int getModA(){
        double td = ax*ax + ay*ay + az*az;
        td = Math.sqrt(td);
        return (int) Math.round(td);
    }
    public int getModB(){
        double td = bx*bx + by*by + bz*bz;
        td = Math.sqrt(td);
        return (int) Math.round(td);
    }    
    public int getModC(){
        double td = cx*cx + cy*cy + cz*cz;
        td = Math.sqrt(td);
        return (int) Math.round(td);
    }    
}
