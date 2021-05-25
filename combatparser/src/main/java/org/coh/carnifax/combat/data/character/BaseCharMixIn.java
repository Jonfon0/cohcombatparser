package org.coh.carnifax.combat.data.character;

import org.coh.carnifax.combat.data.character.summary.PowerSummary;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseCharMixIn {
	@JsonIgnore
	private PowerSummary dps;
	
}
