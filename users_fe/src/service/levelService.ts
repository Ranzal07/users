import { api } from 'src/boot/axios';
import { Level } from 'src/model/types';

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

export const addLevel = async (level: Level) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, level);
    return response.data;
  } catch (error) {
    console.error('Error adding level:', error);
    throw error;
  }
};

export const updateLevel = async (level: Level) => {
  try {
    const response = await api.put(`${CONTROLLER}/update`, level);
    return response.data;
  } catch (error) {
    console.error('Error updating level:', error);
    throw error;
  }
};

export const deleteLevel = async (level: Level) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${level.id}`);
    return response.data;
  } catch (error) {
    console.error('Error deleting level:', error);
    throw error;
  }
};
