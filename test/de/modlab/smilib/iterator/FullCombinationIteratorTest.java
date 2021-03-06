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
 * FullCombinationIteratorTest.java
 * JUnit based test
 *
 * Created on May 17, 2006, 12:40 PM
 */

package de.modlab.smilib.iterator;

import junit.framework.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Andreas Schueller
 */
public class FullCombinationIteratorTest extends TestCase {
  
  int numLinkers;
  int numBBlocks;
  int[] numRGroups;
  
  int[][] expected = new int[][] {
    {0, 0, 0, 0, 0},
    {0, 0, 0, 0, 1},
    {0, 0, 0, 0, 2},
    {0, 0, 0, 0, 3},
    {0, 0, 0, 1, 0},
    {0, 0, 0, 1, 1},
    {0, 0, 0, 1, 2},
    {0, 0, 0, 1, 3},
    {0, 0, 0, 2, 0},
    {0, 0, 0, 2, 1},
    {0, 0, 0, 2, 2},
    {0, 0, 0, 2, 3},
    {0, 0, 0, 3, 0},
    {0, 0, 0, 3, 1},
    {0, 0, 0, 3, 2},
    {0, 0, 0, 3, 3},
    {0, 0, 1, 0, 0},
    {0, 0, 1, 0, 1},
    {0, 0, 1, 0, 2},
    {0, 0, 1, 0, 3},
    {0, 0, 1, 1, 0},
    {0, 0, 1, 1, 1},
    {0, 0, 1, 1, 2},
    {0, 0, 1, 1, 3},
    {0, 0, 1, 2, 0},
    {0, 0, 1, 2, 1},
    {0, 0, 1, 2, 2},
    {0, 0, 1, 2, 3},
    {0, 0, 1, 3, 0},
    {0, 0, 1, 3, 1},
    {0, 0, 1, 3, 2},
    {0, 0, 1, 3, 3},
    {0, 1, 0, 0, 0},
    {0, 1, 0, 0, 1},
    {0, 1, 0, 0, 2},
    {0, 1, 0, 0, 3},
    {0, 1, 0, 1, 0},
    {0, 1, 0, 1, 1},
    {0, 1, 0, 1, 2},
    {0, 1, 0, 1, 3},
    {0, 1, 0, 2, 0},
    {0, 1, 0, 2, 1},
    {0, 1, 0, 2, 2},
    {0, 1, 0, 2, 3},
    {0, 1, 0, 3, 0},
    {0, 1, 0, 3, 1},
    {0, 1, 0, 3, 2},
    {0, 1, 0, 3, 3},
    {0, 1, 1, 0, 0},
    {0, 1, 1, 0, 1},
    {0, 1, 1, 0, 2},
    {0, 1, 1, 0, 3},
    {0, 1, 1, 1, 0},
    {0, 1, 1, 1, 1},
    {0, 1, 1, 1, 2},
    {0, 1, 1, 1, 3},
    {0, 1, 1, 2, 0},
    {0, 1, 1, 2, 1},
    {0, 1, 1, 2, 2},
    {0, 1, 1, 2, 3},
    {0, 1, 1, 3, 0},
    {0, 1, 1, 3, 1},
    {0, 1, 1, 3, 2},
    {0, 1, 1, 3, 3},
    {1, 0, 0, 0, 0, 0, 0},
    {1, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2},
    {1, 0, 0, 0, 0, 0, 3},
    {1, 0, 0, 0, 0, 1, 0},
    {1, 0, 0, 0, 0, 1, 1},
    {1, 0, 0, 0, 0, 1, 2},
    {1, 0, 0, 0, 0, 1, 3},
    {1, 0, 0, 0, 0, 2, 0},
    {1, 0, 0, 0, 0, 2, 1},
    {1, 0, 0, 0, 0, 2, 2},
    {1, 0, 0, 0, 0, 2, 3},
    {1, 0, 0, 0, 0, 3, 0},
    {1, 0, 0, 0, 0, 3, 1},
    {1, 0, 0, 0, 0, 3, 2},
    {1, 0, 0, 0, 0, 3, 3},
    {1, 0, 0, 0, 1, 0, 0},
    {1, 0, 0, 0, 1, 0, 1},
    {1, 0, 0, 0, 1, 0, 2},
    {1, 0, 0, 0, 1, 0, 3},
    {1, 0, 0, 0, 1, 1, 0},
    {1, 0, 0, 0, 1, 1, 1},
    {1, 0, 0, 0, 1, 1, 2},
    {1, 0, 0, 0, 1, 1, 3},
    {1, 0, 0, 0, 1, 2, 0},
    {1, 0, 0, 0, 1, 2, 1},
    {1, 0, 0, 0, 1, 2, 2},
    {1, 0, 0, 0, 1, 2, 3},
    {1, 0, 0, 0, 1, 3, 0},
    {1, 0, 0, 0, 1, 3, 1},
    {1, 0, 0, 0, 1, 3, 2},
    {1, 0, 0, 0, 1, 3, 3},
    {1, 0, 0, 0, 2, 0, 0},
    {1, 0, 0, 0, 2, 0, 1},
    {1, 0, 0, 0, 2, 0, 2},
    {1, 0, 0, 0, 2, 0, 3},
    {1, 0, 0, 0, 2, 1, 0},
    {1, 0, 0, 0, 2, 1, 1},
    {1, 0, 0, 0, 2, 1, 2},
    {1, 0, 0, 0, 2, 1, 3},
    {1, 0, 0, 0, 2, 2, 0},
    {1, 0, 0, 0, 2, 2, 1},
    {1, 0, 0, 0, 2, 2, 2},
    {1, 0, 0, 0, 2, 2, 3},
    {1, 0, 0, 0, 2, 3, 0},
    {1, 0, 0, 0, 2, 3, 1},
    {1, 0, 0, 0, 2, 3, 2},
    {1, 0, 0, 0, 2, 3, 3},
    {1, 0, 0, 0, 3, 0, 0},
    {1, 0, 0, 0, 3, 0, 1},
    {1, 0, 0, 0, 3, 0, 2},
    {1, 0, 0, 0, 3, 0, 3},
    {1, 0, 0, 0, 3, 1, 0},
    {1, 0, 0, 0, 3, 1, 1},
    {1, 0, 0, 0, 3, 1, 2},
    {1, 0, 0, 0, 3, 1, 3},
    {1, 0, 0, 0, 3, 2, 0},
    {1, 0, 0, 0, 3, 2, 1},
    {1, 0, 0, 0, 3, 2, 2},
    {1, 0, 0, 0, 3, 2, 3},
    {1, 0, 0, 0, 3, 3, 0},
    {1, 0, 0, 0, 3, 3, 1},
    {1, 0, 0, 0, 3, 3, 2},
    {1, 0, 0, 0, 3, 3, 3},
    {1, 0, 0, 1, 0, 0, 0},
    {1, 0, 0, 1, 0, 0, 1},
    {1, 0, 0, 1, 0, 0, 2},
    {1, 0, 0, 1, 0, 0, 3},
    {1, 0, 0, 1, 0, 1, 0},
    {1, 0, 0, 1, 0, 1, 1},
    {1, 0, 0, 1, 0, 1, 2},
    {1, 0, 0, 1, 0, 1, 3},
    {1, 0, 0, 1, 0, 2, 0},
    {1, 0, 0, 1, 0, 2, 1},
    {1, 0, 0, 1, 0, 2, 2},
    {1, 0, 0, 1, 0, 2, 3},
    {1, 0, 0, 1, 0, 3, 0},
    {1, 0, 0, 1, 0, 3, 1},
    {1, 0, 0, 1, 0, 3, 2},
    {1, 0, 0, 1, 0, 3, 3},
    {1, 0, 0, 1, 1, 0, 0},
    {1, 0, 0, 1, 1, 0, 1},
    {1, 0, 0, 1, 1, 0, 2},
    {1, 0, 0, 1, 1, 0, 3},
    {1, 0, 0, 1, 1, 1, 0},
    {1, 0, 0, 1, 1, 1, 1},
    {1, 0, 0, 1, 1, 1, 2},
    {1, 0, 0, 1, 1, 1, 3},
    {1, 0, 0, 1, 1, 2, 0},
    {1, 0, 0, 1, 1, 2, 1},
    {1, 0, 0, 1, 1, 2, 2},
    {1, 0, 0, 1, 1, 2, 3},
    {1, 0, 0, 1, 1, 3, 0},
    {1, 0, 0, 1, 1, 3, 1},
    {1, 0, 0, 1, 1, 3, 2},
    {1, 0, 0, 1, 1, 3, 3},
    {1, 0, 0, 1, 2, 0, 0},
    {1, 0, 0, 1, 2, 0, 1},
    {1, 0, 0, 1, 2, 0, 2},
    {1, 0, 0, 1, 2, 0, 3},
    {1, 0, 0, 1, 2, 1, 0},
    {1, 0, 0, 1, 2, 1, 1},
    {1, 0, 0, 1, 2, 1, 2},
    {1, 0, 0, 1, 2, 1, 3},
    {1, 0, 0, 1, 2, 2, 0},
    {1, 0, 0, 1, 2, 2, 1},
    {1, 0, 0, 1, 2, 2, 2},
    {1, 0, 0, 1, 2, 2, 3},
    {1, 0, 0, 1, 2, 3, 0},
    {1, 0, 0, 1, 2, 3, 1},
    {1, 0, 0, 1, 2, 3, 2},
    {1, 0, 0, 1, 2, 3, 3},
    {1, 0, 0, 1, 3, 0, 0},
    {1, 0, 0, 1, 3, 0, 1},
    {1, 0, 0, 1, 3, 0, 2},
    {1, 0, 0, 1, 3, 0, 3},
    {1, 0, 0, 1, 3, 1, 0},
    {1, 0, 0, 1, 3, 1, 1},
    {1, 0, 0, 1, 3, 1, 2},
    {1, 0, 0, 1, 3, 1, 3},
    {1, 0, 0, 1, 3, 2, 0},
    {1, 0, 0, 1, 3, 2, 1},
    {1, 0, 0, 1, 3, 2, 2},
    {1, 0, 0, 1, 3, 2, 3},
    {1, 0, 0, 1, 3, 3, 0},
    {1, 0, 0, 1, 3, 3, 1},
    {1, 0, 0, 1, 3, 3, 2},
    {1, 0, 0, 1, 3, 3, 3},
    {1, 0, 1, 0, 0, 0, 0},
    {1, 0, 1, 0, 0, 0, 1},
    {1, 0, 1, 0, 0, 0, 2},
    {1, 0, 1, 0, 0, 0, 3},
    {1, 0, 1, 0, 0, 1, 0},
    {1, 0, 1, 0, 0, 1, 1},
    {1, 0, 1, 0, 0, 1, 2},
    {1, 0, 1, 0, 0, 1, 3},
    {1, 0, 1, 0, 0, 2, 0},
    {1, 0, 1, 0, 0, 2, 1},
    {1, 0, 1, 0, 0, 2, 2},
    {1, 0, 1, 0, 0, 2, 3},
    {1, 0, 1, 0, 0, 3, 0},
    {1, 0, 1, 0, 0, 3, 1},
    {1, 0, 1, 0, 0, 3, 2},
    {1, 0, 1, 0, 0, 3, 3},
    {1, 0, 1, 0, 1, 0, 0},
    {1, 0, 1, 0, 1, 0, 1},
    {1, 0, 1, 0, 1, 0, 2},
    {1, 0, 1, 0, 1, 0, 3},
    {1, 0, 1, 0, 1, 1, 0},
    {1, 0, 1, 0, 1, 1, 1},
    {1, 0, 1, 0, 1, 1, 2},
    {1, 0, 1, 0, 1, 1, 3},
    {1, 0, 1, 0, 1, 2, 0},
    {1, 0, 1, 0, 1, 2, 1},
    {1, 0, 1, 0, 1, 2, 2},
    {1, 0, 1, 0, 1, 2, 3},
    {1, 0, 1, 0, 1, 3, 0},
    {1, 0, 1, 0, 1, 3, 1},
    {1, 0, 1, 0, 1, 3, 2},
    {1, 0, 1, 0, 1, 3, 3},
    {1, 0, 1, 0, 2, 0, 0},
    {1, 0, 1, 0, 2, 0, 1},
    {1, 0, 1, 0, 2, 0, 2},
    {1, 0, 1, 0, 2, 0, 3},
    {1, 0, 1, 0, 2, 1, 0},
    {1, 0, 1, 0, 2, 1, 1},
    {1, 0, 1, 0, 2, 1, 2},
    {1, 0, 1, 0, 2, 1, 3},
    {1, 0, 1, 0, 2, 2, 0},
    {1, 0, 1, 0, 2, 2, 1},
    {1, 0, 1, 0, 2, 2, 2},
    {1, 0, 1, 0, 2, 2, 3},
    {1, 0, 1, 0, 2, 3, 0},
    {1, 0, 1, 0, 2, 3, 1},
    {1, 0, 1, 0, 2, 3, 2},
    {1, 0, 1, 0, 2, 3, 3},
    {1, 0, 1, 0, 3, 0, 0},
    {1, 0, 1, 0, 3, 0, 1},
    {1, 0, 1, 0, 3, 0, 2},
    {1, 0, 1, 0, 3, 0, 3},
    {1, 0, 1, 0, 3, 1, 0},
    {1, 0, 1, 0, 3, 1, 1},
    {1, 0, 1, 0, 3, 1, 2},
    {1, 0, 1, 0, 3, 1, 3},
    {1, 0, 1, 0, 3, 2, 0},
    {1, 0, 1, 0, 3, 2, 1},
    {1, 0, 1, 0, 3, 2, 2},
    {1, 0, 1, 0, 3, 2, 3},
    {1, 0, 1, 0, 3, 3, 0},
    {1, 0, 1, 0, 3, 3, 1},
    {1, 0, 1, 0, 3, 3, 2},
    {1, 0, 1, 0, 3, 3, 3},
    {1, 0, 1, 1, 0, 0, 0},
    {1, 0, 1, 1, 0, 0, 1},
    {1, 0, 1, 1, 0, 0, 2},
    {1, 0, 1, 1, 0, 0, 3},
    {1, 0, 1, 1, 0, 1, 0},
    {1, 0, 1, 1, 0, 1, 1},
    {1, 0, 1, 1, 0, 1, 2},
    {1, 0, 1, 1, 0, 1, 3},
    {1, 0, 1, 1, 0, 2, 0},
    {1, 0, 1, 1, 0, 2, 1},
    {1, 0, 1, 1, 0, 2, 2},
    {1, 0, 1, 1, 0, 2, 3},
    {1, 0, 1, 1, 0, 3, 0},
    {1, 0, 1, 1, 0, 3, 1},
    {1, 0, 1, 1, 0, 3, 2},
    {1, 0, 1, 1, 0, 3, 3},
    {1, 0, 1, 1, 1, 0, 0},
    {1, 0, 1, 1, 1, 0, 1},
    {1, 0, 1, 1, 1, 0, 2},
    {1, 0, 1, 1, 1, 0, 3},
    {1, 0, 1, 1, 1, 1, 0},
    {1, 0, 1, 1, 1, 1, 1},
    {1, 0, 1, 1, 1, 1, 2},
    {1, 0, 1, 1, 1, 1, 3},
    {1, 0, 1, 1, 1, 2, 0},
    {1, 0, 1, 1, 1, 2, 1},
    {1, 0, 1, 1, 1, 2, 2},
    {1, 0, 1, 1, 1, 2, 3},
    {1, 0, 1, 1, 1, 3, 0},
    {1, 0, 1, 1, 1, 3, 1},
    {1, 0, 1, 1, 1, 3, 2},
    {1, 0, 1, 1, 1, 3, 3},
    {1, 0, 1, 1, 2, 0, 0},
    {1, 0, 1, 1, 2, 0, 1},
    {1, 0, 1, 1, 2, 0, 2},
    {1, 0, 1, 1, 2, 0, 3},
    {1, 0, 1, 1, 2, 1, 0},
    {1, 0, 1, 1, 2, 1, 1},
    {1, 0, 1, 1, 2, 1, 2},
    {1, 0, 1, 1, 2, 1, 3},
    {1, 0, 1, 1, 2, 2, 0},
    {1, 0, 1, 1, 2, 2, 1},
    {1, 0, 1, 1, 2, 2, 2},
    {1, 0, 1, 1, 2, 2, 3},
    {1, 0, 1, 1, 2, 3, 0},
    {1, 0, 1, 1, 2, 3, 1},
    {1, 0, 1, 1, 2, 3, 2},
    {1, 0, 1, 1, 2, 3, 3},
    {1, 0, 1, 1, 3, 0, 0},
    {1, 0, 1, 1, 3, 0, 1},
    {1, 0, 1, 1, 3, 0, 2},
    {1, 0, 1, 1, 3, 0, 3},
    {1, 0, 1, 1, 3, 1, 0},
    {1, 0, 1, 1, 3, 1, 1},
    {1, 0, 1, 1, 3, 1, 2},
    {1, 0, 1, 1, 3, 1, 3},
    {1, 0, 1, 1, 3, 2, 0},
    {1, 0, 1, 1, 3, 2, 1},
    {1, 0, 1, 1, 3, 2, 2},
    {1, 0, 1, 1, 3, 2, 3},
    {1, 0, 1, 1, 3, 3, 0},
    {1, 0, 1, 1, 3, 3, 1},
    {1, 0, 1, 1, 3, 3, 2},
    {1, 0, 1, 1, 3, 3, 3},
    {1, 1, 0, 0, 0, 0, 0},
    {1, 1, 0, 0, 0, 0, 1},
    {1, 1, 0, 0, 0, 0, 2},
    {1, 1, 0, 0, 0, 0, 3},
    {1, 1, 0, 0, 0, 1, 0},
    {1, 1, 0, 0, 0, 1, 1},
    {1, 1, 0, 0, 0, 1, 2},
    {1, 1, 0, 0, 0, 1, 3},
    {1, 1, 0, 0, 0, 2, 0},
    {1, 1, 0, 0, 0, 2, 1},
    {1, 1, 0, 0, 0, 2, 2},
    {1, 1, 0, 0, 0, 2, 3},
    {1, 1, 0, 0, 0, 3, 0},
    {1, 1, 0, 0, 0, 3, 1},
    {1, 1, 0, 0, 0, 3, 2},
    {1, 1, 0, 0, 0, 3, 3},
    {1, 1, 0, 0, 1, 0, 0},
    {1, 1, 0, 0, 1, 0, 1},
    {1, 1, 0, 0, 1, 0, 2},
    {1, 1, 0, 0, 1, 0, 3},
    {1, 1, 0, 0, 1, 1, 0},
    {1, 1, 0, 0, 1, 1, 1},
    {1, 1, 0, 0, 1, 1, 2},
    {1, 1, 0, 0, 1, 1, 3},
    {1, 1, 0, 0, 1, 2, 0},
    {1, 1, 0, 0, 1, 2, 1},
    {1, 1, 0, 0, 1, 2, 2},
    {1, 1, 0, 0, 1, 2, 3},
    {1, 1, 0, 0, 1, 3, 0},
    {1, 1, 0, 0, 1, 3, 1},
    {1, 1, 0, 0, 1, 3, 2},
    {1, 1, 0, 0, 1, 3, 3},
    {1, 1, 0, 0, 2, 0, 0},
    {1, 1, 0, 0, 2, 0, 1},
    {1, 1, 0, 0, 2, 0, 2},
    {1, 1, 0, 0, 2, 0, 3},
    {1, 1, 0, 0, 2, 1, 0},
    {1, 1, 0, 0, 2, 1, 1},
    {1, 1, 0, 0, 2, 1, 2},
    {1, 1, 0, 0, 2, 1, 3},
    {1, 1, 0, 0, 2, 2, 0},
    {1, 1, 0, 0, 2, 2, 1},
    {1, 1, 0, 0, 2, 2, 2},
    {1, 1, 0, 0, 2, 2, 3},
    {1, 1, 0, 0, 2, 3, 0},
    {1, 1, 0, 0, 2, 3, 1},
    {1, 1, 0, 0, 2, 3, 2},
    {1, 1, 0, 0, 2, 3, 3},
    {1, 1, 0, 0, 3, 0, 0},
    {1, 1, 0, 0, 3, 0, 1},
    {1, 1, 0, 0, 3, 0, 2},
    {1, 1, 0, 0, 3, 0, 3},
    {1, 1, 0, 0, 3, 1, 0},
    {1, 1, 0, 0, 3, 1, 1},
    {1, 1, 0, 0, 3, 1, 2},
    {1, 1, 0, 0, 3, 1, 3},
    {1, 1, 0, 0, 3, 2, 0},
    {1, 1, 0, 0, 3, 2, 1},
    {1, 1, 0, 0, 3, 2, 2},
    {1, 1, 0, 0, 3, 2, 3},
    {1, 1, 0, 0, 3, 3, 0},
    {1, 1, 0, 0, 3, 3, 1},
    {1, 1, 0, 0, 3, 3, 2},
    {1, 1, 0, 0, 3, 3, 3},
    {1, 1, 0, 1, 0, 0, 0},
    {1, 1, 0, 1, 0, 0, 1},
    {1, 1, 0, 1, 0, 0, 2},
    {1, 1, 0, 1, 0, 0, 3},
    {1, 1, 0, 1, 0, 1, 0},
    {1, 1, 0, 1, 0, 1, 1},
    {1, 1, 0, 1, 0, 1, 2},
    {1, 1, 0, 1, 0, 1, 3},
    {1, 1, 0, 1, 0, 2, 0},
    {1, 1, 0, 1, 0, 2, 1},
    {1, 1, 0, 1, 0, 2, 2},
    {1, 1, 0, 1, 0, 2, 3},
    {1, 1, 0, 1, 0, 3, 0},
    {1, 1, 0, 1, 0, 3, 1},
    {1, 1, 0, 1, 0, 3, 2},
    {1, 1, 0, 1, 0, 3, 3},
    {1, 1, 0, 1, 1, 0, 0},
    {1, 1, 0, 1, 1, 0, 1},
    {1, 1, 0, 1, 1, 0, 2},
    {1, 1, 0, 1, 1, 0, 3},
    {1, 1, 0, 1, 1, 1, 0},
    {1, 1, 0, 1, 1, 1, 1},
    {1, 1, 0, 1, 1, 1, 2},
    {1, 1, 0, 1, 1, 1, 3},
    {1, 1, 0, 1, 1, 2, 0},
    {1, 1, 0, 1, 1, 2, 1},
    {1, 1, 0, 1, 1, 2, 2},
    {1, 1, 0, 1, 1, 2, 3},
    {1, 1, 0, 1, 1, 3, 0},
    {1, 1, 0, 1, 1, 3, 1},
    {1, 1, 0, 1, 1, 3, 2},
    {1, 1, 0, 1, 1, 3, 3},
    {1, 1, 0, 1, 2, 0, 0},
    {1, 1, 0, 1, 2, 0, 1},
    {1, 1, 0, 1, 2, 0, 2},
    {1, 1, 0, 1, 2, 0, 3},
    {1, 1, 0, 1, 2, 1, 0},
    {1, 1, 0, 1, 2, 1, 1},
    {1, 1, 0, 1, 2, 1, 2},
    {1, 1, 0, 1, 2, 1, 3},
    {1, 1, 0, 1, 2, 2, 0},
    {1, 1, 0, 1, 2, 2, 1},
    {1, 1, 0, 1, 2, 2, 2},
    {1, 1, 0, 1, 2, 2, 3},
    {1, 1, 0, 1, 2, 3, 0},
    {1, 1, 0, 1, 2, 3, 1},
    {1, 1, 0, 1, 2, 3, 2},
    {1, 1, 0, 1, 2, 3, 3},
    {1, 1, 0, 1, 3, 0, 0},
    {1, 1, 0, 1, 3, 0, 1},
    {1, 1, 0, 1, 3, 0, 2},
    {1, 1, 0, 1, 3, 0, 3},
    {1, 1, 0, 1, 3, 1, 0},
    {1, 1, 0, 1, 3, 1, 1},
    {1, 1, 0, 1, 3, 1, 2},
    {1, 1, 0, 1, 3, 1, 3},
    {1, 1, 0, 1, 3, 2, 0},
    {1, 1, 0, 1, 3, 2, 1},
    {1, 1, 0, 1, 3, 2, 2},
    {1, 1, 0, 1, 3, 2, 3},
    {1, 1, 0, 1, 3, 3, 0},
    {1, 1, 0, 1, 3, 3, 1},
    {1, 1, 0, 1, 3, 3, 2},
    {1, 1, 0, 1, 3, 3, 3},
    {1, 1, 1, 0, 0, 0, 0},
    {1, 1, 1, 0, 0, 0, 1},
    {1, 1, 1, 0, 0, 0, 2},
    {1, 1, 1, 0, 0, 0, 3},
    {1, 1, 1, 0, 0, 1, 0},
    {1, 1, 1, 0, 0, 1, 1},
    {1, 1, 1, 0, 0, 1, 2},
    {1, 1, 1, 0, 0, 1, 3},
    {1, 1, 1, 0, 0, 2, 0},
    {1, 1, 1, 0, 0, 2, 1},
    {1, 1, 1, 0, 0, 2, 2},
    {1, 1, 1, 0, 0, 2, 3},
    {1, 1, 1, 0, 0, 3, 0},
    {1, 1, 1, 0, 0, 3, 1},
    {1, 1, 1, 0, 0, 3, 2},
    {1, 1, 1, 0, 0, 3, 3},
    {1, 1, 1, 0, 1, 0, 0},
    {1, 1, 1, 0, 1, 0, 1},
    {1, 1, 1, 0, 1, 0, 2},
    {1, 1, 1, 0, 1, 0, 3},
    {1, 1, 1, 0, 1, 1, 0},
    {1, 1, 1, 0, 1, 1, 1},
    {1, 1, 1, 0, 1, 1, 2},
    {1, 1, 1, 0, 1, 1, 3},
    {1, 1, 1, 0, 1, 2, 0},
    {1, 1, 1, 0, 1, 2, 1},
    {1, 1, 1, 0, 1, 2, 2},
    {1, 1, 1, 0, 1, 2, 3},
    {1, 1, 1, 0, 1, 3, 0},
    {1, 1, 1, 0, 1, 3, 1},
    {1, 1, 1, 0, 1, 3, 2},
    {1, 1, 1, 0, 1, 3, 3},
    {1, 1, 1, 0, 2, 0, 0},
    {1, 1, 1, 0, 2, 0, 1},
    {1, 1, 1, 0, 2, 0, 2},
    {1, 1, 1, 0, 2, 0, 3},
    {1, 1, 1, 0, 2, 1, 0},
    {1, 1, 1, 0, 2, 1, 1},
    {1, 1, 1, 0, 2, 1, 2},
    {1, 1, 1, 0, 2, 1, 3},
    {1, 1, 1, 0, 2, 2, 0},
    {1, 1, 1, 0, 2, 2, 1},
    {1, 1, 1, 0, 2, 2, 2},
    {1, 1, 1, 0, 2, 2, 3},
    {1, 1, 1, 0, 2, 3, 0},
    {1, 1, 1, 0, 2, 3, 1},
    {1, 1, 1, 0, 2, 3, 2},
    {1, 1, 1, 0, 2, 3, 3},
    {1, 1, 1, 0, 3, 0, 0},
    {1, 1, 1, 0, 3, 0, 1},
    {1, 1, 1, 0, 3, 0, 2},
    {1, 1, 1, 0, 3, 0, 3},
    {1, 1, 1, 0, 3, 1, 0},
    {1, 1, 1, 0, 3, 1, 1},
    {1, 1, 1, 0, 3, 1, 2},
    {1, 1, 1, 0, 3, 1, 3},
    {1, 1, 1, 0, 3, 2, 0},
    {1, 1, 1, 0, 3, 2, 1},
    {1, 1, 1, 0, 3, 2, 2},
    {1, 1, 1, 0, 3, 2, 3},
    {1, 1, 1, 0, 3, 3, 0},
    {1, 1, 1, 0, 3, 3, 1},
    {1, 1, 1, 0, 3, 3, 2},
    {1, 1, 1, 0, 3, 3, 3},
    {1, 1, 1, 1, 0, 0, 0},
    {1, 1, 1, 1, 0, 0, 1},
    {1, 1, 1, 1, 0, 0, 2},
    {1, 1, 1, 1, 0, 0, 3},
    {1, 1, 1, 1, 0, 1, 0},
    {1, 1, 1, 1, 0, 1, 1},
    {1, 1, 1, 1, 0, 1, 2},
    {1, 1, 1, 1, 0, 1, 3},
    {1, 1, 1, 1, 0, 2, 0},
    {1, 1, 1, 1, 0, 2, 1},
    {1, 1, 1, 1, 0, 2, 2},
    {1, 1, 1, 1, 0, 2, 3},
    {1, 1, 1, 1, 0, 3, 0},
    {1, 1, 1, 1, 0, 3, 1},
    {1, 1, 1, 1, 0, 3, 2},
    {1, 1, 1, 1, 0, 3, 3},
    {1, 1, 1, 1, 1, 0, 0},
    {1, 1, 1, 1, 1, 0, 1},
    {1, 1, 1, 1, 1, 0, 2},
    {1, 1, 1, 1, 1, 0, 3},
    {1, 1, 1, 1, 1, 1, 0},
    {1, 1, 1, 1, 1, 1, 1},
    {1, 1, 1, 1, 1, 1, 2},
    {1, 1, 1, 1, 1, 1, 3},
    {1, 1, 1, 1, 1, 2, 0},
    {1, 1, 1, 1, 1, 2, 1},
    {1, 1, 1, 1, 1, 2, 2},
    {1, 1, 1, 1, 1, 2, 3},
    {1, 1, 1, 1, 1, 3, 0},
    {1, 1, 1, 1, 1, 3, 1},
    {1, 1, 1, 1, 1, 3, 2},
    {1, 1, 1, 1, 1, 3, 3},
    {1, 1, 1, 1, 2, 0, 0},
    {1, 1, 1, 1, 2, 0, 1},
    {1, 1, 1, 1, 2, 0, 2},
    {1, 1, 1, 1, 2, 0, 3},
    {1, 1, 1, 1, 2, 1, 0},
    {1, 1, 1, 1, 2, 1, 1},
    {1, 1, 1, 1, 2, 1, 2},
    {1, 1, 1, 1, 2, 1, 3},
    {1, 1, 1, 1, 2, 2, 0},
    {1, 1, 1, 1, 2, 2, 1},
    {1, 1, 1, 1, 2, 2, 2},
    {1, 1, 1, 1, 2, 2, 3},
    {1, 1, 1, 1, 2, 3, 0},
    {1, 1, 1, 1, 2, 3, 1},
    {1, 1, 1, 1, 2, 3, 2},
    {1, 1, 1, 1, 2, 3, 3},
    {1, 1, 1, 1, 3, 0, 0},
    {1, 1, 1, 1, 3, 0, 1},
    {1, 1, 1, 1, 3, 0, 2},
    {1, 1, 1, 1, 3, 0, 3},
    {1, 1, 1, 1, 3, 1, 0},
    {1, 1, 1, 1, 3, 1, 1},
    {1, 1, 1, 1, 3, 1, 2},
    {1, 1, 1, 1, 3, 1, 3},
    {1, 1, 1, 1, 3, 2, 0},
    {1, 1, 1, 1, 3, 2, 1},
    {1, 1, 1, 1, 3, 2, 2},
    {1, 1, 1, 1, 3, 2, 3},
    {1, 1, 1, 1, 3, 3, 0},
    {1, 1, 1, 1, 3, 3, 1},
    {1, 1, 1, 1, 3, 3, 2},
    {1, 1, 1, 1, 3, 3, 3}
  };
  
  public FullCombinationIteratorTest(String testName) {
    super(testName);
  }
  
  protected void setUp() throws Exception {
    numLinkers = 2;
    numBBlocks = 4;
    numRGroups = new int[] {
      2, // abcd([R1])efgg[R2]
      3 // qrst([R1])uvwx([R2])yz[R3]
    };
  }
  
  protected void tearDown() throws Exception {
  }
  
  public static Test suite() {
    TestSuite suite = new TestSuite(FullCombinationIteratorTest.class);
    
    return suite;
  }
  
  /**
   * Test of hasNext method, of class de.modlab.smilib.fragments.FullCombinationIterator.
   */
  public void testHasNext() {
    System.out.println("hasNext");
    // tested in testNext();
  }
  
  /**
   * Test of next method, of class de.modlab.smilib.fragments.FullCombinationIterator.
   */
  public void testNext() {
    System.out.println("next");
    
    SmiLibIterator iterator = new FullCombinationIterator(numRGroups, numLinkers, numBBlocks);
    
    for (int i = 0; iterator.hasNext(); i++) {
      int[] result = iterator.next();
//      System.out.println(Arrays.toString(result));
      assertTrue("Resulting ID array not equal to expected ID array. i=" + i, Arrays.equals(expected[i], result));
    }
    
  }
  
  /**
   * Test of remove method, of class de.modlab.smilib.fragments.FullCombinationIterator.
   */
  public void testRemove() {
    System.out.println("remove");
    
    SmiLibIterator iterator = new FullCombinationIterator(numRGroups, numLinkers, numBBlocks);
    
    try {
      iterator.remove();
      fail("No UnsupportedOperationException was thrown when executing remove().");
    } catch (UnsupportedOperationException e) {
    }
  }
  
  public static void main(java.lang.String[] argList) {
    junit.textui.TestRunner.run(suite());
  }

  /**
   * Test of getMaximum method, of class de.modlab.smilib.iterator.FullCombinationIterator.
   */
  public void testGetMaximum() {
    System.out.println("getMaximum");
    
    SmiLibIterator iterator = new FullCombinationIterator(numRGroups, numLinkers, numBBlocks);
    
    assertEquals("Library size", expected.length, iterator.getMaximum());
  }
  
}
