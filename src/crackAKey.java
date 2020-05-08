import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class crackAKey extends JFrame{


    private JButton crackButton;
    private JTextField nTextField;
    private JTextField eTextField;
    private JTextArea textArea1;
    private JPanel main;

    public static void main() {

        JFrame frame = new crackAKey();
        frame.setVisible(true);

    }


    public class crackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            crack();
        }
    }

    public crackAKey() {

        this.setContentPane(main);
        this.pack();

        crackButtonListener CBL = new crackButtonListener();
        crackButton.addActionListener(CBL);


     }

     public void crack(){

      if(nTextField.getText().isEmpty() || eTextField.getText().isEmpty()){
            textArea1.setText("Please enter e and n!");

      }else {


            BigInteger n = new BigInteger(nTextField.getText());
            BigInteger e = new BigInteger(eTextField.getText());

            BigInteger prime1 = primefactorization(n);
            BigInteger prime2 = n.divide(prime1);

            BigInteger[] key = RSA(prime1, prime2, e);

            textArea1.setText("Der öffentliche Schlüssel ist: x^" + key[0] + " mod " + key[2] + "\nDer private Schlüssel ist: x^" + key[1] + " mod " + key[2]);
      }
     }


    public static BigInteger[] RSA(BigInteger prime1, BigInteger prime2, BigInteger e) {

        BigInteger n = prime1.multiply(prime2);
        BigInteger phin = (prime1.subtract(BigInteger.valueOf(1))).multiply(prime2.subtract(BigInteger.valueOf(1)));


        BigInteger[] rückgabeArr = EEA.EEA(phin, e);

        if (rückgabeArr[1].compareTo(BigInteger.valueOf(0)) == -1) {
            rückgabeArr[1] = rückgabeArr[1].mod(phin);
        }

        BigInteger[] Schlüssel = {e, rückgabeArr[1], n};
        return Schlüssel;

    }

    public static BigInteger primefactorization(BigInteger num) {
       BigInteger numSqrt = BigInteger.valueOf((long)Math.pow(num.longValue(), 0.5));

        BigInteger counter = BigInteger.valueOf(2);

        while (numSqrt.compareTo(counter) >= 0 && num.compareTo(BigInteger.ONE) == 1 ) {
            if (num.mod(counter)  == BigInteger.ZERO) {
                num = num.divide(counter);

            } else {
                counter = counter.add(BigInteger.ONE);
            }

        }
        return num;

    }
}
