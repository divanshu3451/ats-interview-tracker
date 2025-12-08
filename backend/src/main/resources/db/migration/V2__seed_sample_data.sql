-- V2__seed_sample_data.sql
-- Sample data for development and testing

-- Insert sample companies
INSERT INTO company (name, website, industry, notes) VALUES
('Google', 'https://careers.google.com', 'Technology', 'Leading tech company'),
('Microsoft', 'https://careers.microsoft.com', 'Technology', 'Cloud and software giant'),
('Amazon', 'https://amazon.jobs', 'E-commerce/Cloud', 'AWS and retail leader'),
('Meta', 'https://metacareers.com', 'Social Media', 'Facebook, Instagram, WhatsApp'),
('Netflix', 'https://jobs.netflix.com', 'Entertainment', 'Streaming platform');

-- Insert sample applications
INSERT INTO application (company_id, job_title, status, job_url, salary_min, salary_max, applied_date, priority, notes) VALUES
(1, 'Senior Software Engineer', 'INTERVIEWING', 'https://careers.google.com/jobs/123', 150000, 200000, '2024-12-01', 5, 'Really excited about this role'),
(2, 'Cloud Solutions Architect', 'APPLIED', 'https://careers.microsoft.com/jobs/456', 140000, 180000, '2024-12-03', 4, 'Strong Azure background required'),
(3, 'Full Stack Developer', 'SCREENING', 'https://amazon.jobs/789', 130000, 170000, '2024-11-28', 3, 'AWS knowledge is a plus'),
(4, 'Frontend Engineer', 'SAVED', 'https://metacareers.com/jobs/abc', 135000, 175000, NULL, 4, 'React and TypeScript position'),
(5, 'Backend Engineer', 'REJECTED', 'https://jobs.netflix.com/def', 145000, 190000, '2024-11-20', 2, 'Did not move forward after phone screen');

-- Insert sample interviews
INSERT INTO interview (application_id, round_name, scheduled_date, scheduled_time, interview_type, status, notes) VALUES
(1, 'Phone Screen', '2024-12-05', '10:00:00', 'PHONE_SCREEN', 'COMPLETED', 'Went well, technical questions on algorithms'),
(1, 'Technical Interview', '2024-12-10', '14:00:00', 'TECHNICAL', 'SCHEDULED', 'System design and coding round'),
(2, 'Recruiter Call', '2024-12-08', '11:30:00', 'PHONE_SCREEN', 'SCHEDULED', 'Initial screening'),
(3, 'Phone Screen', '2024-12-02', '15:00:00', 'PHONE_SCREEN', 'COMPLETED', 'Discussed experience with microservices');
