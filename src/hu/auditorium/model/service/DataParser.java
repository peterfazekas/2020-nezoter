package hu.auditorium.model.service;

import hu.auditorium.model.domain.Category;
import hu.auditorium.model.domain.Chair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataParser {

    public List<Chair> parse(List<String> occupationList,
                             List<String> categoryList) {
        List<Chair> chairs = new ArrayList<>();
        IntStream.range(0, occupationList.size())
                .forEach(row -> chairs.addAll(
                        parseLine(row, occupationList.get(row),
                                categoryList.get(row))));
        return chairs;
    }

    private List<Chair> parseLine(int row, String occupations,
                                  String categories) {
        return IntStream.range(0, occupations.length())
                .mapToObj(number -> createChair(
                        row + 1, number + 1,
                        occupations.substring(number, number + 1),
                        categories.substring(number, number + 1)))
                .collect(Collectors.toList());
    }

    private Chair createChair(int row, int number,
                              String occupation, String category) {
        int categoryId = Integer.parseInt(category);
        Category cat = Category.byId(categoryId);
        boolean occupied = "x".equals(occupation);
        return new Chair(row, number, cat, occupied);
    }
}
