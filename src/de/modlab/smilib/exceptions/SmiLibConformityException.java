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

package de.modlab.smilib.exceptions;

/**
 *SmiLib specific Exception. Thrown when SMILES are not
 *conform to SMiLib restrictions.
 *
 * @author Volker Haehnke
 */
public class SmiLibConformityException extends SmiLibException {
    
    /**
     * Creates a new instance of <code>SmiLibConformityException</code> without detail message.
     */
    public SmiLibConformityException() {
    }
    
    
    /**
     * Constructs an instance of <code>SmiLibConformityException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public SmiLibConformityException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>SmiLibException</code> with the specified cause.
     * 
     * 
     * @param cause another exception (throwable).
     */
    public SmiLibConformityException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs an instance of <code>SmiLibConformityException</code> with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause another exception (throwable).
     */
    public SmiLibConformityException(String message, Throwable cause) {
        super(message, cause);
    }
}
