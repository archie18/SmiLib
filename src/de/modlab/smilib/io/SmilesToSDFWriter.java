/**
 * SmiLib - Rapid Assembly of Combinatorial Libraries in SMILES Notation
 *
 * Copyright (c) 2006, Johann Wolfgang Goethe-Universitaet, Frankfurt am Main, 
 * Germany. All rights reserved.
 *
 * Authors: Volker Haehnke, Andreas Schueller 
 * Contact: a.schueller@chemie.uni-frankfurt.de
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this 
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * - Neither the name of the Johann Wolfgang Goethe-Universitaet, Frankfurt am
 *   Main, Germany nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written
 *   permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.modlab.smilib.io;


import de.modlab.smilib.exceptions.SmiLibException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.exceptions.SmiLibSdfException;
import de.modlab.smilib.main.SmiLib;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Properties;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.geometry.GeometryTools;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.Molecule;
// Temporary work-around until bug #1519183 gets fixed
//import org.openscience.cdk.smiles.SmilesParser;
import de.modlab.smilib.main.SmilesParser;
import org.openscience.cdk.tools.HydrogenAdder;
import org.openscience.cdk.io.MDLWriter;

/**
 *Writes the molecules created as SMILES in a SD file.
 *
 * @author Volker Haehnke
 */
public class SmilesToSDFWriter implements SmilesWriter {
    
    /** CDK Smiles Parser */
    private SmilesParser sParser;
    
    /** CDK Molecule */
    private Molecule currentMol;
    
    /** CDK Hydrogen Adder */
    private HydrogenAdder hAdder;
    
    /** CDK Structure Diagramm Generator - computes 2D-Coordinates for Atoms */
    private StructureDiagramGenerator sdg;
    
    /** CDK MDLWriter - writes the SD file */
    private MDLWriter sdfWriter;
    
    /** needed for fields in the SD file */
    private Map<Object, Object> molProperties;
    
    /** path and name of the SD file */
    private String pathOfResultFile;
    
    /** add hydrogens befor writing the SD file */
    private boolean addHydrogens;
    
    
    
    /**
     * Creates a new instance of SmilesToSDFWriter
     * @param path path/name of the SD file to create
     * @param addHydrogens add implicit hydrogens true/false
     */
    public SmilesToSDFWriter(String path, boolean addHydrogens) {
        this.pathOfResultFile = path;
        this.addHydrogens = addHydrogens;
        molProperties = new Properties();
        sParser = new SmilesParser();
        hAdder = new HydrogenAdder();
        sdg = new StructureDiagramGenerator();
    }
    
    
    /**
     * Closes the SmilesWriter.
     * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
     */
    public void close() throws SmiLibIOException {
        try {
            sdfWriter.close();
        } catch (java.io.IOException exc) {
            throw new SmiLibIOException(exc);
        }
    }
    
    
    /**
     * Writes a SMILES string.
     * @param smiles SMILES string to write
     * @param id molecule ID
     * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
     * @throws de.modlab.smilib.exceptions.SmiLibSdfException if an error occurs converting a SMILES to SDF
     * @throws de.modlab.smilib.exceptions.SmiLibException if an error occurs
     */
    public void writeSMILES(StringBuilder smiles, StringBuilder id) throws SmiLibIOException, SmiLibException {
        try {
            if (sdfWriter == null) {
                sdfWriter = new MDLWriter(new FileWriter(new File(pathOfResultFile)));
            }
            molProperties.put("Compound Name", id.toString());
            molProperties.put("Original SMILES", smiles.toString());
            currentMol = sParser.parseSmiles(smiles.toString());
            
            //takes a lot of time and is not necessary in all cases - so it got a switch
            if (addHydrogens) {
                hAdder.addExplicitHydrogensToSatisfyValency(currentMol);
            }
            
            sdg.setMolecule(currentMol);
            sdg.generateCoordinates();
            currentMol = (Molecule) sdg.getMolecule();
            currentMol.setProperty(CDKConstants.TITLE, id.toString());
            GeometryTools.translateAllPositive(currentMol);
            sdfWriter.setSdFields(molProperties);
            sdfWriter.write(currentMol);
        } catch (java.io.IOException exc) {
            throw new SmiLibIOException(exc);
        } catch (Exception e) {
            throw new SmiLibSdfException(e.getMessage() + SmiLib.nl + "SMILES: " + smiles.toString() + " ID: " + id.toString(), e);
        }
    }    
    
    
    /**
     * Sets whether a preview shall be shown and how many molecules are shown in preview.
     * Operation not supported by this SmilesWriter.
     *
     * @param i number of molecules in preview
     * @throws UnsupportedOperationException since the operation is not supported by this SmilesWriter
     */
    public void showPreview(int i) {
        throw new UnsupportedOperationException("showPreview(int i) not supported by this SmilesWriter.");
    }
}