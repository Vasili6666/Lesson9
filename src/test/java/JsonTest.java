import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    @Test
    void jsonFileTest() {
        System.out.println("🔹 ШАГ 1: Получаем JSON файл из тестовых ресурсов");

        try (InputStream jsonStream = getClass().getClassLoader()
                .getResourceAsStream("Sklepjson.json")) {

            assertNotNull(jsonStream, "Файл не найден! Проверь путь: Sklepjson.json");

            System.out.println("🔹 ШАГ 2: Создаем ObjectMapper для работы с JSON");
            ObjectMapper objectMapper = new ObjectMapper();

            System.out.println("🔹 ШАГ 3: Преобразуем JSON в Java объекты");
            User[] users = objectMapper.readValue(jsonStream, User[].class);

            System.out.println("🔹 ШАГ 4: Проверяем данные");
            assertEquals(3, users.length, "Должно быть 3 пользователя");

            User jan = users[0];
            User anna = users[1];
            User piotr = users[2];

            // Проверяем Яна (первый пользователь)
            assertEquals("Jan", jan.getImie(), "Неверное имя Jan");
            assertEquals("Kowalski", jan.getNazwisko(), "Неверная фамилия Jan");
            assertEquals("jan.kowalski@email.pl", jan.getEmail(), "Неверный email Jan");
            assertEquals(28, jan.getWiek(), "Неверный возраст Jan");
            assertTrue(jan.isAktywny(), "Jan должен быть активен");
            assertTrue(jan.getZamowienia().contains("Laptop"), "У Jan должен быть Laptop");
            assertTrue(jan.getZamowienia().contains("Mysz"), "У Jan должна быть Mysz");
            assertEquals(2, jan.getZamowienia().size(), "У Jan должно быть 2 заказа");

            // Проверяем Анну (второй пользователь)
            assertEquals("Anna", anna.getImie(), "Неверное имя Anna");
            assertEquals("Nowak", anna.getNazwisko(), "Неверная фамилия Anna");
            assertEquals("anna.nowak@email.pl", anna.getEmail(), "Неверный email Anna");
            assertEquals(32, anna.getWiek(), "Неверный возраст Anna");
            assertFalse(anna.isAktywny(), "Anna НЕ должна быть активна");
            assertTrue(anna.getZamowienia().contains("Klawiatura"), "У Anna должна быть Klawiatura");
            assertTrue(anna.getZamowienia().contains("Monitor"), "У Anna должен быть Monitor");
            assertEquals(2, anna.getZamowienia().size(), "У Anna должно быть 2 заказа");

            // Проверяем Петра (третий пользователь)
            assertEquals("Piotr", piotr.getImie(), "Неверное имя Piotr");
            assertEquals("Wiśniewski", piotr.getNazwisko(), "Неверная фамилия Piotr");
            assertEquals("piotr.wisniewski@email.pl", piotr.getEmail(), "Неверный email Piotr");
            assertEquals(25, piotr.getWiek(), "Неверный возраст Piotr");
            assertTrue(piotr.isAktywny(), "Piotr должен быть активен");
            assertTrue(piotr.getZamowienia().contains("Mysz"), "У Piotr должна быть Mysz");
            assertTrue(piotr.getZamowienia().contains("Klawiatura"), "У Piotr должна быть Klawiatura");
            assertTrue(piotr.getZamowienia().contains("Monitor"), "У Piotr должен быть Monitor");
            assertEquals(3, piotr.getZamowienia().size(), "У Piotr должно быть 3 заказа");


            System.out.println("🔹 ШАГ 5: Выводим информацию");
            System.out.println("=== ПОЛЬЗОВАТЕЛИ ИЗ JSON ===");
            for (User user : users) {
                System.out.println("👤 " + user.getImie() + " " + user.getNazwisko());
                System.out.println("   🛒 Zamówienia: " + user.getZamowienia());
            }

            System.out.println("✅ ТЕСТ ЗАВЕРШЕН УСПЕШНО!");

        } catch (Exception e) {
            // Обработка исключений
            System.out.println("❌ Ошибка при работе с файлом: " + e.getMessage());
            e.printStackTrace();
            fail("Тест упал с ошибкой: " + e.getMessage());
        }
    }
}