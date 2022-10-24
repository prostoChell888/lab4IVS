package org.example;

import org.example.FormatClases.GGA;
import org.example.FormatClases.GSA;
import org.example.FormatClases.GSV;
import org.example.FormatClases.RMC;

import java.util.ArrayList;
import java.util.List;

public class Standarts {
    public GGA gga;
    public GSA gsa;
    public List<GSV> gsv;
    public RMC rmc;

    public Standarts() {
        this.gga = new GGA();
        this.gsa = new GSA();
        gsv = new ArrayList<>();
        this.rmc = new RMC();
    }
}
