// Interface for UserRow to be displayed
export interface UserRow {
  id: number;
  lineNo: number;
  firstName: string;
  lastName: string;
  email: string;
  jobTitle: string;
}

// Interface for EmploymentRow to be displayed
export interface EmploymentRow {
  id: number;
  lineNo: number;
  fullName: string;
  level: Level;
  position: Position;
  salary: string;
  status: string;
}

// Interface for User
export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  employment: Employment;
  username: string;
  password: string;
}

// Interface for Employment
export interface Employment {
  id: number;
  level: Level;
  position: Position;
  status: string;
}

// Interface for Level
export interface Level {
  id: number;
  name: string;
  salaryRate: number | null;
}

// Interface for Position
export interface Position {
  id: number;
  name: string;
  salary: number | null;
  role: string;
}
