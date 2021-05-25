package org.coh.carnifax.combat.data.powers;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties( allowGetters = true)
@JsonPropertyOrder({ "activations", "hits", "misses", "accuracy", "criticals", "holds", "knockbacks", "damageTotal", "damage", "damageOverTime", 
	"averageDamagePerActivation", "averageDamagePerHit", "minDamage", "maxDamage", "damageTypes", "heal", "endHeal", "endDamage"})
public class BasePowerData {

	private double damage;
	private Double minDamage = null;
	private Double maxDamage = null;
	
	private double damageOverTime;
	
	
	private Map<String, Double> damageTypes;
	
	private double heal;
	private double endDamage;
	private double endHeal;
	
	private int hits;
	private int misses;
	private int criticals;
	private double accuracy;
	
	
	private Map<String, Integer> mez;
	
	//private int holds;
	//private int knockbacks;
	
	public BasePowerData() {
		damageTypes = new TreeMap<String, Double>();
		mez = new TreeMap<String, Integer>();
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public double getAverageDamagePerHit(){
		return this.getHits() == 0 ? 0.00 : this.getDamageTotal() / this.getHits(); 
	}
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public double getAverageDamagePerActivation(){
		return this.getActivations() == 0 ? 0.00 : this.getDamageTotal() / this.getActivations(); 
	}
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
		
		if( minDamage == null || damage < minDamage ) {
			minDamage = damage;
		}
		if( maxDamage == null || damage > maxDamage ) {
			maxDamage = damage;
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
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public double getAccuracy(){
		if( (hits + misses) == 0 ) {
			accuracy = 0;
		}
		else {
			accuracy = (double)(hits * 100 / (hits + misses) );
		}
		
		return accuracy;
	}
	
	public int getCriticals() {
		return criticals;
	}

	public void setCriticals(int criticals) {
		this.criticals = criticals;
	}

	public void addHit() {
		this.hits++;
	}
	
	public void addMiss(){
		this.misses++;
	}
	
	public void addCrit(){
		this.criticals++;
	}
	
	public void addHeal(double heal2) {
		this.heal = this.heal + heal2;
	}

	public Map<String, Integer> getMez() {
		return this.mez;
	}

	public void setMez( Map<String, Integer> mez) {
		this.mez = mez;
	}
	public void addMez( String type ) {
		int n = 0;
		if( this.mez.containsKey(type) ) {
			n = this.mez.get( type );
		}
		
		this.mez.put(type, n+1 );
	}

	public Double getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(Double minDamage) {
		this.minDamage = minDamage;
	}

	public Double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(Double maxDamage) {
		this.maxDamage = maxDamage;
	}
	
	
}