import json
import csv
import matplotlib.pyplot as plt

# Load configuration
with open('../config.json') as f:
    config = json.load(f)

# Extract configuration details
N = config['N']
board_length = 0.1  # meters
particle_radius = 0.001  # meters
obstacle_radius = 0.005  # meters
obstacle_center = (board_length / 2, board_length / 2)  # middle of the board

# Read particle data from CSV
particles = []
with open('../DMDE/output.csv', 'r') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        particles.append({
            'id': int(row['p_id']),
            'x': float(row['p_x']),
            'y': float(row['p_y']),
        })

# Plotting the board and particles
fig, ax = plt.subplots()

# Plot the obstacle
obstacle = plt.Circle(obstacle_center, obstacle_radius, color='red', alpha=0.5)
ax.add_artist(obstacle)

# Plot the particles
for particle in particles:
    particle_circle = plt.Circle((particle['x'], particle['y']), particle_radius, color='blue', alpha=0.7)
    ax.add_artist(particle_circle)

# Set board dimensions
ax.set_xlim(0, board_length)
ax.set_ylim(0, board_length)

# Set equal scaling and grid
ax.set_aspect('equal', 'box')
ax.grid(True)

# Show the plot
plt.xlabel('X Position (meters)')
plt.ylabel('Y Position (meters)')
plt.title('Particle Distribution on Board')
plt.show()

