/*
 * AtomList.java
 *
 * Created on November 14, 2005, 7:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import java.util.Vector;
import java.util.List;
import java.lang.Integer;
import java.io.*;
import volumes.Volume;

/**
 *
 * @author Javier Bareño
 * This is the class that keeps a list of atoms (position and element)
 * and reads/writes to XML
 */
public class AtomList {
    
    private int number;
    private Vector atomList;
    
    /** Creates a new instance of AtomList */
    public AtomList() {
        number = 0;
        atomList = new Vector();
    }
    public AtomList(org.jdom.Element XMLfragment){
        // read from XML
        Integer tmpInt;
        String tmpStr;
        List childrenList = XMLfragment.getChildren();
        number = childrenList.size();
                
        for (int i=0; i<number; i++){
            org.jdom.Element XMLatom = (org.jdom.Element) childrenList.get(i);
            Atom tmpAtom = new Atom(XMLatom);
            atomList.add(tmpAtom);
        }
    }
    

    public int getNumber() {
        return number;
    }
    public Atom getAtomt(int n) {
        return (Atom) atomList.get(n);
    }
    public Vector getAtomList(){
        return atomList;
    }
    
    public org.jdom.Element toXML(){
        // write XML fragment
        org.jdom.Element XMLfragment = new org.jdom.Element("atom-list");
        for (int i=0; i<number; i++){
            Atom tmpAtom = (Atom) atomList.get(i);
            org.jdom.Element XMLatom = tmpAtom.toXML();
            XMLfragment.addContent(XMLatom);
        }
        return XMLfragment;
    }
    public void concat(AtomList newList){
        number += newList.getNumber();
        atomList.addAll(newList.getAtomList());
    }
    public void addAtom(Atom newAtom){
        number ++;
        atomList.add(newAtom);
    }
    
    public void toPOVRay(String fname){
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream prf = new PrintStream(out);
            
            for (int i=0; i<number; i++){
                Atom tmpAtom = (Atom) atomList.get(i);
                if (tmpAtom.getType() != "Vac"){
                    String atID = tmpAtom.getID();
                    String outStr = "putAtom_";
                    outStr += (atID + "_" + tmpAtom.getType() + "(");
                    outStr += String.valueOf(tmpAtom.getX());
                    outStr += (", " + String.valueOf(tmpAtom.getY()));
                    outStr += (", " + String.valueOf(tmpAtom.getZ()));
                    //outStr += (", " + String.valueOf(tmpAtom.getRadius()));
                    outStr += ")" ;
                    prf.println(outStr);
                }
            }
            
            prf.close();
            
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
    public void toXMLfile(String fname){
        // prepare doco
        org.jdom.Element root = new org.jdom.Element("Michaelangelo_Engine_Output");
        org.jdom.Element meta = new org.jdom.Element("meta");
        // TODO add metadata;
        root.addContent(meta);
        
        root.addContent(toXML());
        org.jdom.Document XMLdoc = new org.jdom.Document(root);
                
        // serialize to file
        try {
            FileOutputStream out = new FileOutputStream(fname);
            org.jdom.output.XMLOutputter serializer = new org.jdom.output.XMLOutputter();
            serializer.output(XMLdoc, out);
            out.flush();
            out.close();
            
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
    
    public void toXYZ(String fname){
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream prf = new PrintStream(out);
            
            prf.println(number);
            prf.println("Generated with Michaelangelo beta");
            
            for (int i=0; i<number; i++){
                double td;
                Atom tmpAtom = (Atom) atomList.get(i);
                String outStr = tmpAtom.getType();
                if (outStr != "Vac"){ // ignore vacancies
                    outStr += "\t";
                    td = tmpAtom.getX() / 100.;
                    outStr += String.valueOf(td);
                    outStr += "\t";
                    td = tmpAtom.getY() / 100.;
                    outStr += String.valueOf(td);
                    outStr += "\t";
                    td = tmpAtom.getZ() / 100.;
                    outStr += String.valueOf(td);
                    prf.println(outStr);
                }
            }
            prf.close();
        }
        
        catch (IOException e){
            System.err.println(e);
        }
    }
    
    public AtomList intersect(Volume vol){
        AtomList retList = new AtomList();
        
        for (int i =0; i < number; i++){
            Atom tmpAtom = (Atom) atomList.get(i);
            if (vol.isIn(tmpAtom.getX(), tmpAtom.getY(), tmpAtom.getZ()))
                retList.addAtom(tmpAtom);
        }
        return retList;
    }
    
    public AtomList purgeVac(){
        
        AtomList retAts = new AtomList();
        for (int i=0; i<number; i++){
            Atom nAt = (Atom) atomList.get(i);
            if (nAt.getType() != "Vac")
                retAts.addAtom(nAt);
        }
        return retAts;
    }
}
