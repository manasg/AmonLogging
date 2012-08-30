What is this?
=============
- A Java Client for Amon.cx and Amon+
- Written as a log4j appender
- Underlying mechanism is ZMQ Dealer

Why?
====
- Yes, I could use Syslog, Flume, Graylog2, Logstash, Logster, Loggly, Papertrailapp, Splunk
- BUT, I like Amon's UI, simplicity to install AND tags in logs!
- Alerts/Notifications and a good UI is a must
- And I have always liked logs as streams - http://adam.heroku.com/past/2011/4/1/logs_are_streams_not_files/
- This is just the first step in that direction
- Wrappers are nice. If we move to another system from Amon - only the appender code needs to change. 

Usage
=====
- Simply attach the appender to your existing loggers. See source code under test/ for a simple example
- A sample log4j.properties is included as well
- To import project in eclipse - use gradle eclipse

Dependencies
============
- build.gradle lists the required jars (not the transitive dependencies)
- You can use the task - gradle dependencies - to get an explicit list
- jZMQ - You need libjzmq + zmq.jar. I built them using - https://github.com/zeromq/jzmq
- When running the code - you may optionally need to specify -Djava.library.path=/location/of/libjzmq/
- At the receiving end - make sure you have AmonMQ daemon. With Amon installation, the stock amonmq will work. Amon+ does not have Amonmq by default - you can get one here - https://github.com/manasg/amonmq-plus


License
=======
MIT License. See LICENSE
