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
 * TestUtils.java
 *
 * Created on 29. Juni 2006, 19:17
 */

package de.modlab.smilib.main;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesGenerator;

/**
 *
 * @author Andreas Schueller
 */
public class TestUtils {
  
  /** Creates a new instance of TestUtils */
  private TestUtils() {
  }
  
  
  public static List<String> readFile(String filename) throws IOException {
    return readFile(new File(filename));
  }
  
  public static List<String> readFile(File file) throws IOException {
    List<String> lines = new ArrayList<String>();
    BufferedReader in = new BufferedReader(new FileReader(file));
    String line;
    while ((line = in.readLine()) != null)
      lines.add(line);
    return lines;
  }
  
  public static void sleep(long milliSeconds) {
    long time = System.currentTimeMillis();
    while (time + milliSeconds > System.currentTimeMillis());
  }
  
  private static String convertWithOpenBabel(String smiles, String openBabelCommandline) throws Exception{
    //System.out.println("OpenBabel command line: '" + openBabelCommandline + "'");
    //System.out.println("Before: '" + smiles + "'");
    Process process;
    process = Runtime.getRuntime().exec(openBabelCommandline, TestConstants.openBabelEnvironment);
    PrintStream toProcess = new PrintStream(new BufferedOutputStream(process.getOutputStream()));
    toProcess.println(smiles);
    toProcess.close();
    
    BufferedReader fromProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
    StringBuilder convertedMolecule = new StringBuilder();
    String line;
    while ((line = fromProcess.readLine()) != null)
      convertedMolecule.append(line.trim() + "\n");
    fromProcess.close();
    
    if (convertedMolecule.length() == 0)
      throw new Exception("Converted molecule read from OpenBabel was of zero length.");

    //System.out.println("After: '" + convertedMolecule.toString() + "'");

    return convertedMolecule.toString();
  }
  
  public static String convertWithOpenBabelToSmiles(String smiles) throws Exception{
    
    String openBabelCommandline;
    if (System.getProperty("os.name").contains("Linux"))
      openBabelCommandline = TestConstants.openBabelComanndlineLinux;
    else
      openBabelCommandline = TestConstants.openBableCommandlineWin;
    
    return convertWithOpenBabel(smiles, openBabelCommandline).trim();
  }
  
  public static String convertWithOpenBabelToInChI(String smiles) throws Exception{
    
    String openBabelCommandline;
    if (System.getProperty("os.name").contains("Linux"))
      openBabelCommandline = TestConstants.openBabelComanndlineLinuxInchi;
    else
      openBabelCommandline = TestConstants.openBableCommandlineWinInchi;
    
    return convertWithOpenBabel(smiles, openBabelCommandline).trim();
  }
  
  public static String convertWithOpenBabelFromSdfToInChI(String molecule) throws Exception {
    
    String openBabelCommandline;
    if (System.getProperty("os.name").contains("Linux"))
      openBabelCommandline = TestConstants.openBabelComanndlineLinuxSdfToInchi;
    else
      openBabelCommandline = TestConstants.openBableCommandlineWinSdfToInchi;
    
    return convertWithOpenBabel(molecule, openBabelCommandline).trim();
  }
  
  public static String convertWithOpenBabelFromSdfToSmiles(String molecule) throws Exception {
    
    String openBabelCommandline;
    if (System.getProperty("os.name").contains("Linux"))
      openBabelCommandline = TestConstants.openBabelComanndlineLinuxSdfToSmiles;
    else
      openBabelCommandline = TestConstants.openBableCommandlineWinSdfToSmiles;
    
    return convertWithOpenBabel(molecule, openBabelCommandline).trim();
  }
  
  public static String convertWithOpenBabelToSdf(String molecule) throws Exception {
    
    String openBabelCommandline;
    if (System.getProperty("os.name").contains("Linux"))
      openBabelCommandline = TestConstants.openBabelComanndlineLinuxSdf;
    else
      openBabelCommandline = TestConstants.openBableCommandlineWinSdf;
    
    return convertWithOpenBabel(molecule, openBabelCommandline);
  }
  
  public static String convertWithCDKToSmiles(String smiles) throws Exception{
    SmilesParser smilesParser = new SmilesParser();
    IMolecule molecule = smilesParser.parseSmiles(smiles);
    SmilesGenerator smilesGenerator = new SmilesGenerator(DefaultChemObjectBuilder.getInstance());
    return smilesGenerator.createSMILES(molecule).trim();
  }
  
  public static void executeSmiLib(String commandLineParameters, StringBuilder stdOutBuffer, StringBuilder stdErrBuffer) {
    PrintStream oldStdOut = System.out;
    ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stdOut));

    PrintStream oldStdErr = System.err;
    ByteArrayOutputStream stdErr = new ByteArrayOutputStream();
    System.setErr(new PrintStream(stdErr));

    String[] args = commandLineParameters.split(" ");
    SmiLib.main(args);

    stdOutBuffer.append(stdOut.toString());
    System.setOut(oldStdOut);

    stdErrBuffer.append(stdErr.toString());
    System.setErr(oldStdErr);
  }

  public static void waitForSmiLib(String filename) {
    File file = new File(filename);
    TestUtils.waitForSmiLib(file);
  }

  public static void waitForSmiLib(File file) {
    long oldLastModified;
    do {
      oldLastModified = file.lastModified();
      TestUtils.sleep(1000);
    } while (file.lastModified() > oldLastModified);
  }

  static List<String> readSDFile(String filename) throws IOException {
    List<String> sdfFile = TestUtils.readFile(filename);
    StringBuilder molecules = new StringBuilder();
    for (String s : sdfFile)
      molecules.append(s + SmiLib.nl);
    String[] moleculeArray = molecules.toString().split("\\$\\$\\$\\$");
    for (int i = 0; i < moleculeArray.length; i++)
      moleculeArray[i] = moleculeArray[i].trim();
    return Arrays.asList(moleculeArray).subList(0, moleculeArray.length - 1);
  }
  
}
