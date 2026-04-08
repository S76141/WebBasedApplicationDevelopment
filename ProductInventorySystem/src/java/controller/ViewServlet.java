/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;
import dao.ProductDao;

/**
 *
 * @author DING WEI QI
 */
public class ViewServlet extends HttpServlet {
    
    private ProductDao productdao;
    
    public void init(){
        productdao = new ProductDao();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
            
        PrintWriter out = response.getWriter();
        
        List <Product> listProduct = productdao.selectAllProducts();
        
        out.println("<h2>Product List (Using DAO)</h2>");
        out.println("<table border='1'><tr><th>ID</th><th>Name</th>"
                + "<th>Category</th><th>Price</th><th>Quantity</th></tr>");
        
        for (Product product : listProduct) {
            out.println("<tr>");
            out.println("<td>" + product.getId() + "</td>");
            out.println("<td>" + product.getName() + "</td>");
            out.println("<td>" + product.getCategory() + "</td>");
            out.println("<td>" + product.getPrice() + "</td>");
            out.println("<td>" + product.getQuantity() + "</td>");
            out.println("<td><a href='UpdateServlet?id=" + product.getId() + "'>Edit</a> | ");
            out.println("<a href='DeleteServlet?id=" + product.getId() + "'>Delete</a></td>");
            out.println("</tr>");
        }
        
        out.println("</table>");
        out.println("<br><a href='index.html'>Add New Product</a>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
