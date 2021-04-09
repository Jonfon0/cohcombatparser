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

public class BaseChar implements BaseCharInterface {
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
	public BasePowerData getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setData(org.coh.carnifax.combat.data.powers.BasePowerData)
	 */
	@Override
	public void setData(BasePowerData data) {
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDamage()
	 */
	@Override
	public PowerSummary getDamage() {
		return damage;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getHeal()
	 */
	@Override
	public PowerSummary getHeal() {
		return heal;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDps()
	 */
	@Override
	public PowerSummary getDps() {
		return dps;
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
	public long getXpPerCombatSecond() {
		return this.totalXP / combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getInfPerCombatSecond()
	 */
	@Override
	public long getInfPerCombatSecond() {
		return this.totalInf / combatDuration;
	}

	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#getDamagePerCombatSecond()
	 */
	@Override
	public long getDamagePerCombatSecond() {
		return (long)this.data.getDamageTotal() / combatDuration;
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
	@Override
	public Map<String, BasePower> getPowers() {
		return powers;
	}
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#setPowers(java.util.Map)
	 */
	@Override
	public void setPowers(Map<String, BasePower> powers) {
		this.powers = powers;
	}

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
	
	/* (non-Javadoc)
	 * @see org.coh.carnifax.combat.data.character.BaseCharInterface#generateSummaries()
	 */
	@Override
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
