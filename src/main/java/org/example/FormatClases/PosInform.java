package org.example.FormatClases;

public class PosInform {
    Double TimeUTC;//распарсить в часы
    Double latitude; //распрсить
    Character indicatorNS;
    Double longitude;
    Character indicatorEW;

    public Double getTimeUTC() {
        return TimeUTC;
    }

    public void setTimeUTC(Double timeUTC) {
        TimeUTC = timeUTC;
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
}
