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

package de.modlab.smilib.fragments;

import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.io.*;
import java.util.List;

/**
 *Stores and distributes SMILES of scaffolds, linkers and building blocks.
 *
 * @author Volker Haehnke
 */
public class ComponentAdministrator {
    
    //stores all linker SMILES as objects
    private List<Linker> linkers;
    
    //stores all building block SMILES as objects
    private List<BuildingBlock> buildingBlocks;
    
    //stores all scaffold SMILES as objects
    private List<Scaffold> scaffolds;
    
    
    
    /**
     * Creates a new instance of ComponentAdministrator.
     * @param scaffoldsPath path/name of the file that contains scaffolds as SMILES
     * @param linkersPath path/name of the file that contains linkers as SMILES
     * @param buildingBlocksPath path/name of the file that contains building blocks as SMILES
     * @param checkSmiles check SMILES for conformity with SMiLib rules true/false
     * @throws java.lang.Exception Exception thrown when error persing source SMILES
     */
    public ComponentAdministrator(String scaffoldsPath, String linkersPath, String buildingBlocksPath, boolean checkSmiles) throws SmiLibIOException, SmiLibConformityException {
        
        //create SMILES reader
        SmilesReader<Scaffold> scaffoldsReader = new SmilesFileReader<Scaffold>(new ScaffoldFactory(), checkSmiles, 0);
        SmilesReader<Linker> linkerReader = new SmilesFileReader<Linker>(new LinkerFactory(), checkSmiles, 1);
        SmilesReader<BuildingBlock> buildingBlockReader = new SmilesFileReader<BuildingBlock>(new BuildingBlockFactory(), checkSmiles, 2);
        
        //read SMILES
        this.scaffolds = scaffoldsReader.readSmiles(scaffoldsPath);
        this.linkers = linkerReader.readSmiles(linkersPath);
        this.buildingBlocks = buildingBlockReader.readSmiles(buildingBlocksPath);
    }
    
    
    /**
   * Creates a new instance of ComponentAdministrator,
   * used when SMiLib ist started in GUI mode.
   * 
   * @param scaffolds array containing scaffold SMILES
   * @param linkers array containing linker SMILES
   * @param buildingBlocks array containing buildingBlock SMILES
   * @param checkSmiles check SMILES for conformity with SMiLib rules true/false
   * @throws de.modlab.smilib.exceptions.SmiLibIOException if an IO error occures
   * @throws de.modlab.smilib.exceptions.SmiLibConformityException if a SMILES string does not conform to SmiLib restrictions
   */
    public ComponentAdministrator(String[] scaffolds, String[] linkers, String[] buildingBlocks, boolean checkSmiles) throws SmiLibIOException, SmiLibConformityException {
        
        //create SMILES reader
        SmilesGuiReader temp = new SmilesGuiReader(checkSmiles);
        
        //read SMILES
        this.scaffolds = temp.readScaffolds(scaffolds);
        this.linkers = temp.readLinkers(linkers);
        this.buildingBlocks = temp.readBuildingBlocks(buildingBlocks);
    }
    
    
    /**
     *Returns whether a number is blacklisted (already used) in a scaffold/linker/building block combination
     * and because of that can not be used in the concatenation of the specified molecule parts..
     *
     *@param num number that is maybe blacklisted
     *@param scaff index of the current scaffold
     *@param linker index of the current linker
     *@param bBlock index of the current building block
     *@return <code>true</code> if <code>num</code> is blacklisted, <code>false</code> if not.
     */
    public boolean numberBlacklisted(int num, int scaff, int linker, int bBlock) {
        boolean returnBool = false;
        if (this.scaffolds.get(scaff).numberBlacklisted(num)) {
            returnBool = true;
        } else if(this.linkers.get(linker).numberBlacklisted(num)) {
            returnBool = true;
        } else if (this.buildingBlocks.get(bBlock).numberBlacklisted(num)) {
            returnBool = true;
        }
        return returnBool;
    }
    
    
    /**
     *Returns the number of scaffolds.
     *
     *@return number of scaffolds
     */
    public int getNumberOfScaffolds() {
        return this.scaffolds.size();
    }
    
    
    /**
     *Returns the number of linkers.
     *
     *@return number of linkers
     */
    public int getNumberOfLinkers() {
        return this.linkers.size();
    }
    
    
    /**
     * Returns the number of building blocks.
     *
     *@return number of building blocks
     */
    public int getNumberOfBuildingBlocks() {
        return this.buildingBlocks.size();
    }
    
    
    /**
     *Returns the scaffold with index <code>i</code> as scaffold object.
     *
     *@param i index of the scaffold
     *@return scaffold with given index as object
     */
    public Scaffold getScaffold(int i) {
        return this.scaffolds.get(i);
    }
    
    
    /**
     *Returns the linker with index <code>i</code> as linker object.
     *
     *@param i index of the linker
     *@return linker with given index as object
     */
    public Linker getLinker(int i) {
        return this.linkers.get(i);
    }
    
    
    /**
     *Returns the building block with index <code>i</code> as building block object.
     *
     *@param i index of the building block
     *@return building block with the given index as object
     */
    public BuildingBlock getBuildingBlock(int i) {
        return this.buildingBlocks.get(i);
    }
    
    
    /**
     *Retruns the id of scaffold with index i.
     *
     *@param i index of the scaffold
     *@return id of the scaffold with the given index
     */
    public String getScaffoldID(int i) {
        return this.scaffolds.get(i).getID();
    }
    
    
    /**
     *Retruns the id of linker with index i.
     *
     *@param i index of the linker
     *@return id of the linker with the given index
     */
    public String getLinkerID(int i) {
        return this.linkers.get(i).getID();
    }
    
    
    /**
     *Retruns the id of building block with index i.
     *
     *@param i index of the building block
     *@return id of the building block with the given index
     */
    public String getBuildingBlockID(int i) {
        return this.buildingBlocks.get(i).getID();
    }
    
    /**
     *Returns the scaffold with index <code>i</code> as SMILES string.
     *
     *@param i index of the scaffold
     *@return scaffold of the given index as SMILES string
     */
    public String getScaffoldString(int i) {
        return this.scaffolds.get(i).getOriginalSMILES();
    }
    
    
    /**
     *Returns the number of variable side chains in the scaffold with index <code>i</code>.
     *
     *@param i index of the scaffold
     *@return number of variable side chains in the scaffold
     */
    public int getNumberOfRGroups(int i) {
        return this.scaffolds.get(i).getNumberOfRGroups();
    }
    
    
    /**
     *Returns numbers of variable side chains in all scaffold as an array.
     *
     *@return numbers of variable side chains for every scaffold
     */
    public int[] getNumbersOfRGroups() {
        int[] returnArray = new int[this.scaffolds.size()];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = this.scaffolds.get(i).getNumberOfRGroups();
        }
        return returnArray;
    }
}