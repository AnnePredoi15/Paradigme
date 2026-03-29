package org.example;

import java.io.*;
import java.util.*;

public class Main {

    public static boolean StudentPrezent(ArrayList<Student> list, Student cautat) {
        for (Student s : list) {
            if (s.equals(cautat)) {
                return true;
            }
        }
        return false;
    }

    public static boolean StudentPrezent1(HashSet<Student> hashSet, Student cautat) {
        for (Student s : hashSet) {
            if (s.equals(cautat)) {
                return true;
            }
        }
        return false;
    }

    public static void afiseazaNotaDupaPrenume(List<Student> listaStudenti, HashMap<Integer, Integer> note, String prenumeCautat) {
        boolean gasit = false;

        for (Student s : listaStudenti) {
            // Folosim equalsIgnoreCase pentru a nu conta dacă scriem cu litere mari sau mici
            if (s.prenume.equalsIgnoreCase(prenumeCautat)) {
                gasit = true;
                int matricol = s.nrMatricol;

                // Verificăm dacă acest student are notă în HashMap
                if (note.containsKey(matricol)) {
                    int nota = note.get(matricol);
                    System.out.println("Studentul " + s.prenume + " are nota: " + nota);
                } else {
                    System.out.println("Studentul a fost gasit, dar nu are nicio nota inregistrata.");
                }
                // Opțional: break; dacă vrei să se oprească la primul student găsit cu acest nume
            }
        }

        if (!gasit) {
            System.out.println("Nu am gasit niciun student cu prenumele: " + prenumeCautat);
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

        boolean rezultat = StudentPrezent(list, s2);
        System.out.println("\nStudentul s2 prezent in ArrayList: " + rezultat);

        boolean rezultat1 = StudentPrezent1(hashSet, s2);
        System.out.println("\nStudentul s2 prezent in HashSet: " + rezultat1);


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

            studenti.sort(
                    Comparator.comparing((Student s) -> s.nume)
                            .thenComparing(s -> s.prenume)
                            .thenComparingInt(s -> s.formatieDeStudiu)
                            .thenComparingInt(s -> s.nrMatricol)
            );

            try (PrintWriter writer = new PrintWriter("src/main/resources/studentisortati.csv")) {
                for (Student s : studenti) {
                    writer.println(s.nrMatricol + "," + s.nume + "," + s.prenume + "," + s.formatieDeStudiu);
                }
            }

            System.out.println("\nFisierul sortat a fost creat.");

        } catch (IOException e) {
            System.err.println("\nEroare");
        }


        HashMap<Integer, Integer> noteStudenti = new HashMap<>();

        try {
            File fNote = new File("src/main/resources/note.csv");
            if (fNote.exists()) {
                try (Scanner scannerNote = new Scanner(fNote)) {
                    while (scannerNote.hasNextLine()) {
                        String linie = scannerNote.nextLine();
                        if (linie.trim().isEmpty()) continue;

                        String[] date = linie.split(",");
                        int matricol = Integer.parseInt(date[0].trim());
                        int nota = Integer.parseInt(date[1].trim());

                        noteStudenti.put(matricol, nota);
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
        afiseazaNotaDupaPrenume(studenti, noteStudenti, prenumeIntrodus);

        tastatura.close();
    }
}


