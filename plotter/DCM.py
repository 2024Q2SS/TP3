import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import scipy.stats as stats


# Function to calculate MSD for a specific particle across multiple runs
def calculate_msd(particle_id, num_runs, num_steps):
    msds_per_run = []

    # Iterate over each run
    for i in range(1, num_runs + 1):
        # Load the data for the i-th run
        file_name = f"../DMDE/outputs/output_{i}.csv"
        data = pd.read_csv(file_name)

        # Filter data for the particle with the given id
        particle_data = data[data["id"] == particle_id]

        # Extract positions
        x = particle_data["x"].values[::10]
        y = particle_data["y"].values[::10]

        # Calculate displacement from the initial position (t=0)
        x0, y0 = x[0], y[0]
        displacement = (x - x0) ** 2 + (y - y0) ** 2

        # Accumulate MSD values for each time step
        msds_per_run.append(displacement)

    # Average MSD over all runs
    min_length = min(len(run) for run in msds_per_run)
    msds_per_run = [run[:min_length] for run in msds_per_run]

    return np.mean(msds_per_run, axis=0), np.std(msds_per_run, axis=0)


# Plot MSD against time
def plot_msd(msd_mean, msd_std, steps):
    time = np.arange(0.0, steps * 0.0005 * 10, 0.0005 * 10)
    fig, ax = plt.subplots(figsize=(8, 6))
    ax.errorbar(time, msd_mean, yerr=msd_std, fmt="o", label="MSD", capsize=3)
    ax.set_xlabel("Time(s)", fontsize=20)
    ax.set_ylabel("Distance(m^2)", fontsize=20)
    ax.legend(loc="upper right", fontsize=20, bbox_to_anchor=(1.4, 1))
    ax.tick_params(axis="both", which="major", labelsize=20)
    ax.grid(True)
    ax.set_xlim(left=0, right=0.5)
    ax.set_ylim(bottom=0, top=0.003)
    plt.show()


# Main script
if __name__ == "__main__":
    particle_id = 300
    num_runs = 10
    num_steps = 100  # Assuming each run has 100 time steps

    # Calculate MSD for particle 300
    msd_mean, msd_std = calculate_msd(particle_id, num_runs, num_steps)
    # print(msd_mean)
    # print(msd_std)
    # Plot MSD vs Time
    plot_msd(msd_mean, msd_std, len(msd_mean))

    time = np.arange(0.0, len(msd_mean) * 0.0005 * 10, 0.0005 * 10)
    slope, intercept, r_value, p_value, std_err = stats.linregress(time, msd_mean)
    print(len(time))
    # El coeficiente de difusión es la pendiente dividida por 4
    D = slope / 4

    # Mostrar resultados
    print(f"Pendiente (4D): {slope}")
    print(f"Coeficiente de difusión D: {D}")

    # Graficar los datos y el ajuste lineal
    fig, ax = plt.subplots(figsize=(8, 6))
    ax.errorbar(time, msd_mean, fmt="o", label="MSD")
    ax.set_xlabel("Time(s)", fontsize=20)
    ax.set_ylabel("Distance(m^2)", fontsize=20)
    ax.tick_params(axis="both", which="major", labelsize=20)
    ax.plot(
        time,
        intercept + slope * time,
        "r",
        label=f"Linear Adjustment(4D = {slope:.5f})",
    )
    plt.legend(loc="upper right", fontsize=20, bbox_to_anchor=(2, 1))

    ax.set_xlim(left=0, right=0.5)
    ax.set_ylim(bottom=0, top=0.003)
    ax.grid(True)
    plt.show()
