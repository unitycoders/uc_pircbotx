/**
 * Copyright © 2012-2014 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.HelpText;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Outputs the formatted date or time.
 *
 * @author Bruce Cowan
 */
@HelpText("Display times and dates in different timezones")
public class DateTimeCommand extends AnnotationModule {
	private final static String DEFAULT_TIMEZONE = "UTC";
	private final static String INVALID_TIMEZONE = "GMT";
	
    private final DateFormat dtformat;
    private final DateFormat dformat;
    private final DateFormat tformat;

    public DateTimeCommand() {
    	super("datetime");
        this.dtformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        this.dformat = DateFormat.getDateInstance(DateFormat.LONG);
        this.tformat = DateFormat.getTimeInstance(DateFormat.LONG);
    }

    @Command("date")
    @HelpText("Display the current date")
    public void onDate(Message message) { 	
    	String tz = message.getArgument(2, DEFAULT_TIMEZONE);
    	String fmt = "The date is %s";
    	message.respond(handleRequest(fmt, tz, dformat));
    }
    
    @Command("time")
    @HelpText("Display the current time")
    public void onTime(Message message) { 	
    	String tz = message.getArgument(2, DEFAULT_TIMEZONE);
    	String fmt = "The time is %s";
    	message.respond(handleRequest(fmt, tz, tformat));
    }
    
    @Command
    @HelpText("Display time and date infomation")
    public void onDateTime(Message message) { 	
    	String tz = message.getArgument(2, DEFAULT_TIMEZONE);
    	String fmt = "The date and time is %s";
    	message.respond(handleRequest(fmt, tz, dtformat));
    }
    
    private String handleRequest(String fmt, String tz, DateFormat format) {
    	Date currTime = new Date();
    	TimeZone timezone = TimeZone.getTimeZone(tz);
    	
    	TimeZone notFound = TimeZone.getTimeZone(INVALID_TIMEZONE);
    	if (!tz.equals(INVALID_TIMEZONE) && notFound.equals(timezone)) {
    		return "I don't know about that timezone";
    	}
    	
    	format.setTimeZone(timezone);
    	return String.format(fmt, format.format(currTime));
    }

    /**
     * Display the current unix timestamp.
     * 
     * @param event the message event from the parser
     */
    @Command("unix")
    @HelpText("Display the current unit timestamp")
    public void unixToTime(Message event) {
    	long unixTime = System.currentTimeMillis() / 1000L;
    	
        String fmt = String.format("The current unix timestamp is %s", unixTime);
        event.respond(fmt);
    }
}
