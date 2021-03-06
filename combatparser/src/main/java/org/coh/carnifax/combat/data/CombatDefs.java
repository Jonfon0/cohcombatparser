package org.coh.carnifax.combat.data;

public class CombatDefs {
	
	public static final String PATTERN_DATE			= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) .+";

	public static final String PATTERN_GLOBAL		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) \\[(.+?)](.+?):(.+)";
	
	public static final String PATTERN_STARTPARSE	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) \\[Local\\] (.+): STARTPARSE (.+)";
	public static final String PATTERN_ENDPARSE		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) \\[Local\\] (.+): ENDPARSE (.+)";
	public static final String PATTERN_WELCOME		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) Welcome to City of Heroes, (.+)!";
		
	public static final String PATTERN_ACTIVATE 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You activated the(.+) power.$";
	public static final String PATTERN_HIT 	 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) HIT (.+)! Your (.+) power had a (.+)% chance to hit, you rolled a (.+).";
	public static final String PATTERN_HIT_AUTO	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) HIT (.+)! Your (.+) power is autohit.";
	public static final String PATTERN_MISS 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) MISSED (.+)!! Your (.+) power had a (.+)% chance to hit, you rolled a (.+).";
	
	public static final String PATTERN_HIT_YOU 		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+) HITS you! (.+) power had a (.+)% chance to hit and rolled a (.+)[.]";
	public static final String PATTERN_MISS_YOU 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+) MISSES! (.+) power had a (.+)% chance to hit, but rolled a (.+)[.]";
	
	
	public static final String PATTERN_KNOCK 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You knock (.+) off their feet with your (.+)!";
	public static final String PATTERN_PSEUDOKNOCK 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  You knock (.+) off their feet with your (.+)!";

	public static final String PATTERN_HOLD 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You (Stun|Hold|Immobilize|Confuse|Taunt|Terrify?) (.+?) with your ([a-zA-Z ]+?)[.]";
	public static final String PATTERN_PSEUDOHOLD 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  You (Stun|Hold|Immobilize|Confuse|Taunt|Terrify?) (.+) with your ([a-zA-Z ]+)[.]";
	
	public static final String PATTERN_PSEUDOHIT 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  HIT (.+)! Your (.+) power had a (.+)% chance to hit, you rolled a (.+).";
	public static final String PATTERN_PSEUDOMISS 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  MISSED (.+)!! Your (.+) power had a (.+)% chance to hit, you rolled a (.+).";
	
	
	public static final String PATTERN_DAMAGE 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You hit (.+) with your (.+) for ([0-9.]+) points of (.+) damage.*[.]";
	public static final String PATTERN_DAMAGE_OVER_TIME
													= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You hit (.+) with your (.+) for (.+) points of (.+) damage over time.";
	public static final String PATTERN_DAM_PROC		= "(.+: Chance for .+)|(.+/Chance for .+)";
	public static final String PATTERN_DAM_CRIT		= ".+\\[CRITICAL\\].+";
	
	public static final String PATTERN_PSEUDODAM	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  You hit (.+) with your (.+) for (.+) points of (.+) damage.$";

	public static final String PATTERN_DAMAGE_YOU 	 	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+) hits you with their (.+) for ([0-9.]+) points of (.+) damage.*[.]";
	public static final String PATTERN_DAMAGE_OVER_TIME_YOU
													= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+) hits you with their (.+) for ([0-9.]+) points of (.+) damage over time[.]";

	
	public static final String PATTERN_PSEUDODAMAGE_OVER_TIME	
													= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+?):  You hit (.+) with your (.+) for (.+) points of (.+) damage over time.";
	
	public static final String PATTERN_HEAL			= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You heal (.+) with (.+) for (.+) health points.";
	public static final String PATTERN_HEAL_OVER_TIME 
													= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You are healed by your (.+) for (.+) health points over time.";
	public static final String PATTERN_PSEUDOHEAL	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+):  You heal (.+) with Lifegiving Spores for (.+) health points.";
	
	public static final String PATTERN_DEFEATS_SELF = "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You have defeated (.+)";
	public static final String PATTERN_DEFEATS_OTHER= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) (.+) has defeated (.+)";
	
	public static final String PATTERN_XP_INF		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You gain ([0-9,]+) experience and ([0-9,]+) inf.+";
	public static final String PATTERN_XP_INCARN	= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You have defeated (.+)";
	
	public static final String PATTERN_DROPS		= "^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+) You received (.+)[.]";
	public static final String PATTERN_DROP_COMMON	= "^Invention:.+";
	public static final String PATTERN_DROP_SET		= ".+\\(Recipe\\).*";
	
	public static final String COMBAT 			= "combat_";
	public static final String GLOBAL_PREFIX 	= "global_";
	public static final String SUMMARY 			= "summary_";
	
	public static final int    INTERVAL			= 1000; 
	
	
}
