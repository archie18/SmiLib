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

import javax.swing.JFileChooser;

/**
 *GUI element for SmiLib GUI that contains control elements.
 *
 * @author  Volker Haehnke
 */
public class ControlPanel extends javax.swing.JPanel {
    
    //SmiLib GUI frame
    private SmiLibFrame parentFrame;
    
    //save SMILES library in file true/false
    private boolean saveAsFile = false;
    
    //save SMILES library as plain SMILES true/false
    private boolean saveAsSmiles = true;
    
    //save SMILES library as SD file true/false
    private boolean saveAsSD = false;
    
    //add hydrogens before computing coordinates for SD file
    private boolean addHydrogens = false;
    
    //show created library in GUI
    private boolean showLibrary = true;
    
    
    
    /**
     *Creates new form ControlPanel
     *
     *@param parent SmiLib GUI frame
     */
    public ControlPanel(SmiLibFrame parent) {
        this.parentFrame = parent;
        initComponents();
    }
    
    
    /**
     *GUI elements available in this panel get deactivated.
     */
    public void deactivateGui() {
        jCheckBox1.setEnabled(false);
        jCheckBox2.setEnabled(false);
        jCheckBox3.setEnabled(false);
        jButton1.setEnabled(false);
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        if (showLibrary)
            jButton2.setEnabled(true);
        else
            jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jTextField1.setEnabled(false);
        jTextField1.setEditable(false);
    }
    
    
    /**
     *GUI elements available in this panel get activated.
     */
    public void activateGui() {
        jCheckBox2.setEnabled(true);
        jCheckBox3.setEnabled(true);
        jButton1.setEnabled(true);
        jTextField1.setEditable(true);
        
        if (showLibrary) {
            jButton2.setEnabled(true);
        }
        
        if (saveAsFile) {
            jRadioButton1.setEnabled(true);
            jRadioButton2.setEnabled(true);
            jTextField1.setEnabled(true);
            jButton3.setEnabled(true);
            if (jRadioButton2.isSelected()) {
                jCheckBox1.setEnabled(true);
            }
        }
    }
    
    
    /**
     *GUI elements available in this panel get reset to default values.
     */
    public void reset() {
        saveAsSmiles = true;
        saveAsSD = false;
        addHydrogens = false;
        showLibrary = true;
        saveAsFile = false;
        
        jCheckBox2.setSelected(true);
        jCheckBox3.setSelected(false);
        buttonGroup1.remove(jRadioButton1);
        buttonGroup1.remove(jRadioButton2);
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jCheckBox1.setSelected(false);
        jCheckBox1.setEnabled(false);
        jTextField1.setText("./library.txt");
        jTextField1.setEditable(true);
        jButton2.setEnabled(false);
        jButton2.setText("library >>");
        jButton1.setEnabled(false);
    }
    
    
    /**
     *Updates filename for library file, taking care of the extension and
     *the current working directory.
     */
    private void updateSaveFile() {
        String path = parentFrame.getCWD() + '/';
        String fileName = jTextField1.getText().substring(jTextField1.getText().lastIndexOf('/')+1, jTextField1.getText().lastIndexOf('.'));
        if (saveAsSmiles) {
            jTextField1.setText(path + fileName + ".txt");
        } else if (saveAsSD) {
            jTextField1.setText(path + fileName + ".sdf");
        }
    }
    
    
    /**
     *Activates/deactivates GUI elements to specify the library file.
     *
     *@param b boolean that indicates whether elements shall be activated or
     *deactivated
     */
    private void setFileOptionsAvailability(boolean b) {
        jLabel5.setEnabled(b);
        jLabel6.setEnabled(b);
        jRadioButton1.setEnabled(b);
        jRadioButton2.setEnabled(b);
        jTextField1.setEnabled(b);
        jButton3.setEnabled(b);
        if (!b)
            jCheckBox1.setEnabled(false);
        else
            jCheckBox1.setEnabled(saveAsSD);
    }
    
    
    /**
     *Toggles text on button that switches between source/library view.
     *
     *@param b boolean that indicates which text shall be shown
     */
    public void toggleViewButton(boolean b) {
        if (b) {
            jButton2.setText("<< back");
        } else {
            jButton2.setText("library >>");
        }
    }
    
    
    /**
     *Activates/deactivates the start button.
     *
     *@param b set start button enabled true/false
     */
    public void enableStartButton(boolean b) {
        jButton1.setEnabled(b);
    }
    
    
    /**
     *Returns path/name of library file.
     *
     *@return path/name of library file
     */
    public String getFileName() {
        return this.jTextField1.getText().trim();
    }
    
    
    /**
     *Returns whether library shall be saved as file or not.
     *
     *@return save library as file true/false
     */
    public boolean getSaveAsFile() {
        return this.saveAsFile;
    }
    
    
    /**
     *Returns whether library shall be saved as SD file.
     *
     *@return save library as file true/false
     */
    public boolean getSaveAsSDF() {
        return this.saveAsSD;
    }
    
    
    /**
     *Returns whether implicit hydrogens shall be added to the molecule
     *when the library is saved as SD file.
     *
     *@return add implicit hydrogens true/false
     */
    public boolean getAddHydrogens() {
        return this.addHydrogens;
    }
    
    
    /**
     *Returns whether library shall be displayed in GUI.
     *
     *@return display library in GUI
     */
    public boolean getShowLibrary() {
        return this.showLibrary;
    }
    
    
    /**
     *Returns whether a target for library (GUI or file) is set.
     *
     *@return target for library set true/false
     */
    public boolean saveOptionsSet() {
        boolean returnBool = true;
        
        if (!jCheckBox2.isSelected() & !jCheckBox3.isSelected()) {
            returnBool = false;
        }
        
        return returnBool;
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setPreferredSize(new java.awt.Dimension(860, 111));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));
        jButton1.setText("enumerate");
        jButton1.setDoubleBuffered(true);
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 90, -1));

        jButton2.setText("library >>");
        jButton2.setDoubleBuffered(true);
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 90, -1));

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("show Library");
        jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox2.setDoubleBuffered(true);
        jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox2ItemStateChanged(evt);
            }
        });

        jPanel1.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jCheckBox3.setText("save as File");
        jCheckBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox3.setDoubleBuffered(true);
        jCheckBox3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox3ItemStateChanged(evt);
            }
        });

        jPanel1.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 110));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("File Format"));
        jLabel5.setText("Save as:");
        jLabel5.setDoubleBuffered(true);
        jLabel5.setEnabled(false);
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        jLabel6.setText("Format:");
        jLabel6.setDoubleBuffered(true);
        jLabel6.setEnabled(false);
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("SMILES Code");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setDoubleBuffered(true);
        jRadioButton1.setEnabled(false);
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton1ItemStateChanged(evt);
            }
        });

        jPanel7.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("SD File");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setDoubleBuffered(true);
        jRadioButton2.setEnabled(false);
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton2ItemStateChanged(evt);
            }
        });

        jPanel7.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, -1));

        jCheckBox1.setText("add Hydrogens");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setDoubleBuffered(true);
        jCheckBox1.setEnabled(false);
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jPanel7.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        jTextField1.setText("./library.txt");
        jTextField1.setDoubleBuffered(true);
        jTextField1.setEnabled(false);
        jPanel7.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 240, -1));

        jButton3.setText("browse");
        jButton3.setDoubleBuffered(true);
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel7.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 570, 110));

    }// </editor-fold>//GEN-END:initComponents
    
    private void jCheckBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox3ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            saveAsFile = true;
            setFileOptionsAvailability(true);
        } else {
            saveAsFile = false;
            setFileOptionsAvailability(false);
        }
        updateSaveFile();
        parentFrame.enableEnumeration();
    }//GEN-LAST:event_jCheckBox3ItemStateChanged
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        parentFrame.toggleView();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jCheckBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox2ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            showLibrary = true;
        } else {
            showLibrary = false;
            jButton2.setEnabled(false);
            if (!parentFrame.getViewState()) {
                parentFrame.toggleView();
            }
        }
        parentFrame.enableEnumeration();
    }//GEN-LAST:event_jCheckBox2ItemStateChanged
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        parentFrame.enumerateLibrary();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            addHydrogens = true;
        } else {
            addHydrogens = false;
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select file to save library");
        if (parentFrame.getCWD() != null) {
            chooser.setCurrentDirectory(new java.io.File(parentFrame.getCWD()));
        }
        int returnVal = chooser.showSaveDialog(parentFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            parentFrame.setCWD(chooser.getCurrentDirectory().getAbsolutePath());
            jTextField1.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton1ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            jCheckBox1.setEnabled(false);
            saveAsSmiles = true;
        } else {
            saveAsSmiles = false;
        }
        updateSaveFile();
    }//GEN-LAST:event_jRadioButton1ItemStateChanged
    
    private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton2ItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            jCheckBox1.setEnabled(true);
            saveAsSD = true;
        } else {
            saveAsSD = false;
        }
        updateSaveFile();
    }//GEN-LAST:event_jRadioButton2ItemStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
