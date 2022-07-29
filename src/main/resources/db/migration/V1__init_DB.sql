CREATE TABLE `roles`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(50) NOT NULL UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `emails`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(75) NOT NULL UNIQUE ,
    `password` VARCHAR (150) NOT NULL ,
    `phone_number` VARCHAR(20) UNIQUE ,
    `status` BOOLEAN NOT NULL DEFAULT FALSE ,
    `role_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `emails_role_id_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `roles`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLe `admins`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(25) NOT NULL ,
    `surname` VARCHAR(30) NOT NULL ,
    `patronymic_name` VARCHAR(35) ,
    `image_path` VARCHAR(255) ,
    `email_id` INT NOT NULL ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `admins_email_id_fk`
        FOREIGN KEY (`email_id`)
            REFERENCES `emails`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `faculties`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `full_name` VARCHAR (300) NOT NULL UNIQUE ,
    `short_name` VARCHAR(50) NOT NULL UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `dean_faculties`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(25) NOT NULL ,
    `surname` VARCHAR(30) NOT NULL ,
    `patronymic_name` VARCHAR(35) ,
    `image_path` VARCHAR(255) ,
    `email_id` INT NOT NULL ,
    `faculty_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `dean_faculties_email_id_fk`
        FOREIGN KEY(`email_id`)
            REFERENCES `emails`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `dean_faculties_faculty_id_fk`
        FOREIGN KEY(`faculty_id`)
            REFERENCES `faculties`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `pulpits`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `full_name` VARCHAR(255) NOT NULL UNIQUE ,
    `short_name` VARCHAR(50) NOT NULL UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `pulpit_governors`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(25) NOT NULL ,
    `surname` VARCHAR(30) NOT NULL ,
    `patronymic_name` VARCHAR(35) ,
    `image_path` VARCHAR (255) NOT NULL ,
    `pulpit_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `pulpit_governors_pulpit_id_fk`
        FOREIGN KEY(`pulpit_id`)
            REFERENCES `pulpits`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `speciality_students`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `full_name` VARCHAR(255) NOT NULL UNIQUE ,
    `short_name` VARCHAR(255) NOT NULL UNIQUE ,
    `faculty_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `speciality_students_faculty_id_fk`
        FOREIGN KEY(`faculty_id`)
            REFERENCES `faculties`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `coteries`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(255) NOT NULL UNIQUE ,
    `image_path` VARCHAR(255) UNIQUE ,
    `pulpit_id` INT ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    CONSTRAINT `coteries_pulpit_id_fk`
        FOREIGN KEY(`pulpit_id`)
            REFERENCES `pulpits`(`id`)
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `teachers`(
    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL ,
    `name` VARCHAR(25) NOT NULL ,
    `surname` VARCHAR(30) NOT NULL ,
    `patronymic_name` VARCHAR(35) ,
    `image_path` VARCHAR(255) UNIQUE ,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `teachers_coteries`(
    `teacher_id` INT NOT NULL ,
    `coterie_id` INT NOT NULL ,

    CONSTRAINT `teachers_coteries_teacher_id_fk`
        FOREIGN KEY (`teacher_id`)
            REFERENCES `teachers`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE ,
    CONSTRAINT `teachers_coteries_coterie_id_fk`
        FOREIGN KEY (`coterie_id`)
            REFERENCES `coteries`(`id`)
                ON UPDATE CASCADE ON DELETE CASCADE
);