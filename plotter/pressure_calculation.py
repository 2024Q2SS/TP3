import os
import subprocess

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

delta_t = 0.009
obstacle_radius = 0.005
obstacle_perimeter = 2 * np.pi * obstacle_radius
wall_size = 0.1
walls_perimeter = 4 * wall_size

def truncate_pressures(pressures):
    min_length = min(len(p) for p in pressures)  # Find the length of the smallest array
    truncated_pressures = [p[:min_length] for p in pressures]  # Truncate each array to the smallest length
    return truncated_pressures

# Function to calculate the pressure from a single CSV
def calculate_pressure(file_path):
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
            print(len(pressures_in_delta_t))
            P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * delta_t)
            pressures.append(P)
            delta_counts += 1
            pressures_in_delta_t = []
        if row["eventType"] == "wallBounceX":
            pressures_in_delta_t.append(2 * abs(row["a_vx"]))
        elif row["eventType"] == "wallBounceY":
            pressures_in_delta_t.append(2 * abs(row["b_vy"]))
    return pressures

def calculate_pressures_obs(file_path):
    df = pd.read_csv(file_path)
    pressures_in_delta_t = []
    delta_counts = 1
    total_time = 0
    L = obstacle_perimeter  # The perimeter of the circular obstacle
    pressures = []
    
    for i, row in df.iterrows():
        total_time += row["time"]
        
        # Calculate the pressure at each delta_t interval
        if total_time >= delta_counts * delta_t:
            if pressures_in_delta_t:  # Avoid division by zero
                P = sum(pressures_in_delta_t) / (len(pressures_in_delta_t) * L * delta_t)
                pressures.append(P)
            delta_counts += 1
            pressures_in_delta_t = []

        # Check for obstacle bounces
        if row["eventType"] == "obstacleBounce":
            normal_velocity = np.sqrt(row["a_vx"]**2 + row["a_vy"]**2)  # Calculate normal velocity on impact
            pressures_in_delta_t.append(2 * normal_velocity)  # Double the normal velocity as force

    return pressures

def plot_pressure_vs_time(summary_df):
    plt.figure(figsize=(8, 6))

    # Plot mean pressure against deltaT with error bars for std_dev
    plt.errorbar(summary_df["deltaT"], summary_df["mean_pressure"], 
                 yerr=summary_df["std_dev"], fmt='o', capsize=5, label='Mean Pressure (with Std Dev)')

    # Optionally, you can also plot with error instead of std_dev by uncommenting the line below:
    # plt.errorbar(summary_df["deltaT"], summary_df["mean_pressure"], yerr=summary_df["error"], fmt='o', capsize=5, label='Mean Pressure (with Error)')

    # Set plot labels and title
    plt.xlabel('Time (deltaT)')
    plt.ylabel('Pressure')
    plt.xlim(left=0)
    plt.ylim(bottom = 0)
    plt.title('Pressure vs Time with Error Bars')
    plt.legend()

    # Show the plot
    plt.grid(True)
    plt.tight_layout()
    plt.savefig("pressure_vs_time.png")  # Save plot as an image
    plt.show()

def main():
    # Initialize list to store pressure values for each run (list of lists)
    pressures = []
    obs_pressures = []

    # Process each output CSV to calculate pressure
    for i in range(1, 11):
        file_path = f"../DMDE/events/events_{i}.csv"
        run_pressures = calculate_pressure(file_path)  # Simulated pressure data
        run_obs_pressures = calculate_pressures_obs(file_path)
        obs_pressures.append(run_obs_pressures)
        pressures.append(run_pressures)
    
  # Truncate pressures to ensure they all have the same length
    truncated_pressures = truncate_pressures(pressures)
    truncated_obs_pressures = truncate_pressures(obs_pressures)
    

    # Convert truncated pressures to a NumPy array for easier calculations
    pressures_np = np.array(truncated_pressures)

    # Automatically determine the number of entries in the pressure data
    num_entries = pressures_np.shape[1]

    # Dynamically generate deltaT values based on the number of pressure entries
    deltaT_values = [delta_t * (i + 1) for i in range(num_entries)]

    # Calculate mean, std dev, and error (standard error of the mean) for each deltaT
    mean_pressures = pressures_np.mean(axis=0)  # Mean across the runs for each deltaT
    std_dev_pressures = pressures_np.std(axis=0)  # Standard deviation for each deltaT
    error_pressures = std_dev_pressures / np.sqrt(pressures_np.shape[0])  # Standard error

    # Create a summary DataFrame with deltaT, mean_pressure, std_dev, and error
    summary_df = pd.DataFrame({
        "deltaT": deltaT_values,
        "mean_pressure": mean_pressures,
        "std_dev": std_dev_pressures,
        "error": error_pressures,
    })
    
    # Save the summary DataFrame to a new CSV file
    summary_df.to_csv("pressure_summary.csv", index=False)
    print("Summary CSV saved as 'pressure_summary.csv'")
    plot_pressure_vs_time(summary_df)



if __name__ == "__main__":
    main()
