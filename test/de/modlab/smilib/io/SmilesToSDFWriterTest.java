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
 * SmilesToSDFWriterTest.java
 * JUnit based test
 *
 * Created on 6. Juli 2006, 11:15
 */

package de.modlab.smilib.io;

import junit.framework.*;
import java.io.File;

/**
 *
 * @author Andreas Schueller
 */
public class SmilesToSDFWriterTest extends TestCase {
  
  public SmilesToSDFWriterTest(String testName) {
    super(testName);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
    File outFile = new File("./out.sdf");
    if (outFile.exists())
      outFile.delete();
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(SmilesToSDFWriterTest.class);
    
    return suite;
  }

  /**
   * Test of close method, of class de.modlab.smilib.io.SmilesToSDFWriter.
   */
  public void testClose() throws Exception {
    System.out.println("close");
    // tested in testWriteSMILES();
  }

  /**
   * Test of writeSMILES method, of class de.modlab.smilib.io.SmilesToSDFWriter.
   */
  public void testWriteSMILES() throws Exception {
    System.out.println("writeSMILES");
    
    StringBuilder smiles = new StringBuilder("c%101ccccc1.O%10");
    StringBuilder id = new StringBuilder("1.1_1");
    SmilesToSDFWriter instance = new SmilesToSDFWriter("./out.sdf", false);
    
    try {
      instance.writeSMILES(smiles, id);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception was thrown:" + e.getMessage());
    }
    
    instance.close();
    
    smiles = new StringBuilder("[n+]%101ccccc1.[O-]%10");
    id = new StringBuilder("1.1_1");
    instance = new SmilesToSDFWriter("./out.sdf", false);
    
    try {
      instance.writeSMILES(smiles, id);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception was thrown:" + e.getMessage());
    }
    
    instance.close();

  }


  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }
  
}
