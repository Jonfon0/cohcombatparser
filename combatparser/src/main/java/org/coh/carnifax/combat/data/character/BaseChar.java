package org.coh.carnifax.combat.data.character;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.coh.carnifax.combat.data.character.summary.PowerSummaryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.summary.DamagePowerSummary;
import org.coh.carnifax.combat.data.character.summary.DropData;
import org.coh.carnifax.combat.data.character.summary.MarkerEntry;
import org.coh.carnifax.combat.data.character.summary.PowerSummary;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.BasePowerData;
import org.coh.carnifax.combat.data.powers.PowerInstance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "uuid", "summary", "startDate", "endDate", "start", "end", "duration", "combatStartDate", "combatEndDate",  "combatStart", "combatEnd", "combatDuration", 
	"totalXp", "xpPerCombatSecond", "totalInf", "infPerCombatSecond", "damagePerCombatSecond", "offensive", "defensive", "drops" })
@JsonIgnoreProperties( allowGetters = true)
public class BaseChar implements BaseCharInterface {
	private final static Logger logger = LogManager.getLogger( BaseChar.class );

	
	private String uuid;
	private String name;
	private String summary;
	
	private Map<String, Integer> defeats;
	private DropData drops;
	
	private Timestamp start;
	private Timestamp end;
	private long 	  duration=1;
	
	private Timestamp combatStart;
	private Timestamp combatEnd;
	private long 	  combatDuration=1;
	
	private long totalXP;
	private long totalInf;
	
	private Powers offensive;
	private Powers defensive;

	private Map<String, MarkerEntry> markers;
	
	public BaseChar( ){
		this.uuid = "";
		this.defeats = new TreeMap<String, Integer>();
		this.drops = new DropData();
		
		this.offensive = new Powers();
		this.defensive = new Powers();
		
		this.markers = new TreeMap<String, MarkerEntry>();
	}
	
	public BaseChar( String uuid ){
		this();
		this.uuid = uuid;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getUuid()
	 */
	@Override
	public String getUuid() {
		return uuid;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setUuid(java.lang.String)
	 */
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getData()
	 */
	@Override
	public Powers getOffensive() {
		return offensive;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setData(org.coh.carnifax.combat.data.powers.BasePowerData)
	 */
	@Override
	public void setOffensive(Powers data) {
		this.offensive = data;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDefeats()
	 */
	@Override
	public Map<String, Integer> getDefeats(){
		return this.defeats;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getCombatDuration()
	 */
	@Override
	public long getCombatDuration() {
		return combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setCombatDuration(long)
	 */
	@Override
	public void setCombatDuration(long combatDuration) {
		this.combatDuration = combatDuration;
	}
	
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getStart()
	 */
	@Override
	public Timestamp getStart() {
		return start;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setStart(java.sql.Timestamp)
	 */
	@Override
	public void setStart(Timestamp start) {
		this.start = start;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setStart(long)
	 */
	@Override
	public void setStart(long start) {
		this.start = new Timestamp(start);
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getEnd()
	 */
	@Override
	public Timestamp getEnd() {
		return end;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setEnd(java.sql.Timestamp)
	 */
	@Override
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setEnd(long)
	 */
	@Override
	public void setEnd(long end) {
		this.end = new Timestamp(end);
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getCombatStart()
	 */
	@Override
	public Timestamp getCombatStart() {
		return combatStart;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setCombatStart(java.sql.Timestamp)
	 */
	@Override
	public void setCombatStart(Timestamp combatStartDate) {
		this.combatStart = combatStartDate;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setCombatStart(long)
	 */
	@Override
	public void setCombatStart(long combatStartDate) {
		this.combatStart = new Timestamp( combatStartDate );
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getCombatEnd()
	 */
	@Override
	public Timestamp getCombatEnd() {
		return combatEnd;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setCombatEnd(java.sql.Timestamp)
	 */
	@Override
	public void setCombatEnd(Timestamp combatEndDate) {
		this.combatEnd= combatEndDate;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setCombatEnd(long)
	 */
	@Override
	public void setCombatEnd(long combatEndDate) {
		this.combatEnd= new Timestamp(combatEndDate);
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getStartDate()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getStartDate() {
		if( this.start == null) {
			return "";
		}
		
		return new Date( start.getTime() ).toString();
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getEndDate()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getEndDate() {
		if( this.end == null) {
			return "";
		}
		
		return new Date( end.getTime() ).toString();
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getCombatStartDate()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getCombatStartDate() {
		if( this.combatStart == null) {
			return "";
		}
		
		return new Date( combatStart.getTime() ).toString();
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getCombatEndDate()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getCombatEndDate() {
		if( this.combatEnd == null) {
			return "";
		}
		
		return new Date( combatEnd.getTime() ).toString();
	}
	

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getTotalXp()
	 */
	@Override
	public long getTotalXp() {
		return totalXP;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setTotalXp(long)
	 */
	@Override
	public void setTotalXp(long totalXP) {
		this.totalXP = totalXP;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getTotalInf()
	 */
	@Override
	public long getTotalInf() {
		return totalInf;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getXpPerCombatSecond()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public long getXpPerCombatSecond() {
		return this.totalXP / combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getInfPerCombatSecond()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public long getInfPerCombatSecond() {
		return this.totalInf / combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDamagePerCombatSecond()
	 */
	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public long getDamagePerCombatSecond() {
		return (long)this.offensive.getData().getDamageTotal() / combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setTotalInf(long)
	 */
	@Override
	public void setTotalInf(long totalInf) {
		this.totalInf = totalInf;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#addXp(java.sql.Timestamp, long)
	 */
	@Override
	public void addXp( Timestamp t, long xp ) {
		this.totalXP = totalXP + xp;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#addInf(java.sql.Timestamp, long)
	 */
	@Override
	public void addInf( Timestamp t, long inf ) {
		this.totalInf = totalInf + inf;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getPowers()
	 */

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDuration()
	 */
	@Override
	public long getDuration() {
		return duration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setDuration(long)
	 */
	@Override
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#updateDuration()
	 */
	@Override
	public void updateDuration() {
		duration = ( end.getTime() - start.getTime() ) / 1000; 
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getPower(java.sql.Timestamp, java.lang.String)
	 */
	@Override
	public BasePower getOffensivePower( Timestamp t, String name ) {
		if( this.start == null ) {
			this.start = t;
		}
		this.end = t;
		
		if( !this.offensive.getPowers().containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName( name );
			p.setTimestamp( t );
			this.offensive.getPowers().put(name, p);
		}
		
		return this.offensive.getPowers().get( name );
	}

	@Override
	public BasePower getOffensiveTarget( Timestamp t, String name ) {
		if( this.start == null ) {
			this.start = t;
		}
		this.end = t;
		
		if( !this.offensive.getTargets().containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName( name );
			p.setTimestamp( t );
			this.offensive.getTargets().put(name, p);
		}
		
		return this.offensive.getTargets().get( name );
	}
	

	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getPower(java.sql.Timestamp, java.lang.String)
	 */
	@Override
	public BasePower getDefensivePower( Timestamp t, String name ) {
		if( this.start == null ) {
			this.start = t;
		}
		this.end = t;
		
		if( !this.defensive.getPowers().containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName( name );
			p.setTimestamp( t );
			this.defensive.getPowers().put(name, p);
		}
		
		return this.defensive.getPowers().get( name );
	}
	
	@Override
	public BasePower getDefensiveTarget( Timestamp t, String name ) {
		if( this.start == null ) {
			this.start = t;
		}
		this.end = t;
		
		if( !this.defensive.getTargets().containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName( name );
			p.setTimestamp( t );
			this.defensive.getTargets().put(name, p);
		}
		
		return this.defensive.getTargets().get( name );
	}

	
	public Powers getDefensive() {
		return defensive;
	}

	public void setDefensive(Powers defensive) {
		this.defensive = defensive;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#addDefeat(java.lang.String, java.lang.String)
	 */
	@Override
	public void addDefeat( String name, String target ) {
		int count = 0;
		if( this.defeats.containsKey(name) ) {
			count = this.defeats.get( name );
		}
		
		
		this.defeats.put( name, (count+1) );
	}
	
	@Override
	public DropData getDrops() {
		return drops;
	}

	@Override
	public void setDrops(DropData drops) {
		this.drops = drops;
	}

	@Override
	public synchronized void addDrop( String name ) {
		this.drops.addDrop(name);
	}
	
	@Override
	public String getSummary() {
		return summary;
	}

	@Override
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public Map<String, MarkerEntry> getMarkers() {
		return markers;
	}
	@Override
	public void setMarkers(Map<String, MarkerEntry> markers) {
		this.markers = markers;
	}
	@Override
	public void addMarker( String name, Timestamp t ) {
		if( !this.markers.containsKey(name) ) {
			MarkerEntry e = new MarkerEntry();
			e.setName(name);
			this.markers.put(name, e);
		}
		
		this.markers.get(name).addInstance( t.getTime() );
	}
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#generateSummaries()
	 */
	@Override
	public void generateSummaries() {
				
		double total;
		generateTotals( this.offensive );
		generateTotals( this.defensive );
		
		
		// DPS
		total = 0.00;
		double defTotal = 0;
		
		long counter = 0;
		
		logger.debug("Summary start : " + start + ". End " + end );
		
		if( end == null ) {
			end = start;
		}
		
		for( long i = start.getTime() / CombatDefs.INTERVAL; i <= end.getTime() / CombatDefs.INTERVAL; i++ ) {
			counter++;
			
			Timestamp t = null;
			for( BasePower p : this.offensive.getPowers().values() ) {
				
				for( PowerInstance pi : p.getInstances() ) {
					if(  pi.getTimeMillis().getTime() / CombatDefs.INTERVAL == i) {
						total += pi.getData().getDamageTotal();
						
						if( pi.getData().getDamageTotal() != 0 ) {
							t = pi.getTimeMillis();
						}
					}
				}
				
				for( BasePower sp : p.getSubPowers().values() ) {
					for( PowerInstance pi : sp.getInstances() ) {
						if(  pi.getTimeMillis().getTime() / CombatDefs.INTERVAL == i) {
							total += pi.getData().getDamageTotal();
							
							if( pi.getData().getDamageTotal() != 0 ) {
								t = pi.getTimeMillis();
							}
						}
					}
		
				}
				
			}
			
			this.offensive.getDps().add( "" + (i*CombatDefs.INTERVAL), total, this.offensive.getData().getDamageTotal());
			
			if( this.combatStart == null && t != null ) {
				this.combatStart = t;
			}
			if( t != null ) {
				this.combatEnd = t;
			}
			
			// Defensive
			for( BasePower p : this.defensive.getPowers().values() ) {
				for( PowerInstance pi : p.getInstances() ) {
					if(  pi.getTimeMillis().getTime() / CombatDefs.INTERVAL == i) {
						defTotal += pi.getData().getDamageTotal();
					}
				}
				
				for( BasePower sp : p.getSubPowers().values() ) {
					for( PowerInstance pi : sp.getInstances() ) {
						if(  pi.getTimeMillis().getTime() / CombatDefs.INTERVAL == i) {
							defTotal += pi.getData().getDamageTotal();
						}
					}
		
				}
			}
			this.defensive.getDps().add( "" + (i*CombatDefs.INTERVAL), defTotal, this.defensive.getData().getDamageTotal());
		}
		
		
		if( this.combatStart != null && this.combatEnd != null ) {
			
			this.combatEnd = new Timestamp( ((DamagePowerSummary)(this.offensive.getDps() )).trim( combatStart.getTime(), combatEnd.getTime() ) );
			((DamagePowerSummary)(this.defensive.getDps() )).trim( combatStart.getTime(), combatEnd.getTime() );

			this.combatDuration = (this.combatEnd.getTime() - this.combatStart.getTime()) / 1000;
		}
		
	}

	private void generateTotals( Powers o ) {
		double total 		= o.getData().getDamageTotal();
		double healTotal	= o.getData().getHeal();
		
		for( BasePower p : o.getPowers().values() ) {
			o.getDamage().add(p.getName(), p.getData().getDamageTotal(), total);
			o.getHeal().add(p.getName(), p.getData().getHeal(), healTotal );
		}
	}

	
}
