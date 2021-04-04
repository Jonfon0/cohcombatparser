package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total", "average", "summary" })
public class PowerSummaryImpl implements PowerSummary {

	private Map<String, SummaryEntry> entries;
	private double total;

	public PowerSummaryImpl() {
		this.entries = new TreeMap<String, SummaryEntry>();
	}
	
	@Override
	public Map<String, SummaryEntry> getSummary() {
		return this.entries;
	}

	@Override
	public void add(String name, double value, double total) {
		if( value == 0 ) {
			return;
		}
		
		SummaryEntry e = new SummaryEntry(name, value, total);
		
		this.total += value;
		
		this.entries.put( name, e );
		
	}

	@Override
	public double getTotal() {
		return total;
	}

	@Override
	public double getAverage() {
		if(this.entries.size() == 0){
			return 0;
		}
		return total / this.entries.size();
	} 
	
	
}
