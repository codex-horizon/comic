export default {
    strict: process.env.NODE_ENV !== 'production',
    namespaced: true,
    state: {
        inlineStyleScrollbar: {
            'border-top-left-radius': '12px',
            'border-top-right-radius': '12px'
        },
        dialog: { // 后续改造Object.assign()
            visible: false,
            title: '对话框',
            width: '36%',
            footer: true
        }
    },
    getters: {
        getInlineStyleScrollbar: state => {
            return state.inlineStyleScrollbar;
        },
        getDialogVisible: state => {
            return state.dialog.visible;
        },
        getDialogTitle: state => {
            return state.dialog.title;
        },
        getDialogWidth: state => {
            return state.dialog.width;
        },
        getDialogFooter: state => {
            return state.dialog.footer;
        }
    },
    mutations: {
        setDialogVisible(state, visible) {
            state.dialog.visible = visible;
        },
        setDialogTitle(state, title) {
            state.dialog.title = title;
        },
        setDialogWidth(state, width) {
            state.dialog.width = width;
        },
        setDialogFooter(state, footer) {
            state.dialog.footer = footer;
        }
    },
    actions: {}
};