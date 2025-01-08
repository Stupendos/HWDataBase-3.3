SELECT *
FROM student;

SELECT *
FROM student
where age between 10 and 19;

SELECT name
FROM student;

SELECT name
FROM student
WHERE name like '%о%'
   or name like '%С%';

SELECT *
FROM student
WHERE age > student.id;

SELECT *
FROM student
ORDER BY age;