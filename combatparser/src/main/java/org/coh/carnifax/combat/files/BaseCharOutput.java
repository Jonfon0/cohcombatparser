package org.coh.carnifax.combat.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummary;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummaryMixIn;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.BasePowerMixIn;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BaseCharOutput {

	boolean concise = false;
	
	public BaseCharOutput() {
		// TODO Auto-generated constructor stub
	}

	public File output( File parentDir, BaseChar c ) throws FileParseException {
		
		String name = (CombatDefs.SUMMARY + c.getName() + "_" + c.getStartMillis().toString() + ".json").replaceAll( "[^a-zA-Z0-9._-]", "_");
		
		File combat = new File( parentDir, name );
		
		
		try ( FileWriter r = new FileWriter(combat) ){
			
			this.getObjectWriter().writeValue( r, c );
			
		} catch (IOException e) {
			throw new FileParseException( e );
		}
		
		return combat;
		
	}

	public ObjectWriter getObjectWriter(){
		ObjectMapper mapper = new ObjectMapper();
		
		if( concise ) {
			mapper.addMixIn(BasePower.class, BasePowerMixIn.class );
			mapper.addMixIn(DamagePowerSummary.class, DamagePowerSummaryMixIn.class );
		}
		
		return mapper.writerWithDefaultPrettyPrinter();

	}
	
	public boolean isConcise() {
		return concise;
	}

	public void setConcise(boolean consise) {
		this.concise = consise;
	}
	
	
	
}
