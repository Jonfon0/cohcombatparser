package org.coh.carnifax.combat.main;

import java.io.File;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.files.BaseCharOutput;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;
import org.coh.carnifax.combat.files.InitialFileParser;
import org.coh.carnifax.combat.parser.CombatParseException;
import org.coh.carnifax.combat.parser.CombatParser;
import org.apache.logging.log4j.LogManager;

public class FilesMain {

	private final static Logger logger = LogManager.getLogger( FilesMain.class );
	
	public static void main(String[] args) {
		
		if( args.length == 0 ) {
			throw new RuntimeException("File name must be specified");
		}
		
		String filename = args[0];
		
		long start = System.currentTimeMillis();
		
		File f = new File( filename );
		
		
		logger.info("Initial parse of " + f.getAbsolutePath());
		if( !f.exists() ) {
			throw new RuntimeException("File " + f.getAbsolutePath() + " does not exist");
		}
		
		
		try {
			InitialFileParser p = new InitialFileParser();
			
			String uuid = UUID.randomUUID().toString();
			FileDescriptor d = p.parse(f, uuid );
			
			for( File combat : d.getCombat().values() ) {
				CombatParser cp = new CombatParser();
				BaseChar cha = cp.parse( uuid, p.getInputFolder(), combat );
				BaseCharOutput bco = new BaseCharOutput();
				bco.output( p.getInputFolder(), cha );
			}
			
			d.destroy();
			
		} catch (FileParseException | CombatParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Completed. Took " + (System.currentTimeMillis() - start) + " ms");
	}

}
