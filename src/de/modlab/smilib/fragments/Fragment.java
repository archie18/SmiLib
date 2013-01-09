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

import java.util.ArrayList;
import java.util.List;


/**
 *Abstract superclass for all fragments.
 *
 * @author Volker Haehnke
 */
public class Fragment {
    
    //original SMILES of the fragment
    private String originalSMILES;
    
    //ringnumbers already used in the original SMILES
    private List<Integer> blacklistedNumbers;
    
    //ID of the fragment - name or index in source file
    private String id;
    
    
    
    /**
     * Creates a new instance of Fragment
     * @param originalSMILES original fragment SMILES
     * @param id fragment ID - identifier or index in source file
     */
    public Fragment(String originalSMILES, String id) {
        this.originalSMILES = originalSMILES;
        this.id = id;
        this.blacklistNumbers(this.originalSMILES);
    }
    
    
    /**
     *Puts all two digit ring numbers already used in the
     *fragment on a blacklist, so that they will not be used during
     *the enumeration, when this specific fragment is used.
     */
    private void blacklistNumbers(String sourceSMILES) {
        StringBuilder temp = new StringBuilder(sourceSMILES);
        this.blacklistedNumbers = new ArrayList<Integer>();
        
        //gets the index of the first % sign, that is a prefix to all two digit ring numbers
        int numbIndex = temp.indexOf("%");
        int currentNumber;
        
        //as long as there are two digit ring numbers
        while (numbIndex != -1) {
            //extract number from string
            currentNumber = Integer.parseInt(temp.substring(numbIndex+1, numbIndex+3));
            
            //if this number is not already blacklisted
            if (!this.blacklistedNumbers.contains(currentNumber)) {
                //put it on the blacklist
                this.blacklistedNumbers.add(currentNumber);
            }
            
            //delte the part of the string that has already been processed
            temp.delete(0, temp.indexOf("%")+3);
            numbIndex = temp.indexOf("%");
        }
    }
    
    
    /**
     *Returns whether a number is blacklisted or not.
     *
     *@param i number that has to be checked
     *@return <code>true</code> if <code>i</code> is blacklisted,
     *<code>false</code> if not.
     */
    public boolean numberBlacklisted(int i) {
        boolean returnBool = false;
        if (this.blacklistedNumbers.contains(i)) {
            returnBool = true;
        }
        return returnBool;
    }
    
    
    /**
     *Returns the original SMILES String of the fragment.
     *
     *@return original SMILES of the fragment.
     */
    public String getOriginalSMILES() {
        return this.originalSMILES;
    }
    
    
    /**
     *Returns id of the fragment.
     *
     *@return id of the fragment.
     */
    public String getID() {
        return this.id;
    }
}