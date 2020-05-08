import javax.swing.*;
import java.math.BigInteger;

public class decodeEncode {

    public static BigInteger[] encode(String text, int blocksize, boolean whichtoEncode) {

        BigInteger[] wholeCode;

        if (whichtoEncode) {
            text = GUI.clear(text);
            while (!(text.length() % blocksize == 0)) {
                text = text + "X";
            }

            char[] letters = textToChar(text);

            wholeCode = encode26(letters, blocksize, text.length());
        } else {
            while (!(text.length() % blocksize == 0)) {
                text = text + " ";
            }

            char[] letters = textToChar(text);

            wholeCode = encode256(letters, blocksize, text.length());
        }
        return wholeCode;

    }

    public static char[] textToChar(String text) {
        char[] letters = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            letters[i] = text.charAt(i);
        }
        return letters;
    }

    public static BigInteger[] encode256(char[] buchstaben, int blocksize, int textlength) {

        BigInteger code = BigInteger.ZERO;
        BigInteger[] wholeCode = new BigInteger[textlength / blocksize];

        for (int y = 0; y < textlength / blocksize; y++) {
            for (int x = 0; x < blocksize; x++) {
                code = code.add(BigInteger.valueOf(buchstaben[x + (blocksize * y)]).multiply(BigInteger.valueOf(256).pow(x)));
            }
            wholeCode[y] = code;
            code = BigInteger.ZERO;

        }

        return wholeCode;
    }

    public static BigInteger[] encode26(char[] buchstaben, int blocksize, int textlength) {


        BigInteger code = BigInteger.ZERO;
        BigInteger[] wholeCode = new BigInteger[textlength / blocksize];


        for (int y = 0; y < textlength / blocksize; y++) {
            for (int x = 0; x < blocksize; x++) {
                code = code.add((BigInteger.valueOf(buchstaben[x + (blocksize * y)]).subtract(BigInteger.valueOf(65))).multiply(BigInteger.valueOf(26).pow(x)));
            }
            wholeCode[y] = code;
            code = BigInteger.ZERO;

        }

        return wholeCode;
    }

    public static String decode(BigInteger[] encodedText, int blocksize, boolean whichtoEncode) {

        String decodedText;

        if (whichtoEncode) {
            decodedText = decode26(encodedText, blocksize);
        } else {
            decodedText = decode256(encodedText, blocksize);
        }

        System.out.println(decodedText);
        return decodedText;
    }

    public static String decode256(BigInteger[] encodedText, int blocksize) {

        String decodedText = "";
        BigInteger x;
        for (int i = 0; i < encodedText.length; i++) {
            x = encodedText[i];
            for (int y = 0; y < blocksize; y++) {
                decodedText = decodedText + ((char)(x.mod(BigInteger.valueOf(256)).intValue()));

                x = x.divide(BigInteger.valueOf(256));
            }
        }
        return decodedText;
    }

    public static String decode26(BigInteger[] encodedText, int blocksize) {

        String decodedText = "";
        BigInteger x;
        for (int i = 0; i < encodedText.length; i++) {
            x = encodedText[i];
            for (int y = 0; y < blocksize; y++) {
                decodedText = decodedText +  ((char)((x.mod(BigInteger.valueOf(26))).add(BigInteger.valueOf(65))).intValue());
                x = x.divide(BigInteger.valueOf(26));
            }
        }
        return decodedText;
    }

}



