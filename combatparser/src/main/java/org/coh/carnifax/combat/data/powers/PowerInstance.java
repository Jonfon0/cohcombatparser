package org.coh.carnifax.combat.data.powers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PowerInstance {
	
	private Timestamp timestamp;
	
	private BasePowerData data;
	
	private boolean didHit;
	private double toHit;
	private double toHitRoll;
	
	private boolean didKnock;
	
	private Map<String, Double> damageTypes; 

	public PowerInstance(){
		this.damageTypes = new TreeMap<String, Double>();
		this.data = new BasePowerData();
	}
	
	public BasePowerData getData() {
		return data;
	}

	public void setData(BasePowerData data) {
		this.data = data;
	}

	public double getToHit() {
		return toHit;
	}
	public void setToHit(double toHit) {
		this.toHit = toHit;
	}
	public double getToHitRoll() {
		return toHitRoll;
	}
	public void setToHitRoll(double toHitRoll) {
		this.toHitRoll = toHitRoll;
	}
	public boolean isDidKnock() {
		return didKnock;
	}
	public void setDidKnock(boolean didKnock) {
		this.didKnock = didKnock;
	}
	public boolean isDidHit() {
		return didHit;
	}
	public void setDidHit(boolean didHit) {
		this.didHit = didHit;
	}
	
	public Timestamp getTimeMillis() {
		return timestamp;
	}
	
	public String getDate() {
		return new Date( timestamp.getTime() ).toString();
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Double> getDamageTypes() {
		return damageTypes;
	}

	public void setDamageTypes(Map<String, Double> damageTypes) {
		this.damageTypes = damageTypes;
	}

	
}
