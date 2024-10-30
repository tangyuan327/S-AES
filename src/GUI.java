import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    public GUI() {
        frame = new JFrame("S-AES 加密/解密");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // 创建选项卡面板
        tabbedPane = new JTabbedPane();

        // 添加单重加密界面
        tabbedPane.addTab("单重加密", createSingleEncryptionPanel());

        // 添加双重加密界面
        tabbedPane.addTab("双重加密", createDoubleEncryptionPanel());

        // 添加中间相遇攻击界面
        tabbedPane.addTab("中间相遇攻击", createMeetInTheMiddlePanel());

        // 添加三重加密界面
        tabbedPane.addTab("三重加密", createTripleEncryptionPanel());

        // 添加CBC加密界面
        tabbedPane.addTab("CBC 加密/解密", createCbcEncryptionPanel());

        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    private JPanel createSingleEncryptionPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("明文 (16位二进制):"));
        JTextField plaintextField = new JTextField();
        panel.add(plaintextField);

        panel.add(new JLabel("密钥 (16位二进制):"));
        JTextField keyField = new JTextField();
        panel.add(keyField);

        panel.add(new JLabel("密文 (16位二进制):"));
        JTextField ciphertextField = new JTextField();
        panel.add(ciphertextField);

        panel.add(new JLabel("ASCII 明文 (2字节):"));
        JTextField asciiPlaintextField = new JTextField();
        panel.add(asciiPlaintextField);

        panel.add(new JLabel("ASCII 密钥 (2字节):"));
        JTextField asciiKeyField = new JTextField();
        panel.add(asciiKeyField);

        panel.add(new JLabel("ASCII 密文 (2字节):"));
        JTextField asciiCiphertextField = new JTextField();
        panel.add(asciiCiphertextField);

        JButton encryptButton = new JButton("加密");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("解密");
        panel.add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();
                String key = keyField.getText();
                String asciiPlaintext = asciiPlaintextField.getText();
                String asciiKey = asciiKeyField.getText();

                if (!plaintext.isEmpty() && plaintext.length() == 16 && !key.isEmpty() && key.length() == 16) {
                    String ciphertext = SAES.encrypt(plaintext, key);
                    ciphertextField.setText(ciphertext);
                    asciiCiphertextField.setText("");
                } else if (!asciiPlaintext.isEmpty() && asciiPlaintext.length() == 2 && !asciiKey.isEmpty() && asciiKey.length() == 2) {
                    String binaryPlaintext = SAES.asciiToBinary(asciiPlaintext);
                    String binaryKey = SAES.asciiToBinary(asciiKey);
                    String ciphertext = SAES.encrypt(binaryPlaintext, binaryKey);
                    ciphertextField.setText("");
                    asciiCiphertextField.setText(SAES.binaryToAscii(ciphertext));
                } else {
                    JOptionPane.showMessageDialog(frame, "明文和密钥必须是16位二进制字符串或2个ASCII字符。");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextField.getText();
                String key = keyField.getText();
                String asciiCiphertext = asciiCiphertextField.getText();
                String asciiKey = asciiKeyField.getText();

                if (!ciphertext.isEmpty() && ciphertext.length() == 16 && !key.isEmpty() && key.length() == 16) {
                    String plaintext = SAES.decrypt(ciphertext, key);
                    plaintextField.setText(plaintext);
                    asciiPlaintextField.setText("");
                } else if (!asciiCiphertext.isEmpty() && asciiCiphertext.length() == 2 && !asciiKey.isEmpty() && asciiKey.length() == 2) {
                    String binaryCiphertext = SAES.asciiToBinary(asciiCiphertext);
                    String binaryKey = SAES.asciiToBinary(asciiKey);
                    String plaintext = SAES.decrypt(binaryCiphertext, binaryKey);
                    plaintextField.setText("");
                    asciiPlaintextField.setText(SAES.binaryToAscii(plaintext));
                } else {
                    JOptionPane.showMessageDialog(frame, "密文和密钥必须是16位二进制字符串或2个ASCII字符。");
                }
            }
        });

        return panel;
    }

    private JPanel createDoubleEncryptionPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("明文 (16位二进制):"));
        JTextField plaintextField = new JTextField();
        panel.add(plaintextField);

        panel.add(new JLabel("密钥 (32位二进制):"));
        JTextField keyField = new JTextField();
        panel.add(keyField);

        panel.add(new JLabel("密文 (16位二进制):"));
        JTextField ciphertextField = new JTextField();
        panel.add(ciphertextField);

        JButton encryptButton = new JButton("加密");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("解密");
        panel.add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();
                String key = keyField.getText();

                if (!plaintext.isEmpty() && plaintext.length() == 16 && !key.isEmpty() && key.length() == 32) {
                    String ciphertext = SAES.doubleEncrypt(plaintext, key);
                    ciphertextField.setText(ciphertext);
                } else {
                    JOptionPane.showMessageDialog(frame, "明文和密钥必须是16位和32位二进制字符串。");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextField.getText();
                String key = keyField.getText();

                if (!ciphertext.isEmpty() && ciphertext.length() == 16 && !key.isEmpty() && key.length() == 32) {
                    String plaintext = SAES.doubleDecrypt(ciphertext, key);
                    plaintextField.setText(plaintext);
                } else {
                    JOptionPane.showMessageDialog(frame, "密文和密钥必须是16位和32位二进制字符串。");
                }
            }
        });

        return panel;
    }

    private JPanel createMeetInTheMiddlePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("明文 (16位二进制):"));
        JTextField plaintextField = new JTextField();
        panel.add(plaintextField);

        panel.add(new JLabel("密文 (16位二进制):"));
        JTextField ciphertextField = new JTextField();
        panel.add(ciphertextField);

        panel.add(new JLabel("密钥 (32位二进制):"));
        JTextField keyField = new JTextField();
        panel.add(keyField);

        JButton attackButton = new JButton("攻击");
        panel.add(attackButton);

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();
                String ciphertext = ciphertextField.getText();

                if (!plaintext.isEmpty() && plaintext.length() == 16 && !ciphertext.isEmpty() && ciphertext.length() == 16) {
                    String key = SAES.meetInTheMiddleAttack(plaintext, ciphertext);
                    keyField.setText(key);
                } else {
                    JOptionPane.showMessageDialog(frame, "明文和密文必须是16位二进制字符串。");
                }
            }
        });

        return panel;
    }

    private JPanel createTripleEncryptionPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("明文 (16位二进制):"));
        JTextField plaintextField = new JTextField();
        panel.add(plaintextField);

        panel.add(new JLabel("密钥 k1 (16位二进制):"));
        JTextField key1Field = new JTextField();
        panel.add(key1Field);

        panel.add(new JLabel("密钥 k2 (16位二进制):"));
        JTextField key2Field = new JTextField();
        panel.add(key2Field);

        panel.add(new JLabel("密钥 k3 (16位二进制):"));
        JTextField key3Field = new JTextField();
        panel.add(key3Field);

        panel.add(new JLabel("密文 (16位二进制):"));
        JTextField ciphertextField = new JTextField();
        panel.add(ciphertextField);

        JButton encryptButton = new JButton("加密");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("解密");
        panel.add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();
                String key1 = key1Field.getText();
                String key2 = key2Field.getText();
                String key3 = key3Field.getText();

                if (!plaintext.isEmpty() && plaintext.length() == 16 && !key1.isEmpty() && key1.length() == 16 && !key2.isEmpty() && key2.length() == 16 && !key3.isEmpty() && key3.length() == 16) {
                    String key = key1 + key2 + key3;
                    String ciphertext = SAES.tripleEncrypt(plaintext, key);
                    ciphertextField.setText(ciphertext);
                } else {
                    JOptionPane.showMessageDialog(frame, "明文和密钥必须是16位二进制字符串。");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextField.getText();
                String key1 = key1Field.getText();
                String key2 = key2Field.getText();
                String key3 = key3Field.getText();

                if (!ciphertext.isEmpty() && ciphertext.length() == 16 && !key1.isEmpty() && key1.length() == 16 && !key2.isEmpty() && key2.length() == 16 && !key3.isEmpty() && key3.length() == 16) {
                    String key = key1 + key2 + key3;
                    String plaintext = SAES.tripleDecrypt(ciphertext, key);
                    plaintextField.setText(plaintext);
                } else {
                    JOptionPane.showMessageDialog(frame, "密文和密钥必须是16位二进制字符串。");
                }
            }
        });

        return panel;
    }

    private JPanel createCbcEncryptionPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("明文 (ASCII):"));
        JTextArea plaintextArea = new JTextArea();
        plaintextArea.setRows(4);
        plaintextArea.setColumns(20);
        JScrollPane plaintextScrollPane = new JScrollPane(plaintextArea);
        panel.add(plaintextScrollPane);

        panel.add(new JLabel("密钥 (16位二进制):"));
        JTextField keyField = new JTextField();
        panel.add(keyField);

        panel.add(new JLabel("IV (16位二进制):"));
        JTextField ivField = new JTextField();
        panel.add(ivField);

        panel.add(new JLabel("密文 (二进制):"));
        JTextArea ciphertextArea = new JTextArea();
        ciphertextArea.setRows(4);
        ciphertextArea.setColumns(20);
        JScrollPane ciphertextScrollPane = new JScrollPane(ciphertextArea);
        panel.add(ciphertextScrollPane);

        panel.add(new JLabel("解密后的明文 (ASCII):"));
        JTextArea decryptedTextarea = new JTextArea();
        decryptedTextarea.setRows(4);
        decryptedTextarea.setColumns(20);
        JScrollPane decryptedTextScrollPane = new JScrollPane(decryptedTextarea);
        panel.add(decryptedTextScrollPane);

        JButton encryptButton = new JButton("CBC 加密");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("CBC 解密");
        panel.add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextArea.getText();
                String key = keyField.getText();
                String iv = ivField.getText();

                if (!plaintext.isEmpty() && !key.isEmpty() && key.length() == 16 && !iv.isEmpty() && iv.length() == 16) {
                    try {
                        int ivInt = Integer.parseInt(iv, 2);
                        List<String> ciphertextBlocks = SAES.cbcEncrypt(plaintext, key, ivInt);
                        StringBuilder ciphertextBuilder = new StringBuilder();
                        for (String block : ciphertextBlocks) {
                            ciphertextBuilder.append(block).append("\n");
                        }
                        ciphertextArea.setText(ciphertextBuilder.toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "IV必须是16位二进制字符串。");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "密钥和IV必须是16位二进制字符串，且明文不能为空。");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciphertext = ciphertextArea.getText();
                String key = keyField.getText();
                String iv = ivField.getText();

                if (!ciphertext.isEmpty() && !key.isEmpty() && key.length() == 16 && !iv.isEmpty() && iv.length() == 16) {
                    try {
                        int ivInt = Integer.parseInt(iv, 2);
                        String[] blocks = ciphertext.split("\n");
                        List<Integer> ciphertextBlocks = new ArrayList<>();
                        for (String block : blocks) {
                            ciphertextBlocks.add(Integer.parseInt(block, 2));
                        }
                        String decryptedText = SAES.cbcDecrypt(ciphertextBlocks, key, ivInt);
                        decryptedTextarea.setText(decryptedText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "IV必须是16位二进制字符串。");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "密钥和IV必须是16位二进制字符串，且密文不能为空。");
                }
            }
        });

        return panel;
    }
    public static void main(String[] args) {
        new GUI();
    }
}