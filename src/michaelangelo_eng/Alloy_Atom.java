/*
 * Alloy_Atom.java
 *
 * Created on November 14, 2005, 6:21 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import java.util.Vector;
import java.lang.Integer;
import java.util.List;

/**
 *
 * @author Javier Bareño
 * This is an atom with many elements associated to it
 * And a random atom getter
 */
public class Alloy_Atom {
    private int x=0; private int y=0; private int z=0;
    
    private int number=0;
    private int totProb = 0; // if < 1000 then alloy or vacancy
    private String id;
    private Vector listElements;
    private Vector probabilities;
     
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    
    public String getID(){
        return id;
    }
    public boolean isAlloy(){
        return (totProb < 1000);
    }
    
    /** Creates a new instance of Alloy_Atom */
    
    public Alloy_Atom(){
        number =0;
        x=0; y=0; z=0;
        id="";
        listElements = new Vector();
        probabilities = new Vector();
    }
    public Alloy_Atom(int nx, int ny, int nz, String ID, int nnum, Vector nElements, Vector nProb) {
        x = nx;
        y = ny;
        z = nz;
        id = ID;
        number = nnum;
        listElements = nElements;
        probabilities = nProb;
    }
    public Alloy_Atom(org.jdom.Element XMLfragment){
        List childrenList = XMLfragment.getChildren();
        int numChildren = childrenList.size();
               
        number = 0;
        totProb = 0;
        id = XMLfragment.getAttributeValue("id");
        
        Integer tmpInt;
        String tmpStr;
        org.jdom.Element tmpXML;
        
        tmpXML = (org.jdom.Element) childrenList.get(0); // relative-coordinates
        tmpStr = tmpXML.getAttributeValue("x");
        tmpInt = new Integer(tmpStr);
        x = tmpInt.intValue();
        tmpStr = tmpXML.getAttributeValue("y");
        tmpInt = new Integer(tmpStr);
        y = tmpInt.intValue();
        tmpStr = tmpXML.getAttributeValue("z");
        tmpInt = new Integer(tmpStr);
        z = tmpInt.intValue();
        
        for (int i=1; i<numChildren; i++){
            tmpXML = (org.jdom.Element) childrenList.get(i); // element-descriptor
            tmpStr = tmpXML.getAttributeValue("probability");
            tmpInt = new Integer(tmpStr);
            int prob = tmpInt.intValue();
            Element tmpElement = new Element(tmpXML);
            addElement(tmpElement,  prob);            
        }
    }
    public Atom getAtom(int n){
        // n is a (random) int between 1 and 1000;
        // returns a (random) element from the list
        // or a vacancy if n beyond list
        
        Element tmpElement;
        
        if (totProb < 1001){ // many possible Elements, pick one
            tmpElement = new Element(0, "Vac", "Default_Vacancy", 1000);
            int p = 0;
            for (int i=0; i<number; i++) {
                p += ((java.lang.Integer) probabilities.get(i)).intValue();
                if (p >= n) {
                    tmpElement = (Element) listElements.get(i);
                    i = number; // terminates search
                }
            }
        } else { // only one Element
            tmpElement = (Element) listElements.get(0);
        }
        
        Atom returnAtom = new Atom(x, y, z, id, tmpElement);
        return returnAtom;
    }
    public org.jdom.Element toXML(){
        org.jdom.Element XMLfragment = new org.jdom.Element("atom");
        XMLfragment.setAttribute("id",id);
        
        org.jdom.Element XMLcoord = new org.jdom.Element("relative-coordinates");
        XMLcoord.setAttribute("x",String.valueOf(x));
        XMLcoord.setAttribute("y",String.valueOf(y));
        XMLcoord.setAttribute("z",String.valueOf(z));
        XMLfragment.addContent(XMLcoord);
        
        for (int i=0; i<number; i++){
            org.jdom.Element atom = ((Element) listElements.get(i)).toXML();
            int prob = ((Integer) probabilities.get(i)).intValue();
            atom.setAttribute("probability",String.valueOf(prob));
            XMLfragment.addContent(atom);
        }
        
        return XMLfragment;
    }
    public void addElement(Element nElement, int prob){
        if (number == 0){
            listElements = new Vector();
            probabilities = new Vector();
        }
        number ++;
        listElements.add(nElement);
        probabilities.add(new Integer(prob));
        totProb += prob;
    }
    
}
