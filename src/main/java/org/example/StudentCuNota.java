package org.example;

public class StudentCuNota extends Student {
double nota;
    public StudentCuNota(int nrMatricol, String prenume, String nume, int formatieDeStudiu, double nota) {
        super(nrMatricol, prenume, nume, formatieDeStudiu);
        this.nota = nota;
}
    @Override
    public String toString() {
        return super.toString() + " | Nota: " + nota;
    }
}
