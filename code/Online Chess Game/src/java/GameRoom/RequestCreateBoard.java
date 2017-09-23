/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameRoom;

import GameClasses.OnlineChessGame.BoardHost;
import GameClasses.OnlineChessGame.ChessBoardList;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
@WebServlet(name = "RequestCreateBoard", urlPatterns = {"/RequestCreateBoard"})
public class RequestCreateBoard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RequestCreateBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            JSONObject hostGame = new JSONObject();
            ChessBoardList chessBoardList = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList();
            if(chessBoardList.hostGame() == true){
                String userId = request.getParameter("cookie");
                Player playerId = ((GameRoom) getServletContext().getAttribute("gameRoom")).getGameRoomPlayerListWindow().getPlayer(userId);              
                int tableNumber = chessBoardList.createBoard(Integer.parseInt(request.getParameter("maximumGameTime")), Integer.parseInt(request.getParameter("timePerMove")), new BoardHost().toBoardHost(playerId));
                hostGame.put("tableNumber", tableNumber);
                hostGame.put("mayHost", true);
                
            }else{
                hostGame.put("mayHost", false);
            }
            
            out.print(hostGame);
            /*
            out.println("<h1>Servlet RequestCreateBoard at " + request.getContextPath() + "</h1>");
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
        } catch (JSONException ex) {
            Logger.getLogger(RequestCreateBoard.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(RequestCreateBoard.class.getName()).log(Level.SEVERE, null, ex);
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
