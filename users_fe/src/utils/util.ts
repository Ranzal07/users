import { Employment } from 'src/model/types';

export const calculateSalary = (employment: Employment) => {
  const salary = (employment?.position?.salary || 0) * (employment?.level?.salaryRate || 1);
  return salary.toLocaleString();
};
