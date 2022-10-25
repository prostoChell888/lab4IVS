package org.example;

import org.example.FormatClases.GGA;
import org.example.FormatClases.GSA;
import org.example.FormatClases.GSV;
import org.example.FormatClases.RMC;

import java.util.logging.Logger;

public class ParserCordFormats {


    static public GGA GGA_Parser(String[] data) {
        var gga = new GGA();
        gga.setTimeUTC(data[1]);
        gga.setLatitude(data[2]);
        gga.setIndicatorNS(data[3]);
        gga.setLongitude(data[4]);
        gga.setIndicatorEW(data[5]);
        gga.setPositionFixIndicator(data[6]);
        gga.setSatellitesUsed(data[7]);
        gga.setHDOP(data[8]);
        gga.setMSLAltitude(data[9]);
        gga.setUnits1(data[10]);
        gga.setUnits2(data[11]);
        gga.setAgeOfDiffCorr(data[12]);
        gga.setDiffrefstationID(data[13]);

        return gga;
    }


    static public GSA GSA_Parser(String[] data) throws Exception {
        Integer i = 0;
        var gsa = new GSA();
        try {

            gsa.setMode1(data[1]);
            gsa.setMode2(data[2]);

            for (i = 3; !data[i].contains("."); i++) {

                gsa.addSatelliteUsed(data[i]);
            }
            gsa.setPDOP(data[i++]);
            gsa.setHDOP(data[i++]);
            gsa.setVDOP(data[i]);
        } catch (Exception ex) {
            throw new Exception("Ошибка на индексе" + i);
        }

        return gsa;
    }

    static public GSV GSV_Parser(String[] data) {
        var gsv = new GSV();
        gsv.setNumberOfMessages(data[1]);
        gsv.setMessageNumber(data[2]);
        gsv.setSatellitesInView(data[3]);
        for (int i = 4; (data.length - i) > 4; i += 4) {
            GSV.GSVinf gsVinf = new GSV.GSVinf();

            gsVinf.setSatelliteID(data[i]);
            gsVinf.setElevation(data[i + 1]);
            gsVinf.setAzimuth(data[i + 2]);
            gsVinf.setSNR_C_No(data[i + 3]);

            gsv.addGsVinf(gsVinf);
        }

        return gsv;
    }

    static public RMC RMC_Parser(String[] data) {
        var rmc = new RMC();

        rmc.setTimeUTC(data[1]);
        rmc.setStatus(data[2]);
        rmc.setLatitude(data[3]);
        rmc.setIndicatorNS(data[4]);
        rmc.setLongitude(data[5]);
        rmc.setIndicatorEW(data[6]);
        rmc.setSpeedOverGround(data[7]);
        rmc.setCourseOverGround(data[8]);
        rmc.setDate(data[9]);
        rmc.setMagneticVariation(data[10]);

        return rmc;
    }


}
