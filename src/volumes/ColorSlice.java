/*
 * ColorSlice.java
 *
 * Created on November 21, 2005, 5:34 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package volumes;
import java.awt.*;
import java.awt.image.*;

/**
 *
 * @author bareno
 */
public class ColorSlice {
    
    private int[] colors;
    private int height;
    private int width;
    private int sliceH;
    
    /** Creates a new instance of ColorSlice */
    public ColorSlice(String bmpName, int w, int h, int sH) {
        height = h;
        width = w;
        sliceH = sH;        
        GrabPixel gumias = new GrabPixel(bmpName);
        colors = gumias.getMatrix();   
        // debug
       // int kk = colors[210] & 0x00ff0000;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSliceH() {
        return sliceH;
    }
    
    int getColor(int x, int y){
        int ret = 0;
        try {
            
         if (x <= width && y <= height){           
            ret = colors[y*width + x];
         }
        } catch (Exception e){
            int what = x;
        } 
        
        return ret;
    }
    
}


