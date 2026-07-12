package hei.school.demo.ticket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

@Component
public class TicketPdfGenerator {

  public byte[] generate(String userName, String courseTitle, Instant courseDate)
      throws IOException {
    try (PDDocument document = new PDDocument()) {
      PDPage page = new PDPage();
      document.addPage(page);

      try (PDPageContentStream content = new PDPageContentStream(document, page)) {
        var titleFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        content.beginText();
        content.setFont(titleFont, 18);
        content.newLineAtOffset(50, 700);
        content.showText("Ticket d'inscription");
        content.endText();

        var normalFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        content.beginText();
        content.setFont(normalFont, 12);
        content.newLineAtOffset(50, 650);
        content.showText("Nom : " + userName);
        content.newLineAtOffset(0, -20);
        content.showText("Cours : " + courseTitle);
        content.newLineAtOffset(0, -20);
        content.showText(
            "Date : "
                + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .withZone(ZoneOffset.UTC)
                    .format(courseDate));
        content.endText();
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      document.save(out);
      return out.toByteArray();
    }
  }
}
