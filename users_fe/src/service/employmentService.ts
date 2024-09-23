import { api } from 'src/boot/axios';

const CONTROLLER = 'employment';

export const getAllEmployments = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error fetching employments:', error);
    throw error;
  }
};

export const updateEmployment = async (data: {
  user: number;
  level: number;
  position: number;
}) => {
  try {
    const response = await api.put(`${CONTROLLER}/update`, data);
    return response.data;
  } catch (error) {
    console.error('Error updating employment:', error);
    throw error;
  }
};
