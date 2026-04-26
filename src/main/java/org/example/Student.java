package org.example;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashSet;

public class Student implements Comparable<Student> {

    private final int nrMatricol;
    private final String prenume;
    private final String nume;
    private final int formatieDeStudiu;

    public Student(int nrMatricol, String prenume, String nume, int formatieDeStudiu) {
        this.nrMatricol = nrMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
    }

    public int getNrMatricol() { return nrMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public int getFormatieDeStudiu() { return formatieDeStudiu; }

    @Override
    public String toString() {
        return "Student{" +
                "nrMatricol=" + nrMatricol +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", formatieDeStudiu=" + formatieDeStudiu +
                '}';
    }

    public boolean studentPrezent(ArrayList<Student> lista) {
        if (lista == null) return false;
        return lista.contains(this);
    }

    public boolean studentPrezentSet(HashSet<Student> set) {
        if (set == null) return false;
        return set.contains(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;

        Student s = (Student) obj;

        return nrMatricol == s.nrMatricol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrMatricol, prenume, nume, formatieDeStudiu);
    }



    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.nrMatricol, other.nrMatricol);
    }
}