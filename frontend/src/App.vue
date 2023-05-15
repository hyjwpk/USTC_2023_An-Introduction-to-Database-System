<template>
  <router-view />
</template>

<script>
import { onMounted } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();

    onMounted(() => {
      let jwt = sessionStorage.getItem("jwt");
      if (jwt) {
        let token = JSON.parse(jwt);
        store.dispatch("setUser", token);
      }
    });
  }
};
</script>