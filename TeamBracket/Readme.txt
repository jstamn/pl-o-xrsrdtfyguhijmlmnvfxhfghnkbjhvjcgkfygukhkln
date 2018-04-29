Makefile - works in src folder

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
