package org.coh.carnifax.combat.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.BaseChar;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.PowerInstance;
import org.coh.carnifax.combat.main.FilesMain;



public class CombatParser {

	private final static Logger logger = LogManager.getLogger( CombatParser.class );

	private Pattern activate;
	private Pattern hit;
	private Pattern hitAuto;
	private Pattern miss;
	private Pattern damage;
	private Pattern pseudoDamage;
	private Pattern healOverTime;
	private Pattern heal;
	private Pattern pseudoHeal;
	private Pattern defeatsSelf;
	private Pattern defeatsOther;
	private Pattern date;
	private Pattern damageProc;
	private Pattern xpInf;
	
	private SimpleDateFormat df;
	
	private BaseChar ch;

		
	public CombatParser() {
		date = Pattern.compile(CombatDefs.PATTERN_DATE);
		activate = Pattern.compile( CombatDefs.PATTERN_ACTIVATE );
		hit = Pattern.compile( CombatDefs.PATTERN_HIT );
		hitAuto = Pattern.compile( CombatDefs.PATTERN_HIT_AUTO );
		miss= Pattern.compile( CombatDefs.PATTERN_MISS );
		damage = Pattern.compile( CombatDefs.PATTERN_DAMAGE );
		damageProc = Pattern.compile( CombatDefs.PATTERN_DAM_PROC );
		pseudoDamage = Pattern.compile( CombatDefs.PATTERN_PSEUDODAM );
		healOverTime = Pattern.compile( CombatDefs.PATTERN_HEAL_OVER_TIME );
		heal = Pattern.compile(CombatDefs.PATTERN_HEAL);
		pseudoHeal = Pattern.compile(CombatDefs.PATTERN_PSEUDOHEAL);
		defeatsSelf = Pattern.compile(CombatDefs.PATTERN_DEFEATS_SELF);
		defeatsOther = Pattern.compile(CombatDefs.PATTERN_DEFEATS_OTHER);
		xpInf = Pattern.compile( CombatDefs.PATTERN_XP_INF );
		
		df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
	}
	
	public BaseChar parse( String uuid, File parentDir, File combat ) throws CombatParseException {
		
		if( !combat.exists() ) {
			throw new CombatParseException("Could not open combat file " + combat.getAbsolutePath() );
		}
		
		
		this.ch = this.createBaseChar( uuid, combat.getName() );

		logger.info("Parsing " + ch.getStartDate() + " : " + ch.getName() );
		
		String line = null;
		int count = 0;
		Timestamp lastDate = null;
		
		try(BufferedReader r = new BufferedReader(new FileReader(combat))) {
			do {
				count++;
				line = r.readLine();
				if( line != null ) {
					this.parseLine( line, count );
				}
				
			} while( line != null );
			
		} catch (IOException e) {
			throw new CombatParseException( e );
		} catch( ParseException e ) {
			throw new CombatParseException( e );
		}

		logger.debug("Complete. Generating summaries");

		this.ch.generateSummaries();
		this.ch.updateDuration();
		return ch;
		
	}
	
	private BaseChar createBaseChar(String uuid, String name) {
		
		String parts[] = name.replace(".txt", "").split("_");
		
		BaseChar ch = new BaseChar(uuid);
		ch.setName(parts[1]);
		ch.setStart( Long.parseLong(parts[parts.length-1]) );
		
		return ch;
	}

	public void parseLine( String line, int count ) throws ParseException {
		try {
			Matcher actM 		= this.activate.matcher( line );
			Matcher hitM 		= this.hit.matcher( line );
			Matcher hitAutoM		= this.hitAuto.matcher( line );
			Matcher missM 		= this.miss.matcher( line );
			Matcher damM 		= this.damage.matcher( line );
			Matcher damProcM	= this.damageProc.matcher( line );
			Matcher pseudoDamM 	= this.pseudoDamage.matcher( line );
			Matcher healOverTimeM= this.healOverTime.matcher( line );
			Matcher healM		= this.heal.matcher( line );
			Matcher pseudoHealM = this.pseudoHeal.matcher( line );
			Matcher defeatSelfM	= this.defeatsSelf.matcher( line );
			Matcher defeatOtherM= this.defeatsOther.matcher( line );
			Matcher xpInfM		= this.xpInf.matcher( line ); 
			
			Timestamp t 	= null;
			
			if( damM.matches() ) {
				t = this.parseDamage(damM, damProcM);
			}
			else if( pseudoDamM.matches() ) {
				t = this.parsePseudoDamage( pseudoDamM );
			}
			else if( hitM.matches() ) {
				t = this.parseHit( hitM );
			}

			else if( hitAutoM.matches() ) {
				t = this.parseHitAuto( hitAutoM );
			}
			else if( missM.matches() ) {
				t = this.parseMiss( missM );
			}
			else if( actM.matches() ) {
				t = this.parseActivate( actM );
			}
			else if( healOverTimeM.matches() ) {
				t = this.parseHealOverTime( healOverTimeM );
			}
			else if( healM.matches() ) {
				t = this.parseHeal( healM );
			}
			else if( pseudoHealM.matches() ) {
				t = this.parsePseudoHeal( pseudoHealM );
			}
			else if( defeatSelfM.matches() ) {
				t = this.parseDefeatSelf( defeatSelfM );
			}
			else if( defeatOtherM.matches() ) {
				t = this.parseDefeatOther( defeatOtherM );
			}
			else if( xpInfM.matches() ) {
				t = this.parseXpInf( xpInfM );
			}
			
			
			if( t!=null ) {
				ch.setEnd( t );
			}
			
		} catch (NumberFormatException | IllegalStateException e) {
			System.out.println("Error on " + count + " : " + line);
			
			throw e;
		}
		
		
	}

	// These really are Events, be nice to have an Event Handler for this stuff. Oh well. 
	
	private Timestamp parseXpInf(Matcher xpInfM) throws ParseException {
		Timestamp t 	= this.parseTime( xpInfM.group(1).trim() );
		long xp			= Long.parseLong( xpInfM.group(2).trim().replace(",", "") );
		long inf		= Long.parseLong( xpInfM.group(3).trim().replace(",", "") );
		
		ch.addXp( t, xp );
		ch.addInf( t, inf );
		
		return t;
	}

	private Timestamp parseDefeatOther(Matcher defeatOtherM) throws ParseException {
		Timestamp t 	= this.parseTime( defeatOtherM.group(1).trim() );
		String person	= defeatOtherM.group(2).trim();
		String target	= defeatOtherM.group(3).trim();
		
		ch.addDefeat( person, target);
		
		return t;
	}

	private Timestamp parseDefeatSelf(Matcher defeatSelfM) throws ParseException {
		Timestamp t 	= this.parseTime( defeatSelfM.group(1).trim() );
		String target	= defeatSelfM.group(2).trim();
		
		ch.addDefeat(" You", target);
		
		return t;
	}

	private Timestamp parseHeal(Matcher healM) throws ParseException {
		Timestamp t 	= this.parseTime( healM.group(1).trim() );
		String target	= healM.group(2).trim();
		String power 	= healM.group(3).trim(); 
		
		double healV	= Double.parseDouble( healM.group(4).trim().replace(",", "")  );
		
		BasePower p 	= ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getData().addHeal(healV);
		
		return t;
	}

	private Timestamp parsePseudoHeal(Matcher pseudoHealM) throws ParseException {
		Timestamp t 	= this.parseTime( pseudoHealM.group(1).trim() );
		String power 	= pseudoHealM.group(2).trim(); 
		String target	= pseudoHealM.group(3).trim();
		
		double healV	= Double.parseDouble( pseudoHealM.group(4).trim().replace(",", "")  );
		
		BasePower p 	= ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getData().addHeal(healV);
		
		return t;
	}

	private Timestamp parseHealOverTime(Matcher healOverTimeM) throws ParseException {
		Timestamp t 	= this.parseTime( healOverTimeM.group(1).trim() );
		String power 	= healOverTimeM.group(2).trim(); 
		double healV	= Double.parseDouble( healOverTimeM.group(3).trim().replace(",", "")  );
		
		BasePower p 	= ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getData().addHeal(healV);
		
		return t;
	}

	private Timestamp parseActivate(Matcher actM) throws ParseException {
		Timestamp t 	= this.parseTime( actM.group(1).trim() );
		String power 	= actM.group(2).trim(); 
		
		BasePower p = ch.getPower( t, power );
		p.addNewInstance(t);
		
		return t;
	}

	private Timestamp parseMiss(Matcher missM) throws ParseException {
		Timestamp t = this.parseTime( missM.group(1).trim() );
		String enemy 	= missM.group(2).trim(); 
		String power 	= missM.group(3).trim(); 
		
		double chance	= Double.parseDouble( missM.group(4).trim() );
		double roll		= Double.parseDouble( missM.group(5).trim() );
		
		BasePower p = ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( false );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addMiss();

		ch.getData().addMiss();
		p.getData().addMiss();
		
		return t;
	}

	private Timestamp parseHitAuto(Matcher hitAutoM) throws ParseException {
		Timestamp t 	= this.parseTime( hitAutoM.group(1) );
		String enemy 	= hitAutoM.group(2); 
		String power 	= hitAutoM.group(3); 
		
		double chance	= 100;
		double roll		= 0;
		
		BasePower p 	= ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getData().addHit();
		p.getData().addHit();
		
		return t;
	}

	private Timestamp parseDamage( Matcher damM, Matcher damProcM ) throws ParseException {
		logger.debug( "Parsing with " + damM.pattern().toString() );
		
		Timestamp t 	= this.parseTime( damM.group(1).trim() );
		String enemy 	= damM.group(2).trim();
		String power 	= damM.group(3).trim(); 
		
		double damage	= Double.parseDouble( damM.group(4).trim().replace(",", "")  );
		String type		= damM.group(5).trim();
		
		BasePower p 	= ch.getPower( t, power );
		
		PowerInstance i = null; 
		if( damProcM.matches()) {
			i = new PowerInstance();
			i.setDidHit(true);
			i.getData().addHit();
			i.setTimestamp(t);
			
			p.addInstance(i);
			p.getData().addHit();
		}
		
		i = p.getLastInstance(t);
		
		p.getData().addDamage(type, damage);
		i.getData().addDamage(type, damage);
		ch.getData().addDamage( type, damage );
		
		return t;
	}
	
	
	private Timestamp parsePseudoDamage( Matcher pseudoDamM ) throws ParseException {
		Timestamp t 	= this.parseTime( pseudoDamM.group(1) );
		String power 	= pseudoDamM.group(2).trim();
		String enemy	= pseudoDamM.group(3).trim();
		String subPower = pseudoDamM.group(4).trim();	
		
		double damage	= Double.parseDouble( pseudoDamM.group(5).trim().replace(",", "")  );
		String type		= pseudoDamM.group(6).trim();
		
		BasePower p 	= ch.getPower( t, power );
		BasePower sub	= p.getSubPower( t, subPower );
		
		sub.addNewInstance(t);
		PowerInstance i = sub.getLastInstance(t);
		i.setDidHit( true );
		
		sub.getData().addHit();
		
		p.getData().addDamage(type, damage);
		i.getData().addDamage(type, damage);
		sub.getData().addDamage(type, damage);
		
		ch.getData().addDamage( type, damage );

		return t;
	}
	
	private Timestamp parseHit( Matcher hitM ) throws ParseException {
		Timestamp t 	= this.parseTime( hitM.group(1) );
		String enemy 	= hitM.group(2); 
		String power 	= hitM.group(3); 
		
		double chance	= Double.parseDouble( hitM.group(4) );
		double roll		= Double.parseDouble( hitM.group(5) );
		
		BasePower p 	= ch.getPower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getData().addHit();
		p.getData().addHit();

		return t;
	}
		
		
	private Timestamp parseTime(String group) throws ParseException {
		return new Timestamp( df.parse( group ).getTime() );
	}
	
	
	
}
