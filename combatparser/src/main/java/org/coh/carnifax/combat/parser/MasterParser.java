package org.coh.carnifax.combat.parser;

import java.io.File;
import java.util.UUID;

import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.files.BaseCharOutput;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;
import org.coh.carnifax.combat.files.InitialFileParser;

public class MasterParser {

	/**
	 * Full parse. Split a Log into Globals and CombatSessions then parse all the CombatSessions 
	 * @param f City of Heroes log file
	 * @return UUID of folder generated
	 * @throws FileParseException If the File Split fails
	 * @throws CombatParseException If the Combat Parsing fails
	 */
	public String parseAll(File f) throws FileParseException, CombatParseException {
		String uuid = UUID.randomUUID().toString();
		
		InitialFileParser p = new InitialFileParser();
		
		FileDescriptor d = p.parse(f, uuid );
		
		for( File combat : d.getCombat().values() ) {
			parseCombatSession( uuid, combat );
		}
		
		d.destroy();
		
		return uuid;
	}

	/**
	 * @param f 
	 * @return
	 * @throws FileParseException
	 * @throws CombatParseException
	 */
	public String splitCombatLog( File f ) throws FileParseException, CombatParseException {
		String uuid = UUID.randomUUID().toString();
		
		InitialFileParser p = new InitialFileParser();
		FileDescriptor d = p.parse(f, uuid );
		
		d.destroyGlobal();
		
		return uuid;
	}
	
	/**
	 * Parse a Combat Session file into the JSON
	 * @param uuid Folder UUID
	 * @param f CombatSession log to be parsed
	 * @return UUID 
	 * @throws FileParseException
	 * @throws CombatParseException
	 */
	public BaseChar parseCombatSession(String uuid, File f) throws FileParseException, CombatParseException {
		CombatParser cp = new CombatParser();
		BaseChar cha = cp.parse( uuid, f.getParentFile(), f );
		BaseCharOutput bco = new BaseCharOutput();
		
		bco.output( f.getParentFile(), cha );
		
		return cha;
	}

}
