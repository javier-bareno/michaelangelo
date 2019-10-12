/*
 * Main.java
 *
 * Created on November 21, 2005, 2:33 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.IOException;
import volumes.*;

/**
 *
 * @author bareno
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Takes four params:
        // 1- path in
        // 2- fname in
        // 3- path out
        // 4- fname out
        String fnamein = args[0] + "/" + args[1];
        String fnameout = args[2] + "/" + args[3];
        Michaelangelo myScene = new Michaelangelo(fnamein);
        AtomList theAns = myScene.getAtomList();
        //theAns.toXMLfile(fnameout);
        theAns.toPOVRay(fnameout);
    }
    
    
    
    
}
