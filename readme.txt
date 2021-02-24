----------------------   INFO  ----------------------

The Markov Chain
(C)2020 Rose Awen

Installer created using NSIS
Programmed using IntelliJ

-------------------- INSTRUCTIONS -------------------

To run, open the 'RUN TheMarkovChain' shortcut from the start menu (in the folder 'RoseAwen'), or search 'TheMarkovChain' in the start menu.

Place training data in 'training_data.txt'. Each string/word should be on a new line.

When ran, some debug information will be printed to the terminal, then you will be able to give user input.

Entering a word longer than 2 characters/letters will cause the program to use the input as the inital characters for the generated word.
Entering a word shorter than 2 characters/letters, or entering nothing at all, will cause the program to randomly select the initial characters.
Entering 'exit' will close the program.

(press ENTER after you have entered what your input)

Initial training data is a set of names from the Old Red conlang, created by members of the Flairwars Red team.

-------------------- REQUIREMENTS -------------------

REQUIRES JRE 8

If during installation the installer requested that you install JRE 8, please do so. It can be found here:
https://www.oracle.com/uk/java/technologies/javase-jre8-downloads.html

The RUN link expects to find the "java.exe" executable in the following path: C:\Program Files\Common Files\Oracle\Java\javapath\java.exe
If this is not the case, the software can be ran with the following command from the folder this file is in: java com.company.Application
This requires that the location of 'java.exe' is stored in the PATH variable.

--------------------- UNINSTALL ---------------------

An uninstaller can be found in the same directory as this readme file (in the installation directory).