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

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import de.modlab.smilib.main.SmiLib;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *Frame for SmiLib Help.
 *
 * @author  Volker Haehnke
 * @author Andreas Schueller
 */
public class HelpFrame extends javax.swing.JFrame {
    
    //help texts
    private String[] helpContent = new String[] {
        "SmiLib creates combinatorial libraries of molecules from molecule fragments " +
                "given in SMILES code. To create a library, three different kinds of fragments " +
                "are needed: one or more scaffolds, linkers and building blocks. A scaffold " +
                "is a common substructure in each molecule created for the library. Linkers are " +
                "used to connect building blocks to the scaffold.\n\n" +
                "To specify where linkers and building blocks are combined and where they are " +
                "attached to the scaffold, you have to add special substrings to the SMILES of your fragments. " +
                "A scaffold has to contain at least one group like \"[RX]\", X any number between " +
                "1 and 99. As you can see, there is a limitation to 99 groups of that kind. Because " +
                "of the manner SmiLib combines the molecule fragment SMILES, only SMILES can be " +
                "accepted, where \"[RX]\"-groups are connected to only 1 atom by a single bond. Otherwise " +
                "it is possible that the created molecules are senseless in a chemical way. If you use more " +
                "than one of these variable side chains, you should use numbers following each other " +
                "starting with 1. If you don't, SmiLib won't start the enumeration when the \"check SMILES\" " +
                "option in the option menu is set.\n\n" +
                "Linkers need two different kinds of additional substrings: \"[A]\" indicates where the " +
                "connection to the scaffold will be created. As you can imagine, you need one more " +
                "group, which specifies where linkers and building blocks are combined. This point " +
                "is specified by \"[R]\". Similar to scaffolds, these two additional groups have to " +
                "be connected to only one atom by a single bond! There is no number necessary " +
                "because each linker can only be connected with one building block. If you want " +
                "to connect a building block to a scaffold without an additional fragment, you can use " +
                "an empty linker like \"[A][R]\".\n\n" +
                "Building blocks have to include \"[A]\", indicating where the connection to a linker " +
                "will be created. Like all SmiLib specific groups a building block only is accepted when it is " +
                "connected to only 1 atom by a single bond.\n\n" +
                "During the combination between building blocks, linkers and scaffolds, first a " +
                "building block and a linker are connected, producing an intermediate molecule fragment. " +
                "During this process the two SmiLib specific groups \"[A]\" in the building block " +
                "SMILES and \"[R]\" in the linker SMILES are removed. In the next step this " +
                "intermediate gets attached to the scaffold, removing \"[A]\" (originating " +
                "from linker SMILES) from the intermediate and \"[RX]\" from the scaffold.\n" +
                "\n" +
                "To create your combinatorial library you just have to follow these 5 steps:\n" +
                "1. create fragment SMILES you want to use with external tools\n" +
                "2. load or enter SMILES in SmiLib GUI\n" +
                "3. specify the reaction scheme if you don't want to create all combinations\n" +
                "4. choose output format (just view library / save library as SMILES / SDF\n" +
                "5. press \"enumerate\" and enjoy\n",
    "SmiLib can process two different kinds of sources as input. You can choose between molecules " +
                "coded in SMILES with or without identifiers. In the first case, ID (first column) and SMILES (second column) " +
                "have to be separated by TAB.\n\n\n" +
                "Sample SMILES without identifiers:\n\n" +
                "[R1]N2CCN([R2])C\n" +
                "N1[R1]C([R2])CC1" +
                "\n\n\n" +
                "Same SMILES with identifier:\n\n" +
                "s1\t[R1]N2CCN([R2])C\n" +
                "s2\tN1[R1]C([R2])CC1" +
                "\n\n\n" +
                "It is even possible to mix these two formats, soth at some SMILES have an ID " +
                "and some don't. Source SMILES can be entered in the GUI mask, or you can save them as " +
                "plain text files. In the second case file extensions don't matter." +
                "\n\n" +
                "Please ensure that your source SMILES are correct. SmiLib does not " +
                "perform any kind of check whether they are valid SMILES or not, because it doesn't actually simulate " +
                "chemical reactions. The program uses only string operation to concatenate " +
                "the given SMILES. So the results will not be very usefull, if you enter " +
                "wrong or invalid SMILES.",
    "SmiLib can save your combinatorial in two different file formats: as plain text files " +
                "containing ID and SMILES for each created molecule separated by TAB " +
                "or as SD file. In the latter case the Chemistry Development Kit is used " +
                "to compute the atom coordinates (see cdk.sf.net for further informations). " +
                "You can choose whether implicit hydrogens shall be added to the molecules " +
                "or not.\n" +
                "It takes quite a long time to compute the coordinates necessary to write the SD file, " +
                "especially if you choose to add the implicit hydrogens. So you should use " +
                "this output format only if you really need it." +
                "\n\n" +
                "The molecule ID is created either from the identifiers of the source SMILES you " +
                "specified for scaffold, linkers and building blocks used in its creation or, if you " +
                "didn't include an ID in your source data, from their index (line number) in the source file " +
                "ot the GUI mask." +
                "\n\n" +
                "The first part of the ID is the identifier or index of the scaffold. The following parts " +
                "separated by \".\" are the fragments attached to the scaffold as variable side chains. " +
                "They are created from one linker (ID part preceding \"_\") and one building block " +
                "(ID part following \"_\"." +
                "\n\n" +
                "Example ID:\t1.1_2.1_4\n" +
                "\t* Scaffold used: 1\n" +
                "\t* First variable side chain: linker 1, building block 2\n" +
                "\t* Second variable side chain: linker 1, building block 4\n" +
                "\n\n" +
                "If only some of the source SMILES have an identifier, the identifiers available " +
                "will be used to create the ID.",
    "You need a reaction scheme only if you don't want to enumerate the complete combinatorial " +
                "library. In that case you can specify which linkers and building blocks shall be " +
                "used on a specific variable side chain. But be aware that you have to define linkers " +
                "and building blocks for each attachement side!" +
                "\n\n" +
                "Essentially, the reaction scheme is a plain text file containing a lot of TAB separated " +
                "columns of numbers. Example:" +
                "\n\n" +
                "1\t2-8\t7;35\n" +
                "2-3;9\t1-4;6-8\t10\n" +
                "\n" +
                "The first column specifies the scaffold. So in our first example the fist line stands for " +
                "the first scaffold in the source file or GUI mask. The following columns specify alternating the linkers " +
                "and building blocks used for each attachement side. Columns 2 and 3 define the linkers and " +
                "building blocks used on the first attachement side, columns 4 and 5 the linkers and building " +
                "blocks and linkers used on the second variable side chain... and so on. So in the " +
                "example, scaffold 1 (with 1 attachement side) is combined with intermediates created " +
                "from linkers 2-8 (2,3,4,5,6,7,8) and linkers 7 and 35.\n\n" +
                "As you can see, it is possible to define ranges of fragments that shall be used using " +
                "\"-\". \"1-45\" means that fragments between index 1 and 45 shall be used, including " +
                "1 and 45. The indices of single molecule parts are separated by \";\". Fragments at " +
                "indices 2 and 35 will be used if you write \"2;35\". It is even possible to combine ranges " +
                "in that way, so if you write \"1-10;27;35-43\", the range 1 to 10, fragment 27 and the range " +
                "35 to 43 will be used.\n\n" +
                "You can use this syntax on scaffolds, linkers and building blocks.",
    "If you want to enumerate a large library and save the result to a file, deactivate the option " +
                "\"view library\". That will speed up the enumeration because no CPU power is " +
                "wasted on displaing the result.\n\n" +
                "If you want to create a really large library, you should only save the result as file. " +
                "Otherwise the complete library will be written in your system memory eventually " +
                "causing a \"Java Heap Size Overflow\" - and that's really a bad thing!" +
                "\n\n" +
                "If you want to create an SD file, only add hydrogens if really necessary. The originally " +
                "implicit hydrogens are quite a number of atoms, so that the calculation of coordinates " +
                "will take much longer!" +
                "\n\n" +
                "SmiLib perfoms some checks on your source SMILES to ensure that the SmiLib specific groups " +
                "are just connected to 1 atom and by a single bond, to ensure that all groups needed are " +
                "included and that no invalid symbols are used. If you want this tests not to be performed for " +
                "some reasons, you can disable them by simply deselecting \"Options/check Smiles\"." +
                "\n\n" +
                "You can add/remove the empty linker by selecting/deselecting the checkbox below the linker " +
                "GUI element.",
    "Copyright (c) 2006, Johann Wolfgang Goethe-Universitaet, Frankfurt am Main, " +
                "Germany. All rights reserved." + SmiLib.nl +
                SmiLib.nl +
                "Redistribution and use in source and binary forms, with or without modification, " +
                "are permitted provided that the following conditions are met:" + SmiLib.nl +
                SmiLib.nl +
                "* Redistributions of source code must retain the above copyright notice, this " +
                "list of conditions and the following disclaimer." + SmiLib.nl +
                "* Redistributions in binary form must reproduce the above copyright notice, " +
                "this list of conditions and the following disclaimer in the documentation " +
                "and/or other materials provided with the distribution." + SmiLib.nl +
                "* Neither the name of the Johann Wolfgang Goethe-Universitaet, Frankfurt am " +
                "Main, Germany nor the names of its contributors may be used to endorse or " +
                "promote products derived from this software without specific prior written " +
                "permission." + SmiLib.nl +
                SmiLib.nl +
                "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND " +
                "ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED " +
                "WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE " +
                "DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR " +
                "ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES " +
                "(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; " +
                "LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON " +
                "ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT " +
                "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS " +
                "SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."};
    
    //help headers
    private String[] header = new String[] {"How to Use", "Input File Formats", "Output File Formats", "Reaction Scheme", "Tipps & Tricks", "The SmiLib License"};
    
    
    
    /** Creates new form HelpFrame */
    public HelpFrame() {
        
        //custom look and feel
        PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
        try {
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        initComponents();
        this.setVisible(false);
        this.center();
    }
    
    
    /**
     *Centers busy window on screen.
     */
    private void center() {
        java.awt.Dimension frameSize = this.getSize();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(frameSize);
        setLocation(((screenSize.width - frameSize.width) / 2), ((screenSize.height - frameSize.height) / 2));
    }
    
    
    /**
     *Displays a specific help text.
     *
     *@param i index of text in source array
     */
    public void showHelp(int i) {
        jTextArea1.setText(helpContent[i]);
        jLabel1.setText(header[i]);
        this.setVisible(true);
        this.jTextArea1.select(0,0);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jPanel1 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JSeparator();
    jSeparator2 = new javax.swing.JSeparator();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem3 = new javax.swing.JMenuItem();
    jMenuItem4 = new javax.swing.JMenuItem();
    jMenuItem5 = new javax.swing.JMenuItem();
    jMenuItem6 = new javax.swing.JMenuItem();
    jSeparator3 = new javax.swing.JSeparator();
    jMenuItem7 = new javax.swing.JMenuItem();

    setTitle("SmiLib v2.0 - Help");
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jLabel1.setText("jLabel1");

    jScrollPane1.setBorder(null);
    jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    jTextArea1.setColumns(20);
    jTextArea1.setEditable(false);
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setBorder(null);
    jScrollPane1.setViewportView(jTextArea1);

    jButton1.setText("Close");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jPanel1.add(jButton1);

    jMenu1.setText("File");
    jMenuItem1.setText("Exit");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });

    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Topics");
    jMenuItem2.setText("How to Use");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem2);

    jMenuItem3.setText("Input File Formats");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem3);

    jMenuItem4.setText("Output File Formats");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem4);

    jMenuItem5.setText("Reaction Scheme");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem5);

    jMenuItem6.setText("Tipps & Tricks");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem6ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem6);

    jMenu2.add(jSeparator3);

    jMenuItem7.setText("License");
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem7ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem7);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(jLabel1)
          .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
          .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
          .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
          .add(jSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(27, 27, 27)
        .add(jLabel1)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(14, 14, 14)
        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        showHelp(5);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        showHelp(4);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        showHelp(3);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        showHelp(2);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        showHelp(1);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        showHelp(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed
        
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
  private javax.swing.JMenuItem jMenuItem7;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JTextArea jTextArea1;
  // End of variables declaration//GEN-END:variables
    
}
