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

/*
 * ConformityCheckerTest.java
 * JUnit based test
 *
 * Created on 29. Juni 2006, 19:02
 */

package de.modlab.smilib.io;

import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.main.TestConstants;
import de.modlab.smilib.main.TestUtils;
import junit.framework.*;
import java.util.List;

/**
 *
 * @author Andreas Schueller
 */
public class ConformityCheckerTest extends TestCase {
  
  public ConformityCheckerTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
    
  }

  protected void tearDown() throws Exception {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(ConformityCheckerTest.class);
    
    return suite;
  }

  /**
   * Test of checkConformity method, of class de.modlab.smilib.io.ConformityChecker.
   */
  public void testCheckConformity() throws Exception {
    System.out.println("checkConformity");
    
    ConformityChecker instance = new ConformityChecker();
    
    List<String> scaffolds = TestUtils.readFile(TestConstants.nonconformScaffolds);
    for (String smiles: scaffolds) {
      try {
        instance.checkConformity(smiles, 0);
        fail("SmiLibConformityException was not thrown for nonconform scaffold SMILES: " + smiles);
      } catch (SmiLibConformityException e) {
        // all good
      }
    }
    
    List<String> linkers = TestUtils.readFile(TestConstants.nonconformLinkers);
    for (String smiles: linkers) {
      try {
        instance.checkConformity(smiles, 1);
        fail("SmiLibConformityException was not thrown for nonconform linker SMILES: " + smiles);
      } catch (SmiLibConformityException e) {
        // all good
      }
    }
    
    List<String> buildingBlocks = TestUtils.readFile(TestConstants.nonconformBuildingBlocks);
    for (String smiles: buildingBlocks) {
      try {
        instance.checkConformity(smiles, 2);
        fail("SmiLibConformityException was not thrown for nonconform building block SMILES: " + smiles);
      } catch (SmiLibConformityException e) {
        // all good
      }
    }
  }
  
  /**
   * Ensures that proper exceptions are thrown with uneven numbers of brackets
   */
  public void testCheckConformityBrackets() throws Exception {
    System.out.println("checkConformity uneven number of brackets");
    
    ConformityChecker conformityChecker = new ConformityChecker();
    
    // Scaffolds
    try {
        conformityChecker.checkConformity("[R1]C1CCC(CC1", 0);
        conformityChecker.checkConformity("[R1]C1CCC[CC1", 0);
        conformityChecker.checkConformity("[R1]C1CCC)CC1", 0);
        conformityChecker.checkConformity("[R1]C1CCC]CC1", 0);
        fail("SmiLibConformityException was not thrown for a SMILES string with uneven number of brackets.");
    } catch (SmiLibConformityException e) {
        // all good
    }
    
    // Linkers
    try {
        conformityChecker.checkConformity("[R1]C1CCC(CC1[A]", 1);
        conformityChecker.checkConformity("[R1]C1CCC[CC1[A]", 1);
        conformityChecker.checkConformity("[R1]C1CCC)CC1[A]", 1);
        conformityChecker.checkConformity("[R1]C1CCC]CC1[A]", 1);
        fail("SmiLibConformityException was not thrown for a SMILES string with uneven number of brackets.");
    } catch (SmiLibConformityException e) {
        // all good
    }
    
    // Building blocks
    try {
        conformityChecker.checkConformity("C1CCC(CC1[A]", 2);
        conformityChecker.checkConformity("C1CCC[CC1[A]", 2);
        conformityChecker.checkConformity("C1CCC)CC1[A]", 2);
        conformityChecker.checkConformity("C1CCC]CC1[A]", 2);
        fail("SmiLibConformityException was not thrown for a SMILES string with uneven number of brackets.");
    } catch (SmiLibConformityException e) {
        // all good
    }
  
  }

  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
  
}
