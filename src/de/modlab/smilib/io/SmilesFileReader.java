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
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.fragments.Fragment;
import de.modlab.smilib.fragments.FragmentFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *Reads SMILES from the input files.
 *
 * @author Volker Haehnke
 * @author Andreas Schueller
 */
public class SmilesFileReader<E extends Fragment> implements SmilesReader<E> {
    
    /** reads the files */
    private BufferedReader buffr;
    
    /** preprocesses SMILES */
    private Preprocessor prePro;
    
    /** Creates Fragment from SMILES */
    private FragmentFactory fragmentFactory;
    
    /** checks SMILES for SmiLib conformity */
    private ConformityChecker smilesChecker;
    
    /** check conformity true/false */
    private boolean checkSmiles;
    
    /** indicates whether scaffold, linker or building block smiles are read */
    private int mode;
    
    
    
    /**
     *Creates a new instance of SmilesFileReader.
     *
     *@param fragmentFactory factory that creates fragments
     *@param checkSmiles check Smiles for SmiLib rule conformity true/false
     *@param mode indicates which kind of source SMILES (0 = scaffold, 1 = linker, 2 = building block) is read
     */
    public SmilesFileReader(FragmentFactory fragmentFactory, boolean checkSmiles, int mode) {
        this.fragmentFactory = fragmentFactory;
        this.checkSmiles = checkSmiles;
        this.mode = mode;
        smilesChecker = new ConformityChecker();
        prePro = new Preprocessor();
    }

    
    /**
   * Reads SMILES from source files.
   * @return List of objects corresponding to source SMILES
   * @param path path/name of source SMILES file
   * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
   * @throws de.modlab.smilib.exceptions.SmiLibConformityException thrown if a SMILES string does not conform to SmiLib restrictions
   */
    public List<E> readSmiles(String path) throws SmiLibIOException, SmiLibConformityException {
        ArrayList<E> tempList = new ArrayList<E>();
        try {
            buffr = new BufferedReader(new FileReader(path));
            String[] tempLine;
            String currentInputLine = "";
            boolean end = false;
            int lineCounter = 1;
            
            while (!end) {
                currentInputLine = buffr.readLine();
                if (currentInputLine != null) {
                    if (!currentInputLine.trim().equals("")) {
                        tempLine = currentInputLine.split("\t");
                        if (tempLine.length == 1) {
                            if (checkSmiles) {
                                smilesChecker.checkConformity(tempLine[0].trim(), mode);
                            }
                            tempList.add((E) fragmentFactory.createFragment(prePro.preprocessSmiles(tempLine[0].trim()), Integer.toString(lineCounter)));
                        } else {
                            if (checkSmiles) {
                                smilesChecker.checkConformity(tempLine[1].trim(), mode); 
                            }
                            tempList.add((E) fragmentFactory.createFragment(prePro.preprocessSmiles(tempLine[1].trim()), tempLine[0].trim()));
                        }
                        lineCounter++;
                    }
                } else
                    end = true;
            }
            buffr.close();
        } catch (java.io.IOException exc) {
            throw new SmiLibIOException(exc);
        }
        return tempList;
    }
}