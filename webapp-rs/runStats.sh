#!/bin/bash

MIN_RPS=5000
RPS_INCREMENT=5000
MAX_RPS=75000

RAMP_UP=150
DURATION=300
SIMULATION_CLASS="org.jboss.perf.HelloWorldSimulations\$Get"

REQUESTS_PER_SECOND=$MIN_RPS

echo "Starting runs"
while [ $REQUESTS_PER_SECOND -lt $MAX_RPS ]
do
    echo "Running @: $REQUESTS_PER_SECOND"
    mvn -P client gatling:execute -Dtest.rps=$REQUESTS_PER_SECOND -Dtest.rampUp=$RAMP_UP -Dtest.duration=$DURATION -Dgatling.simulationClass=$SIMULATION_CLASS -Dgatling.outputName=$REQUESTS_PER_SECOND
    REQUESTS_PER_SECOND=$[$REQUESTS_PER_SECOND+$RPS_INCREMENT]
done
echo "done"
