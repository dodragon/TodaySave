package com.dojagy.todaysave.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256 {

    // AES-256 암호화에 사용할 알고리즘/모드/패딩 형식 정의
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE_BITS = 256;
    private static final int IV_SIZE_BYTES = 16; // AES의 블록 크기는 128비트(16바이트)

    /**
     * 256비트 AES 키를 생성합니다.
     * 실제 운영 환경에서는 이 키를 안전한 곳에 보관하고 재사용해야 합니다.
     * @return SecretKey 객체
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * SecretKey를 Base64 인코딩된 문자열로 변환합니다.
     * @param secretKey
     * @return Base64 인코딩된 키 문자열
     */
    public static String keyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Base64 인코딩된 문자열을 SecretKey 객체로 변환합니다.
     * @param keyString Base64 인코딩된 키 문자열
     * @return SecretKey 객체
     */
    public static SecretKey stringToKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    /**
     * 평문을 AES-256으로 암호화합니다.
     * @param plainText 암호화할 평문
     * @param key       암호화에 사용할 SecretKey
     * @return Base64로 인코딩된 암호문 (IV + 암호문)
     * @throws Exception
     */
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        // 1. IV 생성
        byte[] iv = new byte[IV_SIZE_BYTES];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // 2. Cipher 객체 생성 및 초기화
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

        // 3. 암호화 수행
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // 4. IV와 암호문을 하나로 합침 (IV가 앞에 오도록)
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        // 5. Base64로 인코딩하여 반환
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * AES-256으로 암호화된 문자열을 복호화합니다.
     * @param cipherText Base64로 인코딩된 암호문 (IV + 암호문)
     * @param key        복호화에 사용할 SecretKey
     * @return 복호화된 평문
     * @throws Exception
     */
    public static String decrypt(String cipherText, SecretKey key) throws Exception {
        // 1. Base64 디코딩
        byte[] combined = Base64.getDecoder().decode(cipherText);

        // 2. IV와 암호문 분리
        byte[] iv = new byte[IV_SIZE_BYTES];
        byte[] encryptedBytes = new byte[combined.length - IV_SIZE_BYTES];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        System.arraycopy(combined, iv.length, encryptedBytes, 0, encryptedBytes.length);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // 3. Cipher 객체 생성 및 초기화
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

        // 4. 복호화 수행
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // 5. UTF-8 문자열로 변환하여 반환
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
