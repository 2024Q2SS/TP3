import os
import subprocess

import numpy as np
import pandas as pd

deltaT = 0.1
obstacle_radius = 0.005
obstacle_perimeter = 2 * np.pi * obstacle_radius
wall_size = 0.1
walls_perimeter = 4 * wall_size


# Function to calculate the pressure from a single CSV
def calculate_pressure(file_path, deltaT):
    # Read the CSV file
    df = pd.read_csv(file_path)

    for df.line in df:
        print(df.line)
    # Filter rows with event types of interest (wallBounceX, wallBounceY, obstacleBounce)

    # Count the number of collisions

    # Calculate pressure as collisions per perimeter unit per deltaT


# Main function to run the simulations, calculate pressures, and output final CSV
def main():
    perimeter = 100  # Replace with the actual perimeter of the container
    deltaT = 0.01  # Replace with the actual deltaT (time step)

    # Initialize list to store pressure values for each run
    pressures = []

    # Process each output CSV to calculate pressure
    for i in range(1, 6):
        file_path = f"events_{i}.csv"
        calculate_pressure(file_path, deltaT)
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
