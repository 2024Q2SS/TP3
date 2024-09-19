#!/usr/bin/env bash

# Define the relative path to the events folder
EVENTS_DIR="./events"

# Run the Java app 5 times and rename the output CSV for each run
for i in {1..10}
do
    # Run the Maven command
    mvn exec:java -Dexec.mainClass="ar.edu.itba.ss.App"
    
    # Check if the output CSV exists
    if [ -f "${EVENTS_DIR}/events.csv" ]; then
        # Rename the CSV to events_i.csv where i is the run number
        mv "${EVENTS_DIR}/events.csv" "${EVENTS_DIR}/events_${i}.csv"
    else
        echo "Error: ${EVENTS_DIR}/events.csv not found after run $i"
        exit 1
    fi
done

cd ../plotter
# Run the Python script for pressure calculations
python pressure_calculation.py
