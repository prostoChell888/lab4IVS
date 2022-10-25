DROP TABLE IF EXISTS location_information;
DROP TABLE IF EXISTS pos_inform;
DROP TABLE IF EXISTS RMC;
DROP TABLE IF EXISTS GGA;
DROP TABLE IF EXISTS GSA;
DROP TABLE IF EXISTS GSV;



CREATE TABLE location_information
(
    location_information_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    device_id               INT,
    UTC_date                date,

    CONSTRAINT PK_location_inform_id PRIMARY KEY (location_information_id)


);


CREATE TABLE pos_inform
(
    pos_inform_id INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    latitude      float,
    N_S_indicator CHAR,
    longitude     float,
    E_W_Indicator CHAR,

    CONSTRAINT PK_pos_inform_pos_inform_id PRIMARY KEY (pos_inform_id)

);

CREATE TABLE RMC
(
    RMC_id                  INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    location_information_id INT,
    pos_inform_id           INT,
    status                  CHAR,
    speed_over_ground       FLOAT,
    course_over_ground      FLOAT,
    date_f                  DATE,
    magnetic_variation      CHAR,

    CONSTRAINT PK_RMC_RMC_id PRIMARY KEY (RMC_id),

    CONSTRAINT FK_RMC_l_pos_inform_id FOREIGN KEY (pos_inform_id)
        REFERENCES pos_inform (pos_inform_id),

    CONSTRAINT FK_RMC_location_information_id FOREIGN KEY (location_information_id)
        REFERENCES location_information (location_information_id)
);

CREATE TABLE GGA
(
    GGA_id                  INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    location_information_id INT,
    pos_inform_id           INT,
    position_fix_indicator  INT,
    satellites_used         INT,
    HDOР                    FLOAT,
    MSL_altitude            FLOAT,
    units1                  CHAR,
    geoid_separation1       FLOAT,
    units2                  CHAR,
    age_of_diff_corr        INT,
    diff_ref_station_id     varchar(30),

    CONSTRAINT PK_GGA_GGA_id PRIMARY KEY (GGA_id),

    CONSTRAINT FK_GGA_pos_inform_id FOREIGN KEY (pos_inform_id)
        REFERENCES pos_inform (pos_inform_id),

    CONSTRAINT FK_GGA_location_information_id FOREIGN KEY (location_information_id)
        REFERENCES location_information (location_information_id)
);

CREATE TABLE GSA
(
    GSA_id                  INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    location_information_id INT,
    mode1                   CHAR,
    mode2                   INT,
    PDOP                    FLOAT,
    HDOP                    FLOAT,
    VDOP                    FLOAT,

    CONSTRAINT PK_GSA_GSA_id PRIMARY KEY (GSA_id),

    CONSTRAINT FK_GSA_location_information_id FOREIGN KEY (location_information_id)
        REFERENCES location_information (location_information_id)
);



CREATE TABLE GSV
(
    GSV_id                  INT GENERATED ALWAYS AS IDENTITY NOT NULL,
    location_information_id INT,
    GSA_id                  INT,
    satellite_id            INT,
    elevation               INT,
    azimuth                 INT,
    snr_c_no                INT,

    CONSTRAINT PK_GSV_GSV_id PRIMARY KEY (GSV_id),

    CONSTRAINT FK_GSV_location_information_id FOREIGN KEY (location_information_id)
        REFERENCES location_information (location_information_id),

    CONSTRAINT FK_GSV_GSA_id FOREIGN KEY (GSA_id)
        REFERENCES GSA (GSA_id)
);





