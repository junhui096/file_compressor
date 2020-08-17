package compressor;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static long getFolderSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            }
            else {
                size += getFolderSize(file);
            }
        }
        return size;
    }

    boolean isEqual(File file1, File file2) throws IOException {
        BufferedInputStream inputStream1 = new BufferedInputStream(new FileInputStream(file1));
        BufferedInputStream inputStream2 = new BufferedInputStream(new FileInputStream(file2));
        int b;
        while ((b = inputStream1.read()) != -1) {
            if (b != inputStream2.read()) {
                return false;
            }
        }
        boolean result = inputStream2.read() == -1;
        inputStream1.close();
        inputStream2.close();

        return result;
    }

    static void deleteFolder(File file){
        for (File subFile : file.listFiles()) {
           if(subFile.isDirectory()) {
              deleteFolder(subFile);
           } else {
              subFile.delete();
           }
        }
        file.delete();
    }

    /**
     * clear Rigorous Test :-)
     * 
     * @throws IOException
     */
    @Test
    public void InputToCompressorShouldMatchOutput() throws IOException {
        File dir = new File("input/dir1");
        dir.mkdirs();

        File file1 = new File("input/file1.txt");
        File file2 = new File("input/dir1/file2.txt");

        file1.createNewFile();
        file2.createNewFile();

        OutputStream outputStream1 = null;
        OutputStream outputStream2 = null;

        try {
            outputStream1 = new BufferedOutputStream(new FileOutputStream(file1));
            outputStream2 = new BufferedOutputStream(new FileOutputStream(file2));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for (int i = 0; i < 1000000000; i++) {
            outputStream1.write('a');
            outputStream2.write('a');
        }


        try {
            Compressor.compress("input", "output", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(getFolderSize(new File("input")) > getFolderSize(new File("output")));

        for (File file : new File("output").listFiles()) {
            if(file.isFile()) {
                assertTrue(file.length() < 1000000);
            }
        }
        for (File file : new File("output/dir1").listFiles()) {
            assertTrue(file.length() < 1000000);
        }

        try {
            Decompressor.decompress("output", "input2");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(new File("input2/dir1").exists());
        assertTrue(new File("input2/file1.txt").exists());
        assertTrue(new File("input2/dir1/file2.txt").exists());

        assertTrue(isEqual(new File("input2/file1.txt"), new File("input/file1.txt")));
        assertTrue(isEqual(new File("input2/dir1/file2.txt"), new File("input/dir1/file2.txt")));

        deleteFolder(new File("input"));
        deleteFolder(new File("input2"));
        deleteFolder(new File("output"));

    }
}
