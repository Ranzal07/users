import { api } from 'src/boot/axios';
import { Position } from 'src/model/types';

const CONTROLLER = 'position';

export const getAllPositions = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error fetching positions:', error);
    throw error;
  }
};

export const addPosition = async (position: Position) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, position);
    return response.data;
  } catch (error) {
    console.error('Error adding position:', error);
    throw error;
  }
};

export const updatePosition = async (position: Position) => {
  try {
    if(position.salary) position.salary = parseFloat(position.salary.toLocaleString().replace(/,/g, ''));
    const response = await api.put(`${CONTROLLER}/update`, position);
    return response.data;
  } catch (error) {
    console.error('Error updating position:', error);
    throw error;
  }
};

export const deletePosition = async (position: Position) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${position.id}`);
    return response.data;
  } catch (error) {
    console.error('Error deleting position:', error);
    throw error;
  }
};
