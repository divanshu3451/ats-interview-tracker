-- V1__init_schema.sql
-- Initial schema for ATS Interview Tracker V1.0

-- Create Company table
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    website VARCHAR(500),
    industry VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Application table
CREATE TABLE application (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'SAVED',
    job_url VARCHAR(1000),
    salary_min DECIMAL(12, 2),
    salary_max DECIMAL(12, 2),
    applied_date DATE,
    notes TEXT,
    priority INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_application_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    CONSTRAINT chk_salary CHECK (salary_max IS NULL OR salary_min IS NULL OR salary_max >= salary_min),
    CONSTRAINT chk_status CHECK (status IN ('SAVED', 'APPLIED', 'SCREENING', 'INTERVIEWING', 'OFFER', 'REJECTED'))
);

-- Create Interview table
CREATE TABLE interview (
    id BIGSERIAL PRIMARY KEY,
    application_id BIGINT NOT NULL,
    round_name VARCHAR(100) NOT NULL,
    scheduled_date DATE,
    scheduled_time TIME,
    interview_type VARCHAR(50),
    status VARCHAR(50) DEFAULT 'SCHEDULED',
    notes TEXT,
    feedback TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_interview_application FOREIGN KEY (application_id) REFERENCES application(id) ON DELETE CASCADE,
    CONSTRAINT chk_interview_status CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    CONSTRAINT chk_interview_type CHECK (interview_type IN ('PHONE_SCREEN', 'VIDEO', 'ONSITE', 'TECHNICAL', 'BEHAVIORAL', 'FINAL'))
);

-- Create indexes for performance
CREATE INDEX idx_application_status ON application(status);
CREATE INDEX idx_application_company ON application(company_id);
CREATE INDEX idx_application_applied_date ON application(applied_date DESC);
CREATE INDEX idx_interview_application ON interview(application_id);
CREATE INDEX idx_interview_scheduled_date ON interview(scheduled_date);
CREATE INDEX idx_company_name ON company(name);
