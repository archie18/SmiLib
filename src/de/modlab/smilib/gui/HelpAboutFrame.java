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

import de.modlab.smilib.main.SmiLib;
import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.BrowserLauncherRunner;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherDefaultErrorHandler;
import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherErrorHandler;
import java.net.URL;
import javax.swing.event.HyperlinkEvent;

/**
 *Frame for SmiLib Help.
 *
 * @author  Volker Haehnke
 * @author  Andreas Schueller
 */
public class HelpAboutFrame extends javax.swing.JFrame {
  
    /** Launcher for lauching a web browser */
    private BrowserLauncher launcher = null;
    
    /** Reference to the HelpFrame for License displaying */
    private HelpFrame helpFrame;
  
    /**
     * Creates new form HelpAboutFrame
     */
    public HelpAboutFrame() {
        initComponents();
        center();
        titleLabel.setText("SmiLib v" + SmiLib.version);
        buildLabel.setText("Build " + SmiLib.buildNumber + " (" + SmiLib.buildDate + ")");
        copyrightTextArea.setText(SmiLib.copyright);
        referenceTextArea.setText(SmiLib.reference);
        jEditorPane1.setText(
                "<font size=\"-2\" face=\"Tahoma, Verdana, sans-serif\">" +
                "<p>The following programs and libraries are included in the SmiLib v2.0 distribution:</p>" +

                "<p><b>AbsoluteLayout</b></p>" +
                "<p>Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.<br>" +
                "Distributed under the terms of the <a href=\"http://java.sun.com/spl.html\">Sun Public License Version 1.0</a>.<br>" +
                "<a href=\"http://www.netbeans.org/\">http://www.netbeans.org/</a></p>" +

                "<p><b>BrowserLauncher2 version 1.0 rc4</b></p>" +
                "<p>Distributed under the terms of the <a href=\"http://www.gnu.org/licenses/lgpl.html\">GNU Lesser General Public License Version 2.1</a>.<br>" +
                "<a href=\"http://browserlaunch2.sourceforge.net/\">http://browserlaunch2.sourceforge.net/</a></p>" +

                "<p><b>The Chemistry Development Kit release 20060714</b></p>" +
                "<p>Copyright (C) 2000-2006 The Chemistry Development Kit (CDK) project.<br>" +
                "Distributed under the terms of the <a href=\"http://www.gnu.org/licenses/lgpl.html\">GNU Lesser General Public License Version 2.1</a>.<br>" +
                "<a href=\"http://cdk.sourceforge.net/\">http://cdk.sourceforge.net/</a></p>" +

                "<p><b>Jakarta Commons CLI library version 1.0</b></p>" +
                "<p>Copyright (c) 1999-2001 The Apache Software Foundation. All rights reserved.<br>" +
                "Distributed under the terms of <a href=\"http://www.apache.org/licenses/LICENSE-1.1\">The Apache Software License, Version 1.1</a>.<br>" +
                "<a href=\"http://jakarta.apache.org/commons/cli/\">http://jakarta.apache.org/commons/cli/</a></p>" +

                "<p><b>JGoodies Looks version 2.0.3</b></p>" +
                "<p>Copyright (c) 2001-2006 JGoodies Karsten Lentzsch. All rights reserved.<br>" +
                "Distributed under the terms of the <a href=\"http://gecco.org.chemie.uni-frankfurt.de/smilib/jgoodies-looks-license.txt\">BSD License for the JGoodies Looks</a>.<br>" +
                "<a href=\"http://www.jgoodies.de/\">http://www.jgoodies.de/</a></p>" +

                "<p><b>Swing Layout Extensions version 1.0</b></p>" +
                "<p>Copyright (C) 2005 Sun Microsystems, Inc. All rights reserved. Use is subject to license terms.<br>" +
                "Distributed under the terms of the <a href=\"http://www.gnu.org/licenses/lgpl.html\">GNU Lesser General Public License Version 2.1</a>.<br>" +
                "<a href=\"http://swing-layout.dev.java.net/\">http://swing-layout.dev.java.net/</a></p>" +
                "</font>");
        jEditorPane1.setCaretPosition(0);
        jEditorPane1.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
          public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
              goToURL(e.getURL());
          }
        });
        
        //init browser launcher
        try {
          launcher = new BrowserLauncher(null);
        } catch (UnsupportedOperatingSystemException ex) {
          ex.printStackTrace();
        } catch (BrowserLaunchingInitializingException ex) {
          ex.printStackTrace();
        }
    }
    
    /**
     * Loads a URL in the default web browser with help of BrowserLauncher2.
     * @param url the URL to load
     */
    private void goToURL(URL url) {
      if (launcher != null) {
        BrowserLauncherErrorHandler errorHandler = new BrowserLauncherDefaultErrorHandler();
        BrowserLauncherRunner runner = new BrowserLauncherRunner(launcher, url.toString(), errorHandler);
        Thread launcherThread = new Thread(runner);
        launcherThread.start();
      }
    }
    
    /**
     * Sets the HelpFrame which is needed for license displaying.
     * @param helpFrame the HelpFrame
     */
    public void setHelpFrame(HelpFrame helpFrame) {
      this.helpFrame = helpFrame;
    }
    
    /**
     *Centers frame on screen.
     */
    private void center() {
        java.awt.Dimension frameSize = this.getSize();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(frameSize);
        setLocation(((screenSize.width - frameSize.width) / 2), ((screenSize.height - frameSize.height) / 2));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        licenseButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        buildLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        copyrightTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        referenceTextArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SmiLib v2.0 - About");
        setResizable(false);

        licenseButton.setText("License");
        licenseButton.setActionCommand("licenseButton");
        licenseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenseButtonActionPerformed(evt);
            }
        });

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(licenseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(90, 90, 90)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(licenseButton)
            .add(jButton1)
        );

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        titleLabel.setText("SmiLib v2.0");

        buildLabel.setText("Build 0 (0000-00-00 00:00:00)");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        copyrightTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        copyrightTextArea.setColumns(20);
        copyrightTextArea.setEditable(false);
        copyrightTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        copyrightTextArea.setLineWrap(true);
        copyrightTextArea.setRows(2);
        copyrightTextArea.setText("Copyright (c) 2006, Johann Wolfgang Goethe-Universität,\nFrankfurt am Main, Germany. All rights reserved.");
        copyrightTextArea.setWrapStyleWord(true);
        copyrightTextArea.setBorder(null);
        jScrollPane2.setViewportView(copyrightTextArea);

        jLabel2.setText("Authors:");

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea3.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        jTextArea3.setColumns(20);
        jTextArea3.setEditable(false);
        jTextArea3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea3.setRows(2);
        jTextArea3.setText("Volker Haehnke, Andreas Schueller,\nDr. Evgeny Byvatov, Prof. Dr. Gisbert Schneider");
        jScrollPane3.setViewportView(jTextArea3);

        jLabel3.setText("Reference:");

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        referenceTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        referenceTextArea.setColumns(40);
        referenceTextArea.setEditable(false);
        referenceTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        referenceTextArea.setLineWrap(true);
        referenceTextArea.setRows(4);
        referenceTextArea.setText("A. Schüller, V. Hähnke, G. Schneider; SmiLib v2.0: A Java-Based Tool for Rapid Combinatorial Library Enumeration, QSAR & Combinatorial Science 2007, 3, 407-410.");
        referenceTextArea.setWrapStyleWord(true);
        jScrollPane4.setViewportView(referenceTextArea);

        jLabel4.setText("Portions copyright:");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jEditorPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html");
        jScrollPane1.setViewportView(jEditorPane1);

        jLabel1.setText("<html><a href=\"http://gecco.org.chemie.uni-frankfurt.de/smilib/\">http://gecco.org.chemie.uni-frankfurt.de/smilib/</a></html>");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(titleLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(buildLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .add(jScrollPane2)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jScrollPane1)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(titleLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buildLabel)
                .add(19, 19, 19)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(15, 15, 15)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .add(14, 14, 14)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void licenseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenseButtonActionPerformed
    this.dispose();
    helpFrame.showHelp(5);
  }//GEN-LAST:event_licenseButtonActionPerformed

  private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
    jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event_jLabel1MouseExited

  private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
    jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
  }//GEN-LAST:event_jLabel1MouseEntered

  private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
    try {
      goToURL(new URL("http://gecco.org.chemie.uni-frankfurt.de/smilib/"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel buildLabel;
    private javax.swing.JTextArea copyrightTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton licenseButton;
    private javax.swing.JTextArea referenceTextArea;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    
}