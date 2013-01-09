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

/**
 *Stores SMILES and information about a building block.
 *
 * @author Volker Haehnke
 */
public class BuildingBlock extends Fragment{
    
    //index, where the two digit ring number has to be inserted in the modified SMILES
    private int ringNumberInsertionIndex;
    
    //building block SMILES without the [A]-group
    private String modifiedBlockSMILES;
    
    
    
    /**
     *Creates a new instance of BuildingBlock.
     *
     *@param block original building block SMILES
     *@param id building block ID
     */
    public BuildingBlock(String block, String id) {
        super(block, id);
        this.prepareBlockForConcat(block);
    }
    
    
    /**
     *Removes the [A]-group from the building block SMILES.
     *
     *@param block original building block SMILES
     */
    private void prepareBlockForConcat(String block) {
        StringBuilder temp = new StringBuilder(block);
        int caseOfA = temp.indexOf("([A])");
        
        //[A]-group is not in round brackets
        if (caseOfA == -1) {
            ringNumberInsertionIndex = temp.indexOf("[A]");
            temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 3);
        //[A]-group is in round brackets
        } else {
            ringNumberInsertionIndex = caseOfA;
            temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 5);
        }
        
        this.modifiedBlockSMILES = temp.toString();
    }
    
    
    /**
     *Inserts the two digit ring number in the modified SMILES and returns the SMILES.
     *
     *@param i two digit ring number
     *@return building block SMILES ready for concatenation
     */
    public String getBlockForConcat(int i) {
        StringBuilder temp = new StringBuilder(modifiedBlockSMILES);
        temp.insert(ringNumberInsertionIndex, "%" + i);
        return temp.toString();
    }
}