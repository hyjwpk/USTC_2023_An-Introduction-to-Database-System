import Layout from "@/layout/Index.vue";

const layoutMap = [
    {
        path: "table",
        name: "DataTable",
        meta: { title: "数据表格", roles: ["admin"] },
        component: () => import("@/views/Data/Table.vue")
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
