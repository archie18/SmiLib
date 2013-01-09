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

import java.util.Arrays;

/**
 *Preprocesses SMILES so that they can be used by SmiLib.
 *
 * @author Volker Haehnke
 */
public class Preprocessor {
    
    //symbols that indicate the begin of a new atom; needs to be sorted as by Arrays.sort()
    private char[]      atomClosingSymbols = {'#', '(', '*', '/', '=', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', 'c'};
    
    //symbols allowed in a [R]- or [A]-group; needs to be sorted as by Arrays.sort()
    private char[]      symbolsAllowedInSMILIBGroup = {'%', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'R', '[', ']'};
    
    //lower case symbols; needs to be sorted as by Arrays.sort()
    private char[]      lowerCaseSymbols = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r' ,'s', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    
    //string that is returned after preprocessing
    private String      returnSmiles = "";
    
    //temp buffer
    private StringBuilder temp;
    
    
    
    /**
     *Creates a new instance of Preprocessor.
     */
    public Preprocessor() {
    }
    
    
    /**
     *Performs preprocessing.
     *
     *@param sourceSmiles SMILES to be preprocessed
     *@return preprocessed SMILES
     */
    public String preprocessSmiles(String sourceSmiles) {
        returnSmiles = removeUnnecessarySymbols(sourceSmiles);
        
        if (    !returnSmiles.equals("[A][R1]") &               //dummy linker does not get preprocessed
                !returnSmiles.equals("[R1][A]") &
                !returnSmiles.equals("[A]([R1])") &
                !returnSmiles.equals("[R1]([A])") &
                !returnSmiles.equals("([A])[R1]") &
                !returnSmiles.equals("([R1])[A]") &
                !returnSmiles.equals("([A])([R1])") &
                !returnSmiles.equals("([R1])([A])") &
                !returnSmiles.equals("[A][R]") &
                !returnSmiles.equals("[R][A]") &
                !returnSmiles.equals("[A]([R])") &
                !returnSmiles.equals("[R]([A])") &
                !returnSmiles.equals("([A])[R]") &
                !returnSmiles.equals("([R])[A]") &
                !returnSmiles.equals("([A])([R])") &
                !returnSmiles.equals("([R])([A])")  ) {
            returnSmiles = performPreprocessing1(returnSmiles);
            returnSmiles = performPreprocessing2(returnSmiles);
        }
        return returnSmiles;
    }
    
    
    /**
     *Removes the unnecessary symbol '-' representing a single bond
     *from source SMILES string.
     *
     *@param smiles original source SMILES
     *@return molecule SMILES without '-' representing a single bond
     */
    private String removeUnnecessarySymbols(String smiles) {
        StringBuilder temp = new StringBuilder(smiles);
        
        int end = temp.length();
        int currentPosition = 0;
        boolean inBracket = false;
        
        while (currentPosition < end) {
            switch (temp.charAt(currentPosition)) {
                case '[':   inBracket = true;
                break;
                case ']':   inBracket = false;
                break;
                case '-':
                    //delete only if it is a single bond
                    if (!inBracket) {
                        temp.deleteCharAt(currentPosition);
                        end = temp.length();
                        
                        //necessary because deltion and incrementation at the same time would skip one char in SMILES
                        currentPosition--;
                    }
                    break;
            }
            currentPosition++;
        }
        
        return temp.toString();
    }
    
    
    /**
     * Modifies SMILES not to start with a SmiLibGroup
     * If SMILES starts with a site of variability like [R1] or [A]
     * the SMILES is modified the way that the SmiLibGroup switches position with the next atom.<br>
     * Example:<br>
     * <tt>[R1]CC -> C([R1])C</tt><br><br>
     * This preprocessing is necessary in order to have all SmiLibGroups being preceded by atoms only.
     */
    private String performPreprocessing1(String smi) {
        if ((smi.startsWith("[R") & !smi.startsWith("[Rb") &                            //SMILES starts with variable chain [R...]
                !smi.startsWith("[Ra") & !smi.startsWith("[Rf") &                       //and [R...] is not part of an element
                !smi.startsWith("[Re") & !smi.startsWith("[Ru") &
                !smi.startsWith("[Rh") & !smi.startsWith("[Rn")) ||
                (smi.startsWith("[A]"))) {                                              //or SMILES starts with attachment side[A]
            
            int currentPosition = 0;                                                        //reset of global variables
            int firstAtomStart = 0;
            int firstAtomEnd = 0;
            
            while (smi.charAt(currentPosition) != ']') {                                //SmiLib-Group is skipped
                currentPosition++;
            }
            
            currentPosition++;                                                          //first position after SmiLib-Group
            firstAtomStart = currentPosition;                                           //position where the atom starts, that will become first atom of the SMILES
            
            if (smi.charAt(currentPosition) == '[') {                                   //maybe the first atom has some information like chirality etc., so it could be  in [...]
                while (smi.charAt(currentPosition) != ']') {                                //the corresponding "]" will be found
                    currentPosition++;
                }
                firstAtomEnd = currentPosition+1;                                           //substring() is exclusive endIndex - so we need one more
            } else {                                                                    //the new first atom is a normal atom, so it has max. 2 digits and maybe ringinformation
                currentPosition++;                                                      //index where next atom starts
                while (currentPosition < smi.length() &&                                //start of the next atom will be found
                        !match(smi.charAt(currentPosition),atomClosingSymbols)) {
                    currentPosition++;
                }
                firstAtomEnd = currentPosition;                                         //index, whre next atom ends (remember: endIndex is exclusive!)
            }
            temp = new StringBuilder();
            temp.append(smi.substring(firstAtomStart, firstAtomEnd)).append('(').append(smi.substring(0, firstAtomStart)).append(')').append(smi.substring(firstAtomEnd, smi.length()));
            smi = temp.toString();
        }
        return smi;
    }
    
    
    /**
     * Preprocessing: Modifies SMILES to have no R/A-group following a bracket
     *
     * If an atom in SMILES branches R/A-group is possible to be preceded by a closing branching bracked.
     * If so the R-group is moved to directly succeed the atom.<br>
     * Example:<br>
     * <tt>C(C)[R1] -> C([R1])(C)</tt><br><br>
     * This preprocessing is necessary in order to have all R-groups being preceded by atoms only.
     */
    private String performPreprocessing2(String smi) {
        int currentPosition = 0;
        int roundBracketOpen = 0;
        int roundBracketClose = 0;
        int squareBracketOpen = 0;
        int squareBracketClose = 0;
        boolean rGroupInBrackets = true;
        
        while (currentPosition < smi.length()) {
            
            if (smi.charAt(currentPosition) != '[')
                currentPosition++;
            else {
                squareBracketOpen = currentPosition;
                currentPosition++;
                
                while (smi.charAt(currentPosition) != ']') {
                    if (!match(smi.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
                        rGroupInBrackets = false;
                    }
                    currentPosition++;
                }
                
                squareBracketClose = currentPosition;
                
                if (rGroupInBrackets && smi.charAt(squareBracketOpen-1) == ')') {
                    roundBracketClose = squareBracketOpen - 1;
                    currentPosition = squareBracketOpen - 2;
                    
                    int roundBracketsToOpen = 1;
                    while (roundBracketsToOpen > 0) {
                        currentPosition--;
                        if (smi.charAt(currentPosition) == ')') {
                            roundBracketsToOpen++;
                        } else if (smi.charAt(currentPosition) == '(') {
                            roundBracketsToOpen--;
                        }
                    }
                    
                    roundBracketOpen = currentPosition;
                    
                    temp = new StringBuilder();
                    temp.append(smi.substring(0, roundBracketOpen)).append('(').append(smi.substring(squareBracketOpen, squareBracketClose+1)).append(')').append(smi.substring(roundBracketOpen, roundBracketClose+1)).append(smi.substring(squareBracketClose+1, smi.length()));
                    smi = temp.toString();
                    
                    currentPosition = squareBracketClose + 2;
                }
            }
        }
        return smi;
    }
    
    
    /**
     *Tests whether a given char is in an array or not.
     *
     *@param x char to look for
     *@param chars array to search for x
     *@returns true if <code>x</code> is contained in <code>chars</code>, otherwise false.
     */
    private boolean match(char x, char[] chars) {
        boolean returnBool = false;
        int temp = Arrays.binarySearch(chars, x);
        if (temp >= 0 && chars[temp] == x) { // NovAliX bug-fix: it should be "temp >= 0", but was "temp > 0"
            returnBool = true;
        }
        return returnBool;
    }
}
//
//
//    
//    
//    private String ezPrepro(String smiles) {
//        StringBuffer temp = new StringBuffer(smiles);
//        boolean ezProcessable = true;
//        int currentDoubleBond;
//        
//        currentDoubleBond = temp.indexOf("=");
//        if (currentDoubleBond > -1) {
//            ezProcessable = ezProcessable(temp, currentDoubleBond);
//        }
//        
//        while (currentDoubleBond > -1 & ezProcessable) {
//            int ezCase = determinCase(temp, currentDoubleBond);
//            if (ezCase != -1) {
//                
//            }
//            
//            //find next double bond and check whether this SMILES is processable or not
//            currentDoubleBond = temp.indexOf("=", currentDoubleBond+1);
//            if (currentDoubleBond > -1) {
//                ezProcessable = ezProcessable(temp, currentDoubleBond);
//            }
//        }
//        return "";
//    }
//    
//    
//    private boolean rGroupInvolved(StringBuffer smiles, int doubleBond) {
//        boolean stop = false;
//        boolean involved = false;
//        
//        int rCase = -1;
//        
//        int currentPosition = doubleBond - 2;
//        
//        
//        //search left
//        while (!stop) {
//            if (smiles.charAt(currentPosition) == ']') {
//                boolean foundGroup = true;
//                //find corresponding brace
//                while (smiles.charAt(currentPosition) != '[') {
//                    if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
//                        foundGroup = false;
//                    }
//                    currentPosition--;
//                }
//                
//                if (foundGroup) {
//                    involved = true;
//                    rCase = 1;
//                }
//            } else {
//                
//                //skip some atoms
//                while (smiles.charAt(currentPosition) != '/' & smiles.charAt(currentPosition) != '\\') {
//                    currentPosition--;
//                }
//                if (smiles.charAt(currentPosition) == ']') {
//                    boolean foundGroup = true;
//                    //find corresponding brace
//                    while (smiles.charAt(currentPosition) != '[') {
//                        if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
//                            foundGroup = false;
//                        }
//                        currentPosition--;
//                    }
//                    
//                    if (foundGroup) {
//                        involved = true;
//                        rCase = 0;
//                    }
//                    stop = true;
//                } else {
//                    stop = true;
//                }
//            }
//        }
//        
//        //search right
//        if (!involved) {
//            currentPosition = doubleBond + 3;
//            while (!stop) {
//                if (smiles.charAt(currentPosition) == '[') {
//                    boolean foundGroup = true;
//                    //find corresponding brace
//                    while (smiles.charAt(currentPosition) != '[') {
//                        if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
//                            foundGroup = false;
//                        }
//                        currentPosition++;
//                    }
//                    
//                    if (foundGroup) {
//                        involved = true;
//                        rCase = 2;
//                    }
//                } else {
//                    
//                    //skip some atoms
//                    while (smiles.charAt(currentPosition) != '/' & smiles.charAt(currentPosition) != '\\') {
//                        currentPosition++;
//                    }
//                    if (smiles.charAt(currentPosition) == '[') {
//                        boolean foundGroup = true;
//                        //find corresponding brace
//                        while (smiles.charAt(currentPosition) != ']') {
//                            if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
//                                foundGroup = false;
//                            }
//                            currentPosition++;
//                        }
//                        
//                        if (foundGroup) {
//                            involved = true;
//                            rCase = 3;
//                        }
//                        stop = true;
//                    } else {
//                        stop = true;
//                    }
//                }
//            }
//        }
//        
//        return involved;
//    }
//        
//        
//        
//        private boolean ezProcessable(StringBuffer smiles, int doubleBond) {
//            boolean returnBool = true;
//            boolean rGroupInvolved;
//            int currentPosition = doubleBond-1;
//            
//            //(...) include atom with [...]
//            if (smiles.charAt(currentPosition) == ']') {
//                //find corresponding brace
//                while (smiles.charAt(currentPosition) != '[') {
//                    if (!match(smiles.charAt(currentPosition), symbolsAllowedInSMILIBGroup)) {
//                        rGroupInvolved = false;
//                    }
//                    currentPosition--;
//                }
//                //only 1 Atom (the one in braces) is allowed, maybe with a single bond preceding - nothing else
//                if ((smiles.charAt(currentPosition-1) != '(') &
//                        (smiles.charAt(currentPosition-1) == '/' & smiles.charAt(currentPosition-2) != '(') &
//                        (smiles.charAt(currentPosition-1) == '\\' & smiles.charAt(currentPosition-2) != '(')) {
//                    returnBool = false;
//                }
//                
//                //e.g. (.../Cl) is only allowed if "..." is a single bond preceded by "("
//            } else if (match(smiles.charAt(currentPosition), lowerCaseSymbols)) {
//                if ((smiles.charAt(currentPosition-2) != '(') &
//                        (smiles.charAt(currentPosition-2) == '/' & smiles.charAt(currentPosition-3) != '(') &
//                        (smiles.charAt(currentPosition-2) == '\\' & smiles.charAt(currentPosition-3) != '(')) {
//                    returnBool = false;
//                }
//                
//                //e.g. (...C) is only allowed if "..." is a single bond preceded by "("
//            } else {
//                if ((smiles.charAt(currentPosition-1) != '(') &
//                        (smiles.charAt(currentPosition-1) == '/' & smiles.charAt(currentPosition-2) != '(') &
//                        (smiles.charAt(currentPosition-1) == '\\' & smiles.charAt(currentPosition-2) != '(')) {
//                    returnBool = false;
//                }
//            }
//            
//            return returnBool;
//        }
//        
//        private int determinCase(StringBuffer smiles, int doubleBond) {
//            //-1 for "no r group involved"
//            int returnInt = -1;
//            
//            
//            
//            return returnInt;
//        }