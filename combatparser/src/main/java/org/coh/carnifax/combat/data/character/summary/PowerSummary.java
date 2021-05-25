package org.coh.carnifax.combat.data.character.summary;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonPropertyOrder({ "total", "average", "summary" })
@JsonDeserialize(as = PowerSummaryImpl.class)
public interface PowerSummary {

	public Map<String, SummaryEntry> getSummary();
	public void add( String name, double value, double total );
	public void add( String name, double value, double total, String notes );
	public double getTotal();
	public double getAverage();
	void setSummary(Map<String, SummaryEntry> summary);

}
