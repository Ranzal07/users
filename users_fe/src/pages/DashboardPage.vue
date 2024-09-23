<template>
  <q-page>
    <div class="menu">
      <!-- Welcome message -->
      <h4 class="text-center">
        Welcome, {{ authUser.firstName }} {{ authUser.lastName }} -
        {{ authUser.employment?.level?.name }}
        {{ authUser.employment?.position?.name }}
      </h4>
    </div>
  </q-page>
</template>

<script lang="ts">
import { User } from 'src/model/types';
import { defineComponent, ref} from 'vue';
import * as userService from 'src/service/userService';
export default defineComponent({
  setup() {
    const users = ref<User[]>([]);
    const authUser = ref<any>({});

    const getUsers = async () => {
      try {
        const authData = localStorage.getItem('authData');
        if (authData) {
          authUser.value = JSON.parse(authData).user;
        }
        users.value = await userService.getAllUsers();
        authUser.value = users.value.find((user: User) => user.id === authUser.value.id);
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
