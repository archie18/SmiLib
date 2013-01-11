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
import de.modlab.smilib.main.TestConstants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 * JUnit test of class SmilesListWriter
 * @author andreas
 */
public class SmilesListWriterTest extends TestCase {
    
    public SmilesListWriterTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of close method, of class SmilesListWriter.
     */
    public void testClose() throws Exception {
        System.out.println("close");
        SmilesListWriter instance = new SmilesListWriter();
        instance.close();
    }

    /**
     * Test of writeSMILES method, of class SmilesListWriter.
     */
    public void testWriteSMILES() throws Exception {
        System.out.println("writeSMILES");
        SmilesListWriter instance = new SmilesListWriter();
        for (int i = 0; i < TestConstants.twoThreeFourValidReactionSchemeSmiles.length; i++) {
            StringBuilder smiles = new StringBuilder();
            StringBuilder id = new StringBuilder();
            smiles.append(TestConstants.twoThreeFourValidReactionSchemeSmiles[i]);
            id.append(i);
            instance.writeSMILES(smiles, id);
        }
        List<String[]> smilesList = instance.getSmilesList();
        
        // Assert equal size
        assertEquals("SmilesList not of expected size", TestConstants.twoThreeFourValidReactionSchemeSmiles.length, smilesList.size());
        
        // Assert content
        for (int i = 0; i < TestConstants.twoThreeFourValidReactionSchemeSmiles.length; i++) {
            String expectedId = String.valueOf(i);
            String expectedSmiles = TestConstants.twoThreeFourValidReactionSchemeSmiles[i];
            String actualId = smilesList.get(i)[0];
            String actualSmiles = smilesList.get(i)[1];
            assertEquals("Assert equal ID", expectedId, actualId);
            assertEquals("Assert equal SMILES", expectedSmiles, actualSmiles);
        }
    }

    /**
     * Test of showPreview method, of class SmilesListWriter.
     */
    public void testShowPreview() {
        System.out.println("showPreview");
        int i = 0;
        SmilesListWriter instance = new SmilesListWriter();
        try {
            instance.showPreview(i);
            fail("Expected exception 'UnsupportedOperationException' not thrown.");
        } catch (UnsupportedOperationException ex) {
            // All good
        }
    }

    /**
     * Test of getSmilesList method, of class SmilesListWriter.
     */
    public void testGetSmilesList() {
        System.out.println("getSmilesList");
        SmilesListWriter instance = new SmilesListWriter();
        for (int i = 0; i < TestConstants.twoThreeFourValidReactionSchemeSmiles.length; i++) {
            try {
                StringBuilder smiles = new StringBuilder();
                StringBuilder id = new StringBuilder();
                smiles.append(TestConstants.twoThreeFourValidReactionSchemeSmiles[i]);
                id.append(i);
                instance.writeSMILES(smiles, id);
            } catch (Exception ex) {
                fail("Unexpected exception thrown: " + ex);
            }
        }
        List<String[]> smilesList = instance.getSmilesList();
        
        // Assert equal size
        assertEquals("SmilesList not of expected size", TestConstants.twoThreeFourValidReactionSchemeSmiles.length, smilesList.size());
        
        // Assert content
        for (int i = 0; i < TestConstants.twoThreeFourValidReactionSchemeSmiles.length; i++) {
            String expectedId = String.valueOf(i);
            String expectedSmiles = TestConstants.twoThreeFourValidReactionSchemeSmiles[i];
            String actualId = smilesList.get(i)[0];
            String actualSmiles = smilesList.get(i)[1];
            assertEquals("Assert equal ID", expectedId, actualId);
            assertEquals("Assert equal SMILES", expectedSmiles, actualSmiles);
        }
    }
}
