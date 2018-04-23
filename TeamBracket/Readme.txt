This is a basic version of the GUI. 

Should make a Challenger class and redo a lot of the current stuff. Just use this project to take ideas from and make a simpler version next.

This is a backup file just incase anything goes wrong and we need something to turn in last second.

Makefile - (couldn't figure out how to do this)
-Tried doing this, may have added some random extra files just ignore for now
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



I tried getting Challenger class to work, I think I know how to do it now, probably something like this

Challenger() {

Label name;
TextField score;

Getters / setters. . .
}

This means could make a array of Challengers in Round class rather than teams array and scores array

Buttons were a big problem in this code because they clogged up the code making it hard to read. Need to make a Button array in Round class to fix this.

I used a gridpane do make bracket making indexing hard to use. Should probably use Hboxes and Vboxes to make it much simpler, but I don't have much experience with javaFX.

Right now manually did a lot of things making the code repetitive. Tried to automate this so it would work for 16,32,64. . . but I had some trouble. So currently there are a bunch of if() statement cases which only goes up to 32.

Small bugs like you must fill out the all outer textFields before you can get a winner.

Pretty sure Deb said she would tell us how to make buttons easier in class on Tuesday, so don't worry about it for now.



