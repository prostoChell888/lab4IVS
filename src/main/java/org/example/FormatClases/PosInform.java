package org.example.FormatClases;

public class PosInform {

    Double latitude; //распрсить
    Character indicatorNS;
    Double longitude;
    Character indicatorEW;

    public PosInform(GGA gga, RMC rmc) {
        latitude = (gga.getLatitude() != null)? gga.getLatitude():rmc.getLatitude();
        indicatorNS = (gga.getIndicatorNS() != null)? gga.getIndicatorNS():rmc.getIndicatorNS();
        longitude = (gga.getLongitude() != null)? gga.getLongitude():rmc.getLongitude();
        indicatorEW = (gga.getIndicatorEW() != null)? gga.getIndicatorEW():rmc.getIndicatorEW();
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Character getIndicatorNS() {
        return indicatorNS;
    }

    public void setIndicatorNS(Character indicatorNS) {
        this.indicatorNS = indicatorNS;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Character getIndicatorEW() {
        return indicatorEW;
    }

    public void setIndicatorEW(Character indicatorEW) {
        this.indicatorEW = indicatorEW;
    }

    private double strToDegree(String value) {
        if ("".equals(value))
            return 0;

        double latitudeInDegrees = Math.round((Double.parseDouble(value) / 100));
        latitudeInDegrees += (Double.parseDouble(value) % 100) / 60;
        //System.out.println("градусы = " + latitudeInDegrees);
        return latitudeInDegrees ;
    }
}
