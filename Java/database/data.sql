BEGIN TRANSACTION;


INSERT INTO users (username,password_hash,role, first_name, last_name) VALUES ('kit.the.avenger@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Kinsey','Christensen');
INSERT INTO users (username,password_hash,role, first_name, last_name) VALUES ('kiyna.twisdale@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Kiyna', 'Twisdale');


INSERT INTO students (school_id, last_name, first_name) VALUES (1, 'Able', 'Mable');
INSERT INTO students (school_id, last_name, first_name) VALUES (2, 'Nook', 'Tommy');
INSERT INTO students (school_id, last_name, first_name) VALUES (3, 'Nook', 'Timmy');
INSERT INTO students (school_id, last_name, first_name) VALUES (4, 'Able', 'Sable');
INSERT INTO students (school_id, last_name, first_name) VALUES (5, 'Gold', 'Isabelle');
INSERT INTO students (school_id, last_name, first_name) VALUES (6, 'Hunter', 'Flick');
INSERT INTO students (school_id, last_name, first_name) VALUES (7, 'Hunter', 'C.J.');
INSERT INTO students (school_id, last_name, first_name) VALUES (8, 'Llama', 'Cyrus');
INSERT INTO students (school_id, last_name, first_name) VALUES (9, 'Llama', 'Reese');
INSERT INTO students (school_id, last_name, first_name) VALUES (10, 'Fox', 'Redd');
INSERT INTO students (school_id, last_name, first_name) VALUES (11, 'Owl', 'Celeste');
INSERT INTO students (school_id, last_name, first_name) VALUES (12, 'Owl', 'Blathers');
INSERT INTO students (school_id, last_name, first_name) VALUES (13, 'Roost', 'Brewster');
INSERT INTO students (school_id, last_name, first_name) VALUES (14, 'Resetti', 'Don');
INSERT INTO students (school_id, last_name, first_name) VALUES (15, 'Shrunk', 'Doc');
INSERT INTO students (school_id, last_name, first_name) VALUES (16, 'Giraffe', 'Gracie');
INSERT INTO students (school_id, last_name, first_name) VALUES (17, 'Gull', 'Gullivarrr');

INSERT INTO school_class (description, class_name, is_active) VALUES ('4th grade class at Moss Elementary, 26 students', '2023-2024', true);
INSERT INTO school_class (description, class_name, is_active) VALUES ('sample class, 4th grade', '2024-2025', true);
INSERT INTO school_class (description, class_name, is_active) VALUES ('4th grade class at Moss Elementary, 26 students', '2022-2023', false);
INSERT INTO school_class (description, class_name, is_active) VALUES ('sample archive class, 4th grade', '2023-2024', false);

INSERT INTO class_to_teacher (class_id, teacher_id) VALUES (1, 1);
INSERT INTO class_to_teacher (class_id, teacher_id) VALUES (2, 2);


INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 1);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 2);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 3);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 4);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 5);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 6);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 7);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 8);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 9);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 10);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 11);
INSERT INTO student_to_class (class_id, student_id) VALUES (1 , 12);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 13);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 14);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 15);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 16);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 17);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 1);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 2);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 3);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 4);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 5);
INSERT INTO student_to_class (class_id, student_id) VALUES (2 , 6);

INSERT INTO subjects (code, description, not_attempted, below, approaching, proficient, mastered) VALUES ('Math', '3rd Grade Math', 0, 1, 2, 3, 4);
INSERT INTO subjects (code, description, not_attempted, below, approaching, proficient, mastered) VALUES ('L.A.', '3rd Grade Language Arts', 0, 1, 2, 3, 4);

INSERT INTO topics (code, description, subject_id) VALUES ('1', 'Generalize Place Value Understanding', 1);
INSERT INTO topics (code, description, subject_id) VALUES ('2', 'Math Topic #2', 1);
INSERT INTO topics (code, description, subject_id) VALUES ('3', 'Math Topic # 3', 1);
INSERT INTO topics (code, description, subject_id) VALUES ('Organize', 'Writing Standards 3', 2);

INSERT INTO lessons (code, description, topic_id) VALUES ('1','Numbers Through One Million', 1);
INSERT INTO lessons (code, description, topic_id) VALUES ('2','Place Value Relationships', 1);
INSERT INTO lessons (code, description, topic_id) VALUES ('3','Compare Whole Numbers', 1);
INSERT INTO lessons (code, description, topic_id) VALUES ('4','Round Whole Numbers', 1);
INSERT INTO lessons (code, description, topic_id) VALUES ('5','Problem Solving', 1);
INSERT INTO lessons (code, description, topic_id) VALUES ('Paragraph Structure','Can Students organize a paragraph with a topic sentence and evidence?', 4);
INSERT INTO lessons (code, description, topic_id) VALUES ('Conclusion','Can students write a conclusion that summarizes arguments without introducing new information?', 4);
INSERT INTO lessons (code, description, topic_id) VALUES ('Introduction','Can students write a compelling introduction that includes a topic sentence?', 4);

INSERT INTO school_group (group_name, description, class_id, subject_id, is_active) VALUES ('#1', 'Reading groups', 1, 2, true);
INSERT INTO school_group (group_name, description, class_id, subject_id, is_active) VALUES ('#7', 'Math group 7', 1, 1, true);
INSERT INTO school_group (group_name, description, class_id, subject_id, is_active) VALUES ('Pull Out - Reading', 'Pull out for reading at 10:15', 1, 2, true);
INSERT INTO school_group (group_name, description, class_id, subject_id, is_active) VALUES ('Table Six', 'table six, math', 2, 1, false);


INSERT INTO student_to_group (group_id, student_id) VALUES (1, 3);
INSERT INTO student_to_group (group_id, student_id) VALUES (1, 4);
INSERT INTO student_to_group (group_id, student_id) VALUES (1, 5);
INSERT INTO student_to_group (group_id, student_id) VALUES (2, 1);
INSERT INTO student_to_group (group_id, student_id) VALUES (2, 2);
INSERT INTO student_to_group (group_id, student_id) VALUES (2, 4);
INSERT INTO student_to_group (group_id, student_id) VALUES (3, 12);
INSERT INTO student_to_group (group_id, student_id) VALUES (3, 15);
INSERT INTO student_to_group (group_id, student_id) VALUES (4, 14);
INSERT INTO student_to_group (group_id, student_id) VALUES (4, 10);
INSERT INTO student_to_group (group_id, student_id) VALUES (4, 17);
INSERT INTO student_to_group (group_id, student_id) VALUES (4, 16);

INSERT INTO artifact_type (description) VALUES ('Quick Check');
INSERT INTO artifact_type (description) VALUES ('Practice Buddy');
INSERT INTO artifact_type (description) VALUES ('Small Group Observation');
INSERT INTO artifact_type (description) VALUES ('Practice Page');
INSERT INTO artifact_type (description) VALUES ('Practice Page With Partner');
INSERT INTO artifact_type (description) VALUES ('Exit Ticket');
INSERT INTO artifact_type (description) VALUES ('Retest');

INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date, trends, comments)
VALUES (1, 'Quick Check # 6', 1, 1, '01/01/2024', 'no trends', '#1 - # 3 completed as class');

INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date, trends, comments)
VALUES (2, 'partners', 5, 1, '10/10/2023', 'had to reteach word problem vocabulary', 'fire drill day');

INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date, comments)
VALUES (3, '-', 6, 1, '02/14/2024', 'valentines cut group time to 10 minutes');

INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date)
VALUES (7, 'lunch retest for Timmy', 3, 1, '02/16/2023');

INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date, trends, comments)
VALUES (6, 'number sort', 3, 1, '02/22/2024', 'some students struggling with thousands vs ten thousands', 'sub');


INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (1, 1, 0, 'pulled for band', true);
INSERT INTO scores (student_id, artifact_id, score) VALUES (2, 1, 2);
INSERT INTO scores (student_id, artifact_id, score) VALUES (3, 1, 4);
INSERT INTO scores (student_id, artifact_id, score) VALUES (4, 1, 2);
INSERT INTO scores (student_id, artifact_id, score) VALUES (5, 1, 2.4);
INSERT INTO scores (student_id, artifact_id, score) VALUES (6, 1, 2);
INSERT INTO scores (student_id, artifact_id, score) VALUES (7, 1, 1);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (8, 1, 0.5, 'checked out, needs to complete', false);
INSERT INTO scores (student_id, artifact_id, score) VALUES (9, 1, 3);
INSERT INTO scores (student_id, artifact_id, score) VALUES (10, 1, 2);
INSERT INTO scores (student_id, artifact_id, score) VALUES (11, 1, 2.7);
INSERT INTO scores (student_id, artifact_id, score) VALUES (12, 1, 2);
INSERT INTO scores (student_id, artifact_id, score) VALUES (13, 1, 3);
INSERT INTO scores (student_id, artifact_id, score) VALUES (14, 1, 1);
INSERT INTO scores (student_id, artifact_id, score) VALUES (15, 1, 1.5);
INSERT INTO scores (student_id, artifact_id, score) VALUES (16, 1, 2);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (17, 1, 0, 'absent', true);


INSERT INTO scores (student_id, artifact_id, score) VALUES (1, 5, 3);
INSERT INTO scores (student_id, artifact_id, score) VALUES (2, 5, 1);
INSERT INTO scores (student_id, artifact_id, score) VALUES (3, 5, 3);
INSERT INTO scores (student_id, artifact_id, score) VALUES (4, 5, 2.5);
INSERT INTO scores (student_id, artifact_id, score) VALUES (5, 5, 2.4);
INSERT INTO scores (student_id, artifact_id, score) VALUES (6, 5, 1);
INSERT INTO scores (student_id, artifact_id, score) VALUES (7, 5, 1.4);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (8, 5, 0.5, 'checked out, needs to complete', false);
INSERT INTO scores (student_id, artifact_id, score) VALUES (9, 5, 3.1);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (10, 5, 2, 'sub said Blathers gave Redd answers, needs retesting', true);
INSERT INTO scores (student_id, artifact_id, score) VALUES (11, 5, 2.7);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (12, 5, 3, 'sub said Blathers was caught giving Redd answers', false);
INSERT INTO scores (student_id, artifact_id, score) VALUES (13, 5, 3);
INSERT INTO scores (student_id, artifact_id, score) VALUES (14, 5, 1);
INSERT INTO scores (student_id, artifact_id, score) VALUES (15, 5, 1.5);
INSERT INTO scores (student_id, artifact_id, score) VALUES (16, 5, 4);
INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (17, 5, 0, 'sick leave', true);

INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) VALUES (3, 3, 3, 'did whiteboarding questions', false);

INSERT INTO class_to_subject (class_id, subject_id) VALUES (1, 1);
INSERT INTO class_to_subject (class_id, subject_id) VALUES (1, 2);
INSERT INTO class_to_subject (class_id, subject_id) VALUES (2, 1);
INSERT INTO class_to_subject (class_id, subject_id) VALUES (2, 2);


COMMIT TRANSACTION;