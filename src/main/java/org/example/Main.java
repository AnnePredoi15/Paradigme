package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static List<Student> sortByName(List<Student> lista) {
        lista.sort(Comparator.comparing(s -> s.getNume()));
        return lista;
    }

    public static List<Student> sortStudents(List<Student> lista) {
        lista.sort(Comparator.comparingInt((Student s) -> s.getFormatieDeStudiu()).thenComparing(s -> s.getNume()));
        return lista;
    }

    public static int notaStudent(Student student, Map<Student, Integer> mapNote) {
        if (mapNote == null) return -1;
        if (mapNote.containsKey(student)) {
            return mapNote.get(student);
        } else {
            return -1;
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


        // --- SECȚIUNEA 2: INTEGRAREA DESIGN PATTERN-ULUI DAO ---

        // Instanțiem obiectele DAO care se ocupă acum de fișiere
        StudentDAO csvDao = new StudentCsvDAO();
        StudentXlsxDAO xlsxDao = new StudentXlsxDAO();

        // Citesc studenții din CSV folosind DAO (fără cod try-catch direct aici)
        List<Student> studenti = csvDao.citesteStudenti("src/main/resources/studenti.csv");

        // Sortez lista de studenți
        studenti = sortByName(studenti);

        // Salvez lista sortată în fișier tot prin intermediul DAO
        csvDao.salveazaStudenti(studenti, "src/main/resources/studentisortati.csv");

        // Citesc notele din fișier folosind DAO
        Map<Student, Integer> noteStudenti = csvDao.citesteNote("src/main/resources/note.csv", studenti);


        // --- SECȚIUNEA 3: Logica aplicației și lucrul cu tastatura (Rămâne neschimbată) ---
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


        // --- SECȚIUNEA 4: Salvare și citire EXCEL prin DAO ---
        String fisierExcel = "laborator8_students.xlsx";

        // Apelăm metodele mutate în StudentXlsxDAO
        xlsxDao.salveazaStudentiNoteXlsx(listaCuNote, fisierExcel);
        ArrayList<StudentCuNota> studentiDinXlsx = xlsxDao.citesteStudentiNoteXlsx(fisierExcel);

        System.out.println("\nDatele citite din Excel sunt:");
        for (StudentCuNota s : studentiDinXlsx) {
            System.out.println(s);
        }


        // --- SECȚIUNEA 5: Procesare Stream API (Rămâne neschimbată) ---
        System.out.println("\nStudenti cu nota 10:");
        listaCuNote.stream()
                .filter(s -> s.getNota() == 10)
                .forEach(System.out::println);

        System.out.println("\nStudenti cu nota sub 5:");
        listaCuNote.stream()
                .filter(s -> s.getNota() < 5)
                .forEach(System.out::println);

        List<StudentCuNota> listaMapata = listaCuNote.stream()
                .map(s -> s.getNota() < 4 ? new StudentCuNota(s.getNrMatricol(), s.getPrenume(), s.getNume(), s.getFormatieDeStudiu(), 4.0) : s)
                .collect(Collectors.toList());
        System.out.println("\nLista cu nota minima 4:");
        listaMapata.forEach(System.out::println);

        double sumaNote = listaCuNote.stream()
                .map(StudentCuNota::getNota)
                .reduce(0.0, (a, b) -> a + b);
        System.out.println("\nSuma notelor tuturor studentilor: " + sumaNote);

        if (!listaCuNote.isEmpty()) {
            double media = listaCuNote.stream()
                    .map(StudentCuNota::getNota)
                    .reduce(0.0, Double::sum) / listaCuNote.size();
            System.out.println("Media notelor: " + String.format("%.2f", media));
        }


        CompletableFuture<List<Student>> taskFisier1 = CompletableFuture.supplyAsync(() ->
                csvDao.citesteStudenti("src/main/resources/studenti.csv")
        );

        CompletableFuture<List<Student>> taskFisier2 = CompletableFuture.supplyAsync(() ->
                csvDao.citesteStudenti("src/main/resources/studenti2.csv")
        );

        List<Student> toateGrupele = taskFisier1.thenCombine(taskFisier2, (lista1, lista2) -> {
            List<Student> listaCombinata = new ArrayList<>();
            listaCombinata.addAll(lista1);
            listaCombinata.addAll(lista2);
            return listaCombinata;
        }).join();

        sortByName(toateGrupele);

        System.out.println("\nStudentii combinati din ambele fisiere (Sortati dupa Nume):");
        toateGrupele.forEach(System.out::println);

        tastatura.close();
    }
}