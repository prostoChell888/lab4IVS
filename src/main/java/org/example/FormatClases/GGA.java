package org.example.FormatClases;

public class GGA {
    private Float TimeUTC;//распарсить в часы
    private Double latitude; //распрсить
    private Character indicatorNS;
    private Double longitude;
    private Character indicatorEW;
    private Integer positionFixIndicator;
    private Integer satellitesUsed;
    private Double HDOP;
    private Double MSLAltitude;
    private Character units1;
    private Double geoidSeparation1;
    private Character units2;
    private String ageOfDiffCorr;
    private Integer diffrefstationID;


    private double strToDegree(String value) {
        if ("".equals(value))
            return 0;
        double latitudeInDegrees;
        latitudeInDegrees = (Double.parseDouble(value) / 100);
        latitudeInDegrees += (Double.parseDouble(value) % 100) / 60;
        return latitudeInDegrees;
    }

    public Float getTimeUTC() {
        return TimeUTC;
    }

    public void setTimeUTC(String timeUTC) {
        if (timeUTC.equals("")) return;

        Float timeInHours = Float.parseFloat(timeUTC.substring(0, 2)) + 3;
        timeInHours += Float.parseFloat(timeUTC.substring(2, 4)) / 60;
        timeInHours += Float.parseFloat(timeUTC.substring(4, 6)) / 360;

        this.TimeUTC = timeInHours;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (latitude.equals("")) return;
        this.latitude = strToDegree(latitude);
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
        this.longitude = strToDegree(longitude);
    }

    public Character getIndicatorEW() {
        return indicatorEW;
    }

    public void setIndicatorEW(String indicatorEW) {
        if (indicatorEW.equals("")) return;
        this.indicatorEW = indicatorEW.charAt(0);
    }

    public Integer getPositionFixIndicator() {
        return positionFixIndicator;
    }

    public void setPositionFixIndicator(String positionFixIndicator) {
        if (positionFixIndicator.equals("")) return;
        this.positionFixIndicator = Integer.parseInt(positionFixIndicator);
    }

    public Integer getSatellitesUsed() {
        return satellitesUsed;
    }

    public void setSatellitesUsed(String satellitesUsed) {
        if (satellitesUsed.equals("")) return;
        this.satellitesUsed = Integer.parseInt(satellitesUsed);
    }

    public Double getHDOP() {
        return HDOP;
    }

    public void setHDOP(String HDOP) {
        if (HDOP.equals("")) return;
        this.HDOP = Double.parseDouble(HDOP);
    }

    public Double getMSLAltitude() {
        return MSLAltitude;
    }

    public void setMSLAltitude(String MSLAltitude) {
        if (MSLAltitude.equals("")) return;
        this.MSLAltitude = Double.parseDouble(MSLAltitude);
    }

    public Character getUnits1() {
        return units1;
    }

    public void setUnits1(String units1) {
        if (units1.equals("")) return;
        this.units1 = units1.charAt(0);
    }

    public Double getGeoidSeparation1() {
        return geoidSeparation1;
    }

    public void setGeoidSeparation1(String geoidSeparation1) {
        if (geoidSeparation1.equals("")) return;
        this.geoidSeparation1 = Double.parseDouble(geoidSeparation1);
    }

    public Character getUnits2() {
        return units2;
    }

    public void setUnits2(String units2) {
        if (units2.equals("")) return;
        this.units2 = units2.charAt(0);
    }

    public String getAgeOfDiffCorr() {
        return ageOfDiffCorr;
    }

    public void setAgeOfDiffCorr(String ageOfDiffCorr) {
        if (ageOfDiffCorr.equals("")) return;
        this.ageOfDiffCorr = ageOfDiffCorr;
    }

    public Integer getDiffrefstationID() {
        return diffrefstationID;
    }

    public void setDiffrefstationID(String diffrefstationID) {
        if (diffrefstationID.equals("")) return;
        this.diffrefstationID = Integer.parseInt(diffrefstationID);
    }


}
