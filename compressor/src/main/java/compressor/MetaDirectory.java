package compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

class MetaDirectory {

    private final HashMap<String, ArrayList<String>> map;
    private final String outputDir;

    MetaDirectory(final String outputDir) {
        this.map = new HashMap<String, ArrayList<String>>();
        this.outputDir = outputDir;
    }

    void add(final String inputDir, final String outputDir) {
        if (!this.map.containsKey(inputDir)) {
            this.map.put(inputDir, new ArrayList<String>());
        }
        this.map.get(inputDir).add(outputDir);

    }

    void save() throws IOException {
        final String outputName = this.outputDir + "/__meta__";
        final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputName));
        outputStream.writeObject(this.map);
        outputStream.close();
    }

    static HashMap<String, ArrayList<String>> getDirectoryIndex(final String outputDir)
            throws IOException, ClassNotFoundException {
        final FileInputStream f = new FileInputStream(new File(outputDir + "/__meta__"));
        final ObjectInputStream s = new ObjectInputStream(f);
        final HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) s.readObject();
        s.close();
        return map;
    }





    
}