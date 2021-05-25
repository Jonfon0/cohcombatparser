package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total", "average", "summary" })
@JsonIgnoreProperties( allowGetters = true)
public class PowerSummaryImpl implements PowerSummary {

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
	public void setSummary(Map<String, SummaryEntry> summary) {
		this.summary = summary;
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
	public void add(String name, double value, double total, String note) {
		if( value == 0 ) {
			return;
		}
		
		SummaryEntry e = new SummaryEntry(name, value, total);
		e.setNote(note);
		
		this.total += value;
		
		this.summary.put( name, e );
		
	}


	@Override
	public double getTotal() {
		return total;
	}

	@Override
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public double getAverage() {
		if(this.summary.size() == 0){
			return 0;
		}
		return total / this.summary.size();
	}

	
	
}
