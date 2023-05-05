import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class App {
    static String filename = "tak22_Persons.csv";
    static List<Persons> persons = new ArrayList<>();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static void main(String[] args) {
        readFileContents();
        System.out.println(persons.size()); // Show List result
        showList(persons);
    }

    // Meetod mis arvutab inimese vanuse tänase päeva seisuga
    private static int calculateAge(LocalDate birth) {
        LocalDate today = LocalDate.now();
        return Period.between(birth, today).getYears();
    }

    private static void readFileContents() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int lineCounter = 0;
            for (String line; (line = br.readLine()) != null; ) {
                if (lineCounter > 0) {
                    // System.out.println(line); // test
                    String[] parts = line.split(";");
                    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // enne maini olemas
                    LocalDate date = LocalDate.parse(parts[2], formatter);
                    persons.add(new Persons(parts[0], parts[1], date, parts[3], parts[4]));
                    System.out.println(line); // test
                }
                lineCounter++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showList(List<Persons> persons) {
        for (Persons p : persons) {
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // enne main olemas
            String dataStr = p.getBirth().format(formatter);
            int age = calculateAge(p.getBirth());
            System.out.println(String.join(";", p.getFirstname(), p.getLastname(), dataStr, p.getGender(),p.getPersonalCode(), Integer.toString(age)));
        }
    }
}
