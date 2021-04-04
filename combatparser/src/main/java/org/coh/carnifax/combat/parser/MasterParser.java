package org.coh.carnifax.combat.parser;

import java.io.File;
import java.util.UUID;

import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.files.BaseCharOutput;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;
import org.coh.carnifax.combat.files.InitialFileParser;

public class MasterParser {

	public void parse(File f) throws FileParseException, CombatParseException {
		String uuid = UUID.randomUUID().toString();
		
		InitialFileParser p = new InitialFileParser();
		
		FileDescriptor d = p.parse(f, uuid );
		
		for( File combat : d.getCombat().values() ) {
			CombatParser cp = new CombatParser();
			BaseChar cha = cp.parse( uuid, p.getInputFolder(), combat );
			BaseCharOutput bco = new BaseCharOutput();
			bco.output( p.getInputFolder(), cha );
		}
		
		d.destroy();
		
	}

}
