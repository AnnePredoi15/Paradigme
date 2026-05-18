package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentXlsxDAO {

    public void salveazaStudentiNoteXlsx(List<StudentCuNota> studenti, String numeFisier) {
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
            System.out.println("\n[Excel] Fisier salvat cu succes: " + numeFisier);
        } catch (IOException e) {
            System.err.println("Eroare I/O la salvarea Excel: " + e.getMessage());
        }
    }

    public ArrayList<StudentCuNota> citesteStudentiNoteXlsx(String numeFisier) {
        ArrayList<StudentCuNota> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(numeFisier);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

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
}