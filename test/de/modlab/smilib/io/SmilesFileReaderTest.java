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
 * SmilesFileReaderTest.java
 * JUnit based test
 *
 * Created on 29. Juni 2006, 17:43
 */

package de.modlab.smilib.io;

import de.modlab.smilib.fragments.BuildingBlock;
import de.modlab.smilib.fragments.BuildingBlockFactory;
import de.modlab.smilib.fragments.Linker;
import de.modlab.smilib.fragments.LinkerFactory;
import de.modlab.smilib.fragments.Scaffold;
import de.modlab.smilib.fragments.ScaffoldFactory;
import de.modlab.smilib.main.TestConstants;
import junit.framework.*;
import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.fragments.Fragment;
import de.modlab.smilib.fragments.FragmentFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Schueller
 */
public class SmilesFileReaderTest extends TestCase {
  
  public SmilesFileReaderTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(SmilesFileReaderTest.class);
    
    return suite;
  }

  /**
   * Test of readSmiles method, of class de.modlab.smilib.io.SmilesFileReader.
   */
  public void testReadSmiles() throws Exception {
    System.out.println("readSmiles");
    
    SmilesFileReader<Scaffold> scaffoldsReader = new SmilesFileReader<Scaffold>(new ScaffoldFactory(), true, 0);
    List<Scaffold> scaffolds = scaffoldsReader.readSmiles(TestConstants.twoScaffolds);
    for (int i = 0; i < TestConstants.twoScaffoldsSmiles.length; i++)
      assertEquals(TestConstants.twoScaffoldsSmiles[i] + " == " + scaffolds.get(i).getOriginalSMILES(), TestConstants.twoScaffoldsSmiles[i], scaffolds.get(i).getOriginalSMILES());
    
    SmilesFileReader<Linker> linkersReader = new SmilesFileReader<Linker>(new LinkerFactory(), true, 1);
    List<Linker> linkers = linkersReader.readSmiles(TestConstants.threeLinkers);
    for (int i = 0; i < TestConstants.threeLinkersSmiles.length; i++)
      assertEquals(TestConstants.threeLinkersSmiles[i] + " == " + linkers.get(i).getOriginalSMILES(), TestConstants.threeLinkersSmiles[i], linkers.get(i).getOriginalSMILES());
    
    SmilesFileReader<BuildingBlock> buildingBlocksReader = new SmilesFileReader<BuildingBlock>(new BuildingBlockFactory(), true, 2);
    List<BuildingBlock> buildingBlocks = buildingBlocksReader.readSmiles(TestConstants.fourBuildingBlocks);
    for (int i = 0; i < TestConstants.fourBuildingBlocksSmiles.length; i++)
      assertEquals(TestConstants.fourBuildingBlocksSmiles[i] + " == " + buildingBlocks.get(i).getOriginalSMILES(), TestConstants.fourBuildingBlocksSmiles[i], buildingBlocks.get(i).getOriginalSMILES());
    
    try {
      scaffolds = scaffoldsReader.readSmiles("");
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException e) {
      // all good
    }
    
    try {
      linkers = linkersReader.readSmiles("");
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException e) {
      // all good
    }
    
    try {
      buildingBlocks = buildingBlocksReader.readSmiles("");
      fail("SmiLibIOException was not thrown.");
    } catch (SmiLibIOException e) {
      // all good
    }

  }

  /**
   * Makes sure that empty lines are ignored by the file reader. This had caused
   * a bug of "incomplete" SMILES.
   */
  public void testReadSmilesEmptyLines() throws Exception {
    System.out.println("readSmiles");
    
    SmilesFileReader<Scaffold> scaffoldsReader = new SmilesFileReader<Scaffold>(new ScaffoldFactory(), true, 0);
    List<Scaffold> scaffolds = scaffoldsReader.readSmiles(TestConstants.emptyLinesScaffolds);
    if (TestConstants.emptyLinesScaffoldsSmiles.length != scaffolds.size())
      fail("An incorrect number of SMILES strings were read.");
    for (int i = 0; i < TestConstants.emptyLinesScaffoldsSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesScaffoldsSmiles[i] + " == " + scaffolds.get(i).getOriginalSMILES(), TestConstants.emptyLinesScaffoldsSmiles[i], scaffolds.get(i).getOriginalSMILES());
    
    SmilesFileReader<Linker> linkersReader = new SmilesFileReader<Linker>(new LinkerFactory(), true, 1);
    List<Linker> linkers = linkersReader.readSmiles(TestConstants.emptyLinesLinkers);
    if (TestConstants.emptyLinesLinkersSmiles.length != linkers.size())
      fail("An incorrect number of SMILES strings were read.");
    for (int i = 0; i < TestConstants.emptyLinesLinkersSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesLinkersSmiles[i] + " == " + linkers.get(i).getOriginalSMILES(), TestConstants.emptyLinesLinkersSmiles[i], linkers.get(i).getOriginalSMILES());
    
    SmilesFileReader<BuildingBlock> buildingBlocksReader = new SmilesFileReader<BuildingBlock>(new BuildingBlockFactory(), true, 2);
    List<BuildingBlock> buildingBlocks = buildingBlocksReader.readSmiles(TestConstants.emptyLinesBuildingBlocks);
    if (TestConstants.emptyLinesBuildingBlocksSmiles.length != buildingBlocks.size())
      fail("An incorrect number of SMILES strings were read.");
    for (int i = 0; i < TestConstants.emptyLinesBuildingBlocksSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesBuildingBlocksSmiles[i] + " == " + buildingBlocks.get(i).getOriginalSMILES(), TestConstants.emptyLinesBuildingBlocksSmiles[i], buildingBlocks.get(i).getOriginalSMILES());
  }

  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
  
}
