package Nivelamento.Teste.Teste2;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;


public class TransformacaoDeDados {
    public static void main() {
        try {
            String zipFilePath = "download/anexos.zip";
            String extractedFolderPath = "extracted/";

            unzipFiles(zipFilePath, extractedFolderPath);

            File pdfFile = new File(extractedFolderPath + "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
            List<String[]> data = extractDataFromPDF(pdfFile);

            replaceAbbreviations(data);

            String csvFile = "Teste_LuizVictor.csv";
            saveToCSV(data, csvFile);

            zipFile(csvFile, "Teste_LuizVictor.zip");

            System.out.println("Processo concluído com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void unzipFiles(String zipFilePath, String outputFolder) throws IOException {
        Files.createDirectories(Paths.get(outputFolder));
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File extractedFile = new File(outputFolder + entry.getName());
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(extractedFile))) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
        }
    }

    private static List<String[]> extractDataFromPDF(File pdfFile) throws IOException {
        List<String[]> tableData = new ArrayList<>();
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String text = stripper.getText(document);

            // Simulação da extração da tabela, por linhas e colunas
            // Ajuste conforme o formato real da tabela no PDF
            String[] lines = text.split("\n");
            for (String line : lines) {
                String[] columns = line.split("\\s+");
                tableData.add(columns);
            }
        }
        return tableData;
    }

    private static void replaceAbbreviations(List<String[]> data) {
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if ("OD".equals(row[i])) {
                    row[i] = "Seg. Odontológica";  // Substitua com a descrição real
                } else if ("AMB".equals(row[i])) {
                    row[i] = "Seg. Ambulatorial";  // Substitua com a descrição real
                }
            }
        }
    }

    private static void saveToCSV(List<String[]> data, String csvFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeAll(data);
        }
    }

    private static void zipFile(String fileName, String zipFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
        }
    }
}
