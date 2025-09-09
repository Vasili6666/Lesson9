import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipTest {

    @Test
    @DisplayName("PDF файл. Проверка.")
    void pdfFileInZipTest() throws Exception {
        try (InputStream zipStream = getClass().getClassLoader()
                .getResourceAsStream("Sklep.zip");
             ZipInputStream zis = new ZipInputStream(zipStream)) {

            assertNotNull(zipStream, "ZIP архив не найден!");
            boolean pdfFound = false;

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("Skleppdf.pdf".equals(entry.getName())) {
                    chekingPdfFile(zis);
                    pdfFound = true;
                    break;
                }
                zis.closeEntry();
            }

            assertTrue(pdfFound, "PDF файл не найден в архиве");
        }
    }

    @Test
    @DisplayName("XLSX файл.Проверка.")
    void xlsxFileInZipTest() throws Exception {
        try (InputStream zipStream = getClass().getClassLoader()
                .getResourceAsStream("Sklep.zip");
             ZipInputStream zis = new ZipInputStream(zipStream)) {

            assertNotNull(zipStream, "ZIP архив не найден!");
            boolean xlsxFound = false;

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("Sklepxlsx.xlsx".equals(entry.getName())) {
                    chekingXlsxFile(zis);
                    xlsxFound = true;
                    break;
                }
                zis.closeEntry();
            }

            assertTrue(xlsxFound, "XLSX файл не найден в архиве");
        }
    }


    @Test
    @DisplayName("Проверка CSV файла в ZIP архиве")
    void csvFileInZipTest() throws Exception {
        try (InputStream zipStream = getClass().getClassLoader()
                .getResourceAsStream("Sklep.zip");
             ZipInputStream zis = new ZipInputStream(zipStream)) {

            assertNotNull(zipStream, "ZIP архив не найден!");
            boolean csvFound = false;

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("Sklepcsv.csv".equals(entry.getName())) {
                    chekingCsvFile(zis);
                    csvFound = true;
                    break;
                }
                zis.closeEntry();
            }

            assertTrue(csvFound, "CSV файл не найден в архиве");
        }
    }

    void chekingPdfFile(InputStream stream) throws Exception {
        PDF pdf = new PDF(stream);
        assertTrue(pdf.text.contains("Laptop"), "PDF должен содержать 'Laptop'");
        assertTrue(pdf.text.contains("1000 zł"), "PDF должен содержать '1000 zł'");
        assertTrue(pdf.text.contains("Mysz"), "PDF должен содержать 'Mysz'");
    }

    void chekingXlsxFile(InputStream stream) throws Exception {
        XLS xls = new XLS(stream);
        String content = xls.excel.getSheetAt(0).getRow(4).getCell(0).getStringCellValue();
        assertTrue(content.contains("Monitor"), "XLSX должен содержать 'Monitor'");
    }


    void chekingCsvFile(InputStream stream) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(stream))) {
            List<String[]> content = reader.readAll();


            String[] firstRow = content.get(0);
            if (firstRow[0].startsWith("\uFEFF")) {
                firstRow[0] = firstRow[0].substring(1);
            }

            assertArrayEquals(new String[]{"id", "produkt", "ilosc", "cena"}, firstRow,
                    "Неверные заголовки CSV");
            assertEquals(5, content.size(), "CSV должен содержать 5 строк");
        }
    }
}