SELECT Student.name AS student_name,
       Student.age  AS student_age,
       Faculty.name AS faculty_name
FROM Student
         INNER JOIN
     Faculty
     ON
         Student.faculty_id = Faculty.id;

SELECT Student.name AS student_name,
       Student.age  AS student_age
FROM Student
         INNER JOIN
     Avatar
     ON
         Student.id = Avatar.student_id;