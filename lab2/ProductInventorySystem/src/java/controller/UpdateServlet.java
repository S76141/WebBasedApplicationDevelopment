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
import model.Product;
import dao.ProductDao;

/**
 *
 * @author DING WEI QI
 */
public class UpdateServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateServlet at " + request.getContextPath() + "</h1>");
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
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Product existingProduct = productdao.selectProduct(id);
        
        if (existingProduct != null) {
            out.println("<h2>Update Product (Using DAO)</h2>");
            out.println("<form action='UpdateServlet' method='POST'>");
            out.println("<input type='hidden' name='id' value='" + existingProduct.getId() + "'>");
            out.println("Name: <input type='text' name='name' value='" + existingProduct.getName() + "' required><br><br>");
            out.println("Category: <input type='text' name='category' value='" + existingProduct.getCategory() + "' required><br><br>");
            out.println("Price: <input type='text' name='price' value='" + existingProduct.getPrice() + "' required><br><br>");
            out.println("Quantity: <input type='text' name='quantity' value='" + existingProduct.getQuantity() + "' required><br><br>");
            out.println("<input type='submit' value='Update User'>");
            out.println("</form>");
        }
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
        
         int id = Integer.parseInt(request.getParameter("id"));
        
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String p = request.getParameter("price");
        String q = request.getParameter("quantity");
        
        double price = Double.parseDouble(p);
        int quantity = Integer.parseInt(q);
        
        Product user = new Product(id, name, category, price, quantity);
        productdao.updateProduct(user); // Panggil DAO
        
        response.sendRedirect("ViewServlet");
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
