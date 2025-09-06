import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipTest {

    @Test
    void zipArchiveTest() {


        try (InputStream zipStream = getClass().getClassLoader()
                .getResourceAsStream("Sklep.zip");
             ZipInputStream zis = new ZipInputStream(zipStream)) {

            assertNotNull(zipStream, "ZIP архив не найден!");
            ZipEntry entry;
            int fileCount = 0;

            while ((entry = zis.getNextEntry()) != null) {
                fileCount++;

                byte[] fileContent = zis.readAllBytes();
                ByteArrayInputStream fileStream = new ByteArrayInputStream(fileContent);

                switch (entry.getName()) {
                    case "Skleppdf.pdf":
                        testPdfFile(fileStream);
                        break;
                    case "Sklepxlsx.xlsx":
                        testXlsxFile(fileStream);
                        break;
                    case "Sklepcsv.csv":
                        testCsvFile(fileStream);
                        break;
                    default:
                        fail("Неизвестный файл в архиве: " + entry.getName());
                }

                zis.closeEntry();
            }

            assertEquals(3, fileCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void testPdfFile(InputStream stream) throws Exception {

        PDF pdf = new PDF(stream);
        assertTrue(pdf.text.contains("Laptop"), "PDF должен содержать 'Laptop'");
        assertTrue(pdf.text.contains("1000 zł"), "PDF должен содержать '1000 zł'");
        assertTrue(pdf.text.contains("Mysz"), "PDF должен содержать 'Mysz'");
    }

    void testXlsxFile(InputStream stream) throws Exception {

        XLS xls = new XLS(stream);
        String content = xls.excel.getSheetAt(0).getRow(4).getCell(0).getStringCellValue();
        assertTrue(content.contains("Monitor"));
    }

    void testCsvFile(InputStream stream) throws Exception {

        System.out.println("🔹 Проверяем CSV файл");
        try (CSVReader reader = new CSVReader(new InputStreamReader(stream))) {
            List<String[]> content = reader.readAll();

            // Убираем BOM из первой строки если он есть
            String[] firstRow = content.get(0);
            if (firstRow[0].startsWith("\uFEFF")) {
                firstRow[0] = firstRow[0].substring(1);
            }
            assertArrayEquals(new String[]{"id", "produkt", "ilosc", "cena"}, firstRow);
            assertEquals(5, content.size());
        }
    }
}