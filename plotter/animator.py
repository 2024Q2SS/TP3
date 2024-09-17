import csv
import json

import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Load configuration
with open("../config.json") as f:
    config = json.load(f)

# Extract configuration details
N = config["N"]  # Number of particles per frame
board_length = 0.1  # meters
particle_radius = 0.001  # meters
obstacle_radius = 0.005  # meters
obstacle_center = (board_length / 2, board_length / 2)  # middle of the board

# Read particle data from CSV
particles = []
with open("../DMDE/output.csv", "r") as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        particles.append(
            {
                "id": int(row["id"]),
                "x": float(row["x"]),
                "y": float(row["y"]),
            }
        )

# Prepare the figure and axis
fig, ax = plt.subplots()

# Plot the obstacle (static)
obstacle = plt.Circle(obstacle_center, obstacle_radius, color="red", alpha=0.5)
ax.add_artist(obstacle)

# Initialize particle circles (empty for now)
particle_circles = [
    plt.Circle((0, 0), particle_radius, color="blue", alpha=0.7) for _ in range(N)
]
for circle in particle_circles:
    ax.add_artist(circle)

# Set board dimensions
ax.set_xlim(0, board_length)
ax.set_ylim(0, board_length)

# Set equal scaling and grid
ax.set_aspect("equal", "box")
ax.grid(True)


# Function to update each frame
def update(frame):
    start_index = frame * N
    for i, circle in enumerate(particle_circles):
        if start_index + i < len(particles):
            particle = particles[start_index + i]
            circle.set_center((particle["x"], particle["y"]))
    return particle_circles


# Create animation
num_frames = len(particles) // N
ani = FuncAnimation(fig, update, frames=num_frames, blit=True)

# Automatically display the animation when plotting is complete
plt.xlabel("X Position (meters)")
plt.ylabel("Y Position (meters)")
plt.title("Particle Distribution Animation")

# Save animation to file (GIF and MP4)
ani.save("particle_simulation.gif", writer="pillow", fps=30)
ani.save("particle_simulation.mp4", writer="ffmpeg", fps=30)

# Show the plot (optional if you just want to save the file)
# plt.show()
