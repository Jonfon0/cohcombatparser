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

@JsonPropertyOrder({ "name", "uuid", "startDate", "endDate", "start", "end", "duration", "combatStartDate", "combatEndDate",  "combatStart", "combatEnd", "combatDuration", 
	"totalXp", "xpPerCombatSecond", "totalInf", "infPerCombatSecond", "damagePerCombatSecond", "data", "damage", "heal", "powers", "dps" })

public class BaseChar {
	private final static Logger logger = LogManager.getLogger( BaseChar.class );

	
	private String uuid;
	private String name;
	
	private Map<String, BasePower> powers;
	private Map<String, Integer> defeats;
	
	private Timestamp start;
	private Timestamp end;
	private long 	  duration=1;
	
	private Timestamp combatStart;
	private Timestamp combatEnd;
	private long 	  combatDuration=1;
	
	private long totalXP;
	private long totalInf;
	
	private BasePowerData data;
	private PowerSummary damage;
	private PowerSummary heal;
	private PowerSummary dps;
	private PowerSummary inf;
	private PowerSummary xp;
	private Map<Timestamp, String> drops;
	
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
	
	public long getCombatDuration() {
		return combatDuration;
	}

	public void setCombatDuration(long combatDuration) {
		this.combatDuration = combatDuration;
	}
	
	
	public Timestamp getStart() {
		return start;
	}
	
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public void setStart(long start) {
		this.start = new Timestamp(start);
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public void setEnd(long end) {
		this.end = new Timestamp(end);
	}

	public Timestamp getCombatStart() {
		return combatStart;
	}

	public void setCombatStart(Timestamp combatStartDate) {
		this.combatStart = combatStartDate;
	}
	public void setCombatStart(long combatStartDate) {
		this.combatStart = new Timestamp( combatStartDate );
	}

	public Timestamp getCombatEnd() {
		return combatEnd;
	}

	public void setCombatEnd(Timestamp combatEndDate) {
		this.combatEnd= combatEndDate;
	}
	public void setCombatEnd(long combatEndDate) {
		this.combatEnd= new Timestamp(combatEndDate);
	}

	public String getStartDate() {
		if( this.start == null) {
			return "";
		}
		
		return new Date( start.getTime() ).toString();
	}
	public String getEndDate() {
		if( this.end == null) {
			return "";
		}
		
		return new Date( end.getTime() ).toString();
	}
	
	public String getCombatStartDate() {
		if( this.combatStart == null) {
			return "";
		}
		
		return new Date( combatStart.getTime() ).toString();
	}

	public String getCombatEndDate() {
		if( this.combatEnd == null) {
			return "";
		}
		
		return new Date( combatEnd.getTime() ).toString();
	}
	

	public long getTotalXp() {
		return totalXP;
	}

	public void setTotalXp(long totalXP) {
		this.totalXP = totalXP;
	}

	public long getTotalInf() {
		return totalInf;
	}
	
	public long getXpPerCombatSecond() {
		return this.totalXP / combatDuration;
	}

	public long getInfPerCombatSecond() {
		return this.totalInf / combatDuration;
	}

	public long getDamagePerCombatSecond() {
		return (long)this.data.getDamageTotal() / combatDuration;
	}

	public void setTotalInf(long totalInf) {
		this.totalInf = totalInf;
	}

	public void addXp( Timestamp t, long xp ) {
		this.totalXP = totalXP + xp;
	}
	
	public void addInf( Timestamp t, long inf ) {
		this.totalInf = totalInf + inf;
	}
	
	public Map<String, BasePower> getPowers() {
		return powers;
	}
	public void setPowers(Map<String, BasePower> powers) {
		this.powers = powers;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public void updateDuration() {
		duration = ( end.getTime() - start.getTime() ) / 1000; 
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
		
		double total 		= this.data.getDamageTotal();
		double healTotal	= this.data.getHeal();
		
		for( BasePower p : this.powers.values() ) {
			damage.add(p.getName(), p.getData().getDamageTotal(), total);
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
			
			Timestamp t = null;
			for( BasePower p : this.powers.values() ) {
				for( PowerInstance pi : p.getInstances() ) {
					if(  pi.getTimeMillis().getTime() / 1000 == i) {
						total += pi.getData().getDamageTotal();
						if( pi.getData().getDamageTotal() != 0 ) {
							t = pi.getTimeMillis();
						}
					}
				}
				
				for( BasePower sp : p.getSubPowers().values() ) {
					for( PowerInstance pi : sp.getInstances() ) {
						if(  pi.getTimeMillis().getTime() / 1000 == i) {
							total += pi.getData().getDamageTotal();
							if( pi.getData().getDamageTotal() != 0 ) {
								t = pi.getTimeMillis();
							}
						}
					}
		
				}
			}
			
			if( this.combatStart == null && t != null ) {
				this.combatStart = t;
			}
			
			if( t != null ) {
				this.combatEnd = t;
			}
			
			this.dps.add( "" + (i*1000), (total / counter), this.data.getDamageTotal());
			
		}
		if( this.combatStart != null && this.combatEnd != null ) {
			this.combatDuration = (this.combatEnd.getTime() - this.combatStart.getTime()) / 1000;
		}
		
	}

	
}
