<template>
  <q-page>
    <div class="menu">
      <!-- Welcome message -->
      <h4 class="text-center">
        Welcome, {{ authUser?.firstName }} {{ authUser?.lastName }} -
        {{ authUser?.employment?.level?.name }}
        {{ authUser?.employment?.position?.name }}
      </h4>
    </div>
  </q-page>
</template>

<script lang="ts">
import { User } from 'src/model/types';
import { defineComponent, ref} from 'vue';
import * as authService from 'src/service/authService';
export default defineComponent({
  setup() {
    const authUser = ref<User>();

    const getUsers = async () => {
      try {
        authUser.value = await authService.getAuthUser();
      } catch (error) {
        console.error('Failed to fetch users', error);
      }
    };
    getUsers();

    return {
      authUser,
    };
  },
});
</script>

<style scoped>
.menu {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 1em;
}

.text-decoration-none {
  text-decoration: none;
}

.text-center {
  text-align: center;
}
</style>
