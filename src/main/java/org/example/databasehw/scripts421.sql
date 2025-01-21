ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK ( age > 16 );

ALTER TABLE student
    ADD CONSTRAINT name UNIQUE (name);

ALTER TABLE Student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE Faculty
    ADD CONSTRAINT unique_name_color UNIQUE (name, color);

ALTER TABLE Student
    ALTER COLUMN age SET DEFAULT 20;


