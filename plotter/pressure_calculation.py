import os
import subprocess

import numpy as np
import pandas as pd

delta_t = 0.03
obstacle_radius = 0.005
obstacle_perimeter = 2 * np.pi * obstacle_radius
wall_size = 0.1
walls_perimeter = 4 * wall_size


# Function to calculate the pressure from a single CSV
def calculate_pressure(file_path, deltaT):
    # Read the CSV file
    df = pd.read_csv(file_path)
    pressures_in_delta_t = []
    delta_counts = 1
    total_time = 0
    L = walls_perimeter
    pressures = []
    for i, row in df.iterrows():
        total_time += row["time"]
        if total_time >= delta_counts * delta_t:
            P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * delta_t)
            pressures.append(P)
            delta_counts += 1
            pressures_in_delta_t = []
        if row["eventType"] == "wallBounceX":
            pressures_in_delta_t.append(2 * abs(row["a_vx"]))
        elif row["eventType"] == "wallBounceY":
            pressures_in_delta_t.append(2 * abs(row["b_vy"]))
    if pressures_in_delta_t:
        P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * delta_t)
        pressures.append(P)
    return pressures


# Main function to run the simulations, calculate pressures, and output final CSV
def main():

    # Initialize list to store pressure values for each run
    pressures = []

    # Process each output CSV to calculate pressure
    for i in range(1, 6):
        file_path = f"../DMDE/events/events_{i}.csv"
        pressures = calculate_pressure(file_path, delta_t)
        print(pressures)

        # pressure = calculate_pressure(file_path, perimeter, deltaT)
        # pressures.append(pressure)

    # Convert pressures to a NumPy array for easy calculations
    # pressures = np.array(pressures)

    # Calculate mean, std dev, and error (standard error of the mean)
    # mean_pressure = pressures.mean()
    # std_dev_pressure = pressures.std()
    # error_pressure = std_dev_pressure / np.sqrt(5)  # Standard error of the mean
    #
    # Create a summary DataFrame with deltaT, mean_pressure, std_dev, and error
    # summary_df = pd.DataFrame(
    #     {
    #         "deltaT": [deltaT],
    #         "mean_pressure": [mean_pressure],
    #         "std_dev": [std_dev_pressure],
    #         "error": [error_pressure],
    #     }
    # )
    #
    # # Save the summary DataFrame to a new CSV file
    # summary_df.to_csv("pressure_summary.csv", index=False)


if __name__ == "__main__":
    main()
