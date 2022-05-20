package com.example.microblog.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String hash(String text){
        StringBuffer sb = new StringBuffer();
        MessageDigest md = null;
    try{
              md  = MessageDigest.getInstance("MD5");}
    catch (NoSuchAlgorithmException e){
        e.printStackTrace();
    }
    assert md != null;
    md.update(text.getBytes());

    byte[] bytes =  md.digest();
    for(int i = 0; i < bytes.length; i++){
        sb.append(Integer.toString((bytes[i] & 0xff) +0x100, 16).substring(1));
    }

    return sb.toString();



    }

}