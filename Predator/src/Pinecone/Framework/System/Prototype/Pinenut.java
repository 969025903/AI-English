package Pinecone.Framework.System.Prototype;

public interface Pinenut {
    Prototype prototype();

    String  prototypeName();

    boolean isPrototypeOf( Prototype that );

    String toJSONString();


}
