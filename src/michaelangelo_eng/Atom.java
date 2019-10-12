/*
 * Atom.java
 *
 * Created on November 14, 2005, 4:46 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import java.lang.Integer;
import java.util.*;

/**
 *
 * @author Javier Bareño
 * An atom is an element in a position
 */
public class Atom {
    private int x; private int y; private int z;
    private Element element;
    private String id;
    
    /** Creates a new instance of Atom */
    public Atom(int nx, int ny, int nz, String nID, Element nElement) {
        x = nx;
        y = ny;
        z = nz;
        id = nID;
        element = nElement;
    }    
    
    public Atom(org.jdom.Element XMLfragment){
        org.jdom.Element relCoor = XMLfragment.getChild("relative-coordinates");
        org.jdom.Element XMLement = XMLfragment.getChild("element-descriptor");
        id = XMLfragment.getAttributeValue("id");
        
        Integer tmpInt;
        String tmpStr;
        
        tmpStr = relCoor.getAttributeValue("x");
        tmpInt = new Integer(tmpStr);
        x = tmpInt.intValue();
        tmpStr = relCoor.getAttributeValue("y");
        tmpInt = new Integer(tmpStr);
        y = tmpInt.intValue();
        tmpStr = relCoor.getAttributeValue("z");
        tmpInt = new Integer(tmpStr);
        z = tmpInt.intValue();
        
        element = new Element(XMLement);
    }

    public int getX() {
        return x;
    }
    public void setX(int nx){
        x = nx;
    }
    public int getY() {
        return y;
    }
    public void setY(int ny){
        y = ny;
    }
    public int getZ() {
        return z;
    }
    public void setZ(int nz){
        z = nz;
    }
    public int getRadius(){
        return element.getRadius();
    }
    public String getID(){
        return id;
    }
    
    public String getType(){
        return element.getType();
    }
    
    public Element getElement() {
        return element;
    }
    
    public org.jdom.Element toXML(){
        org.jdom.Element XMLfragment = new org.jdom.Element("atom");
        XMLfragment.setAttribute("id",id);
        
        org.jdom.Element RelCoord = new org.jdom.Element("relative-coordinates");
        RelCoord.setAttribute("x",String.valueOf(x));
        RelCoord.setAttribute("y",String.valueOf(y));
        RelCoord.setAttribute("z",String.valueOf(z));
        
        XMLfragment.addContent(RelCoord);
        XMLfragment.addContent(element.toXML());
        
        return XMLfragment;
    }
    
}
