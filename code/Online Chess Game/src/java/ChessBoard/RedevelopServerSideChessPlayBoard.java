/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.ChessBoard;
import GameClasses.OnlineChessGame.ChessBoardPlayerListWindow;
import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.GameRoom;
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
@WebServlet(name = "RedevelopServerSideChessPlayBoard", urlPatterns = {"/RedevelopServerSideChessPlayBoard"})
public class RedevelopServerSideChessPlayBoard extends HttpServlet {

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
            throws ServletException, IOException, JSONException{
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RedevelopServerSideChessPlayBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            
            String userId = request.getParameter("userId");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            JSONObject result = new JSONObject();
            
            ChessBoard chessBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber];
            chessBoard.setPlaying(true);
            int maximumGameTime = chessBoard.getChessPlayBoard().getMaximumGameTimeConstant();
            int timePerMove = chessBoard.getChessPlayBoard().getTimePerMoveConstant();
            chessBoard.setChessPlayBoard(new ChessPlayBoard());
            chessBoard.getChessPlayBoard().setTime(maximumGameTime, timePerMove);
            chessBoard.getChessPlayBoard().setChessBoardPlayerListWindow(chessBoard.getChessBoardPlayerListWindow());
            
            result.put("redevelopServerSideChessPlayBoardResponse", true);
            
            out.print(result);
            /*
            out.println("<h1>Servlet RedevelopServerSideChessPlayBoard at " + request.getContextPath() + "</h1>");
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
            Logger.getLogger(RedevelopServerSideChessPlayBoard.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RedevelopServerSideChessPlayBoard.class.getName()).log(Level.SEVERE, null, ex);
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
