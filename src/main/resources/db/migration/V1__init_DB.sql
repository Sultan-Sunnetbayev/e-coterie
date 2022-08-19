CREATE TABLE `roles`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(50) NOT NULL UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `roles_id_pk`
        PRIMARY KEY(`id`)
);

CREATE TABLE `users`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(25) NOT NULL ,
    `surname` VARCHAR(30) NOT NULL ,
    `patronymic_name` VARCHAR(35) ,
    `email` VARCHAR(75) NOT NULL UNIQUE ,
    `password` VARCHAR(150) NOT NULL ,
    `image_path` VARCHAR(200) ,
    `status` BOOLEAN DEFAULT FALSE ,
    `role_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `users_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `users_role_id_fk`
        FOREIGN KEY(`role_id`)
            REFERENCES `roles`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `faculties`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `full_name` VARCHAR(100) NOT NULL UNIQUE ,
    `short_name` VARCHAR(30) NOT NULL UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `faculties_id_pk`
        PRIMARY KEY(`id`)
);

CREATE TABLE `dean_faculties`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `user_id` INT NOT NULL ,
    `faculty_id` INT NOT NULL ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `dean_faculties_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `dean_faculties_user_id_fk`
        FOREIGN KEY(`user_id`)
            REFERENCES `users`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `dean_faculties_faculty_id_fk`
        FOREIGN KEY(`faculty_id`)
            REFERENCES `faculties`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `coteries`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(100) NOT NULL UNIQUE ,
    `image_path` VARCHAR(200) ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `coteries_id_pk`
        PRIMARY KEY(`id`)
);

CREATE TABLE `student_specialities`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `full_name` VARCHAR(75) NOT NULL UNIQUE ,
    `short_name` VARCHAR(15) NOT NULL UNIQUE ,
    `faculty_id` INT NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `student_specialities_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `student_specialites_faculty_id_fk`
        FOREIGN KEY(`faculty_id`)
            REFERENCES `faculties`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `teachers`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `user_id` INT NOT NULL ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `teachers_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `teachers_user_id_fk`
        FOREIGN KEY(`user_id`)
            REFERENCES `users`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `teachers_coteries`(
    `teacher_id` INT NOT NULL ,
    `coterie_id` INT NOT NULL ,

    CONSTRAINT `teachers_coteries_teacher_id_coterie_id_pk`
        PRIMARY KEY(`teacher_id`,`coterie_id`) ,
    CONSTRAINT `teachers_coteries_teacher_id_fk`
        FOREIGN KEY(`teacher_id`)
            REFERENCES `teachers`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `teachers_coteries_coterie_id_fk`
        FOREIGN KEY(`coterie_id`)
            REFERENCES `coteries`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `students`(
    `id` INT AUTO_INCREMENT NOT NULL ,
    `user_id` INT NOT NULL ,
    `student_speciality_id` INT NOT NULL ,
    `hostel` BOOLEAN DEFAULT FALSE ,
    `gender` VARCHAR(5) NOT NULL ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `students_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `students_user_id_fk`
        FOREIGN KEY(`user_id`)
            REFERENCES `users`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `students_student_speciality_id_fk`
        FOREIGN KEY(`student_speciality_id`)
            REFERENCES `student_specialities`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `students_coteries`(
    `student_id` INT NOT NULL ,
    `coterie_id` INT NOT NULL ,

    CONSTRAINT `students_coteries_student_id_coterie_id_pk`
        PRIMARY KEY(`student_id`,`coterie_id`) ,
    CONSTRAINT `students_coteries_student_id_fk`
        FOREIGN KEY(`student_id`)
            REFERENCES `students`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `students_coteries_coterie_id_fk`
        FOREIGN KEY(`coterie_id`)
            REFERENCES `coteries`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `governor_coteries`(
    `id` INT NOT NULL ,
    `user_id` INT NOT NULL ,
    `coterie_id` INT NOT NULL ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `governor_coteries_id_pk`
        PRIMARY KEY(`id`) ,
    CONSTRAINT `governor_coteries_user_id_fk`
        FOREIGN KEY(`user_id`)
            REFERENCES `users`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `governor_coteries_coterie_id_fk`
        FOREIGN KEY(`coterie_id`)
            REFERENCES `coteries`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);