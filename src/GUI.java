import javax.swing.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class GUI extends JFrame {

    private JPanel mainPanel;
    private JPanel primePanel;

    private JTextArea decryptionTextArea;
    private JTextArea encryptionTextArea;

    private JTextPane keyOutputField;

    private JButton decryptButton;
    private JButton saveKeyButton;
    private JButton generateKeyButton;
    private JButton encryptButton;
    private JButton crackAKeyButton;
    private JButton getKeyButton;

    private JCheckBox keyExistsCheckbox;

    private JTextField charsInABlockTextField;
    private JTextField dTextField;
    private JTextField nTextField;
    private JTextField eTextField;
    private JTextField prime1;
    private JTextField prime2;

    private JRadioButton encode26;
    private JRadioButton encode127;

    private JTextField profileNameFiled;
    private JTextArea textArea1;

    private JComboBox keyComboBox;

    private BigInteger[] key = {BigInteger.valueOf(0), BigInteger.valueOf(0), BigInteger.valueOf(0)};

    String keyName = "";
    public ArrayList<String[]> keyList = new ArrayList<>(0);


    public int charsInABlock;


    public static void main(String[] args) {

        JFrame frame = new GUI();
        frame.setVisible(true);

    }

    public class GKButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            generateKey();

        }

    }

    public class decryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            decrypt();


        }
    }

    public class encryptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            encrypt();

        }
    }

    public class keyExsitsCheckboxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {

            if (keyExistsCheckbox.isSelected()) {
                generateKeyButton.setEnabled(false);
                primePanel.setVisible(false);
                generateKeyButton.setVisible(false);
                keyOutputField.setText("");


                eTextField.setEditable(true);
                dTextField.setEditable(true);
                nTextField.setEditable(true);


            } else if (!keyExistsCheckbox.isSelected()) {
                generateKeyButton.setEnabled(true);
                primePanel.setVisible(true);
                generateKeyButton.setVisible(true);
                eTextField.setText("");
                dTextField.setText("");
                nTextField.setText("");

                eTextField.setEditable(false);
                dTextField.setEditable(false);
                nTextField.setEditable(false);


            }

        }
    }

    public class saveKeyButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            if(profileNameFiled.getText().isEmpty()|| eTextField.getText().isEmpty()|| dTextField.getText().isEmpty()|| nTextField.getText().isEmpty()){
                textArea1.setText("Please enter all parameters!");

            }else if(!(GUI.isNumeric(eTextField.getText()) || GUI.isNumeric(dTextField.getText()) || GUI.isNumeric(nTextField.getText()))) {
                textArea1.setText("Please enter a real Code");

            } else{

               String[] keySave = new String[4];

                keyName = profileNameFiled.getText();

                keySave[0] = keyName;
                keySave[1] = eTextField.getText();
                keySave[2] = dTextField.getText();
                keySave[3] = nTextField.getText();

                keyList.add(keySave);

                keyComboBox.addItem(keyName);

            }

        }
    }

    public class getKeyButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(keyComboBox.getItemCount() == 0){
                textArea1.setText("Please save a key before!");
            }else{
              String keyName  = keyComboBox.getSelectedItem().toString();

              int i = 0;
              while (!(keyName.equals(keyList.get(i)[0]))){
                  i++;

              }

                eTextField.setText(keyList.get(i)[1]);
                dTextField.setText(keyList.get(i)[2]);
                nTextField.setText(keyList.get(i)[3]);

            }

        }
    }

    public class crackAKeyButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            crackAKey.main();
        }
    }

    public GUI() {

        super("Kryptograhpor");

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.pack();
        encode26.setSelected(true);
        this.setResizable(false);



        encryptionTextArea.setLineWrap(true);
        encryptionTextArea.setWrapStyleWord(true);


        keyExsitsCheckboxListener KECL = new keyExsitsCheckboxListener();
        keyExistsCheckbox.addItemListener(KECL);

        GKButtonListener GKBL = new GKButtonListener();
        generateKeyButton.addActionListener(GKBL);

        decryptButtonListener DBL = new decryptButtonListener();
        decryptButton.addActionListener(DBL);

        encryptButtonListener EBL = new encryptButtonListener();
        encryptButton.addActionListener(EBL);

        saveKeyButtonListener SBL = new saveKeyButtonListener();
        saveKeyButton.addActionListener(SBL);

        getKeyButtonListener GetKBL = new getKeyButtonListener();
        getKeyButton.addActionListener(GetKBL);

        crackAKeyButtonListener CAKBL = new crackAKeyButtonListener();
        crackAKeyButton.addActionListener(CAKBL);

    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public void generateKey() {

        if (prime1.getText().equals("") && prime2.getText().equals("")) {
            keyOutputField.setText("Please enter two prime numbers!");
        } else {

            String prime1String = prime1.getText();
            String prime2String = prime2.getText();


            key = RSA.RSA(BigInteger.valueOf(Integer.parseInt(prime1String)), BigInteger.valueOf(Integer.parseInt(prime2String)));

            BigInteger blocksize = BigInteger.valueOf(Integer.parseInt(charsInABlockTextField.getText()));

            if (key[2].compareTo(BigInteger.valueOf((long) (Math.pow(26, blocksize.intValue()) - 1))) == -1) {
                keyOutputField.setText("Please enter bigger prime two numbers!");
            } else {
                keyOutputField.setText(" Öffentlicher Schlüssel: x^" + key[0] + " mod " + key[2] + "\n Privater Schlüssel: x^" + key[1] + " mod " + key[2]);

                eTextField.setText(String.valueOf(key[0]));
                dTextField.setText(String.valueOf(key[1]));
                nTextField.setText(String.valueOf(key[2]));

            }

        }

    }

    public void decrypt() {

        if (!isNumeric(charsInABlockTextField.getText())) {
            encryptionTextArea.setText("Please enter a block size number!");
        } else {
            if (dTextField.getText().isEmpty() || nTextField.getText().isEmpty()) {
                encryptionTextArea.setText("Please enter a key!");
            } else {
                charsInABlock = Integer.parseInt(charsInABlockTextField.getText());

                key[1] = BigInteger.valueOf(Long.parseLong(dTextField.getText()));
                key[2] = BigInteger.valueOf(Long.parseLong(nTextField.getText()));

                boolean Encode26 = encode26.isSelected();

                if (key[1].equals(BigInteger.valueOf(0)) || key[2].equals(BigInteger.valueOf(0))) {
                    encryptionTextArea.setText("Please enter a key!");
                } else {


                    if (Encode26) {
                        if (key[2].compareTo(BigInteger.valueOf((long) (Math.pow(26, Long.parseLong(charsInABlockTextField.getText())) - 1))) == -1) {
                            smallKeyError();
                        } else {
                            encryptionTextArea.setText(decryptEncrypt.decrypt(key, decryptionTextArea.getText(), charsInABlock, Encode26));
                        }

                    } else {
                        if (key[2].compareTo(BigInteger.valueOf((long) (Math.pow(256, Long.parseLong(charsInABlockTextField.getText())) - 1))) == -1) {
                            smallKeyError();
                        } else {
                            encryptionTextArea.setText(decryptEncrypt.decrypt(key, decryptionTextArea.getText(), charsInABlock, Encode26));
                        }
                    }

                }
            }

        }
    }

    public void encrypt() {

        if (!isNumeric(charsInABlockTextField.getText())) {
            decryptionTextArea.setText("Please enter a block size number!");
        } else {
            if (eTextField.getText().isEmpty() || nTextField.getText().isEmpty()) {
                decryptionTextArea.setText("Please enter a key!");
            } else {
                charsInABlock = Integer.parseInt(charsInABlockTextField.getText());

                key[0] = BigInteger.valueOf(Long.parseLong(eTextField.getText()));
                key[2] = BigInteger.valueOf(Long.parseLong(nTextField.getText()));

                boolean Encode26 = encode26.isSelected();

                if (key[0].equals(BigInteger.valueOf(0)) || key[2].equals(BigInteger.valueOf(0))) {
                    decryptionTextArea.setText("Please enter a key!");
                } else {

                    if (Encode26) {
                        if (key[2].compareTo(BigInteger.valueOf((long) (Math.pow(26, Long.parseLong(charsInABlockTextField.getText())) - 1))) == -1) {
                            smallKeyError();
                        } else {
                            decryptionTextArea.setText(decryptEncrypt.encrypt(key, encryptionTextArea.getText(), charsInABlock, Encode26));
                        }

                    } else {
                        if (key[2].compareTo(BigInteger.valueOf((long) (Math.pow(256, Long.parseLong(charsInABlockTextField.getText())) - 1))) == -1) {
                            smallKeyError();
                        } else {
                            decryptionTextArea.setText(decryptEncrypt.encrypt(key, encryptionTextArea.getText(), charsInABlock, Encode26));
                        }
                    }

                }
            }

        }
    }

    public void smallKeyError(){
    keyOutputField.setText("Please enter bigger prime numbers or a greater key!");
}

    public static String clear (String inputText){
            String outputText = "";
            for (int i = 0; i < inputText.length(); i++) {
                char character = inputText.charAt(i);
                int newChar;
                if ((int) character >= (int) 'A' && (int) character <= (int) 'Z') {
                    // Zeichen ist ein Großbuchstabe
                    outputText = outputText + character;
                } else if ((int) character >= (int) 'a' && (int) character <= (int) 'z') {
                    //Zeichen ist ein Großbuchstabe
                    newChar = character - 32;
                    outputText = outputText + (char) newChar;
                } else if (character == 'ß') {
                    outputText = outputText + "SS";
                } else if (character == 'Ä' || character == 'ä') {
                    outputText = outputText + "AE";
                } else if (character == 'Ö' || character == 'ö') {
                    outputText = outputText + "OE";
                } else if (character == 'Ü' || character == 'ü') {
                    outputText = outputText + "UE";
                } else if (character == '\n') {
                    outputText = outputText + "";
                }

            }

            return outputText;

        }

    }












