package org.coh.carnifax.combat.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.data.character.BaseCharInterface;
import org.coh.carnifax.combat.data.character.BaseCharMixIn;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummary;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummaryMixIn;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.BasePowerMixIn;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BaseCharOutput {
	private final static Logger logger = LogManager.getLogger( BaseCharOutput.class );

	boolean concise = false;
	boolean dps 	= true;
	
	public BaseCharOutput() {
		// TODO Auto-generated constructor stub
	}

	public File output( File parentDir, BaseCharInterface c ) throws FileParseException {
		
		String name = (CombatDefs.SUMMARY + c.getStart().toString() + "_" + c.getName() + ".json").replaceAll( "[^a-zA-Z0-9._ -]", "_");
		File combat = new File( parentDir, name );
		
		c.setSummary( name );
		
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
			logger.debug( "Concise" );
			mapper.addMixIn(BasePower.class, BasePowerMixIn.class );
		}
		
		if( !dps ) {
			logger.debug( "Dps" );
			mapper.addMixIn(BaseChar.class, BaseCharMixIn.class );
			mapper.addMixIn(DamagePowerSummary.class, DamagePowerSummaryMixIn.class );
		}
		
		return mapper.writerWithDefaultPrettyPrinter();

	}
	
	public boolean isConcise() {
		return concise;
	}

	public void setConcise(boolean consise) {
		this.concise = consise;
		logger.debug( "Concise set " + this.concise );

	}

	public boolean isDps() {
		return dps;
	}

	public void setDps(boolean dps) {
		this.dps = dps;
		logger.debug( "DPS set " + this.dps );
	}
	
	
	
}
