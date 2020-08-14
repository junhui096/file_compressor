package compressor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class Compressor {

    static void compress(String inputPath, String outputPath, int maxSizePerFile) throws Exception {
        MetaDirectory meta = new MetaDirectory(outputPath);
        StreamHandler handler  = new StreamHandler(null, null);
        File inputDirectory = new File(inputPath);
        if (!inputDirectory.exists()) {
            throw new Exception("Invalid input string");
        } else {
            Compressor.compressDirectory(inputDirectory, outputPath, handler, maxSizePerFile * 1000000, meta);
        }
        meta.save();
    }
        
    private static void compressDirectory(File dirToParse, String outputDir, StreamHandler handler, int maxSizePerFile, MetaDirectory meta)
            throws IOException {
        File dir = new File(outputDir);

        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Error creating output directory. Please validate the output path.");
        }
        for(File file: dirToParse.listFiles()) {
            if (file.isFile()) {
                Compressor.compressFile(file, outputDir + "/" + file.getName(), handler, maxSizePerFile, meta);
            } else {
                Compressor.compressDirectory(file, outputDir + "/" + file.getName(), handler, maxSizePerFile, meta);
            }
        }
    }

    static void compressFile(File inputFile, String outputDir, StreamHandler handler, int maxSizePerFile, MetaDirectory meta)
            throws IOException {
        String outputName = outputDir + "_" + "file";
        OutputStream outputStream = handler.getCompressedOutputStream(new File(outputName));
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile));
        int numBytesRead = 0;
        int b = 0;
        while ((b = inputStream.read())!= -1) {
            outputStream.write(b);
        }
        meta.add(inputFile.getPath(), outputName);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        // Written the compressed output to a single file named outputName

        int fileNo = 0;
        File compressedOutputFile = new File(outputName);
        inputStream = new BufferedInputStream(new FileInputStream(compressedOutputFile));

        // Write compressed output to a set of files
        outputName = outputDir + "_" + "file" + fileNo;
        outputStream = new BufferedOutputStream(new FileOutputStream(outputName));
        while(true) {
        while (numBytesRead <  maxSizePerFile && (b = inputStream.read())!= -1) {
            numBytesRead += 1;
            outputStream.write(b);
        }
        outputStream.flush();
        outputStream.close();
        meta.add(inputFile.getPath(), outputName);
        if (b == -1) {
            compressedOutputFile.delete();
            inputStream.close();
            break;
        } else {
            fileNo += 1;
            outputName = outputDir + "_" + "file" + fileNo ;
            outputStream = new BufferedOutputStream(new FileOutputStream(outputName));
            numBytesRead = 0;
        }
    }
    }
}