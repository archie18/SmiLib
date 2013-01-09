/*
 * SmilesGuiReaderTest.java
 * JUnit based test
 *
 * Created on 15. Januar 2008, 17:13
 */

package de.modlab.smilib.io;

import de.modlab.smilib.main.TestConstants;
import java.util.List;
import junit.framework.*;
import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.fragments.BuildingBlock;
import de.modlab.smilib.fragments.Linker;
import de.modlab.smilib.fragments.Scaffold;
import java.util.ArrayList;

/**
 *
 * @author Andreas Schueller
 */
public class SmilesGuiReaderTest extends TestCase {
  
  private SmilesGuiReader smilesGuiReader;
  
  public SmilesGuiReaderTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
    smilesGuiReader = new SmilesGuiReader(true);
  }

  protected void tearDown() throws Exception {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(SmilesGuiReaderTest.class);
    
    return suite;
  }

  /**
   * Test of readScaffolds method, of class de.modlab.smilib.io.SmilesGuiReader.
   */
  public void testReadScaffolds() throws Exception {
    System.out.println("readScaffolds");
    
    List<Scaffold> fragments = smilesGuiReader.readScaffolds(TestConstants.emptyLinesScaffoldsGui);
    if (TestConstants.emptyLinesScaffoldsSmiles.length != fragments.size())
      fail("An incorrect number of SMILES strings were read.");
    
    for (int i = 0; i < TestConstants.emptyLinesScaffoldsSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesScaffoldsSmiles[i] + " == " + fragments.get(i).getOriginalSMILES(), TestConstants.emptyLinesScaffoldsSmiles[i], fragments.get(i).getOriginalSMILES());
  }

  /**
   * Test of readLinkers method, of class de.modlab.smilib.io.SmilesGuiReader.
   */
  public void testReadLinkers() throws Exception {
    System.out.println("readLinkers");
    
    List<Linker> fragments = smilesGuiReader.readLinkers(TestConstants.emptyLinesLinkersGui);
    if (TestConstants.emptyLinesLinkersSmiles.length != fragments.size())
      fail("An incorrect number of SMILES strings were read.");
    
    for (int i = 0; i < TestConstants.emptyLinesLinkersSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesLinkersSmiles[i] + " == " + fragments.get(i).getOriginalSMILES(), TestConstants.emptyLinesLinkersSmiles[i], fragments.get(i).getOriginalSMILES());
  }

  /**
   * Test of readBuildingBlocks method, of class de.modlab.smilib.io.SmilesGuiReader.
   */
  public void testReadBuildingBlocks() throws Exception {
    System.out.println("readBuildingBlocks");
    
    List<BuildingBlock> fragments = smilesGuiReader.readBuildingBlocks(TestConstants.emptyLinesBuildingBlocksGui);
    if (TestConstants.emptyLinesBuildingBlocksSmiles.length != fragments.size())
      fail("An incorrect number of SMILES strings were read.");
    
    for (int i = 0; i < TestConstants.emptyLinesBuildingBlocksSmiles.length; i++)
      assertEquals(TestConstants.emptyLinesBuildingBlocksSmiles[i] + " == " + fragments.get(i).getOriginalSMILES(), TestConstants.emptyLinesBuildingBlocksSmiles[i], fragments.get(i).getOriginalSMILES());
  }

  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
  
}
