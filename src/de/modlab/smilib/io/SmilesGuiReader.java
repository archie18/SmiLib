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

/*
 * SmilesGuiReader.java
 *
 * Created on May 31, 2006, 12:42 PM
 */

package de.modlab.smilib.io;

import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.fragments.BuildingBlock;
import de.modlab.smilib.fragments.Linker;
import de.modlab.smilib.fragments.Scaffold;
import java.util.ArrayList;

/**
 * Parses SMILES to molecule fragments when SMILES are entered in the GUI mask.
 * @author Volker Haehnke
 */
public class SmilesGuiReader {
    
    //performs preprocessing on SMILES
    private Preprocessor prePro;
    
    //check SMILES for SmiLib rule conformity true/false
    private boolean checkSmiles;
    
    //checks SMILES for SmiLib rule conformity
    private ConformityChecker smilesChecker;
    
    
    
    /**
     * Creates a new instance of SmilesGuiReader
     * @param checkSmiles check conformity of SMILES true/false
     */
    public SmilesGuiReader(boolean checkSmiles) {
        this.checkSmiles = checkSmiles;
        prePro = new Preprocessor();
        smilesChecker = new ConformityChecker();
    }
    
    
  /**
   * Reads source SMILES.
   * 
   * @param scaffolds source SMILES strings as an array, optionally with a leading ID separated with a tab 
   * @return ArrayList of scaffold objects corresponding to the source SMILES
   * @throws de.modlab.smilib.exceptions.SmiLibConformityException if a SMILES string does not conform to SmiLib restrictions
   */
    public ArrayList<Scaffold> readScaffolds(String[] scaffolds) throws SmiLibConformityException {
        ArrayList<Scaffold> tempList = new ArrayList<Scaffold>();
        int lineCounter = 1;
        String currentInputLine;
        String[] tempLine;
        for (int i = 0; i < scaffolds.length; i++) {
            currentInputLine = scaffolds[i];
            if (currentInputLine != null) {
                if (!currentInputLine.trim().equals("")) {
                    tempLine = currentInputLine.split("\t");
                    if (tempLine.length == 1) {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[0].trim(), 0);
                        }
                        tempList.add(new Scaffold(prePro.preprocessSmiles(tempLine[0].trim()), Integer.toString(lineCounter)));
                    } else {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[1].trim(), 0);
                        }
                        tempList.add(new Scaffold(prePro.preprocessSmiles(tempLine[1].trim()), tempLine[0].trim()));
                    }
                    lineCounter++;
                }
            }
        }
        return tempList;
    }
    
    
    /**
     * Reads source SMILES.
     * @param linkers source SMILES strings as an array, optionally with a leading ID separated with a tab
     * @return ArrayList of linker objects corresponding to the source SMILES
     * @throws de.modlab.smilib.exceptions.SmiLibConformityException if a SMILES string does not conform to SmiLib restrictions
     */
    public ArrayList<Linker> readLinkers(String[] linkers) throws SmiLibConformityException {
        ArrayList<Linker> tempList = new ArrayList<Linker>();
        int lineCounter = 1;
        String currentInputLine;
        String[] tempLine;
        for (int i = 0; i < linkers.length; i++) {
            currentInputLine = linkers[i];
            if (currentInputLine != null) {
                if (!currentInputLine.trim().equals("")) {
                    tempLine = currentInputLine.split("\t");
                    if (tempLine.length == 1) {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[0].trim(), 1);
                        }
                        tempList.add(new Linker(prePro.preprocessSmiles(tempLine[0].trim()), Integer.toString(lineCounter)));
                    } else {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[1].trim(), 1);
                        }
                        tempList.add(new Linker(prePro.preprocessSmiles(tempLine[1].trim()), tempLine[0].trim()));
                    }
                    lineCounter++;
                }
            }
        }
        return tempList;
    }
    
    
    /**
     * Reads source SMILES.
     * @param buildingBlocks source SMILES strings as an array, optionally with a leading ID separated with a tab
     * @return ArrayList of building block objects corresponding to the source SMILES
     * @throws de.modlab.smilib.exceptions.SmiLibConformityException if a SMILES string does not conform to SmiLib restrictions
     */
    public ArrayList<BuildingBlock> readBuildingBlocks(String[] buildingBlocks) throws SmiLibConformityException {
        ArrayList<BuildingBlock> tempList = new ArrayList<BuildingBlock>();
        int lineCounter = 1;
        String currentInputLine;
        String[] tempLine;
        for (int i = 0; i < buildingBlocks.length; i++) {
            currentInputLine = buildingBlocks[i];
            if (currentInputLine != null) {
                if (!currentInputLine.trim().equals("")) {
                    tempLine = currentInputLine.split("\t");
                    if (tempLine.length == 1) {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[0].trim(), 2);
                        }
                        tempList.add(new BuildingBlock(prePro.preprocessSmiles(tempLine[0].trim()), Integer.toString(lineCounter)));
                    } else {
                        if (checkSmiles) {
                            smilesChecker.checkConformity(tempLine[1].trim(), 2);
                        }
                        tempList.add(new BuildingBlock(prePro.preprocessSmiles(tempLine[1].trim()), tempLine[0].trim()));
                    }
                    lineCounter++;
                }
            }
        }
        return tempList;
    }
}