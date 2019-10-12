/*
 * GrabPixel.java
 *
 * Created on November 22, 2005, 9:19 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package volumes;
import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*;

/**
 *
 * @author javier
 */
public class GrabPixel {
    private BufferedImage buffIm;
    private int[] theMatrix;
    
    /** Creates a new instance of GrabPixel */
    public GrabPixel(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        Image i = icon.getImage();
        
        // draw into buffered
        int w = i.getWidth(null), h = i.getHeight(null);
        BufferedImage buffImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = buffImage.createGraphics();
        imageGraphics.drawImage(i, 0, 0, null);
        buffIm = buffImage;
        int [] buffer = new int[w*h];
        buffIm.getRGB(0,0, w, h, buffer, 0, w);
        theMatrix = buffer;
        // debug
        //int kk = (buffer [210] & 0x0000ff00)>>16;
    }
    
    public int[] getMatrix(){
        return theMatrix;
    }
    
}
