DROP TABLE IF EXISTS location_information;
DROP TABLE IF EXISTS RMC;
DROP TABLE IF EXISTS GGA;
DROP TABLE IF EXISTS pos_inform;
DROP TABLE IF EXISTS GSA;
DROP TABLE IF EXISTS Satellite_Useds;
DROP TABLE IF EXISTS GSV_inf;
DROP TABLE IF EXISTS GSV;


CREATE TABLE navigation_device
(
    navigation_device_id   SERIAL PRIMARY KEY,
    name varchar(30)
);

CREATE TABLE satellite_useds
(
    satellite_useds_id SERIAL PRIMARY KEY,
    satellite_used_1   INT,
    satellite_used_2   INT,
    satellite_used_3   INT,
    satellite_used_4   INT,
    satellite_used_5   INT,
    satellite_used_6   INT,
    satellite_used_7   INT,
    satellite_used_8   INT,
    satellite_used_9   INT,
    satellite_used_10  INT,
    satellite_used_11  INT,
    satellite_used_12  INT
);

CREATE TABLE GSA
(
    GSA_id                 SERIAL PRIMARY KEY,
    mode1              CHAR,
    mode2              INT,
    satellite_useds_id INT REFERENCES satellite_useds (satellite_useds_id),
    PDOP               FLOAT,
    HDOP               FLOAT,
    VDOP               FLOAT
);


CREATE TABLE pos_inform
(
    pos_inform_id SERIAL PRIMARY KEY,

    UTC_time      time,
    latitude      float,
    N_S_indicator CHAR,
    longitude     float,
    E_W_Indicator CHAR

);

CREATE TABLE RMC
(

    RMC_id                 SERIAL PRIMARY KEY,
    pos_inform_id      INT REFERENCES pos_inform (pos_inform_id),
    status             CHAR,
    speed_over_ground  FLOAT,
    course_over_ground FLOAT,
    date_f             DATE,
    magnetic_variation CHAR
);

CREATE TABLE GGA
(
    GGA_id                     SERIAL PRIMARY KEY,
    pos_inform_id          INT REFERENCES pos_inform (pos_inform_id),
    position_fix_indicator INT,
    satellites_used        INT,
    HDOР                   FLOAT,
    MSL_altitude           FLOAT,
    units1                 CHAR,
    geoid_separation1      FLOAT,
    units2                 CHAR,
    age_of_diff_corr       INT,
    diff_ref_station_id    varchar(30)
);

CREATE TABLE GSV
(
    GSV_id             SERIAL PRIMARY KEY,
    number_of_messages INT,
    message_number     INT,
    satellites_in_view INT
);

CREATE TABLE GSV_inf
(
    id           SERIAL PRIMARY KEY,
    GSV_id       INT REFERENCES GSV (GSV_id),
    satellite_id INT,
    elevation    INT,
    azimuth      INT,
    snr_c_no     INT
);

CREATE TABLE location_information
(
    id     SERIAL PRIMARY KEY,
    device_id INT REFERENCES navigation_device (navigation_device_id),
    GSV_id INT REFERENCES GSV (GSV_id),
    GSA_id INT REFERENCES GSA (GSA_id),
    RMC_id INT REFERENCES RMC (RMC_id),
    GGA_id INT REFERENCES GGA (GGA_id)

);

-- CREATE TABLE location_information(
--     id SERIAL PRIMARY KEY,
--
--
-- )


