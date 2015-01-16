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

    private final DateFormat dtformat;
    private final DateFormat dformat;
    private final DateFormat tformat;

    public DateTimeCommand() {
        this.dtformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        this.dformat = DateFormat.getDateInstance(DateFormat.LONG);
        this.tformat = DateFormat.getTimeInstance(DateFormat.LONG);
    }

    @Command
    public void onMessage(Message event) throws Exception {
        String msg = event.getMessage();
        Date date = new Date();

        String[] args = msg.split(" ");
        String keyword = args[0];

        String tense = "are";
        String resp = "INVALID";
        if (keyword.equals("date")) {
            tense = "is";
            resp = dformat.format(date);
        }

        if (keyword.equals("time")) {
            tense = "is";
            resp = tformat.format(date);
        }

        if (keyword.equals("datetime")) {
            keyword = "date and time";
            tense = "are";
            resp = dtformat.format(date);
        }

        String fmt = String.format("The current %s %s %s", keyword, tense, resp);
        event.respond(fmt);
    }

    @Command("local")
    public void onLocalTime(Message event) throws Exception {
        String[] args = event.getMessage().split(" ");

        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        if (args.length > 2) {
            df.setTimeZone(TimeZone.getTimeZone(args[2]));
        }

        String fmt = String.format("The current %s %s %s", "date and time", "is", df.format(date));
        event.respond(fmt);
    }

    @Command("unix")
    public void unixToTime(Message event) throws Exception {
        String fmt = String.format("The current unix timestamp is %s", (int) (System.currentTimeMillis() / 1000L));
        event.respond(fmt);
    }
}
