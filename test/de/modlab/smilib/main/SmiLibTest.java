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
 * SmiLibTest.java
 * JUnit based test
 *
 * Created on June 20, 2006, 10:41 AM
 */

package de.modlab.smilib.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import junit.framework.*;

/**
 *
 * @author Andreas Schueller
 */
public class SmiLibTest extends TestCase {
  
    private String nl = System.getProperty("line.separator");
    
    public SmiLibTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
      File outFile = new File("./out.txt");
      if (outFile.exists())
        outFile.deleteOnExit();

      outFile = new File("./out.sdf");
      if (outFile.exists())
        outFile.deleteOnExit();
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(SmiLibTest.class);
        
        return suite;
    }
    
    /**
     * Test of printHelp method, of class de.modlab.smilib.main.SmiLib.
     */
//    public void testPrintHelp() {
//        System.out.println("printHelp");
//        
//        SmiLib instance = new SmiLib();
//        
//        instance.printHelp();
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of enumerateLibrary method, of class de.modlab.smilib.main.SmiLib.
     */
//    public void testEnumerateLibrary() {
//        System.out.println("enumerateLibrary");
//        
//        SmiLib instance = new SmiLib();
//        
////        instance.enumerateLibrary();
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of main method for command line parameters error messages, of class de.modlab.smilib.main.SmiLib.
     */
    public void testMain1() {
        System.out.println("main1");

        String expectedOut = "usage: java -jar SmiLib.jar [-c] [-r reaction_scheme.txt] [-b" + nl +
                             "       building_blocks.smi] [-s scaffolds.smi] [-y] [-l linkers.smi] [-h]" + nl +
                             "       [-u] [-f lib.smi/lib.sdf]" + nl;
        String expectedErr = "Wrong or missing command line parameters." + nl;
        StringBuilder actualOut = new StringBuilder();
        StringBuilder actualErr = new StringBuilder();
        TestUtils.executeSmiLib("-s 12345", actualOut, actualErr);
        assertEquals("stdOut:" + nl + "expected:" + nl + "'" + expectedOut + "'" + nl + "actual:" + nl + "'" + actualOut.toString() + "'", expectedOut, actualOut.toString());
        assertEquals("stdErr:" + nl + "expected:" + nl + "'" + expectedErr + "'" + nl + "actual:" + nl + "'" + actualErr.toString() + "'", expectedErr, actualErr.toString());

        expectedOut = "";
        String expectedErrDe = "java.io.FileNotFoundException: notthere.smi (Das System kann die angegebene Datei nicht finden)" + nl +
                              "An error occured. Program halted." + nl;
        String expectedErrEn = "java.io.FileNotFoundException: notthere.smi (No such file or directory)" + nl +
                              "An error occured. Program halted." + nl;
        actualOut = new StringBuilder();
        actualErr = new StringBuilder();
        TestUtils.executeSmiLib("-s notthere.smi" + " -l " + TestConstants.validLinkers + " -b " + TestConstants.validBuildingBlocks + " -r " + TestConstants.validReactionScheme + " -f " + "./out.txt", actualOut, actualErr);
        assertEquals("stdOut:" + nl + "expected:" + nl + "'" + expectedOut + "'" + nl + "actual:" + nl + "'" + actualOut.toString() + "'", expectedOut, actualOut.toString());
        if (expectedErrDe.equals(actualErr.toString())) {
            assertEquals("stdErr:" + nl + "expected:" + nl + "'" + expectedErrDe + "'" + nl + "actual:" + nl + "'" + actualErr.toString() + "'", expectedErrDe, actualErr.toString());
        } else {
            assertEquals("stdErr:" + nl + "expected:" + nl + "'" + expectedErrEn + "'" + nl + "actual:" + nl + "'" + actualErr.toString() + "'", expectedErrEn, actualErr.toString());
        }
    }
    
    /**
     * Test of main method for normal combinatorial enumeration, of class de.modlab.smilib.main.SmiLib.
     */
    public void testMain2() {
        System.out.println("main2");
        
        String commandLine = "-s " + TestConstants.validScaffolds + " -l " + TestConstants.validLinkers + " -b " + TestConstants.validBuildingBlocks + " -r " + TestConstants.validReactionScheme + " -f " + "./out.txt";
//        System.out.println(commandLine);
        String[] args = commandLine.split(" ");
        
        SmiLib.main(args);
        String[] lines = null;
        try {
            File outFile = new File("./out.txt");
            TestUtils.waitForSmiLib(outFile);
            lines = TestUtils.readFile(outFile).toArray(new String[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        if (lines.length != TestConstants.expectedValidSmiles.length)
            fail("Read " + lines.length + " lines but expected " + TestConstants.expectedValidSmiles.length + ".");
        
//        System.out.println(Arrays.toString(lines));
        for (int i = 0; i < lines.length; i++) {
            String smiles = lines[i].split("\t")[1];
//            if (TestConstants.expectedValidSmiles[i].equals("WRONG_CONVERSION"))
//                assertEquals(i + " " + TestConstants.expectedValidSmilesRaw[i] + " == " + smiles, TestConstants.expectedValidSmilesRaw[i], smiles);
//            else {
                String convertedSmiles = null;
                String convertedExpectedSmiles = null;
                try {
                    convertedSmiles = TestUtils.convertWithOpenBabelToSmiles(smiles);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    fail("Error executing OpenBabel with SMILES " + smiles + " with index " + i + ".");
                }
//                System.out.println(smiles);
//                System.out.println(TestConstants.expectedValidSmiles[i]);
//                System.out.println(convertedSmiles);
                assertEquals(i + ": " + TestConstants.expectedValidSmiles[i] + " == " + convertedSmiles, TestConstants.expectedValidSmiles[i], convertedSmiles);
//            }
        }
    }
    
    /**
     * Test of main method for SDF capabilities, of class de.modlab.smilib.main.SmiLib.
     * To-do:<ul>
     * <li>Write SmilesParser that understands stereo information of Smiles and converts them to
     * AtomParities.</li>
     * <li>Wait until CDK bug 1530926 is fixed, so that [n+]%101ccccc1.[O-]%10 is written correctly
     * as SD file. Aromaticity is lost in this bug.</li>
     * <li>Wait until OpenBabel correctly converts stereo-SMILES to InChI and remove the workaround.
     * </li></ul>
     */
    public void testMain3() {
        System.out.println("main3");

        String commandLine = "-s " + TestConstants.validScaffolds + " -l " + TestConstants.validLinkers + " -b " + TestConstants.validBuildingBlocks + " -r " + TestConstants.validReactionScheme + " -f " + "./out.sdf";
        String[] args = commandLine.split(" ");
        
        SmiLib.main(args);
        TestUtils.waitForSmiLib("./out.sdf");

        // read resulting SD file
        List<String> resultingSDF = null;
        try {
          resultingSDF = TestUtils.readSDFile("./out.sdf");
        } catch (Exception ex) {
          ex.printStackTrace();
          fail("Unexpected exception was thrown: " + ex.getMessage());
        }

        // read expected SD file
        List<String> expectedSDF = null;
        try {
          expectedSDF = TestUtils.readSDFile(TestConstants.validLibrarySdf);
        } catch (Exception ex) {
          ex.printStackTrace();
          fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        if (expectedSDF.size() != resultingSDF.size())
            fail("SmiLib created " + resultingSDF.size() + " molecules but expected are " + expectedSDF.size() + ".");
        
        for (int i = 0; i < resultingSDF.size(); i++) {
            if (i != 12 && i != 13) { // TO-DO: Remove once CDK bug 1530926 (lost aromaticity) is fixed. Isotope doesn't work either
            String convertedResultingMolecule = null;
            String convertedExpectedMolecule = null;
            try {
                // Does not work right now, since CDK skips stereo information
                // convertedResultingMolecule = TestUtils.convertWithOpenBabelFromSdfToInChI(resultingSDF.get(i));
                // Here comes the work-around:
                convertedResultingMolecule = TestUtils.convertWithOpenBabelFromSdfToSmiles(resultingSDF.get(i));
                convertedResultingMolecule = convertedResultingMolecule.split("\t")[0];
                convertedResultingMolecule = TestUtils.convertWithCDKToSmiles(convertedResultingMolecule);
    //            convertedResultingMolecule = TestUtils.convertWithOpenBabelToInChI(convertedResultingMolecule);
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Error executing OpenBabel with resulting SD molecule at index " + i + ".");
            }
            try {
                // Does not work right now, since CDK skips stereo information
                // convertedExpectedMolecule = TestUtils.convertWithOpenBabelFromSdfToInChI(expectedSDF.get(i));
                // Here comes the work-around:
                convertedExpectedMolecule = TestUtils.convertWithOpenBabelFromSdfToSmiles(expectedSDF.get(i));
                convertedExpectedMolecule = convertedExpectedMolecule.split("\t")[0];
                convertedExpectedMolecule = TestUtils.convertWithCDKToSmiles(convertedExpectedMolecule);
    //            convertedExpectedMolecule = TestUtils.convertWithOpenBabelToInChI(convertedExpectedMolecule);
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Error executing OpenBabel with expected SD molecule at index " + i + ".");
            }
            assertEquals(i + ": " + convertedExpectedMolecule + " == " + convertedResultingMolecule, convertedExpectedMolecule, convertedResultingMolecule);
            }
        }
    }
    
    /**
     * Test of main method for SDF capabilities with hydrogen adding, of class de.modlab.smilib.main.SmiLib.
     * To-do:<ul>
     * <li>Wait until OpenBabel correctly converts stereo-SMILES to InChI to have test case not fail.
     * </li></ul>
     */
    public void testMain4() {
        System.out.println("main4");
        
        String commandLine = "-s " + TestConstants.validScaffolds + " -l " + TestConstants.validLinkers + " -b " + TestConstants.validBuildingBlocks + " -r " + TestConstants.validReactionScheme + " -f " + "./out.sdf -y";
        String[] args = commandLine.split(" ");
        
        SmiLib.main(args);
        TestUtils.waitForSmiLib("./out.sdf");

        // read resulting SD file
        List<String> resultingSDF = null;
        try {
          resultingSDF = TestUtils.readSDFile("./out.sdf");
        } catch (Exception ex) {
          ex.printStackTrace();
          fail("Unexpected exception was thrown: " + ex.getMessage());
        }

        // read expected SD file
        List<String> expectedSDF = null;
        try {
          expectedSDF = TestUtils.readSDFile(TestConstants.validLibrarySdfWithHydrogens);
        } catch (Exception ex) {
          ex.printStackTrace();
          fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        if (expectedSDF.size() != resultingSDF.size())
            fail("SmiLib created " + resultingSDF.size() + " molecules but expected are " + expectedSDF.size() + ".");
        
        for (int i = 0; i < resultingSDF.size(); i++) {
            if (i != 12 && i != 13) { // TO-DO: Remove once CDK bug 1530926 (lost aromaticity) is fixed. Isotope data doesn't work either.
                String convertedResultingMolecule = null;
                String convertedExpectedMolecule = null;
                try {
                    convertedResultingMolecule = TestUtils.convertWithOpenBabelFromSdfToInChI(resultingSDF.get(i));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    fail("Error executing OpenBabel with resulting SD molecule at index " + i + ".");
                }
                try {
                    convertedExpectedMolecule = TestUtils.convertWithOpenBabelFromSdfToInChI(expectedSDF.get(i));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    fail("Error executing OpenBabel with expected SD molecule at index " + i + ".");
                }
                assertEquals(i + ": " + convertedExpectedMolecule + " == " + convertedResultingMolecule, convertedExpectedMolecule, convertedResultingMolecule);
                }
        }
    }
    
    /**
     * Test of main method for chiral SMILES enumeration, of class de.modlab.smilib.main.SmiLib.
     * To-do:<ul>
     * <li>Wait until OpenBabel correctly converts stereo-SMILES to InChI to have test case not fail.
     * Manual inspection showed, that SmiLib worked fine.</li></ul>
     */
    public void testMain5() {
        System.out.println("main5");

        String commandLine = "-s " + TestConstants.chiralScaffolds + " -l " + TestConstants.chiralLinkers + " -b " + TestConstants.chiralBuildingBlocks + " -r " + TestConstants.chiralReactionScheme + " -f " + "./out.txt";
//        System.out.println(commandLine);
        String[] args = commandLine.split(" ");
        
        SmiLib.main(args);
        
        // read resulting smiles
        String[] resultingSmiles = null;
        try {
            File outFile = new File("./out.txt");
            TestUtils.waitForSmiLib(outFile);
            resultingSmiles = TestUtils.readFile(outFile).toArray(new String[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        // read expected smiles
        String[] expectedSmiles = null;
        try {
            File outFile = new File(TestConstants.chiralExpectedSmiles);
            TestUtils.waitForSmiLib(outFile);
            expectedSmiles = TestUtils.readFile(outFile).toArray(new String[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        if (resultingSmiles.length != expectedSmiles.length)
            fail("Read " + resultingSmiles.length + " lines but expected " + expectedSmiles.length + ".");
        
        for (int i = 0; i < resultingSmiles.length; i++) {
          String oneResultingSmiles = resultingSmiles[i].split("\t")[1];
          String convertedResultingSmiles = null;
          String convertedExpectedSmiles = null;
          try {
//              System.out.println("resulting SMILES: " + oneResultingSmiles);
              convertedResultingSmiles = TestUtils.convertWithOpenBabelToInChI(oneResultingSmiles);
//              convertedResultingSmiles = TestUtils.convertWithOpenBabelToSdf(oneResultingSmiles);
//              convertedResultingSmiles = TestUtils.convertWithOpenBabelFromSdfToInChI(convertedResultingSmiles);
          } catch (Exception ex) {
              ex.printStackTrace();
              fail("Error executing OpenBabel with SMILES " + oneResultingSmiles + " with index " + i + ".");
          }
          try {
//            System.out.println("expected SMILES: " + expectedSmiles[i]);
              convertedExpectedSmiles = TestUtils.convertWithOpenBabelToInChI(expectedSmiles[i]);
//              convertedExpectedSmiles = TestUtils.convertWithOpenBabelToSdf(expectedSmiles[i]);
//              convertedExpectedSmiles = TestUtils.convertWithOpenBabelFromSdfToInChI(convertedExpectedSmiles);
          } catch (Exception ex) {
              ex.printStackTrace();
              fail("Error executing OpenBabel with SMILES " + convertedExpectedSmiles + " with index " + i + ".");
          }
          assertEquals(i + ": " + convertedExpectedSmiles + " == " + convertedResultingSmiles, convertedExpectedSmiles, convertedResultingSmiles);
        }
        
        
    }
    
    /**
     * Test of getValidOptions method, of class de.modlab.smilib.main.SmiLib.
     */
//    public void testGetValidOptions() {
//        System.out.println("getValidOptions");
//        
//        SmiLib instance = new SmiLib();
//        
//        boolean expResult = true;
//        boolean result = instance.getValidOptions();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of getUseUserInterface method, of class de.modlab.smilib.main.SmiLib.
     */
//    public void testGetUseUserInterface() {
//        System.out.println("getUseUserInterface");
//        
//        SmiLib instance = new SmiLib();
//        
//        boolean expResult = true;
//        boolean result = instance.getUseUserInterface();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of getShowHelp method, of class de.modlab.smilib.main.SmiLib.
     */
//    public void testGetShowHelp() {
//        System.out.println("getShowHelp");
//        
//        SmiLib instance = new SmiLib();
//        
//        boolean expResult = true;
//        boolean result = instance.getShowHelp();
//        assertEquals(expResult, result);
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Test of main method to remove a triple bond bug found by Pascal Muller of NovAliX.
     * <pre>
     * Dear Dr Schüller,
     * 
     * You may be interested to know that I have just seen that triple bond may be misplaced in the smiles generated with SmiLib v2.0 rc2.
     * 
     * For exemple, with scaffold "O[R1]", empty linker, and building blocks [A]C#N and [A]C=N, I get this output:
     * 1.1_1    O%10.C#%10N
     * 1.1_2    O%10.C%10=N
     * 
     * The triple bond is between O and C (at least when viewed with Marvin / Chemaxon).
     * 
     * Anyway, thanks for this useful software.
     * 
     * Best regards,
     * Pascal
     * -- 
     * 	
     * Pascal MULLER PhD, PharmD
     * Head of Molecular Modeling
     * NovAliX
     * Building A: Chemistry
     * BioParc, bld Sébastien Brant
     * BP 30170
     * F-67405 ILLKIRCH CEDEX
     * FRANCE
     * tel: 	+33 (0) 368 330 202
     * fax: 	+33 (0) 368 330 201
     * email: 	pm@novalix-pharma.com
     * www: 	http://www.novalix.com
     */
    public void testMainNovAliXBug() {
        System.out.println("testMainNovAliXBug");
        
        String commandLine = "-s " + TestConstants.novalixScaffold + " -l " + TestConstants.pseudoLinker + " -b " + TestConstants.novalixBuildingBlocks + " -f " + "./out.txt";
//        System.out.println(commandLine);
        String[] args = commandLine.split(" ");
        
        String[] expectedResult = null;
        try {
            File expFile = new File(TestConstants.novalixExpectedResult);
            expectedResult = TestUtils.readFile(expFile).toArray(new String[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        SmiLib.main(args);
        String[] lines = null;
        try {
            File outFile = new File("./out.txt");
            TestUtils.waitForSmiLib(outFile);
            lines = TestUtils.readFile(outFile).toArray(new String[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Unexpected exception was thrown: " + ex.getMessage());
        }
        
        if (lines.length != expectedResult.length)
            fail("Read " + lines.length + " lines but expected " + expectedResult.length + ".");
        
        if (lines.length == 0)
            fail("Read zero lines.");
        
//        System.out.println(Arrays.toString(lines));
        for (int i = 0; i < lines.length; i++) {
            assertEquals(i + ": " + expectedResult[i] + " == " + lines[i], expectedResult[i], lines[i]);
        }
    }

  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
    
}
