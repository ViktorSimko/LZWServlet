package com.viktorsimko.lzwservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/lzwservlet")
@MultipartConfig
public class LZWServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String parameter = httpServletRequest.getParameter("text_input");

        LZWBinFa binFa = new LZWBinFa();

        for (int i = 0; i < parameter.length(); i++) {
            binFa.egyBitFeldolg(parameter.charAt(i));
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        binFa.kiir(printWriter);

        printWriter.write(
                "<br/>Mélység: " + binFa.getMelyseg() + "<br/>Átlag: " + binFa.getAtlag() + "<br/>Szórás: " + binFa.getSzoras()
        );

        httpServletRequest.setAttribute("result", stringWriter.toString());
        httpServletRequest.getRequestDispatcher("/results.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Part part = httpServletRequest.getPart("file");
        InputStream inputStream = part.getInputStream();

        byte[] b = new byte[1];

        LZWBinFa binFa = new LZWBinFa();

        while (inputStream.read(b) != -1) {
            if (b[0] == 0x0a) {
                break;
            }
        }

        boolean kommentben = false;

        while (inputStream.read(b) != -1) {

            if (b[0] == 0x3e) {
                kommentben = true;
                continue;
            }

            if (b[0] == 0x0a) {
                kommentben = false;
                continue;
            }

            if (kommentben) {
                continue;
            }

            if (b[0] == 0x4e)
            {
                continue;
            }

            for (int i = 0; i < 8; ++i) {
                if ((b[0] & 0x80) != 0)
                {
                    binFa.egyBitFeldolg('1');
                } else
                {
                    binFa.egyBitFeldolg('0');
                }
                b[0] <<= 1;
            }

        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        binFa.kiir(printWriter);

        printWriter.write(
            "<br/>Mélység: " + binFa.getMelyseg() + "<br/>Átlag: " + binFa.getAtlag() + "<br/>Szórás: " + binFa.getSzoras()
        );

        httpServletRequest.setAttribute("result", stringWriter.toString());
        httpServletRequest.getRequestDispatcher("/results.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
