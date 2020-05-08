import java.math.BigInteger;
import java.util.Random;


public class RSA {

    public static BigInteger[] RSA(BigInteger prime1, BigInteger prime2) {

        BigInteger n = prime1.multiply(prime2);
        BigInteger phin = (prime1.subtract(BigInteger.ONE)).multiply(prime2.subtract(BigInteger.ONE));

        Random randNum = new Random();

       int bitlength = phin.bitLength();

        BigInteger e = new BigInteger(bitlength, randNum );
        BigInteger[] rückgabeArr = EEA.EEA(phin, e);
        while (!rückgabeArr[2].equals(BigInteger.ONE) && e.compareTo(phin.subtract(BigInteger.ONE)) > 0) {
            e = new BigInteger(bitlength, randNum );
            rückgabeArr = EEA.EEA(phin, e);
        }

        if (rückgabeArr[1].compareTo(BigInteger.ZERO) == -1) {
            rückgabeArr[1] = rückgabeArr[1].mod(phin);
        }

        BigInteger[] Schlüssel = {e, rückgabeArr[1], n};
        return Schlüssel;

    }

}
