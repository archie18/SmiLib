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
import de.modlab.smilib.iterator.SmiLibIterator;
import de.modlab.smilib.main.SmiLibRunner;
import edu.stanford.ejalbert.BrowserLauncher;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main frame of the GUI for SmiLib.
 *
 * @author  Volker Haehnke
 * @author  Andreas Schueller
 */
public class SmiLibFrame extends javax.swing.JFrame {
  
  /** JOptionPane which dislays messages */
  private javax.swing.JOptionPane messageBox;
  
  /** Menu that holds the SmiLib logo */
  private javax.swing.JMenu logoMenu;
  
  /** panel with source SMILES for molecules (scaff, linker and bb) */
  private SourcePanel source;
  
  /** panel containing the control elements */
  private ControlPanel control;
  
  /** panel where the combinatorial library will be shown */
  private LibraryPanel library;
  
  /** thread that contains methods to enumerate the library */
  private SmiLibRunner sRunner;
  
  /** boolean necessary to switch between library/source panel
   *  true: source      false: library */
  private boolean viewState = true;
  
  /** current working directory - starts from folder that contains program */
  private String cwd = ".";
  
  /** status window shown while library is created */
  private BusyWindow busy;
  
  /** frame for SmiLib help */
  private HelpFrame help = new HelpFrame();
  
  /** frame for the about box */
  private HelpAboutFrame about;
  
  /** SmiLib iterator over virtual reactions */
  private SmiLibIterator iterator;
  
  /** boolean that indicates whether the enumeration was stopped by the user or not */
  private boolean enumerationStopped = false;
  
  /** check SMILES for SmiLib rule conformity true/false */
  private boolean checkSmiles = true;
  
  /** enumeration cancelled because of an error true/false */
  private boolean errorOccured = false;
  
  /** enumeration was first enumeration true/false */
  private boolean firstEnumeration = true;
  
  /** limit of molecules - if you show more in the GUI you may provoke a Java Heap Size Overflow */
  private int javaHeapSizeOverflowLimit = 75000;
  
  
  /**
   * Creates new form SmiLibFrame
   */
  public SmiLibFrame() {
    //third party look and feel
    PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
    try {
      UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
    } catch (UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    
    //init
    this.initComponents();
    
    //custom panels are added
    this.addCustomComponents();
    
    //frame gets centered
    this.center();
    
    //frame gets shown
    this.setVisible(true);
    
    //init of message box
    messageBox = new javax.swing.JOptionPane();
  }
  
  
  /**
   * Custom labels get initalised and are added to the frame.
   */
  private void addCustomComponents() {
    source = new SourcePanel(this);
    control = new ControlPanel(this);
    library = new LibraryPanel();
    logoMenu = new JButtonMenu();
    about = new HelpAboutFrame();
    about.setHelpFrame(help);
    
    this.setLayout(new BorderLayout());
    
    // Adds a MouseListener to the logoMenu to be able to react on mousePressed events.
    logoMenu.addMouseListener(new java.awt.event.MouseListener() {
      public void mouseClicked(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
      public void mousePressed(MouseEvent e) {
        logoMenuMousePressed(e);
      }
      public void mouseReleased(MouseEvent e) {
      }
    });
    
    // Layout the logo menu
    logoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/modlab/smilib/resources/logo11_evensmaller.jpg")));
    logoMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
    logoMenu.setIconTextGap(0);
    logoMenu.setMargin(new java.awt.Insets(0, 0, 0, 0));
    logoMenu.setArmed(false);
    logoMenu.setRolloverEnabled(false);
    
    // Add the logo menu to the menu bar with right alignment
    jMenuBar1.add(javax.swing.Box.createHorizontalGlue());
    jMenuBar1.add(logoMenu);
    
    this.getContentPane().add(source, BorderLayout.NORTH);
    source.setVisible(true);
    
    this.getContentPane().add(control, BorderLayout.CENTER);
    control.setVisible(true);
    
    library.setVisible(false);
    
    pack();
    repaint();
  }
  
  /**
   * Opens the about box
   * @param mouseEvent the mouseEvent that occurred
   */
  private void logoMenuMousePressed(MouseEvent mouseEvent) {
     if (about == null)
      about = new HelpAboutFrame();
     about.setVisible(true);
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
  
  
  /**
   *Resets all GUI Elements to their default values.
   */
  private void reset() {
    enumerationStopped = false;
    errorOccured = false;
    checkSmiles = true;
    firstEnumeration = true;
    jCheckBoxMenuItem1.setSelected(true);
    
    if(!viewState) {
      toggleView();
    }
    
    source.reset();
    control.reset();
    library.reset();
  }
  
  /**
   *All GUI elements get deactivated, so that the user is not able
   *to use them.
   */
  public void deactivateGui() {
    source.deactivateGui();
    control.deactivateGui();
  }
  
  
  /**
   *All GUI elements get activated.
   */
  public void activateGui() {
    source.activateGui();
    control.activateGui();
  }
  
  
  /**
   * Enables start of enumeration.
   */
  public void enableEnumeration() {
    if (source.getAllSourcesAvailable() & control.saveOptionsSet()) {
      control.enableStartButton(true);
    } else {
      control.enableStartButton(false);
    }
  }
  
  
  /**
   *Starts enumeration of a combinatorial library.
   */
  public void enumerateLibrary() {
    
    int returnVal = 0;
    boolean preview = false;
    
    //till now no error and process not cancelled
    errorOccured = false;
    enumerationStopped = false;
    
    if (!firstEnumeration) {
      returnVal = messageBox.showConfirmDialog(this,
              "You're on the way to start a new enumeration.\nThis might overwrite your previous library.\nDo you want to continue?",
              "Create new library?",
              javax.swing.JOptionPane.YES_NO_OPTION,
              javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    if (firstEnumeration || (returnVal == javax.swing.JOptionPane.OK_OPTION)) {
      
      //reset the iterator
      iterator = null;

      //new thread created
      sRunner = new SmiLibRunner(
              source.getScaffolds(),
              source.getLinkers(),
              source.getBuildingBlocks(),
              source.useReactionScheme(),
              source.getReactionScheme(),
              library.getTargetTextArea(),
              control.getShowLibrary(),
              control.getSaveAsFile(),
              control.getSaveAsSDF(),
              control.getAddHydrogens(),
              control.getFileName(),
              this,
              checkSmiles);
      
      //iterator is null, when en error occured duing preprocessing
      if (iterator != null) {
        if (control.getShowLibrary() & iterator.getMaximum() > javaHeapSizeOverflowLimit) {
          Object[] options = {"Just show preview",
          "Show complete library",
          "Cancel"};
          returnVal = messageBox.showOptionDialog(this,
                  "The number of molecules in the library you want to create and display in the GUI\n" +
                  "might provoke a \"Java Heap Size Overflow\". You can choose to diplsay just a preview of\n" +
                  "your library (first " + javaHeapSizeOverflowLimit + " molecules). If you reserved enough memory for your VM you can\n" +
                  "continue with the enumeration showing the complete library. If you want to change your\n" +
                  "settings you can cancel enumeration." ,
                  "Warning - very large library",
                  javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                  javax.swing.JOptionPane.WARNING_MESSAGE,
                  null,
                  options,
                  options[0]);
          if (returnVal == javax.swing.JOptionPane.OK_OPTION) {
            preview = true;
          }
        }
        
        if (returnVal == javax.swing.JOptionPane.OK_OPTION || returnVal == javax.swing.JOptionPane.NO_OPTION) {
          
          //GUI gets freezed, so that no change can be performed
          this.deactivateGui();
          
          //reset of library panel
          library.reset();
          
          if (preview) {
            sRunner.getSmilesWriter().showPreview(javaHeapSizeOverflowLimit);
            library.setTitle("Library - preview (" + javaHeapSizeOverflowLimit + " molecules)");
          }
          
          //thread gets started
          Thread libraryEnumeration = new Thread(sRunner);
          
          //if no error occured the status window is shown
          if (iterator != null & !errorOccured) {
            busy = new BusyWindow(iterator, this);
          }
          
          //process gets started
          try {
            libraryEnumeration.start();
          } catch (Exception exc) {
            messageBox.showMessageDialog(this, "Enumeration of combinatorial library was cancelled by user","Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
          }
          
          //all following enumerations can't be the first one, if the first one was successfull
          if (!enumerationStopped) {
            firstEnumeration = false;
          }
        }
      }
    }
  }
  
  
  /**
   *Reactivates the GUI if an error occurs during the enumeration.
   */
  public void emergencyShutdown() {
    if (busy != null) {
      busy.dispose();
    }
    errorOccured = true;
    this.activateGui();
  }
  
  
  /**
   *Shows a message, that enumeration is completed or that it has been
   *interrupted by the user.
   */
  public void showEndMessage(long start, long end) {
    if (busy != null) {
      busy.dispose();
    }
    
    javax.swing.JOptionPane messageBox = new javax.swing.JOptionPane();
    
    if (enumerationStopped)
      messageBox.showMessageDialog(this, "Enumeration of combinatorial library was cancelled by user\n" +
              "after " + new DecimalFormat("###,###,###,##0.###").format((double)(end - start)/1000000000) + " sec", "Cancelled",
              javax.swing.JOptionPane.WARNING_MESSAGE);
    else if (errorOccured)
      messageBox.showMessageDialog(this, "Enumeration of combinatorial library was cancelled by error\n" +
              "after " + new DecimalFormat("###,###,###,##0.###").format((double)(end - start)/1000000000) + " sec","Cancelled",
              javax.swing.JOptionPane.ERROR_MESSAGE);
    else
      messageBox.showMessageDialog(this, "Combinatorial Library successfully created.\n" +
              "Time for enumeration: " + new DecimalFormat("###,###,###,##0.###").format((double)(end - start)/1000000000) + " sec",
              "Finished",
              javax.swing.JOptionPane.INFORMATION_MESSAGE);
    
    if (viewState & control.getShowLibrary()) {
      toggleView();
    }
    this.activateGui();
  }
  
  
  /**
   *Loads examples in the GUI mask.
   */
  private void loadExample(int ex) {
    source.loadExample(ex);
  }
  
  
  /**
   *Enumeration of the combinatorial library is stopped by fullfilling a
   *specific criterion in the thread that enumerates it.
   */
  public void stopEnumeration() {
    sRunner.setStop(true);
    enumerationStopped = true;
  }
  
  
  /**
   *Switches between the two different visible panels.
   */
  public void toggleView() {
    if (viewState) {
      this.getContentPane().remove(source);
      this.getContentPane().add(library, BorderLayout.CENTER);
      library.setVisible(true);
      source.setVisible(false);
    } else {
      this.getContentPane().remove(library);
      this.getContentPane().add(source, BorderLayout.CENTER);
      source.setVisible(true);
      library.setVisible(false);
    }
    control.toggleViewButton(viewState);
    viewState = !viewState;
    repaint();
  }
  
  
  /**
   *Progress shown in the status window and libryry panel is incrased.
   */
  public void increaseProgress() {
    busy.increaseProgress();
    if (control.getShowLibrary()) {
      library.increaseProgress();
    }
  }
  
  
  /**
   *Sets the current working directory.
   *
   *@param d current working directory
   */
  public void setCWD(String d)  {
    cwd = d;
  }
  
  
  /**
   *Returns the current working directory.
   *
   *@return current working directory
   */
  public String getCWD() {
    return this.cwd;
  }
  
  
  /**
   *Shows some statistics (number of scaffolds, linkers and
   *building blocks in GUI.
   *
   *@param scaffolds number of scaffolds
   *@param linkers number of linkers
   *@param bBlocks humber of building blocks
   */
  public void setStatistics(int scaffolds, int linkers, int bBlocks) {
    library.setStatistics(scaffolds, linkers, bBlocks);
  }
  
  
  /**
   *Shows time for enumeration in GUI.
   *
   *@param start start time in nano seconds counted from 1970
   *@param end  end time in nano seconds counted from 1970
   */
  public void setTime(long start, long end) {
    library.setTime(start, end);
  }
  
  
  /**
   *Returns which panel is shown in GUI.
   *
   *@return source panel shown true/false
   */
  public boolean getViewState() {
    return this.viewState;
  }
  
  
  /**
   *Sets Iterator used for enumeration.
   *
   *@param iterator Iterator used in enumeration
   */
  public void setIterator(SmiLibIterator iterator) {
    this.iterator = iterator;
  }
  
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    jLabel1 = new javax.swing.JLabel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem7 = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JSeparator();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu4 = new javax.swing.JMenu();
    jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
    jMenu3 = new javax.swing.JMenu();
    jMenuItem9 = new javax.swing.JMenuItem();
    jMenuItem10 = new javax.swing.JMenuItem();
    jMenuItem11 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem5 = new javax.swing.JMenuItem();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem6 = new javax.swing.JMenuItem();
    jMenuItem3 = new javax.swing.JMenuItem();
    jMenuItem8 = new javax.swing.JMenuItem();
    jSeparator3 = new javax.swing.JSeparator();
    jMenuItem12 = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JSeparator();
    jMenuItem4 = new javax.swing.JMenuItem();

    jLabel1.setText("jLabel1");

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("SmiLib v2.0 - Create your own combinatorial library!");
    setResizable(false);
    jMenu1.setText("File");
    jMenuItem7.setText("Reset");
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem7ActionPerformed(evt);
      }
    });

    jMenu1.add(jMenuItem7);

    jMenu1.add(jSeparator2);

    jMenuItem1.setText("Exit");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });

    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu4.setText("Options");
    jCheckBoxMenuItem1.setSelected(true);
    jCheckBoxMenuItem1.setText("check SMILES");
    jCheckBoxMenuItem1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jCheckBoxMenuItem1ItemStateChanged(evt);
      }
    });

    jMenu4.add(jCheckBoxMenuItem1);

    jMenuBar1.add(jMenu4);

    jMenu3.setText("Examples");
    jMenuItem9.setText("Example 1");
    jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem9ActionPerformed(evt);
      }
    });

    jMenu3.add(jMenuItem9);

    jMenuItem10.setText("Example 2");
    jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem10ActionPerformed(evt);
      }
    });

    jMenu3.add(jMenuItem10);

    jMenuItem11.setText("Example 3");
    jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem11ActionPerformed(evt);
      }
    });

    jMenu3.add(jMenuItem11);

    jMenuBar1.add(jMenu3);

    jMenu2.setText("Help");
    jMenuItem5.setText("How to Use");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem5);

    jMenuItem2.setText("Input File Formats");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem2);

    jMenuItem6.setText("Output File Formats");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem6ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem6);

    jMenuItem3.setText("Reaction Scheme");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem3);

    jMenuItem8.setText("Tipps & Tricks");
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem8ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem8);

    jMenu2.add(jSeparator3);

    jMenuItem12.setText("License");
    jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem12ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem12);

    jMenu2.add(jSeparator1);

    jMenuItem4.setText("About");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }
    });

    jMenu2.add(jMenuItem4);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
    help.showHelp(5);
  }//GEN-LAST:event_jMenuItem12ActionPerformed
  
    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
      loadExample(2);
    }//GEN-LAST:event_jMenuItem11ActionPerformed
    
    private void jCheckBoxMenuItem1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ItemStateChanged
      if (jCheckBoxMenuItem1.isSelected())
        checkSmiles = true;
      else
        checkSmiles = false;
    }//GEN-LAST:event_jCheckBoxMenuItem1ItemStateChanged
    
    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
      loadExample(1);
    }//GEN-LAST:event_jMenuItem10ActionPerformed
    
    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
      loadExample(0);
    }//GEN-LAST:event_jMenuItem9ActionPerformed
    
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
      help.showHelp(4);
    }//GEN-LAST:event_jMenuItem8ActionPerformed
    
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
      this.reset();
    }//GEN-LAST:event_jMenuItem7ActionPerformed
    
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
      help.showHelp(0);
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
      help.showHelp(3);
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
      help.showHelp(2);
    }//GEN-LAST:event_jMenuItem6ActionPerformed
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      help.showHelp(1);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
     if (about == null)
      about = new HelpAboutFrame();
      about.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
      System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
    /**
     * Main method of SmiLibFrame.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
          new SmiLibFrame().setVisible(true);
        }
      });
    }
    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem10;
  private javax.swing.JMenuItem jMenuItem11;
  private javax.swing.JMenuItem jMenuItem12;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
  private javax.swing.JMenuItem jMenuItem7;
  private javax.swing.JMenuItem jMenuItem8;
  private javax.swing.JMenuItem jMenuItem9;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  // End of variables declaration//GEN-END:variables
  
}
