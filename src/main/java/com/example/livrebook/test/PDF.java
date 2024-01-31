package com.example.livrebook.test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class PDF {
    public static void main(String[] args) throws WriterException, IOException, DocumentException {
        String data = "ahla bik fi jendouba ";
        String path = "C:\\Users\\user\\Desktop\\QR-Code\\infybuzz.jpg";
        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
        System.out.println("Qr code successfully created");

        var doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("Ticket.pdf"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        doc.open();

        // Define Fonts
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.DARK_GRAY);
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.GRAY);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        // Add title
        Paragraph title = new Paragraph("Event Ticket", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        try {
            doc.add(title);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // Add subtitle
        Paragraph subtitle = new Paragraph("Event Details", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(10f);
        try {
            doc.add(subtitle);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // Add QR Code image
        try {
            Image qrCodeImage = Image.getInstance(path);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            qrCodeImage.scaleAbsolute(200f, 200f);
            doc.add(qrCodeImage);
            doc.add(new Paragraph("Scan QR Code for Event Access", normalFont));
            doc.add(Chunk.NEWLINE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add description
        Paragraph description = new Paragraph(
                "Join us for an exciting event filled with entertainment and fun. " +
                        "Don't miss out on this amazing experience!",
                normalFont);
        description.setAlignment(Element.ALIGN_JUSTIFIED);
        description.setSpacingAfter(20f);
        try {
            doc.add(description);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // Add event details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 5});

        table.addCell(createCell("Date:", normalFont));
        table.addCell(createCell("2024-01-29", normalFont));

        table.addCell(createCell("Time:", normalFont));
        table.addCell(createCell("6:00 PM - 10:00 PM", normalFont));

        table.addCell(createCell("Location:", normalFont));
        table.addCell(createCell("Event Venue, City", normalFont));

        try {
            doc.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // Close the document
        doc.close();
    }

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
