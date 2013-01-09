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
 * PartialCombinationIteratorTest.java
 * JUnit based test
 *
 * Created on 30. Juni 2006, 13:34
 */

package de.modlab.smilib.iterator;

import de.modlab.smilib.main.TestConstants;
import junit.framework.*;
import de.modlab.smilib.exceptions.ReactionSchemeException;
import de.modlab.smilib.exceptions.SmiLibException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author Andreas Schueller
 */
public class PartialCombinationIteratorTest extends TestCase {
  
  private int[][] expectedVirtualReactions = new int[][] {
    {0, 0, 0, 0, 0},
    {0, 0, 2, 2, 0},
    {0, 0, 2, 3, 0},
    {0, 0, 0, 2, 0},
    {0, 0, 0, 3, 0},
    {0, 1, 2, 2, 0},
    {0, 1, 2, 3, 0},
    {0, 1, 0, 2, 0},
    {0, 1, 0, 3, 0},
    {1, 0, 0, 0, 0, 0, 0}
  };
  
  public PartialCombinationIteratorTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(PartialCombinationIteratorTest.class);
    
    return suite;
  }
  
  /**
   * Test of the constructor of class de.modlab.smilib.iterator.PartialCombinationIterator.
   */
  public void testPartialCombinationIterator() {
    System.out.println("partialCombinationIterator");

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.verySimpleReactionScheme, new int[] {1}, 1, 1);
    } catch (ReactionSchemeException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme1, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme1);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme2, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme2);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme3, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme3);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme4, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme4);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme5, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme5);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme6, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme6);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme7, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme7);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme8, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme8);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme9, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme9);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator(TestConstants.twoThreeFourInvalidReactionScheme10, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("ReactionSchemeException was not thrown for invalid reaction scheme: " + TestConstants.twoThreeFourInvalidReactionScheme10);
    } catch (ReactionSchemeException ex) {
      // all good
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

    try {
      Iterator<int[]> iterator = new PartialCombinationIterator("", TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
      fail("SmiLibIOException was not thrown.");
    } catch (ReactionSchemeException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibIOException ex) {
      // all good
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }

  }

  /**
   * Test of hasNext method, of class de.modlab.smilib.iterator.PartialCombinationIterator.
   */
  public void testHasNext() {
    System.out.println("hasNext");
    // tested in testNext()
  }

  /**
   * Test of next method, of class de.modlab.smilib.iterator.PartialCombinationIterator.
   */
  public void testNext() {
    System.out.println("next");
    
    PartialCombinationIterator instance = null;
    Iterator<int[]> iterator = null;
    try {
      iterator = new PartialCombinationIterator(TestConstants.twoThreeFourValidReactionScheme, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
    } catch (ReactionSchemeException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown: " + ex.getMessage());
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown: " + ex.getMessage());
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown: " + ex.getMessage());
    }
    
    int counter = 0;
    while (iterator.hasNext()) {
      int[] virtualReaction = iterator.next();
      assertTrue(counter + ": " + Arrays.toString(expectedVirtualReactions[counter]) + " == " + Arrays.toString(virtualReaction), Arrays.equals(expectedVirtualReactions[counter], virtualReaction));
      counter++;
    }
    
    assertEquals(expectedVirtualReactions.length, counter);
  }

  /**
   * Test of remove method, of class de.modlab.smilib.iterator.PartialCombinationIterator.
   */
  public void testRemove() {
    System.out.println("remove");
    
    PartialCombinationIterator instance = null;
    Iterator<int[]> iterator = null;
    try {
      iterator = new PartialCombinationIterator(TestConstants.verySimpleReactionScheme, new int[] {1}, 1, 1);
    } catch (ReactionSchemeException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }
    
    try {
      iterator.remove();
      fail("No UnsupportedOperationException was thrown when executing remove().");
    } catch (UnsupportedOperationException e) {
    }
  }

  /**
   * Test of getMaximum method, of class de.modlab.smilib.iterator.PartialCombinationIterator.
   */
  public void testGetMaximum() {
    System.out.println("getMaximum");
    
    PartialCombinationIterator iterator = null;
    try {
      iterator = new PartialCombinationIterator(TestConstants.twoThreeFourValidReactionScheme, TestConstants.twoThreeFourNumRGroups, TestConstants.twoThreeFourNumLinkers, TestConstants.twoThreeFourNumBuildingBlocks);
    } catch (ReactionSchemeException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibIOException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    } catch (SmiLibException ex) {
      ex.printStackTrace();
      fail("Unexpected exception was thrown.");
    }
    
    int expected = expectedVirtualReactions.length;
    assertEquals(expected, iterator.getMaximum());
  }

  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
  
}
