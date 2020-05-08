

import java.math.BigInteger;
import java.util.ArrayList;


public class decryptEncrypt {

    public static String decrypt(BigInteger[] key, String encryptedText, int charsInABlock, boolean encode26) {

        String[] encryptedTextArr = encryptedText.split("\n");


        BigInteger[] encryptedTextArrBigInt = new BigInteger[encryptedTextArr.length];
        BigInteger[] decryptedTextArrBigInt = new BigInteger[encryptedTextArr.length];

        for (int i = 0; i < encryptedTextArr.length; i++) {
            encryptedTextArrBigInt[i] = new BigInteger(encryptedTextArr[i]);


        }


        for (int i = 0; i < encryptedTextArr.length; i++) {
            decryptedTextArrBigInt[i] = encryptedTextArrBigInt[i].modPow(key[1], key[2]);
        }

        String klartext;

        klartext = decodeEncode.decode(decryptedTextArrBigInt, charsInABlock,encode26);

        return klartext;


    }

    public static String encrypt(BigInteger[] key, String text, int charsInABlock, boolean encode26) {

        String encryptedText;

        BigInteger[] textArrInt = decodeEncode.encode(text, charsInABlock, encode26 );

        ArrayList<BigInteger> encodedText = new ArrayList<>();

        for (int i = 0; i < textArrInt.length; i++) {
            encodedText.add(textArrInt[i]);
        }

        ArrayList<BigInteger> encryptedTextList = new ArrayList<>();

        for (int i = 0; i < textArrInt.length; i++) {
            BigInteger value = encodedText.get(i);
            encryptedTextList.add(value.modPow(key[0], key[2]));
        }

        encryptedText = String.valueOf(encryptedTextList.get(0).longValue());
        for (int i = 1; i < encryptedTextList.size(); i++) {
            encryptedText = encryptedText + "\n" + encryptedTextList.get(i);
        }

        return  encryptedText;
    }

}



















