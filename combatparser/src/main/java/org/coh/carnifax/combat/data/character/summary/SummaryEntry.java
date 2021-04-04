package org.coh.carnifax.combat.data.character.summary;

public class SummaryEntry {

	public String name;
	public double value;
	public double total;
	public double percent;
	
	public SummaryEntry(String name, double value, double total) {
		super();
		this.name = name;
		this.value = value;
		this.total = total;
		this.percent = value * 100 / total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
