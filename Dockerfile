FROM java:openjdk-7-jre

# Create a user for the bot
RUN adduser --disabled-password --gecos "" uc_pircbotx
WORKDIR /home/uc_pircbotx/

# drop root privs
USER uc_pircbotx
RUN mkdir /home/uc_pircbotx/plugins/ /home/uc_pircbotx/config/ && chown -R uc_pircbotx:uc_pircbotx /home/uc_pircbotx/
VOLUME ["/home/uc_pircbotx/plugins/", "/home/uc_pircbotx/config/"]

# copy the bot into the user's home dir
ADD target/uc_pircbotx-0.3-SNAPSHOT-jar-with-dependencies.jar .
CMD ["java", "-jar", "uc_pircbotx-0.3-SNAPSHOT-jar-with-dependencies.jar", "config/bot.json"]
