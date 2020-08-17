# file_compressor
Package to compress files in Java. Each compressed file generated will be restricted to a certain size.
The package provides the utility to reverse this compression operation.

Setup:
```
cd compressor
mvn compile

```

For compression:

```
mvn exec:java -Dexec.args="<inputPath> <outputPath> <maxSize per file>"

```

For decompression:

```
mvn exec:java -Dexec.args="< inputPath> <outputPath>"

```

To execute tests:
```
mvn test

```