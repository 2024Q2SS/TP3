import os
import subprocess

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

obstacle_radius = 0.005
particle_radius = 0.001
obstacle_perimeter = 2 * np.pi * obstacle_radius
wall_size = 0.1
walls_perimeter = 4 * wall_size


def truncate_pressures(pressures):
    min_length = min(len(p) for p in pressures)  # Find the length of the smallest array
    truncated_pressures = [
        p[:min_length] for p in pressures
    ]  # Truncate each array to the smallest length
    return truncated_pressures


# Function to calculate the pressure from a single CSV
def calculate_pressure(file_path):
    # Read the CSV file
    df = pd.read_csv(file_path)
    pressures_in_delta_t = []
    total_time = 0.0
    L = walls_perimeter
    for i, row in df.iterrows():
        total_time += row["time"]
        if row["eventType"] == "wallBounceX":
            pressures_in_delta_t.append(2 * abs(row["a_vx"]))
        elif row["eventType"] == "wallBounceY":
            pressures_in_delta_t.append(2 * abs(row["b_vy"]))
    P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * total_time)
    return P


def calculate_pressures_obs(file_path):
    df = pd.read_csv(file_path)
    pressures_in_delta_t = []
    total_time = 0.0
    L = obstacle_perimeter  # The perimeter of the circular obstacle

    for i, row in df.iterrows():
        total_time += row["time"]

        # Check for obstacle bounces
        if row["eventType"] == "obstacleBounce":
            a_x = row["a_x"]
            a_y = row["a_y"]
            b_x = row["b_x"]
            b_y = row["b_y"]
            a_vx = row["a_vx"]
            a_vy = row["a_vy"]
            p_x = (b_x - a_x) / (obstacle_radius + particle_radius)
            p_y = (b_y - a_y) / (obstacle_radius + particle_radius)

            normal_velocity = abs((p_x * a_vx) + (p_y * a_vy))
            pressures_in_delta_t.append(2 * normal_velocity)

    P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * total_time)
    return P


def main():
    # Initialize list to store pressure values for each run (list of lists)
    pressures = []
    obs_pressures = []
    v_obs_p = []
    v_p = []
    velocities = [1, 3, 6, 10]

    for v in velocities:
        print("v:", v)

        for i in range(1, 11):
            print("i:", i)
            file_path = f"../DMDE/events{v}/events_{i}.csv"
            run_pressures = calculate_pressure(file_path)
            run_obs_pressures = calculate_pressures_obs(file_path)
            obs_pressures.append(run_obs_pressures)
            pressures.append(run_pressures)

        pressures_np = np.array(pressures)
        obs_pressures_np = np.array(obs_pressures)

        mean_pressures = pressures_np.mean()
        std_dev_pressures = pressures_np.std()
        error_pressures = std_dev_pressures / np.sqrt(pressures_np.shape[0])

        obs_mean_pressures = obs_pressures_np.mean()
        obs_std_dev_pressures = obs_pressures_np.std()
        obs_error_pressures = obs_std_dev_pressures / np.sqrt(obs_pressures_np.shape[0])

        v_obs_p.append(
            (v, obs_mean_pressures, obs_std_dev_pressures, obs_error_pressures)
        )
        v_p.append((v, mean_pressures, std_dev_pressures, error_pressures))
        pressures = []
        obs_pressures = []
        pressures_np = []
        obs_pressures_np = []

    df = pd.DataFrame(v_p, columns=["Velocity", "Mean_Pressure", "Std_Dev", "Error"])
    df_obs = pd.DataFrame(
        v_obs_p, columns=["Velocity", "Obs_Mean_Pressure", "Std_Dev", "Error"]
    )

    ax = plt.subplot(111)

    ax.errorbar(
        df["Velocity"],
        df["Mean_Pressure"],
        yerr=df["Error"],
        fmt="-o",
        capsize=5,
        label="Pressure",
    )
    ax.errorbar(
        df_obs["Velocity"],
        df_obs["Obs_Mean_Pressure"],
        yerr=df_obs["Error"],
        fmt="-o",
        capsize=5,
        label="Obs_Pressure",
    )
    ax.set_xlabel("Velocity (m/s)", fontsize=20)
    ax.set_ylabel("Mean Pressure (N/m)", fontsize=20)

    # Put the legend outside the plot, top right
    ax.set_xlim(left=0)
    ax.set_ylim(bottom=0)
    ax.grid(True)
    ax.legend(loc="upper right", fontsize=14, bbox_to_anchor=(1.6, 1))
    plt.show()


if __name__ == "__main__":
    main()
