Shoutcast Title Updater

This tool is used to send the send the contents of a text file to a Shoutcast server as the current song.
It fixes issues in certain playout software where the artist or title is duplicated in the text file.

Usage:
java -jar scupdater.jar shoutcast_IP:port shoutcast_admin_password path_to_text_file

e.g.
java -jar scupdater.jar 127.0.0.1:8000 changeme "C:\Program Files\Playout\output.txt"


Depending on your system, you may need to specify the full path to Java and the files. e.g. (in Windows):

"C:\Program Files (x86)\Java\jre7\bin\java.exe" -jar "C:\Users\Anthony\Desktop\scupdater.jar" 127.0.0.1:8000 changeme "C:\Program Files\Playout\output.txt"
