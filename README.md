# uc_pircbotx - IRC Bot
[![build
status](https://git.fossgalaxy.com/open-source/irc-bot/badges/master/build.svg)](https://git.fossgalaxy.com/open-source/irc-bot/commits/master)

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

### Running as a docker image
This is the way we are currently using the bot for our needs. There is a
public docker image on [docker hub](https://hub.docker.com/r/webpigeon/uc_pircbotx/).

There are however a few issues needing to be fixed:
* Config file is baked into the build, you need to modify this before
  running the bot.
* Plugins directory is not a thing yet
* Database is not stored on a volume (data gets nuked with the volume)

#### running the image
```
mkdir config plugins
wget https://raw.githubusercontent.com/unitycoders/uc_pircbotx/master/src/main/resources/uc_pircbotx.json.example -O config/bot.json
# edit config file
sudo docker run -d --name ircbot -v `pwd`/config:/home/uc_pircbotx/config -v `pwd`/plugins:/home/uc_pircbotx/plugins webpigeon/uc_pircbotx
```


#### building the docker image
This is mostly for webpigeon incase he forgets

```
# sudo docker login
sudo docker build -t webpigeon/uc_pircbotx .
sudo docker push webpigeon/uc_pircbotx
```


### Maven With IDEs
#### Maven With Eclipse
The maven website has tutorials on using maven with eclipse. You can see them at http://maven.apache.org/guides/mini/guide-ide-eclipse.html.

#### Maven with NetBeans
Opening a maven project in netbeans will work 'out of the box' as of Netbeans 6.9 (7.1+ recommended).

[pircbotx]: http://code.google.com/p/pircbotx/
