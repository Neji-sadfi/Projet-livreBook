package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import com.example.livrebook.model.event.Ticket;
import com.example.livrebook.service.eventServices.EventService;
import com.example.livrebook.service.eventServices.TicketService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.awt.Desktop.getDesktop;


public class EventDescriptionController  implements Initializable {

    @FXML
    private Button addTicket;


    @FXML
    private Label nb_ticket;


    @FXML
    private Label ticket_adresse;

    @FXML
    private Label ticket_description;

    @FXML
    private Label ticket_end;

    @FXML
    private ImageView ticket_photo;

    @FXML
    private Label ticket_price;

    @FXML
    private Label ticket_start;

    @FXML
    private Label ticket_title;




    TicketService ticketService;
     Event receivedEvent;
     EventService eventService;

    @FXML
    void addTicket(ActionEvent event) throws WriterException, IOException, SQLException, DocumentException {

        String data = "https://thatsthefinger.com/";
        String path = "C:\\Users\\user\\Desktop\\QR-Code\\infybuzz.jpg";
        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
        System.out.println("Qr code successfully created");
        String pathbase = "C:/Users/user/Desktop/QR-Code/Ticket.jpg";
        Ticket ticket = new Ticket(pathbase, hashCode(), 0, receivedEvent.getId());
        ticketService.insert(ticket);

         eventService.updateTicket(receivedEvent);
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
        Paragraph title = new Paragraph("Event "+receivedEvent.getTitle()+" Ticket", titleFont);
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
        table.addCell(createCell(String.valueOf(receivedEvent.getStartDate()), normalFont));

        table.addCell(createCell("About:", normalFont));
        table.addCell(createCell(receivedEvent.getDescription(), normalFont));

        table.addCell(createCell("Location:", normalFont));
        table.addCell(createCell(receivedEvent.getAdresse(), normalFont));

        try {
            doc.add(table);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        // Close the document
        doc.close();
        File pdfFile = new File("Ticket.pdf");
        try {
            getDesktop().open(pdfFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to open PDF file: " + e.getMessage());
        }
    }

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }





    public void setEvent(Event event) {
       this.receivedEvent = event;

        setData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ticketService = new TicketService();
        this.eventService = new EventService();

    }




    @Override
    public int hashCode() {
      return Objects.hash(receivedEvent.getId());


    }

    public void setData() {


        if (receivedEvent != null) {
            if (ticket_photo != null) {
            javafx.scene.image.Image image = new javafx.scene.image.Image((getClass().getResourceAsStream(receivedEvent.getPicture())));

            ticket_photo.setImage(image);}
            ticket_title.setText(receivedEvent.getTitle());
            String date = String.valueOf(receivedEvent.getStartDate());
            ticket_start.setText(date);
            String date_end = String.valueOf(receivedEvent.getEndDate());
            ticket_end.setText(date_end);
            ticket_adresse.setText(receivedEvent.getAdresse());
            ticket_description.setText(receivedEvent.getDescription());
            ticket_price.setText(String.valueOf(hashCode()));

            System.out.println(receivedEvent);
        } else {
            // Handle the case where receivedEvent is null
            // For example, set default values for your UI elements
            ticket_title.setText("");
            ticket_start.setText("");
            ticket_end.setText("");
            ticket_adresse.setText("");
            ticket_description.setText("");
            ticket_price.setText("");
        }
    }


}
