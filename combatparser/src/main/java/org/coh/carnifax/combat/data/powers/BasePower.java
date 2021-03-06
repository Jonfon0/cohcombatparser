package org.coh.carnifax.combat.data.powers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BasePower  {

	private String name;
	private Timestamp timestamp;
	
	private BasePowerData data;
	
	private Map<String, BasePower> subPowers;
	
	private List<PowerInstance>	instances;
	
	public BasePower() {
		super();
		this.data = new BasePowerData();
		
		this.subPowers	 = new TreeMap<String, BasePower>();
		this.instances	 = new ArrayList<PowerInstance>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BasePowerData getData() {
		return data;
	}

	public void setData(BasePowerData data) {
		this.data = data;
	}

	public Map<String, BasePower> getSubPowers() {
		return subPowers;
	}

	public void setSubPowers(Map<String, BasePower> subPowers) {
		this.subPowers = subPowers;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public BasePower getSubPower( Timestamp t, String name ) {
		if( !this.subPowers.containsKey( name ) ) {
			BasePower p = new BasePower();
			p.setName(name);
			p.setTimestamp( t );
			this.subPowers.put(name, p);
		}
		
		return this.subPowers.get( name );
	}
	
	public List<PowerInstance> getInstances() {
		return instances;
	}
	public void setInstances(List<PowerInstance> instances) {
		this.instances = instances;
	}

	public void addInstance( PowerInstance i ) {
		this.instances.add(i);
	}
	
	public void addNewInstance( Timestamp t ) {
		PowerInstance p = new PowerInstance();
		p.setTimeMillis( t );

		this.instances.add( p );
	}
	public PowerInstance getLastInstance( Timestamp t ) {
		PowerInstance p = null; 
		
		if( this.instances.size()  > 0 ) {
			p = this.instances.get( this.instances.size()-1 );
		}
		
		if( p == null || t.getTime() - p.getTimeMillis().getTime() > 4000 ) {
			p = new PowerInstance();
			p.setTimeMillis( t );
			this.instances.add( p );
		}
		
		return p;
	}

	public void setMisses(int misses) {
		this.data.setMisses(misses);
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getDate() {
		return new Date( timestamp.getTime() ).toString();
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
