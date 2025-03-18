package ro.ps.proiect.presenter.file_operation;

import com.opencsv.CSVWriter;
import com.vaadin.flow.server.StreamResource;
import org.apache.poi.xwpf.usermodel.*;
import ro.ps.proiect.model.data_structures.Bilet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BileteFileWriter {

    public static StreamResource getCSVStream(List<Bilet> bilete){
        return new StreamResource("bilete.csv", () -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writeBileteToCSV(outputStream, bilete);
            return new ByteArrayInputStream(outputStream.toByteArray());
        });
    }

    public static StreamResource getDOCXStream(List<Bilet> bilete){
        return new StreamResource("bilete.docx", () -> {
            try{
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XWPFDocument document = new XWPFDocument();

                XWPFParagraph title = document.createParagraph();
                title.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = title.createRun();
                titleRun.setText("Train Tickets Report");
                titleRun.setBold(true);
                titleRun.setFontSize(16);

                document.createParagraph();

                XWPFTable table = document.createTable();

                XWPFTableRow header = table.getRow(0);
                header.getCell(0).setText("ID");
                header.addNewTableCell().setText("Travel Date");
                header.addNewTableCell().setText("Departure st.");
                header.addNewTableCell().setText("Destination st.");
                header.addNewTableCell().setText("Departure t.");
                header.addNewTableCell().setText("Arrival t.");
                header.addNewTableCell().setText("Train No.");
                header.addNewTableCell().setText("Wagon No.");
                header.addNewTableCell().setText("Seat No.");

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                for(Bilet bilet : bilete){
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(String.valueOf(bilet.getId()));
                    row.getCell(1).setText(bilet.getDataCalatoriei().format(dateFormatter));
                    row.getCell(2).setText(bilet.getGaraDePlecare().toString());
                    row.getCell(3).setText(bilet.getGaraDeDestinatie().toString());
                    row.getCell(4).setText(bilet.getOraDePlecare().format(timeFormatter));
                    row.getCell(5).setText(bilet.getOraDeSosire().format(timeFormatter));
                    row.getCell(6).setText(bilet.getVagon().getTren().getNrTren());
                    row.getCell(7).setText(bilet.getVagon().getNrVagon());
                    row.getCell(8).setText(String.valueOf(bilet.getNrLoc()));
                }

                document.write(out);
                document.close();

                return new ByteArrayInputStream(out.toByteArray());
            }
            catch (IOException ex){
                throw new RuntimeException("Error generating .doc file", ex);
            }
        });
    }

    private static void writeBileteToCSV(OutputStream outputStream, List<Bilet> bilete){
        try(CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            String[] header = {"ID", "Travel Date", "Departure Station", "Destination Station", "Departure Time",
                    "Arrival Time", "Train Number", "Wagon Number", "Seat Number"};
            csvWriter.writeNext(header);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            for(Bilet bilet : bilete){
                String[] row = {
                        String.valueOf(bilet.getId()),
                        bilet.getDataCalatoriei().format(dateFormatter),
                        bilet.getGaraDePlecare().toString(),
                        bilet.getGaraDeDestinatie().toString(),
                        bilet.getOraDePlecare().format(timeFormatter),
                        bilet.getOraDeSosire().format(timeFormatter),
                        bilet.getVagon().getTren().getNrTren(),
                        bilet.getVagon().getNrVagon(),
                        String.valueOf(bilet.getNrLoc())
                };
                csvWriter.writeNext(row);
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
