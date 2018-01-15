package io.ride.controller;

import io.ride.DO.Result;
import io.ride.util.JacksonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class Hello extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();

        Result result = new Result(1);
        result.setData("info", "this is test!");

        String jsonStr = JacksonUtil.toJSon(result);

        out.print(jsonStr);

    }

    public static void main(String[] args) {
    }
}
