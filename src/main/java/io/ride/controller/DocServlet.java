package io.ride.controller;

import io.ride.DO.Result;
import io.ride.util.DocUtil;
import io.ride.util.JacksonUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/doc")
public class DocServlet extends HttpServlet {


    // 上传文件目录
    private static final String TEMP_DIRECTORY = "temp";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 102481024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 12;

    // 返回的json数据
    private String jsonStr;

    // 临时目录路径
    private String path;

    // 上传文档的名字
    private String docName;

    // 文档转换为html后的html内容
    private String htmlContent;

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("====> init");
        path = getServletContext().getRealPath("/") + "/" + TEMP_DIRECTORY;
        System.out.println("path ======> " + path);
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("====> 文件夹创建成功");
            } else {
                System.out.println("====> 文件夹创建失败");
            }
        } else {
            System.out.println("====> 文件夹已存在, 无需重新创建");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String op = req.getParameter("op");
        System.out.println("op is =====>" + op);


        if (op == null) {
            jsonStr = JacksonUtil.toJSon(new Result(0));
            out.print(jsonStr);
            return;
        }

        if (op.equals("show")) {
            doShow(req, out);
        } else if (op.equals("upload")) {
            doUpload();
        } else {
            jsonStr = JacksonUtil.toJSon(new Result(0));
            out.print(jsonStr);
        }
    }

    /*
     * 将上传的文档转换成html
     */
    private void doShow(HttpServletRequest request, PrintWriter out) {
        // 检查是否为文件上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            Result result = new Result(0);
            result.setData("msg", "Error: 表单必须包含 enctype=multipart/form-data");
            jsonStr = JacksonUtil.toJSon(result);
            out.print(jsonStr);
            out.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值, 超过后产生的临时文件并存储到临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置文件上传的最大值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值(包括文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        upload.setHeaderEncoding("UTF-8");

        try {
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    // 文件
                    if (!item.isFormField()) {
                        System.out.println("filename is " + item.getName());
                        if (item.getName() == null || item.getName().equals("")) {
                            out.print(JacksonUtil.toJSon(new Result(0).setData("msg", "未选择文件!")));
                            out.flush();
                            return;
                        }
                        docName = item.getName();
                        htmlContent = DocUtil.doc2Html(item.getInputStream(), path);
                    }
                }
            }

            jsonStr = JacksonUtil.toJSon(new Result(1).setData("msg", "Success").setData("content", htmlContent));
            out.print(jsonStr);
            out.flush();
        } catch (Exception e) {
            jsonStr = JacksonUtil.toJSon(new Result(0).setData("msg", "Error: Unknown"));
            out.print(jsonStr);
            out.flush();
            e.printStackTrace();
        }
    }

    private void doUpload() {
        // TODO 将html代码保存到数据库
    }
}
