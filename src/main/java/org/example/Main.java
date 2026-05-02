package org.example;

import java.io.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.io.*;
import java.util.*;

public class Main {

    public static List<Student> sortByName(List<Student> lista) {
        lista.sort(Comparator.comparing(s -> s.getNume()));
        return lista;
    }

    public static List<Student> sortStudents(List<Student> lista) {
        lista.sort(Comparator.comparingInt((Student s) -> s.getFormatieDeStudiu()).thenComparing(s -> s.getNume()));
        return lista;
    }

    public static void outputStudentList(List<Student> lista) {
        try (PrintWriter writer = new PrintWriter("src/main/resources/studentisortati.csv")) {
            for (Student s : lista) {
                writer.println(s.getNrMatricol() + "," + s.getNume() + "," + s.getPrenume() + "," + s.getFormatieDeStudiu());
            }
            System.out.println("\nFisierul de sortare a fost creat cu succes!");
        } catch (IOException e) {
            System.out.println("\nEroare la scrierea in fisier: " + e.getMessage());
        }
    }

    public static int notaStudent(Student student, Map<Student, Integer> mapNote) {
        if (mapNote == null) return -1;
        if (mapNote.containsKey(student)) {
            return mapNote.get(student);
        } else {
            return -1;
        }
    }


    public static void outputStudentCuNota(List<StudentCuNota> lista) {
        try (PrintWriter writer = new PrintWriter("src/main/resources/studenticunota.csv")) {
            for (StudentCuNota s : lista) {
                writer.println(s.getNrMatricol() + "," + s.getNume() + "," + s.getPrenume() + "," + s.getFormatieDeStudiu() + "," + s.getNota());
            }
            System.out.println("\nFisierul cu studenti si note a fost creat cu succes!");
        } catch (Exception e) {
            System.out.println("\nEroare la scrierea in fisier: " + e.getMessage());
        }
    }

    public static void mutaStudentInFormatie(List<StudentCuNota> lista, int nrMatricol, int nouaFormatie) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNrMatricol() == nrMatricol) {
                StudentCuNota studentNou = lista.get(i).cuNouaFormatie(nouaFormatie);
                lista.set(i, studentNou);
                System.out.println("\nStudentul cu numarul matricol " + nrMatricol + " a fost mutat in formatia " + nouaFormatie);
                break;
            }
        }
    }

    public static List<List<StudentCuNota>> imparteInDouaFormatii(List<StudentCuNota> lista) {
        int mijloc = (lista.size() + 1) / 2;

        List<StudentCuNota> formatia1 = new ArrayList<>(lista.subList(0, mijloc));
        List<StudentCuNota> formatia2 = new ArrayList<>(lista.subList(mijloc, lista.size()));

        List<List<StudentCuNota>> rezultat = new ArrayList<>();
        rezultat.add(formatia1);
        rezultat.add(formatia2);

        return rezultat;
    }

    public static void salveazaStudentiNoteXlsx(List<StudentCuNota> studenti, String numeFisier) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(numeFisier)) {

            XSSFSheet sheet = workbook.createSheet("StudentiNote");
            int rowNum = 0;
            Row header = sheet.createRow(rowNum++);
            String[] coloane = {"NrMatricol", "Prenume", "Nume", "Formatie", "Nota"};
            for (int i = 0; i < coloane.length; i++) {
                header.createCell(i).setCellValue(coloane[i]);
            }

            for (StudentCuNota s : studenti) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(s.getNrMatricol());
                row.createCell(1).setCellValue(s.getPrenume());
                row.createCell(2).setCellValue(s.getNume());
                row.createCell(3).setCellValue(s.getFormatieDeStudiu());
                row.createCell(4).setCellValue(s.getNota());
            }

            workbook.write(out);
            System.out.println("\n[Excel] Fisier salvat: " + numeFisier);
        } catch (IOException e) {
            System.err.println("Eroare I/O: " + e.getMessage());
        }
    }

    public static ArrayList<StudentCuNota> citesteStudentiNoteXlsx(String numeFisier) {
        ArrayList<StudentCuNota> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(numeFisier);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            // Iterăm peste rânduri, sărind header-ul
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Verificare rând gol

                int nrMatricol = (int) row.getCell(0).getNumericCellValue();
                String prenume = row.getCell(1).getStringCellValue();
                String nume    = row.getCell(2).getStringCellValue();
                int formatie   = (int) row.getCell(3).getNumericCellValue();
                double nota    = row.getCell(4).getNumericCellValue();

                lista.add(new StudentCuNota(nrMatricol, prenume, nume, formatie, nota));
            }
        } catch (Exception e) {
            System.err.println("Eroare la citirea Excel: " + e.getMessage());
        }
        return lista;
    }



    public static void main(String[] args) {

        Student s1 = new Student(1234, "Ion", "Popescu", 101);
        Student s2 = new Student(6758, "Ana", "Popa", 213);
        Student s3 = new Student(3582, "Maria", "Georgescu", 223);
        Student s4 = new Student(6789, "Vlad", "Ionescu", 234);

        ArrayList<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);

        System.out.println("ArrayList:");
        for (Student student : list) {
            System.out.println(student);
        }

        LinkedList<Student> linkedList = new LinkedList<>();
        linkedList.add(s1);
        linkedList.add(s2);
        linkedList.add(s3);
        linkedList.add(s4);

        System.out.println("\nLinkedList:");
        for (Student student : linkedList) {
            System.out.println(student);
        }

        HashSet<Student> hashSet = new HashSet<>();
        hashSet.add(s1);
        hashSet.add(s2);
        hashSet.add(s3);
        hashSet.add(s4);

        System.out.println("\nHashSet:");
        for (Student student : hashSet) {
            System.out.println(student);
        }

        TreeSet<Student> treeSet = new TreeSet<>();
        treeSet.add(s1);
        treeSet.add(s2);
        treeSet.add(s3);
        treeSet.add(s4);

        System.out.println("\nTreeSet:");
        for (Student student : treeSet) {
            System.out.println(student);
        }

        boolean prezentInLista = s1.studentPrezent(list);
        System.out.println("\nStudentul este prezent: " + prezentInLista);

        boolean prezentInSet = s1.studentPrezentSet(hashSet);
        System.out.println("Studentul este prezent: " + prezentInSet);


        List<Student> studenti = new ArrayList<>();

        try {

            File fisierIntrare = new File("src/main/resources/studenti.csv");
            if (fisierIntrare.exists()) {
                try (Scanner scanner = new Scanner(fisierIntrare)) {
                    while (scanner.hasNextLine()) {
                        String linie = scanner.nextLine();
                        if (linie.isBlank()) continue;

                        String[] date = linie.split(",");
                        int nrMatricol = Integer.parseInt(date[0].trim());
                        String nume = date[1].trim();
                        String prenume = date[2].trim();
                        int formatieDeStudiu = Integer.parseInt(date[3].trim());

                        studenti.add(new Student(nrMatricol, prenume, nume, formatieDeStudiu));
                    }
                }
            }

            studenti = sortByName(studenti);
            outputStudentList(studenti);

        } catch (IOException e) {
            System.err.println("\nEroare");
        }


        HashMap<Student, Integer> noteStudenti = new HashMap<>();

        try {
            File fNote = new File("src/main/resources/note.csv");
            if (fNote.exists()) {
                try (Scanner scannerNote = new Scanner(fNote)) {
                    while (scannerNote.hasNextLine()) {
                        String linie = scannerNote.nextLine();
                        if (linie.trim().isEmpty()) continue;

                        String[] date = linie.split(",");
                        int matricolCsv = Integer.parseInt(date[0].trim());
                        int notaCsv = Integer.parseInt(date[1].trim());

                        Student studentGasit = null;
                        for (Student s : studenti) {
                            if (s.getNrMatricol() == matricolCsv) {
                                studentGasit = s;
                                break;
                            }
                        }

                        if (studentGasit != null) {
                            noteStudenti.put(studentGasit, notaCsv);
                        }
                    }
                }
            } else {
                System.out.println("\nFisierul note.csv nu a fost gasit!");
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea notelor ");
        }


        Scanner tastatura = new Scanner(System.in);
        System.out.print("\nIntroduceti prenumele studentului pentru a vedea nota: ");
        String prenumeCautat = tastatura.nextLine();

        Student studentGasit = null;
        for (Student s : studenti) {
            if (s.getPrenume().equalsIgnoreCase(prenumeCautat)) {
                studentGasit = s;
                break;
            }
        }

        if (studentGasit != null) {
            int nota = notaStudent(studentGasit, noteStudenti);
            System.out.println("Studentul " + studentGasit.getPrenume() + " are nota: " + (nota != -1 ? nota : "Nu are nota"));
        } else {
            System.out.println("Studentul nu a fost gasit.");
        }

        List<StudentCuNota> listaCuNote = new ArrayList<>();
        for (Student s : studenti) {
            int notaValoare = notaStudent(s, noteStudenti);
            if (notaValoare != -1) {
                listaCuNote.add(new StudentCuNota(
                        s.getNrMatricol(), s.getPrenume(), s.getNume(), s.getFormatieDeStudiu(), notaValoare));
            }
        }


        List<List<StudentCuNota>> grupe = imparteInDouaFormatii(listaCuNote);

        System.out.println("\nSTUDENTI FORMATIA 1");
        for (StudentCuNota s : grupe.get(0)) {
            System.out.println(s);
        }

        System.out.println("\nSTUDENTI FORMATIA 2");
        for (StudentCuNota s : grupe.get(1)) {
            System.out.println(s);
        }

        if (!listaCuNote.isEmpty()) {
            int matricolDeMutat = listaCuNote.get(0).getNrMatricol();
            mutaStudentInFormatie(listaCuNote, matricolDeMutat, 1);
        }

        String fisierExcel = "laborator8_students.xlsx";
        salveazaStudentiNoteXlsx(listaCuNote, fisierExcel);

        ArrayList<StudentCuNota> studentiDinXlsx = citesteStudentiNoteXlsx(fisierExcel);

        System.out.println("\nDatele citite din Excel sunt:");
        for (StudentCuNota s : studentiDinXlsx) {
            System.out.println(s);
        }
        tastatura.close();
    }
}


