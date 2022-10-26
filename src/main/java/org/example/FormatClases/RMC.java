package org.example.FormatClases;

import java.util.Date;

//идет вторым по счету
public class RMC {


    Double timeUTC;//привести к нужному формату(возможно к дабл)
    Character status;
    Double latitude; //распрсить
    Character indicatorNS;
    Double longitude;
    Character indicatorEW;
    Double speedOverGround;
    Double courseOverGround;
    String date; //распарсить и привести к нужному значению
    Character magneticVariation;
    Integer checksum;

    public Double getTimeUTC() {
        return timeUTC;
    }

    public void setTimeUTC(String timeUTC) {
        if (timeUTC.equals("")) return;


        double timeInHours = Double.parseDouble(timeUTC.substring(0, 2)) + 3;
        timeInHours += Double.parseDouble(timeUTC.substring(2, 4)) / 60;
        timeInHours += Double.parseDouble(timeUTC.substring(4, 6)) / 360;


        this.timeUTC = timeInHours;
    }
    public Character getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("")) return;
        this.status = status.charAt(0);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (latitude.equals("")) return;
        this.latitude = Double.parseDouble(latitude);
    }

    public Character getIndicatorNS() {
        return indicatorNS;
    }

    public void setIndicatorNS(String indicatorNS) {
        if (indicatorNS.equals("")) return;
        this.indicatorNS = indicatorNS.charAt(0);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        if (longitude.equals("")) return;
        this.longitude = Double.parseDouble(longitude);
    }

    public Character getIndicatorEW() {
        return indicatorEW;
    }

    public void setIndicatorEW(String indicatorEW) {
        if (indicatorEW.equals("")) return;
        this.indicatorEW = indicatorEW.charAt(0);
    }

    public Double getSpeedOverGround() {
        return speedOverGround;
    }

    public void setSpeedOverGround(String speedOverGround) {
        if (speedOverGround.equals("")) return;
        this.speedOverGround = Double.parseDouble(speedOverGround);
    }

    public Double getCourseOverGround() {
        return courseOverGround;
    }

    public void setCourseOverGround(String courseOverGround) {
        if (courseOverGround.equals("")) return;
        this.courseOverGround = Double.parseDouble(courseOverGround);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date.equals("")) return;

        String day = date.substring(0, 2);
        String moth = date.substring(2, 4);
        String year = date.substring(4, 6);

        String stringBuilder = "20" + year + "-" + moth + "-" + day;

        this.date = stringBuilder;
    }

    public Character getMagneticVariation() {
        return magneticVariation;
    }

    public void setMagneticVariation(String magneticVariation) {
        if (magneticVariation.equals("")) return;
        this.magneticVariation = magneticVariation.charAt(0);
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        if (checksum.equals("")) return;
        this.checksum = Integer.parseInt(checksum);
    }
}
