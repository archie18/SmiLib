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

package de.modlab.smilib.main;

/**
 *Concatenates SMILES for SmiLib.
 *
 * @author Volker Haehnke
 */
public class SmilesConcatenator {
    
    
    
    /**
     *Creates a new instance of SmilesConcatenator
     */
    public SmilesConcatenator() {
    }
    
    
    /**
     *Concatenates linker and building block to an intermediate,
     *using a char to separate them. Doesn't need to return something
     *because StringBuilder isn't final.
     *
     *@param intermediate StringBuilder that contains the linker and after the concatenation the intermediate as SMILES
     *@param separator char that will be inserted between linker and building block
     *@param block String that contains the building block as SMILES
     */
    public void concatenate(StringBuilder intermediate, char separator, String block) {
        intermediate.append(separator).append(block);
    }
    
    
    /**
     *Concatenates an intermediate (linker/building block) to the current scaffold,
     *using a char to separate them. Doesn't need to return something
     *because StringBuilder isn't final.
     *
     *@param currentMolecule StringBuilder that contains the current (maybe already modified) scaffold as SMILES
     *@param intermediate StringBuilder that contains the intermediate (linker/building block) to attache to the scaffold as SMILES
     *@param separator char that will be inserted between scaffold and intermediate
     *@param currentRGroup part of the current scaffold SMILES that marks the attachement point for the current intermediate
     *@param ringNumber number that will be used for the "ring" to concatenate scaffold and intermediate
     */
    public void concatenate(StringBuilder currentMolecule, StringBuilder intermediate, char separator, String currentRGroup, int ringNumber) {
        
        //necessary for forming the correct rings
        StringBuilder temp = new StringBuilder().append('%').append(ringNumber);
        
        //-1 if [A] has nor () around
        int caseOfA = intermediate.indexOf("([A])");
        
        //if [A] is not in round brackets
        if (caseOfA == -1) {
            intermediate.replace(
                    intermediate.indexOf("[A]"),
                    intermediate.indexOf("[A]") + 3,
                    temp.toString());
        } else {
            intermediate.replace(
                    intermediate.indexOf("([A])"),
                    intermediate.indexOf("([A])") + 5,
                    temp.toString());
        }
        
        currentMolecule.replace(
                currentMolecule.indexOf(currentRGroup),
                currentMolecule.indexOf(currentRGroup) + currentRGroup.length(),
                temp.toString());
        
        currentMolecule.append(separator).append(intermediate);
    }
    
    
    /**
     *Concatenates a building block to the current scaffold,
     *using a char to separate them. Doesn't need to return something
     *because StringBuilder isn't final.
     *
     *@param currentMolecule StringBuilder that contains the current (maybe already modified) scaffold as SMILES
     *@param block contains the building block to attache to the scaffold as SMILES
     *@param separator char that will be inserted between scaffold and building block
     *@param currentRGroup part of the current scaffold SMILES that marks the attachement point for the current building block
     *@param ringNumber number that will be used for the "ring" to concatenate scaffold and intermediate
     */
    public void concatenate(StringBuilder currentMolecule, String block, char separator, String currentRGroup, int ringNumber) {
        StringBuilder temp = new StringBuilder().append('%').append(ringNumber);
        
        currentMolecule.replace(
                currentMolecule.indexOf(currentRGroup),
                currentMolecule.indexOf(currentRGroup) + currentRGroup.length(),
                temp.toString()).append(separator).append(block);
    }
}