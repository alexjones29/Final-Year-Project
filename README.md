# Final-Year-Project
Cracking the Zodiac killer's cipher using a hill-climbing algorithm

# There are two programs for this project, the main one is for general purposes such as the Z340. The second is solely for the Z408.
# The main program can be found in the 'Final-Year-Project' directory whilst the 408 specific program can be found in the '408' directory.

Main program information
=========================

# Key Directories and files for the main program
Final-Year-Project\CipherCracker\resources contains the following key files and directories:

	- '\attempts' when the program is ran, the first solution that has a score above the given threshold is written
		directory, named after its score and containing its attempted decryption.
	- '340' this file contains the Z340 cipher.
	- 'bigramfrequencies' contains the bigram frequencies that the program uses
	- 'cribs' this contains the crib words to be used. To use any cribs in the program enter words in this file. One word per line.
	- 'dictionary' this is the dictionary that the program uses.
	- 'encryptedpassage' - decryption' this contains the decryption of the example cipher used.
	- 'encryptedpassage' this contains the example cipher
	- 'letters' this contains the english letters and frequencies used.
	- 'scores' the program writes out the score everytime a new best score is found or a restart occurs as the algorithm runs,
		in the form 'score : timestamp'. The timestamp represents the total time it has been running in seconds.
	- 'trigramfrequencies' containts the trigram frequencies that the program uses.

# Running the main program

# From the command line:

To run the main program from the command line, navigate to the \Final-Year-Project\CipherCracker directory. 

Then run the CipherCracker.jar from the command line, giving the cipher to break and a score threshold. The optional third parameter is a seed that can be used.
The cipher parameter must be a text file in the '/resources' directory. Only the filename needs to be given, not the file extension. An example can be seen below:

	java -jar CipherCracker.jar encryptedpassage 180    

Where encryptedpassage is the cipher file and 180 is the scorethreshold. A recommended score threshold is somewhere between 175 and 190.
The '340' file can be given as a command line argument to run the program on that cipher instead.

To enter crib words, edit the 'cribs' file in the '/resources' sub-directory. One word per line.

# From eclipse
First import the project and then run the 'RunApplication.java' class. First change the program arguments in the run 
configuration, to contain the file and threshold (and optionally a seed).
Example arguments are:

	340 175
Where 340 is the Z340 cipher and 175 is the score threshold.

#Output
Every time the program is run it will write out its scores with time intervals in seconds to the 'scores' file. 
It is recommended before you run the program to delete the contents. As a new run will append the new runs scores 
and time to the end of the file. The attempt of the solution will be written to the '/attempts' sub-directory, named after 
the best solutions score and containing its decryption. The last score at the bottom of the 'scores' file will be the
same as its the name of its decryption file in the '/attempts' directory.

It is recommended that the contents of the scores file is deleted after each run or the file itself renamed if preservation of its contents are desired.



408 information
================

# Unique files for the 408 program are the following:
\408\Final-Year-Project\CipherCracker\resources

	- '408cipher' contains the Z408 cipher
	- '408dictionary' contains the Z408 dictionary used 
	- '408letters' contains the letters and their frequencies used for this program
	- 'fixedLetters' contains the hard-coded symbols for this program
	- 'translation' contains all the symbols and their decryption
		
# Running the 408 program

#From the command line
Running from the command line is similar to the main program, with the exception of one less argument and a different jar name.
The cipher is not needed to be given as it is always the '408cipher'. The score threshold needs to be given,
the seed is optional again. For example after navigating to '\408\Final-Year-Project\CipherCracker':

	java -jar 408CipherCracker.jar 230
where 230 is the score threshold.

A recommended score threshold for this program is between 220 and 240.

# From eclipse
First import the project and then run the 'RunApplication.java' class. First change the program arguments in the
run configuration, to contain the threshold (and optionally a seed).
Example arguments are:

	230
Where 230 is the score threshold.

# The output works the same as for the main program. See above.














