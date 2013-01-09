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

import de.modlab.smilib.exceptions.SmiLibException;
import de.modlab.smilib.exceptions.SmiLibIOException;

/**
 *Interface used by all SmiLib SmilesWriters.
 *
 * @author Volker Haehnke
 */
public interface SmilesWriter {
  
  /**
   * Closes the SmilesWriter.
   * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
   */
  public void close() throws SmiLibIOException;
  
  
  /**
   * Writes a SMILES string.
   * 
   * 
   * @param smiles SMILES string to write
   * @param id molecule ID
   * @throws de.modlab.smilib.exceptions.SmiLibException if an error occurs
   * @throws de.modlab.smilib.exceptions.SmiLibIOException thrown if an IO error occurs
   */
  public void writeSMILES(StringBuilder smiles, StringBuilder id) throws SmiLibIOException, SmiLibException;
  
  
  /**
   *Sets whether a preview shall be shown and how many molecules are shown in preview.
   *
   *@param i number of molecules in preview
   */
  public void showPreview(int i);
}