package org.coh.carnifax.combat.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.BaseChar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseCharOutput {

	public BaseCharOutput() {
		// TODO Auto-generated constructor stub
	}

	public void output( File parentDir, BaseChar c ) throws FileParseException {
		File combat = new File( parentDir, (CombatDefs.SUMMARY + c.getName() + "_" + c.getStartMillis().toString() + ".json") );
		
		ObjectMapper mapper = new ObjectMapper();
		
		try ( FileWriter r = new FileWriter(combat) ){
			mapper.writerWithDefaultPrettyPrinter().writeValue( r, c );
		} catch (IOException e) {
			throw new FileParseException( e );
		}
		
	}
	
}
