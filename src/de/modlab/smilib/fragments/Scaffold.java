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
import java.util.Arrays;
import java.util.List;

/**
 *Stores SMILES and informations about a scaffold.
 *
 * @author Volker Haehnke
 */
public class Scaffold extends Fragment{
    
    //number of variable side chains in the scaffold
    private int                 numberOfRGroups;
    
    //numbers used in the attachement points of variable side chains
    private int[]               rGroupNumbers;
    
    //symbols that are allowed in a part of the scaffold SMILES that represents a variable side chain
    private char[]             symbolsAllowedInSMILIBGroup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'R', '[', ']'};
    
    //parts of the scaffold SMILES used in the attachement points of variable side chains
    private List<String>    rGroupStrings;
    
    
    
    /**
     *Creates a new instance of Scaffold.
     *
     *@param scaff scaffold SMILES
     *@param id scaffold ID
     */
    public Scaffold(String scaff, String id) {
        super(scaff, id);
        getRGroupsFromScaffold(scaff);
    }
    
    
    /**
     *Extracts the parts of the scaffold SMILES that represent the attachement points
     *of the variable side chains. They are stored, and the used numbers get
     *sorted and stored separately.
     *
     *@param scaffold scaffold as SMILES
     */
    private void getRGroupsFromScaffold(String scaffold) {
        int currentPosition = 0;
        int bracketOpen = 0;
        int bracketClose = 0;
        boolean rGroupInBrackets;
        this.rGroupStrings = new ArrayList<String>();
        
        //until the end of the SMILES is reached
        while (currentPosition < scaffold.length()) {
            //find '[''
            if (scaffold.charAt(currentPosition) != '[')
                currentPosition++;
            else {
                //index of '[' is stored
                bracketOpen = currentPosition;
                currentPosition++;
                rGroupInBrackets = true;
                
                //find the corresponding ']'
                while (scaffold.charAt(currentPosition) != ']') {
                    //if an invalid symbol is between the brackets, it can not be a variable side chain
                    if (!match(scaffold.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
                        rGroupInBrackets = false;
                    }
                    currentPosition++;                    
                }
                
                //index of ']' is stored
                bracketClose = currentPosition;
                
                //if a variable side chain is between the brackets
                if (rGroupInBrackets) {
                    //maybe it is between "(...)" - store the part of the SMILES
                    if (scaffold.charAt(bracketOpen-1) == '(' && scaffold.charAt(bracketClose+1) == ')') {
                        rGroupStrings.add(scaffold.substring(bracketOpen-1, bracketClose+2));
                    } else {
                        rGroupStrings.add(scaffold.substring(bracketOpen, bracketClose+1));
                    }
                }
            }
        }
        numberOfRGroups = this.rGroupStrings.size();
        
        rGroupNumbers = new int[numberOfRGroups];
        
        String temp;
        for (int i = 0; i < numberOfRGroups; i++) {
            temp = rGroupStrings.get(i);
            rGroupNumbers[i] = getNumberFromGroup(temp);
        }
        java.util.Arrays.sort(rGroupNumbers);
    }
    
    
    /**
     *Gets the number used in an attachement point of a variable side chain.
     *
     *@param t attachement point of the variable side chain
     *@returns number used in the attachement point as int
     */
    private int getNumberFromGroup(String t) {
        int returnInt = 0;
        if (t.startsWith("([")) {
            returnInt = Integer.parseInt(t.substring(3, t.length()-2));
        } else {
            returnInt = Integer.parseInt(t.substring(2, t.length()-1));
        }
        return returnInt;
    }
    
    
    /**
     *Returns how many variable side chains are in this scaffold.
     *
     *@return number of variable side chains
     */
    public int getNumberOfRGroups() {
        return this.numberOfRGroups;
    }
    
    
    /**
     *Returns the string corresponding to the nth variable side chain.
     *
     *@param n index of the variable side chain
     *@return part of the SMILES that represents the nth variable side chain
     */
    public String getStringOfGroupWithIndex(int n) {
        String returnString = "";
        int indexOfNumberToFind = n;
        
        for (int i = 0; i < numberOfRGroups; i++) {
            if (getNumberFromGroup(rGroupStrings.get(i)) == rGroupNumbers[indexOfNumberToFind]) {
                returnString = rGroupStrings.get(i);
            }
        }
        return returnString;
    }
    
    
    /**
     *Tests whether a given char is in an array or not.
     *
     *@param x char to look for
     *@param chars array to search for x
     *@returns whether <code>x</code> is containes in <code>chars</code> or not.
     */
    private boolean match(char x, char[] chars) {
        boolean returnBool = false;
        int temp = Arrays.binarySearch(chars, x);
        if ((temp > -1) && (chars[temp] == x)) {
            returnBool = true;
        }
        return returnBool;
    }
}