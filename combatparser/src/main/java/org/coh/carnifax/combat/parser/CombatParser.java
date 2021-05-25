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
import org.coh.carnifax.combat.data.character.BaseCharInterface;
import org.coh.carnifax.combat.data.powers.BasePower;
import org.coh.carnifax.combat.data.powers.PowerInstance;
import org.coh.carnifax.combat.main.FilesMain;



public class CombatParser {

	private final static Logger logger = LogManager.getLogger( CombatParser.class );

	private Pattern activate;
	private Pattern hit;
	private Pattern hitAuto;
	private Pattern pseudoHit;
	private Pattern miss;
	private Pattern pseudoMiss;
	private Pattern damage;
	private Pattern damageOverTime;
	private Pattern pseudoDamageOverTime;
	private Pattern pseudoDamage;
	private Pattern damageProc;
	private Pattern critical;
	private Pattern healOverTime;
	private Pattern heal;
	private Pattern pseudoHeal;
	private Pattern defeatsSelf;
	private Pattern defeatsOther;
	private Pattern date;
	private Pattern xpInf;
	private Pattern knockback;
	private Pattern pseudoKnockback;
	private Pattern hold;
	private Pattern pseudoHold;
	private Pattern drop;
	
	private Pattern defHit;
	private Pattern defMiss;
	private Pattern defDamage;
	private Pattern defDamageOverTime;
	
	
	private String markers[];
	
	
	private SimpleDateFormat df;
	
	private BaseCharInterface ch;

		
	public CombatParser() {
		date = Pattern.compile(CombatDefs.PATTERN_DATE);
		activate = Pattern.compile( CombatDefs.PATTERN_ACTIVATE );
		hit = Pattern.compile( CombatDefs.PATTERN_HIT );
		pseudoHit = Pattern.compile( CombatDefs.PATTERN_PSEUDOHIT );
		hitAuto = Pattern.compile( CombatDefs.PATTERN_HIT_AUTO );
		miss= Pattern.compile( CombatDefs.PATTERN_MISS );
		pseudoMiss= Pattern.compile( CombatDefs.PATTERN_PSEUDOMISS );
		damage = Pattern.compile( CombatDefs.PATTERN_DAMAGE );
		damageOverTime = Pattern.compile( CombatDefs.PATTERN_DAMAGE_OVER_TIME );
		damageProc = Pattern.compile( CombatDefs.PATTERN_DAM_PROC );
		critical = Pattern.compile( CombatDefs.PATTERN_DAM_CRIT );
		pseudoDamage = Pattern.compile( CombatDefs.PATTERN_PSEUDODAM );
		pseudoDamageOverTime = Pattern.compile( CombatDefs.PATTERN_PSEUDODAMAGE_OVER_TIME );
		healOverTime = Pattern.compile( CombatDefs.PATTERN_HEAL_OVER_TIME );
		heal = Pattern.compile(CombatDefs.PATTERN_HEAL);
		pseudoHeal = Pattern.compile(CombatDefs.PATTERN_PSEUDOHEAL);
		defeatsSelf = Pattern.compile(CombatDefs.PATTERN_DEFEATS_SELF);
		defeatsOther = Pattern.compile(CombatDefs.PATTERN_DEFEATS_OTHER);
		xpInf = Pattern.compile( CombatDefs.PATTERN_XP_INF );
		knockback = Pattern.compile( CombatDefs.PATTERN_KNOCK );
		pseudoKnockback = Pattern.compile( CombatDefs.PATTERN_PSEUDOKNOCK );
		hold = Pattern.compile( CombatDefs.PATTERN_HOLD );
		pseudoHold = Pattern.compile( CombatDefs.PATTERN_PSEUDOHOLD );
		drop = Pattern.compile( CombatDefs.PATTERN_DROPS );
	
		defHit = Pattern.compile( CombatDefs.PATTERN_HIT_YOU );
		defMiss= Pattern.compile( CombatDefs.PATTERN_MISS_YOU );
		defDamage = Pattern.compile( CombatDefs.PATTERN_DAMAGE_YOU );
		defDamageOverTime = Pattern.compile( CombatDefs.PATTERN_DAMAGE_OVER_TIME_YOU );
	
		
		df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		markers = new String[0];
		
	}
	
	public BaseCharInterface parse( String uuid, File parentDir, File combat, String markers[] ) throws CombatParseException {
		
		if( !combat.exists() ) {
			throw new CombatParseException("Could not open combat file " + combat.getAbsolutePath() );
		}
		this.markers = markers;
		if( this.markers == null ) {
			markers = new String[0];
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
	
	private BaseCharInterface createBaseChar(String uuid, String name) {
		
		String parts[] = name.replace(".txt", "").split("_");
		
		BaseCharInterface ch = new BaseChar(uuid);
		ch.setStart( Long.parseLong(parts[1]) );

		ch.setName(parts[ parts.length-1 ]);
		
		return ch;
	}

	public void parseLine( String line, int count ) throws ParseException {
		try {
			Matcher actM 		= this.activate.matcher( line );
			Matcher hitM 		= this.hit.matcher( line );
			Matcher hitAutoM	= this.hitAuto.matcher( line );
			Matcher pseudoHitM 	= this.pseudoHit.matcher( line );
			Matcher missM 		= this.miss.matcher( line );
			Matcher pseudoMissM	= this.pseudoMiss.matcher( line );
			Matcher damM 		= this.damage.matcher( line );
			Matcher damOverTimeM= this.damageOverTime.matcher( line );
			Matcher damProcM	= this.damageProc.matcher( line );
			Matcher critM		= this.critical.matcher( line );
			Matcher pseudoDamM 	= this.pseudoDamage.matcher( line );
			Matcher pseudoDamageOverTimeM
								= this.pseudoDamageOverTime.matcher( line );
			Matcher healOverTimeM
								= this.healOverTime.matcher( line );
			Matcher healM		= this.heal.matcher( line );
			Matcher pseudoHealM = this.pseudoHeal.matcher( line );
			Matcher defeatSelfM	= this.defeatsSelf.matcher( line );
			Matcher defeatOtherM= this.defeatsOther.matcher( line );
			Matcher xpInfM		= this.xpInf.matcher( line ); 
			Matcher knockM		= this.knockback.matcher( line );
			Matcher pseudoknockM		
								= this.pseudoKnockback.matcher( line );
			Matcher holdM		= this.hold.matcher( line );
			Matcher pseudoHoldM	= this.pseudoHold.matcher( line );
			
			Matcher dropM		= this.drop.matcher( line );
			
			Matcher defHitM 	= this.defHit.matcher( line );
			Matcher defMissM	= this.defMiss.matcher( line );
			
			Matcher defDamageM 	= this.defDamage.matcher( line );
			Matcher defDamageOverTimeM	= this.defDamageOverTime.matcher( line );
			
			
			Timestamp t 	= null;
			
			if( damOverTimeM.matches() ) {
				t = this.parseDamageOverTime(damOverTimeM, damProcM);
			}
			if( damM.matches() ) {
				t = this.parseDamage(damM, damProcM, critM );
			}
			else if( defDamageOverTimeM.matches() ) {
				t = this.parseDefDamageOverTime( defDamageOverTimeM );
			}
			else if( defDamageM.matches() ) {
				t = this.parseDefDamage(defDamageM, critM );
			}
			else if( pseudoDamM.matches() ) {
				t = this.parsePseudoDamage( pseudoDamM );
			}
			else if( pseudoDamageOverTimeM.matches() ) {
				t = this.parsePseudoDamageOverTime( pseudoDamageOverTimeM );
			}
			else if( hitM.matches() ) {
				t = this.parseHit( hitM );
			}
			else if( defHitM.matches() ) {
				t = this.parseDefHit( defHitM );
			}
			else if( pseudoHitM.matches() ) {
				t = this.parsePseudoHit( pseudoHitM );
			}

			else if( hitAutoM.matches() ) {
				t = this.parseHitAuto( hitAutoM );
			}
			else if( missM.matches() ) {
				t = this.parseMiss( missM );
			}
			else if( defMissM.matches() ) {
				t = this.parseDefMiss( defMissM );
			}
			else if( pseudoMissM.matches() ) {
				t = this.parsePseudoMiss( pseudoMissM );
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
			else if( knockM.matches() ) {
				t = this.parseKnockback( knockM );
			}
			else if( pseudoknockM.matches() ) {
				t = this.parsePseudoKnockback( pseudoknockM );
			}
			else if( holdM.matches() ) {
				t = this.parseHold( holdM );
			}
			else if( pseudoHoldM.matches() ) {
				t = this.parsePseudoHold( pseudoHoldM );
			}
			else if( dropM.matches() ) {
				t = this.parseDrop( dropM );
			}
			
			if( t!=null ) {
				ch.setEnd( t );
				
				for(String m : this.markers) {
					if( line.toLowerCase().contains(m.toLowerCase()) ) {
						ch.addMarker( m, t );
					}
				}
			}
			
		} catch (NumberFormatException | IllegalStateException e) {
			System.out.println("Error on " + count + " : " + line);
			throw e;
		}
		
		
	}

	// These really are Events, be nice to have an Event Handler for this stuff. Oh well. 

	private Timestamp parseHold( Matcher holdM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( holdM.group(1).trim() );
		String type 	= holdM.group(2).trim();
		String enemy 	= holdM.group(3).trim();
		String power 	= holdM.group(4).trim(); 
		
		BasePower p 	= ch.getOffensivePower( t, power );
		
		PowerInstance i = null; 
		i = p.getLastInstance( t );
		
		p.getData().addMez( type );
		i.getData().addMez( type );
		ch.getOffensive().getData().addMez( type );
		
		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMez(type);
		
		return t;
	}
	
	private Timestamp parsePseudoHold( Matcher holdM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( holdM.group(1).trim() );
		String power 	= holdM.group(2).trim(); 
		String type 	= holdM.group(3).trim();
		String enemy 	= holdM.group(4).trim();
		String subPower = holdM.group(5).trim(); 
		
		BasePower p 	= ch.getOffensivePower( t, power );
		
		PowerInstance i = null; 
		i = p.getLastInstance( t );
		BasePower sub	= p.getSubPower( t, subPower );
		
		p.getData().addMez( type );
		i.getData().addMez( type );
		ch.getOffensive().getData().addMez( type );
		sub.getData().addMez( type );

		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMez(type);
		
		return t;
	}

	
	private Timestamp parseKnockback( Matcher knockM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( knockM.group(1).trim() );
		String enemy 	= knockM.group(2).trim();
		String power 	= knockM.group(3).trim(); 
		
		BasePower p 	= ch.getOffensivePower( t, power );
		
		PowerInstance i = null; 
		i = p.getLastInstance( t );
		
		p.getData().addMez("Knock");
		i.getData().addMez("Knock");
		ch.getOffensive().getData().addMez("Knock");
		
		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMez("Knock");
		
		return t;
	}
	
	private Timestamp parsePseudoKnockback( Matcher knockM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( knockM.group(1).trim() );
		String power 	= knockM.group(2).trim(); 
		String enemy 	= knockM.group(3).trim();
		String subPower = knockM.group(4).trim(); 
		
		BasePower p 	= ch.getOffensivePower( t, power );
		
		PowerInstance i = null; 
		i = p.getLastInstance( t );
		BasePower sub	= p.getSubPower( t, subPower );
		
		p.getData().addMez("Knock");
		i.getData().addMez("Knock");
		ch.getOffensive().getData().addMez("Knock");
		sub.getData().addMez("Knock");

		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMez("Knock");
		
		return t;
	}
	
	private Timestamp parseXpInf(Matcher xpInfM) throws ParseException {
		Timestamp t 	= this.parseTime( xpInfM.group(1).trim() );
		long xp			= Long.parseLong( xpInfM.group(2).trim().replace(",", "") );
		long inf		= Long.parseLong( xpInfM.group(3).trim().replace(",", "") );
		
		ch.addXp( t, xp );
		ch.addInf( t, inf );
		
		ch.getDrops().addXp(xp);
		ch.getDrops().addInf(inf);
		
		return t;
	}

	private Timestamp parseDrop(Matcher dropM) throws ParseException {
		Timestamp t 	= this.parseTime( dropM.group(1).trim() );
		String name		= dropM.group(2).trim();
		
		ch.addDrop( name );
		
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
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getOffensive().getData().addHeal(healV);

		BasePower e		= ch.getOffensiveTarget(t, target );
		e.getData().addHeal( healV );
		
		return t;
	}
	

	private Timestamp parsePseudoHeal(Matcher pseudoHealM) throws ParseException {
		Timestamp t 	= this.parseTime( pseudoHealM.group(1).trim() );
		String power 	= pseudoHealM.group(2).trim(); 
		String target	= pseudoHealM.group(3).trim();
		
		double healV	= Double.parseDouble( pseudoHealM.group(4).trim().replace(",", "")  );
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getOffensive().getData().addHeal(healV);

		BasePower e		= ch.getOffensiveTarget(t, target );
		e.getData().addHeal( healV );
		
		return t;
	}

	private Timestamp parseHealOverTime(Matcher healOverTimeM) throws ParseException {
		Timestamp t 	= this.parseTime( healOverTimeM.group(1).trim() );
		String power 	= healOverTimeM.group(2).trim(); 
		double healV	= Double.parseDouble( healOverTimeM.group(3).trim().replace(",", "")  );
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		p.getData().addHeal(healV);
		i.getData().addHeal(healV);
		ch.getOffensive().getData().addHeal(healV);

		return t;
	}

	private Timestamp parseActivate(Matcher actM) throws ParseException {
		Timestamp t 	= this.parseTime( actM.group(1).trim() );
		String power 	= actM.group(2).trim(); 
		
		BasePower p = ch.getOffensivePower( t, power );
		p.addNewInstance(t);
		
		
		
		return t;
	}

	private Timestamp parseMiss(Matcher missM) throws ParseException {
		Timestamp t = this.parseTime( missM.group(1).trim() );
		String enemy 	= missM.group(2).trim(); 
		String power 	= missM.group(3).trim(); 
		
		double chance	= Double.parseDouble( missM.group(4).trim() );
		double roll		= Double.parseDouble( missM.group(5).trim() );
		
		BasePower p = ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( false );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addMiss();

		ch.getOffensive().getData().addMiss();
		p.getData().addMiss();

		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMiss();
		
		return t;
	}

	private Timestamp parsePseudoMiss(Matcher missM) throws ParseException {
		Timestamp t = this.parseTime( missM.group(1).trim() );

		String power 		= missM.group(2).trim(); 
		String enemy 		= missM.group(3).trim(); 
		String subPower 	= missM.group(4).trim(); 
		
		double chance	= Double.parseDouble( missM.group(5).trim() );
		double roll		= Double.parseDouble( missM.group(6).trim() );
		
		BasePower p = ch.getOffensivePower( t, power );
		BasePower sub	= p.getSubPower( t, subPower );
		
		sub.addNewInstance(t);
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( false );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addMiss();

		ch.getOffensive().getData().addMiss();
		p.getData().addMiss();
		sub.getData().addMiss();
		
		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addMiss( );
		
		return t;
	}
	private Timestamp parsePseudoHit( Matcher hitM ) throws ParseException {
		Timestamp t 	= this.parseTime( hitM.group(1) );
		String power 	= hitM.group(2); 
		String enemy 	= hitM.group(3); 
		String subPower = hitM.group(4);
		
		double chance	= Double.parseDouble( hitM.group(5) );
		double roll		= Double.parseDouble( hitM.group(6) );
		
		BasePower p 	= ch.getOffensivePower( t, power );
		BasePower sub	= p.getSubPower( t, subPower );
		
		sub.addNewInstance(t);
		PowerInstance i = sub.getLastInstance(t);
		
		sub.getData().addHit();
		
		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getOffensive().getData().addHit();
		p.getData().addHit();
		sub.getData().addHit();

		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addHit( );
		
		return t;
	}

	private Timestamp parseHitAuto(Matcher hitAutoM) throws ParseException {
		Timestamp t 	= this.parseTime( hitAutoM.group(1) );
		String enemy 	= hitAutoM.group(2); 
		String power 	= hitAutoM.group(3); 
		
		double chance	= 100;
		double roll		= 0;
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getOffensive().getData().addHit();
		p.getData().addHit();
		
		BasePower e		= ch.getOffensiveTarget(t, enemy );
		e.getData().addHit( );

		return t;
	}

	private Timestamp parseDamage( Matcher damM, Matcher damProcM, Matcher critical ) throws ParseException {
		
		Timestamp t 	= this.parseTime( damM.group(1).trim() );
		String enemy 	= damM.group(2).trim();
		String power 	= damM.group(3).trim(); 
		
		double damage	= Double.parseDouble( damM.group(4).trim().replace(",", "")  );
		String type		= damM.group(5).trim();
		
		BasePower p 	= ch.getOffensivePower( t, power );

		BasePower e		= ch.getOffensiveTarget(t, enemy );
		
		logger.debug("Dam " + power + " : " + damage );
		
		PowerInstance i = null; 
		if( damProcM.matches()) {
			i = new PowerInstance();
			i.setDidHit(true);
			i.getData().addHit();
			i.setTimeMillis(t);
			
			p.addInstance(i);
			p.getData().addHit();
		}
		
		i = p.getLastInstance(t);
		
		p.getData().addDamage(type, damage);
		i.getData().addDamage(type, damage);
		e.getData().addDamage(type, damage);
		
		ch.getOffensive().getData().addDamage( type, damage );
		
		if( critical.matches() ) {
			p.getData().addCrit();
			i.getData().addCrit();
			e.getData().addCrit();

			ch.getOffensive().getData().addCrit();
		}
		
		return t;
	}
	
	private Timestamp parseDamageOverTime( Matcher damOverTimeM, Matcher damProcM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( damOverTimeM.group(1).trim() );
		String enemy 	= damOverTimeM.group(2).trim();
		String power 	= damOverTimeM.group(3).trim(); 
		
		double damage	= Double.parseDouble( damOverTimeM.group(4).trim().replace(",", "")  );
		String type		= damOverTimeM.group(5).trim();
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = null; 
		BasePower e		= ch.getOffensiveTarget(t, enemy );
		
		if( damProcM.matches()) {
			i = new PowerInstance();
			i.setDidHit(true);
			i.getData().addHit();
			i.setTimeMillis(t);
			
			p.addInstance(i);
			p.getData().addHit();
			
			e.getData().addHit();
		}
		
		i = p.getLastInstance(t);
		
		p.getData().addDamageOverTime(type, damage);
		i.getData().addDamageOverTime(type, damage);
		e.getData().addDamageOverTime(type, damage);
		
		ch.getOffensive().getData().addDamageOverTime( type, damage );
		
		return t;
	}

	private Timestamp parseDefDamage( Matcher damM, Matcher critical ) throws ParseException {
		
		Timestamp t 	= this.parseTime( damM.group(1).trim() );
		String enemy 	= damM.group(2).trim();
		String power 	= damM.group(3).trim(); 
		
		double damage	= Double.parseDouble( damM.group(4).trim().replace(",", "")  );
		String type		= damM.group(5).trim();
		
		BasePower p 	= ch.getDefensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		BasePower e 	= ch.getDefensiveTarget( t, enemy );
		
		if( i == null ) {
			i = new PowerInstance();
			i.setDidHit(true);
			i.getData().addHit();
			i.setTimeMillis(t);
			
			p.addInstance(i);
			p.getData().addHit();
			i = p.getLastInstance(t);
			
			e.getData().addHit();
		}
		
		p.getData().addDamage(type, damage);
		i.getData().addDamage(type, damage);
		e.getData().addDamage(type, damage);
		
		ch.getDefensive().getData().addDamage( type, damage );
		
		if( critical.matches() ) {
			p.getData().addCrit();
			i.getData().addCrit();
			e.getData().addCrit();
			ch.getDefensive().getData().addCrit();
		}
		
		return t;
	}
	
	private Timestamp parseDefDamageOverTime( Matcher damOverTimeM ) throws ParseException {
		
		Timestamp t 	= this.parseTime( damOverTimeM.group(1).trim() );
		String enemy 	= damOverTimeM.group(2).trim();
		String power 	= damOverTimeM.group(3).trim(); 
		
		double damage	= Double.parseDouble( damOverTimeM.group(4).trim().replace(",", "")  );
		String type		= damOverTimeM.group(5).trim();
		
		BasePower p 	= ch.getDefensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		BasePower e 	= ch.getDefensiveTarget( t, enemy );
		
		if( i == null ) {
			i = new PowerInstance();
			i.setDidHit(true);
			i.getData().addHit();
			i.setTimeMillis(t);
			
			p.addInstance(i);
			p.getData().addHit();
			i = p.getLastInstance(t);
			
			e.getData().addHit();
		}
		
		p.getData().addDamageOverTime(type, damage);
		i.getData().addDamageOverTime(type, damage);
		e.getData().addDamageOverTime(type, damage);
		
		ch.getDefensive().getData().addDamageOverTime( type, damage );
		
		return t;
	}

	
	private Timestamp parsePseudoDamage( Matcher pseudoDamM ) throws ParseException {
		Timestamp t 	= this.parseTime( pseudoDamM.group(1) );
		String power 	= pseudoDamM.group(2).trim();
		String enemy	= pseudoDamM.group(3).trim();
		String subPower = pseudoDamM.group(4).trim();	
		
		double damage	= Double.parseDouble( pseudoDamM.group(5).trim().replace(",", "")  );
		String type		= pseudoDamM.group(6).trim();
		
		BasePower p 	= ch.getOffensivePower( t, power );
		BasePower sub	= p.getSubPower( t, subPower );
		BasePower e 	= ch.getOffensiveTarget( t, enemy );
		
		sub.addNewInstance(t);
		PowerInstance i = sub.getLastInstance(t);
		i.setDidHit( true );
		
		sub.getData().addHit();
		
		p.getData().addDamage(type, damage);
		i.getData().addDamage(type, damage);
		e.getData().addDamage(type, damage);
		sub.getData().addDamage(type, damage);
		
		ch.getOffensive().getData().addDamage( type, damage );

		return t;
	}
	
	private Timestamp parsePseudoDamageOverTime( Matcher pseudoDamM ) throws ParseException {
		Timestamp t 	= this.parseTime( pseudoDamM.group(1) );
		String power 	= pseudoDamM.group(2).trim();
		String enemy	= pseudoDamM.group(3).trim();
		String subPower = pseudoDamM.group(4).trim();	
		
		double damage	= Double.parseDouble( pseudoDamM.group(5).trim().replace(",", "")  );
		String type		= pseudoDamM.group(6).trim();
		
		BasePower p 	= ch.getOffensivePower( t, power );
		BasePower sub	= p.getSubPower( t, subPower );
		
		//sub.addNewInstance(t);
		PowerInstance i = sub.getLastInstance(t);
		BasePower e 	= ch.getOffensiveTarget( t, enemy );
		
		sub.getData().addHit();
		
		p.getData().addDamageOverTime(type, damage);
		i.getData().addDamageOverTime(type, damage);
		e.getData().addDamageOverTime(type, damage);
		
		sub.getData().addDamageOverTime(type, damage);
		
		ch.getOffensive().getData().addDamageOverTime( type, damage );

		return t;
	}
	
	private Timestamp parseHit( Matcher hitM ) throws ParseException {
		Timestamp t 	= this.parseTime( hitM.group(1) );
		String enemy 	= hitM.group(2); 
		String power 	= hitM.group(3); 
		
		double chance	= Double.parseDouble( hitM.group(4) );
		double roll		= Double.parseDouble( hitM.group(5) );
		
		BasePower p 	= ch.getOffensivePower( t, power );
		PowerInstance i = p.getLastInstance(t);
		BasePower e 	= ch.getOffensiveTarget( t, enemy );

		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getOffensive().getData().addHit();
		p.getData().addHit();
		e.getData().addHit();

		return t;
	}

	private Timestamp parseDefHit( Matcher hitM ) throws ParseException {
		Timestamp t 	= this.parseTime( hitM.group(1) );
		String enemy 	= hitM.group(2); 
		String power 	= hitM.group(3); 
		
		double chance	= Double.parseDouble( hitM.group(4) );
		double roll		= Double.parseDouble( hitM.group(5) );
		
		BasePower p 	= ch.getDefensivePower( t, power );
		BasePower e 	= ch.getDefensiveTarget( t, enemy );

		p.addNewInstance(t);
		PowerInstance i = p.getLastInstance(t);
		
		i.setDidHit( true );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addHit();
		
		ch.getDefensive().getData().addHit();
		p.getData().addHit();
		e.getData().addHit();
		
		return t;
	}

	private Timestamp parseDefMiss( Matcher missM ) throws ParseException {
		Timestamp t 	= this.parseTime( missM.group(1) );
		String enemy 	= missM.group(2); 
		String power 	= missM.group(3); 
		
		double chance	= Double.parseDouble( missM.group(4) );
		double roll		= Double.parseDouble( missM.group(5) );
		
		BasePower p 	= ch.getDefensivePower( t, power );
		p.addNewInstance(t);
		PowerInstance i = p.getLastInstance(t);
		BasePower e 	= ch.getDefensiveTarget( t, enemy );

		
		i.setDidHit( false );
		i.setToHit( chance );
		i.setToHitRoll( roll );
		i.getData().addMiss();
		
		ch.getDefensive().getData().addMiss();
		p.getData().addMiss();
		e.getData().addMiss();

		return t;
	}

		
		
	private Timestamp parseTime(String group) throws ParseException {
		return new Timestamp( df.parse( group ).getTime() );
	}
	
	
	
}
