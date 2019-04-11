#!/bin/sh
WEB_HOME=/timo/timo-afterservice
JMX_PORT=12345
IP=10.143.234.178
MEM=2048

JAVA_OPTS="-server \
 -Dcom.sun.management.jmxremote\
 -Dcom.sun.management.jmxremote.authenticate=false\
 -Dcom.sun.management.jmxremote.ssl=false\
 -Dcom.sun.management.jmxremote.port=${JMX_PORT}\
 -Dcom.sun.management.jmxremote.local.only=false\
 -Djava.rmi.server.hostname=${IP}\
 -Diris.hostname=${IP}\
 -Xmx${MEM}m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -XX:G1ReservePercent=30 -XX:InitiatingHeapOccupancyPercent=30\
 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintAdaptiveSizePolicy -XX:+PrintHeapAtGC -XX:+PrintHeapAtGCExtended -Xloggc:${WEB_HOME}/gc.log\
 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${WEB_HOME}\
 -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005\
 "

cd ${WEB_HOME}
nohup java ${JAVA_OPTS} -jar -Dspring.profiles.active=test timo-afterservice.jar 1>>${WEB_HOME}/nohup.log 2>&1 &
