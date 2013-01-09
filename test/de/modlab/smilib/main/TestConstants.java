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
 * TestConstants.java
 *
 * Created on 29. Juni 2006, 17:44
 */

package de.modlab.smilib.main;

/**
 *
 * @author Andreas Schueller
 */
public class TestConstants {
  
  public static final String nl = System.getProperty("line.separator");
  
  public static final String twoScaffolds = "./testdata/2scaffolds.txt";
  
  public static final String threeLinkers = "./testdata/3linkers.txt";
  
  public static final String fourBuildingBlocks = "./testdata/4buildingblocks.txt";
  
  public static final String nonconformScaffolds = "./testdata/nonconformScaffolds.txt";
  
  public static final String nonconformLinkers = "./testdata/nonconformLinkers.txt";
  
  public static final String nonconformBuildingBlocks = "./testdata/nonconformBuildingBlocks.txt";
  
  public static final String validScaffolds = "./testdata/scaffolds.txt";
  
  public static final String validLinkers = "./testdata/linkers.txt";
  
  public static final String validBuildingBlocks = "./testdata/buildingblocks.txt";
  
  public static final String validReactionScheme = "./testdata/valid_reaction_scheme.txt";
  
  public static final String validLibrarySdf = "./testdata/library.sdf";
  
  public static final String validLibrarySdfWithHydrogens = "./testdata/library_wH.sdf";
  
  public static final String verySimpleReactionScheme = "./testdata/very_simple_reaction_scheme.txt";
  
  public static final String twoThreeFourInvalidReactionScheme1 = "./testdata/234_invalid_reaction_scheme1.txt";
  
  public static final String twoThreeFourInvalidReactionScheme2 = "./testdata/234_invalid_reaction_scheme2.txt";
  
  public static final String twoThreeFourInvalidReactionScheme3 = "./testdata/234_invalid_reaction_scheme3.txt";
  
  public static final String twoThreeFourInvalidReactionScheme4 = "./testdata/234_invalid_reaction_scheme4.txt";
  
  public static final String twoThreeFourInvalidReactionScheme5 = "./testdata/234_invalid_reaction_scheme5.txt";
  
  public static final String twoThreeFourInvalidReactionScheme6 = "./testdata/234_invalid_reaction_scheme6.txt";
  
  public static final String twoThreeFourInvalidReactionScheme7 = "./testdata/234_invalid_reaction_scheme7.txt";
  
  public static final String twoThreeFourInvalidReactionScheme8 = "./testdata/234_invalid_reaction_scheme8.txt";
  
  public static final String twoThreeFourInvalidReactionScheme9 = "./testdata/234_invalid_reaction_scheme9.txt";
  
  public static final String twoThreeFourInvalidReactionScheme10 = "./testdata/234_invalid_reaction_scheme10.txt";
  
  public static final String twoThreeFourValidReactionScheme = "./testdata/234_valid_reaction_scheme.txt";
  
  public static final int[] twoThreeFourNumRGroups = new int[] {2, 3};
  
  public static final int twoThreeFourNumLinkers = 3;
  
  public static final int twoThreeFourNumBuildingBlocks = 4;
  
  public static final String chiralScaffolds = "./testdata/chiral_scaffolds.txt";
  
  public static final String chiralLinkers = "./testdata/chiral_linkers.txt";
  
  public static final String chiralBuildingBlocks = "./testdata/chiral_buildingblocks.txt";
  
  public static final String chiralReactionScheme = "./testdata/chiral_reaction_scheme.txt";
  
  public static final String chiralExpectedSmiles = "./testdata/chiral_expected_smiles.txt";
  
  /** SMILES generated with SMILIB 1.0.3 */
  public static final String twoThreeFourCompleteEnumerationSmiles = "./testdata/234_complete_enumeration_smiles.txt";
  
  public static final String[] twoScaffoldsSmiles = new String[] {
    "c%10([R1])ccccc%10[R2]",
    "C([R1])NCC([R2])O[R3]"
  };
  
  public static final String[] threeLinkersSmiles = new String[] {
    "[R][A]",
    "O([A])([R])",
    "C([A])NC[R1]"
  };
  
  public static final String[] fourBuildingBlocksSmiles = new String[] {
    "C1([A])CCCC1",
    "C([A])(=O)O",
    "N([A])",
    "N(=N)NC[A]"
  };
  
  public static final String emptyLinesScaffolds = "./testdata/empty_lines_scaffolds.txt";
  
  public static final String emptyLinesLinkers = "./testdata/empty_lines_linkers.txt";
  
  public static final String emptyLinesBuildingBlocks = "./testdata/empty_lines_buildingblocks.txt";
  
  public static final String[] emptyLinesScaffoldsSmiles = new String[] {"C1CCCCC1[R1]"};
  
  public static final String[] emptyLinesLinkersSmiles = new String[] {"O([R1])([A])"};
  
  public static final String[] emptyLinesBuildingBlocksSmiles = new String[] {"Cl[A]", "Br[A]"};
  
  public static final String[] emptyLinesScaffoldsGui = new String[] {"C1CCCCC1[R1]", "", ""};
  
  public static final String[] emptyLinesLinkersGui = new String[] {"\t", "O([R1])([A])"};
  
  public static final String[] emptyLinesBuildingBlocksGui = new String[] {"Cl[A]", "    ", "Br[A]", ""};
  
  public static final String openBableCommandlineWin = "./openbabel/babel.exe -ismi -ocan";
  
  public static final String openBabelComanndlineLinux = "./openbabel/babel -ismi -ocan";
  
  public static final String openBableCommandlineWinInchi = "./openbabel/babel.exe -ismi -oinchi";
  
  public static final String openBabelComanndlineLinuxInchi = "./openbabel/babel -ismi -oinchi";
  
  public static final String openBableCommandlineWinSdf = "./openbabel/babel.exe -ismi -osdf -x3";
  
  public static final String openBabelComanndlineLinuxSdf = "./openbabel/babel -ismi -osdf -x3";
  
  public static final String openBableCommandlineWinSdfToInchi = "./openbabel/babel.exe -isdf -oinchi -xw";
  
  public static final String openBabelComanndlineLinuxSdfToInchi = "./openbabel/babel -isdf -oinchi -xw";
  
  public static final String openBableCommandlineWinSdfToSmiles = "./openbabel/babel.exe -isdf -osmi";
  
  public static final String openBabelComanndlineLinuxSdfToSmiles = "./openbabel/babel -isdf -osmi";
  
  /** Expected resulting SMILES from validScaffolds/validLinkers/validBuildingBlocks/validReactionScheme processing. */
  public static final String[] expectedValidSmiles = new String[] {
    "C[C@H](NC1CCCCC1)C(=O)O",	
    "OC(=O)[C@@](C)(N)OC1CCCCC1",
    "OC(=O)[C@](C)(N)OC1CCCCC1",
    "C[C@@](N)(F)C(=O)O",
    "C[C@](F)(Br)C(=O)O",
    "C[C@@](F)(Br)C(=O)O",
    "C[C@](F)(Br)C(=O)O",
    "C[C@](N)(CCCC)C(=O)OCl",
    "C[C@](Cl)(CCCC)C(=O)O",
    "CCCCCl",
    "CCCCCl",
    "CCCCOCl",
    "[O-][n+]1ccccc1",
    "O([2H])[OH2+]",
    "CCCl",
    "CCCl"
  };
  
  public static final String[] twoThreeFourValidReactionSchemeSmiles = {
    "c1(c(cccc1)C1CCCC1)C1CCCC1",
    "c1(c(cccc1)CNCC1CCCC1)N",
    "c1(c(cccc1)CNCC1CCCC1)CNN=N",
    "c1(c(cccc1)C1CCCC1)N",
    "c1(c(cccc1)C1CCCC1)CNN=N",
    "c1(c(cccc1)CNCC1CCCC1)ON",
    "c1(c(cccc1)CNCC1CCCC1)OCNN=N",
    "c1(c(cccc1)C1CCCC1)ON",
    "c1(c(cccc1)C1CCCC1)OCNN=N",
    "C(C1CCCC1)NCC(C1CCCC1)OC1CCCC1"
  };
  
  public static final String pseudoLinker = "./testdata/pseudo_linker.txt";
  
  public static final String novalixScaffold = "./testdata/novalix_scaffold.txt";
  
  public static final String novalixBuildingBlocks = "./testdata/novalix_buildingblocks.txt";
  
  public static final String novalixExpectedResult = "./testdata/novalix_expected_result.txt";
  
  
//  /** A few exceptions that openbabel cannot convert correctly so far. */
//  public static final String[] expectedValidSmilesRaw = new String[expectedValidSmiles.length];
  
//  static {
//    expectedValidSmilesRaw[ 4] = "[C@@]%10(F)(C)C(=O)O.Br%10";
//    expectedValidSmilesRaw[ 5] = "Br[C@@](C)%10C(=O)O.F%10";
//  }
  
  
  /**
   * Creates a new instance of TestConstants
   */
  private TestConstants() {
  }
  
}
