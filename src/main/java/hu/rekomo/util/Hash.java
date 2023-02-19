package hu.rekomo.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 *
 * @author futo
 */
public class Hash {
    static Argon2 argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id,
                16,
                32);
    public static String hash(String plaintext) {
        

        char[] password = plaintext.toCharArray();
        String hash = argon2.hash(3, // Number of iterations
                64 * 1024, // 64mb
                2, // how many parallel threads to use
                password);
        return hash;
    }
    public static Boolean verify(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}
