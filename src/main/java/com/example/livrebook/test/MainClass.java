package com.example.livrebook.test;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import  com.google.zxing.client.j2se.*;

import java.io.IOException;
import java.nio.file.Paths;


public class MainClass {
    public static void main(String[] args) throws WriterException, IOException {
        String data = "ahla bik fi jendouba ";
        String path = "C:\\Users\\user\\Desktop\\QR-Code\\infybuzz.jpg";
        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE,500,500);
          MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        System.out.println("Qr code successfully created");

    }
}
