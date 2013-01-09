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
 *Iterator for all possible molecules that can be created
 *using the given scaffold, linkers and building blcoks.
 * 
 * @author Volker Haehnke
 * @author Andreas Schueller
 * 
 */
public class FullCombinationIterator implements SmiLibIterator {
    
    /** numbers of variable side chains for the used scaffolds */
    private int[] numsRGroups;
    
    /** index of the current scaffold */
    private int scaffoldIndex = 0;
    
    /** number of linkers available */
    private int numLinkers;
    
    /** number of building blocks available */
    private int numBBlocks;
    
    /** index of the current reaction (counts from 0 to maxium) */
    private int reactionIndex = 0;
    
    
    
    /**
     *Creates a new instance of FullCombinationIterator
     *
     *@param numsRGroups array of number of variable side chains for each scaffold
     *@param numLinkers number of linkers available
     *@param numBBlocks number of building blocks available
     */
    public FullCombinationIterator(int[] numsRGroups, int numLinkers, int numBBlocks) {
        this.numsRGroups = numsRGroups;
        this.numLinkers = numLinkers;
        this.numBBlocks = numBBlocks;
    }
    

    /**
     *Returns whether there is one more combination of
     *scaffold/linkers/building blocks available or not.
     *
     *@return one more combination available true/false
     */
    public boolean hasNext() {
        return scaffoldIndex < numsRGroups.length;
    }

    
    /**
     *Creates the next combination of scaffolds, linkers and building blocks.
     *
     *@return Array of indices corresponding to the next combination of scaffolds, linkers and building blocks: [s,l,l,l,...,b,b,b,...]
     */
    public int[] next() {
        
        int numRGroups = numsRGroups[scaffoldIndex];
        
        //number of possible combinations for this scaffold
        long maxReactions = (long) Math.pow(numLinkers * numBBlocks, numRGroups); //calculate number of possible reactions
        
        // calculate length of the reaction ID array
        int numReactants = 1 + 2 * numRGroups;

        int[] virtualReaction = new int[numReactants];
        
        // scaffold
        long numReactions = maxReactions / 1;
        virtualReaction[0] = scaffoldIndex;
        
        // linkers
        for (int i = 0; i < numRGroups; i++) {
            numReactions /= numLinkers;
            virtualReaction[1 + i] = (int) (reactionIndex / numReactions) % numLinkers;
        }
        
        // building blocks
        for (int i = 0; i < numRGroups; i++) {
            numReactions /= numBBlocks;
            virtualReaction[1 + numRGroups + i] = (int) (reactionIndex / numReactions) % numBBlocks;
        }

        reactionIndex++;
        
        if (reactionIndex >= maxReactions) {
            reactionIndex = 0;
            scaffoldIndex++;
        }

        return virtualReaction;
    }

    /**Not supported*/
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported by this iterator.");
    }

    /**
     * Returns the number of molecules the iterator creates.
     * @return number of molecules to build
     */
    public long getMaximum() {
        long maxReactions = 0;
        
        for (int s = 0; s < numsRGroups.length; s++) {
            int numRGroups = numsRGroups[s];
            long temp = (long) Math.pow(numLinkers * numBBlocks, numRGroups);
            maxReactions += temp;
        }
        return maxReactions;
    }
}