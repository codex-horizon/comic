// import {defineAsyncComponent, Plugin} from 'vue';
// const Horizon_Dialog = defineAsyncComponent(() => import('@/components/HorizonDialog.vue'));
// const HorizonDialog: Plugin = {
//     install(app) {
//         app.component(Horizon_Dialog.name, Horizon_Dialog);
//     }
// };

import Horizon_Dialog from '@/components/HorizonDialog.vue';
const HorizonDialog = {
    install: function (app) {
        app.component(Horizon_Dialog.name, Horizon_Dialog);
    }
}
export default HorizonDialog;