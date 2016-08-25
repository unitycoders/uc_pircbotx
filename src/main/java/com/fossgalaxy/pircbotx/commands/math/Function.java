package com.fossgalaxy.pircbotx.commands.math;

public interface Function {

    public String getName();

    public int getArity();

    public double evaulate(double... args);

    public boolean isLeftAssoc();

    public int getPrecidence();

}
