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
                    @click="cancelEdit()"
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
import { defineComponent, onMounted, ref } from 'vue';
import * as dashboardService from 'src/service/dashboardService';
import * as userService from 'src/service/userService';
import { AuthUserPayload } from 'src/payload/types';
import { QTableColumn } from 'quasar';
import { useRouter } from 'vue-router';
import { isValidToken } from 'src/router/authGuard';

export default defineComponent({
  setup() {
    const authUser = ref<AuthUserPayload[]>([]);
    const editingRow = ref<AuthUserPayload>();
    const originalData = ref<AuthUserPayload>();
    const router = useRouter();

    const fetchAuthUser = async () => {
      authUser.value = [await dashboardService.getAuthUser()];
    };
    onMounted(() => {
      fetchAuthUser();
    });

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

    const startEditing = (userRow: AuthUserPayload) => {
      editingRow.value = userRow;
      originalData.value = { ...userRow };
    };

    const isEditing = (userRow: AuthUserPayload) => {
      return editingRow.value === userRow;
    };

    const updateRow = async (userRow: AuthUserPayload) => {
      if (await isValidToken(router) && editingRow.value === userRow) {
        await userService.updateAuthUser(userRow);
        window.location.reload();
      }
    };

    const cancelEdit = () => {
      fetchAuthUser();
    };

    return {
      rows: authUser,
      columns,
      startEditing,
      updateRow,
      isEditing,
      cancelEdit,
      isFieldEditable,
    };
  },
});
</script>

<style scoped>
.container {
  margin-top: 20px;
}
</style>
