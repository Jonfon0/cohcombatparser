package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;
import java.util.TreeMap;

public class DamagePowerSummary extends PowerSummaryImpl {
	private Map<String, SummaryEntry> summary;

	
	public long trim( long start, long end ){
		Map<String, SummaryEntry> e = super.getSummary();
		Map<String, SummaryEntry> n = new TreeMap<String, SummaryEntry>();
		
		long newEnd = 0;
		
		for( String k : e.keySet() ) {
			long t = Long.parseLong(k);
			if( t >= start && t <= end && e.get(k).getPercent() < 99.9 ) {
				if( t > newEnd ){
					newEnd = t;
				}
				n.put(k, e.get(k) );
			}
		}
		super.setSummary(n);
		
		return newEnd;
	}
}
