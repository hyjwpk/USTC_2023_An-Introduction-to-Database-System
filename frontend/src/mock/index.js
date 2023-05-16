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
      message: "success"
    };
  }
  return {
    code: "1",
    message: "fail"
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
      message: "success"
    };
  }
  return {
    code: "1",
    message: "fail"
  };
})


Mock.mock("/api/addUser", "post", (params) => {
  let bodyData = JSON.parse(params.body);
  userlist.push(bodyData);
  return {
    code: "0",
    message: "success"
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
      message: "success"
    };
  }
  return {
    code: "1",
    message: "fail"
  };
})
