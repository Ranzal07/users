<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-8">
        <h2 class="text-center">List of Users</h2>
        <q-table
          class="custom-table"
          :columns="columns"
          :rows="rows"
          flat
          bordered
          v-model:pagination="pagination"
          :rows-per-page-options="[]"
          :separator="'vertical'"
        >
          <template v-slot:body="props">
            <q-tr :props="props">
              <q-td
                v-for="col in props.cols"
                :key="col.name"
                :props="props"
                :class="{
                  'line-no-col': col.name === 'lineNo',
                  'action-col': col.name === 'action',
                  'dynamic-col': isFieldEditable(col.name),
                }"
              >
                <span>{{ props.row[col.field] }}</span>
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </div>
    </div>
  </q-page>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import * as userService from 'src/service/userService';
import { QTableColumn } from 'quasar';
import { User, UserRow } from 'src/model/types';

export default defineComponent({
  setup() {
    const users = ref<UserRow[]>([]);

    const getUsers = async () => {
      try {
        const response = await userService.getAllUsers();
        users.value = response.map(
          (user: User, index: number):UserRow => {
            return {
              id: user.id,
              lineNo: index + 1,
              firstName: user.firstName,
              lastName: user.lastName,
              email: user.email,
              jobTitle: `${user.employment.level.name} ${user.employment.position.name}`
            };
          }
        );
      } catch (error) {
        console.error('Failed to fetch users', error);
      }
    };
    getUsers();

    const columns: QTableColumn[] = [
      { label: 'LINE NO', field: 'lineNo', name: 'lineNo', align: 'center' },
      { label: 'FIRST NAME', field: 'firstName', name: 'firstName', align: 'center' },
      { label: 'LAST NAME', field: 'lastName', name: 'lastName', align: 'center' },
      {
        label: 'EMAIL',
        field: 'email',
        name: 'email',
        align: 'center',
      },
      {
        label: 'JOB TITLE',
        field: 'jobTitle',
        name: 'jobTitle',
        align: 'center',
      }
    ];

    const isFieldEditable = (colName: string) => {
      return colName !== 'lineNo' && colName !== 'action';
    };

    return {
      rows: users,
      columns,
      getUsers,
      isFieldEditable,
      pagination: {
        page: 1,
        rowsPerPage: 10,
      },
    };
  },
});
</script>

<style scoped>
.container {
  margin-top: 20px;
}
</style>
