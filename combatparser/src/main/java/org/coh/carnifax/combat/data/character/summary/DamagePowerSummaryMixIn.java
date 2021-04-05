package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract  class DamagePowerSummaryMixIn {
	@JsonIgnore 
	private Map<String, SummaryEntry> entries;
	
	@JsonIgnore 
	private Map<String, SummaryEntry> summary;
	
	

}
