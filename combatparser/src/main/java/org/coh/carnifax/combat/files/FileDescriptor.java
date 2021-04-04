package org.coh.carnifax.combat.files;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileDescriptor {

	private Map<String, File> combat;
	private Map<String, File> globals;
	private Timestamp start;
	private Timestamp end;
	
	public FileDescriptor() {
		super();
		this.globals = new HashMap<String, File>();
		this.combat = new HashMap<String, File>();
	}
	public Timestamp getStart() {
		return start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public Map<String, File> getCombat() {
		return combat;
	}
	public void setCombat(Map<String, File> combat) {
		this.combat = combat;
	}
	public Map<String, File> getGlobals() {
		return globals;
	}
	public void setGlobals( Map<String, File> globals ) {
		this.globals = globals;
	}
	public void addGlobal( String name, File file ) {
		this.globals.put(name, file);
	}
	public void addCombat( String name, File file ) {
		this.combat.put(name, file);
	}
	public Iterator<String> getGlobalNames( ) {
		return this.globals.keySet().iterator();
	}
	public boolean hasGlobal( String name) {
		return this.globals.containsKey( name );
	}
	public boolean hasCombat( ) {
		return this.combat != null;
	}
	
	public void destroyGlobal() {
		for( File f : this.globals.values() ) {
			f.delete();
		}
	}
	public void destroyCombat() {
		for( File f : this.combat.values() ) {
			f.delete();
		}
	}
	public void destroy() {
		destroyCombat();
		destroyGlobal();
	}

}
