package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total", "average", "summary" })
public interface PowerSummary {

	public Map<String, SummaryEntry> getSummary();
	public void add( String name, double value, double total );
	public double getTotal();
	public double getAverage();
	
	public void setHidable(boolean h);
	public boolean isHidable();
}
