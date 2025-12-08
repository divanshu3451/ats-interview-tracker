import { InterviewStatus, InterviewType } from './enums.model';

export interface Interview {
  id: number;
  applicationId: number;
  roundName: string;
  scheduledDate?: string;
  scheduledTime?: string;
  interviewType?: InterviewType;
  status: InterviewStatus;
  notes?: string;
  feedback?: string;
  createdAt: string;
  updatedAt: string;
}
