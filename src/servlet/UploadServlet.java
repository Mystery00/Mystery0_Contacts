package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;


/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // 配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // 设置临时存储目录
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);

            // 设置最大文件上传值
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // 设置最大请求值 (包含文件和表单数据)
            upload.setSizeMax(MAX_REQUEST_SIZE);

            // 中文处理
            upload.setHeaderEncoding("UTF-8");

            // 构造临时路径来存储上传的文件
            // 这个路径相对当前应用的目录
            String uploadPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;

            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
            {
                //noinspection ResultOfMethodCallIgnored
                uploadDir.mkdir();
            }

            FileItemIterator fileItemIterator = upload.getItemIterator(request);
            while (fileItemIterator.hasNext())
            {
                FileItemStream fileItemStream = fileItemIterator.next();

                if (!fileItemStream.isFormField())
                {
                    String path = fileItemStream.getName();
                    String fileName = path.substring(path.lastIndexOf("\\") + 1);

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileItemStream.openStream());
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + "\\" + fileName)));
                    Streams.copy(bufferedInputStream, bufferedOutputStream, true);
                    request.getSession().setAttribute("fileName", fileName);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Import Excel Error!");
            response.sendRedirect("index.jsp");
        }
        response.sendRedirect("ImportServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
