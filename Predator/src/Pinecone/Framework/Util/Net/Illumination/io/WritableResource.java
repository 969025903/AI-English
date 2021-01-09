package Pinecone.Framework.Util.Net.Illumination.io;

import java.io.IOException;
import java.io.OutputStream;

public interface WritableResource extends Resource {
    boolean isWritable();

    OutputStream getOutputStream() throws IOException;
}