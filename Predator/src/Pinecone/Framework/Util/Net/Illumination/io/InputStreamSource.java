package Pinecone.Framework.Util.Net.Illumination.io;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
