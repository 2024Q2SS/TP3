import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

delta_t = 0.005
particle_id = 300


def main():
    positions = []
    msd = []

    for i in range(1, 10):
        filepath_out = f"../DMDE/outputs/output_{i}.csv"
        filepath_ev = f"../DMDE/events_movable/events_{i}.csv"
        positions = get_positions(filepath_out)


if __name__ == "__main__":
    main()
