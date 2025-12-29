-- ============================================
-- KUET Academic Portal - Attendance Sample Data
-- ============================================
-- This script populates the attendance table with sample data for testing
-- Make sure your MySQL server is running before executing

USE StudentDB;

-- Clear existing attendance data (optional - comment out if you want to keep existing data)
-- DELETE FROM attendance;
-- ALTER TABLE attendance AUTO_INCREMENT = 1;

-- ============================================
-- SAMPLE ATTENDANCE DATA
-- ============================================
-- Assuming student roll: 1803001
-- Year: 1, Term: 1
-- Department: CSE, Section: A
-- Sample courses and attendance records

-- Course 1: CSE 1101 (Programming Language I)
-- Total: 30 classes, Attended: 28, Missed: 2 (93.3% attendance)
INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES
('CSE 1101', 'Programming Language I', '2024-01-10', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-12', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-15', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-17', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-19', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-22', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-24', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-26', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-29', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-01-31', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-02', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-05', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-07', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-09', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-12', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-14', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-16', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-19', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-21', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-23', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-26', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-02-28', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-01', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-04', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-06', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-08', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-11', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-13', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-15', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1101', 'Programming Language I', '2024-03-18', 'Present', 1, 1, 'CSE', 'A', '1803001');

-- Course 2: CSE 1103 (Discrete Mathematics)
-- Total: 25 classes, Attended: 23, Missed: 2 (92% attendance)
INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES
('CSE 1103', 'Discrete Mathematics', '2024-01-11', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-13', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-16', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-18', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-20', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-23', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-25', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-27', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-01-30', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-01', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-03', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-06', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-08', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-10', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-13', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-15', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-17', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-20', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-22', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-24', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-02-27', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-03-02', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-03-05', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-03-07', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('CSE 1103', 'Discrete Mathematics', '2024-03-09', 'Present', 1, 1, 'CSE', 'A', '1803001');

-- Course 3: MATH 1101 (Calculus I)
-- Total: 28 classes, Attended: 20, Missed: 8 (71.4% attendance - Below 75%)
INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES
('MATH 1101', 'Calculus I', '2024-01-10', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-12', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-15', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-17', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-19', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-22', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-24', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-26', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-29', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-01-31', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-02', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-05', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-07', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-09', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-12', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-14', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-16', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-19', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-21', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-23', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-26', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-02-28', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-01', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-04', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-06', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-08', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-11', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('MATH 1101', 'Calculus I', '2024-03-13', 'Present', 1, 1, 'CSE', 'A', '1803001');

-- Course 4: ENG 1101 (English)
-- Total: 20 classes, Attended: 19, Missed: 1 (95% attendance)
INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES
('ENG 1101', 'English', '2024-01-11', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-13', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-16', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-18', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-20', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-23', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-25', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-27', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-01-30', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-01', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-03', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-06', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-08', 'Absent', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-10', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-13', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-15', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-17', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-20', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-22', 'Present', 1, 1, 'CSE', 'A', '1803001'),
('ENG 1101', 'English', '2024-02-24', 'Present', 1, 1, 'CSE', 'A', '1803001');

-- ============================================
-- VERIFICATION QUERIES
-- ============================================

-- Summary by course
SELECT
    course_no,
    course_name,
    COUNT(*) as total_classes,
    SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as attended_classes,
    ROUND(SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as attendance_percentage
FROM attendance
WHERE student_roll = '1803001'
GROUP BY course_no, course_name
ORDER BY course_no;

-- Overall attendance
SELECT
    COUNT(*) as total_classes,
    SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as attended_classes,
    SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) as missed_classes,
    ROUND(SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as overall_percentage
FROM attendance
WHERE student_roll = '1803001';

-- Courses below 75% attendance (Warning)
SELECT
    course_no,
    course_name,
    ROUND(SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as attendance_percentage
FROM attendance
WHERE student_roll = '1803001'
GROUP BY course_no, course_name
HAVING attendance_percentage < 75
ORDER BY attendance_percentage;

-- ============================================
-- Expected Results for Roll 1803001:
-- ============================================
-- CSE 1101: 93.3% (28/30) - GOOD
-- CSE 1103: 92.0% (23/25) - GOOD
-- MATH 1101: 71.4% (20/28) - WARNING (Below 75%)
-- ENG 1101: 95.0% (19/20) - EXCELLENT
-- Overall: 87.4% (90/103)
-- ============================================

