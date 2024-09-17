import csv
import json

import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np

totalTime = 0

# Load configuration
with open("../config.json") as f:
    config = json.load(f)


deltaT = config["deltaT"]  # Number of particles per frame
obsRadius = config["obsRadius"]
boardLength = config["boardLength"]

# Read events data from CSV
events = []
with open("../DMDE/events.csv", "r") as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        events.append(
            {
                "frame": int(row["frame"]),
                "eventType": row["eventType"],
                "time": float(row["time"]),
                "a_x": float(row["a_x"]),
                "a_y": float(row["a_y"]),
                "a_vx": float(row["a_vx"]),
                "a_vy": float(row["a_vy"]),
                "b_x": float(row["b_x"]),
                "b_y": float(row["b_y"]),
                "b_vx": float(row["b_vx"]),
                "b_vy": float(row["b_vy"]),
            }
        )


perimeter = (np.pi * obsRadius) + 4*boardLength
pressures = []
deltas = []
overAllcounter = 0
collisionCounter = 0
v_i = 0;
for event in events:
    totalTime += event.time
    if(totalTime >= deltaT):
        p = (-v_i)/(collisionCounter * deltaT * perimeter)
        collisionCounter = 0
        v_i = 0
        pressures[overAllcounter] = p
        deltas[overAllcounter] = totalTime
        totalTime = 0
        overAllcounter += 1
    if(event.eventType == "wallBounceX"):
        v_i += 2*event.a_vx
    else: 
        if(event.eventType == "wallBounceY"):
            v_i += 2*event.b_vx
        else:
            if(event.eventType == "obstacleBounce"):
                v_i += #calcular bien la normal contra el obstaculo
    collision += 1


