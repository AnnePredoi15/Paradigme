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
        System.out.println("\nStudent prezent in ArrayList: " + rezultat);

        boolean rezultat1 = StudentPrezent1(hashSet, s2);
        System.out.println("Student prezent in HashSet: " + rezultat1);


        List<Student> studenti = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Studenti.csv"));
            String linie;

            while ((linie = br.readLine()) != null) {

                String[] date = linie.split(",");

                int nrMatricol = Integer.parseInt(date[0]);
                String nume = date[1];
                String prenume = date[2];
                int formatieDeStudiu = Integer.parseInt(date[3]);

                studenti.add(new Student(nrMatricol, prenume, nume, formatieDeStudiu));
            }

            br.close();

            studenti.sort(
                    Comparator.comparing((Student s) -> s.nume)
                            .thenComparing(s -> s.prenume)
                            .thenComparingInt(s -> s.formatieDeStudiu)
                            .thenComparingInt(s -> s.nrMatricol)
            );

            PrintWriter writer = new PrintWriter("src/main/resources/studentisortati.csv");

            for (Student s : studenti) {
                writer.println(s.nrMatricol + "," + s.nume + "," + s.prenume + "," + s.formatieDeStudiu);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}