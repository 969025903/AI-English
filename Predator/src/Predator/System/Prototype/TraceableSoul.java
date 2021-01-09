package Predator.System.Prototype;

import Pinecone.Framework.Debug.Console;

public interface TraceableSoul {
    Console trace (Object data, Object ...more );

    void spit  ( Object data, Object ...more );

    void vomit ( Object data, Object ...more );
}
