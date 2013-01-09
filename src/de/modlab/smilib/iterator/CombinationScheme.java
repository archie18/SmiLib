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

package de.modlab.smilib.iterator;

/**
 *Administrates information which Linkers/BuildingBlocks shall
 *be used on the variable side chains.
 *
 *  @author Volker Haehnke
 * 
 */
public class CombinationScheme {
    
    //index of the rGroup whichs linkers get added
    private int currentLinkerIndex = 0;
    
    //index of the rGroup whichs bBlocks get added
    private int currentBlockIndex = 0;
    
    //[rGroups][linkersPerRGroup]
    private int[][] linkers;
    
    //[rGroups}]bBlocksPerRGroup]
    private int[][] bBlocks;
    
    //index of scaffold in source file
    private int scaffoldNumber;
    
    
    
    /** Creates a new instance of CombinationScheme
     *
     *@param scaffoldNumber index of scaffold in source file corrected to counting from zero
     *@param numRGroups number of variable side chains
     */
    public CombinationScheme(int scaffoldNumber, int numRGroups) {
        this.scaffoldNumber = scaffoldNumber;
        linkers = new int[numRGroups][];
        bBlocks = new int[numRGroups][];
    }
    
    
    /**
     *The building blocks specified in a column of the reaction scheme are
     *set as the building blocks to be used on the rGroup corresponding to
     *that column.
     *
     *@param block Indices (line numbers in source file) of the building blocks to be used on that rGroup
     */
    public void addBBlocks(Integer[] block) {
        int[] temp = new int[block.length];
        for (int i = 0; i < block.length; i++) {
            //Java counts from 0, so the block with index 1 in the file will be 0 in an array
            temp[i] = block[i].intValue()-1;
        }
        this.bBlocks[currentBlockIndex] = temp;
        currentBlockIndex++;
    }
    
    
    /**
     *The linkers specified in a column of the reaction scheme are
     *set as the linkers to be used on the rGroup corresponding to
     *that column.
     *
     *@param linker Indices (line numbers in source file) of the linkers to be used on that rGroup
     */
    public void addLinkers(Integer[] linker) {
        int[] temp = new int[linker.length];
        for (int i = 0; i < linker.length; i++) {
            //Java counts from 0, so the linker with index 1 in the file will be 0 in an array
            temp[i] = linker[i].intValue()-1;
        }
        this.linkers[currentLinkerIndex] = temp;
        currentLinkerIndex++;
    }
    
    
    /**
     *Returns, which linkers shall be used on which rGroups
     *
     *@return array of arrays that contain the indices of the linkers that shall be used on the scaffold-rGroups
     */
    public int[][] getLinkers() {
        return this.linkers;
    }
    
    
    /**
     *Returns, which building blocks shall be used on which rGroups
     *
     *@return array of arrays that contain the indices of the building blocks that shall be used on the scaffold-rGroups
     */
    public int[][] getBBlocks() {
        return this.bBlocks;
    }
    
    
    /**
     *Returns Index of the scaffold
     *
     *@return index of scaffold
     */
    public int getScaffoldIndex() {
        return this.scaffoldNumber;
    }
}
