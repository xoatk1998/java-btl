DELIMITER $$
CREATE PROCEDURE generate_student_data()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE name_varchar VARCHAR(255);
    DECLARE age_int INT;
    DECLARE is_male_boolean BOOLEAN;
    DECLARE class_id_int INT;

    WHILE i <= 100 DO
            SET name_varchar = CONCAT('Le Viet ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 200 DO
            SET name_varchar = CONCAT('Le Dinh ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 300 DO
            SET name_varchar = CONCAT('Nguyen Thi ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 400 DO
            SET name_varchar = CONCAT('Nguyen Thi ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 500 DO
            SET name_varchar = CONCAT('Nguyen Van ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;

    WHILE i <= 600 DO
            SET name_varchar = CONCAT('Do Thi ', i);
            SET age_int = FLOOR(RAND() * (25 - 18 + 1) + 18); -- Random tuổi từ 18 đến 25
            SET is_male_boolean = FLOOR(RAND() * 2); -- Random giới tính
            SET class_id_int = FLOOR(RAND() * 5) + 1; -- Random lớp học từ 1 đến 5

            INSERT INTO student (name, age, is_male, class_id) VALUES (name_varchar, age_int, is_male_boolean, class_id_int);

            SET i = i + 1;
        END WHILE;
END$$
DELIMITER ;

call generate_student_data();