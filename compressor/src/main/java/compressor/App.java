package compressor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NumberFormatException, Exception
    {
        if(args.length == 3) {
            Compressor.compress(args[0], args[1], Integer.parseInt(args[2]));
        } else {
            Decompressor.decompress(args[0], args[1]);
        }
    }

}
