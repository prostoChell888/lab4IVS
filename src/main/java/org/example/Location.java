package org.example;


public class Location {
    //поля
    String deviceId = "";//индетифекатор

    String date = "";// XXXXXX(6 цифр) XX(Day)XX(Month)XX(Year)!!!!
    String time = "";//XXXXXX(6 цифр) XX(Часы)XX(Минуты)XX (Секунды)!!!!

    String latitude = ""; // Значение широты (разделить на 100)!!!!
    String n_s = "";//N: Север, S: Юг

    String longitude = "";//Значение долготы (разделить на 100)!!!!
    String e_v = "";// E: Восток, W: Запад

    String speed = ""; //Значение скорости (км/ч)!!!!
    String course = ""; //Значение направления(градусы)!!!!

    String altitude = ""; //Высота (метры)!!!!

    //конструктор
    public Location() {
    }
    //=========================сеторы==============================================
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setN_s(String n_s) {
        this.n_s = n_s;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setE_v(String e_v) {
        this.e_v = e_v;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
    //=========================геторы для построения графикоф==============================================


    //
    public double getTimeInHours() throws Exception {
        if ("".equals(time)){
            return 0;
        }
        double timeInHours = Double.parseDouble(time.substring(1));
        timeInHours += Double.parseDouble(time.substring(1, 3)) /60;
        timeInHours += Double.parseDouble(time.substring(3, 5)) /360;

        return timeInHours;
    }

    public double getLatitudeInDegrees() {
        return strToDegree(latitude);
    }

    public double getLongitudeInDegrees() {
        return strToDegree(longitude);
    }

    public double getAltitudeInDegrees() {
        return strToDegree(altitude);
    }

    private double strToDegree(String value) {
        if ("".equals(value))
            return 0;
        double latitudeInDegrees;
        latitudeInDegrees = (Double.parseDouble(value) / 100);
        latitudeInDegrees += (Double.parseDouble(value) % 100) / 60;
        return latitudeInDegrees;
    }

    public double getSpeedInKilPerHour() {
        if ("".equals(speed))
            return 0;

        return Double.parseDouble(speed);
    }

    public double getCourse() {
        return Double.parseDouble(course);
    }

    //=========================геторы стандартные==============================================


    public String getDeviceId() {
        return deviceId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getN_s() {
        return n_s;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getE_v() {
        return e_v;
    }

    public String getSpeed() {
        return speed;
    }

    public String getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "====================================\n" +
                "deviceId=" + deviceId + '\n' +
                ", date='" + date + '\'' + '\n' +
                ", time='" + time + '\'' + '\n' +
                ", latitude=" + latitude + '\n' +
                ", n_s=" + n_s + '\n' +
                ", longitude=" + longitude + '\n' +
                ", e_v=" + e_v + '\n' +
                ", speed=" + speed + '\n' +
                ", course=" + course + '\n' +
                ", altitude=" + altitude + '\n' +
                "====================================\n";
    }
}
