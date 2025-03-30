package Nivelamento.Teste.Teste1;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.*;

public class WebScraping {
    public static void main() {
        try {
            String downloadDir = "download/";
            Files.createDirectories(Paths.get(downloadDir));

            String zipFilePath = downloadDir + "anexos.zip";

            String[] pdfUrls = {
                    "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
                    "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
            };

            try (FileOutputStream fos = new FileOutputStream(zipFilePath);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                for (String pdfUrl : pdfUrls) {
                    addFileToZip(pdfUrl, zos);
                }
            }

            System.out.println("Download e compactação concluídos com sucesso.");
            System.out.println("Arquivo ZIP salvo em: " + zipFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addFileToZip(String fileUrl, ZipOutputStream zos) throws IOException {
        URL url = new URL(fileUrl);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

        zos.putNextEntry(new ZipEntry(fileName));

        try (InputStream in = url.openStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
        }

        zos.closeEntry();
        System.out.println("Arquivo adicionado ao ZIP: " + fileName);
    }
}