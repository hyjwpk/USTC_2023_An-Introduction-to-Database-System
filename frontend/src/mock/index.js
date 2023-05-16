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
  console.log(result)
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

