export default {
    strict: process.env.NODE_ENV !== 'production',
    namespaced: true,
    state: {
        inlineStyleScrollbar: {
            'border-top-left-radius': '12px',
            'border-top-right-radius': '12px'
        }
    },
    getters: {
        getInlineStyleScrollbar(state) {
            return state.inlineStyleScrollbar;
        }
    },
    mutations: {},
    actions: {}
};