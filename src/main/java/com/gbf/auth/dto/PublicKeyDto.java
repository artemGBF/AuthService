package com.gbf.auth.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class PublicKeyDto {
    private byte[] bytes;

    public PublicKeyDto() {
    }

    public PublicKeyDto(PublicKey key) throws NoSuchAlgorithmException, IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(key);
        this.bytes = b.toByteArray();
        o.close();
        b.close();
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
