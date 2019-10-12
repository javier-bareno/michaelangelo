/*
 * Material.java
 *
 * Created on November 14, 2005, 8:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package michaelangelo_eng;
import java.util.Vector;
import java.util.List;
import java.lang.Integer;
import volumes.*;

/**
 *
 * @author Javier Bareño
 * This class keeps a description of a Material
 * methods to read/write XML
 * and to generate lists of atoms that fill a volume
 */
public class Material {
    
    private boolean alloy = false;
    private boolean glass = false;
    private int alpha = 0; // amplitude of random displacement for glass
    
    private int color; // to map in bmps
    private String id; // to identify material
    
    // origin of material coord system absolute coord in fm
    private int ox; private int oy; private int oz;
    
    // bravais lattice, absolute, fm
    private int ax; private int ay; private int az;
    private int bx; private int by; private int bz;
    private int cx; private int cy; private int cz;
    
    // Basis
    private int basis_size;
    private Vector basis_list; // this is a vector of Alloy_Atom objects
    
   // set and get methods
    public boolean isAlloy() {
        return alloy;
    }
   
    public boolean isGlass() {
        return glass;
    }
   
    public int getAlpha() {
        return alpha;
    }
    
    public int getOx() {
        return ox;
    }
    
    public int getOy() {
        return oy;
    }
    
    public int getOz() {
        return oz;
    }
   
    public int getAx() {
        return ax;
    }
    
    public int getAy() {
        return ay;
    }
    
    public int getAz() {
        return az;
    }
    
    public int getBx() {
        return bx;
    }
    
    public int getBy() {
        return by;
    }
    
    public int getBz() {
        return bz;
    }
    
    public int getCx() {
        return cx;
    }
    
    public int getCy() {
        return cy;
    }
    
    public int getCz() {
        return cz;
    }
    
    public int getBasis_size() {
        return basis_size;
    }
   
    public Vector getBasis_list() {
        return basis_list;
    }
    
    public int getColor() {
        return color;
    }
    public void bind(int bindColor){
        color = bindColor;
    }
    
    public String getID() {
        return id;
    }
    
     /** Creates a new instance of Material */
    
    public Material(org.jdom.Element XMLmaterial){
        // load Material data from XML <material/> fragment
        // when loaded, Material is ubnound to any color in bmps
        // returns lists of atoms that sweep volumes
        id = XMLmaterial.getAttributeValue("id");
        
        org.jdom.Element tmpElement = XMLmaterial.getChild("description");
        Boolean tmpBoole = new Boolean(tmpElement.getAttributeValue("is-alloy"));
        alloy = tmpBoole.booleanValue(); 
        tmpBoole = new Boolean(tmpElement.getAttributeValue("is-glass"));
        glass = tmpBoole.booleanValue();
        Integer tmpInt = new Integer(tmpElement.getAttributeValue("alpha"));
        alpha = tmpInt.intValue();
        
        tmpElement = XMLmaterial.getChild("origin");
        tmpInt = new Integer(tmpElement.getAttributeValue("x"));
        ox = tmpInt.intValue();
        tmpInt = new Integer(tmpElement.getAttributeValue("y"));
        oy = tmpInt.intValue();
        tmpInt = new Integer(tmpElement.getAttributeValue("z"));
        oz = tmpInt.intValue();
        
        tmpElement = XMLmaterial.getChild("unit-cell"); 
        org.jdom.Element tmpXMLvector;
        List tmpXMLvectorList = tmpElement.getChildren(); // basis vectors a, b, c
        tmpXMLvector = (org.jdom.Element) tmpXMLvectorList.get(0); // a
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("x"));
        ax = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("y"));
        ay = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("z"));
        az = tmpInt.intValue();
        tmpXMLvector = (org.jdom.Element) tmpXMLvectorList.get(1); // b
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("x"));
        bx = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("y"));
        by = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("z"));
        bz = tmpInt.intValue();
        tmpXMLvector = (org.jdom.Element) tmpXMLvectorList.get(2); // c
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("x"));
        cx = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("y"));
        cy = tmpInt.intValue();
        tmpInt = new Integer(tmpXMLvector.getAttributeValue("z"));
        cz = tmpInt.intValue();
        
        tmpElement = XMLmaterial.getChild("basis-atoms"); 
        List tmpXMLatoms = tmpElement.getChildren();
        basis_size = tmpXMLatoms.size();
        basis_list = new Vector();
        for (int i=0; i< basis_size; i++){
            org.jdom.Element tmpXMLatom = (org.jdom.Element) tmpXMLatoms.get(i);
            basis_list.add(new Alloy_Atom(tmpXMLatom));
        }
    }
    
    public AtomList getFullBox(Box bBox){
        // Box coord treated in material's Coord system
        
        AtomList retAtList = new AtomList();    
        
        int minA = bBox.getMinA();
        int maxA = bBox.getMaxA();
        int minB = bBox.getMinB();
        int maxB = bBox.getMaxB();
        int minC = bBox.getMinC();
        int maxC = bBox.getMaxC();
        
        int x; int y; int z;
        
        // this gives a box that bounds the volume completely
        for (int l = minA; l <= maxA; l++) {
            for (int j= minB; j <= maxB; j++){
                for (int k = minC; k <= maxC; k++) {
                    // that sweeps the vol in terms of a, b, c vectors
                    x = ox + l*ax + j*bx + k*cx;
                    y = oy + l*ay + j*by + k*cy;
                    z = oz + l*az + j*bz + k*cz;
                    
                    java.util.Random randEng = new java.util.Random();
                    
                    for (int i=0; i < basis_size; i++){
                        // check each atom in vol; if(invol) copy to list
                        Alloy_Atom tmpAAt = (Alloy_Atom) basis_list.get(i);
                        int randInt = randEng.nextInt(999);
                        Atom tmpAt = tmpAAt.getAtom(randInt++);
                        
                        int rdx = 0; int rdy = 0; int rdz = 0;
                        if (glass){ // if glass -> random displacement
                            double td;
                            td = alpha * randEng.nextInt(1000) / 1000.;
                            rdx = new Double(td).intValue();
                            td = alpha * randEng.nextInt(1000) / 1000.;
                            rdy = new Double(td).intValue();
                            td = alpha * randEng.nextInt(1000) / 1000.;
                            rdz = new Double(td).intValue();
                        }
                        
                        float tfx;
                        tfx =  tmpAt.getX()*ax + tmpAt.getY()*bx + tmpAt.getZ()*cx;
                        tfx /= 1000;
                        int atx = x + Math.round(tfx) + rdx;
                        float tfy;
                        tfy =  tmpAt.getX()*ay + tmpAt.getY()*by + tmpAt.getZ()*cy;
                        tfy /= 1000;
                        int aty = y + Math.round(tfy) + rdy;
                        float tfz;
                        tfz =  tmpAt.getX()*az + tmpAt.getY()*bz + tmpAt.getZ()*cz;
                        tfz /= 1000;
                        int atz = z + Math.round(tfz) + rdz;
                        // this gives absolute position of atom
                        tmpAt.setX(atx);
                        tmpAt.setY(aty);
                        tmpAt.setZ(atz);
                        retAtList.addAtom(tmpAt);
                    } // finish sweep basis isIn determined by if atoms in the volume
                }
            }
        }
        // retAtList is much bigger than intended box.
        
        AtomList retAtList2 = retAtList.purgeVac();
        return retAtList2; 
    }
    
    public AtomList getFullVol(Volume vol){
        CoordSys coordSys = new CoordSys(ox, oy, oz, ax, ay, az, bx, by, bz, 
                cx, cy, cz);
        
        Box bBox = vol.getBoundingBox(coordSys);
        AtomList tmpAtList = getFullBox(bBox);
        AtomList retAtList = tmpAtList.intersect(vol);
        
        return retAtList;
    }
    
    public AtomList getFullColor(ColorCube vol, int col) {
        AtomList step1 = getFullVol(vol);
        // debug
        
        AtomList step2 = new AtomList();
        for (int i=0; i < step1.getNumber(); i++){
            Atom atom = step1.getAtomt(i);
            if (vol.isAtomColor(atom, col))
                step2.addAtom(atom);
        }
        return step2;              
    }
    
   
    
    
   /* public org.jdom.Element toXML(){
        org.jdom.Element XMLfragment = new org.jdom.Element("Material");
        org.jdom.Element XMLmeta = new org.jdom.Element("metadata");
        XMLmeta.setAttribute("Label",Label);
        XMLmeta.setAttribute("color", String.valueOf(color));
        XMLmeta.setAttribute("is_alloy", String.valueOf(alloy));
        XMLmeta.setAttribute("is_glass", String.valueOf(glass));
        XMLmeta.setAttribute("alpha", String.valueOf(alpha));
        XMLfragment.addContent(XMLmeta);
        
        org.jdom.Element XMLorigin = new org.jdom.Element("origin");
        XMLorigin.setAttribute("x",String.valueOf(ox));
        XMLorigin.setAttribute("y",String.valueOf(oy));
        XMLorigin.setAttribute("z",String.valueOf(oz));
        XMLfragment.addContent(XMLorigin);
        
        org.jdom.Element XMLlattice = new org.jdom.Element("Lattice_cell");
        org.jdom.Element XMLa = new org.jdom.Element("vector_a");
        XMLa.setAttribute("x",String.valueOf(ax));
        XMLa.setAttribute("y",String.valueOf(ay));
        XMLa.setAttribute("z",String.valueOf(az));
        XMLlattice.addContent(XMLa);
        org.jdom.Element XMLb = new org.jdom.Element("vector_b");
        XMLb.setAttribute("x",String.valueOf(bx));
        XMLb.setAttribute("y",String.valueOf(by));
        XMLb.setAttribute("z",String.valueOf(bz));
        XMLlattice.addContent(XMLb);
        org.jdom.Element XMLc = new org.jdom.Element("vector_c");
        XMLc.setAttribute("x",String.valueOf(cx));
        XMLc.setAttribute("y",String.valueOf(cy));
        XMLc.setAttribute("z",String.valueOf(cz));
        XMLlattice.addContent(XMLc);
        XMLfragment.addContent(XMLlattice);
        
        org.jdom.Element XMLbasis = new org.jdom.Element("basis");
        XMLbasis.setAttribute("size",String.valueOf(basis_size));
        for (int i=0; i<basis_size; i++){
            Alloy_Atom tmpAA = (Alloy_Atom) basis_list.get(i);
            XMLbasis.addContent(tmpAA.toXML());
        }
        XMLfragment.addContent(XMLbasis);
        
        return XMLfragment;                 
    }  */
/*    
    private AtomList sweepRow (int oa, int ob, int oc, int va, int vb, int vc, Volume vol){
        // all coordinates in material's system
        // process stops as soon as out of vol
        int x; int y; int z; // absolute coordinates
        AtomList retAtList = new AtomList();
        
        // start checking origin
        x = ox + oa*ax + ob*bx + oc*cx;
        y = oy + oa*ay + ob*by + oc*cy;
        z = oz + oa*az + ob*bz + oc*cz;
        
        if (!vol.isIn(x, y, z))
            return retAtList;
        
        boolean isIn = true; // allows sweep j
        
        java.util.Random randEng = new java.util.Random();
        
        for (int j=0; isIn; j++){
            x += j * (va*ax + vb*bx + vc*cx);
            y += j * (va*ay + vb*by + vc*cy);
            z += j * (va*az + vb*bz + vc*cz);
            
            isIn = false; // will stop loop if no atoms in
            
            for (int i=0; i < basis_size; i++){
                // check each atom in vol; if(invol) copy to list
                Alloy_Atom tmpAAt = (Alloy_Atom) basis_list.get(i);
                int randInt = randEng.nextInt(999);
                Atom tmpAt = tmpAAt.getAtom(randInt++);
                
                int rdx = 0; int rdy = 0; int rdz = 0;
                if (glass){ // if glass -> random displacement
                    double td;
                    td = alpha * randEng.nextInt(1000) / 1000.;
                    rdx = new Double(td).intValue();
                    td = alpha * randEng.nextInt(1000) / 1000.;
                    rdy = new Double(td).intValue();
                    td = alpha * randEng.nextInt(1000) / 1000.;
                    rdz = new Double(td).intValue();
                }
                
                float tfx;
                tfx =  tmpAt.getX()*ax + tmpAt.getY()*bx + tmpAt.getZ()*cx;
                tfx /= 1000;
                int atx = x + Math.round(tfx) + rdx;
                float tfy;
                tfy =  tmpAt.getX()*ay + tmpAt.getY()*by + tmpAt.getZ()*cy;
                tfy /= 1000;
                int aty = y + Math.round(tfy) + rdy;
                float tfz;
                tfz =  tmpAt.getX()*az + tmpAt.getY()*bz + tmpAt.getZ()*cz;
                tfz /= 1000;
                int atz = z + Math.round(tfz) + rdz;
                // this gives absolute position of atom
                
                // now check if in vol
                if (vol.isIn(atx, aty, atz)){
                    isIn = true;
                    tmpAt.setX(atx);
                    tmpAt.setY(aty);
                    tmpAt.setZ(atz);
                    retAtList.addAtom(tmpAt);
                }
            } // finish sweep basis isIn determined by if atoms in the volume    
        }// goes on sweeping j till isIn = false, then exits this loop
        
        return retAtList;
    }  
    
    private AtomList sweepPlane(int oa, int ob, int oc, int v1a, int v1b, int v1c, int v2a, int v2b, int v2c, Volume vol){
        int x; int y; int z; // absolute coordinates
        AtomList retAtList = new AtomList();
        
        // start checking origin
        x = ox + oa*ax + ob*bx + oc*cx;
        y = oy + oa*ay + ob*by + oc*cy;
        z = oz + oa*az + ob*bz + oc*cz;
        
         if (!vol.isIn(x, y, z))
            return retAtList;
       
        boolean isIn = true; // allows sweep j        
        for (int j=1; isIn; j++){ // sweep + direction
            x += j * (v1a*ax + v1b*bx + v1c*cx);
            y += j * (v1a*ay + v1b*by + v1c*cy);
            z += j * (v1a*az + v1b*bz + v1c*cz);
            
            AtomList row = sweepRow(x, y, z, v2a, v2b, v2c, vol);
            isIn = (row.getNumber() > 0);
            if (isIn)
                retAtList.concat(row);
        }
        
        x = ox + oa*ax + ob*bx + oc*cx;
        y = oy + oa*ay + ob*by + oc*cy;
        z = oz + oa*az + ob*bz + oc*cz;
        
        isIn = true; // allows sweep j-        
        for (int j=0; isIn; j++){ // sweep + direction
            x -= j * (v1a*ax + v1b*bx + v1c*cx);
            y -= j * (v1a*ay + v1b*by + v1c*cy);
            z -= j * (v1a*az + v1b*bz + v1c*cz);
            
            AtomList row = sweepRow(x, y, z, v2a, v2b, v2c, vol);
            isIn = (row.getNumber() > 0);
            if (isIn)
                retAtList.concat(row);
        }
        
        return retAtList;
    }
    
    public AtomList sweepBox(int mx, int my, int mz){
        // Returns box of atoms corners 0, 0, 0 and mx, my, mz
        AtomList retList = new AtomList();
        Volume vol = new Volume(mx,my,mz);
        
        int x=0; int y=0; int z=0; //absolute origin
        
        boolean isIn = true;
        for (int j=0; isIn; j++ ){
            x += j * cx;
            y += j * cy;
            z += j * cz;
            
        }
        
    }
    
    */
}
