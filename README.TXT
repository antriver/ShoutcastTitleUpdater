Shoutcast Title Updater v2.0

This tool is used to send the send the contents of a text file to a Shoutcast server as the current song. It is designed for use with the BCX playout software to take the “single line output file” (SLOF.txt) it generates.

Usage:
java -jar SLOF.jar shoutcast_IP:port shoutcast_password path_to_slof

e.g.
java -jar SLOF.jar 127.0.0.1:8000 changeme “C:\Program Files\BCX\SLOF.txt”