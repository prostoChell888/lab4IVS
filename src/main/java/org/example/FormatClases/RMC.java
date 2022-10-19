package org.example.FormatClases;

import java.util.Date;

//идет вторым по счету
public class RMC {
   // Double timeUTC;//привести к нужному формату(возможно к дабл)
    Character status;
   Double latitude; //распрсить
    Character indicatorNS;
    Double longitude;
    Character indicatorEW;
    Double speedOverGround;
    Double courseOverGround;
    Date date; //распарсить и привести к нужному значению
    Character magneticVariation;
    Integer checksum;
}
