package org.example;

import java.io.*;
import java.util.*;

public class Main {

    public static List<Student> sortByName(List<Student> lista) {
        lista.sort(Comparator.comparing(s -> s.nume));
        return lista;
    }

    public static List<Student> sortStudents(List<Student> lista) {
        lista.sort(Comparator.comparingInt((Student s) -> s.formatieDeStudiu).thenComparing(s -> s.nume));
        return lista;
    }

    public static void outputStudentList(List<Student> lista) {
        try (PrintWriter writer = new PrintWriter("src/main/resources/studentisortati.csv")) {
            for (Student s : lista) {
                writer.println(s.nrMatricol + "," + s.nume + "," + s.prenume + "," + s.formatieDeStudiu);
            }
            System.out.println("\nFisierul sortat a fost creat cu succes!");
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
                writer.println(s.nrMatricol + "," + s.nume + "," + s.prenume + "," + s.formatieDeStudiu + "," + s.nota);
            }
            System.out.println("\nFisierul cu studenti si note a fost creat cu succes!");
        } catch (Exception e) {
            System.out.println("\nEroare la scrierea in fisier: " + e.getMessage());
        }
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

            studenti = sortStudents(studenti);
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
                            if (s.nrMatricol == matricolCsv) {
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
        String prenumeIntrodus = tastatura.nextLine();

        Student studentPentruCautare = null;
        for (Student s : studenti) {
            if (s.prenume.equals(prenumeIntrodus)) {
                studentPentruCautare = s;
                break;
            }
        }

        if (studentPentruCautare != null) {
            int nota = notaStudent(studentPentruCautare, noteStudenti);

            if (nota != -1) {
                System.out.println("Studentul " + studentPentruCautare.prenume + " are nota: " + nota);
            } else {
                System.out.println("Studentul a fost gasit, dar nu are nota inregistrata.");
            }
        } else {
            System.out.println("Nu am gasit niciun student cu acest prenume in baza de date.");
        }
        List<StudentCuNota> lista = new ArrayList<>();

        for (Student s : studenti) {
            int notaValoare = notaStudent(s, noteStudenti);

            if (notaValoare != -1) {
                StudentCuNota sn = new StudentCuNota(s.nrMatricol, s.prenume, s.nume, s.formatieDeStudiu, notaValoare);
                lista.add(sn);
            }
        }
        outputStudentCuNota(lista);
        tastatura.close();
    }
}



