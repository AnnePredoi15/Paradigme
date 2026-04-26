package org.example;

public final class StudentCuNota extends Student {

    private final double nota;

    public StudentCuNota(int nrMatricol, String prenume, String nume, int formatieDeStudiu, double nota) {
        super(nrMatricol, prenume, nume, formatieDeStudiu);
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }

    public StudentCuNota cuNouaFormatie(int nouaFormatie) {
        return new StudentCuNota(
                this.getNrMatricol(),
                this.getPrenume(),
                this.getNume(),
                nouaFormatie,
                this.nota);
    }

    @Override
    public String toString() {
        return super.toString() + " | Nota: " + nota;
    }
}