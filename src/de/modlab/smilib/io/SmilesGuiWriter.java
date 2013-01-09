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
import de.modlab.smilib.gui.SmiLibFrame;
import javax.swing.JTextArea;

/**
 *Writes SMILES to a JTextArea.
 *
 * @author Volker Haehnke
 */
public class SmilesGuiWriter implements SmilesWriter {
    
    //SMilesWriter used if molecule SMILES shall be shown in GUI AND be saved to file
    private SmilesWriter smiWri;
    
    //save library to file true/false
    private boolean saveAsFile;
    
    //display library in GUI true/false
    private boolean showLibrary;
    
    //GUI frame
    private SmiLibFrame smiFrame;
    
    //JTextArea to write in
    private JTextArea targetTextArea;
    
    //display just a few molecules in GUI
    private boolean showPreview = false;
    
    //number of molecules shown in preview
    private int numMoleculesInPreview;
    
    private int currentNumOfMolecules = 0;
    
    
    /**
     * Creates a new instance of SmilesGuiWriter.
     * @param targetTextArea JTextArea in which the SMILES are written
     * @param showLibrary show library in GUI true/false
     * @param saveAsFile save library as file true/false
     * @param saveAsSDF save pibrary as SD file
     * @param addHydrogens add implicit hydrogens if saving as SD file true/false
     * @param path path/name of file
     * @param smiFrame GUI frame
     */
    public SmilesGuiWriter(JTextArea targetTextArea, boolean showLibrary, boolean saveAsFile, boolean saveAsSDF, boolean addHydrogens, String path, SmiLibFrame smiFrame) {
        this.targetTextArea = targetTextArea;
        this.showLibrary = showLibrary;
        this.saveAsFile = saveAsFile;
        this.smiFrame = smiFrame;
        if (saveAsFile & saveAsSDF) {
            smiWri = new SmilesToSDFWriter(path, addHydrogens);
        } else if (saveAsFile & !saveAsSDF) {
            smiWri = new SmilesFileWriter(path);
        }
    }
    
    
    /**
     * Closes the SmilesWriter.
     * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
     */
    public void close() throws SmiLibIOException {
        if (smiWri != null)
            smiWri.close();
    }
    
    
    /**
     * Writes a SMILES string.
     * @param smiles SMILES string to write
     * @param id molecule ID
     * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
     * @throws de.modlab.smilib.exceptions.SmiLibException if an error occurs
     */
    public void writeSMILES(StringBuilder smiles, StringBuilder id) throws SmiLibIOException, SmiLibException {
        currentNumOfMolecules++;
        if (saveAsFile) {
            StringBuilder temp1 = new StringBuilder(smiles.toString());
            StringBuilder temp2 = new StringBuilder(id.toString());
            smiWri.writeSMILES(temp1, temp2);
        }
        if (showLibrary) {
            if (showPreview) {
                if (currentNumOfMolecules <= numMoleculesInPreview) {
                    this.targetTextArea.append(id.append('\t').append(smiles).append('\n').toString());
                    targetTextArea.select(1,1);
                }
            } else {
                this.targetTextArea.append(id.append('\t').append(smiles).append('\n').toString());
                targetTextArea.select(1,1);
            }
        }
        smiFrame.increaseProgress();
    }
    
    
    /**
     * Sets whether a preview shall be shown and how many molecules are shown in preview.
     * @param i number of molecules in preview
     */
    public void showPreview(int i) {
        showPreview = true;
        numMoleculesInPreview = i;
    }
}