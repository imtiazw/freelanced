/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SignUp;

import DataAccessClasses.UpdateTableDAO;
import GameClasses.SingInUpRecoverPassword.SignUpProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALI
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignUp</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignUp at " + request.getContextPath() + "</h1>");
            */
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String userId = request.getParameter("userid");
            String password = request.getParameter("password");
            String contact = request.getParameter("contact");
            String gender = request.getParameter("Gender");
            String primaryEmail = request.getParameter("primaryemail");
            String secondaryEmail = request.getParameter("secondaryemail");
            String address = request.getParameter("address");
            String dateOfBirth = request.getParameter("month") + " " + request.getParameter("date") + " " + request.getParameter("year");
            
            SignUpProfile signUpProfile = new SignUpProfile(userId, firstName, lastName, dateOfBirth, gender, address, contact, 
                                                            primaryEmail, password, secondaryEmail, false, 0);
            
            String isAlreadyRegistered = signUpProfile.isAlreadyRegistered();
            if(isAlreadyRegistered.equals("userId")){
                response.sendRedirect("userid_registered.html");
            }else if(isAlreadyRegistered.equals("primaryEmail")){
                response.sendRedirect("primary_email.html");
            }else if(isAlreadyRegistered.equals("")){
                String authenticationSql = "INSERT INTO [Authentication]"
                        + "VALUES ('" + userId + "','" + password + "','" + primaryEmail + "','" + secondaryEmail + "'," + "0, 0)";
                new UpdateTableDAO().insertIntoTable(authenticationSql);
                
                String playerSql = "INSERT INTO [Player]"
                        + "VALUES ('" + userId + "', 0)";
                new UpdateTableDAO().insertIntoTable(playerSql);
                
                String verificationCode = new VerificationCode().getCode();
                String emailSql = "INSERT INTO [Email Verification] "
                        + "(userId, verification_code)"
                        + "VALUES ('" + userId + "','" + verificationCode + "')";
                new UpdateTableDAO().insertIntoTable(emailSql);
                String subject = "Your verification code is\n"
                        + verificationCode + "\n"
                        + "Please go to the following link and paste this verification code along with your user id\n"
                        + "http://localhost:8080/OnlineChessGame/email_sent.html";
                
                String userSql = "INSERT INTO [User]"
                        + "VALUES ('" + userId + "','" + firstName + "','" + lastName + "','" + dateOfBirth + "','" + gender.charAt(0) + "','"
                        + address + "','" + contact + "','" + userId + "','" + userId + "','" + userId +"')";
                new UpdateTableDAO().insertIntoTable(userSql);
                
                if(new GenerateEmail().sendEmail(primaryEmail, subject) == true){
                    response.sendRedirect("email_sent.html");
                }
            }
            /*
            out.println("</body>");
            out.println("</html>");
            */
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
