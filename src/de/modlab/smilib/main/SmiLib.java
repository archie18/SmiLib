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


import de.modlab.smilib.gui.SmiLibFrame;
import java.io.PrintWriter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;


/**
 * Main class of SmiLib. Parses the command line and
 * starts the enumeration of a combinatorial library in an own thread.
 * <br><br>
 * usage: java -jar SmiLib.jar [-s scaffolds.smi] [-u] [-b<br>
 *       building_blocks.smi] [-h] [-y] [-c] [-r reaction_scheme.txt] [-f<br>
 *        lib.smi/lib.sdf] [-l linkers.smi]<br>
 * ----------------------------------------------------------------------<br>
 * SmiLib v2.0 rc4<br>
 * by Volker Haehnke, Andreas Schueller,<br>
 * Dr. Evgeny Byvatov and Prof. Dr. Gisbert Schneider<br>
 * Copyright (c) 2006, Johann Wolfgang Goethe-Universitaet, Frankfurt am<br>
 * Main, Germany. All rights reserved.<br>
 * http://www.modlab.de/<br>
 * A. Schueller, V. Haehnke, G. Schneider; SmiLib v2.0: A Java-Based Tool<br>
 * for Rapid Combinatorial Library Enumeration, QSAR & Combinatorial Science<br>
 * 2007, 3, 407-410.<br>
 * ----------------------------------------------------------------------<br>
 * SmiLib Help<br>
 * Create Combinatorial Libraries in SMILES format<br>
 * Mandatory information are the SMILES input files for scaffolds,<br>
 * linkers and building blocks (options -s, -l and -b). Without a reaction<br>
 * scheme a complete enumeration of the virtual library is performed.<br>
 * ----------------------------------------------------------------------<br>
 * USAGE:<br>
 * Scenario 1:<br>
 * java -jar SmiLib.jar -s &lt;scaffolds.smi&gt; -l &lt;linkers.smi&gt; -b<br>
 * &lt;buildingblocks.smi&gt; -f &lt;library.smi&gt;<br>
 * creates a combinatorial library in &quot;library.smi&quot; containing all<br>
 * possible reaction products (complete enumeration) using all scaffolds,<br>
 * linkers and building blocks in the specified input files.<br>
 * ---<br>
 * If option &quot;-f &lt;filename&gt;&quot; is omitted SmiLib by default prints the<br>
 * combinatorial library to standard out.<br>
 * The file extension of the library file specifies the file format. A<br>
 * library file name with the extension &quot;.sdf&quot; saves the combinatorial<br>
 * library in an SD file, any other file extension saves the library in<br>
 * an ASCII file in SMILES notation.<br>
 * ---<br>
 * Scenario 2:<br>
 * java -jar SmiLib.jar -s &lt;scaffolds.smi&gt; -l &lt;linkers.smi&gt; -b<br>
 * &lt;buildingblocks.smi&gt; -r &lt;reaction_scheme.txt&gt; -f &lt;library.smi&gt;<br>
 * creates a combinatorial library in &quot;library.smi&quot; using the reaction<br>
 * scheme specified in &quot;reaction_scheme.txt&quot;.<br>
 * ---<br>
 * A valid reaction scheme has to fulfill the following pattern (in this<br>
 * example the scaffold has 2 variable side chains):<br>
 * [scaffold indices] &lt;tab&gt; [linker indices for first variable side<br>
 * chain] &lt;tab&gt; [building block indices for first variable side chain]<br>
 * &lt;tab&gt; [linker indices for second variable side chain] &lt;tab&gt; [building<br>
 * block indices for second variable side chain]<br>
 * Indices start with 1 and refer to line numbers of the input SMILES<br>
 * files. Indices can be separated by &quot;;&quot; (e.g. &quot;1;4;6&quot;) or a range can<br>
 * be defined using &quot;-&quot; (e.g. &quot;1-10&quot;). It is also possible to mix these<br>
 * formats (e.g. &quot;1-10;12;16-20&quot;). Columns in the reaction scheme file<br>
 * are separated by tabs. Please ensure that you use no index that has no<br>
 * corresponding SMILES in the source file. If you use 20 linkers, you<br>
 * can't use indices above 20 in your reaction schemefor linkers. In this<br>
 * manner you can define the linkers and building blocks for each<br>
 * variable side chain separatly.<br>
 * ---<br>
 * RESTRICTIONS:<br>
 * SmiLib compatible SMILES may not include the symbols &quot;\&quot; and &quot;/&quot; since<br>
 * SMILES with E/Z-isomerie information can not be processed. (This<br>
 * behaviour can be changed with option -c.) The SmiLib specific groups<br>
 * [A], [R1], [R2], etc. must be connected to exactly one atom by a<br>
 * single bond only.<br>
 * ----------------------------------------------------------------------<br>
 * For additional help, please refer to the manual avaiable online at<br>
 * http://www.modlab.de/<br>
 * Have fun creating combinatorial libraries with SmiLib v2.0.<br>
 * ----------------------------------------------------------------------<br>
 *     -b,--bblocks &lt;building_blocks.smi&gt;          text file containing<br>
 *                                                 building block SMILES<br>
 *     -c,--check                                  deactivates SmiLib<br>
 *                                                 conformity checks of SMILES<br>
 *     -f,--savetofile &lt;lib.smi/lib.sdf&gt;           store combinatorial<br>
 *                                                 library in a file<br>
 *     -h,--help                                   shows SmiLib help<br>
 *     -l,--linkers &lt;linkers.smi&gt;                  text file containing<br>
 *                                                 linker SMILES<br>
 *     -r,--reaction_scheme &lt;reaction_scheme.txt&gt;  text file containing<br>
 *                                                 the reaction scheme<br>
 *     -s,--scaffolds &lt;scaffolds.smi&gt;              text file containing<br>
 *                                                 scaffold SMILES<br>
 *     -u,--user_interface                         starts SmiLib in<br>
 *                                                 graphical user interface mode<br>
 *     -y,--hydrogens                              adds hydrogens when<br>
 *                                                 library is saved as SD file<br>
 * 
 * @author Volker Haehnke
 * @author Andreas Schueller
 */
public class SmiLib {
    
    /** reaction scheme is used true/false (option -r) */
    private boolean useReactionScheme = false;
    
    /** command line had all necessary options true/false */
    private boolean validOptions = false;
    
    /** library shall be printed to the command line true/false (option -p) */
    private boolean printToCommandLine = true;
    
    /** add hydrogens when saving as SD file */
    private boolean addHydrogens = false;
    
    /** start with suer interface */
    private boolean startWithUserInterface = false;
    
    /** show help instead of launching programm */
    private boolean showHelp = false;
    
    /** path to the scaffold SMILES source file */
    private String scaffoldsPath;
    
    /** path to the linker SMILES source file */
    private String linkersPath;
    
    /** path to the building block SMILES source file */
    private String buildingBlocksPath;
    
    /** path to the reation scheme file */
    private String reactionSchemePath;
    
    /** path/filename of the file, where the combinatorial library will be stored */
    private String saveFilePath;
    
    /** contains all possible option that can occur in the command line */
    private Options options;
    
    /** will enumerate the library */
    private SmiLibRunner sRunner;
    
    /** check SMILES true/false */
    private boolean checkSmiles = true;
    
    /** new line separator */
    public static final String nl = System.getProperty("line.separator");
    
    /** version number of SmiLib */
    public static String version = "<from manifest>";
    
    /** build number of SmiLib */
    public static String buildNumber = "<from manifest>";

    /** build date of SmiLib */
    public static String buildDate = "<from manifest>";
    
    /** reference to the SmiLib publication */
    public static final String reference = "A. Schueller, V. Haehnke, G. Schneider; SmiLib v2.0: A Java-Based Tool for Rapid Combinatorial Library Enumeration, QSAR & Combinatorial Science 2007, 3, 407-410.";
    
    /** SmiLib copyright */
    public static final String copyright = "Copyright (c) 2006, Johann Wolfgang Goethe-Universitaet, Frankfurt am Main, Germany. All rights reserved.";

    /**
     * Static block to read version number, build number and build date from the
     * manifest file and parse them to static variables.
     */
    static {
      String rawVersion = SmiLib.class.getPackage().getImplementationVersion();
      if (rawVersion != null) {
        String[] rawVersionArray = rawVersion.split(";");
        SmiLib.version = rawVersionArray[0];
        SmiLib.buildNumber = rawVersionArray[1];
        SmiLib.buildDate = rawVersionArray[2];
      }
    }
    
    /**
     *Creates a new instance of SmiLib.
     */
    public SmiLib() {
        options = new Options();
    }
    
    
    /**
     *Parses the command line.
     *
     *@param args command line as String[]
     */
    private void parseCommandLineOptions(String[] args) {
        options.addOption(OptionBuilder.hasArg().withLongOpt("scaffolds").withArgName("scaffolds.smi").withDescription("text file containing scaffold SMILES").create('s'));
        options.addOption(OptionBuilder.hasArg().withLongOpt("linkers").withArgName("linkers.smi").withDescription("text file containing linker SMILES").create('l'));
        options.addOption(OptionBuilder.hasArg().withLongOpt("bblocks").withArgName("building_blocks.smi").withDescription("text file containing building block SMILES").create('b'));
        options.addOption(OptionBuilder.hasArg().withLongOpt("reaction_scheme").withArgName("reaction_scheme.txt").withDescription("text file containing the reaction scheme").create('r'));
        options.addOption(OptionBuilder.hasArg().withLongOpt("savetofile").withArgName("lib.smi/lib.sdf").withDescription("store combinatorial library in a file").create('f'));
//        options.addOption(OptionBuilder.withLongOpt("print").withDescription("combinatorial library is printed to the command line").create('p'));
        options.addOption(OptionBuilder.withLongOpt("hydrogens").withDescription("adds hydrogens when library is saved as SD file").create('y'));
        options.addOption(OptionBuilder.withLongOpt("user_interface").withDescription("starts SmiLib in graphical user interface mode").create('u'));
        options.addOption(OptionBuilder.withLongOpt("help").withDescription("shows SmiLib help").create('h'));
        options.addOption(OptionBuilder.withLongOpt("check").withDescription("deactivates SmiLib conformity checks of SMILES").create('c'));
        
        CommandLine line = null;
        
        try {
            CommandLineParser parser = new PosixParser();
            line = parser.parse(options, args);
        } catch (ParseException exception) {
            System.err.println("Wrong or missing command line parameters: " + exception.getMessage());
            printUsage();
            System.exit(0);
        }
        
        //if all necessary options are set and SmiLib help shall not be displayed
        if (line.hasOption("s") && line.hasOption("l") && line.hasOption("b") && !line.hasOption("h") && !line.hasOption("u")) {
            scaffoldsPath = line.getOptionValue("s");
            linkersPath = line.getOptionValue("l");
            buildingBlocksPath = line.getOptionValue("b");
            
            if (line.hasOption("r")) {
                reactionSchemePath = line.getOptionValue("r");
                useReactionScheme = true;
            }
            
            if (line.hasOption("f")) {
                saveFilePath = line.getOptionValue("f");
                printToCommandLine = false;
            }
            
            if (line.hasOption("p")) {
                printToCommandLine = true;
            }
            
            if (line.hasOption("y")) {
                addHydrogens = true;
            }
            
            if (line.hasOption("c")) {
                checkSmiles = false;                    
            }   
            
            validOptions = true;
            
        } else if (line.hasOption("u")) {
            startWithUserInterface = true;
        } else if (line.hasOption("h")) {
            showHelp = true;
        } else if (line.getOptions().length == 0) {
            System.out.println("By default starting in graphical user interface mode." + nl + "Start SmiLib with option -h for help.");
            startWithUserInterface = true;
        } else {
            validOptions = false;
        }
    }
    
    
    /**
     *Shows help for SmiLib usage.
     */
    public void printHelp() {
        String hr = "----------------------------------------------------------------------";
        String hrSmall = "---";
        String header = hr + nl +
                "SmiLib v" + version + " - Build: " + buildNumber + " (" + buildDate + ")" + nl +
                "by Volker Haehnke, Andreas Schueller" + nl +
                "Dr. Evgeny Byvatov and Prof. Dr. Gisbert Schneider" + nl +
                copyright + nl +
                "http://www.modlab.de/" + nl +
                reference + nl +
                hr + nl +
                "SmiLib Help" + nl +
                "Create Combinatorial Libraries in SMILES format" + nl +
                "Mandatory information are the SMILES input files for " +
                "scaffolds, linkers and building blocks (options -s, -l and -b). " +
                "Without a reaction scheme a complete enumeration of the virtual library is performed. " + 
                hr + nl +
                "USAGE: " + nl +
                "Scenario 1: " + nl +
                "java -jar SmiLib.jar -s <scaffolds.smi> -l <linkers.smi> -b <buildingblocks.smi> -f <library.smi> " + nl +
                "creates a combinatorial library in \"library.smi\" containing all possible reaction products " +
                "(complete enumeration) using all scaffolds, linkers and building blocks in the specified input files." + nl +
                hrSmall + nl +
                "If option \"-f <filename>\" is omitted SmiLib by default prints the combinatorial library to standard out. " + nl +
                "The file extension of the library file specifies the file format. A library file " +
                "name with the extension \".sdf\" saves the combinatorial library in an SD file, " +
                "any other file extension saves the library in an ASCII file in SMILES notation." + nl +
                hrSmall + nl +
                "Scenario 2: " + nl +
                "java -jar SmiLib.jar -s <scaffolds.smi> -l <linkers.smi> -b <buildingblocks.smi> -r <reaction_scheme.txt> -f <library.smi>\n " +
                "creates a combinatorial library in \"library.smi\" using the reaction scheme specified in \"reaction_scheme.txt\". " + nl +
                hrSmall + nl + 
                "A valid reaction scheme has to fulfill the following pattern (in this example the scaffold has 2 variable side chains):" + nl +
                "[scaffold indices] <tab> [linker indices for first variable side chain] <tab> [building block indices for first variable side chain] <tab> " +
                "[linker indices for second variable side chain] <tab> [building block indices for second variable side chain]" + nl +
                "Indices start with 1 and refer to line numbers of the input SMILES files. " +
                "Indices can be separated by \";\" (e.g. \"1;4;6\") or a range can be defined using \"-\" (e.g. \"1-10\"). " +
                "It is also possible to mix these formats (e.g. \"1-10;12;16-20\"). Columns in the reaction scheme file are separated by tabs. " +
                "Please ensure that you use no index that has no corresponding SMILES in the source file. " +
                "If you use 20 linkers, you can't use indices above 20 in your reaction scheme" +
                "for linkers. In this manner you can define the linkers and building blocks for each variable side chain separatly. " + nl +
                hrSmall + nl +
                "RESTRICTIONS:" + nl +
                "SmiLib compatible SMILES may not include the symbols \"\\\" and \"/\" since SMILES with E/Z-isomerie information can not be " +
                "processed. (This behaviour can be changed with option -c.) The SmiLib specific groups [A], [R1], [R2], etc. must be connected to exactly one atom by a single bond only." + nl +
                hr + nl +
                "For additional help, please refer to the manual avaiable online at http://www.modlab.de/" + nl +
                "Have fun creating combinatorial libraries with SmiLib v2.0." + nl +
                hr + nl;
                String footer = "";
        

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(new PrintWriter(System.out, true),    // PrintWriter
                              70,                                 // width
                              "java -jar SmiLib.jar",     // cmdLineSyntax
                              header,                             // header
                              options,                            // options
                              4,                                  // leftPad
                              2,                                  // descPad
                              footer,                             // footer
                              true                                // autoUsage
                );
    }
    
    
    /**
     *Starts enumeration of the combinatorial library.
     */
    public void enumerateLibrary() {
        if (this.useReactionScheme)
            sRunner = new SmiLibRunner(scaffoldsPath, linkersPath, buildingBlocksPath, reactionSchemePath, printToCommandLine, saveFilePath, addHydrogens, checkSmiles);
        else
            sRunner = new SmiLibRunner(scaffoldsPath, linkersPath, buildingBlocksPath, printToCommandLine, saveFilePath, addHydrogens, checkSmiles);
        Thread libraryEnumeration = new Thread(sRunner);
        libraryEnumeration.start();
    }
    
    
    /**
     *Main method of SmiLib.
     *
     *@param args command line as array of strings
     */
    public static void main(String[] args) {
        
        SmiLib smilib = new SmiLib();
        smilib.parseCommandLineOptions(args);
        
        // valid options - command line mode
        if (smilib.getValidOptions()) {
            smilib.enumerateLibrary();
            
        // explicit show help - help is shown
        } else if (smilib.getShowHelp()) {
            smilib.printHelp();
            
        // no option - like when .jar is double klicked - GUI started
        // or option -u
        } else if (smilib.getUseUserInterface()) {
            SmiLibFrame smiFrame = new SmiLibFrame();
        // not enough options supplied - print error message
        } else {
          System.err.println("Wrong or missing command line parameters.");
          smilib.printUsage();
        }
    }
    
    
    /**
     *Returns whether all necessary options are set in the command line.
     *
     *@return <code>true</code> if all necessary options are set in the command line,
     *<code>false</code> if not.
     */
    public boolean getValidOptions() {
        return this.validOptions;
    }
    
    
    /**
     *Returns whether user interface shall be used or not.
     *
     *@return use GUI true/false
     */
    public boolean getUseUserInterface() {
        return this.startWithUserInterface;
    }
    
    
    /**
     *Returns whether help shall be shown or not.
     *
     *@return show help true/false
     */
    public boolean getShowHelp() {
        return this.showHelp;
    }

    /**
     * Prints the command line usage of this program.
     */
    public void printUsage() {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printUsage(new PrintWriter(System.out, true), 70, "java -jar SmiLib.jar", options);

    }
}