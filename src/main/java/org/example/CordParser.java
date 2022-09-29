package org.example;



import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CordParser {
    public static List<Location> parseTxtFile(File file)
            throws Exception {

        List<Location> listOfLocations = new ArrayList<>();


        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            getCordinatsFromBufReader(listOfLocations, br);

        } catch (Exception ex) {
            throw new Exception("Ошибка преобразования файла\n" +
                    ex.getMessage());
        }

        return listOfLocations;
    }

    private static void getCordinatsFromBufReader(List<Location> listOfLocations,
                                                  BufferedReader br)
            throws IOException {

        Location bufLocation;
        String bufLine;

        while ((bufLine = br.readLine()) != null) {


            String[] arrInfisticInStr = bufLine.split(",");
            bufLocation = parseStrToCord(arrInfisticInStr);
            listOfLocations.add(bufLocation);
        }
    }

    private static Location parseStrToCord(String[] arrInfisticInStr) {
        Location bufLocation;
        bufLocation = new Location();

        bufLocation.setDeviceId(arrInfisticInStr[1]);

        bufLocation.setDate(arrInfisticInStr[2]);
        bufLocation.setTime(arrInfisticInStr[3]);

        bufLocation.setLatitude(arrInfisticInStr[4]);
        bufLocation.setN_s(arrInfisticInStr[5]);

        bufLocation.setLongitude(arrInfisticInStr[6]);
        bufLocation.setE_v(arrInfisticInStr[7]);

        bufLocation.setSpeed(arrInfisticInStr[8]);
        bufLocation.setCourse(arrInfisticInStr[9]);
        bufLocation.setAltitude(arrInfisticInStr[10]);


        return bufLocation;
    }
}

