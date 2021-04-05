package org.coh.carnifax.combat.data.character;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.coh.carnifax.combat.data.character.summary.PowerSummaryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummary;
import org.coh.carnifax.combat.data.character.summary.PowerSummary;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.BasePowerData;
import org.coh.carnifax.combat.data.powers.PowerInstance;
import org.coh.carnifax.combat.parser.CombatParser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "uuid", "startDate", "endDate", "startMillis", "endMillis", "data", "damage", "heal", "powers", "dps" })

public class BaseChar {
	private final static Logger logger = LogManager.getLogger( BaseChar.class );

	
	private String uuid;
	private String name;
	
	private Map<String, BasePower> powers;
	private Map<String, Integer> defeats;
	
	private Timestamp start;
	private Timestamp end;
	
	private BasePowerData data;
	private PowerSummary damage;
	private PowerSummary heal;
	private PowerSummary dps;
	
	public BaseChar( String uuid ){
		this.uuid = uuid;
		this.powers = new TreeMap<String, BasePower>();
		this.data   = new BasePowerData();
		this.defeats = new TreeMap<>();
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BasePowerData getData() {
		return data;
	}

	public void setData(BasePowerData data) {
		this.data = data;
	}

	public PowerSummary getDamage() {
		return damage;
	}
	public PowerSummary getHeal() {
		return heal;
	}
	public PowerSummary getDps() {
		return dps;
	}
	public Map<String, Integer> getDefeats(){
		return this.defeats;
	}
	
	
	public Map<String, BasePower> getPowers() {
		return powers;
	}
	public void setPowers(Map<String, BasePower> powers) {
		this.powers = powers;
	}
	public Timestamp getStartMillis() {
		return start;
	}
	public String getStartDate() {
		if( this.start == null) {
			return "";
		}
		
		return new Date( start.getTime() ).toString();
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public void setStart(long start) {
		this.start = new Timestamp(start);
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	} 

	public void setEnd(long end) {
		this.end = new Timestamp(end);
	} 

	public Timestamp getEndMillis() {
		return end;
	}
	public String getEndDate(){
		if( this.end == null) {
			return "";
		}
		
		return new Date( end.getTime() ).toString();
	}
	public BasePower getPower( Timestamp t, String name ) {
		if( this.start == null ) {
			this.start = t;
		}
		this.end = t;
		
		if( !this.powers.containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName( name );
			p.setTimestamp( t );
			this.powers.put(name, p);
		}
		
		return this.powers.get( name );
		
	}
	
	public void addDefeat( String name, String target ) {
		int count = 0;
		if( this.defeats.containsKey(name) ) {
			count = this.defeats.get( name );
		}
		
		
		this.defeats.put( name, (count+1) );
	}
	
	public void generateSummaries() {
		
		this.damage = new PowerSummaryImpl();
		this.heal   = new PowerSummaryImpl();
		this.dps 	= new DamagePowerSummary();
		this.dps.setHidable(true);
		
		double total 		= this.data.getDamage();
		double healTotal	= this.data.getHeal();
		
		for( BasePower p : this.powers.values() ) {
			damage.add(p.getName(), p.getData().getDamage(), total);
			heal.add(p.getName(), p.getData().getHeal(), healTotal );
		}
		
		// DPS
		total = 0.00;
		long counter = 0;
		
		logger.debug("Summary start : " + start + ". End " + end );
		
		if( end == null ) {
			end = start;
		}
		
		for( long i = start.getTime() / 1000; i <= end.getTime() / 1000; i++ ) {
			counter++;
			
			for( BasePower p : this.powers.values() ) {
				for( PowerInstance pi : p.getInstances() ) {
					if(  pi.getTimeMillis().getTime() / 1000 == i) {
						total += pi.getData().getDamage();
					}
				}
				
				for( BasePower sp : p.getSubPowers().values() ) {
					for( PowerInstance pi : sp.getInstances() ) {
						if(  pi.getTimeMillis().getTime() / 1000 == i) {
							total += pi.getData().getDamage();
						}
					}
		
				}
			}
			
			this.dps.add( "" + (i*1000), (total / counter), this.data.getDamage());
			
		}
		
	}
	
}
