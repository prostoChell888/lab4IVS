--данные в формате RMC
SELECT UTC_date,
       status,
       latitude,
       N_S_indicator,
       longitude,
       E_W_Indicator,
       speed_over_ground,
       course_over_ground,
       magnetic_variation
FROM rmc
         JOIN pos_inform USING (pos_inform_id)
         JOIN location_information USING (location_information_id);

SELECT UTC_date,
       latitude,
       N_S_indicator,
       longitude,
       E_W_Indicator,
       position_fix_indicator,
       satellites_used,
       HDOP,
       MSL_altitude,
       units1,
       geoid_separation1,
       units2,
       age_of_diff_corr,
       diff_ref_station_id
FROM GGA
         JOIN pos_inform USING (pos_inform_id)
         JOIN location_information USING (location_information_id);


SELECT *
FROM GSA;

SELECT satellite_id
FROM gsv
WHERE GSA_id = 16;


--все уникльные id спутников
SELECT DISTINCT(satellite_id)
FROM gsv;

--долготоа и широта спутника с заднмы id
SELECT  elevation, azimuth
FROM gsv
WHERE satellite_id = 10;