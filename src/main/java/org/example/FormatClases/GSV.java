package org.example.FormatClases;

import java.util.ArrayList;
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

        public Integer getSatelliteID() {
            return satelliteID;
        }

        public void setSatelliteID(String satelliteID) {
            if (satelliteID.equals("")) return;
            this.satelliteID = Integer.parseInt(satelliteID);
        }

        public Integer getElevation() {
            return elevation;
        }

        public void setElevation(String elevation) {
            if (elevation.equals("")) return;
            this.elevation = Integer.parseInt(elevation);
        }

        public Integer getAzimuth() {
            return azimuth;
        }

        public void setAzimuth(String azimuth) {
            if (azimuth.equals("")) return;
            this.azimuth = Integer.parseInt(azimuth);
        }

        public Integer getSNR_C_No() {
            return SNR_C_No;
        }

        public void setSNR_C_No(String SNR_C_No) {
            if (SNR_C_No.equals("")) return;
            this.SNR_C_No = Integer.parseInt(SNR_C_No);
        }
    }

    Integer numberOfMessages;
    Integer messageNumber;
    Integer satellitesInView;
    List<GSVinf> gsVinfList = new ArrayList<>();

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(String numberOfMessages) {
        if (numberOfMessages.equals("")) return;
        this.numberOfMessages = Integer.parseInt(numberOfMessages);

    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        if (messageNumber.equals("")) return;
        this.messageNumber = Integer.parseInt(messageNumber);
    }

    public Integer getSatellitesInView() {
        return satellitesInView;
    }

    public void setSatellitesInView(String satellitesInView) {
        if (satellitesInView.equals("")) return;
        this.satellitesInView = Integer.parseInt(satellitesInView);
    }

    public List<GSVinf> getGsVinfList() {
        return gsVinfList;
    }

    public void addGsVinf(GSVinf gsVinfList) {
        this.gsVinfList.add(gsVinfList);
    }
}

