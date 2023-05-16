import Mock from "mockjs"

Mock.mock("/api/login", "post", (params) => {
    let newData = JSON.parse(params.body);
    if (newData.name === "admin" && newData.password === "123456") {
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

