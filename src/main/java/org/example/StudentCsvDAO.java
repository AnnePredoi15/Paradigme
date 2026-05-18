package org.example;

import java.io.*;
import java.util.*;

public class StudentCsvDAO implements StudentDAO {

    @Override
    public List<Student> citesteStudenti(String caleFisier) {
        List<Student> studenti = new ArrayList<>();
        File fisier = new File(caleFisier);
        if (!fisier.exists()) return studenti;

        try (Scanner scanner = new Scanner(fisier)) {
            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                if (linie.isBlank()) continue;

                String[] date = linie.split(",");
                int nrMatricol = Integer.parseInt(date[0].trim());
                String nume = date[1].trim();
                String prenume = date[2].trim();
                int formatieDeStuniu = Integer.parseInt(date[3].trim());

                studenti.add(new Student(nrMatricol, prenume, nume, formatieDeStuniu));
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea CSV: " + e.getMessage());
        }
        return studenti;
    }

    @Override
    public void salveazaStudenti(List<Student> lista, String caleFisier) {
        try (PrintWriter writer = new PrintWriter(caleFisier)) {
            for (Student s : lista) {
                writer.println(s.getNrMatricol() + "," + s.getNume() + "," + s.getPrenume() + "," + s.getFormatieDeStudiu());
            }
            System.out.println("\n[CSV] Fisierul " + caleFisier + " a fost creat/actualizat cu succes!");
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in fisierul CSV: " + e.getMessage());
        }
    }

    @Override
    public Map<Student, Integer> citesteNote(String caleFisier, List<Student> studentiExistenti) {
        HashMap<Student, Integer> noteStudenti = new HashMap<>();
        File fNote = new File(caleFisier);
        if (!fNote.exists()) {
            System.out.println("\nFisierul note.csv nu a fost gasit!");
            return noteStudenti;
        }

        try (Scanner scannerNote = new Scanner(fNote)) {
            while (scannerNote.hasNextLine()) {
                String linie = scannerNote.nextLine();
                if (linie.trim().isEmpty()) continue;

                String[] date = linie.split(",");
                int matricolCsv = Integer.parseInt(date[0].trim());
                int notaCsv = Integer.parseInt(date[1].trim());

                Student studentGasit = null;
                for (Student s : studentiExistenti) {
                    if (s.getNrMatricol() == matricolCsv) {
                        studentGasit = s;
                        break;
                    }
                }

                if (studentGasit != null) {
                    noteStudenti.put(studentGasit, notaCsv);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea notelor din CSV.");
        }
        return noteStudenti;
    }
}