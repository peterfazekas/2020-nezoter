package hu.auditorium.model.service;

import hu.auditorium.model.domain.Chair;

import java.util.List;

public class DataApi {

    private final FileReader fileReader;
    private final DataParser dataParser;

    public DataApi(FileReader fileReader, DataParser dataParser) {
        this.fileReader = fileReader;
        this.dataParser = dataParser;
    }

    public List<Chair> getChairs(String occupationName, String categoryName) {
        List<String> occupationList = fileReader.read(occupationName);
        List<String> categoryList = fileReader.read(categoryName);
        return dataParser.parse(occupationList, categoryList);
    }
}
