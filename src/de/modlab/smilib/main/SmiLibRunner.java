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

package de.modlab.smilib.main;

import de.modlab.smilib.exceptions.ReactionSchemeException;
import de.modlab.smilib.exceptions.SmiLibConformityException;
import de.modlab.smilib.exceptions.SmiLibIOException;
import de.modlab.smilib.exceptions.SmiLibSdfException;
import de.modlab.smilib.gui.SmiLibFrame;
import de.modlab.smilib.io.SmilesGuiWriter;
import de.modlab.smilib.io.SmilesToSDFWriter;
import de.modlab.smilib.iterator.FullCombinationIterator;
import de.modlab.smilib.fragments.ComponentAdministrator;
import de.modlab.smilib.io.SmilesFileWriter;
import de.modlab.smilib.io.SmilesLineWriter;
import de.modlab.smilib.io.SmilesWriter;
import de.modlab.smilib.iterator.PartialCombinationIterator;
import de.modlab.smilib.iterator.SmiLibIterator;
import java.text.DecimalFormat;
import javax.swing.JTextArea;


/**
 *Enumerates the combinatorial libraray.
 *
 * @author Volker Haehnke
 * @author Andreas Schueller
 */
public class SmiLibRunner implements Runnable {
    
    
    
    //stores and distributes SMILES of scaffolds, linkers and building blocks
    private ComponentAdministrator compAdmin;
    
    //writes SMILES
    private SmilesWriter smiWri;
    
    //iterates the combination that shall be created
    private SmiLibIterator iterator;
    
    //counts how many molecules are already created
    private int compoundCounter = 0;
    
    //concatenates SMILES of scaffolds/linkers/building blocks
    private SmilesConcatenator smiConcat = new SmilesConcatenator();
    
    //GUI
    private SmiLibFrame smiFrame;
    
    //boolean that is true when GUI mode is used
    private boolean useGui = false;
    
    //stop thread true/false
    private boolean stop = false;
    
    //print library to command line true/false    
    private boolean printToCommandLine;
    
    //enable start of enumeration true/false
    private boolean startEnumeration = true;
    
    
    
    /**
     * Creates a new instance of SmiLibRunner using a FullCombinationIterator (without reaction scheme)
     * for enumerating the virtual library.
     *
     * @param scaffoldsPath path/name of the file that contains scaffolds as SMILES
     * @param linkersPath path/name of the file that contains linkers as SMILES
     * @param buildingBlocksPath path/name of the file that contains building blocks as SMILES
     * @param printToCommandLine combinatorial library shall be printed to the command line true/false
     * @param saveFilePath path/name of the file where the combinatorial library will be stored if it shall be saved in a file
     * @param addHydrogens add hydrogens when save as SD file
     * @param checkSmiles check SMILES for conformity with SMiLib rules true/false
     */
    public SmiLibRunner(String scaffoldsPath, String linkersPath, String buildingBlocksPath, boolean printToCommandLine, String saveFilePath, boolean addHydrogens, boolean checkSmiles) {
        this.printToCommandLine = printToCommandLine;
        
        try {
            compAdmin = new ComponentAdministrator(scaffoldsPath, linkersPath, buildingBlocksPath, checkSmiles);

            this.iterator = new FullCombinationIterator(
                    compAdmin.getNumbersOfRGroups(),
                    compAdmin.getNumberOfLinkers(),
                    compAdmin.getNumberOfBuildingBlocks());

            if (printToCommandLine)
                smiWri = new SmilesLineWriter();
            else
                if (saveFilePath.endsWith(".sdf"))
                    smiWri = new SmilesToSDFWriter(saveFilePath, addHydrogens);
                else
                    smiWri = new SmilesFileWriter(saveFilePath);
        } catch (Exception ex) {
            handleException(ex);
        }
    }
    
    
    /**
     * Creates a new instance of SmiLibRunner using a PartialCombinationIterator (with reaction scheme)
     * for enumerating the virtual library.
     *
     * @param scaffoldsPath path/name of the file that contains scaffolds as SMILES
     * @param linkersPath path/name of the file that contains linkers as SMILES
     * @param buildingBlocksPath path/name of the file that contains building blocks as SMILES
     * @param reactionSchemePath path/filename of the file that contains the reaction scheme
     * @param printToCommandLine combinatorial library shall be printed to the command line true/false
     * @param saveFilePath path/name of the file where the combinatorial library will be stored if it shall be saved in a file
     * @param addHydrogens add hydrogens when save as SD file
     * @param checkSmiles check SMILES for conformity with SMiLib rules true/false
     */
    public SmiLibRunner(String scaffoldsPath, String linkersPath, String buildingBlocksPath, String reactionSchemePath, boolean printToCommandLine, String saveFilePath, boolean addHydrogens, boolean checkSmiles) {
        this.printToCommandLine = printToCommandLine;
        
        try {
            compAdmin = new ComponentAdministrator(scaffoldsPath, linkersPath, buildingBlocksPath, checkSmiles);
            this.iterator = new PartialCombinationIterator(
                    reactionSchemePath,
                    compAdmin.getNumbersOfRGroups(),
                    compAdmin.getNumberOfLinkers(), compAdmin.getNumberOfBuildingBlocks());
        
            if (printToCommandLine)
                smiWri = new SmilesLineWriter();
            else
                if (saveFilePath.endsWith(".sdf"))
                    smiWri = new SmilesToSDFWriter(saveFilePath, addHydrogens);
                else
                    smiWri = new SmilesFileWriter(saveFilePath);
        } catch (Exception ex) {
            handleException(ex);
        }
    }
    
    
    /**
     *Creates a new instance of SmiLibRunner, used when programm is started in GUI mode.
     *
     *@param scaffolds array of source scaffold (with ID)
     *@param linkers array of source linkers (with ID)
     *@param bBlocks array of source building block (with ID)
     *@param useReactionScheme use reaction scheme true/false
     *@param reactionScheme reactionScheme as array
     *@param targetTextArea text area where library will be written in
     *@param showLibrary show library in GUI true/false
     *@param saveAsFile save library as file true/false
     *@param saveAsSDF save file as SD file true/false
     *@param addHydrogens add implicit hydrogens when building the SD file true/false
     *@param path path/name of file where library will be saved
     *@param frame main frame of GUI
     *@param checkSmiles check smiles for SmiLib conformity true/false
     */
    public SmiLibRunner(String[] scaffolds, String[] linkers, String[] bBlocks,
            boolean useReactionScheme, String[] reactionScheme, JTextArea targetTextArea,
            boolean showLibrary, boolean saveAsFile, boolean saveAsSDF, boolean addHydrogens, String path,
            SmiLibFrame frame, boolean checkSmiles) {
        
        this.smiFrame = frame;
        this.useGui = true;
        
        //load source SMILES
        try {
            compAdmin = new ComponentAdministrator(scaffolds, linkers, bBlocks, checkSmiles);
            if (!useReactionScheme) {
                this.iterator = new FullCombinationIterator(
                        compAdmin.getNumbersOfRGroups(),
                        compAdmin.getNumberOfLinkers(),
                        compAdmin.getNumberOfBuildingBlocks());
            } else {
                this.iterator = new PartialCombinationIterator(
                        reactionScheme,
                        compAdmin.getNumbersOfRGroups(),
                        compAdmin.getNumberOfLinkers(),
                        compAdmin.getNumberOfBuildingBlocks());
            }
            smiFrame.setIterator(iterator);
            smiWri = new SmilesGuiWriter(targetTextArea, showLibrary, saveAsFile, saveAsSDF, addHydrogens, path, smiFrame);
            
        } catch (Exception exc) {
            handleException(exc);
        }
    }

    
    /**
     * Creates a new instance of SmiLibRunner that can be conveniently used
     * in other Java projects when SmiLib is employed as a Java library.<br>
     * <br>
     * Example to enumerate a complete SmiLib library:
     * <pre>
     * SmilesListWriter smiWri = new SmilesListWriter();
     * SmiLibRunner runner = new SmiLibRunner(scaffolds, linkers, bBlocks, null, true, smiWri);
     * runner.run();
     * List&lt;String&gt; smiles = smiWri.getSmilesList();
     * </pre>
     * <br>
     * Example to enumerate a SmiLib library according to a reaction scheme:
     * <pre>
     * SmilesListWriter smiWri = new SmilesListWriter();
     * SmiLibRunner runner = new SmiLibRunner(scaffolds, linkers, bBlocks, reactionScheme, true, smiWri);
     * runner.run();
     * List&lt;String&gt; smiles = smiWri.getSmilesList();
     * </pre>
     * <br>
     * @param scaffolds array of source scaffolds, optionally with ID
     * @param linkers array of source linkers, optionally with ID
     * @param bBlocks array of source building blocks, optionally with ID
     * @param reactionScheme reactionScheme as an array. If null, complete enumeration is performed
     * @param checkSmiles check smiles for SmiLib conformity true/false
     * @param smiWri output smiles writer
     * @since 2.0 rc4
     */
    public SmiLibRunner(String[] scaffolds, String[] linkers, String[] bBlocks,
            String[] reactionScheme, boolean checkSmiles, SmilesWriter smiWri) {
        
        try {
            compAdmin = new ComponentAdministrator(scaffolds, linkers, bBlocks, checkSmiles);
            if (reactionScheme == null) {
                this.iterator = new FullCombinationIterator(
                        compAdmin.getNumbersOfRGroups(),
                        compAdmin.getNumberOfLinkers(),
                        compAdmin.getNumberOfBuildingBlocks());
            } else {
                this.iterator = new PartialCombinationIterator(
                        reactionScheme,
                        compAdmin.getNumbersOfRGroups(),
                        compAdmin.getNumberOfLinkers(),
                        compAdmin.getNumberOfBuildingBlocks());
            }
            this.smiWri = smiWri;
            
        } catch (Exception exc) {
            handleException(exc);
        }
    }
    
    
    /**
     *Starts enumeration of the combinatorial library.
     */
    public void run() {
        //enumeration starts
        if (startEnumeration) {
            
            //start time
            long start = System.nanoTime();
            
            //if GUI mode is used, some statistics are shown
            if (useGui) {
                smiFrame.setStatistics(
                        compAdmin.getNumberOfScaffolds(),
                        compAdmin.getNumberOfLinkers(),
                        compAdmin.getNumberOfBuildingBlocks());
            }
            
            //start enumeration
            try {
                this.enumerateLibrary();
            } catch (Throwable thr) {
                handleException(thr);
            }
            
            //end time
            long end = System.nanoTime();
            
            //formats time
            DecimalFormat df= new DecimalFormat("###,###,###,##0.000000000###");
            
            //message of end of enumeration is shown in command line/GUI - NOT if library is printed to command line
            if (useGui) {
                smiFrame.setTime(start, end);
                smiFrame.showEndMessage(start, end);
            } else if (!printToCommandLine) {
                //total time is printed out
                if (compoundCounter == 1) {
                    System.out.println(compoundCounter + " compound synthesized in " + df.format((double)(end - start)/1000000000) + " seconds");
                } else {
                    System.out.println(compoundCounter + " compounds synthesized in " + df.format((double)(end - start)/1000000000) + " seconds");
                }
            }
        }
    }
    
    
    /**
     *Enumerates the combinatorial library.
     */
    private void enumerateLibrary() throws Exception {
        boolean linkerEmpty;
        int numRGroups;
        int ringNumber;
        int[] currentCombination;
        
        StringBuilder intermediateProduct = new StringBuilder();
        StringBuilder currentMolecule = new StringBuilder();
        StringBuilder moleculeID = new StringBuilder();
        
        //as long as new combination are available and thread shall not be stopped
        while (iterator.hasNext() & !stop) {
            //gets next combination
            currentCombination = iterator.next();
            
            //StringBuilder with the currentMolecule gets a reset - so you can use one Object for all molecules
            currentMolecule.delete(0, currentMolecule.length()).append(compAdmin.getScaffoldString(currentCombination[0]));
            
            //StringBuilder with molecule ID gets a reset
            moleculeID.delete(0, moleculeID.length()).append(compAdmin.getScaffoldID(currentCombination[0]));
            
            //gets number of variable side chains in the current scaffold
            numRGroups = compAdmin.getNumberOfRGroups(currentCombination[0]);
            ringNumber = 10;
            
            //for each variable side chain
            for (int i = 1; i <= numRGroups; i++) {
                linkerEmpty = compAdmin.getLinker(currentCombination[i]).isEmpty();
                
                //ringnumber is checked, so that conflicts with existing ringnumbers in scaffold-, linker- or buildingblock-SMILES can not occur
                while(compAdmin.numberBlacklisted(
                        ringNumber,
                        currentCombination[0],
                        currentCombination[i],
                        currentCombination[i+numRGroups]))
                    ringNumber++;
                
                //if linker is not the empty linker
                if (!linkerEmpty) {
                    //StringBuilder with the current intermediate gets a reset - so you can use one Object for all intermediates
                    intermediateProduct.delete(0, intermediateProduct.length()).append(compAdmin.getLinker(currentCombination[i]).getLinkerForConcat(ringNumber));
                    
                    //current intermediate is created
                    smiConcat.concatenate(
                            intermediateProduct,
                            '.',
                            compAdmin.getBuildingBlock(currentCombination[i + numRGroups]).getBlockForConcat(ringNumber));
                    ringNumber++;
                    
                    //current intermediate gets attached to the scaffold
                    smiConcat.concatenate(
                            currentMolecule,
                            intermediateProduct,
                            '.',
                            compAdmin.getScaffold(currentCombination[0]).getStringOfGroupWithIndex(i-1),
                            ringNumber);
                    ringNumber++;
                    
                    //if linker is the empty linker
                } else {
                    //building block gets directly attached to the scaffold
                    smiConcat.concatenate(
                            currentMolecule,
                            compAdmin.getBuildingBlock(currentCombination[i + numRGroups]).getBlockForConcat(ringNumber),
                            '.',
                            compAdmin.getScaffold(currentCombination[0]).getStringOfGroupWithIndex(i-1),
                            ringNumber);
                    ringNumber++;
                }
                
                //molecule ID gets an update
                moleculeID.append('.').append(compAdmin.getLinkerID(currentCombination[i])).append('_').append(compAdmin.getBuildingBlockID(currentCombination[i + numRGroups]));
            }
            //created molecule is written
            smiWri.writeSMILES(currentMolecule, moleculeID);
            compoundCounter++;
        }
        smiWri.close();
    }
    
    
    /**
     *Centralised Exception handling. Thread ist stopped and a
     *message for each different kind of message is shown/printed.
     *
     *@param thr cause of Error/Exception
     */
    public void handleException(Throwable thr) {
        startEnumeration = false;
        
        //GUI: show JOptionpane; else: print errormessage
        if (useGui) {
            smiFrame.emergencyShutdown();
            javax.swing.JOptionPane messageBox = new javax.swing.JOptionPane();
            
            if (thr instanceof SmiLibConformityException) {
                messageBox.showMessageDialog(smiFrame, thr.getMessage(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (thr instanceof ReactionSchemeException) {
                messageBox.showMessageDialog(smiFrame, thr.getMessage(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (thr instanceof SmiLibIOException) {
                messageBox.showMessageDialog(smiFrame, thr.getMessage(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (thr instanceof StringIndexOutOfBoundsException) {
                messageBox.showMessageDialog(smiFrame, thr.getMessage(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (thr instanceof NumberFormatException) {
                messageBox.showMessageDialog(smiFrame, "Invalid reaction scheme.\nPlease check reaction scheme and/or use SmiLib help!", "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (thr instanceof java.lang.OutOfMemoryError) {
                messageBox.showMessageDialog(smiFrame, "Java heap size overflow.\nRestart program with access to more memory!", "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            } else if (thr instanceof SmiLibSdfException) {
                messageBox.showMessageDialog(smiFrame, "An error occured converting SMILES to SDF.\n" + thr.getMessage(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                messageBox.showMessageDialog(smiFrame, thr.toString(), "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
          if (thr instanceof SmiLibConformityException) {
            System.err.println(thr.getMessage());
            System.err.println("An error occured. Program halted.");
          } else if (thr instanceof ReactionSchemeException) {
            System.err.println(thr.getMessage());
            System.err.println("An error occured. Program halted.");
          } else if (thr instanceof SmiLibIOException) {
            System.err.println(thr.getMessage());
            System.err.println("An error occured. Program halted.");
          } else if (thr instanceof StringIndexOutOfBoundsException) {
            System.err.println(thr.getMessage());
            System.err.println("An error occured. Program halted.");
          } else if (thr instanceof NumberFormatException) {
            System.err.println("Invalid reaction scheme.\nPlease check reaction scheme and/or use SmiLib help!");
            System.err.println("An error occured. Program halted.");
          } else if (thr instanceof java.lang.OutOfMemoryError) {
            System.err.println("Java heap size overflow.\nRestart program with access to more memory!");
            System.err.println("An error occured. Program halted.");
            System.exit(1);
          } else if (thr instanceof SmiLibSdfException) {
              System.err.println("An error occured converting SMILES to SDF.");
              System.err.println(thr.getMessage());
              System.err.println("An error occured. Program halted.");
          } else {
            System.err.println(thr.getMessage());
            System.err.println("An error occured. Program halted.");
          }
        }
    }
    
    
    /**
     *Sets whether this thread shall be stopped or not. Is
     *necessary to cancel enumeration in a save way instead of
     *using "Thread.suspend" (deprecated).
     *
     *@param b cancel enumeration true/false
     */
    public void setStop(boolean b) {
        stop = b;
    }
    
    
    /**
     *Returns the SmilesWriter of this class.
     *
     *@return SmilesWriter of this class
     */
    public SmilesWriter getSmilesWriter() {
        return this.smiWri;
    }
}