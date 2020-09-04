## error-correcting-encoder-decoder

This program represents a set of functions used to simulate sending data across a network. There is no actual sending of data.

This is done by first encoding a piece of data, then sending it over the network and finally [the receiver] decoding that data (and comparing it across some form of validation to ensure data sent is data received). In real life, there is a chance of data corrupting or errors occurring during transmission (hence the need for validation).

Each function will output process to the console, the result will be stored to output file specified by user.

The course of action for sending data (and the available set of functions) are as follows:
- encode: The data is encoded using the [(7, 4)-Hamming Code](https://www.youtube.com/watch?v=2BI7wvmdFE8) and stored to the output file.
- send: Each byte of the input data is manipulated by flipping one random bit before being written to the output file. 
- decode: Since data is encoded with the (7,4)-Hamming Code, one error can be detected and fixed (other versions of the [Hamming code](https://en.wikipedia.org/wiki/Hamming_code), can be used to detect more one-bit errors). Here, for each byte, parity bits and their associated data bits are checked and the error is caught and fixed, before being written to output file.

#### *Running Program*
After compiling program, run `java Runner`.
The rest of the program will be interactive.

> It is necessary for there to be one text file already created, containing a message that will be encoded, sent & later decoded (if you choose to follow the recommended course of action). Of course, you don't need to follow the course of action but the output won't make too much sense.

Once the program starts, user will be met with prompted with `Enter a mode:` where they have a choice of **_encode_**, **_send_** or **_decode_**.

Once a choice is made, user will be prompted with a prompt a text file to read data from and a file to read result to.

Read prompt: `Enter a file to read:`

Write prompt: `Enter a file to write:`

**If either file is invalid, function will not run.**

##### URL: https://hyperskill.org/projects/58
