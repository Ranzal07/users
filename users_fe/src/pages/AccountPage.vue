<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-8">
        <h2 class="text-center">Account Management</h2>
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
                  'dynamic-col': col.name !== 'lineNo' && col.name !== 'action',
                }"
              >
                <q-input
                  v-if="isFieldEditable(col.name) && isEditing(props.row)"
                  v-model="props.row[col.field]"
                  outlined
                  dense
                />
                <span v-else>{{ props.row[col.name] }}</span>
                <template v-if="col.name === 'action'">
                  <q-btn
                    v-if="!isEditing(props.row)"
                    color="primary"
                    label="Edit"
                    outline
                    style="margin: 8px"
                    @click="startEditing(props.row)"
                  />
                  <q-btn
                    v-if="isEditing(props.row)"
                    color="primary"
                    label="Update"
                    outline
                    style="margin: 8px"
                    @click="updateRow(props.row)"
                  />
                  <q-btn
                    v-if="isEditing(props.row)"
                    color="secondary"
                    label="Cancel"
                    outline
                    @click="cancelEdit(props.row)"
                  />
                </template>
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
import { User } from 'src/model/types';
import { QTableColumn } from 'quasar';

export default defineComponent({
  setup() {
    const users = ref<User[]>([]);
    const authUser = ref<any[]>([]);
    const editingRow = ref<User>();
    const originalData = ref<User>();

    const getUsers = async () => {
      try {
        const authData = localStorage.getItem('authData');
        if (authData) {
          authUser.value = [JSON.parse(authData).user];
        }
        users.value = await userService.getAllUsers();
        const foundUser = users.value.find((user: User) => user.id === authUser.value[0].id);
        authUser.value = [foundUser];
      } catch (error) {
        console.error('Failed to fetch users', error);
      }
    };
    getUsers();

    const columns: QTableColumn[] = [
      {
        label: 'FIRST NAME',
        field: 'firstName',
        name: 'firstName',
        align: 'center',
      },
      {
        label: 'LAST NAME',
        field: 'lastName',
        name: 'lastName',
        align: 'center',
      },
      { label: 'EMAIL', field: 'email', name: 'email', align: 'center' },
      { label: 'ACTION', field: '', name: 'action', align: 'center' },
    ];

    const isFieldEditable = (colName: string) => {
      return colName === 'firstName' || colName === 'lastName' || colName === 'email';
    };

    const startEditing = (userRow: User) => {
      editingRow.value = userRow;
      originalData.value = { ...userRow };
    };

    const isEditing = (userRow: User) => {
      return editingRow.value === userRow;
    };

    const updateRow = async (userRow: User) => {
      if (editingRow.value === userRow) {
        try {
          await userService.updateAuthUser(userRow);
          window.location.reload();
        } catch (error) {
          console.error('Failed to update userRow: ', error);
        }
      }
    };

    const cancelEdit = (userRow: User) => {
      const index = authUser.value.findIndex((r: User) => r.id === userRow.id);
      if (index !== -1 && originalData.value) {
        authUser.value[index] = { ...originalData.value };
      }
    };

    return {
      rows: authUser,
      columns,
      startEditing,
      updateRow,
      isEditing,
      cancelEdit,
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
