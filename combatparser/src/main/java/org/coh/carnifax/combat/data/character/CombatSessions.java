package org.coh.carnifax.combat.data.character;

import java.util.UUID;

public class CombatSessions {

	private String name;
	private long start;
	private long end;
	private String uuid;
	
	public CombatSessions(){
		this.uuid = UUID.randomUUID().toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
