package org.coh.carnifax.combat.data.powers;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BasePowerMixIn {
	
	@JsonIgnore
	private List<PowerInstance>	instances;
	
}
