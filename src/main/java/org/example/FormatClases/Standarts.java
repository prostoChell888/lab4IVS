package org.example.FormatClases;

import org.example.FormatClases.GGA;
import org.example.FormatClases.GSA;
import org.example.FormatClases.GSV;
import org.example.FormatClases.RMC;

import java.util.ArrayList;
import java.util.List;

public class Standarts {
    public GGA gga = null;
    public GSA gsa = null;
    public List<GSV> gsv;
    public RMC rmc = null;
    public Standarts() {
        gsv = new ArrayList<>();
    }
}
