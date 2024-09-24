<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center">
        <q-card class="q-pa-md shadow-2 my_card" bordered>
          <q-form @submit.prevent="authUser">
            <q-card-section class="text-center">
              <div class="text-h5 text-weight-bold">Sign in</div>
              <div>
                Sign in below to access your account
              </div>
              <div>
                {{ errorMessage }}
              </div>
            </q-card-section>
            <q-card-section>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left'}"
                v-model="username"
                label="Username"
                autocomplete="username"
              ></q-input>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left' }"
                class="q-mt-md"
                v-model="password"
                type="password"
                label="Password"
                autocomplete="current-password"
              ></q-input>
            </q-card-section>
            <div v-if="errorMessage" class="error-message q-mt-sm">
              {{ errorMessage }}
            </div>
            <q-card-section>
              <q-btn
                type="submit"
                style="border-radius: 10px"
                rounded
                size="md"
                label="Sign in"
                no-caps
                class="full-width"
              ></q-btn>
            </q-card-section>
            <q-card-section class="text-center q-pt-none">
              <div>
                Don't have an account yet?
                <a
                  href="#"
                  class="text-weight-bold"
                  style="text-decoration: none"
                  >Signup.</a
                >
              </div>
            </q-card-section>
          </q-form>
        </q-card>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import { authenticateUser } from 'src/service/authService';
import axios from 'axios';
export default defineComponent({
  setup() {
    const username = ref('');
    const password = ref('');
    const errorMessage = ref('');
    const router = useRouter();

    const authUser = async () => {
      try {
        await authenticateUser(username.value, password.value);
        router.push('/v1/dashboard/index');
      } catch (error: unknown) {
        if (axios.isAxiosError(error) && error.response) {
          errorMessage.value = error.response.data || 'Invalid username or password.';
        } else if (error instanceof Error) {
          errorMessage.value = error.message;
        } else {
          errorMessage.value = 'An unknown error occurred';
        }
      }
    };
    return {
      username,
      password,
      authUser,
      errorMessage,
    };
  },
});
</script>

<style>
.my_card {
  width: 25rem;
  border-radius: 8px;
  box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1),
    0 8px 10px -6px rgb(0 0 0 / 0.1);
}
.error-message {
  color: red;
  font-weight: bold;
  text-align: center;
}
</style>
