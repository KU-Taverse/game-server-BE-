CREATE TABLE map_user (
                      user_id VARCHAR(255) NOT NULL PRIMARY KEY,
                      position_x DOUBLE NOT NULL,
                      position_y DOUBLE NOT NULL,
                      position_z DOUBLE NOT NULL,
                      rotation_pitch DOUBLE NOT NULL,
                      rotation_yaw DOUBLE NOT NULL,
                      rotation_roll DOUBLE NOT NULL,
                      status VARCHAR(50) NOT NULL,
                      velocity_x DOUBLE NOT NULL,
                      velocity_y DOUBLE NOT NULL,
                      velocity_z DOUBLE NOT NULL,
                      local_date_time DATETIME
);