import { api } from 'src/boot/axios';
import { LevelsPayload } from 'src/payload/types';

const CONTROLLER = 'level';

export const getAllLevels = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error fetching levels:', error);
    throw error;
  }
};

export const addLevel = async (level: LevelsPayload) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, level);
    console.log(response.data);
  } catch (error: any) {
    console.error('Error adding level:', error.message);
  }
};

export const updateLevel = async (level: LevelsPayload) => {
  try {
    const response = await api.put(`${CONTROLLER}/update`, level);
    console.log(response.data);
  } catch (error: any) {
    console.error('Error updating level:', error.message);
  }
};

export const deleteLevel = async (level: LevelsPayload) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${level.id}`);
    console.log(response.data);
  } catch (error: any) {
    console.error('Error deleting level:', error.message);
  }
};
