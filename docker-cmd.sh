#!/bin/sh
set -e

APP_VERSION=${APP_VERSION:="1970-01-01-00-00-00-unknown"}
XMS=${XMS:="256m"}
XMX=${XMX:="512m"}
COUNTRY=${COUNTRY:="US"}
LANGUAGE=${LANGUAGE:="en"}
TIMEZONE=${TIMEZONE:="UTC"}

JAVA_OPTIONS=${JAVA_OPTIONS:="-server -Xms${XMS} -Xmx${XMX} -Duser.country=${COUNTRY} -Duser.language=${LANGUAGE} -Duser.timezone=${TIMEZONE}"}

if [ -n "${ENABLE_JMX+set}" ]; then
    JAVA_OPTIONS="${JAVA_OPTIONS} \
                  -Dcom.sun.management.jmxremote.authenticate=false \
                  -Dcom.sun.management.jmxremote.local.only=false \
                  -Dcom.sun.management.jmxremote.port=9090 \
                  -Dcom.sun.management.jmxremote.rmi.port=9090 \
                  -Dcom.sun.management.jmxremote.ssl=false \
                  -Dcom.sun.management.jmxremote=true \
                  -Djava.rmi.server.hostname=$(wget -q -t 1 -T 1 -O - http://169.254.169.254/latest/meta-data/public-hostname || echo localhost)";
fi

JAVA_OPTIONS=$(echo "${JAVA_OPTIONS}" | xargs)
echo "Set up JAVA_OPTIONS: ${JAVA_OPTIONS}"

exec java ${JAVA_OPTIONS} ${OPTIONS} -jar app.jar
