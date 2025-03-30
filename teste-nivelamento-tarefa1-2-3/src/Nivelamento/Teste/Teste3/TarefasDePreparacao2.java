package Nivelamento.Teste.Teste3;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class TarefasDePreparacao2 {
    private static final String DOWNLOAD_DIR = "downloadsDados";
    private static final String FILE_URL = "https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/Relatorio_cadop.csv";
    private static final String OUTPUT_FILE = DOWNLOAD_DIR + "/Operadoras_Ativas_Na_ANS.csv";

    public static void main() {
        new File(DOWNLOAD_DIR).mkdir();
        downloadFile(FILE_URL, OUTPUT_FILE);
    }

    private static void downloadFile(String fileUrl, String outputFilePath) {
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, Paths.get(outputFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo baixado com sucesso: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao baixar o arquivo: " + e.getMessage());
        }
    }
}
