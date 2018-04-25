This is a backup file just incase anything goes wrong and we need something to turn in last second.

Makefile - (couldn't figure out how to do this)
-Also I couldn't add "teams00.txt" to Github just an empty file


	javac *.java
    
teams00: 
	java Main teams00.txt > teams00.log
        
teams01: 
	java Main teams01.txt > teams01.log
    
teams02: 
	java Main teams02.txt > teams02.log
    
teams04: 
	java Main teams04.txt > teams04.log
    
teams08: 
	java Main teams08.txt > teams08.log
    
teams16: 
	java Main teams16.txt > teams16.log

teams32: 
	java Main teams32.txt > teams32.log
    
clean:
	\rm *.class
