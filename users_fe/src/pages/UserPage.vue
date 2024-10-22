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
import { defineComponent, onMounted, ref } from 'vue';
import * as userService from 'src/service/userService';
import { QTableColumn } from 'quasar';
import { UsersPayload } from 'src/payload/types';

export default defineComponent({
  setup() {
    const users = ref<UsersPayload[]>([]);
    const fetchUsers = async () => {
      users.value = await userService.getAllUsers();
    }
    onMounted(() => {
      fetchUsers();
    });

    const columns: QTableColumn[] = [
      { label: 'LINE NO', field: 'lineNo', name: 'lineNo', align: 'center' },
      { label: 'FIRST NAME', field: 'firstName', name: 'firstName', align: 'center' },
      { label: 'LAST NAME', field: 'lastName', name: 'lastName', align: 'center' },
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
      isFieldEditable,
      pagination: {
        page: 1,
        rowsPerPage: users.value.length,
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
