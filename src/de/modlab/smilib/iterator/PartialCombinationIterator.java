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

import de.modlab.smilib.exceptions.ReactionSchemeException;
import de.modlab.smilib.exceptions.SmiLibException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 *Iterator for the molecules defined in a reaction scheme.
 *
 * @author Volker Haehnke
 * @author Andreas Schueller
 *
 */
public class PartialCombinationIterator implements SmiLibIterator {
    
    //numbers of variable side chains for the used scaffolds
    private int[] numsRGroups;
    
    //index of the current scaffold
    private int scaffoldIndex = 0;
    
    //index of the current reaction (counts from 0 to maxium)
    private int reactionIndex = 0;
    
    //index of the current combination scheme
    private int combinationSchemeIndex = 0;
    
    //reads reaction scheme
    private BufferedReader buffr;
    
    //list of CombinationScheme objects - which linkers/building
    //blocks shall be used on the variable side chains of a scaffold
    List<CombinationScheme> combSchemes;
    
    
    /**
   * Creates a new instance of PartialCombinationIterator
   * 
   * @param rSchemePath path/name of the file containing the reaction scheme
   * @param numsRGroups array of number of variable side chains for each scaffold
   * @param maxLinkers number of linkers available
   * @param maxBBlocks number of building blocks available
   * @throws de.modlab.smilib.exceptions.ReactionSchemeException thrown if the reaction scheme is badly defined
   * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
   * @throws de.modlab.smilib.exceptions.SmiLibException thrown if an unexpected error occurs
   */
    public PartialCombinationIterator(String rSchemePath, int[] numsRGroups, int maxLinkers, int maxBBlocks) throws ReactionSchemeException, SmiLibIOException, SmiLibException {
        this.numsRGroups = numsRGroups;
        this.combSchemes = new ArrayList<CombinationScheme>();
        this.readReactionScheme(rSchemePath, maxLinkers, maxBBlocks);
    }
    
    
    /**
     * Creates a new instance of PartialCombinationIterator
     * @param rScheme reaction scheme as string array
     * @param numsRGroups array of number of variable side chains for each scaffold
     * @param maxLinkers number of linkers available
     * @param maxBBlocks number of building blocks available
     * @throws java.lang.Exception Exception thrown if error occurs whil parsing reaction scheme
     */
    public PartialCombinationIterator(String[] rScheme, int[] numsRGroups, int maxLinkers, int maxBBlocks) throws Exception {
        this.numsRGroups = numsRGroups;
        this.combSchemes = new ArrayList<CombinationScheme>();
        this.readReactionScheme(rScheme, maxLinkers, maxBBlocks);
    }
    
    
    /**
     *Reads the reaction scheme from GUI.
     *
     *@param rScheme reaction scheme, each line is an own part of the array
     *@param maxLinkers number of linkers available
     *@param maxBBlocks number of building blocks available
     */
    private void readReactionScheme(String[] rScheme, int maxLinkers, int maxBBlocks) throws Exception {
        try {
            String currentInputLine;
            
            //line splitted by TAB => column
            String[] tempLine;
            
            //column splitted by ";" => range
            String[] tempColumn;
            
            //range splitted by "-" => start/end-index
            String[] tempRange;
            
            Integer[] tempScaffoldNumbers;
            Integer[] tempLinkerNumbers;
            Integer[] tempBBlockNumbers;
            CombinationScheme cScheme;
            
            for(int i = 0; i < rScheme.length; i++) {
                currentInputLine =rScheme[i];
                
                if (currentInputLine != null) {
                    tempLine = currentInputLine.split("\t");
                    
                    tempScaffoldNumbers = this.getValues(tempLine[0]);
                             
                    for (int t = 0; t < tempScaffoldNumbers.length; t++) {
                        
                         if (tempLine.length != 2 * numsRGroups[tempScaffoldNumbers[t]-1] + 1) {
                             throw new ReactionSchemeException("Invalid number of columns specified in the reaction scheme at line " + (i + 1) + ".");
                         }
                        
                        //ATTENTION: JAVA COUNTS FROM 0, BUT FILES START WITH LINE 1
                        cScheme = new CombinationScheme(tempScaffoldNumbers[t]-1, numsRGroups[tempScaffoldNumbers[t]-1]);
                        
                        //first column is scaffold, then follow linker/bb/linker/bb... - i count variable side chain
                        for (int j = 1; j < tempLine.length; j+=2) {
                            tempLinkerNumbers = this.getValues(tempLine[j]);
                            check(tempLinkerNumbers, maxLinkers);
                            tempBBlockNumbers = this.getValues(tempLine[j+1]);
                            check(tempBBlockNumbers, maxBBlocks);
                            
                            cScheme.addLinkers(tempLinkerNumbers);
                            cScheme.addBBlocks(tempBBlockNumbers);
                        }
                        this.combSchemes.add(cScheme);
                    }
                }
            }
            
            //sets scaffoldIndex to the first valid index
            scaffoldIndex = combSchemes.get(0).getScaffoldIndex();
            
        } catch (ReactionSchemeException e) {
            throw e;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new ReactionSchemeException("Wrong number of columns in your reaction scheme.\nPlease check reaction scheme and/or use SmiLib help!");
        } catch (java.lang.NumberFormatException e) {
            throw new ReactionSchemeException("Non numerical entry in the reaction scheme found.\nPlease check reaction scheme and/or use SmiLib help!");
        } catch (java.lang.Exception e) {
            throw new SmiLibException("Error while parsing the reaction scheme.\nPlease check reaction scheme and/or read SmiLib help!", e);
        }
    }
    
    
    /**
   * Reads the reaction scheme from source file.
   * 
   * 
   * @param rSchemePath path/name of the file containing the reaction scheme
   * @param maxLinkers number of linkers available
   * @param maxBBlocks number of building blocks available
   * @throws de.modlab.smilib.exceptions.ReactionSchemeException thrown if the reaction scheme is badly defined
   * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
   * @throws de.modlab.smilib.exceptions.SmiLibException thrown if an unexpected error occurs
   */
    private void readReactionScheme(String rSchemePath, int maxLinkers, int maxBBlocks) throws ReactionSchemeException, SmiLibIOException, SmiLibException {
        try {
            buffr = new BufferedReader(new FileReader(rSchemePath));
            String currentInputLine;
            
            //line splitted by TAB => column
            String[] tempLine;
            
            //column splitted by ";" => range
            String[] tempColumn;
            
            //range splitted by "-" => start/end-index
            String[] tempRange;
            
            Integer[] tempScaffoldNumbers;
            Integer[] tempLinkerNumbers;
            Integer[] tempBBlockNumbers;
            boolean end = false;
            CombinationScheme cScheme;
            
            while (!end) {
                currentInputLine = buffr.readLine();
                
                if (currentInputLine != null) {
                    tempLine = currentInputLine.trim().split("\t");
                    
                    tempScaffoldNumbers = this.getValues(tempLine[0]);
                    
                    for (int t = 0; t < tempScaffoldNumbers.length; t++) {
                        
                        if (tempLine.length != 2 * numsRGroups[tempScaffoldNumbers[t]-1] + 1) {
                             throw new ReactionSchemeException("Invalid number of columns specified in the reaction scheme");
                         }
                        
                        //ATTENTION: JAVA COUNTS FROM 0, BUT FILES START WITH LINE 1
                        cScheme = new CombinationScheme(tempScaffoldNumbers[t]-1, numsRGroups[tempScaffoldNumbers[t]-1]);
                        
                        //first column is scaffold, then follow linker/bb/linker/bb... - i counts variable side chain
                        for (int i = 1; i < tempLine.length; i+=2) {
                            tempLinkerNumbers = this.getValues(tempLine[i]);
                            check(tempLinkerNumbers, maxLinkers);
                            tempBBlockNumbers = this.getValues(tempLine[i+1]);
                            check(tempBBlockNumbers, maxBBlocks);
                            
                            cScheme.addLinkers(tempLinkerNumbers);
                            cScheme.addBBlocks(tempBBlockNumbers);
                        }
                        this.combSchemes.add(cScheme);
                    }
                } else
                    end = true;
            }
            buffr.close();
            
            //sets scaffoldIndex to the first valid index
            scaffoldIndex = combSchemes.get(0).getScaffoldIndex();
            
        } catch (ReactionSchemeException e) {
            throw e;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new ReactionSchemeException("Wrong number of columns in your reaction scheme.\nPlease check reaction scheme and/or use SmiLib help!");
        } catch (java.lang.NumberFormatException e) {
            throw new ReactionSchemeException("Non numerical entry in the reaction scheme found.\nPlease check reaction scheme and/or use SmiLib help!");
        } catch (java.io.IOException e) {
            throw new SmiLibIOException("Error while reading the reaction scheme source file.");
        } catch (java.lang.Exception e) {
            throw new SmiLibException("Error while parsing the reaction scheme.\nPlease check reaction scheme and/or read SmiLib help!", e);
        }
    }
    
    
    /**
     *Checks whether an array contains a value above a specified maximum.
     *If that's the case, an Exception is thrown.
     *
     *@param vaules array to be checked
     *@param max maximum for values in array
     */
    private void check(Integer[] values, int max) throws Exception{
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                throw new ReactionSchemeException("Invalid index used in the reaction scheme: " + values[i]);
            }
        }
    }
    
    
    /**
     *Gets Integer objectes for numbers used in String
     *where "-" defines a range between two numbers
     *and ";" separates ranges or descrete numbers.
     *
     *@param source String that defines numbers and ranges
     *@return Array of Integer objects corresponding to the numbers defined in source
     *@throws ReactionSchemeException if indices in reaction scheme define a negative range or at least one index is 0
     */
    private Integer[] getValues(String source) throws ReactionSchemeException {
        String[] tempFields;
        String[] tempRange;
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        tempFields = source.split(";");
        for (int i = 0; i < tempFields.length; i++) {
            
            if (tempFields[i].length() <= 0)
                throw new ReactionSchemeException("Invalid number range specified in the reaction scheme.\nPlease check reaction scheme and/or read SmiLib help!");
            
            tempRange = tempFields[i].split("-");

            if (tempRange.length == 1) {
                if (checkIndex(Integer.parseInt(tempRange[0]))) {
                    tempList.add(Integer.parseInt(tempRange[0]));
                } else {
                    throw new ReactionSchemeException("Invalid index in reaction scheme: " + tempRange[0]);
                }
            } else {
            
                if (tempRange[0].length() <= 0 || tempRange[1].length() <= 0)
                    throw new ReactionSchemeException("Invalid number range specified in the reaction scheme.\nPlease check reaction scheme and/or read SmiLib help!");

                if (checkIndices(Integer.parseInt(tempRange[0]), Integer.parseInt(tempRange[1]))) {
                    for (int k = Integer.parseInt(tempRange[0]); k <= Integer.parseInt(tempRange[1]); k++) {
                        tempList.add(k);
                    }
                } else {
                    throw new ReactionSchemeException("Invalid range in reaction scheme: " + tempRange[0] + "-" + tempRange[1]);
                }
            }
        }
        Integer[] returnArray = new Integer[tempList.size()];
        tempList.toArray(returnArray);
        return returnArray;
    }
    
    
    /**
     *Checks whether an index is at least 1.
     *
     *@param x index to check
     *@return x > 0 true/false
     */
    private boolean checkIndex(int x) {
        boolean returnBool = true;
        if (x < 1) {
            returnBool = false;
        }
        return returnBool;
    }
    
    
    /**
     *Checks whether two indices define a valid, positiv range and are both at least 1.
     *
     *@param x start index of range to check
     *@param y end index of range to check
     *@return indices define a positiv range and are both > 0 true/false
     */
    private boolean checkIndices(int x, int y) {
        boolean returnBool = true;
        if (x > y | !checkIndex(x) | !checkIndex(y)) {
            returnBool = false;
        }
        return returnBool;
    }
    
    
    /**
     *Returns whether there is one more combination of
     *scaffold/linkers/building blocks available or not.
     *
     *@return one more combination available true/false
     */
    public boolean hasNext() {
        return combinationSchemeIndex < combSchemes.size();
    }
    
    
    /**
     *Creates the next combination of scaffolds, linkers and building blocks.
     *
     *@return Array of indices corresponding to the next combination of scaffolds, linkers and building blocks: [s,l,l,l,...,b,b,b,...]
     */
    public int[] next() {
        scaffoldIndex = combSchemes.get(combinationSchemeIndex).getScaffoldIndex(); //neu
        
        int numRGroups = numsRGroups[scaffoldIndex];
        
        int[][] linkers = combSchemes.get(combinationSchemeIndex).getLinkers();
        int[][] bbs = combSchemes.get(combinationSchemeIndex).getBBlocks();
        
        //number of possible combinations with the given reaction scheme
        int maxReactions = 1;
        for (int i = 0; i < linkers.length; i++)
            maxReactions *= linkers[i].length;
        for (int i = 0; i < bbs.length; i++)
            maxReactions *= bbs[i].length;
        
        //length of the array to return
        int numReactants = 1 + (2*numRGroups);
        
        int numReactions = maxReactions / 1;
        
        //array to return
        int[] virtualReaction = new int[numReactants];
        virtualReaction[0] = scaffoldIndex;
        
        //linkers
        for (int i = 0; i < numRGroups; i++) {
            numReactions /= linkers[i].length;
            virtualReaction[1 + i] = linkers[i][(reactionIndex / numReactions) % linkers[i].length];
        }
        
        //building blocks
        for (int i = 0; i < numRGroups; i++) {
            numReactions /= bbs[i].length;
            virtualReaction[1 + linkers.length + i] = bbs[i][(reactionIndex / numReactions) % bbs[i].length];
        }
        
        reactionIndex++;
        
        if (reactionIndex >= maxReactions) {
            reactionIndex = 0;
            combinationSchemeIndex++;
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
        
        for (int s = 0; s < combSchemes.size(); s++) {
            int[][] linkers = combSchemes.get(s).getLinkers();
            int[][] bbs = combSchemes.get(s).getBBlocks();
            long temp = 1;
            for (int i = 0; i < linkers.length; i++)
                temp *= linkers[i].length;
            for (int i = 0; i < bbs.length; i++)
                temp *= bbs[i].length;
            maxReactions += temp;
        }
        
        return maxReactions;
    }
}