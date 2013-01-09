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
 *Stores SMILES and informations about a linker.
 *
 * @author Volker Haehnke
 */
public class Linker extends Fragment{
    
    //linker is empty linker true/false
    private boolean empty;
    
    //index, where the two digit ring number has to be inserted in the modified SMILES
    private int ringNumberInsertionIndex;
    
    //linker SMILES without the [R]- or [R1]-group
    private String modifiedLinkerSMILES;
    
    
    
    /**
     *Creates a new Instance of Linker.
     *
     *@param linker linker SMILES
     *@param id linker ID
     */
    public Linker(String linker, String id) {
        super(linker, id);
        empty = this.linkerEmpty(linker);
        this.prepareLinkerForConcat(linker);
    }
    
    
    /**
     *Returns wthether this linker is an empty linker or not.
     *
     *@param link linker SMILES
     *@returns <code>true</code> if this linker is empty, <code>false</code> if not.
     */
    private boolean linkerEmpty(String link) {
        boolean returnBool = false;
        if (    link.equals("[A][R1]") ||
                link.equals("[R1][A]") ||
                link.equals("[A]([R1])") ||
                link.equals("[R1]([A])") ||
                link.equals("([A])[R1]") ||
                link.equals("([R1])[A]") ||
                link.equals("([A])([R1])") ||
                link.equals("([R1])([A])") ||
                link.equals("[A][R]") ||
                link.equals("[R][A]") ||
                link.equals("[A]([R])") ||
                link.equals("[R]([A])") ||
                link.equals("([A])[R]") ||
                link.equals("([R])[A]") ||
                link.equals("([A])([R])") ||
                link.equals("([R])([A])")  ) {
            returnBool = true;
        }
        
        return returnBool;
    }
    
    
    /**
     *Removes the [R]- or [R1]-group from the linker SMILES.
     *
     *@param link linker SMILES
     */
    private void prepareLinkerForConcat(String link) {
        StringBuilder temp = new StringBuilder(link);
        
        if (temp.indexOf("[R1]") != -1) {
            int caseOfR = temp.indexOf("([R1])");
            if (caseOfR == -1) {                                                    //"([R1])" not in building block SMILES
                ringNumberInsertionIndex = temp.indexOf("[R1]");
                temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 4);
            } else {                                                                //"([R1])" in building block SMILES
                ringNumberInsertionIndex = caseOfR;
                temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 6);
            }
        } else {                                                              //ABSCHNITT FUER [R] IN LINKER - WIRD BISHER VOM PREPROCESSING NICHT UNTERSTUETZT
            int caseOfR = temp.indexOf("([R])");
            if (caseOfR == -1) {                                                    //"([R])" not in building block SMILES
                ringNumberInsertionIndex = temp.indexOf("[R]");
                temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 4);
            } else {                                                                //"([R])" in building block SMILES
                ringNumberInsertionIndex = caseOfR;
                temp.delete(ringNumberInsertionIndex, ringNumberInsertionIndex + 5);
            }
        }
        this.modifiedLinkerSMILES = temp.toString();
    }
    
    
    /**
     *Inserts the two digit ring number in the modified SMILES and returns the SMILES.
     *
     *@param i two digit ring number
     *@return linker SMILES ready for concatenation
     */
    public String getLinkerForConcat(int i) {
        StringBuilder temp = new StringBuilder(modifiedLinkerSMILES);
        temp.insert(ringNumberInsertionIndex, "%" + i);
        return temp.toString();
    }
    
    
    /**
     *Returns whether this linker is empty or not.
     *
     *@return <code>true</code> if this linker is empty, <code>false</code> if not
     */
    public boolean isEmpty() {
        return this.empty;
    }
}
