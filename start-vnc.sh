#!/bin/bash

# Start virtual display
Xvfb :1 -screen 0 1024x768x16 &

# Start window manager
DISPLAY=:1 fluxbox &

# Start VNC server
x11vnc -display :1 -nopw -listen 0.0.0.0 -xkb -ncache 10 -ncache_cr -forever &

# Start noVNC web interface
/opt/novnc/utils/launch.sh --vnc localhost:5900 --listen $PORT &

# Start Java application
DISPLAY=:1 java -jar app.jar &

# Keep container running
wait