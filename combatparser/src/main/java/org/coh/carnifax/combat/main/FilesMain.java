package org.coh.carnifax.combat.main;

import java.io.File;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.files.BaseCharOutput;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;
import org.coh.carnifax.combat.parser.CombatParseException;
import org.coh.carnifax.combat.parser.CombatParser;
import org.coh.carnifax.combat.parser.InitialFileParser;
import org.coh.carnifax.combat.parser.MasterParser;
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
			MasterParser p = new MasterParser();
			p.parseAll( f );
		} catch (FileParseException | CombatParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Completed. Took " + (System.currentTimeMillis() - start) + " ms");
	}

}
