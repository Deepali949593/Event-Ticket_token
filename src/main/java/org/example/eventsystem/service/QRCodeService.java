package org.example.eventsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;

public class QRCodeService {
    public static String generateQRCode(String text, String email) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            var bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 300, 300);
            String filePath = "qr_" + email.replace("@", "_") + ".png";
            Path path = Paths.get(filePath);
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", fos);
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
