<template>
  <q-page>
    <div class="menu">
      <!-- Welcome message -->
      <h4 class="text-center">
        Welcome, {{ authUser?.firstName + " " + authUser?.lastName }} - {{ authUser?.jobTitle }}
      </h4>
    </div>
  </q-page>
</template>

<script lang="ts">
import { AuthUserPayload } from 'src/payload/types';
import { defineComponent, onMounted, ref} from 'vue';
import * as dashboardService from 'src/service/dashboardService';
export default defineComponent({
  setup() {
    const authUser = ref<AuthUserPayload>();
    const fetchAuthUser = async () => {
      authUser.value = await dashboardService.getAuthUser();
    };
    onMounted(() => {
      fetchAuthUser();
    });

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
