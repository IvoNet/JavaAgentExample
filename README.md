# Introduction #

This project demonstrates the usage of a javaagent

# Usage #

* open command prompt / terminal in this folder
* mvn package
* java -javaagent:target/IvoNetJavaAgent-jar-with-dependencies.jar=blabla nl.ivonet.example.code.MyMain foo bar baz

# Description #

The IvoNetAgent class implements the method below witch is statically called by the vm:

    public static void premain(final String args, final Instrumentation inst) throws Exception {}


The MANIFEST.MF file created by maven will look something like this:

    Manifest-Version: 1.0
    Archiver-Version: Plexus Archiver
    Created-By: Apache Maven
    Built-By: ivonet
    Build-Jdk: 1.7.0_25
    Agent-Class: nl.ivonet.example.agent.IvoNetJavaAgent
    Can-Redefine-Classes: true
    Can-Retransform-Classes: true
    Premain-Class: nl.ivonet.example.agent.IvoNetJavaAgent

Note the **Premain-Class** item and the **Agent-Class**...

The FooMethodTransformer class adds a **public void foo() {}** method to all classes being loaded

That's just about it.


# Todo #

* make a useful javaagent like tracing a specific class by configuration without having to rebuild the project the class lives in.
* demonstrate cross-cutting concerns by added AOP like functionallity through an agent
* Add logging to all classes by using an agent


# Reference #

I have done some researche on the net and some of the sites I found interesting were:

* http://www.slideshare.net/arhan/taming-java-agents
* http://dhruba.name/2010/02/07/creation-dynamic-loading-and-instrumentation-with-javaagents/