package com.assessment.flights.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * FileReader class that will read json file
 * @param <T>
 */
public class FileReader<T> {

    /***
     * FileReader class that will read json file with array values and translate to String objects list
     * @param filePath
     * @param t
     * @return List<String>
     * @throws IOException
     */
    public List<String> jsonReader(String filePath, T t) throws IOException {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert to Event object
        List<T> objs = objectMapper.readValue(new File(filePath), new TypeReference<List<T>>(){});

        List<String> objsStrings = new ArrayList<String>();
        for(T item: objs){
            String itemString = objectMapper.writeValueAsString(item);
            objsStrings.add(itemString);
        }

        return objsStrings;
    }
}
