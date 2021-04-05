package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;
import java.util.TreeMap;

import org.coh.carnifax.combat.data.IsHidable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total", "average", "summary" })
public class PowerSummaryImpl extends IsHidable implements PowerSummary {

	private Map<String, SummaryEntry> summary;
	private double total;
	
	public PowerSummaryImpl() {
		this.summary = new TreeMap<String, SummaryEntry>();
	}
	
	@Override
	public Map<String, SummaryEntry> getSummary() {
		return this.summary;
	}

	@Override
	public void add(String name, double value, double total) {
		if( value == 0 ) {
			return;
		}
		
		SummaryEntry e = new SummaryEntry(name, value, total);
		
		this.total += value;
		
		this.summary.put( name, e );
		
	}

	@Override
	public double getTotal() {
		return total;
	}

	@Override
	public double getAverage() {
		if(this.summary.size() == 0){
			return 0;
		}
		return total / this.summary.size();
	}

	
}
