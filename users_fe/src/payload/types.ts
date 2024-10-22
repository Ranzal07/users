export interface NewUser {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export interface AuthUserPayload {
  lineNo: number;
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  jobTitle: string;
  canEdit: boolean;
}

export interface UsersPayload {
  lineNo: number;
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
  salary: number | string;
  status: string;
}

export interface LevelsPayload {
  lineNo: number;
  id: number;
  name: string;
  salaryRate: number;
}

export interface PositionsPayload {
  lineNo: number;
  id: number;
  name: string;
  salary: number;
  role: string;
}

