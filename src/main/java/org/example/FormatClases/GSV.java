package org.example.FormatClases;

import java.util.Date;
import java.util.List;

//объединение будет до закидывания на сервер
//для этого создастся отдельный объект для добавление на сервер

public class GSV {
    public static class GSVinf {
        Integer satelliteID;
        Integer elevation;
        Integer azimuth;
        Integer SNR_C_No;
    }

    Integer numberOfMessages;
    Integer messageNumber;
    Integer satellitesInView;
    List<GSVinf> gsVinfList;
}

