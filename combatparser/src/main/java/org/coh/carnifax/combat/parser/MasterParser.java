package org.coh.carnifax.combat.parser;

import java.io.File;
import java.util.UUID;

import org.coh.carnifax.combat.data.character.BaseCharInterface;
import org.coh.carnifax.combat.files.BaseCharOutput;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;

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
			parseCombatSession( uuid, combat, new String[0] );
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
	public BaseCharInterface parseCombatSession(String uuid, File f, String markers[] ) throws FileParseException, CombatParseException {
		CombatParser cp = new CombatParser();
		BaseCharInterface cha = cp.parse( uuid, f.getParentFile(), f, markers );
		BaseCharOutput bco = new BaseCharOutput();
		
		bco.setConcise( false );
		bco.setDps( true ); 
		
		bco.output( f.getParentFile(), cha );
		
		return cha;
	}

}
