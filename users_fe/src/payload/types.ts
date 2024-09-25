export interface AuthUserPayload {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  jobTitle: string;
}

export interface UsersPayload {
  lineNo: number | undefined;
  id: number;
  firstName: string;
  lastName: string;
  jobTitle: string;
}

export interface EmploymentsPayload {
  lineNo: number;
  id: number;
  fullName: string;
  level: LevelsPayload;
  position: PositionsPayload;
  salary: string;
  status: string;
}

export interface LevelsPayload {
  lineNo: number | null;
  id: number;
  name: string;
  salaryRate: number | string;
}

export interface PositionsPayload {
  lineNo: number | null;
  id: number;
  name: string;
  salary: number | null;
  role: string;
}

