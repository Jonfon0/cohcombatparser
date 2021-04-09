package org.coh.carnifax.combat.data.powers;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class BasePowerData {
	@JsonPropertyOrder({ "activations", "hits", "misses", "damageTotal", "damage", "damageOverTime", "averageDamagePerActivation", "averageDamagePerHit", "damageTypes", "heal", "endHeal", "endDamage"})

	private double damage;
	private double damageOverTime;
	
	
	private Map<String, Double> damageTypes;
	
	private double heal;
	private double endDamage;
	private double endHeal;
	
	private int hits;
	private int misses;
	
	public BasePowerData() {
		damageTypes = new TreeMap<String, Double>();
	}

	public double getDamageTotal() {
		return damage + damageOverTime;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamageOverTime() {
		return damageOverTime;
	}
	
	public double getAverageDamagePerHit(){
		return this.getHits() == 0 ? 0.00 : this.getDamageTotal() / this.getHits(); 
	}
	
	public double getAverageDamagePerActivation(){
		return this.getActivations() == 0 ? 0.00 : this.getDamageTotal() / this.getActivations(); 
	}
	
	public int getActivations(){
		return hits + misses;
	}

	public void setDamageOverTime(double damageOverTime) {
		this.damageOverTime = damageOverTime;
	}

	public void addDamage( String type, double damage ) {
		this.damage = this.damage + damage;

		double iDam = 0.00;
		if( this.damageTypes.containsKey(type)) {
			iDam = this.damageTypes.get( type );
		}
		
		this.damageTypes.put( type, iDam + damage );

	}
	public void addDamageOverTime( String type, double damage ) {
		this.damageOverTime = this.damageOverTime + damage;

		double iDam = 0.00;
		if( this.damageTypes.containsKey(type)) {
			iDam = this.damageTypes.get( type );
		}
		
		this.damageTypes.put( type, iDam + damage );

	}
	
	public Map<String, Double> getDamageTypes() {
		return damageTypes;
	}

	public void setDamageTypes(Map<String, Double> damageTypes) {
		this.damageTypes = damageTypes;
	}

	public double getHeal() {
		return heal;
	}

	public void setHeal(double heal) {
		this.heal = heal;
	}

	public double getEndDamage() {
		return endDamage;
	}

	public void setEndDamage(double endDamage) {
		this.endDamage = endDamage;
	}

	public double getEndHeal() {
		return endHeal;
	}

	public void setEndHeal(double endHeal) {
		this.endHeal = endHeal;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public void addHit() {
		this.hits++;
	}
	
	public void addMiss(){
		this.misses++;
	}

	public void addHeal(double heal2) {
		this.heal = this.heal + heal2;
	}
	
	
	
}