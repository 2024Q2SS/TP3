import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

base_path = "../DMDE/events/events1/events"


def pick_delta(df):
    slices = 20
    return df["time"].sum() / slices


def calculate_bounces(file_path):
    df = pd.read_csv(file_path)
    dt = pick_delta(df)
    first_bounces = []
    total_bounces = []
    first_bounces_in_dt = 0
    total_bounces_in_dt = 0
    time_elapsed = 0.0
    for i, row in df.iterrows():
        time_elapsed += row["time"]
        if time_elapsed >= dt:
            first_bounces.append(first_bounces_in_dt)
            total_bounces.append(total_bounces_in_dt)
            total_bounces_in_dt = 0
            first_bounces_in_dt = 0
            time_elapsed = 0.0
        if row["eventType"] == "firstCollision":
            first_bounces_in_dt += 1
            total_bounces_in_dt += 1
        elif row["eventType"] == "obstacleBounce":
            total_bounces_in_dt += 1
    return first_bounces, total_bounces


first_bounces = []
all_bounces = []
for i in range(1, 11):
    print(f"Processing file {i}")
    file_path = f"{base_path}_{i}.csv"
    first, all = calculate_bounces(file_path)
    first_bounces.append(first)
    all_bounces.append(all)

min_length = min(len(p) for p in first_bounces)

first_bounces = [p[:min_length] for p in first_bounces]
all_bounces = [p[:min_length] for p in all_bounces]

first_means = np.array(first_bounces).mean(axis=0)
first_stds = np.array(first_bounces).std(axis=0)

all_means = np.array(all_bounces).mean(axis=0)
all_stds = np.array(all_bounces).std(axis=0)

length = np.arange(1, min_length + 1)


# Plot settings
def create_plot():
    fig, axs = plt.subplots(figsize=(12, 8))
    # Plot 1: first_means with error bars
    # axs[0].errorbar(
    #     length,
    #     first_means,
    #     yerr=first_stds,
    #     fmt="o",
    #     label="Mean amount of first bounces with obstacle",
    #     color="blue",
    #     capsize=5,
    # )
    # axs[0].set_xlim(left=0)
    # axs[0].set_ylim(bottom=0, top=30)
    # axs[0].set_xlabel("Time(in steps)")
    # axs[0].set_ylabel("Mean amount of bounces with obstacle")
    # axs[0].legend(loc="upper right", fontsize=12, bbox_to_anchor=(1.6, 1))
    # axs[0].grid(True)
    # Plot 2: all_means with error bars
    # axs[1].errorbar(
    #     length,
    #     all_means,
    #     yerr=all_stds,
    #     fmt="o",
    #     label="Mean amount of total bounces with obstacle",
    #     color="green",
    #     capsize=5,
    # )
    # axs[1].set_xlim(left=0)
    # axs[1].set_ylim(bottom=0, top=30)
    # axs[1].set_xlabel("Time(in steps)")
    # axs[1].set_ylabel("Mean amount of bounces with obstacle")
    # axs[1].legend(loc="upper right", fontsize=12, bbox_to_anchor=(1.6, 1))
    # axs[1].grid(True)
    # Plot 3: both means with error bars
    axs.errorbar(
        length,
        first_means,
        yerr=first_stds,
        fmt="o",
        label="Mean first bounces",
        color="blue",
        capsize=5,
    )
    axs.errorbar(
        length,
        all_means,
        yerr=all_stds,
        fmt="o",
        label="Mean total bounces",
        color="green",
        capsize=5,
    )
    axs.set_xlim(left=0)
    axs.set_ylim(bottom=0, top=30)
    axs.set_xlabel("Time(in steps)", fontsize=20)
    axs.set_ylabel("Bounces", fontsize=20)
    axs.legend(loc="upper right", fontsize=20, bbox_to_anchor=(1.5, 1))
    axs.tick_params(axis="both", which="major", labelsize=20)
    axs.grid(True)
    plt.show()


create_plot()
