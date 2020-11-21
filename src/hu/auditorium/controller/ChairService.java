package hu.auditorium.controller;

import hu.auditorium.model.domain.Chair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChairService {

    private static final int MAX_NUMBER = 20;
    private static final int MAX_ROW = 15;

    private final List<Chair> chairs;

    public ChairService(List<Chair> chairs) {
        this.chairs = chairs;
    }

    /**
     * 2. feladat: Kérje be a felhasználótól egy sor,
     * és azon belül egy szék számát, majd írassa ki a képernyőre,
     * hogy az adott hely még szabad-e vagy már foglalt!
     */
    public String isGivenChairOccupied(int row, int number) {
        return String.format("A megadott szék %s",
                isOccupied(row, number) ? "már foglalt." : "még üres.");
    }

    private boolean isOccupied(int row, int number) {
        return chairs.stream()
                .filter(i -> i.isChair(row, number))
                .findAny()
                .map(Chair::isOccupied)
                .orElse(true);
    }

    /**
     * 3. feladat: Határozza meg, hogy hány jegyet adtak el eddig,
     * és ez a nézőtér befogadóképességének hány százaléka!
     * A százalékértéket kerekítse egészre, és az eredményt
     * írassa ki a képernyőre.
     */
    public String getStatistic() {
        long occupiedChairs = countOccupiedChairs();
        long percent = occupiedChairs * 100 / chairs.size() ;
        return String.format("Az előadásra eddig %d jegyet adtak el, " +
                "ez a nézőtér %d%%-a." , countOccupiedChairs(), percent);
    }

    private long countOccupiedChairs() {
        return chairs.stream()
                .filter(Chair::isOccupied)
                .count();
    }

    /**
     * 4. feladat: Határozza meg, hogy melyik árkategóriában adták el
     * a legtöbb jegyet! Az eredményt írassa ki a képernyőre
     */
    public int getMostPopularCategoryId() {
        return getChairCategoryMap().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .get();
    }

    private Map<Integer, Long> getChairCategoryMap() {
        return chairs.stream()
                .filter(Chair::isOccupied)
                .collect(Collectors.groupingBy(Chair::getCategoryId, Collectors.counting()));
    }

    /**
     * 5. feladat: Mennyi lenne a színház bevétele a pillanatnyilag eladott
     * jegyek alapján? Írassa ki az eredményt a képernyőre!
     */
    public int countTotalIncome() {
        return chairs.stream()
                .filter(Chair::isOccupied)
                .mapToInt(Chair::getPrice)
                .sum();
    }

    /**
     * 6. feladat: Mivel az emberek általában nem egyedül mennek színházba,
     * ha egy üres hely mellett nincs egy másik üres hely is, akkor azt
     * nehezebben lehet értékesíteni. Határozza meg, és írassa ki a
     * képernyőre, hogy hány ilyen „egyedülálló” üres hely van a nézőtéren!
     */
    public long countSingleFreeChairs() {
        return chairs.stream()
                .filter(this::isSingleFreeChair)
                .count();
    }
    private boolean isSingleFreeChair(Chair chair) {
        int row = chair.getRow();
        int number = chair.getNumber();
        return !chair.isOccupied()
                && isOccupied(row, number - 1)
                && isOccupied(row, number + 1);
    }

    /**
     * 7. feladat: A színház elektronikus eladási rendszere az
     * érdeklődőknek az üres helyek esetén a hely árkategóriáját
     * jeleníti meg, míg a foglalt helyeket csak egy „x”
     * karakterrel jelzi.
     */
    public List<String> getAuditoriumStatus() {
        String auditoriumStatusInRow = getAuditoriumStatusInRow();
        return IntStream.range(0, MAX_ROW)
                .mapToObj(row -> printChairsInRow(auditoriumStatusInRow, row))
                .collect(Collectors.toList());
    }

    private String getAuditoriumStatusInRow() {
        return chairs.stream()
                .map(Chair::toString)
                .collect(Collectors.joining());
    }

    private String printChairsInRow(String auditoriumStatusInRow, int row) {
        return auditoriumStatusInRow.substring(row * MAX_NUMBER, (row + 1) * MAX_NUMBER);
    }
}
