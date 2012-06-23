#!/bin/bash
echo "Starting Global configuration process"
#Get the local directory in order to set the full path to the log4j config file
ROQ_BIN=$(dirname "$(readlink -f "$(type -P $0 || echo $0)")")
ROQ_HOME=$(dirname $ROQ_BIN)
echo "ROQ BIN Home= $ROQ_HOME"

if [ -n "$1" ]; then
	echo "Input starting script $@"
	 java -Djava.library.path=/usr/local/lib -Dlog4j.configuration="file:$ROQ_HOME/config/log4j.properties" -cp ../lib/roq-management-1.0-SNAPSHOT-jar-with-dependencies.jar org.roqmessaging.management.launcher.GlobalConfigurationLauncher $@
else
	 java -Djava.library.path=/usr/local/lib -Dlog4j.configuration="file:$ROQ_HOME/config/log4j.properties" -cp ../lib/roq-management-1.0-SNAPSHOT-jar-with-dependencies.jar org.roqmessaging.management.launcher.GlobalConfigurationLauncher ../config/GCM.properties
fi
