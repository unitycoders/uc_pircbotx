# uc_pircbotx - IRC Bot
[![Build Status](https://travis-ci.org/unitycoders/uc_pircbotx.svg?branch=master)](https://travis-ci.org/unitycoders/uc_pircbotx)

This is a bot for #unity-coders, which uses [pircbotx][pircbotx]. Source available on
[github](http://git.unitycoders.co.uk/uc_pircbotx).

## Documentation
If you would like documentation on how to use the bot, please consult the
[project wiki](https://github.com/unitycoders/uc_pircbotx/wiki).

## Developer Infomation

### Compiling and running with maven
    $ mvn package
    $ java -jar target/uc_pircbotx-0.1-SNAPSHOT-jar-with-dependencies.jar

### Running as a service (demo, not a real system service yet)
Warning: This is **very** experimental. It /should/ work on debian based distributions providing all the packages needed are installed.

    $ mvn package
    $ # edit src/main/scripts/ucbot-ctl # change user as needed
    $ # edit src/main/scripts/unix-install # change user to match ucbot-ctl
    $ sudo src/main/scripts/unix-install
    $ vim /etc/uc_pircbotx.json # edit to suit your needs
    $ sudo ucbot-ctl

### Maven With IDEs
#### Maven With Eclipse
The maven website has tutorials on using maven with eclipse. You can see them at http://maven.apache.org/guides/mini/guide-ide-eclipse.html.

#### Maven with NetBeans
Opening a maven project in netbeans will work 'out of the box' as of Netbeans 6.9 (7.1+ recommended).

[pircbotx]: http://code.google.com/p/pircbotx/
