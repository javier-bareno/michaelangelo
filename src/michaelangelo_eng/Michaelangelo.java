/*
 * Michaelangelo.java
 *
 * Created on November 14, 2005, 8:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import java.util.*;
import volumes.*;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.IOException;

/**
 *
 * @author Javier Bareño
 * This class keeps a scene, reads/writes XML and parses to AtomList
 */
public class Michaelangelo {
    // This class does the job.
    // Load data from XML file and process each material in turn.
    // Produce output XML and log file.
    private AtomList atomCoords;
    private org.jdom.Element logXMLfrag;
    
    public Michaelangelo(org.jdom.Element fragXML){
        // variables to store scene data
        int bmpX, bmpY; // pixel
        int szX, szY; // pm
        
        String tmpStr, key, value;
        Integer tmpInt;
        
        
        // start object vars
        atomCoords = new AtomList();
        if (logXMLfrag == null){
            logXMLfrag = new org.jdom.Element("Michaelangelo-Log");
            logXMLfrag.setAttribute("type","Created from XML fragment");
        }
        
        // Create hashtable with color name-value
        Hashtable colorIDs = new Hashtable();
        
        org.jdom.Element bmpDesc = fragXML.getChild("meta").getChild("bitmap-description");
        org.jdom.Element pixGeom = bmpDesc.getChild("pixel-geometry");
        tmpStr = bmpDesc.getAttributeValue("width");
        tmpInt = new Integer(tmpStr);
        bmpX = tmpInt.intValue();
        tmpStr = bmpDesc.getAttributeValue("height");
        tmpInt = new Integer(tmpStr);
        bmpY = tmpInt.intValue();
        pixGeom = null;
        
        org.jdom.Element bmpColors = bmpDesc.getChild("colors");
        List colorList = bmpColors.getChildren();
        int numCols = colorList.size();
        for (int i=0; i<numCols; i++){
            org.jdom.Element colorXML = (org.jdom.Element) colorList.get(i);
            key = colorXML.getAttributeValue("id");
            value = colorXML.getAttributeValue("value");
            colorIDs.put(key, value);
        }
        bmpColors = null;
        colorList = null;
        bmpDesc = null;
                
        // Create hashtable with material-colorValue bindings
        Hashtable matColors = new Hashtable();
        org.jdom.Element matCols = fragXML.getChild("bindings");
        List matList = matCols.getChildren();
        int numMats = matList.size();
        for (int i=0; i<numMats; i++){
            org.jdom.Element colorMat = (org.jdom.Element) matList.get(i);            
            key = colorMat.getAttributeValue("color");
            value = (String) colorIDs.get(key);
            key = colorMat.getAttributeValue("material");
            matColors.put(key, value);
        }
        matList = null;
        matCols = null;
        
        // load dimensions and create colorCube
        org.jdom.Element strXML = fragXML.getChild("meta").getChild("structure");
        org.jdom.Element dimXML = strXML.getChild("bounding-box");
        strXML = null;
        tmpStr = dimXML.getAttributeValue("x");
        tmpInt = new Integer(tmpStr);
        szX = tmpInt.intValue();
        tmpStr = dimXML.getAttributeValue("y");
        tmpInt = new Integer(tmpStr);
        szY = tmpInt.intValue();
        dimXML = null;        
        ColorCube cube = new ColorCube(szX, szY, bmpX, bmpY);
        
        org.jdom.Element bmpsXML = fragXML.getChild("bitmaps");
        List bmpList = bmpsXML.getChildren();
        int numSlizes = bmpList.size();
        for (int i=0; i < numSlizes; i++){
            bmpsXML = (org.jdom.Element) bmpList.get(i);
            String bmpName = bmpsXML.getAttributeValue("path");
            bmpName += "/";
            bmpName += bmpsXML.getAttributeValue("name");
            tmpStr = bmpsXML.getAttributeValue("slice-height");
            tmpInt = new Integer(tmpStr);
            int slzH = tmpInt.intValue();
            cube.addSlice(bmpName, slzH);
        }   
        bmpList = null;
        bmpsXML = null;
        
        // Process each material in materials node to atomCoords
        org.jdom.Element materialsXML = fragXML.getChild("materials");
        List materialsList = materialsXML.getChildren();
        numMats = materialsList.size();
        for (int i=0; i<numMats; i++){
            org.jdom.Element materialXML = (org.jdom.Element) materialsList.get(i);
            Material thisMaterial = new Material(materialXML);
            tmpStr = materialXML.getAttributeValue("id");
            tmpStr = (String) matColors.get(tmpStr);
            tmpInt = new Integer(tmpStr);
            int colorVal = tmpInt.intValue();
            AtomList tmpAtList = thisMaterial.getFullColor(cube, colorVal);
            atomCoords.concat(tmpAtList);            
        }
    }
    
    public Michaelangelo(String fileName){
        //retrieve XML from file, create logXMLfrag, and call const.
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Element fragXML;
        
        try {
            org.jdom.Document XMLdoc = builder.build(fileName);
            fragXML = XMLdoc.getRootElement();
            // variables to store scene data
            int bmpX, bmpY; // pixel
            int szX, szY; // pm
            
            String tmpStr, tmpStr2, key, value;
            Integer tmpInt;
            
            
            // start object vars
            atomCoords = new AtomList();
            if (logXMLfrag == null){
                logXMLfrag = new org.jdom.Element("Michaelangelo-Log");
                logXMLfrag.setAttribute("type","Created from XML fragment");
            }
            
            // Create hashtable with color name-value
            Hashtable colorIDs = new Hashtable();
            
            org.jdom.Element bmpDesc = fragXML.getChild("meta").getChild("bitmap-description");
            org.jdom.Element pixGeom = bmpDesc.getChild("pixel-geometry");
            tmpStr = pixGeom.getAttributeValue("width");
            tmpInt = new Integer(tmpStr);
            bmpX = tmpInt.intValue();
            tmpStr = pixGeom.getAttributeValue("height");
            tmpInt = new Integer(tmpStr);
            bmpY = tmpInt.intValue();
            pixGeom = null;
            
            org.jdom.Element bmpColors = bmpDesc.getChild("colors");
            List colorList = bmpColors.getChildren();
            int numCols = colorList.size();
            for (int i=0; i<numCols; i++){
                org.jdom.Element colorXML = (org.jdom.Element) colorList.get(i);
                key = colorXML.getAttributeValue("id");
                value = colorXML.getAttributeValue("value");
                colorIDs.put(key, value);
            }
            bmpColors = null;
            colorList = null;
            bmpDesc = null;
            
            // Create hashtable with material-colorValue bindings
            Hashtable matColors = new Hashtable();
            org.jdom.Element matCols = fragXML.getChild("bindings");
            List matList = matCols.getChildren();
            int numMats = matList.size();
            for (int i=0; i<numMats; i++){
                org.jdom.Element colorMat = (org.jdom.Element) matList.get(i);
                key = colorMat.getAttributeValue("color");
                value = (String) colorIDs.get(key);
                key = colorMat.getAttributeValue("material");
                matColors.put(key, value);
            }
            matList = null;
            matCols = null;
            
            // load dimensions and create colorCube
            org.jdom.Element strXML = fragXML.getChild("meta").getChild("structure");
            org.jdom.Element dimXML = strXML.getChild("bounding-box");
            strXML = null;
            tmpStr = dimXML.getAttributeValue("x");
            tmpInt = new Integer(tmpStr);
            szX = tmpInt.intValue();
            tmpStr = dimXML.getAttributeValue("y");
            tmpInt = new Integer(tmpStr);
            szY = tmpInt.intValue();
            dimXML = null;
            ColorCube cube = new ColorCube(szX, szY, bmpX, bmpY);
            
            org.jdom.Element bmpsXML = fragXML.getChild("bitmaps");
            List bmpList = bmpsXML.getChildren();
            int numSlizes = bmpList.size();
            for (int i=0; i < numSlizes; i++){
                bmpsXML = (org.jdom.Element) bmpList.get(i);
                String bmpName = bmpsXML.getAttributeValue("path");
                bmpName += "/";
                bmpName += bmpsXML.getAttributeValue("name");
                tmpStr = bmpsXML.getAttributeValue("slice-height");
                tmpInt = new Integer(tmpStr);
                int slzH = tmpInt.intValue();
                cube.addSlice(bmpName, slzH);
            }
            bmpList = null;
            bmpsXML = null;
            
            // Process each material in materials node to atomCoords
            org.jdom.Element materialsXML = fragXML.getChild("materials");
            List materialsList = materialsXML.getChildren();
            numMats = materialsList.size();
            for (int i=0; i<numMats; i++){
                org.jdom.Element materialXML = (org.jdom.Element) materialsList.get(i);
                Material thisMaterial = new Material(materialXML);
                tmpStr = materialXML.getAttributeValue("id");
                tmpStr2 = (String) matColors.get(tmpStr);
                tmpStr = tmpStr2.substring(1);
                //tmpInt = new Integer.parseInt(tmpStr2);
                //int colorVal = tmpInt.intValue();
                int colorVal = Integer.parseInt(tmpStr, 16);
                AtomList tmpAtList = thisMaterial.getFullColor(cube, colorVal);
                atomCoords.concat(tmpAtList);
            }
        }
        // indicates a well-formedness error
        catch (JDOMException e) {
            System.out.println(fileName + " is not well-formed.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not check " + fileName);
            System.out.println(" because " + e.getMessage());
        }
        
    }
    
    public AtomList getAtomList(){
        return atomCoords;
    }    
    public org.jdom.Element toXMLfrag(){
        org.jdom.Element frag = atomCoords.toXML();
        return frag; 
    }   
    public org.jdom.Element getLog(){
        return logXMLfrag;
    }   
    
    public static void main(String[] args) {
        // TODO code application logic here
        // Takes four params:
        // 1- path in
        // 2- fname in
        // 3- path out
        // 4- fname out
        String fnamein = args[0] + "/" + args[1];
        String fnameout = args[2] + "/" + args[3] + ".xml";
        String fnamexyz = args[2] + "/" + args[3] + ".xyz";
        String fnamePOV = args[2] + "/" + args[3] + ".pov";
        Michaelangelo myScene = new Michaelangelo(fnamein);
        AtomList theAns = myScene.getAtomList();
       // theAns.toXMLfile(fnameout);
        theAns.toXYZ(fnamexyz);
        theAns.toPOVRay(fnamePOV);
    }
    
}
