CREATE TABLE MapUser (
                      userId VARCHAR(255) NOT NULL PRIMARY KEY,
                      positionX DOUBLE NOT NULL,
                      positionY DOUBLE NOT NULL,
                      positionZ DOUBLE NOT NULL,
                      rotationPitch DOUBLE NOT NULL,
                      rotationYaw DOUBLE NOT NULL,
                      rotationRoll DOUBLE NOT NULL,
                      status VARCHAR(50) NOT NULL,
                      velocityX DOUBLE NOT NULL,
                      velocityY DOUBLE NOT NULL,
                      velocityZ DOUBLE NOT NULL,
                      localDateTime DATETIME
);