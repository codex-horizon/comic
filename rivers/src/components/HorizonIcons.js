import * as Icons from "@element-plus/icons-vue";
const HorizonIcons = {
    install: function (app) {
        for (const [name, component] of Object.entries(Icons)) {
            app.component(name, component);
        }
    }
}
export default HorizonIcons;