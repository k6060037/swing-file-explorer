# swing-file-explorer
This is a useful program for performing a time-consuming mechanical task:

At my job, several times a day I have to look for the necessary documents by part numbers in the folder with the technical documents - 
there are more than 150 thousand documents in the folder, and I need to pull out 50-100 documents every day. 
This program performs this function for me in seconds: it reads the list of part numbers, runs through the catalog with technical documentation and subdirectories, 
selects the necessary files, copies them to neccessary folder and adds #index number (from one to ...) to filenames.

If several files are suitable for one party number, the program selects the most recent file. If there is no file, at the end of the procedure I am informed about it, I make a new document, add it to technical documents and next time this number is already taken into account in the search. Complete processing of 50 files takes about 30 seconds.

The program has a primitive error protection: if the fields are empty or write something else instead of a path, the program will warn you about it.
![image](https://user-images.githubusercontent.com/53838650/127382277-4b7f3c3c-4071-4247-a6df-d9392dbe328b.png)

