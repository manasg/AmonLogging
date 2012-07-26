What is this?
=============
- A Java Client for Amon.cx
- Written as a log4j appender
- Underlying mechanism is a very simplistic HTTP post - without any intelligence

Why?
====
- Yes, I could use Syslog, Flume, Graylog2, Logstash, Logster, Loggly, Papertrailapp, Splunk
- BUT, I like Amon's UI, simplicity to install AND tags in logs!
- Alerts/Notifications and a good UI is a must
- And I have always liked logs as streams - http://adam.heroku.com/past/2011/4/1/logs_are_streams_not_files/
- This is just the first step in that direction

ToDo
=====
- Non-blocking calls, using ZeroMQ OR AsyncHTTPClient
- Or use smart connection pooling
- Add jar targets in build.gradle

Usage
=====
- Simply attach the appender to your existing loggers. See source code under test/ for a simple example
- A sample log4j.properties is included as well
- To import project in eclipse - use gradle eclipse

License
=======
MIT License. See LICENSE
