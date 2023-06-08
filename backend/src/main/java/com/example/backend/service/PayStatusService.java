package com.example.backend.service;

import com.example.backend.common.MyException;
import com.example.backend.common.Response;
import com.example.backend.common.ResponseEnum;
import com.example.backend.entity.PayStatus;
import com.example.backend.mapper.PayStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayStatusService {
    final PayStatusMapper payStatusMapper;

    @Autowired
    public PayStatusService(PayStatusMapper payStatusMapper) {
        this.payStatusMapper = payStatusMapper;
    }

    public Response edit(PayStatus payStatus) {
        try {
            payStatusMapper.edit(payStatus);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response add(PayStatus payStatus) {
        Integer status;
        Map<String, Object> map = new HashMap<>();
        try {
            payStatusMapper.add(payStatus, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        status = (Integer) map.get("status");
        if (status == -2) {
            return new Response(ResponseEnum.FAIL.getCode(), "贷款号输入错误", null);
        }
        if (status == -3) {
            return new Response(ResponseEnum.FAIL.getCode(), "还款不能为负数", null);
        }
        if (status == 0) {
            return new Response(ResponseEnum.FAIL.getCode(), "该贷款已还清", null);
        }
        if (status > 0) {
            return new Response(ResponseEnum.FAIL.getCode(), "只需还款" + status + "元即可", null);
        }
        return Response.success();
    }

    public Response delete(PayStatus payStatus) {
        try {
            payStatusMapper.delete(payStatus);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResponseEnum.FAIL);
        }
        return Response.success();
    }

    public Response page(Integer page, Integer size, PayStatus payStatus) {
        Integer start = (page - 1) * size;
        Integer count = payStatusMapper.count(payStatus);
        List<PayStatus> payStatusList = payStatusMapper.page(start, size, payStatus);
        Map<String, Object> map = new HashMap<>();
        map.put("data", payStatusList);
        map.put("count", count);
        return Response.success(map);
    }
}
