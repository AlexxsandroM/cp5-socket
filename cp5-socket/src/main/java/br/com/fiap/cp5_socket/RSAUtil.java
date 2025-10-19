package br.com.fiap.cp5_socket;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

public class RSAUtil {
    private BigInteger p, q, n, phi, e, d;
    private int bitlength = 1024;
    private SecureRandom r = new SecureRandom();

    public RSAUtil(BigInteger p, BigInteger q, BigInteger e) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = e;
        this.d = e.modInverse(phi);
    }

    public BigInteger getN() { return n; }
    public BigInteger getE() { return e; }
    public BigInteger getD() { return d; }

    public BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(d, n);
    }

    public static BigInteger stringToBigInt(String text) {
        return new BigInteger(1, text.getBytes(StandardCharsets.UTF_8));
    }

    public static String bigIntToString(BigInteger value) {
        byte[] bytes = value.toByteArray();
        // Remove byte extra de sinal se necessário
        if (bytes.length > 1 && bytes[0] == 0) {
            byte[] tmp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tmp, 0, tmp.length);
            bytes = tmp;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // --- Métodos auxiliares para trabalhar com mensagens maiores que n ---
    // Estratégia simples: criptografar byte a byte e enviar como CSV de BigIntegers
    public static String encryptToCsv(String text, BigInteger e, BigInteger n) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int unsigned = data[i] & 0xFF; // 0..255
            BigInteger m = BigInteger.valueOf(unsigned);
            BigInteger c = m.modPow(e, n);
            if (i > 0) sb.append(',');
            sb.append(c.toString());
        }
        return sb.toString();
    }

    public String decryptFromCsv(String csv) {
        if (csv == null || csv.isEmpty()) return "";
        String[] parts = csv.split(",");
        byte[] out = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            BigInteger c = new BigInteger(parts[i].trim());
            BigInteger m = decrypt(c); // m < n
            int val = m.intValue();
            out[i] = (byte)(val & 0xFF);
        }
        return new String(out, StandardCharsets.UTF_8);
    }
}
