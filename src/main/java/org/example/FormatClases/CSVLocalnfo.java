package org.example.FormatClases;

import java.sql.Date;

public class CSVLocalnfo {
    Date date = null; //распарсить и привести к нужному значению
    Float timeUTC = null;
    Double latitude  = null ;
    Double longitude = null;
    Double speedOverGround = null;
    String address = null;

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

        String[] timeInArr = timeUTC.split(":");

        Float timeInHours = Float.parseFloat(timeInArr[0]) + 3;
        timeInHours += Float.parseFloat(timeInArr[1]) / 60;
        timeInHours += Float.parseFloat(timeInArr[2]) / 360;

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

    public Date getDate() {
        return date;
    }

    public void setDate(String date)  {
        if (date.equals("")) return;
        this.date =  Date.valueOf(date);

    }

//    private double strToDegree(String value) {
//        if ("".equals(value))
//            return 0;
//
//        double latitudeInDegrees = Math.round((Double.parseDouble(value) / 100));
//        latitudeInDegrees += (Double.parseDouble(value) % 100) / 60;
//        //System.out.println("градусы = " + latitudeInDegrees);
//        return latitudeInDegrees ;
//    }


}
