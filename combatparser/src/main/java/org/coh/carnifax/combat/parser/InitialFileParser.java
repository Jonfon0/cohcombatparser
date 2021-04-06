package org.coh.carnifax.combat.parser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coh.carnifax.combat.data.CombatDefs;
import org.coh.carnifax.combat.data.character.CombatSessions;
import org.coh.carnifax.combat.files.FileDescriptor;
import org.coh.carnifax.combat.files.FileParseException;

/**
 * Initial File parser run. Separate Global channels from others.
 * Also separate the Combat log into individual sessions, based on someone logging in or a 
 * /local STARTPARSE message being seen in the logs. 
 * @author Carnifax
 *
 */
public class InitialFileParser implements Closeable{
	
	private final static Logger logger = LogManager.getLogger( InitialFileParser.class );

	private Pattern timePattern;
	private Pattern globalPattern;
	private Pattern welcomePattern;
	private Pattern startPattern;
	private Pattern endPattern;
	private List<CombatSessions> sessions;
	
	private CombatSessions current;
	
	private FileDescriptor descriptor;
	private Map<String, PrintStream> globals;
	private Map<String, PrintStream> combat; 
	private PrintStream curCombat;
	
	private File inputFolder;
	private String uuid;
	
	private SimpleDateFormat df;
	
	private boolean isRecording = false;
	
	public InitialFileParser() {
		timePattern = Pattern.compile( CombatDefs.PATTERN_DATE );
		globalPattern = Pattern.compile(CombatDefs.PATTERN_GLOBAL);
		globals = new HashMap<String, PrintStream>();
		combat = new HashMap<String, PrintStream>();
		
		sessions = new ArrayList<CombatSessions>();
		
		welcomePattern = Pattern.compile(CombatDefs.PATTERN_WELCOME);
		startPattern = Pattern.compile(CombatDefs.PATTERN_STARTPARSE);
		endPattern = Pattern.compile(CombatDefs.PATTERN_ENDPARSE);
		
		df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	}
	
	public FileDescriptor parse( File input, String uuid ) throws FileParseException {
		
		if( input.isDirectory() || !input.isFile() || !input.exists() ) {
			throw new FileParseException( "Illegal file " + input.getAbsolutePath() );
		}
		
		descriptor = new FileDescriptor();
		inputFolder = new File(input.getParentFile(), uuid);
		if(! inputFolder.mkdir() ){
			throw new FileParseException("Could not create parent folder " + inputFolder.getAbsolutePath() );
		}
		
		this.uuid = uuid;
		
		logger.info("Starting first parse");
		try(BufferedReader r = new BufferedReader( new FileReader(input) )) {
			String s = null;
			String lastLine = null;
			
			do {
				s = r.readLine();
				if(s != null  ) {
					this.parseLine( s );
					lastLine = s;
				}
				
			} while( s != null );
			
			finishSessions( lastLine );
			logger.info("First parse complete");
			logger.info("Sessions : " + descriptor.getCombat().size() + ". Globals : " + descriptor.getGlobals().size() );
			
		} catch ( IOException | ParseException e ) {
			throw new FileParseException( e );
		}
		finally {
			try {
				// Close the Writers
				this.close();
			} catch (IOException e) {
				throw new FileParseException("Error closing streams");
			}
		}
		
		return descriptor;
	}
	
	private void parseLine( String s ) throws IOException, ParseException {
		// For handling midnight switch overs
		if( current == null && this.combat.isEmpty() ) {
			Matcher m = timePattern.matcher( s );
			if( m.matches() ) {
				startSession( "Initial", parseTime( m.group( 1 ) ) );
			}
		}
		
		// Check to see if this line triggers a new Session.
		// Eeeeew. Nests. This needs refactoring
		Matcher m = welcomePattern.matcher( s );
		if( m.matches() ) {
			startSession( m.group(2), parseTime( m.group( 1 ) ) );
		}
		else {
			m = startPattern.matcher( s );
			
			if( m.matches() ) {
				startSession( m.group(2), parseTime( m.group( 1 ) ) );
			}
			else  {
				m = endPattern.matcher( s );
				if( m.matches() ) {
					endSession( parseTime( m.group( 1 ) ) );
				}
			}
			
		}

		// Global
		m = globalPattern.matcher(s);
		if( m.matches() ) {
			String name = m.group(2);
			
			// Create new File
			if( !descriptor.hasGlobal( name ) ) {
				this.createGlobal(name);
			}
			
			// Write the String to the new file
			this.globals.get(name).println(s);
		}
		
		else if( this.isRecording ){
			this.curCombat.println( s );
		}
		
	}
	
	private void startSession( String name, long start ) throws IOException {
		if( this.current != null ) {
			endSession( start-1 );
		}
		
		this.current = new CombatSessions();
		this.current.setName(name);
		this.current.setStart(start);
		
		this.createCombat( name,  start );
		
		logger.debug("Started a new session for " + name + " at " + new Date(start) );
		this.isRecording = true;

	}
	
	private void endSession( long end ) {
		if( this.current == null ) {
			return;
		}
		
		this.current.setEnd(end);
		this.sessions.add( current );
		
		logger.debug("Finished a session for " + this.current.getName() + " at " + new Date(end) );

		this.current = null;
		this.isRecording = false;
		
	}
	
	private void finishSessions( String lastLine ) throws ParseException {
		Matcher m = timePattern.matcher(lastLine);
		
		// Sanity setting
		long end = System.currentTimeMillis();
		
		if( m.matches() ) {
			end = parseTime( m.group(1) );
		}
		endSession( end );
		
	}

	/**
	 * Create a writer to a Global file
	 * @param name
	 * @throws IOException
	 */
	private void createGlobal( String name ) throws IOException {
		File f = new File( inputFolder, CombatDefs.GLOBAL_PREFIX + name + ".txt" );
		PrintStream w = new PrintStream(f);
		
		this.globals.put(name, w);
		this.descriptor.addGlobal(name, f);
	}

	/**
	 * Create a writer to a Combat file, for stage 2 parsing
	 * @param name
	 * @throws IOException
	 */
	private void createCombat( String name, long t ) throws IOException {
		File f = new File( inputFolder,  (CombatDefs.COMBAT + t + "_" +new Timestamp(t).toString() + "_" + name + ".txt").replaceAll( "[^a-zA-Z0-9._ -]", "_") );
		
		this.curCombat = new PrintStream(f);
		this.combat.put( name, curCombat );
		
		this.descriptor.addCombat( name, f );
	}

	@Override
	public void close() throws IOException {
		for( PrintStream p : this.combat.values() ) {
			p.close();
		}
		
		for(PrintStream p : this.globals.values() ) {
			p.close();
		}
	}

	
	private long parseTime(String group) throws ParseException {
		return  df.parse( group ).getTime();
	}
	
	// Setters and Getters
	public Pattern getGlobalPattern() {
		return globalPattern;
	}

	public FileDescriptor getDescriptor() {
		return descriptor;
	}

	public Map<String, PrintStream> getGlobals() {
		return globals;
	}

	public PrintStream getCurCombat() {
		return curCombat;
	}

	public File getInputFolder() {
		return inputFolder;
	}

	public String getUuid() {
		return uuid;
	}

	public void setGlobalPattern(Pattern globalPattern) {
		this.globalPattern = globalPattern;
	}

	public void setDescriptor(FileDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public void setGlobals(Map<String, PrintStream> globals) {
		this.globals = globals;
	}

	public void setCurCombat(PrintStream combat) {
		this.curCombat = combat;
	}

	public void setInputFolder(File inputFolder) {
		this.inputFolder = inputFolder;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<CombatSessions> getSessions() {
		return sessions;
	}

	public void setSessions(List<CombatSessions> sessions) {
		this.sessions = sessions;
	}
	
	
}
