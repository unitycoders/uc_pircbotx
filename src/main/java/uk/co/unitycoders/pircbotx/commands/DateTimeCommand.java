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
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Outputs the formatted date or time.
 *
 * @author Bruce Cowan
 */
public class DateTimeCommand {
	private final static String DEFAULT_COMMAND = "datetime";
	private final static String DEFAULT_TIMEZONE = "UTC";
	
	//avoid magic values in the code
	private final static String CMD_DATETIME = "datetime";
	private final static String CMD_TIME = "time";
	private final static String CMD_DATE = "date";
	
    private final DateFormat dtformat;
    private final DateFormat dformat;
    private final DateFormat tformat;

    public DateTimeCommand() {
        this.dtformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        this.dformat = DateFormat.getDateInstance(DateFormat.LONG);
        this.tformat = DateFormat.getTimeInstance(DateFormat.LONG);
    }

    /**
     * Display the date or time in the default (bot's current) timezone.
     * 
     * @param event the message event from the parser
     */
    @Command
    public void onMessage(Message event) {
        Date date = new Date();

        //In the event you don't provide an arguement this crashes - which is bad.
        String keyword = event.getArgument(0, DEFAULT_COMMAND);

        String tense = "are";
        String resp = "INVALID";
        
        if (CMD_DATE.equals(keyword)) {
            tense = "is";
            resp = dformat.format(date);
        }

        if (CMD_TIME.equals(keyword)) {
            tense = "is";
            resp = tformat.format(date);
        }

        if (CMD_DATETIME.equals(keyword)) {
            keyword = "date and time";
            tense = "are";
            resp = dtformat.format(date);
        }

        String fmt = String.format("The current %s %s %s", keyword, tense, resp);
        event.respond(fmt);
    }

    /**
     * Display a time in the user's selected timezone (defaults to UTC if not provided)
     * 
     * @param event the message event from the parser
     */
    @Command("local")
    public void onLocalTime(Message event) {
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        //Argument format is:
        // 0 -> command name
        // 1 -> option (local, default, unix, etc...)
        // 2 ... n -> user provided options
        // if no value for 1 is provided (ie. &date) then "default" is inserted as option
        // (ie. &date -> &date default).
        
        df.setTimeZone(TimeZone.getTimeZone(event.getArgument(2, DEFAULT_TIMEZONE)));

        String fmt = String.format("The current %s %s %s", "date and time", "is", df.format(date));
        event.respond(fmt);
    }

    /**
     * Display the current unix timestamp.
     * 
     * @param event the message event from the parser
     */
    @Command("unix")
    public void unixToTime(Message event) {
        String fmt = String.format("The current unix timestamp is %s", (int) (System.currentTimeMillis() / 1000L));
        event.respond(fmt);
    }
}
