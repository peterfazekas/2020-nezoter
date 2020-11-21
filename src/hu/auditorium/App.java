package hu.auditorium;

import hu.auditorium.controller.ChairService;
import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.service.*;

import java.util.List;
import java.util.Scanner;

public class App {
    
    private final ChairService chairService;
    private final Console console;
    private final FileWriter fileWriter;
    
    private App() {
        console = new Console(new Scanner(System.in));
        fileWriter = new FileWriter("szabad.txt");
        DataApi dataApi = new DataApi(new FileReader(), new DataParser());
        List<Chair> chairs = dataApi.getChairs("foglaltsag.txt", 
                "kategoria.txt");
        chairService = new ChairService(chairs);
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("2. feladat");
        System.out.println("Adja meg a keresett szék helyét:");
        System.out.print(" -  sor száma: ");
        int row = console.read();
        System.out.print(" - szék száma: ");
        int number = console.read();
        System.out.println(chairService.isGivenChairOccupied(row, number));
        System.out.println("3. feladat");
        System.out.println(chairService.getStatistic());
        System.out.println("4. feladat");
        System.out.println("A legtöbb jegyet a(z) " +
                chairService.getMostPopularCategoryId()+
                ". árkategóriában értékesítették. ");
        System.out.println("5. feladat");
        System.out.println("A pillanatnyilag eladott jegyek alapján a színház bevétele: " +
                chairService.countTotalIncome() + " Ft.");
        System.out.println("6. feladat");
        System.out.println("A nézőtéren jelenleg " +
                chairService.countSingleFreeChairs() +
                " db egyedülálló üres hely van.");
        fileWriter.printAll(chairService.getAuditoriumStatus());
    }
}
