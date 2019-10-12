/*
 * Element.java
 *
 * Created on November 14, 2005, 2:44 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;

import java.lang.*;



/**
 *
 * @author Javier Bareño
 * Describes the element properties, not position
 */
public class Element {
    private String type; // chem symbol
    private String id; // to map in LUT
    private int radius; // radius in fm
    private int probability = 1000; // normalized to 1000
    
    /** Creates a new instance of Element */
    public Element(int r, String t, String nid, int p) {
        radius=r;
        type = t;
        id = nid;
        probability = p;
    }
    public Element(org.jdom.Element XMLfragment){
        type = XMLfragment.getAttributeValue("type");
        id = XMLfragment.getAttributeValue("id");
        String tmpst = XMLfragment.getAttributeValue("radius");
        Integer integ = new Integer(tmpst);
        radius = integ.intValue();
        tmpst = XMLfragment.getAttributeValue("probability");
        integ = new Integer(tmpst);
        probability = integ.intValue();
    }

    public String getType() {
        return type;
    }
    public String getID() {
        return id;
    }
    public int getRadius() {
        return radius;
    }
    
    public int getProbability(){
        return probability;
    }
    public org.jdom.Element toXML(){
        org.jdom.Element XMLfragment = new org.jdom.Element("element-descriptor");
        XMLfragment.setAttribute("id",id);
        XMLfragment.setAttribute("type",type);
        XMLfragment.setAttribute("radius",String.valueOf(radius));
        XMLfragment.setAttribute("probability",String.valueOf(probability));
        
        return XMLfragment;
    }
    
}
