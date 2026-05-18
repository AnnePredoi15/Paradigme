package org.example;

import java.util.List;
import java.util.Map;

public interface StudentDAO {
    List<Student> citesteStudenti(String caleFisier);

    void salveazaStudenti(List<Student> lista, String caleFisier);

    Map<Student, Integer> citesteNote(String caleFisier, List<Student> studentiExistenti);
}