package com.fossgalaxy.pircbotx.backends;


import org.pircbotx.User;

/**
 * Created by webpigeon on 14/08/16.
 */
public interface UserService {


    User getBotUser();

    User getUser(String name);

}
