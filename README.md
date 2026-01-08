# Voxel Engine

📦 **Repository:** https://github.com/Extrajulien/voxel-engine

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

## Overview

**Voxel Engine** is a Minecraft-inspired voxel rendering engine written in **Java** using **LWJGL**.  
The project focuses on rendering architecture, chunk management, and performance-oriented design rather than full gameplay features. It is a technical exploration of engine-level systems and real-time rendering fundamentals.

This codebase represents a working engine prototype with a complete, clean structure, suitable for further experimentation and extension.

## Features

- Chunk-based voxel world structure
- Efficient mesh generation and rendering
- Camera control and basic world interaction
- Modular architecture for rendering and world systems
- Clear separation of engine components

## Status

The current implementation is functional and stable for its core scope. Additional features may be added over time, but the existing code represents a complete iteration of the engine’s core systems.

### Notes / Linux Users
- On Linux, this project currently requires **X11** due to a known GLFW bug. Wayland is not supported.
- To force **X11**, run:
```sh
sudo GDK_BACKEND=x11 gradle run
```

## Getting Started

### Requirements

- Java 17+ (or newer)
- LWJGL dependencies (bundled via included gradle setup)

### Build & Run

1. Clone the repository:
```sh
git clone https://github.com/Extrajulien/voxel-engine.git
```
2. navigate to project root:
```sh
cd voxel_engine
```
3. execute the program via `gradle run` 
```sh
  gradle run
```

# Screenshots

### Dirt house
- You can interact with the world with collisions and placing / destroying blocks.
<img width="1920" height="1009" alt="dirt_house" src="https://github.com/user-attachments/assets/b335fd81-921a-4e2f-9139-39247c9c8329" />

### Chunk border
- The world is split in chunks in all the axes to achieve **very** large worlds.
<img width="1920" height="1080" alt="chunk_border" src="https://github.com/user-attachments/assets/26b21d8d-8c6f-4c4d-8c6d-c98ccfd74742" />

### Pseudo random world gen
- This benchmark tested for generating a noise from -50 to 50 **y**. *(ignore the player model)*

<img width="1920" height="1080" alt="extreme_gen" src="https://github.com/user-attachments/assets/54e70153-12e1-4ef6-be43-84d8e72930e3" />

