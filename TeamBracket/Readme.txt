update on canvas 

manifest.txt (only need to do this step once)
Create a text file named manifest.txt place in same directory as your java files.  
Or, you may copy our manifest.txt file to your source file directory.
Manifest-Version: 1.0
Created-By: deppeler
Main-Class: application.Main
executable.jar (must repeated this step when program code changes)
launch terminal window
navigate to your source file folder (project folder or project/src folder if you have one)
compile your program:                  javac application/*.java
create executable.jar: 
If your java files are in a separate src folder, use:
             jar -cmf manifest.txt ../executable.jar application
If your java files are not in a separate src folder, use:
              jar -cmf manifest.txt executable.jar application
tournament.zip (must be repeated if anything changes)
Create zip file as described here so that it includes all file inside your project, but not your project directory file.
cd project (go to project folder not src folder if you have one)
zip -r tournament .
Check that your tournament.zip file contains the following structure (must repeat if anything changes)
shows application or src folder and exectuable.jar as well as other image files
Upload tournament.zip (this file should be in your project folder - not src folder) (Must repeat this step if anything 


Makefile - works in src folder, just had to copy BracketGUI class to Main class

ex
Schmidts-Air:src user$ ls
Makefile	teams04.txt	teams16.txt	teams00.txt
application	teams01.txt	teams02.txt	teams08.txt	teams32.txt
Schmidts-Air:src user$ make
javac application/*.java
Schmidts-Air:src user$ make teams02
java application.Main teams02.txt > teams02.log
Schmidts-Air:src user$ make teams16
java application.Main teams16.txt > teams16.log
Schmidts-Air:src user$ make clean
\rm application/*.class
Schmidts-Air:src user$ 


-Also I couldn't add "teams00.txt" to Github just an empty file
