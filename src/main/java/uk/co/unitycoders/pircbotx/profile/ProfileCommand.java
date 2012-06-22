/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.profile;

import java.sql.SQLException;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.ProfileModel;

/**
 *
 *
 */
public class ProfileCommand {
    private ProfileManager manager;
    private ProfileModel model;

    public ProfileCommand(ProfileManager manager) throws ClassNotFoundException, SQLException{
        this.manager = manager;
        this.model = DBConnection.getProfileModel();
    }

    @Command("register")
    public void register(MessageEvent<PircBotX> event) throws Exception {
        try{
            manager.register("demo123");
            event.respond("Created new profile for you");
        } catch (SQLException ex){
            event.respond("error: "+ex);
        }
    }

    @Command("login")
    public void login(MessageEvent<PircBotX> event) throws Exception {
        manager.login(event.getUser(), "demo123");
        event.respond("you are now logged in");
    }

    @Command("logoff")
    public void logoff(MessageEvent<PircBotX> event) throws Exception {
        manager.logoff(event.getUser());
        event.respond("you have been logged out");
    }

    @Command("addperm")
    public void addPerm(MessageEvent<PircBotX> event) throws Exception {
        Profile profile = manager.getProfile(event.getUser());
        if( profile == null ){
            event.respond("you are not logged in.");
            return;
        }

        model.addPerm(profile.getName(), "perm1");
        event.respond("permission 'perm1' added to your profile");
    }

    @Command("rmperm")
    public void removePerm(MessageEvent<PircBotX> event) throws Exception {
        Profile profile = manager.getProfile(event.getUser());
        if( profile == null ){
            event.respond("you are not logged in.");
            return;
        }

        model.removePerm(profile.getName(), "perm1");
        event.respond("permission 'perm1' removed from your profile");
    }

    @Command("hasperm")
    public void hasPerm(MessageEvent<PircBotX> event) throws Exception {
        Profile profile = manager.getProfile(event.getUser());
        if( profile == null ){
            event.respond("you are not logged in.");
            return;
        }

        if(profile.hasPermission("perm1")){
            event.respond("you have permission 'perm1'");
        }else{
            event.respond("you don't have permission 'perm1'");
        }
    }

}
