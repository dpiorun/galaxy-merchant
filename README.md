## Problem

See [CHALLENGE](CHALLENGE.md)

## How to build
First, compile the code and create the `.jar` file. To do that, in the project folder, in the terminal run:  
```console
javac -d galaxy-merchant/target/classes galaxy-merchant/src/main/java/galaxy/*.java 
jar cvfm galaxy-merchant.jar Manifest.MF -C galaxy-merchant/target/classes/ .   
```

## How to run
In the terminal open the directory, where you put the `.jar` file and run: 
```console
java -jar galaxy-merchant.jar
```

## Assumptions
The program:
* accepts just the terrestrial Latin alphabet.
* accepts notes and questions in a specific order: it accepts _"pish is X"_ but rejects _"X is pish"_, it answers _"how many Credits is glob Gold?"_ and does not know the answer for _"how many Credits is Gold, glob?"_.
* is case sensitive: it accepts information _"glob is I"_ but rejects _"glob is i"_, it answers the question _"how much is glob?"_ but not _"How much is glob?"_. Therefore word _"Credits"_ must be capitalized, roman numerals must be uppercase, rest of the text should be lowercase. Product names should be provided in the same pattern every time. 
* expects a question mark at the end of a question.
* sanitizes input from extra whitespaces. It accepts _"tegj is L"_ and _"&nbsp;&nbsp;tegj&nbsp;&nbsp;is&nbsp;&nbsp;&nbsp;&nbsp;L&nbsp;&nbsp;"_.  

## Usage
First, the program expects you to provide painstakingly collected notes.  
```console
Please provide a path to a file, by typing 'f:{path}' or provide a note in the console:
```  
You can provide a file with notes and questions. The program will inform you if the file doesn't exist. It accepts a relative path.  
```console
f:/computer/wrong_path_to_the_file/test-input.txt  
/computer/wrong_path_to_the_file/test-input.txt  (No such file or directory)  
f:test-input.txt  
pish tegj glob glob is 42  
glob prok Silver is 68 Credits  
glob prok Gold is 57800 Credits  
glob prok Iron is 782 Credits  
I have no idea what you are talking about  
```  
The program will wait for the next questions until you type `exit`.  
```console
how much is pish?  
pish is 10  
```  
```console
how many Credits is pish Silver?
pish Silver is 170 Credits
```  
The program doesn't answer accurately for every question.  
```console
how do you do?  
I have no idea what you are talking about  
```  
But it informs if it recognizes some misstatements.  
```console
how much is glob glob glob glob?  
Invalid numeral  
```
The program is case sensitive. It's very annoying and should definitely be improved in version 1.1:  
```console
How much is pish?  
I have no idea what you are talking about  
```