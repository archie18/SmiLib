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
 * SmiLibRunnerTest.java
 * JUnit based test
 *
 * Created on June 20, 2006, 10:50 AM
 */

package de.modlab.smilib.main;

import de.modlab.smilib.io.SmilesListWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import junit.framework.*;

/**
 *
 * @author Andreas Schueller
 */
public class SmiLibRunnerTest extends TestCase {
  
  public SmiLibRunnerTest(String testName) {
    super(testName);
  }
  
  protected void setUp() throws Exception {
  }
  
  protected void tearDown() throws Exception {
    File outFile = new File("./out.txt");
    if (outFile.exists())
      outFile.delete();
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(SmiLibRunnerTest.class);
    
    return suite;
  }
  
  /**
   * Test of constructor, of class de.modlab.smilib.main.SmiLibRunner.
   */
  public void testSmiLibRunner() {
    System.out.println("testSmiLibRunner");

    try {
      SmiLibRunner instance = new SmiLibRunner(TestConstants.twoScaffolds, TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, false, "./out.txt", false, true);
    } catch (Exception e) {
      e.printStackTrace();
      fail("An unexpected exception was thrown: " + e.getMessage());
    }

    System.out.println("Provoking an error...");
    try {
      SmiLibRunner instance = new SmiLibRunner("", TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, false, "./out.txt", false, true);
    } catch (Exception e) {
      e.printStackTrace();
      fail("An unexpected exception was thrown: " + e.getMessage());
    }
  }

  /**
   * Test of constructor, of class de.modlab.smilib.main.SmiLibRunner, for use
   * in external Java projects
   */
  public void testSmiLibRunnerList() {
    System.out.println("testSmiLibRunnerList");

    // Load expected smiles
    List<String> expectedSmiles = null;
    try {
      expectedSmiles = TestUtils.readFile(TestConstants.twoThreeFourCompleteEnumerationSmiles);
    } catch (IOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    // Generate smiles
    SmilesListWriter smiWri = new SmilesListWriter();
    SmiLibRunner instance = new SmiLibRunner(TestConstants.twoScaffoldsSmiles, TestConstants.threeLinkersSmiles, TestConstants.fourBuildingBlocksSmiles, null, true, smiWri);
    instance.run();
    List<String> smiles = smiWri.getSmilesList();

    // Assertion
    assertEquals("Library size", expectedSmiles.size(), smiles.size());
    
    System.out.println("Converting SMILES with OpenBabel...");
    for (int i = 0; i < expectedSmiles.size(); i++) {
      String convertedSmiles = null;
      String convertedExpectedSmiles = null;
      try {
        convertedSmiles = TestUtils.convertWithOpenBabelToInChI(smiles.get(i).split("\t")[1]);
        convertedExpectedSmiles = TestUtils.convertWithOpenBabelToInChI(expectedSmiles.get(i));
      } catch (Exception ex) {
        ex.printStackTrace();
        fail("An error converting with openbabel occurred: " + ex.getMessage());
      }
      assertEquals(i + ": " + convertedExpectedSmiles + " == " + convertedSmiles, convertedExpectedSmiles, convertedSmiles);
    }
    
  }
  
  /**
   * Test of run method, of class de.modlab.smilib.main.SmiLibRunner.
   */
  public void testRun() {
    System.out.println("run");
    
    SmiLibRunner instance = new SmiLibRunner(TestConstants.twoScaffolds, TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, false, "./out.txt", false, true);
    instance.run();
    //TestUtils.sleep(1000);
    List<String> smiles = null;
    List<String> expectedSmiles = null;
    try {
      smiles = TestUtils.readFile("./out.txt");
      expectedSmiles = TestUtils.readFile(TestConstants.twoThreeFourCompleteEnumerationSmiles);
    } catch (IOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }
    
    assertEquals("Library size", expectedSmiles.size(), smiles.size());
    
    System.out.println("Converting SMILES with OpenBabel...");
    for (int i = 0; i < expectedSmiles.size(); i++) {
      String convertedSmiles = null;
      String convertedExpectedSmiles = null;
      try {
        convertedSmiles = TestUtils.convertWithOpenBabelToInChI(smiles.get(i).split("\t")[1]);
        convertedExpectedSmiles = TestUtils.convertWithOpenBabelToInChI(expectedSmiles.get(i));
      } catch (Exception ex) {
        ex.printStackTrace();
        fail("An error converting with openbabel occurred: " + ex.getMessage());
      }
      assertEquals(i + ": " + convertedExpectedSmiles + " == " + convertedSmiles, convertedExpectedSmiles, convertedSmiles);
    }

  }
  
  /**
   * Test of setStop method, of class de.modlab.smilib.main.SmiLibRunner.
   */
  public void testSetStop() {
    System.out.println("setStop");
    System.out.println("Test not yet implemented.");
  }
  
  /**
   * Test of handleException method, of class de.modlab.smilib.main.SmiLibRunner.
   */
  public void testHandleException() {
    System.out.println("handleException");
    
    Throwable thr = null;
    SmiLibRunner instance = null;
    
//        instance.handleException(thr);
    
    // TODO review the generated test code and remove the default call to fail.
    System.out.println("Test not yet implemented.");
  }
  
}
