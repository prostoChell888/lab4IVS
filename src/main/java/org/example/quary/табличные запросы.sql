--GGA формата
SELECT UTC_date,
       date_of_locate,
       latitude,
       N_S_indicator,
       longitude,
       E_W_Indicator,
       position_fix_indicator,
       satellites_used,
       hdop,
       units2
FROM gga
         JOIN location_information USING (location_information_id)
         JOIN pos_inform USING (pos_inform_id)
ORDER BY  UTC_date;

--RMC формата
SELECT UTC_date,
       date_of_locate,
       latitude,
       N_S_indicator,
       longitude,
       E_W_Indicator,
       speed_over_ground,
       course_over_ground
FROM rmc
         JOIN location_information USING (location_information_id)
         JOIN pos_inform USING (pos_inform_id)
ORDER BY UTC_date;

--GSA
SELECT UTC_date,
       date_of_locate,
       PDOP,
       HDOP,
       VDOP,
       mode1,
       mode2
FROM gsa
         JOIN location_information USING (location_information_id)
ORDER BY  UTC_date;

--GSV
SELECT UTC_date,
       date_of_locate,
       satellite_id,
       azimuth,
       elevation,
       snr_c_no
FROM gsv
         JOIN location_information USING (location_information_id)
ORDER BY  UTC_date;







