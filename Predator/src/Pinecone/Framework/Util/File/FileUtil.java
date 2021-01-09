package Pinecone.Framework.Util.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static byte[] readByteAll( File pFile ) throws IOException {
        Long fileLength = pFile.length();
        byte[] fileContent = new byte[ fileLength.intValue() ];

        FileInputStream fileInputStream = new FileInputStream(pFile);
        int nRealReadied = fileInputStream.read(fileContent);
        fileInputStream.close();

        if ( nRealReadied != fileLength.intValue() ) {
            throw new IOException( "Read all content failed !" );
        }
        return fileContent;
    }

    public static String readAll ( String szFileDir ) throws IOException {
        File file = new File(szFileDir);
        return readAll(file);
    }

    public static String readAll ( String szFileDir, Charset charset ) throws IOException {
        File file = new File(szFileDir);
        return readAll( file, charset );
    }

    public static String readAll ( File pFile, Charset charset ) throws IOException {
        return new String( readByteAll( pFile ), charset );
    }

    public static String readAll ( File pFile ) throws IOException {
        return new String( readByteAll( pFile ), StandardCharsets.UTF_8 );
    }

}
