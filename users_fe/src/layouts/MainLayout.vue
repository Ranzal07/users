<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>
          {{ authUser?.firstName + " " + authUser?.lastName }}
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
import { defineComponent, onMounted, ref } from 'vue';
import * as authService from 'src/service/authService';
import * as dashboardService from 'src/service/dashboardService';
import { AuthUserPayload } from 'src/payload/types';
export default defineComponent({
  setup() {
    const authUser = ref<AuthUserPayload>();
    const fetchAuthUser = async () => {
      authUser.value = await dashboardService.getAuthUser();
    };
    onMounted(() => {
      fetchAuthUser();
    });
    
    const logout = async () => {
      try {
        await authService.logoutUser();
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
