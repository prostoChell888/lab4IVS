package org.example.FormatClases;

import java.sql.Date;

public class CSVLocalnfo {
    Float timeUTC;
    Double latitude;
    Double longitude;
    Double speedOverGround;
    Date date; //распарсить и привести к нужному значению
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getTimeUTC() {
        return timeUTC;
    }

    public void setTimeUTC(String timeUTC) {
        if (timeUTC.equals("")) return;

        Float timeInHours = Float.parseFloat(timeUTC.substring(0, 2)) + 3;
        timeInHours += Float.parseFloat(timeUTC.substring(2, 4)) / 60;
        timeInHours += Float.parseFloat(timeUTC.substring(4, 6)) / 360;

        this.timeUTC = timeInHours;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (latitude.equals("")) return;
        this.latitude = Double.parseDouble(latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        if (longitude.equals("")) return;
        this.longitude = Double.parseDouble(longitude);
    }

    public Double getSpeedOverGround() {
        return speedOverGround;
    }

    public void setSpeedOverGround(String speedOverGround) {
        if (speedOverGround.equals("")) return;
        this.speedOverGround = Double.parseDouble(speedOverGround);
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(String date)  {
        if (date.equals("")) return;

        String day = date.substring(0, 2);
        String moth = date.substring(2, 4);
        String year = date.substring(4, 6);

        this.date =  java.sql.Date.valueOf("20" + year + "-" + moth + "-" + day);

    }


}
