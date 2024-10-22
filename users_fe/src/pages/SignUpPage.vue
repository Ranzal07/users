<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center">
        <q-card class="q-pa-md shadow-2 my_card" bordered>
          <q-form @submit.prevent="authUser">
            <q-card-section class="text-center">
              <div class="text-h5 text-weight-bold">Sign up</div>
              <div>
                Sign up below to create your new account
              </div>
            </q-card-section>
            <q-card-section>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left'}"
                v-model="firstName"
                label="First Name"
                autocomplete="firstName"
              ></q-input>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left'}"
                class="q-mt-md"
                v-model="lastName"
                label="Last Name"
                autocomplete="lastName"
              ></q-input>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left'}"
                class="q-mt-md"
                v-model="username"
                label="Username"
                autocomplete="username"
              ></q-input>
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left'}"
                class="q-mt-md"
                v-model="email"
                label="Email"
                autocomplete="email"
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
              <q-input
                outlined
                :input-style="{ color: 'white', textAlign: 'left' }"
                class="q-mt-md"
                v-model="confirmPassword"
                type="password"
                label="Confirm Password"
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
                label="Sign up"
                no-caps
                class="full-width"
              ></q-btn>
            </q-card-section>
            <q-card-section class="text-center q-pt-none">
              <div>
                Already have an account yet?
                <a
                  href="/v1/auth/login"
                  class="text-weight-bold"
                  style="text-decoration: none"
                  >Login</a
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
import { NewUser } from 'src/payload/types';
import { addNewUser } from 'src/service/authService';
import axios from 'axios';
export default defineComponent({
  setup() {
    const firstName = ref('');
    const lastName = ref('');
    const username = ref('');
    const email = ref('');
    const password = ref('');
    const confirmPassword = ref('');
    const errorMessage = ref('');
    const router = useRouter();
    const newUser = ref<NewUser>();

    const authUser = async () => {
      try {
        newUser.value = {
          firstName: firstName.value,
          lastName: lastName.value,
          username: username.value,
          email: email.value,
          password: password.value,
          confirmPassword: confirmPassword.value
        };
        await addNewUser(newUser.value);
        router.push('/v1/dashboard/index');
      } catch (error: unknown) {
        if (axios.isAxiosError(error) && error.response) {
          errorMessage.value = error.response.data;
        } else if (error instanceof Error) {
          errorMessage.value = error.message;
        } else {
          errorMessage.value = 'An unknown error occurred';
        }
      }
    };
    return {
      firstName,
      lastName,
      username,
      email,
      password,
      confirmPassword,
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
