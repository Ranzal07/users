import { api } from 'src/boot/axios';
import { User } from 'src/model/types';

const CONTROLLER = 'user';

export const getAllUsers = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const addUser = async (user: User) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, user);
    return response.data;
  } catch (error) {
    console.error('Error adding user:', error);
    throw error;
  }
};

export const updateAuthUser = async (user: User) => {
  try {
    const response = await api.put(`${CONTROLLER}/update`, user);
    return response.data;
  } catch (error) {
    console.error('Error updating user:', error);
    throw error;
  }
};

export const deleteUser = async (user: User) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${user.id}`);
    return response.data;
  } catch (error) {
    console.error('Error deleting user:', error);
    throw error;
  }
};
