<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>
          {{ authUser?.firstName }} {{ authUser?.lastName }}
        </q-toolbar-title>
        <q-btn to="/v1/dashboard/index" label="Dashboard" />
        <q-btn to="/v1/user/index" label="Users" />
        <q-btn to="/v1/employment/index" label="Employment" />
        <q-btn to="/v1/level/index" label="Level" />
        <q-btn to="/v1/position/index" label="Position" />
        <q-btn to="/v1/account/index" label="Account" />
        <q-btn color="negative" @click="logout" label="Logout" outline/>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import * as authService from 'src/service/authService';
import { User } from 'src/model/types';
export default defineComponent({
  setup() {
    const router = useRouter()
    const authUser = ref<User>();

    const getUsers = async () => {
      try {
        authUser.value = await authService.getAuthUser();
      } catch (error) {
        console.error('Failed to fetch users', error);
      }
    };
    getUsers();

    const logout = async () => {
      try {
        await authService.logoutUser();
        localStorage.removeItem('authData');
        router.push('/v1/auth/login');
      } catch (error) {
        console.error('Logout failed: ', error);
      }
    };

    return {
      authUser,
      logout,
    };
  },
});
</script>
