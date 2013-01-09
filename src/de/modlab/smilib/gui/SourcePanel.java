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

package de.modlab.smilib.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;

/**
 *GUI element for SmiLib GUI where source SMILES can be entered.
 *
 * @author  Volker Haehnke
 */
public class SourcePanel extends javax.swing.JPanel {
    
    //SmiLib GUI frame
    private SmiLibFrame parentFrame;
    
    //scaffold string available true/false
    private boolean scaffoldsAvailable = false;
    
    //linker strings available true/false
    private boolean linkersAvailable = false;
    
    //building block strings available true/false
    private boolean buildingBlocksAvailable = false;
    
    //reaction scheme available true/false
    private boolean reactionSchemeAvailable = false;
    
    //reaction scheme required (not complete enumeration) true/false
    private boolean reactionSchemeRequired = false;
    
    //include empty linker in linkers true/false
    private boolean includeEmptyLinker = false;
    
    //all sources available true/false
    private boolean allSourcesAvailable = false;
    
    //possible strings for empty linker
    private String[] emptyLinkers = new String[] {  "[A][R1]",  "[R1][A]", "[A]([R1])", "[R1]([A])", 
                                                                        "([A])[R1]", "([R1])[A]", "([A])([R1])", "([R1])([A])",
                                                                        "[A][R]", "[R][A]", "[A]([R])", "[R]([A])",
                                                                        "([A])[R]", "([R])[A]", "([A])([R])", "([R])([A])",
                                                                        "[A]-[R1]",  "[R1]-[A]", "[A]-([R1])", "[R1]-([A])",
                                                                        "([A])-[R1]", "([R1])-[A]", "([A])-([R1])", "([R1])-([A])",
                                                                        "[A]-[R]", "[R]-[A]", "[A]-([R])", "[R]-([A])",
                                                                        "([A])-[R]", "([R])-[A]", "([A])-([R])", "([R])-([A])"};
    
            
    
    /**
     * Creates new form SourcePanel
     * @param parent SmiLibFrame that contains this source panel.
     */
    public SourcePanel(SmiLibFrame parent) {        
        this.parentFrame = parent;
        
        initComponents();
    }
    
    
    /**
     *GUI elements available in this panel get deactivated.
     */
    public void deactivateGui() {
        jTextArea4.setEnabled(false);
        jTextArea4.setEditable(false);
        jTextArea5.setEnabled(false);
        jTextArea5.setEditable(false);
        jTextArea6.setEnabled(false);
        jTextArea6.setEditable(false);
        jTextArea7.setEnabled(false);
        jTextArea7.setEditable(false);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        jCheckBox1.setEnabled(false);
    }
    
    
    /**
     *GUI elements available in this panel get activated.
     */
    public void activateGui() {
        jTextArea5.setEnabled(true);
        jTextArea5.setEditable(true);
        jTextArea6.setEnabled(true);
        jTextArea6.setEditable(true);
        jTextArea7.setEnabled(true);
        jTextArea7.setEditable(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        if (reactionSchemeRequired) {
            jButton4.setEnabled(true);
            jTextArea4.setEnabled(true);
            jTextArea4.setEditable(true);
            jLabel7.setEnabled(true);
        } else {
            jButton4.setEnabled(false);
            jTextArea4.setEnabled(false);
            jTextArea4.setEditable(false);
            jLabel7.setEnabled(false);
        }
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jCheckBox1.setEnabled(true);
    }
    
    
    /**
     *GUI elements available in this panel get reset to default values.
     */
    public void reset() {
        jTextArea4.setText("");
        jTextArea5.setText("");
        jTextArea6.setText("");
        jTextArea7.setText("");
        buttonGroup1.remove(jRadioButton1);
        buttonGroup1.remove(jRadioButton2);
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jTextArea4.setEnabled(false);
        jTextArea4.setEditable(false);
        scaffoldsAvailable = false;
        linkersAvailable = false;
        buildingBlocksAvailable = false;
        reactionSchemeRequired = false;
        reactionSchemeAvailable = false;
    }
    
    
    /**
     *Opens a file dialog, so offers the opportunity to load strings from files
     *
     *@param textArea JTextArea in which the loaded strings will be written
     *@param dialogTitle title to display in the JFileChooser
     *@param mode int that indicates which kind of source strings (scaffold/linker/building block) was loaded
     */
    private void load(javax.swing.JTextArea textArea, String dialogTitle) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(dialogTitle);
        if (parentFrame.getCWD() != null) {
            chooser.setCurrentDirectory(new java.io.File(parentFrame.getCWD()));
        }
        int returnVal = chooser.showOpenDialog(parentFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            parentFrame.setCWD(chooser.getCurrentDirectory().getAbsolutePath());
            try {
                BufferedReader buffr = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                String line;
                StringBuilder completeText = new StringBuilder();
                boolean end = false;
                while (!end) {
                    line = buffr.readLine();
                    if (line != null) {
                        completeText.append(line).append('\n');
                    } else
                        end = true;
                }
                buffr.close();
                textArea.setText(completeText.toString().trim());
                textArea.select(1,1);
            } catch (java.io.IOException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    
    /**
     *Adds empty linker to linkers in GUI.
     */
    public void addEmptyLinker() {
        if (includeEmptyLinker & !detectEmptyLinker()) {
            if (jTextArea6.getText().trim().length() == 0)
                jTextArea6.setText("[A][R]");
            else {
                jTextArea6.setText(jTextArea6.getText().trim());
                jTextArea6.append("\n[A][R]");
            }
        }
        jCheckBox1.setSelected(true);
        checkDataAvailability();
    }
    
    
    /**
     *Removes empty linker from linkers in GUI.
     */
    public void removeEmptyLinker() {
        String[] linkers = jTextArea6.getText().split("\n");
        boolean[] delete = new boolean[linkers.length];
  
        for (int i = 0; i < linkers.length; i++) {
            for (int j = 0; j < emptyLinkers.length; j++) {
                if (linkers[i].equals(emptyLinkers[j])) {
                    delete[i] = true;
                }
            }
        }
        
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < delete.length; i++) {
            if (!delete[i]) {
                temp.append(linkers[i]).append("\n");
            }
        }
        
        jCheckBox1.setSelected(false);
        
        jTextArea6.setText(temp.toString().trim());
        jTextArea6.select(0,0);
    }
    
    
    /**
     *Searches empty linker.
     */
    private boolean detectEmptyLinker() {       
        String[] linkers = jTextArea6.getText().trim().split("\n");
        boolean emptyLinkerFound = false;
  
        for (int i = 0; i < linkers.length; i++) {
            for (int j = 0; j < emptyLinkers.length; j++) {
                if (linkers[i].equals(emptyLinkers[j])) {
                    emptyLinkerFound = true;
                }
            }
        }
        
        if (emptyLinkerFound) {
            jCheckBox1.setSelected(true);
        } else {
            jCheckBox1.setSelected(false);
        }
        
        return emptyLinkerFound;
    }
    
    
    /**
     *Checks whether all needed sources are available and enables
     *the start button in that case.
     */
    private void checkDataAvailability() {
        if (jTextArea5.getText().trim().length() != 0) {
            scaffoldsAvailable = true;
        } else {
            scaffoldsAvailable = false;
        }
        if (jTextArea6.getText().trim().length() != 0) {
            linkersAvailable = true;
        } else {
            linkersAvailable = false;
            jCheckBox1.setSelected(false);
        }
        if (jTextArea7.getText().trim().length() != 0) {
            buildingBlocksAvailable = true;
        } else {
            buildingBlocksAvailable = false;
        } if (reactionSchemeRequired) {
            if (jTextArea4.getText().trim().length() != 0) {
                reactionSchemeAvailable = true;
            } else {
                reactionSchemeAvailable = false;
            }
        }
        
        if (scaffoldsAvailable & linkersAvailable & buildingBlocksAvailable & (!reactionSchemeRequired || (reactionSchemeRequired & reactionSchemeAvailable))) {
            allSourcesAvailable = true;
        } else {
            allSourcesAvailable = false;
        }
        parentFrame.enableEnumeration();
    }
    
    
    /**
     *Enables GUI elements for reaction scheme.
     *
     *@param b enable GUI elements for reaction scheme true/false
     */
    private void enableReactionSchemeWindow(boolean b) {
        jButton4.setEnabled(b);
        jTextArea4.setEnabled(b);
        jTextArea4.setEditable(b);
        jLabel7.setEnabled(b);
        reactionSchemeRequired = b;
    }
    
    
    /**
     *Loads examples to GUI.
     *
     *@param ex index of example
     */
    public void loadExample(int ex) {
        switch (ex) {
            case 0:     jTextArea5.setText("[R1]c1ccccc1");
                            jTextArea6.setText("[A]O[R1]");
                            jTextArea7.setText("[A]CN(C)C\n" +
                                    "[A]C(O)=O");
                            jTextArea4.setText("1\t1\t1-2");
                            jButton4.setEnabled(true);
                            jTextArea4.setEnabled(true);
                            jTextArea4.setEditable(true);
                            jLabel7.setEnabled(true);
                            buttonGroup1.remove(jRadioButton1);
                            buttonGroup1.remove(jRadioButton2);
                            jRadioButton1.setSelected(false);
                            jRadioButton2.setSelected(true);
                            buttonGroup1.add(jRadioButton1);
                            buttonGroup1.add(jRadioButton2);
                            break;
            case 1:     jTextArea5.setText("[R1]N2CCN([R2])C1=CC=CC=C1C2\n[R2]N(C3=C2C=CC=C3)CC12CCN([R1])CC1\n[R1]N1CCN([R2])CC1");
                            jTextArea6.setText("[A][R1]\n" +
                                    "[A]S(=O)(N[R1])=O\n" +
                                    "[A]C(N[R1])=O\n" +
                                    "[A]C(O[R1])=O\n" +
                                    "[A]O[R1]\n" +
                                    "[A]C[R1]");
                            jTextArea7.setText("[A]C\n" +
                                    "C(C[A])(C)(C)C\n" +
                                    "[A]CCOC\n" +
                                    "C1(=CC=C(C=C1)C[A])OC\n" +
                                    "N1(CC[A])CCCCC1\n" +
                                    "C(=O)([O])C[A]\n" +
                                    "[A]C1=CC=CC=C1\n" +
                                    "C(C(F)(F)F)[A]\n" +
                                    "C1=CC(=CC=C1S(=O)(=O)C)[A]\n" +
                                    "N1(C(CSC1C)=O)C[A]");
                            jTextArea4.setText(null);
                            jButton4.setEnabled(false);
                            jTextArea4.setEnabled(false);
                            jTextArea4.setEditable(false);
                            jLabel7.setEnabled(false);
                            buttonGroup1.remove(jRadioButton1);
                            buttonGroup1.remove(jRadioButton2);
                            jRadioButton1.setSelected(true);
                            jRadioButton2.setSelected(false);
                            buttonGroup1.add(jRadioButton1);
                            buttonGroup1.add(jRadioButton2);
                            break;                
            case 2:     jTextArea5.setText( "C1-C-C-C-C-C1-[R1]\n" +
                                                            "N[C@@]([R1])(C)C(=O)O\n" +
                                                            "[R1][C@@](F)(C)C(=O)O\n" +
                                                            "Br[C@@](C)([R1])C(=O)O\n" +
                                                            "Br[C@@](C)(C(=O)O)([R1])\n" +
                                                            "CCCC[R1]\n" +
                                                            "[R1][n+]1ccccc1\n" +
                                                            "[R1][n+]1ccccc1\n" +
                                                            "[OH2+][R1]\n" +
                                                            "C%10.C%10[R1]\n" +
                                                            "C%99.C%99[R1]\n" +
                                                            "CC([R1])CCC([R2])CCC([R3])CC\n" +
                                                            "CC([R2])CCC([R3])CCC([R1])CC\n" +
                                                            "CC([R3])CCC([R2])CCC([R1])CC");
                            jTextArea6.setText( "[A][R]\n" +
                                                            "[A]([R1])\n" +
                                                            "[A]O[R1]\n" +
                                                            "[A][C@@](N)(C)C(=O)O[R1]\n" +
                                                            "[A][C@@]([R1])(C)C(=O)O\n" +
                                                            "[A]-O-[R]");
                            jTextArea7.setText( "[A]N[C@@H](C)C(=O)O\n" +
                                                            "[A][C@@](N)(C)C(=O)O\n" +
                                                            "N[C@@]([A])(C)C(=O)O\n" +
                                                            "[A]F\n" +
                                                            "Br[A]\n" +
                                                            "[A]C\n" +
                                                            "[A]-Cl\n" +
                                                            "[O-][A]\n" +
                                                            "[A]O[2H]");
                            jTextArea4.setText(null);
                            jButton4.setEnabled(false);
                            jTextArea4.setEnabled(false);
                            jTextArea4.setEditable(false);
                            jLabel7.setEnabled(false);
                            buttonGroup1.remove(jRadioButton1);
                            buttonGroup1.remove(jRadioButton2);
                            jRadioButton1.setSelected(true);
                            jRadioButton2.setSelected(false);
                            buttonGroup1.add(jRadioButton1);
                            buttonGroup1.add(jRadioButton2);
                            break;                
        }
        jTextArea4.select(0,0);
        jTextArea5.select(0,0);
        jTextArea6.select(0,0);
        jTextArea7.select(0,0);
        checkDataAvailability();
        detectEmptyLinker();
    }
    
    
    /**
     *Returns scaffold as array of strings, including the IDs.
     *
     *@return array of scaffold strings including ID is specified
     */
    public String[] getScaffolds() {
        return jTextArea5.getText().trim().split("\n");
    }
    
    
    /**
     *Returns linkers as array of strings, including the IDs.
     *
     *@return array of linker strings including ID is specified
     */
    public String[] getLinkers() {
        return jTextArea6.getText().trim().split("\n");
    }
    
    
    /**
     *Returns building block as array of strings, including the IDs.
     *
     *@return array of building block strings including ID is specified
     */
    public String[] getBuildingBlocks() {
        return jTextArea7.getText().trim().split("\n");
    }
    
    
    /**
     *Returns reaction scheme as array of strings.
     *
     *@return reaction scheme as array of strings
     */
    public String[] getReactionScheme() {
        return jTextArea4.getText().trim().split("\n");
    }
    
    
    /**
     * Returns whether all necessary sources are available or not.
     * @return all necessary sources available true/false
     */
    public boolean getAllSourcesAvailable() {
        return this.allSourcesAvailable;
    }
    
    
    /**
     *Returns whether a reaction scheme shall be used or not.
     *
     *@return use reaction scheme true/false
     */
    public boolean useReactionScheme() {
        return this.reactionSchemeRequired;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setMinimumSize(new java.awt.Dimension(860, 410));
        setPreferredSize(new java.awt.Dimension(860, 410));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Scaffolds"));
        jLabel1.setText("Please enter scaffold molecules in SMILES");
        jLabel1.setDoubleBuffered(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setText("format.");
        jLabel2.setDoubleBuffered(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jScrollPane5.setDoubleBuffered(true);
        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jTextArea5.setDoubleBuffered(true);
        jTextArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea5KeyReleased(evt);
            }
        });

        jScrollPane5.setViewportView(jTextArea5);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 240, 110));

        jButton1.setText("load");
        jButton1.setDoubleBuffered(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel5.add(jButton1);

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 240, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 240));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Linkers"));
        jLabel3.setText("Please enter linker molecules in SMILES");
        jLabel3.setDoubleBuffered(true);
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel4.setText("format.");
        jLabel4.setDoubleBuffered(true);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jScrollPane6.setDoubleBuffered(true);
        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jTextArea6.setDoubleBuffered(true);
        jTextArea6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea6KeyReleased(evt);
            }
        });

        jScrollPane6.setViewportView(jTextArea6);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 240, 110));

        jButton2.setText("load");
        jButton2.setDoubleBuffered(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel6.add(jButton2);

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 240, 30));

        jCheckBox1.setText("use empty Linker");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 280, 240));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Building Blocks"));
        jLabel5.setText("Please enter building block molecules in");
        jLabel5.setDoubleBuffered(true);
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel6.setText("SMILES format.");
        jLabel6.setDoubleBuffered(true);
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jScrollPane7.setDoubleBuffered(true);
        jTextArea7.setColumns(20);
        jTextArea7.setRows(5);
        jTextArea7.setDoubleBuffered(true);
        jTextArea7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea7KeyReleased(evt);
            }
        });

        jScrollPane7.setViewportView(jTextArea7);

        jPanel3.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 240, 110));

        jButton3.setText("load");
        jButton3.setDoubleBuffered(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel7.add(jButton3);

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 240, 30));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 280, 240));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Reaction Scheme"));
        jScrollPane4.setDoubleBuffered(true);
        jTextArea4.setColumns(20);
        jTextArea4.setEditable(false);
        jTextArea4.setRows(5);
        jTextArea4.setDoubleBuffered(true);
        jTextArea4.setEnabled(false);
        jTextArea4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea4KeyReleased(evt);
            }
        });

        jScrollPane4.setViewportView(jTextArea4);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 530, 100));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("enumerate complete library");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setDoubleBuffered(true);
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton1ItemStateChanged(evt);
            }
        });

        jPanel4.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("use reaction scheme");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setDoubleBuffered(true);
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton2ItemStateChanged(evt);
            }
        });

        jPanel4.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel7.setText("Please enter a reaction scheme defining the combinatorial library.");
        jLabel7.setDoubleBuffered(true);
        jLabel7.setEnabled(false);
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        jButton4.setText("load");
        jButton4.setDoubleBuffered(true);
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel8.add(jButton4);

        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 240, 30));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 860, 170));

    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            includeEmptyLinker = true;
            this.addEmptyLinker();
        } else {
            includeEmptyLinker = false;
            this.removeEmptyLinker();
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton1ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            enableReactionSchemeWindow(false);
            reactionSchemeRequired = false;
        } else {
            enableReactionSchemeWindow(true);
            reactionSchemeRequired = true;
        }
        checkDataAvailability();
    }//GEN-LAST:event_jRadioButton1ItemStateChanged

    private void jTextArea4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea4KeyReleased
         checkDataAvailability();
    }//GEN-LAST:event_jTextArea4KeyReleased

    private void jTextArea7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea7KeyReleased
         checkDataAvailability();
    }//GEN-LAST:event_jTextArea7KeyReleased

    private void jTextArea6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea6KeyReleased
         checkDataAvailability();
         detectEmptyLinker();
    }//GEN-LAST:event_jTextArea6KeyReleased

    private void jTextArea5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea5KeyReleased
        checkDataAvailability();
    }//GEN-LAST:event_jTextArea5KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        load(jTextArea4, "Select source file for Reaction Scheme");
        checkDataAvailability();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        load(jTextArea7, "Select source file for Building Block SMILES");
        checkDataAvailability();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        load(jTextArea6, "Select source file for Linker SMILES");
        checkDataAvailability();
        detectEmptyLinker();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.load(jTextArea5, "Select source file for Scaffold SMILES");
        checkDataAvailability();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton2ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            enableReactionSchemeWindow(true);
            reactionSchemeRequired = true;
        } else {
            enableReactionSchemeWindow(false);
            reactionSchemeRequired = false;
        }
        checkDataAvailability();
    }//GEN-LAST:event_jRadioButton2ItemStateChanged
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    // End of variables declaration//GEN-END:variables
    
}
