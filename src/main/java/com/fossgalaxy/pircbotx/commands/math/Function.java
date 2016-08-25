package com.fossgalaxy.pircbotx.commands.math;

public interface Function {

    String getName();

    int getArity();

    double evaulate(double... args);

    boolean isLeftAssoc();

    int getPrecidence();

}
