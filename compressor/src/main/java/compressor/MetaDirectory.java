package compressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MetaDirectory {

    private HashMap<String, ArrayList<String>> map;
    private String outputDir;

    public MetaDirectory(String outputDir) {
        this.map = new HashMap<String, ArrayList<String>>();
        this.outputDir = outputDir;
    }

    public void add(String inputDir, String outputDir) {
        if (!this.map.containsKey(inputDir)) {
            this.map.put(inputDir, new ArrayList<String>());
        }
        this.map.get(inputDir).add(outputDir);

    }

    public void save() throws IOException {
        String outputName = this.outputDir + "/__meta__";
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputName));
        outputStream.writeObject(this.map);
        outputStream.close();
    }

    public static HashMap<String, ArrayList<String>> getDirectoryIndex(String outputDir) throws IOException,
            ClassNotFoundException {
        FileInputStream f = new FileInputStream(new File(outputDir + "/__meta__"));  
        ObjectInputStream s = new ObjectInputStream(f);          
        HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) s.readObject();
        s.close();
        return map;
    }





    
}