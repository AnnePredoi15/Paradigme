import org.example.Main;
import org.example.Student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

public class StudentTest {

    @Test
    public void testSortByName() {

        List<Student> lista = new ArrayList<>();

        lista.add(new Student(1234, "Ion", "Zaharia", 101));
        lista.add(new Student(6758, "Ana", "Antonescu", 213));
        lista.add(new Student(3582, "Maria", "Popescu", 223));

        Main.sortByName(lista);

        Assertions.assertEquals("Antonescu", lista.get(0).nume);

        Assertions.assertEquals("Popescu", lista.get(1).nume);

        Assertions.assertEquals("Zaharia", lista.get(2).nume);
    }
}