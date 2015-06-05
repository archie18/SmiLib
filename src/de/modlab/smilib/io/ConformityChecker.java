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

import de.modlab.smilib.exceptions.SmiLibConformityException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *Checks whether source SMILES are SmiLib conform.
 *
 * @author Volker Haehnke
 */
public class ConformityChecker {
    
    //symbols that are allowed in a part of the scaffold SMILES that represents a variable side chain
    private char[]             symbolsAllowedInSMILIBGroup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'R', '[', ']'};
    
    //symbols indicating a ring closure
    private char[]             ringSymbols = {'%', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    //symbols indicating connection to an other atom by atom symbol
    private char[]             atomSymbols = {'*', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'c', 'n', 'o', 's', 'r', 'l'};
    
    //symbols indicating the start of another atom
    private char[]             atomStartSymbols = {'(', '[', '*', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'c', 'n', 'o', 's'};
    
    //symbols indicating an explicit single bond
    private char[]             singleBondSymbols = {'-', '/', '\\'};
    
    
    
    /** Creates a new instance of ConformityChecker */
    public ConformityChecker() {
    }
    
    
    /**
     * Checks conformity of a given SMILES.
     * @param smiles source SMILES
     * @param mode 0: scaffold, 1: linker, 2: building block
     * @throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    public void checkConformity(String smiles, int mode) throws SmiLibConformityException {
        this.checkBrackets(smiles);
        this.checkForEZIsomerie(smiles);
        this.checkForNecessaryGroups(smiles, mode);
        this.checkBondCount(smiles, mode);
    }
    
    private void checkBrackets(String smiles) throws SmiLibConformityException {
        int     numberOfRoundBracketsOpen = 0;
        int     numberOfSquaredBracketsOpen = 0;
        
        for (int i = 0; i < smiles.length(); i++) {
            if (smiles.charAt(i) == '(') {
                numberOfRoundBracketsOpen++;
            } else if (smiles.charAt(i) == ')') {
                numberOfRoundBracketsOpen--;
            } else if (smiles.charAt(i) == '[') {
                numberOfSquaredBracketsOpen++;
            } else if (smiles.charAt(i) == ']') {
                numberOfSquaredBracketsOpen--;
            }
            if (numberOfRoundBracketsOpen < 0) {
                throw new SmiLibConformityException("Invalid use of brackets in SMILES " + smiles);
            }
            if (numberOfSquaredBracketsOpen < 0) {
                throw new SmiLibConformityException("Invalid use of brackets in SMILES " + smiles);
            }
        }
        
        if (numberOfRoundBracketsOpen != 0) {
            throw new SmiLibConformityException("Unequal numbers of \"(\" and \")\" in SMILES " + smiles);
        }
        if (numberOfSquaredBracketsOpen != 0) {
            throw new SmiLibConformityException("Unequal numbers of \"[\" and \"]\" in SMILES " + smiles);
        }
    }
    
    
    /**
     *Checks whether a SMILES contains EZ isomerie information
     *and throws an exception if that's the case - EZ Isomerie can't
     *be handled by SmiLib at the moment.
     *@param smiles source SMILES
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void checkForEZIsomerie(String smiles) throws SmiLibConformityException {
        if (smiles.indexOf("/") != -1 | smiles.indexOf("\\") != -1)
            throw new SmiLibConformityException("Information regarding ez isomerie found in SMILES\n" + smiles +
                    "\nUp to this version, SmiLib can't handle this kind of isomerie.\n" +
                    "If you want to allow start of enumeration deactivate\n\"Options /check SMILES\"");
    }
    
    
    /**
     *Checks smiles for necessary SmiLib specific groups.
     *
     *@param smiles source SMILES
     *@param mode 0: scaffold, 1: linker, 2: building block
     *@throws SmiLibConformityException thrown if SMILES does not contain necessary groups
     */
    private void checkForNecessaryGroups(String smiles, int mode) throws SmiLibConformityException {
        switch (mode) {
            
            //scaffold
            case 0:
                findAndCountRGroups(smiles, true);
                break;
                
                //linker
            case 1:
                findAGroup(smiles);
                findAndCountRGroups(smiles, false);
                break;
                
                //building block
            case 2:
                findAGroup(smiles);
                break;
        }
    }
    
    
    /**
     *Checks bond count on SmiLib specific groups
     *
     *@param smiles source SMILES
     *@param mode 0: scaffold, 1: linker, 2: building block
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void checkBondCount(String smiles, int mode) throws SmiLibConformityException {
        // Check bond count for each dot-disconnected part
        String[] parts = smiles.split("\\.");
        if (parts.length == 0) {
            parts = new String[]{ smiles };
        }
        for (String part: parts) {
            switch (mode) {

                //scaffold
                case 0:
                    this.checkBondCountForRGroups(part);
                    break;

                //linker
                case 1:
                    this.checkBondCountForRGroups(part);
                    this.checkBondCountForAGroup(part);
                    break;

                //building block
                case 2:
                    this.checkBondCountForAGroup(part);
            }
        }
    }
    
    
    /**
     *Searches for an [A] group in source SMILES. Throws Exception if none is found.
     *
     *@param smiles source SMILES
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void findAGroup(String smiles) throws SmiLibConformityException {
        StringBuffer temp = new StringBuffer(smiles);
        
        int aGroupIndex = temp.indexOf("[A]");
        
        if (aGroupIndex == -1) {
            throw new SmiLibConformityException("No attachement side found in SMILES " + smiles);
        } else {
            temp.delete(0, aGroupIndex+3);
            aGroupIndex = temp.indexOf("[A]");
            if (aGroupIndex != -1) {
                throw new SmiLibConformityException("More than one attachement side found in SMILES " + smiles);
            }
        }
    }
    
    
    /**
     *Searches for [R] group in source SMILES. Throws Exception if none is found.
     *Checks whether they use following numbers. Throws Exception if they don't.
     *
     *@param smiles source SMILES
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void findAndCountRGroups(String smiles, boolean processScaffold) throws SmiLibConformityException {
        StringBuilder temp = new StringBuilder(smiles);
        
        //source SMILES contains variable chain true/false
        boolean foundRGroup = false;
        
        //all numbers used for [R...] follow each other starting with 1
        boolean allNumbersUsed = true;
        
        //this substring starting with [R is a variable chain true/false
        boolean thisIsRGroup = true;
        
        //number sused in [R...]
        ArrayList<Integer> rNumbers = new ArrayList<Integer>();
        
        //possible start of first [R...]
        int currentPosition = temp.indexOf("[R");
        
        //start index of [R...]
        int rGroupStart;
        
        //end index of [R...]
        int rGroupEnd;
        
        //scaffold is processed
        if (processScaffold) {
            //for the whole string
            while (currentPosition != -1) {

                foundRGroup = true;
                rGroupStart = currentPosition;

                //find indices for [ and ]
                while (temp.charAt(currentPosition) != ']') {

                    //if an invalid symbol is between the brackets, it can not be a variable side chain
                    if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
                        thisIsRGroup = false;
                    }

                    currentPosition++;
                }

                if (thisIsRGroup) {
                    foundRGroup = true;
                }

                rGroupEnd = currentPosition;

                //if its a variable chain, store number
                if (foundRGroup) {
                    int number = getNumberFromGroup(temp.substring(rGroupStart, rGroupEnd));
                    rNumbers.add(number);
                }

                temp.delete(0, rGroupEnd);

                //find index of next [R]group
                currentPosition = temp.indexOf("[R");
            }

            //index of group possible missing
            int missingGroup = 0;

            //check whether all numbers starting with 1 are used
            if (foundRGroup) {
                for (int i = 1; i <= rNumbers.size(); i++) {
                    if (!rNumbers.contains(i)) {
                        allNumbersUsed = false;
                        missingGroup = i;
                    }
                }
            }

            //if no variable chain was found - throw exception because there is no sense in using this string in SmiLib
            if (!foundRGroup) {
                throw new SmiLibConformityException("No variable chain found in scaffold SMILES " + smiles);
            }

            //if not all numbers are used correctly - throw exception because it feels good to be hard :-)
            if (!allNumbersUsed) {
                throw new SmiLibConformityException("Error in SmiLIb specific groups in scaffold SMILES " + smiles + "\nMissing group no: " + missingGroup + "\nYou have to use following numbers starting from 1!");
            }
            
        //linker is processed
        } else {
            //if no valid linker string is included, exception is thrown
            if (temp.indexOf("[R]") == -1 & temp.indexOf("[R1]") == -1) {
                throw new SmiLibConformityException("No variable chain found in linker SMILES " + smiles);
            }
        }
    }
    
    
    /**
     *Gets the number used in an attachement point of a variable side chain.
     *
     *@param t attachement point of the variable side chain
     *@returns number used in the attachement point as int
     */
    private int getNumberFromGroup(String t) {
        return Integer.parseInt(t.substring(2, t.length()));
    }
    
    
    /**
     *Checks bond count to [R] groups in source SMILES. Throws Exception if
     *it contains one with a count > 1
     *
     *@param smiles source SMILES
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void checkBondCountForRGroups(String smiles) throws SmiLibConformityException {
        int currentPosition = 0;
        int bracketOpen = 0;
        int bracketClose = 0;
        int groupLength = 0;
        boolean rGroupInBrackets;
        
        //until the end of the SMILES is reached
        while (currentPosition < smiles.length()) {
            //find '[''
            if (smiles.charAt(currentPosition) != '[')
                currentPosition++;
            else {
                //index of '[' is stored
                bracketOpen = currentPosition;
                currentPosition++;
                rGroupInBrackets = true;
                
                //find the corresponding ']'
                while (smiles.charAt(currentPosition) != ']') {
                    //if an invalid symbol is between the brackets, it can not be a variable side chain
                    if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
                        rGroupInBrackets = false;
                    }
                    currentPosition++;
                }
                
                //index of ']' is stored
                bracketClose = currentPosition;
                
                //if a variable side chain is between the brackets
                if (rGroupInBrackets) {
                    int index = bracketOpen;
                    groupLength = bracketClose - bracketOpen + 1;   //eventuell +1
                    
                    boolean smilesStartsWithGroup = true;
                    if (index != 0) {
                        smilesStartsWithGroup = false;
                    }
                    
                    boolean smilesEndsWithGroup = true;
                    if (index != smiles.length()-groupLength) {
                        smilesEndsWithGroup = false;
                    }
                    
                    //direct double or triple bond
                    if (smilesStartsWithGroup) {
                        if (    smiles.charAt(index+groupLength) == '=' ||
                                smiles.charAt(index+groupLength) == '#') {
                            throw new SmiLibConformityException("Illegal bond type used in connection to [R] in SMILES\n" + smiles);
                        }
                    } else if (smilesEndsWithGroup) {
                        if (    smiles.charAt(index-1) == '=' ||
                                smiles.charAt(index-1) == '#') {
                            throw new SmiLibConformityException("Illegal bond type used in connection to [R] in SMILES\n" + smiles);
                        }
                    } else {
                        if (    smiles.charAt(index-1) == '=' ||
                                smiles.charAt(index-1) == '#' ||
                                smiles.charAt(index+groupLength) == '=' ||
                                smiles.charAt(index+groupLength) == '#') {
                            throw new SmiLibConformityException("Illegal bond type used in connection to [R] in SMILES\n" + smiles);
                        }
                    }
                    
                    //ring closure => 2 bonds
                    if (!smilesEndsWithGroup) {
                        if (    match(smiles.charAt(index+groupLength), ringSymbols)) {
                            throw new SmiLibConformityException("More than one atom connected to [R] because of ring closure in SMILES\n" + smiles);
                        }
                    }
                    
                    //two direct bonds to implicit to atoms, specified by '-' or to side chains with (...)
                    if (!smilesStartsWithGroup && !smilesEndsWithGroup) {
                        if (    (match(smiles.charAt(index-1), atomSymbols) && match(smiles.charAt(index+groupLength), atomSymbols)) ||
                                (match(smiles.charAt(index-1), atomSymbols) && match(smiles.charAt(index+groupLength), singleBondSymbols)) ||
                                (match(smiles.charAt(index-1), atomSymbols) && smiles.charAt(index+groupLength) == '(') ||
                                (match(smiles.charAt(index-1), atomSymbols) && smiles.charAt(index+groupLength) == '[') ||
                                
                                (match(smiles.charAt(index-1), singleBondSymbols) && match(smiles.charAt(index+groupLength), atomSymbols)) ||
                                (match(smiles.charAt(index-1), singleBondSymbols) && match(smiles.charAt(index+groupLength), singleBondSymbols)) ||
                                (match(smiles.charAt(index-1), singleBondSymbols) && smiles.charAt(index+groupLength) == '(') ||
                                (match(smiles.charAt(index-1), singleBondSymbols) && smiles.charAt(index+groupLength) == '[') ||
                                
                                (smiles.charAt(index-1) == ')' && match(smiles.charAt(index+groupLength), atomSymbols)) ||
                                (smiles.charAt(index-1) == ')' && match(smiles.charAt(index+groupLength), singleBondSymbols)) ||
                                (smiles.charAt(index-1) == ')' && smiles.charAt(index+groupLength) == '(') ||
                                (smiles.charAt(index-1) == ')' && smiles.charAt(index+groupLength) == '[') ||
                                
                                (smiles.charAt(index-1) == '(' && match(smiles.charAt(index+groupLength), atomSymbols)) ||
                                (smiles.charAt(index-1) == '(' && match(smiles.charAt(index+groupLength), singleBondSymbols)) ||
                                (smiles.charAt(index-1) == '(' && smiles.charAt(index+groupLength) == '(') ||
                                (smiles.charAt(index-1) == '(' && smiles.charAt(index+groupLength) == '[') ||
                                
                                (smiles.charAt(index-1) == ']' && match(smiles.charAt(index+groupLength), atomSymbols)) ||
                                (smiles.charAt(index-1) == ']' && match(smiles.charAt(index+groupLength), singleBondSymbols)) ||
                                (smiles.charAt(index-1) == ']' && smiles.charAt(index+groupLength) == '(') ||
                                (smiles.charAt(index-1) == ']' && smiles.charAt(index+groupLength) == '[')) {
                            throw new SmiLibConformityException("More than one atom connected to [R] in SMILES\n" + smiles);
                        }
                    // Check for cases like this: [R1](F)CC --> invalid
                    } else if (!smilesEndsWithGroup) {
                        int parenthesisEndIndex;
                        if (    smiles.charAt(index + groupLength) == '(' &&                                                  // If there is an opening parenthesis after the R-group
                                (parenthesisEndIndex = smiles.indexOf(')', index + groupLength)) != -1 &&                     // find the position of closing parenthesis,
                                parenthesisEndIndex + 1 < smiles.length() &&                                                  // make it's not at the end minus one of the string
                                match(smiles.charAt(parenthesisEndIndex + 1), atomStartSymbols)) {                            // and if there is an atom start symbol following the closing parenthesis
                            throw new SmiLibConformityException("More than one atom connected to [R] in SMILES\n" + smiles);  // then the R-group is connected to more than one atom.
                        }
                    }
                }
            }
        }
    }
    
    
    /**
     *Checks bond count to [A] groups in source SMILES. Throws Exception if
     *it contains one with a count > 1
     *
     *@param smiles source SMILES
     *@throws SmiLibConformityException thrown if SMILES is not conform to SmiLib restrictions
     */
    private void checkBondCountForAGroup(String smiles) throws SmiLibConformityException {
        StringBuilder temp = new StringBuilder(smiles);
        int index = temp.indexOf("[A]");
        
        boolean smilesStartsWithGroup = true;
        if (index != 0) {
            smilesStartsWithGroup = false;
        }
        
        boolean smilesEndsWithGroup = true;
        if (index != temp.length()-3) {
            smilesEndsWithGroup = false;
        }
        
        //direct double or triple bond
        if (smilesStartsWithGroup) {
            if (    temp.charAt(index+3) == '=' ||
                    temp.charAt(index+3) == '#') {
                throw new SmiLibConformityException("Illegal bond type used in connection to [A] in SMILES\n" + smiles);
            }
        } else if (smilesEndsWithGroup) {
            if (    temp.charAt(index-1) == '=' ||
                    temp.charAt(index-1) == '#') {
                throw new SmiLibConformityException("Illegal bond type used in connection to [A] in SMILES\n" + smiles);
            }
        } else {
            if (    temp.charAt(index-1) == '=' ||
                    temp.charAt(index-1) == '#' ||
                    temp.charAt(index+3) == '=' ||
                    temp.charAt(index+3) == '#') {
                throw new SmiLibConformityException("Illegal bond type used in connection to [A] in SMILES\n" + smiles);
            }
        }
        
        //ring closure => 2 bonds
        if (!smilesEndsWithGroup) {
            if (    match(temp.charAt(index+3), ringSymbols)) {
                throw new SmiLibConformityException("More than one atom connected to [A] because of ring closure in SMILES\n" + smiles);
            }
        }
        
        //two direct bonds to implicit to atoms, specified by '-' or to side chains with (...)
        if (!smilesStartsWithGroup & !smilesEndsWithGroup) {
            if (    (match(temp.charAt(index-1), atomSymbols) & match(temp.charAt(index+3), atomSymbols)) ||
                    (match(temp.charAt(index-1), atomSymbols) & match(temp.charAt(index+3), singleBondSymbols)) ||
                    (match(temp.charAt(index-1), atomSymbols) & temp.charAt(index+3) == '(') ||
                    (match(temp.charAt(index-1), atomSymbols) & temp.charAt(index+3) == '[') ||
                    
                    (match(temp.charAt(index-1), singleBondSymbols) & match(temp.charAt(index+3), atomSymbols)) ||
                    (match(temp.charAt(index-1), singleBondSymbols) & match(temp.charAt(index+3), singleBondSymbols)) ||
                    (match(temp.charAt(index-1), singleBondSymbols) & temp.charAt(index+3) == '(') ||
                    (match(temp.charAt(index-1), singleBondSymbols) & temp.charAt(index+3) == '[') ||
                    
                    (smiles.charAt(index-1) == '(' & match(smiles.charAt(index+3), atomSymbols)) ||
                    (smiles.charAt(index-1) == '(' & match(smiles.charAt(index+3), singleBondSymbols)) ||
                    (smiles.charAt(index-1) == '(' & smiles.charAt(index+3) == '(') ||
                    (smiles.charAt(index-1) == '(' & smiles.charAt(index+3) == '[') ||
                    
                    (temp.charAt(index-1) == ')' & match(temp.charAt(index+3), atomSymbols)) ||
                    (temp.charAt(index-1) == ')' & match(temp.charAt(index+3), singleBondSymbols)) ||
                    (temp.charAt(index-1) == ')' & temp.charAt(index+3) == '(') ||
                    (temp.charAt(index-1) == ')' & temp.charAt(index+3) == '[') ||
                    
                    (temp.charAt(index-1) == ']' & match(temp.charAt(index+3), atomSymbols)) ||
                    (temp.charAt(index-1) == ']' & match(temp.charAt(index+3), singleBondSymbols)) ||
                    (temp.charAt(index-1) == ']' & temp.charAt(index+3) == '(') ||
                    (temp.charAt(index-1) == ']' & temp.charAt(index+3) == '[')) {
                throw new SmiLibConformityException("More than one atom connected to [A] in SMILES\n" + smiles);
            }
        // Check for cases like this: [R1](F)CC --> invalid
        } else if (!smilesEndsWithGroup) {
            int parenthesisEndIndex;
            if (    smiles.charAt(index + 3) == '(' &&                                                            // If there is an opening parenthesis after the A-group
                    (parenthesisEndIndex = smiles.indexOf(')', index + 3)) != -1 &&                               // find the position of closing parenthesis,
                    parenthesisEndIndex + 1 < smiles.length() &&                                                  // make it's not at the end minus one of the string
                    match(smiles.charAt(parenthesisEndIndex + 1), atomStartSymbols)) {                            // and if there is an atom start symbol following the closing parenthesis
                throw new SmiLibConformityException("More than one atom connected to [A] in SMILES\n" + smiles);  // then the A-group is connected to more than one atom.
            }
        }
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
