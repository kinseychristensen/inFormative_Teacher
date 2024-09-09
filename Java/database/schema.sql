BEGIN TRANSACTION;

DROP TABLE IF EXISTS class_to_subject_archived, class_to_subject, scores, artifacts, student_to_group, school_group, lessons,  topics, subjects, artifact_type,
 student_to_class, students, class_to_teacher, school_class, users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(100) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE school_class (
    class_id SERIAL,
   description text,
    class_name varchar (50) NOT NULL,
    is_active boolean default true,
    CONSTRAINT PK_class_id PRIMARY KEY (class_id)
    );

CREATE TABLE class_to_teacher (
    class_id int NOT NULL,
    teacher_id int NOT NULL,
    CONSTRAINT FK_teacher_id FOREIGN KEY (teacher_id) REFERENCES users(user_id),
    CONSTRAINT FK_class_id FOREIGN KEY (class_id) REFERENCES school_class(class_id)
);



CREATE TABLE students (
    student_id serial,
    last_name varchar (50) NOT NULL,
    first_name varchar (50) NOT NULL,
    school_id int NOT NULL,
    CONSTRAINT PK_student_id PRIMARY KEY (student_id)
    );

CREATE TABLE student_to_class (
    class_id int NOT NULL,
    student_id int NOT NULL,
    CONSTRAINT FK_class_id FOREIGN KEY (class_id) REFERENCES school_class(class_id),
    CONSTRAINT FK_student_id FOREIGN KEY (student_id) REFERENCES students(student_id)
    );

CREATE TABLE artifact_type (
    code SERIAL,
    description varchar (50) NOT NULL,
    CONSTRAINT PK_code PRIMARY KEY (code)
    );

 CREATE TABLE subjects (
    subject_id serial,
    code varchar (50) NOT NULL,
    description text NOT NULL,
    not_attempted int default 0,
    below int default 1,
    approaching int default 2,
    proficient int default 3,
    mastered int default 4,
    CONSTRAINT PK_subject_id PRIMARY KEY (subject_id)
    );


CREATE TABLE topics (
    topic_id serial,
    code varchar (50) NOT NULL,
    description text NOT NULL,
    subject_id int NOT NULL,
    CONSTRAINT PK_topic_id PRIMARY KEY (topic_id),
    CONSTRAINT FK_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
    );

CREATE TABLE lessons (
    lesson_id serial,
    code varchar (50) NOT NULL,
    description text NOT NULL,
    topic_id int NOT NULL,
    CONSTRAINT PK_lesson_id PRIMARY KEY (lesson_id),
     CONSTRAINT FK_topic_id FOREIGN KEY (topic_id) REFERENCES topics(topic_id)
    );

CREATE TABLE school_group (
    group_id SERIAL,
    group_name varchar (50) NOT NULL,
    description text,
    class_id int NOT NULL,
    subject_id int NOT NULL,
   color int default 1,
    CONSTRAINT PK_group_id PRIMARY KEY (group_id),
    CONSTRAINT FK_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
    );


CREATE TABLE student_to_group (
    group_id int NOT NULL,
    student_id int NOT NULL,
    CONSTRAINT FK_group_id FOREIGN KEY (group_id) REFERENCES school_group(group_id),
    CONSTRAINT FK_student_id FOREIGN KEY (student_id) REFERENCES students(student_id)
    );

CREATE TABLE artifacts (
    artifact_id SERIAL,
    artifact_type int NOT NULL,
    description text,
    lesson int NOT NULL,
    teacher_id int NOT NULL,
    assignment_date DATE NOT NULL,
    comments TEXT,
    CONSTRAINT PK_artifact_id PRIMARY KEY (artifact_id),
    CONSTRAINT FK_artifact_type FOREIGN KEY (artifact_type) REFERENCES artifact_type(code),
    CONSTRAINT FK_lesson FOREIGN KEY (lesson) REFERENCES lessons(lesson_id),
    CONSTRAINT FK_teacher_id FOREIGN KEY (teacher_id) REFERENCES users(user_id)
    );

CREATE TABLE scores (
    student_id int NOT NULL,
    artifact_id int NOT NULL,
    score real DEFAULT -1,
    comments text,
    is_waived boolean NOT NULL DEFAULT FALSE,
    CONSTRAINT FK_student_id FOREIGN KEY (student_id) REFERENCES students(student_id),
    CONSTRAINT FK_artifact_id FOREIGN KEY (artifact_id) REFERENCES artifacts(artifact_id),
    CONSTRAINT PK_unique_score PRIMARY KEY (student_id, artifact_id)
    );

    CREATE TABLE class_to_subject (
    class_id int NOT NULL,
    subject_id int NOT NULL,
    color int default 1,
    CONSTRAINT FK_class_id FOREIGN KEY (class_id) REFERENCES school_class(class_id),
    CONSTRAINT FK_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
    );

CREATE TABLE class_to_subject_archived (
     class_id int NOT NULL,
     subject_id int NOT NULL,
     CONSTRAINT FK_class_id FOREIGN KEY (class_id) REFERENCES school_class(class_id),
     CONSTRAINT FK_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
     );


COMMIT TRANSACTION;
