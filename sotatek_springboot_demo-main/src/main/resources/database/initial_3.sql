DELIMITER $$
CREATE PROCEDURE generate_teacher_data()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE name_varchar VARCHAR(255);
    DECLARE age_int INT;
    DECLARE is_male_boolean BOOLEAN;
    DECLARE address_varchar VARCHAR(1000);

    WHILE i <= 50 DO
            SET name_varchar = CONCAT('Nguyen Van ', i);
            SET address_varchar = 'Ha Noi';
            SET age_int = FLOOR(RAND() * (50 - 25 + 1) + 25); -- Random tuổi từ 25 đến 50
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính

            INSERT INTO teacher (name, age, is_male, address) VALUES (name_varchar, age_int, is_male_boolean, address_varchar);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 100 DO
            SET name_varchar = CONCAT('Nguyen Van ', i);
            SET address_varchar = 'Nam Dinh';
            SET age_int = FLOOR(RAND() * (50 - 25 + 1) + 25); -- Random tuổi từ 25 đến 50
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính

            INSERT INTO teacher (name, age, is_male, address) VALUES (name_varchar, age_int, is_male_boolean, address_varchar);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 150 DO
            SET name_varchar = CONCAT('Le Dinh ', i);
            SET address_varchar = 'Nam Dinh';
            SET age_int = FLOOR(RAND() * (50 - 25 + 1) + 25); -- Random tuổi từ 25 đến 50
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính

            INSERT INTO teacher (name, age, is_male, address) VALUES (name_varchar, age_int, is_male_boolean, address_varchar);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 200 DO
            SET name_varchar = CONCAT('Nguyen Quang ', i);
            SET address_varchar = 'Hai Duong';
            SET age_int = FLOOR(RAND() * (50 - 25 + 1) + 25); -- Random tuổi từ 25 đến 50
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính

            INSERT INTO teacher (name, age, is_male, address) VALUES (name_varchar, age_int, is_male_boolean, address_varchar);

            SET i = i + 1;
        END WHILE;


END$$
DELIMITER ;

call generate_teacher_data();