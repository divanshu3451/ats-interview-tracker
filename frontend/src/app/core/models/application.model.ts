import { ApplicationStatus } from './enums.model';

export interface Application {
  id: number;
  companyId: number;
  companyName: string;
  jobTitle: string;
  status: ApplicationStatus;
  jobUrl?: string;
  salaryMin?: number;
  salaryMax?: number;
  appliedDate?: string;
  notes?: string;
  priority: number;
  createdAt: string;
  updatedAt: string;
  interviewCount: number;
}

export interface ApplicationCreate {
  companyId: number;
  jobTitle: string;
  status: ApplicationStatus;
  jobUrl?: string;
  salaryMin?: number;
  salaryMax?: number;
  appliedDate?: string;
  notes?: string;
  priority?: number;
}

export interface ApplicationUpdate {
  jobTitle: string;
  status: ApplicationStatus;
  jobUrl?: string;
  salaryMin?: number;
  salaryMax?: number;
  appliedDate?: string;
  notes?: string;
  priority?: number;
}
