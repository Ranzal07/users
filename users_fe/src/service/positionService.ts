import { api } from 'src/boot/axios';
import { PositionsPayload } from 'src/payload/types';

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

export const addPosition = async (position: PositionsPayload) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, position);
    console.log(response.data);
  } catch (error) {
    console.error('Error adding position:', error);
    throw error;
  }
};

export const updatePosition = async (position: PositionsPayload) => {
  try {
    if(position.salary) position.salary = parseFloat(position.salary.toLocaleString().replace(/,/g, ''));
    const response = await api.put(`${CONTROLLER}/update`, position);
    console.log(response.data);
  } catch (error) {
    console.error('Error updating position:', error);
    throw error;
  }
};

export const deletePosition = async (position: PositionsPayload) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${position.id}`);
    console.log(response.data);
  } catch (error) {
    console.error('Error deleting position:', error);
    throw error;
  }
};