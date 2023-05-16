import Mock from "mockjs"
let userlist = [
    {
        name: "admin",
        password: "123456"
    },
    {
        name: "user",
        password: "111111"
    }
]


Mock.mock("/api/login", "post", (params) => {
    let bodyData = JSON.parse(params.body);

    let result = userlist.find(item => {
        return item.name === bodyData.name && item.password === bodyData.password
    }
    )
    if (result) {
        return {
            code: "0",
            message: "登录成功"
        };
    }
    return {
        code: "1",
        message: "登录失败"
    };
})

Mock.mock("/api/getUserList", "get", () => {
    return {
        data: userlist
    };
})

Mock.mock("/api/editUser", "post", (params) => {
    let bodyData = JSON.parse(params.body);
    let result = userlist.find(item => {
        return item.name === bodyData.name
    }
    )
    if (result) {
        result.password = bodyData.password;
        return {
            code: "0",
            message: "编辑成功"
        };
    }
    return {
        code: "1",
        message: "编辑失败"
    };
})


Mock.mock("/api/addUser", "post", (params) => {
    let bodyData = JSON.parse(params.body);
    userlist.push(bodyData);
    return {
        code: "0",
        message: "添加成功"
    };
})

Mock.mock("/api/deleteUser", "post", (params) => {
    let bodyData = JSON.parse(params.body);
    let index = userlist.findIndex(item => {
        return item.name === bodyData.name
    }
    )
    if (index > -1) {
        userlist.splice(index, 1);
        return {
            code: "0",
            message: "删除成功"
        };
    }
    return {
        code: "1",
        message: "删除失败"
    };
})
