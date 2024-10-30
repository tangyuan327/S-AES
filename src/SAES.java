import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class SAES {
    // S-box and inverse S-box
    private static final int[][] S_BOX = {
            {9, 4, 10, 11},
            {13, 1, 8, 5},
            {6, 2, 0, 3},
            {12, 14, 15, 7}
    };

    private static final int[][] INV_S_BOX = {
            {10, 5, 9, 11},
            {1, 7, 8, 15},
            {6, 0, 2, 3},
            {12, 4, 13, 14}
    };

    // Round constants
    private static final int[] RCON1 = {8, 0};
    private static final int[] RCON2 = {3, 0};

    // Key expansion
    public static int[][] keyExpansion(String keyStr) {
        int[] key = from16to4(keyStr);  // 原密钥
        int[][] W = new int[6][2];
        W[0][0] = key[0];
        W[0][1] = key[1];
        W[1][0] = key[2];
        W[1][1] = key[3];
        int[] gw1 = G(W[1], RCON1);
        W[2][0] = W[0][0] ^ gw1[0];
        W[2][1] = W[0][1] ^ gw1[1];
        W[3][0] = W[2][0] ^ W[1][0];
        W[3][1] = W[2][1] ^ W[1][1];
        int[] gw2 = G(W[3], RCON2);
        W[4][0] = W[2][0] ^ gw2[0];
        W[4][1] = W[2][1] ^ gw2[1];
        W[5][0] = W[4][0] ^ W[3][0];
        W[5][1] = W[4][1] ^ W[3][1];
        return W;
    }

    // Substitute using S-box
    private static int[] substitute(int[] state) {
        int[] result = new int[state.length];
        for (int i = 0; i < state.length; i++) {
            int[] pos = parseHexStr(state[i]);
            result[i] = S_BOX[pos[0]][pos[1]];
        }
        return result;
    }

    // Inverse substitute using inverse S-box
    private static int[] invSubstitute(int[] state) {
        int[] result = new int[state.length];
        for (int i = 0; i < state.length; i++) {
            int[] pos = parseHexStr(state[i]);
            result[i] = INV_S_BOX[pos[0]][pos[1]];
        }
        return result;
    }

    // Add round key
    private static int[] addRoundKey(int[] state, int[] roundKey) {
        for (int i = 0; i < state.length; i++) {
            state[i] ^= roundKey[i];
        }
        return state;
    }

    // Shift rows
    private static int[] shiftRows(int[] state) {
        int a = state[1];
        state[1] = state[3];
        state[3] = a;
        return state;
    }

    // Mix columns
    private static int[] mixColumns(int[] state) {
        String binStr = from4to16(state);
        int[] bin = new int[16];
        for (int i = 0; i < 16; i++) {
            bin[i] = Integer.valueOf(binStr.substring(i, i + 1));
        }
        int[] result = new int[16];
        result[0] = bin[0] ^ bin[6];
        result[1] = bin[1] ^ bin[4] ^ bin[7];
        result[2] = bin[2] ^ bin[4] ^ bin[5];
        result[3] = bin[3] ^ bin[5];
        result[4] = bin[2] ^ bin[4];
        result[5] = bin[0] ^ bin[3] ^ bin[5];
        result[6] = bin[0] ^ bin[1] ^ bin[6];
        result[7] = bin[1] ^ bin[7];
        result[8] = bin[8] ^ bin[14];
        result[9] = bin[9] ^ bin[12] ^ bin[15];
        result[10] = bin[10] ^ bin[12] ^ bin[13];
        result[11] = bin[11] ^ bin[13];
        result[12] = bin[10] ^ bin[12];
        result[13] = bin[8] ^ bin[11] ^ bin[13];
        result[14] = bin[8] ^ bin[9] ^ bin[14];
        result[15] = bin[9] ^ bin[15];
        String binStr2 = "";
        for (int i = 0; i < 16; i++) {
            binStr2 += String.valueOf(result[i]);
        }
        return from16to4(binStr2);
    }

    // Inverse mix columns
    private static int[] invMixColumns(int[] state) {
        String binStr = from4to16(state);
        int[] bin = new int[16];
        for (int i = 0; i < 16; i++) {
            bin[i] = Integer.valueOf(binStr.substring(i, i + 1));
        }
        int[] result = new int[16];
        result[0] = bin[3] ^ bin[5];
        result[1] = bin[0] ^ bin[6];
        result[2] = bin[1] ^ bin[4] ^ bin[7];
        result[3] = bin[2] ^ bin[3] ^ bin[4];
        result[4] = bin[1] ^ bin[7];
        result[5] = bin[2] ^ bin[4];
        result[6] = bin[0] ^ bin[3] ^ bin[5];
        result[7] = bin[0] ^ bin[6] ^ bin[7];
        result[8] = bin[11] ^ bin[13];
        result[9] = bin[8] ^ bin[14];
        result[10] = bin[9] ^ bin[12] ^ bin[15];
        result[11] = bin[10] ^ bin[11] ^ bin[12];
        result[12] = bin[9] ^ bin[15];
        result[13] = bin[10] ^ bin[12];
        result[14] = bin[8] ^ bin[11] ^ bin[13];
        result[15] = bin[8] ^ bin[14] ^ bin[15];
        String binStr2 = "";
        for (int i = 0; i < 16; i++) {
            binStr2 += String.valueOf(result[i]);
        }
        return from16to4(binStr2);
    }

    // Encryption
    public static String encrypt(String plaintext, String keyStr) {
        int[] state = from16to4(plaintext);
        int[][] W = keyExpansion(keyStr);

        // Initial round key addition
        state[0] ^= W[0][0];
        state[1] ^= W[0][1];
        state[2] ^= W[1][0];
        state[3] ^= W[1][1];

        // Round 1
        state = substitute(state);
        state = shiftRows(state);
        state = mixColumns(state);
        state[0] ^= W[2][0];
        state[1] ^= W[2][1];
        state[2] ^= W[3][0];
        state[3] ^= W[3][1];

        // Round 2
        state = substitute(state);
        state = shiftRows(state);
        state[0] ^= W[4][0];
        state[1] ^= W[4][1];
        state[2] ^= W[5][0];
        state[3] ^= W[5][1];

        return from4to16(state);
    }

    // Decryption
    public static String decrypt(String ciphertext, String keyStr) {
        int[] state = from16to4(ciphertext);
        int[][] W = keyExpansion(keyStr);

        // Final round key addition
        state[0] ^= W[4][0];
        state[1] ^= W[4][1];
        state[2] ^= W[5][0];
        state[3] ^= W[5][1];

        // Round 2
        state = shiftRows(state);
        state = invSubstitute(state);
        state[0] ^= W[2][0];
        state[1] ^= W[2][1];
        state[2] ^= W[3][0];
        state[3] ^= W[3][1];

        // Round 1
        state = invMixColumns(state);
        state = shiftRows(state);
        state = invSubstitute(state);
        state[0] ^= W[0][0];
        state[1] ^= W[0][1];
        state[2] ^= W[1][0];
        state[3] ^= W[1][1];

        return from4to16(state);
    }

    // Helper methods
    private static int[] from16to4(String binStr) {
        int[] dec = new int[4];
        for (int i = 0; i < 4; i++) {
            dec[i] = Integer.parseInt(binStr.substring(i * 4, i * 4 + 4), 2);
        }
        return dec;
    }

    private static String from4to16(int[] dec) {
        StringBuilder binStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            binStr.append(String.format("%4s", Integer.toBinaryString(dec[i])).replace(' ', '0'));
        }
        return binStr.toString();
    }

    private static int[] parseHexStr(int dec) {
        String binStr = String.format("%4s", Integer.toBinaryString(dec)).replace(' ', '0');
        int a = Integer.parseInt(binStr.substring(0, 2), 2);
        int b = Integer.parseInt(binStr.substring(2, 4), 2);
        return new int[]{a, b};
    }

    private static int[] G(int[] W, int[] rcon) {
        int[] newW = new int[2];
        newW[0] = W[1];
        newW[1] = W[0];
        newW = substitute(newW);
        for (int i = 0; i < 2; i++) {
            newW[i] ^= rcon[i];
        }
        return newW;
    }

    // Convert ASCII string to binary string
    public static String asciiToBinary(String ascii) {
        StringBuilder binary = new StringBuilder();
        for (char c : ascii.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    // Convert binary string to ASCII string
    public static String binaryToAscii(String binary) {
        StringBuilder ascii = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            ascii.append((char) Integer.parseInt(binary.substring(i, i + 8), 2));
        }
        return ascii.toString();
    }

    // Double encryption
    public static String doubleEncrypt(String plaintext, String key) {
        String key1 = key.substring(0, 16);
        String key2 = key.substring(16);
        String intermediate = encrypt(plaintext, key1);
        return encrypt(intermediate, key2);
    }

    // Double decryption
    public static String doubleDecrypt(String ciphertext, String key) {
        String key1 = key.substring(0, 16);
        String key2 = key.substring(16);
        String intermediate = decrypt(ciphertext, key2);
        return decrypt(intermediate, key1);
    }

    // Meet-in-the-middle attack
    public static String meetInTheMiddleAttack(String plaintext, String ciphertext) {
        Map<String, String> forwardMap = new HashMap<>();
        for (int i = 0; i < 65536; i++) {
            String key = String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');
            String intermediate = encrypt(plaintext, key);
            forwardMap.put(intermediate, key);
        }

        for (int i = 0; i < 65536; i++) {
            String key = String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');
            String intermediate = decrypt(ciphertext, key);
            if (forwardMap.containsKey(intermediate)) {
                String key1 = forwardMap.get(intermediate);
                String key2 = key;
                return key1 + key2;
            }
        }

        return null;
    }

    // Triple encryption
    public static String tripleEncrypt(String plaintext, String key) {
        String key1 = key.substring(0, 16);
        String key2 = key.substring(16, 32);
        String key3 = key.substring(32);
        String intermediate1 = encrypt(plaintext, key1);
        String intermediate2 = encrypt(intermediate1, key2);
        return encrypt(intermediate2, key3);
    }

    // Triple decryption
    public static String tripleDecrypt(String ciphertext, String key) {
        String key1 = key.substring(0, 16);
        String key2 = key.substring(16, 32);
        String key3 = key.substring(32);
        String intermediate1 = decrypt(ciphertext, key3);
        String intermediate2 = decrypt(intermediate1, key2);
        return decrypt(intermediate2, key1);
    }

    private static int[] From16to4(String binaryStr) {
        int[] blocks = new int[binaryStr.length() / 16];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = Integer.parseInt(binaryStr.substring(i * 16, (i + 1) * 16), 2);
        }
        return blocks;
    }

    private static String ToBinary(int number, int bitLength) {
        return String.format("%" + bitLength + "s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    public static List<String> cbcEncrypt(String plaintext, String keyStr, int iv) {
        List<String> ciphertextBlocks = new ArrayList();
        int[] plaintextBlocks = From16to4(plaintext);
        int previousBlock = iv;
        int[] var6 = plaintextBlocks;
        int var7 = plaintextBlocks.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            int plaintextBlock = var6[var8];
            int xoredBlock = plaintextBlock ^ previousBlock;
            String encryptedBlock = encrypt(ToBinary(xoredBlock, 16), keyStr);
            ciphertextBlocks.add(encryptedBlock);
            previousBlock = Integer.parseInt(encryptedBlock, 2);
        }

        return ciphertextBlocks;
    }

    public static String cbcDecrypt(List<Integer> ciphertextBlocks, String keyStr, int iv) {
        StringBuilder plaintext = new StringBuilder();
        int previousBlock = iv;

        int ciphertextBlock;
        for(Iterator var5 = ciphertextBlocks.iterator(); var5.hasNext(); previousBlock = ciphertextBlock) {
            ciphertextBlock = (Integer)var5.next();
            String decryptedBlock = decrypt(ToBinary(ciphertextBlock, 16), keyStr);
            int decryptedBlockInt = Integer.parseInt(decryptedBlock, 2);
            int plaintextBlock = decryptedBlockInt ^ previousBlock;
            plaintext.append(ToBinary(plaintextBlock, 16));
        }

        return plaintext.toString();
    }
}

