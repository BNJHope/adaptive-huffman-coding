# Adaptive Huffman Coding
Java implementation of adaptive Huffman coding using the Vitter algorithm - this was a practical I did as a part of the data
encoding module in first semester of 3rd year.

Build
---------------------
The project includes a build.xml file for the Apache ant tool. To build the project into a Java archive (.jar) format, run this
in the project directory :
```sh
$ ant jar
```
Run
---------------------
The project includes options to both encode and decode. To run the encoder on a file "test.txt" with optimal compression results (8-bit codes
used in leaf nodes on the tree), first build the project and then run
```sh
$ java -jar AdaptiveHuffmanCoding.jar -e test.txt 8
```
To break down the parameters, "-e" is a flag used to signify encoding, the filename is the file to encode, and the "8" signifies
8-bit codes used.

To run the decoding process on the file which was output from the encoding process, test.compressed8.txt, run
```sh
$ java -jar AdaptiveHuffmanCoding.jar -d test.compressed8.txt
```

If you do want to experiment with the different outputs achieved from varying different code lengths (which was an intention of
this project), the argument at the end of the encoding command (the "8") can be replaced by 2 and 4, signifying 2 bit and 4 bit
codes respectfully. These output "*.compressed2.*" and "*.compressed4.*" filenames depending on what the selection was.
