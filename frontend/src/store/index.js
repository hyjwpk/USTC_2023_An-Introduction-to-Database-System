import { createStore } from "vuex";
import { CLEAR_USER, SET_USER, SET_ROUTES } from "./mutation-types";
import { layoutMap } from "@/router/router";

const state = {
    users: null,
    routers: []
};

const getters = {
    getUserName(state) {
        return !state.users ? "" : state.users.username;
    }
};

const mutations = {
    [CLEAR_USER](state) {
        state.users = null;
        state.routers = [];
    },
    [SET_USER](state, payload) {
        state.users = payload;
    },
    [SET_ROUTES](state, payload) {
        state.routers = payload;
    }
};

const actions = {
    clearUser({ commit }) {
        commit(CLEAR_USER);
    },
    setUser({ commit }, payload) {
        commit(SET_USER, payload);
        commit(SET_ROUTES, layoutMap);
    }
};

export default createStore({
    state,
    getters,
    mutations,
    actions
});
