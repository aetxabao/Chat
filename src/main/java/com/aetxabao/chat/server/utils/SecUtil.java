package com.aetxabao.chat.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import static java.util.Map.entry;

public class SecUtil {

    public static String salt(int bitLength) {
        int length = bitLength / 8;
        byte[] array = new byte[length]; // length is bounded by 7
        new Random().nextBytes(array);
//        return new String(array, Charset.forName("UTF-8"));
        return byteToHex(array);
    }

    public static String sha256(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        return byteToHex(hash);
    }

    private static String byteToHex(byte[] array){
        StringBuilder hexString = new StringBuilder(2 * array.length);
        for (int i = 0; i < array.length; i++) {
            String hex = Integer.toHexString(0xff & array[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Map<String, String> usrPwds = Map.ofEntries(
                entry("Ana", "**ANA**"),
                entry("Luis", "**LUIS**"),
                entry("Ignacio", "**IGNACIO**"),
                entry("Alberto", "**ALBERTO**"),
                entry("Elena", "**ELENA**"),
                entry("María", "**MARIA**"),
                entry("Julia", "**JULIA**")
        );
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `users` (`name`, `salt`, `hash`) VALUES \n");
        for (Map.Entry<String, String> e : usrPwds.entrySet()) {
            String username = e.getKey();
            String password = e.getValue();
            String salt = salt(512);
            String hash = sha256(password + salt);
            System.out.println(username);
            System.out.println(password);
            System.out.println(salt);
            System.out.println(hash);
            sb.append("('").append(username).append("', '").append(salt).append("', '").append(hash).append("'),\n");
        }
        sb.deleteCharAt(sb.length()-1);//\n
        sb.deleteCharAt(sb.length()-1);//,
        sb.append(";\n");
        String s = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,\n" +
                "  `salt` varchar(128) COLLATE utf8_spanish2_ci NOT NULL,\n" +
                "  `hash` varchar(64) COLLATE utf8_spanish2_ci NOT NULL,\n" +
                "  PRIMARY KEY (`name`)\n" +
                ") ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=8 ;";
        System.out.println(s);
        System.out.println(sb.toString());
    }

}
