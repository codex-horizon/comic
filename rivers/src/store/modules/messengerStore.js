export default {
    strict: process.env.NODE_ENV !== 'production',
    namespaced: true,
    state: {
        inlineStyleScrollbar: {
            'border-top-left-radius': '12px',
            'border-top-right-radius': '12px'
        },
        dialog: { // 后续改造Object.assign()
            currentView: '',
            visible: false,
            title: '对话框',
            width: '36%',
            footer: true,
            fullScreen:false
        },
        table: {
            data: [
                {
                    date: '2016-05-03',
                    name: 'Tom',
                    address: 'No. 189, Grove St, Los Angeles',
                },
                {
                    date: '2016-05-02',
                    name: 'John',
                    address: 'No. 189, Grove St, Los Angeles',
                },
                {
                    date: '2016-05-04',
                    name: 'Morgan',
                    address: 'No. 189, Grove St, Los Angeles',
                },
                {
                    date: '2016-05-01',
                    name: 'Jessy',
                    address: 'No. 189, Grove St, Los Angeles',
                },
            ],
            search: '',
            pagination: {
                currentPage: 1,
                pageSize: 10,
                pageSizes: [100, 200, 300, 400],
                background: true,
                layout: ['total', 'sizes', 'prev', 'pager', 'next', 'jumper'],
                total: 100
            }
        }
    },
    getters: {
        getInlineStyleScrollbar: state => {
            return state.inlineStyleScrollbar;
        },
        getDialogCurrentView: state => {
            return state.dialog.currentView;
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
        },
        getDialogFullScreen: state => {
            return state.dialog.fullScreen;
        },
        getTableData: state => {
            return state.table.data;
        },
        getTableSearch: state => {
            return state.table.search;
        },
        getTablePaginationCurrentPage: state => {
            return state.table.pagination.currentPage;
        },
        getTablePaginationPageSize: state => {
            return state.table.pagination.pageSize;
        },
        getTablePaginationPageSizes: state => {
            return state.table.pagination.pageSizes;
        },
        getTablePaginationBackground: state => {
            return state.table.pagination.background;
        },
        getTablePaginationLayout: state => {
            return state.table.pagination.layout.join(',');
        },
        getTablePaginationTotal: state => {
            return state.table.pagination.total;
        },
    },
    mutations: {
        setDialogCurrentView(state, currentView) {
            state.dialog.currentView = currentView;
        },
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
        },
        setDialogFullScreen(state, fullScreen) {
            state.dialog.fullScreen = fullScreen;
        },
        setTableData(state, data) {
            state.table.data = data;
        },
        setTableSearch(state, search) {
            state.table.search = search;
        },
        setTablePaginationCurrentPage(state, currentPage) {
            state.table.pagination.currentPage = currentPage;
        },
        setTablePaginationPageSize(state, pageSize) {
            state.table.pagination.pageSize = pageSize;
        },
        setTablePaginationPageSizes(state, pageSizes) {
            state.table.pagination.pageSizes = pageSizes;
        },
        setTablePaginationBackground(state, background) {
            state.table.pagination.background = background;
        },
        setTablePaginationLayout(state, layout) {
            state.table.pagination.layout = layout.split(",");
        },
        setTablePaginationTotal(state, total) {
            state.table.pagination.total = total;
        }
    },
    actions: {}
};