package Nivelamento.Teste.Teste3;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.zip.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TarefasDePreparacao1 {
    private static final String DOWNLOAD_DIR = "downloadsDados";
    private static final String OUTPUT_CSV_2023 = DOWNLOAD_DIR + "/Demonstracoes_Contabeis_2023.csv";
    private static final String OUTPUT_CSV_2024 = DOWNLOAD_DIR + "/Demonstracoes_Contabeis_2024.csv";
    private static final String[] URLS = {
            "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2023/",
            "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/"
    };

    public static void main() {
        new File(DOWNLOAD_DIR).mkdir();

        processYear(URLS[0], OUTPUT_CSV_2023);
        processYear(URLS[1], OUTPUT_CSV_2024);
    }

    private static void processYear(String url, String outputCsv) {
        downloadAndExtractFiles(url);
        mergeCsvFiles(outputCsv);
        deleteZipFiles();
    }

    private static void downloadAndExtractFiles(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String fileName = link.attr("href");
                if (fileName.endsWith(".zip")) {
                    File zipFile = downloadFile(url + fileName, DOWNLOAD_DIR);
                    if (zipFile != null) {
                        extractZip(zipFile, DOWNLOAD_DIR);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao acessar " + url + ": " + e.getMessage());
        }
    }

    private static File downloadFile(String fileUrl, String directory) {
        try (InputStream in = new URL(fileUrl).openStream()) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
            File file = new File(directory, fileName);
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Baixado: " + fileName);
            return file;
        } catch (IOException e) {
            System.err.println("Erro ao baixar " + fileUrl + ": " + e.getMessage());
        }
        return null;
    }

    private static void extractZip(File zipFile, String outputFolder) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(outputFolder, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
            System.out.println("Extraído: " + zipFile.getName());
        } catch (IOException e) {
            System.err.println("Erro ao extrair " + zipFile.getName() + ": " + e.getMessage());
        }
    }

    private static void mergeCsvFiles(String outputCsv) {
        File dir = new File(DOWNLOAD_DIR);
        File[] csvFiles = dir.listFiles((d, name) -> name.endsWith(".csv") && !name.equals(outputCsv));
        if (csvFiles == null || csvFiles.length == 0) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsv))) {
            boolean firstFile = true;
            for (File csv : csvFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (firstFile || !line.startsWith("registro_ans")) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
                firstFile = false;
            }
            System.out.println("Arquivo unificado criado: " + outputCsv);
        } catch (IOException e) {
            System.err.println("Erro ao unificar CSVs: " + e.getMessage());
        }
    }

    private static void deleteZipFiles() {
        File dir = new File(DOWNLOAD_DIR);
        File[] zipFiles = dir.listFiles((d, name) -> name.endsWith(".zip"));
        if (zipFiles != null) {
            for (File zip : zipFiles) {
                if (zip.delete()) {
                    System.out.println("Apagado: " + zip.getName());
                } else {
                    System.err.println("Erro ao apagar " + zip.getName());
                }
            }
        }
    }
}
