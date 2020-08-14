package compressor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

public class Decompressor {

    static void decompress(String inputPath, String outputPath) throws IOException, ClassNotFoundException {
        StreamHandler handler  = new StreamHandler(null, null);
        for (Map.Entry<String, ArrayList<String>> entry : MetaDirectory.getDirectoryIndex(inputPath).entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();

            File decompressedFile = new File(outputPath + "/" + key);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(decompressedFile));

            for(String compressedFile : value) {
                InputStream inputStream = handler.getDecompressedInputStream(new File(inputPath + "/" + compressedFile));
                int b = 0;
                while((b = inputStream.read())!= -1) {
                    outputStream.write(b);
                }
                inputStream.close();
            }
            outputStream.flush();
            outputStream.close();
        }
    }
}
