package org.coh.carnifax.combat.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class IsHidable {
	private boolean hidable;

	@JsonIgnore
	public boolean isHidable() {
		return hidable;
	}

	@JsonIgnore
	public void setHidable(boolean hidable) {
		this.hidable = hidable;
	}
	
	
}
