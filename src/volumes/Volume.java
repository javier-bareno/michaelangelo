/*
 * Volume.java
 *
 * Created on November 17, 2005, 3:21 PM
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
public interface Volume {
    
    // all funcs take abs coordinates
    
    boolean isIn(int x, int y, int z);
    int colorOf(int x, int y, int z);
    Box getBoundingBox(CoordSys coordSys);
}
