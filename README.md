# michaelangelo
A Java package to create complex polycrystalline structures. The program produces a list of atomic positions, consistent with the crystal strcutures of each grain in the structure. These positions can then be used to render a 3D view of the structure (e.g., with [POV-Ray](http://www.povray.org)), or perform atomic calculations. You can find an example of structures generated with it in figure 5 of https://doi.org/10.1103/PhysRevB.75.155437



# Usage
src/michaelangelo_eng/Michaelangelo.java defines the Michaelangelo class, which processes the input. The construction takes either an XML object (org.jdom.Element) or the path/name to an XML file. 

After processing the input file, a call to Michaelangelo.getAtomList() returns a src/michaelangelo_eng/AtomList.java object. The calculated atomic positions can be exported with any of the following AtomList methods:
* toXMLfile(fnameout)
* toXYZ(fnameout)
* toPOVRay(fnameout)

where fnameout is the path/name of the desired output file.

Michaelangelo.main() and the main() method of src/michaelangelo_eng/Main.java provide an alternative way to run the workflow. Each takes four string parameters: path_input, filename_input, path_output, filename_output. They use these parmaters to construct the input and output file path/name's, construct a Michaelangelo object, get an AtomList instance from it, and call the ouput exporting method(s).

## Input

## Output

* **AtomList.toXMLfile(fnameout)** and XML file listing the type (element symbol) and position of each atom
* **AtomList.toXYZ(fnameout)** an xyz file listing the type and position of each atom
* **AtomList.toPOVRay(fnameout)** a file to #include in a POV-Ray scene, containing a list of PutAtom_xx_yy(x, y, z) macro calls, derived from calculated atom types and positions. Defining these macros in the POV-Ray scene allows the user to control how the amos will be rendered in the final POV-Ray image.
