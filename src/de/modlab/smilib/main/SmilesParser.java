/*
 *  $RCSfile: SmilesParser.java,v $
 *  $Author: andreas $
 *  $Date: 2006/07/29 17:24:42 $
 *  $Revision: 1.2 $
 *
 *  Copyright (C) 2002-2006  The Chemistry Development Kit (CDK) project
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *  All I ask is that proper credit is given for my work, which includes
 *  - but is not limited to - adding the above copyright notice to the beginning
 *  of your source code files, and to any copyright notice that you may distribute
 *  with programs based on this work.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
package de.modlab.smilib.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Stack;
import java.util.StringTokenizer;

import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomParity;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomParity;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.PseudoAtom;
import org.openscience.cdk.Reaction;
import org.openscience.cdk.interfaces.ISetOfMolecules;
import org.openscience.cdk.aromaticity.HueckelAromaticityDetector;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.io.MDLWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.HydrogenAdder;
import org.openscience.cdk.tools.LoggingTool;
import org.openscience.cdk.tools.ValencyHybridChecker;

/**
 * Parses a SMILES {@cdk.cite SMILESTUT} string and an AtomContainer. The full
 * SSMILES subset {@cdk.cite SSMILESTUT} and the '%' tag for more than 10 rings
 * at a time are supported. An example:
 * <pre>
 * try {
 *   SmilesParser sp = new SmilesParser();
 *   Molecule m = sp.parseSmiles("c1ccccc1");
 * } catch (InvalidSmilesException ise) {
 * }
 * </pre>
 *
 * <p>This parser does not parse stereochemical information, but the following
 * features are supported: reaction smiles, partitioned structures, charged
 * atoms, implicit hydrogen count, '*' and isotope information.
 *
 * <p>See {@cdk.cite WEI88} for further information.
 *
 * @author         Christoph Steinbeck
 * @author         Egon Willighagen
 * @cdk.module     smiles
 * @cdk.created    2002-04-29
 * @cdk.keyword    SMILES, parser
 * @cdk.bug        1095696
 * @cdk.bug        1235852
 * @cdk.bug        1274464
 * @cdk.bug        1296113
 * @cdk.bug        1363882
 * @cdk.bug        1365547
 * @cdk.bug        1503541
 * @cdk.bug        1519183
 */
public class SmilesParser {

	private LoggingTool logger;
	private HydrogenAdder hAdder;
	private ValencyHybridChecker valencyChecker;
	private int status = 0;


	/**
	 *  Constructor for the SmilesParser object
	 */
	public SmilesParser()
	{
		logger = new LoggingTool(this);
		try
		{
			valencyChecker = new ValencyHybridChecker();
			hAdder = new HydrogenAdder(valencyChecker);
		} catch (Exception exception)
		{
			logger.error("Could not instantiate valencyChecker or hydrogenAdder: ",
					exception.getMessage());
			logger.debug(exception);
		}
	}


	int position = -1;
	int nodeCounter = -1;
	String smiles = null;
	double bondStatus = -1;
	double bondStatusForRingClosure = 1;
    boolean bondIsAromatic = false;
	Atom[] rings = null;
	double[] ringbonds = null;
	int thisRing = -1;
	org.openscience.cdk.Molecule molecule = null;
	String currentSymbol = null;


	/**
	 *  Description of the Method
	 *
	 *@param  smiles                      Description of the Parameter
	 *@return                             Description of the Return Value
	 *@exception  InvalidSmilesException  Description of the Exception
	 */
	public Reaction parseReactionSmiles(String smiles) throws InvalidSmilesException
	{
		StringTokenizer tokenizer = new StringTokenizer(smiles, ">");
		String reactantSmiles = tokenizer.nextToken();
		String agentSmiles = "";
		String productSmiles = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens())
		{
			agentSmiles = productSmiles;
			productSmiles = tokenizer.nextToken();
		}

		Reaction reaction = new Reaction();

		// add reactants
		IMolecule reactantContainer = parseSmiles(reactantSmiles);
		ISetOfMolecules reactantSet = ConnectivityChecker.partitionIntoMolecules(reactantContainer);
		IMolecule[] reactants = reactantSet.getMolecules();
		for (int i = 0; i < reactants.length; i++)
		{
			reaction.addReactant(reactants[i]);
		}

		// add reactants
		if (agentSmiles.length() > 0)
		{
			IMolecule agentContainer = parseSmiles(agentSmiles);
			ISetOfMolecules agentSet = ConnectivityChecker.partitionIntoMolecules(agentContainer);
			IMolecule[] agents = agentSet.getMolecules();
			for (int i = 0; i < agents.length; i++)
			{
				reaction.addAgent(agents[i]);
			}
		}

		// add products
		IMolecule productContainer = parseSmiles(productSmiles);
		ISetOfMolecules productSet = ConnectivityChecker.partitionIntoMolecules(productContainer);
		IMolecule[] products = productSet.getMolecules();
		for (int i = 0; i < products.length; i++)
		{
			reaction.addProduct(products[i]);
		}

		return reaction;
	}


	/**
	 *  Parses a SMILES string and returns a Molecule object.
	 *
	 *@param  smiles                      A SMILES string
	 *@return                             A Molecule representing the constitution
	 *      given in the SMILES string
	 *@exception  InvalidSmilesException  Exception thrown when the SMILES string
	 *      is invalid
	 */
	public org.openscience.cdk.Molecule parseSmiles(String smiles) throws InvalidSmilesException
	{
		logger.debug("parseSmiles()...");
		Bond bond = null;
		nodeCounter = 0;
		bondStatus = 0;
        bondIsAromatic = false;
		boolean bondExists = true;
		thisRing = -1;
		currentSymbol = null;
		molecule = new org.openscience.cdk.Molecule();
		position = 0;
		// we don't want more than 1024 rings
		rings = new Atom[1024];
		ringbonds = new double[1024];
		for (int f = 0; f < 1024; f++)
		{
			rings[f] = null;
			ringbonds[f] = -1;
		}

		char mychar = 'X';
		char[] chars = new char[1];
		Atom lastNode = null;
		Stack atomStack = new Stack();
		Stack bondStack = new Stack();
		Atom atom = null;
		do
		{
			try
			{
				mychar = smiles.charAt(position);
				logger.debug("");
				logger.debug("Processing: " + mychar);
				if (lastNode != null)
				{
					logger.debug("Lastnode: ", lastNode.hashCode());
				}
				if ((mychar >= 'A' && mychar <= 'Z') || (mychar >= 'a' && mychar <= 'z') ||
						(mychar == '*'))
				{
					status = 1;
					logger.debug("Found a must-be 'organic subset' element");
					// only 'organic subset' elements allowed
					atom = null;
					if (mychar == '*')
					{
						currentSymbol = "*";
						atom = new PseudoAtom("*");
					} else
					{
						currentSymbol = getSymbolForOrganicSubsetElement(smiles, position);
						if (currentSymbol != null)
						{
							if (currentSymbol.length() == 1)
							{
								if (!(currentSymbol.toUpperCase()).equals(currentSymbol))
								{
									currentSymbol = currentSymbol.toUpperCase();
									atom = new Atom(currentSymbol);
									atom.setHybridization(CDKConstants.HYBRIDIZATION_SP2);
								} else
								{
									atom = new Atom(currentSymbol);
								}
							} else
							{
								atom = new Atom(currentSymbol);
							}
							logger.debug("Made atom: ", atom);
						} else
						{
							throw new InvalidSmilesException(
									"Found element which is not a 'organic subset' element. You must " +
									"use [" + mychar + "].");
						}
					}

					molecule.addAtom(atom);
					logger.debug("Adding atom ", atom.hashCode());
					if ((lastNode != null) && bondExists)
					{
						logger.debug("Creating bond between ", atom.getSymbol(), " and ", lastNode.getSymbol());
						bond = new Bond(atom, lastNode, bondStatus);
						            if (bondIsAromatic) {
                            bond.setFlag(CDKConstants.ISAROMATIC, true);
                        }
						molecule.addBond(bond);
					}
					bondStatus = CDKConstants.BONDORDER_SINGLE;
					lastNode = atom;
					nodeCounter++;
					position = position + currentSymbol.length();
					bondExists = true;
                    bondIsAromatic = false;
				} else if (mychar == '=')
				{
					position++;
					if (status == 2 || smiles.length() == position + 1 || !(smiles.charAt(position) >= '0' && smiles.charAt(position) <= '9'))
					{
						bondStatus = CDKConstants.BONDORDER_DOUBLE;
					} else
					{
						bondStatusForRingClosure = CDKConstants.BONDORDER_DOUBLE;
					}
				} else if (mychar == '#')
				{
					position++;
					if (status == 2 || smiles.length() == position + 1 || !(smiles.charAt(position) >= '0' && smiles.charAt(position) <= '9'))
					{
						bondStatus = CDKConstants.BONDORDER_TRIPLE;
					} else
					{
						bondStatusForRingClosure = CDKConstants.BONDORDER_TRIPLE;
					}
				} else if (mychar == '(')
				{
					atomStack.push(lastNode);
					logger.debug("Stack:");
					Enumeration ses = atomStack.elements();
					while (ses.hasMoreElements())
					{
						Atom a = (Atom) ses.nextElement();
						logger.debug("", a.hashCode());
					}
					logger.debug("------");
					bondStack.push(new Double(bondStatus));
					position++;
				} else if (mychar == ')')
				{
					lastNode = (Atom) atomStack.pop();
					logger.debug("Stack:");
					Enumeration ses = atomStack.elements();
					while (ses.hasMoreElements())
					{
						Atom a = (Atom) ses.nextElement();
						logger.debug("", a.hashCode());
					}
					logger.debug("------");
					bondStatus = ((Double) bondStack.pop()).doubleValue();
					position++;
				} else if (mychar >= '0' && mychar <= '9')
				{
					status = 2;
					chars[0] = mychar;
					currentSymbol = new String(chars);
					thisRing = (new Integer(currentSymbol)).intValue();
					handleRing(lastNode);
					position++;
				} else if (mychar == '%')
				{
					currentSymbol = getRingNumber(smiles, position);
					thisRing = (new Integer(currentSymbol)).intValue();
					handleRing(lastNode);
					position += currentSymbol.length() + 1;
				} else if (mychar == '[')
				{
					currentSymbol = getAtomString(smiles, position);
					atom = assembleAtom(currentSymbol);
					molecule.addAtom(atom);
					logger.debug("Added atom: ", atom);
					if (lastNode != null && bondExists)
					{
						bond = new Bond(atom, lastNode, bondStatus);
						            if (bondIsAromatic) {
                            bond.setFlag(CDKConstants.ISAROMATIC, true);
                        }
						molecule.addBond(bond);
						logger.debug("Added bond: ", bond);
					}
					bondStatus = CDKConstants.BONDORDER_SINGLE;
                    bondIsAromatic = false;
					lastNode = atom;
					nodeCounter++;
					position = position + currentSymbol.length() + 2;
					// plus two for [ and ]
					bondExists = true;
				} else if (mychar == '.')
				{
					bondExists = false;
					position++;
				} else if (mychar == '-')
				{
					bondExists = true;
					// a simple single bond
					position++;
                } else if (mychar == ':') {
                    bondExists = true;
                    bondIsAromatic = true;
                    position++;
				} else if (mychar == '/' || mychar == '\\')
				{
					logger.warn("Ignoring stereo information for double bond");
					position++;
				} else if (mychar == '@')
				{
					if (position < smiles.length() - 1 && smiles.charAt(position + 1) == '@')
					{
						position++;
					}
					logger.warn("Ignoring stereo information for atom");
					position++;
				} else
				{
					throw new InvalidSmilesException("Unexpected character found: " + mychar);
				}
			} catch (InvalidSmilesException exc)
			{
				logger.error("InvalidSmilesException while parsing char (in parseSmiles()): " + mychar);
				logger.debug(exc);
				throw exc;
			} catch (Exception exception)
			{
				logger.error("Error while parsing char: " + mychar);
				logger.debug(exception);
				throw new InvalidSmilesException("Error while parsing char: " + mychar);
			}
			logger.debug("Parsing next char");
		} while (position < smiles.length());

		// add implicit hydrogens
		try
		{
			logger.debug("before H-adding: ", molecule);
			hAdder.addImplicitHydrogensToSatisfyValency(molecule);
			logger.debug("after H-adding: ", molecule);
		} catch (Exception exception)
		{
			logger.error("Error while calculation Hcount for SMILES atom: ", exception.getMessage());
		}

		// setup missing bond orders
		try
		{
			valencyChecker.saturate(molecule);
			logger.debug("after adding missing bond orders: ", molecule);
		} catch (Exception exception)
		{
			logger.error("Error while calculation Hcount for SMILES atom: ", exception.getMessage());
		}

		// conceive aromatic perception
		IMolecule[] moleculeSet = ConnectivityChecker.partitionIntoMolecules(molecule).getMolecules();
		logger.debug("#mols ", moleculeSet.length);
		for (int i = 0; i < moleculeSet.length; i++)
		{
			logger.debug("mol: ", moleculeSet[i]);
			try
			{
				valencyChecker.saturate(moleculeSet[i]);
				logger.debug(" after saturation: ", moleculeSet[i]);
				if (HueckelAromaticityDetector.detectAromaticity(moleculeSet[i]))
				{
					logger.debug("Structure is aromatic...");
				}
			} catch (Exception exception)
			{
				logger.error("Could not perceive aromaticity: ", exception.getMessage());
				logger.debug(exception);
			}
		}

		return molecule;
	}


	/**
	 *  Gets the AtomString attribute of the SmilesParser object
	 *
	 *@param  pos                         Description of the Parameter
	 *@param  smiles                      Description of the Parameter
	 *@return                             The AtomString value
	 *@exception  InvalidSmilesException  Description of the Exception
	 */
	private String getAtomString(String smiles, int pos) throws InvalidSmilesException
	{
		logger.debug("getAtomString()");
		StringBuffer atomString = new StringBuffer();
		try
		{
			for (int f = pos + 1; f < smiles.length(); f++)
			{
				char character = smiles.charAt(f);
				if (character == ']')
				{
					break;
				} else
				{
					atomString.append(character);
				}
			}
		} catch (Exception exception)
		{
			String message = "Problem parsing Atom specification given in brackets.\n";
			message += "Invalid SMILES string was: " + smiles;
			logger.error(message);
			logger.debug(exception);
			throw new InvalidSmilesException(message);
		}
		return atomString.toString();
	}


	/**
	 *  Gets the Charge attribute of the SmilesParser object
	 *
	 *@param  chargeString  Description of the Parameter
	 *@param  position      Description of the Parameter
	 *@return               The Charge value
	 */
	private int getCharge(String chargeString, int position)
	{
		logger.debug("getCharge(): Parsing charge from: ", chargeString.substring(position));
		int charge = 0;
		if (chargeString.charAt(position) == '+')
		{
			charge = +1;
			position++;
		} else if (chargeString.charAt(position) == '-')
		{
			charge = -1;
			position++;
		} else
		{
			return charge;
		}
		StringBuffer multiplier = new StringBuffer();
		while (position < chargeString.length() && Character.isDigit(chargeString.charAt(position)))
		{
			multiplier.append(chargeString.charAt(position));
			position++;
		}
		if (multiplier.length() > 0)
		{
			logger.debug("Found multiplier: ", multiplier);
			try
			{
				charge = charge * Integer.parseInt(multiplier.toString());
			} catch (Exception exception)
			{
				logger.error("Could not parse positive atomic charge!");
				logger.debug(exception);
			}
		}
		logger.debug("Found charge: ", charge);
		return charge;
	}


	/**
	 *  Gets the implicitHydrogenCount attribute of the SmilesParser object
	 *
	 *@param  s         Description of the Parameter
	 *@param  position  Description of the Parameter
	 *@return           The implicitHydrogenCount value
	 */
	private int getImplicitHydrogenCount(String s, int position)
	{
		logger.debug("getImplicitHydrogenCount(): Parsing implicit hydrogens from: " + s);
		int count = 1;
		if (s.charAt(position) == 'H')
		{
			StringBuffer multiplier = new StringBuffer();
			while (position < (s.length() - 1) && Character.isDigit(s.charAt(position + 1)))
			{
				multiplier.append(position + 1);
				position++;
			}
			if (multiplier.length() > 0)
			{
				try
				{
					count = count + Integer.parseInt(multiplier.toString());
				} catch (Exception exception)
				{
					logger.error("Could not parse number of implicit hydrogens!");
					logger.debug(exception);
				}
			}
		}
		return count;
	}


	/**
	 *  Gets the ElementSymbol attribute of the SmilesParser object
	 *
	 *@param  s    Description of the Parameter
	 *@param  pos  Description of the Parameter
	 *@return      The ElementSymbol value
	 */
	private String getElementSymbol(String s, int pos)
	{
		logger.debug("getElementSymbol(): Parsing element symbol (pos=" + pos + ") from: " + s);
		// try to match elements not in the organic subset.
		// first, the two char elements
		if (pos < s.length() - 1)
		{
			String possibleSymbol = s.substring(pos, pos + 2);
			logger.debug("possibleSymbol: ", possibleSymbol);
			if (("HeLiBeNeNaMgAlSiClArCaScTiCrMnFeCoNiCuZnGaGeAsSe".indexOf(possibleSymbol) >= 0) ||
					("BrKrRbSrZrNbMoTcRuRhPdAgCdInSnSbTeXeCsBaLuHfTaRe".indexOf(possibleSymbol) >= 0) ||
					("OsIrPtAuHgTlPbBiPoAtRnFrRaLrRfDbSgBhHsMtDs".indexOf(possibleSymbol) >= 0))
			{
				return possibleSymbol;
			}
		}
		// if that fails, the one char elements
		String possibleSymbol = s.substring(pos, pos + 1);
		logger.debug("possibleSymbol: ", possibleSymbol);
		if (("HKUVY".indexOf(possibleSymbol) >= 0))
		{
			return possibleSymbol;
		}
		// if that failed too, then possibly a organic subset element
		return getSymbolForOrganicSubsetElement(s, pos);
	}


	/**
	 *  Gets the ElementSymbol for an element in the 'organic subset' for which
	 *  brackets may be omited. <p>
	 *
	 *  See: <a href="http://www.daylight.com/dayhtml/smiles/smiles-atoms.html">
	 *  http://www.daylight.com/dayhtml/smiles/smiles-atoms.html</a> .
	 *
	 *@param  s    Description of the Parameter
	 *@param  pos  Description of the Parameter
	 *@return      The symbolForOrganicSubsetElement value
	 */
	private String getSymbolForOrganicSubsetElement(String s, int pos)
	{
		logger.debug("getSymbolForOrganicSubsetElement(): Parsing organic subset element from: ", s);
		if (pos < s.length() - 1)
		{
			String possibleSymbol = s.substring(pos, pos + 2);
			if (("ClBr".indexOf(possibleSymbol) >= 0))
			{
				return possibleSymbol;
			}
		}
		if ("BCcNnOoFPSsI".indexOf((s.charAt(pos))) >= 0)
		{
			return s.substring(pos, pos + 1);
		}
		if ("fpi".indexOf((s.charAt(pos))) >= 0)
		{
			logger.warn("Element ", s, " is normally not sp2 hybridisized!");
			return s.substring(pos, pos + 1);
		}
		logger.warn("Subset element not found!");
		return null;
	}


	/**
	 *  Gets the RingNumber attribute of the SmilesParser object
	 *
	 *@param  s    Description of the Parameter
	 *@param  pos  Description of the Parameter
	 *@return      The RingNumber value
	 */
	private String getRingNumber(String s, int pos) throws InvalidSmilesException
	{
		logger.debug("getRingNumber()");
		pos++;

                // Two digits impossible due to end of string
                if (pos >= s.length() - 1)
                  throw new InvalidSmilesException("Percent sign ring closure numbers must be two-digit.");
		
                String retString = s.substring(pos, pos + 2);
                
                if (retString.charAt(0) < '0' || retString.charAt(0) > '9' || retString.charAt(1) < '0' || retString.charAt(1) > '9')
                  throw new InvalidSmilesException("Percent sign ring closure numbers must be two-digit.");

                return retString;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  s                           Description of the Parameter
	 *@return                             Description of the Return Value
	 *@exception  InvalidSmilesException  Description of the Exception
	 */
	private Atom assembleAtom(String s) throws InvalidSmilesException
	{
		logger.debug("assembleAtom(): Assembling atom from: ", s);
		Atom atom = null;
		int position = 0;
		String currentSymbol = null;
		StringBuffer isotopicNumber = new StringBuffer();
		char mychar;
		logger.debug("Parse everythings before and including element symbol");
		do
		{
			try
			{
				mychar = s.charAt(position);
				logger.debug("Parsing char: " + mychar);
				if ((mychar >= 'A' && mychar <= 'Z') || (mychar >= 'a' && mychar <= 'z'))
				{
					currentSymbol = getElementSymbol(s, position);
					if (currentSymbol == null)
					{
						throw new InvalidSmilesException(
								"Expected element symbol, found null!"
								);
					} else
					{
						logger.debug("Found element symbol: ", currentSymbol);
						position = position + currentSymbol.length();
						if (currentSymbol.length() == 1)
						{
							if (!(currentSymbol.toUpperCase()).equals(currentSymbol))
							{
								currentSymbol = currentSymbol.toUpperCase();
								atom = new Atom(currentSymbol);
								atom.setHybridization(CDKConstants.HYBRIDIZATION_SP2);
								if (atom.getHydrogenCount() > 0)
								{
									atom.setHydrogenCount(atom.getHydrogenCount() - 1);
								}
							} else
							{
								atom = new Atom(currentSymbol);
							}
						} else
						{
							atom = new Atom(currentSymbol);
						}
						logger.debug("Made atom: ", atom);
					}
					break;
				} else if (mychar >= '0' && mychar <= '9')
				{
					isotopicNumber.append(mychar);
					position++;
				} else if (mychar == '*')
				{
					currentSymbol = "*";
					atom = new PseudoAtom(currentSymbol);
					logger.debug("Made atom: ", atom);
					position++;
					break;
				} else
				{
					throw new InvalidSmilesException("Found unexpected char: " + mychar);
				}
			} catch (InvalidSmilesException exc)
			{
				logger.error("InvalidSmilesException while parsing atom string: " + s);
				logger.debug(exc);
				throw exc;
			} catch (Exception exception)
			{
				logger.error("Could not parse atom string: ", s);
				logger.debug(exception);
				throw new InvalidSmilesException("Could not parse atom string: " + s);
			}
		} while (position < s.length());
		if (isotopicNumber.toString().length() > 0)
		{
			try
			{
				atom.setMassNumber(Integer.parseInt(isotopicNumber.toString()));
			} catch (Exception exception)
			{
				logger.error("Could not set atom's atom number.");
				logger.debug(exception);
			}
		}
		logger.debug("Parsing part after element symbol (like charge): ", s.substring(position));
		int charge = 0;
		int implicitHydrogens = 0;
		while (position < s.length())
		{
			try
			{
				mychar = s.charAt(position);
				logger.debug("Parsing char: " + mychar);
				if (mychar == 'H')
				{
					// count implicit hydrogens
					implicitHydrogens = getImplicitHydrogenCount(s, position);
					position++;
					if (implicitHydrogens > 1)
					{
						position++;
					}
					atom.setHydrogenCount(implicitHydrogens);
				} else if (mychar == '+' || mychar == '-')
				{
					charge = getCharge(s, position);
					position++;
					if (charge < -1 || charge > 1)
					{
						position++;
					}
					atom.setFormalCharge(charge);
				} else if (mychar == '@')
				{
					if (position < s.length() - 1 && s.charAt(position + 1) == '@')
					{
						position++;
					}
					logger.warn("Ignoring stereo information for atom");
					position++;
				} else
				{
					throw new InvalidSmilesException("Found unexpected char: " + mychar);
				}
			} catch (InvalidSmilesException exc)
			{
				logger.error("InvalidSmilesException while parsing atom string: ", s);
				logger.debug(exc);
				throw exc;
			} catch (Exception exception)
			{
				logger.error("Could not parse atom string: ", s);
				logger.debug(exception);
				throw new InvalidSmilesException("Could not parse atom string: " + s);
			}
		}
		return atom;
	}


	/**
	 *  We call this method when a ring (depicted by a number) has been found.
	 *
	 *@param  atom  Description of the Parameter
	 */
	private void handleRing(Atom atom)
	{
		logger.debug("handleRing():");
		double bondStat = bondStatusForRingClosure;
		Bond bond = null;
		Atom partner = null;
		Atom thisNode = rings[thisRing];
		// lookup
		if (thisNode != null)
		{
			partner = thisNode;
			bond = new Bond(atom, partner, bondStat);
			      if (bondIsAromatic) {
            	
                bond.setFlag(CDKConstants.ISAROMATIC, true);
            }
			molecule.addBond(bond);
            bondIsAromatic = false;
			rings[thisRing] = null;
			ringbonds[thisRing] = -1;

		} else
		{
			/*
			 *  First occurence of this ring:
			 *  - add current atom to list
			 */
			rings[thisRing] = atom;
			ringbonds[thisRing] = bondStatusForRingClosure;
		}
		bondStatusForRingClosure = 1;
	}
        
        public static void main(String[] args) {
          String smiles = "[n+]%101ccccc1.[O-]%10";
          
          SmilesParser smilesParser = new SmilesParser();
          IMolecule molecule = null;
          try {
            molecule = smilesParser.parseSmiles(smiles);
          } catch (InvalidSmilesException ex) {
            ex.printStackTrace();
          }

          StructureDiagramGenerator sdg = new StructureDiagramGenerator();
          sdg.setMolecule(molecule);
          try {
            sdg.generateCoordinates();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          molecule = sdg.getMolecule();

          try {
            IChemObjectWriter mdlWriter = new MDLWriter(new FileWriter(new File("./library.sdf")));
            mdlWriter.write(molecule);
            mdlWriter.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          
          SmilesGenerator smilesGenerator = new SmilesGenerator(DefaultChemObjectBuilder.getInstance());
          System.out.println(smilesGenerator.createSMILES(molecule));
          
          System.out.println("Without set parity");
          for (int i = 0; i < molecule.getAtoms().length; i++) {
            System.out.println(i + ": " + molecule.getAtoms()[i]);
          }

          IAtomParity atomParity = new AtomParity(molecule.getAtomAt(0), molecule.getAtomAt(1), molecule.getAtomAt(2), molecule.getAtomAt(3), molecule.getAtomAt(4), 1);
          molecule.addAtomParity(atomParity);
          
          sdg.setMolecule(molecule);
          try {
            sdg.generateCoordinates();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          molecule = sdg.getMolecule();
          System.out.println("With set parity");
          for (int i = 0; i < molecule.getAtoms().length; i++) {
            System.out.println(i + ": " + molecule.getAtoms()[i]);
          }

          try {
            System.out.println(smilesGenerator.createChiralSMILES(molecule, new boolean[0]));
          } catch (CDKException ex) {
            ex.printStackTrace();
          }
          
        }

}

