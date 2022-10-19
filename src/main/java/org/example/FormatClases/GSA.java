package org.example.FormatClases;

import java.util.List;

public class GSA {
    Character Mode1;
    Integer Mode2;
    List<Integer> SatelliteUsed;
    Double PDOP;
    Double HDOP;
    Double VDOP;
    Integer checksum;

    public Character getMode1() {
        return Mode1;
    }

    public void setMode1(String mode1) {
        if (mode1.equals("")) return;
        Mode1 = mode1.charAt(0);
    }

    public Integer getMode2() {
        return Mode2;
    }

    public void setMode2(String mode2) {
        if (mode2.equals("")) return;
        Mode2 = Integer.getInteger(mode2);
    }

    public List<Integer> getSatelliteUsed() {
        return SatelliteUsed;
    }

    public void addSatelliteUsed(String satelliteUsed) {
        if (satelliteUsed.equals("")) return;
        SatelliteUsed.add(Integer.getInteger(satelliteUsed));
    }

    public Double getPDOP() {
        return PDOP;
    }

    public void setPDOP(String PDOP) {
        if (PDOP.equals("")) return;
        this.PDOP = Double.parseDouble(PDOP);
    }

    public Double getHDOP() {
        return HDOP;
    }

    public void setHDOP(String HDOP) {
        if (HDOP.equals("")) return;
        this.HDOP = Double.parseDouble(HDOP);
    }

    public Double getVDOP() {
        return VDOP;
    }

    public void setVDOP(String VDOP) {
        if (VDOP.equals("")) return;
        this.VDOP = Double.parseDouble(VDOP);
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        if (checksum.equals("")) return;
        this.checksum = Integer.getInteger(checksum);
    }
}
