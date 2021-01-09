package Pinecone.Framework.Util.Net.Illumination.prototype;

import Pinecone.Framework.System.Prototype.Ally;
import Pinecone.Framework.Util.JSON.JSONArray;
import Pinecone.Framework.Util.JSON.JSONObject;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  Bean Nuts Pinecone PineconeJava Illumination - Wizard
 *  ****************************************************************************************************************
 *  Illumination: JSON Based Java Servlet [C/C++ Style]
 *  Matrix: Bean Nuts Pinecone C/CPP Runtime Framework Extension Fast CGI Servlet Illumination (JSON Based MVC)
 *  Notice: Pinecone is base on JSON,
 *  Notice: All functions or methods are based on JSON. We highly recommend you using JSON as data format, it is
 *          easy to compatible with JS, PHP and other platforms.
 *  Notice: For sub modular extends this interface is necessary. Add any function if your json config haves.
 *  ****************************************************************************************************************
 */
public interface Wizard extends Ally {
    String prototypeName();

    String getTitle();

    JSONObject getPrivateTables();

    JSONObject getModularConfig();

    String getModularRole();

    int getModularRoleIndex();

    JSONArray getMyNaughtyGenies();

    String getWizardCommand();

}
