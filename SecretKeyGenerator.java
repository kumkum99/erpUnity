import java.security.Key;
import java.util.Base64;

import javax.crypto.KeyGenerator;
public class SecretKeyGenerator {
    public static void main(String[] args) throws Exception {
        // Generate a secure random key
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // Use 256 bits for HMACSHA256
        Key key = keyGen.generateKey();

        // Encode the key in Base64
        String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated JWT Secret Key: " + secretKey);
    }

}
