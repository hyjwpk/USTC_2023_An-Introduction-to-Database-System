import Layout from "@/layout/Index.vue";
import RouteView from "@/components/RouteView.vue";

const layoutMap = [
    {
        path: "data",
        name: "Data",
        component: RouteView,
        meta: { title: "数据管理", icon: "DataLine" },
        children: [
            {
                path: "table",
                name: "DataTable",
                meta: { title: "数据表格", roles: ["admin"] },
                component: () => import("@/views/Data/Table.vue")
            }
        ]
    }
];

const routes = [
    {
        path: '/Login',
        name: 'login',
        component: () => import('@/views/Login/Login.vue')
    },
    {
        path: "/",
        name: "Layout",
        component: Layout,
        children: [...layoutMap]
    }
];

export { routes, layoutMap };
