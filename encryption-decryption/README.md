## encryption-decryption

This is a program that allows a user to encrypt and decrypt messages using the Caesar cipher.  
You can find out more [here](http://practicalcryptography.com/ciphers/caesar-cipher/), essentially
it is an encryption algorithm that consists of shifting the letters of an alphabet for a set number
of keys. For a sender & receiver to effectively communicate, they need to know the alphabet in use 
as well as the number of letters shifted.  
It isn't an effective cipher these days, so it's not something
that should be used in real life environments.

The user can shift the characters for any integer amount of keys, including with negative numbers.
The key is specified with the **-key** flag. If there is no key specified, shift no characters.

The user is allowed 2 variations for the algorithm (which is specified with **-alg** flag):
- _shift_: Only letters will shift (if no algorithm specified, this is chosen)
- _unicode_: Characters from values 32-126 in this [table](https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html)

The message to modify can be passed in by user as an argument (passed with **-data** flag) or  
in a file that the program will go through (passed with **-in** flag). If neither flag is provided,  
then an empty string will be used. If both are provided, then **-data** takes precedence over **-in**.

The resulting data can either be displayed in the standard output (right on the console output) or  
written into a file. The output file is specified with the **-out** flag & if no flag is given then the result 
will be outputted on the screen.

#### *Running Program*

After compiling files, run program with:  
`java Runner [-mode enc|dec] [-key <integer>] [-data <String>] [-in <filename>] [-out <filename>]`

##### URL: https://hyperskill.org/projects/46