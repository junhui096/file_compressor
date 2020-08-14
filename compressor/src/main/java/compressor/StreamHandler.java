package compressor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class StreamHandler {
    Inflater inflater;
    Deflater deflater;

    public StreamHandler(Inflater inflater, Deflater deflater) {
        this.inflater = inflater;
        this.deflater = deflater;
    }

    public OutputStream getCompressedOutputStream(File file) {
        OutputStream in;
        try {
            in = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
        if (this.deflater == null){
            return new DeflaterOutputStream(in);
            
        } else {
            return new DeflaterOutputStream(in, this.deflater);
        }
    }

    public InputStream getDecompressedInputStream(File file) {
        InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
        if (this.inflater == null){
            return new InflaterInputStream(in);
        } else {
            return new InflaterInputStream(in, this.inflater);
        }
    }
    
    
}