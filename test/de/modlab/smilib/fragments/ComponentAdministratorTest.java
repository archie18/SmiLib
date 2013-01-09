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
 * ComponentAdministratorTest.java
 * JUnit based test
 *
 * Created on 29. Juni 2006, 16:18
 */

package de.modlab.smilib.fragments;

import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.main.TestConstants;
import java.util.Arrays;
import junit.framework.*;

/**
 *
 * @author Andreas Schueller
 */
public class ComponentAdministratorTest extends TestCase {
  
  private ComponentAdministrator componentAdministrator;
  
  public ComponentAdministratorTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
    componentAdministrator = new ComponentAdministrator(TestConstants.twoScaffolds, TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, true);
  }

  protected void tearDown() throws Exception {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(ComponentAdministratorTest.class);
    
    return suite;
  }
  
  /**
   * Test of the constructor of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testComponentAdministrator() {
    System.out.println("componentAdministrator");

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator("", TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, true);
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException ex) {
      // all good
    } catch (SmiLibConformityException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    }

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator(TestConstants.twoScaffolds, "", TestConstants.fourBuildingBlocks, true);
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException ex) {
      // all good
    } catch (SmiLibConformityException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    }

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator(TestConstants.twoScaffolds, TestConstants.threeLinkers, "", true);
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException ex) {
      // all good
    } catch (SmiLibConformityException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    }

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator(TestConstants.nonconformScaffolds, TestConstants.threeLinkers, TestConstants.fourBuildingBlocks, true);
      fail("SmiLibConformityException was not thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    } catch (SmiLibConformityException ex) {
      // all good
    }

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator(TestConstants.twoScaffolds, TestConstants.nonconformLinkers, TestConstants.fourBuildingBlocks, true);
      fail("SmiLibConformityException was not thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    } catch (SmiLibConformityException ex) {
      // all good
    }

    try {
      ComponentAdministrator componentAdministrator = new ComponentAdministrator(TestConstants.twoScaffolds, TestConstants.threeLinkers, TestConstants.nonconformBuildingBlocks, true);
      fail("SmiLibConformityException was not thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception.");
    } catch (SmiLibConformityException ex) {
      // all good
    }
  }

  /**
   * Test of numberBlacklisted method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testNumberBlacklisted() {
    System.out.println("numberBlacklisted");
    
    boolean expResult = true;
    boolean result = componentAdministrator.numberBlacklisted(10, 0, 0, 0);
    assertEquals("%10 already used in first scaffold? (=true)", expResult, result);
    
    expResult = false;
    result = componentAdministrator.numberBlacklisted(10, 1, 0, 0);
    assertEquals("%10 already used in second scaffold? (=false)", expResult, result);
  }

  /**
   * Test of getNumberOfScaffolds method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetNumberOfScaffolds() {
    System.out.println("getNumberOfScaffolds");
    
    int expResult = 2;
    int result = componentAdministrator.getNumberOfScaffolds();
    assertEquals(expResult, result);
  }

  /**
   * Test of getNumberOfLinkers method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetNumberOfLinkers() {
    System.out.println("getNumberOfLinkers");
    
    int expResult = 3;
    int result = componentAdministrator.getNumberOfLinkers();
    assertEquals(expResult, result);
  }

  /**
   * Test of getNumberOfBuildingBlocks method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetNumberOfBuildingBlocks() {
    System.out.println("getNumberOfBuildingBlocks");
    
    int expResult = 4;
    int result = componentAdministrator.getNumberOfBuildingBlocks();
    assertEquals(expResult, result);
  }

  /**
   * Test of getScaffold method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetScaffold() {
    System.out.println("getScaffold");
    
    String expResult1 = TestConstants.twoScaffoldsSmiles[0];
    String expResult2 = TestConstants.twoScaffoldsSmiles[1];

    Scaffold result1 = componentAdministrator.getScaffold(0);
    assertEquals(expResult1, result1.getOriginalSMILES());

    Scaffold result2 = componentAdministrator.getScaffold(1);
    assertEquals(expResult2, result2.getOriginalSMILES());
  }

  /**
   * Test of getLinker method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetLinker() {
    System.out.println("getLinker");
    
    String expResult1 = TestConstants.threeLinkersSmiles[0];
    String expResult2 = TestConstants.threeLinkersSmiles[1];
    String expResult3 = TestConstants.threeLinkersSmiles[2];

    Linker result1 = componentAdministrator.getLinker(0);
    assertEquals(expResult1, result1.getOriginalSMILES());

    Linker result2 = componentAdministrator.getLinker(1);
    assertEquals(expResult2, result2.getOriginalSMILES());

    Linker result3 = componentAdministrator.getLinker(2);
    assertEquals(expResult3, result3.getOriginalSMILES());
  }

  /**
   * Test of getBuildingBlock method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetBuildingBlock() {
    System.out.println("getBuildingBlock");
    
    String expResult1 = TestConstants.fourBuildingBlocksSmiles[0];
    String expResult2 = TestConstants.fourBuildingBlocksSmiles[1];
    String expResult3 = TestConstants.fourBuildingBlocksSmiles[2];
    String expResult4 = TestConstants.fourBuildingBlocksSmiles[3];

    BuildingBlock result1 = componentAdministrator.getBuildingBlock(0);
    assertEquals(expResult1, result1.getOriginalSMILES());

    BuildingBlock result2 = componentAdministrator.getBuildingBlock(1);
    assertEquals(expResult2, result2.getOriginalSMILES());

    BuildingBlock result3 = componentAdministrator.getBuildingBlock(2);
    assertEquals(expResult3, result3.getOriginalSMILES());

    BuildingBlock result4 = componentAdministrator.getBuildingBlock(3);
    assertEquals(expResult3, result3.getOriginalSMILES());
  }

  /**
   * Test of getScaffoldID method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetScaffoldID() {
    System.out.println("getScaffoldID");
    
    String expResult = "1";
    String result = componentAdministrator.getScaffoldID(0);
    assertEquals(expResult, result);
    
    expResult = "2";
    result = componentAdministrator.getScaffoldID(1);
    assertEquals(expResult, result);
  }

  /**
   * Test of getLinkerID method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetLinkerID() {
    System.out.println("getLinkerID");
    
    String expResult = "1";
    String result = componentAdministrator.getLinkerID(0);
    assertEquals(expResult, result);
    
    expResult = "2";
    result = componentAdministrator.getLinkerID(1);
    assertEquals(expResult, result);
    
    expResult = "3";
    result = componentAdministrator.getLinkerID(2);
    assertEquals(expResult, result);
  }

  /**
   * Test of getBuildingBlockID method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetBuildingBlockID() {
    System.out.println("getBuildingBlockID");
    
    String expResult = "1";
    String result = componentAdministrator.getBuildingBlockID(0);
    assertEquals(expResult, result);
    
    expResult = "2";
    result = componentAdministrator.getBuildingBlockID(1);
    assertEquals(expResult, result);
    
    expResult = "3";
    result = componentAdministrator.getBuildingBlockID(2);
    assertEquals(expResult, result);
    
    expResult = "4";
    result = componentAdministrator.getBuildingBlockID(3);
    assertEquals(expResult, result);
  }

  /**
   * Test of getScaffoldString method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetScaffoldString() {
    System.out.println("getScaffoldString");
    
    String expResult1 = TestConstants.twoScaffoldsSmiles[0]; // "c%10([R1])ccccc%10[R2]";
    String expResult2 = TestConstants.twoScaffoldsSmiles[1]; // "C([R1])NCC([R2])O[R3]";

    String result1 = componentAdministrator.getScaffoldString(0);
    assertEquals(expResult1, result1);

    String result2 = componentAdministrator.getScaffoldString(1);
    assertEquals(expResult2, result2);
  }

  /**
   * Test of getNumberOfRGroups method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetNumberOfRGroups() {
    System.out.println("getNumberOfRGroups");
    
    int expResult = TestConstants.twoThreeFourNumRGroups[0];
    int result = componentAdministrator.getNumberOfRGroups(0);
    assertEquals(expResult, result);
    
    expResult = TestConstants.twoThreeFourNumRGroups[1];
    result = componentAdministrator.getNumberOfRGroups(1);
    assertEquals(expResult, result);
  }

  /**
   * Test of getNumbersOfRGroups method, of class de.modlab.smilib.fragments.ComponentAdministrator.
   */
  public void testGetNumbersOfRGroups() {
    System.out.println("getNumbersOfRGroups");
    
    int[] expResult = TestConstants.twoThreeFourNumRGroups;
    int[] result = componentAdministrator.getNumbersOfRGroups();
    assertTrue("true: " + Arrays.toString(expResult) + " == " + Arrays.toString(result), Arrays.equals(expResult, result));
  }

  public static void main(java.lang.String[] argList) {

    junit.textui.TestRunner.run(suite());
  }
  
}
