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

        public Integer getSatelliteID() {
            return satelliteID;
        }

        public void setSatelliteID(String satelliteID) {
            if (satelliteID.equals("")) return;
            this.satelliteID = Integer.getInteger(satelliteID);
        }

        public Integer getElevation() {
            return elevation;
        }

        public void setElevation(String elevation) {
            if (elevation.equals("")) return;
            this.elevation = Integer.getInteger(elevation);
        }

        public Integer getAzimuth() {
            return azimuth;
        }

        public void setAzimuth(String azimuth) {
            if (azimuth.equals("")) return;
            this.azimuth = Integer.getInteger(azimuth);
        }

        public Integer getSNR_C_No() {
            return SNR_C_No;
        }

        public void setSNR_C_No(String SNR_C_No) {
            if (SNR_C_No.equals("")) return;
            this.SNR_C_No = Integer.getInteger(SNR_C_No);
        }
    }

    Integer numberOfMessages;
    Integer messageNumber;
    Integer satellitesInView;
    List<GSVinf> gsVinfList;

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(String numberOfMessages) {
        if (numberOfMessages.equals("")) return;
        this.numberOfMessages = Integer.getInteger(numberOfMessages);
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        if (messageNumber.equals("")) return;
        this.messageNumber = Integer.getInteger(messageNumber);
    }

    public Integer getSatellitesInView() {
        return satellitesInView;
    }

    public void setSatellitesInView(String satellitesInView) {
        if (satellitesInView.equals("")) return;
        this.satellitesInView = Integer.getInteger(satellitesInView);
    }

    public List<GSVinf> getGsVinfList() {
        return gsVinfList;
    }

    public void addGsVinfList(GSVinf gsVinfList) {
        this.gsVinfList.add(gsVinfList);
    }
}

