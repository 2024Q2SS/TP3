import math

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

N = 300


times = []

# for i in range(1, 11):
#     df = pd.read_csv(f"{base_path}_{i}.csv")
#     time = 0.0
#     first_bounce_count = 0
#     for i, row in df.iterrows():
#         time += row["time"]
#         first_bounce_count += 1 if row["eventType"] == "firstCollision" else 0
#         if first_bounce_count == math.floor(N * 4 / 10):
#             break
#     times.append(time)
#
# print(times)

data = [
    [
        0.6603866335756473,
        0.6206538074281335,
        0.6188086151398712,
        0.6003674448210258,
        0.6479384764840824,
        0.6320750552041952,
        0.6056988315260159,
        0.6640714787122572,
        0.6465591376514681,
        0.6076703023266745,
    ],
    [
        0.2161742488285788,
        0.20300424154552915,
        0.2083496274100485,
        0.22695787859157884,
        0.21475179369706807,
        0.1913402959689246,
        0.18960443427530213,
        0.22705986544896428,
        0.23494157944241129,
        0.19577995328983086,
    ],
    [
        0.09846172819503234,
        0.1086065708305649,
        0.10862526998572913,
        0.10672645957050732,
        0.10319257388233019,
        0.11037767157805085,
        0.10221580170216071,
        0.11778036505716401,
        0.10407677541955902,
        0.11214660206383396,
    ],
    [
        0.054437375536759106,
        0.06813478132959512,
        0.0642718495120767,
        0.073701016379152,
        0.06531206885525269,
        0.0649006510374858,
        0.06724744931780198,
        0.06484025336616261,
        0.06551427717756714,
        0.06797166673372224,
    ],
]

means = [np.mean(d) for d in data]
std = [np.std(d) for d in data]

v = [1.0, 3.0, 6.0, 10.0]

fig, ax = plt.subplots(figsize=(12, 10))

ax.errorbar(
    v,
    means,
    yerr=std,
    fmt="o",
    capsize=5,
    label="Mean time",
)
ax.set_xlim(left=0, right=11)
ax.set_ylim(bottom=0, top=0.8)
ax.set_xlabel("Velocity (m/s)", fontsize=20)
ax.set_ylabel("Time (s)", fontsize=20)
ax.legend(loc="upper right", fontsize=20, bbox_to_anchor=(1.4, 1))
ax.tick_params(axis="both", which="major", labelsize=20)
ax.grid(True)
plt.show()


base_path = "../DMDE/events/events"
mean_instant_bounces_with_v = []
std_instant_bounces_with_v = []
for i in [1, 3, 6, 10]:

    instant_bounces_for_run = []
    for j in range(1, 11):
        df = pd.read_csv(f"{base_path}{i}/events_{j}.csv")
        time = df["time"].sum()

        values = df["eventType"].value_counts()
        bounces = values["obstacleBounce"] + values["firstCollision"]
        bounces_in_dt = bounces / time
        instant_bounces_for_run.append(bounces_in_dt)
    mean_instant_bounces_with_v.append(np.mean(instant_bounces_for_run))
    std_instant_bounces_with_v.append(np.std(instant_bounces_for_run))
    instant_bounces_for_run.clear()

means = mean_instant_bounces_with_v
std = std_instant_bounces_with_v
print(means, std)
fig, ax = plt.subplots(figsize=(12, 10))
ax.errorbar(
    [1.0, 3.0, 6.0, 10.0],
    means,
    yerr=std,
    fmt="o",
    capsize=5,
    label="Mean bounces",
)

ax.set_xlim(left=0, right=11)
ax.set_ylim(bottom=0, top=5000)
ax.set_xlabel("Velocity (m/s)", fontsize=20)
ax.set_ylabel("Frecuency(1/s)", fontsize=20)
ax.legend(loc="upper right", fontsize=20, bbox_to_anchor=(1.4, 1))
ax.grid(True)
ax.tick_params(axis="both", which="major", labelsize=20)
plt.show()
