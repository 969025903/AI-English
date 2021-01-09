package Predator.Util;

import Predator.System.Predator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PredatorCipherHelper extends PredatorHelper {
    protected Cipher mCipher = null; /** AES128 CTR PKCS5 **/

    protected byte[]   mRawKey = null;

    protected SecretKeySpec mKeySpec = null;

    protected IvParameterSpec mShardIvParameterSpec = null;

    private void initCipher(){
        try {
            this.mCipher = Cipher.getInstance( "AES/CTR/PKCS5Padding");
        }
        catch ( NoSuchAlgorithmException | NoSuchPaddingException e ){
            e.printStackTrace();
        }
        String szKey = this.getInner16Key();
        if ( szKey.length() == 16 ) {
            this.mRawKey = szKey.getBytes( StandardCharsets.UTF_8 );
            this.mKeySpec = new SecretKeySpec( this.mRawKey, "AES" );
            this.mShardIvParameterSpec = new IvParameterSpec( this.mRawKey );
        }
    }

    public PredatorCipherHelper( Predator matrix ){
        this.init( matrix );
        this.initCipher();
    }



    public Cipher getCipher() {
        return this.mCipher;
    }

    public String getInner16Key() {
        return this.mMatrix.get16BitInnerPassword();
    }

    public String simpleEncrypt( byte[] data ) {
        if( this.mRawKey == null || this.mCipher == null ){
            return null;
        }

        try{
            this.mCipher.init( Cipher.ENCRYPT_MODE, this.mKeySpec, this.mShardIvParameterSpec );
            byte[] encrypted = this.mCipher.doFinal( data );
            return PredatorHelper.base64Encode( encrypted );
        }
        catch ( InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e ){
            e.printStackTrace();
            return null;
        }
    }

    public byte[] simpleDecrypt( String szBase64Data ) {
        if( this.mRawKey == null || this.mCipher == null ){
            return null;
        }

        try {
            this.mCipher.init( Cipher.DECRYPT_MODE, this.mKeySpec, this.mShardIvParameterSpec );
            return this.mCipher.doFinal( PredatorHelper.base64Decode( szBase64Data ) );
        }
        catch ( InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e ){
            e.printStackTrace();
            return null;
        }
    }

}
