package Pinecone.Framework.Util.Net.Illumination.MultipartFile;

import Pinecone.Framework.Util.Net.Illumination.http.HttpHeaders;
import Pinecone.Framework.Util.Net.Illumination.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

public interface MultipartHttpServletRequest extends HttpServletRequest, MultipartRequest {
    HttpMethod getRequestMethod();

    HttpHeaders getRequestHeaders();

    HttpHeaders getMultipartHeaders(String var1);
}
