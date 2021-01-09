package Predator.System.Prototype;

import Pinecone.Framework.Util.Net.Illumination.prototype.WizardSoul;

import javax.servlet.ServletException;
import java.io.IOException;

public interface JasperYokedSoul extends WizardSoul {
    void setFertilizerRole( boolean bRole );

    boolean isJasperYoked();

    String jspTPLRender( String szJSPSimpleName ) throws ServletException, IOException;

    boolean prospectIsDefaultFertilizer();

    void appendDefaultAttribute(String key, Object value);

}
