/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.ChessBoard;
import GameClasses.OnlineChessGame.ChessBoardMessage;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
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
@WebServlet(name = "BootPlayer", urlPatterns = {"/BootPlayer"})
public class BootPlayer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.InterruptedException
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BootPlayer</title>");            
            out.println("</head>");
            out.println("<body>");
            */
            String userId = request.getParameter("userId");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            
            ChessBoard chessBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber];
            if(chessBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true){
                if(chessBoard.getChessPlayBoard().isGameStarted() == false){
                    Player player = chessBoard.getChessBoardPlayerListWindow().getPlayer();
                    Queue<ChessBoardMessage> playerMessages = chessBoard.getChessBoardMessageWindow().getChessBoardMessage().get(player.getUserId());
                    String bootPlayerMessage = "<span class='bootPlayerMessage' style='color: red; font-style: italic; font-family: sans-serif'>You have been booted from the Chess Board</span>";
                    playerMessages.add(new ChessBoardMessage(bootPlayerMessage));

                    while(playerMessages.isEmpty() == false);

                    if(playerMessages.isEmpty() == true){
                        chessBoard.getChessBoardPlayerListWindow().bootPlayer();
                        chessBoard.getChessBoardMessageWindow().getChessBoardMessage().remove(player.getUserId());
                    }
                }
                /*
                out.println("<h1>Servlet BootPlayer at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
                */
            }
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
        } catch (InterruptedException ex) {
            Logger.getLogger(BootPlayer.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (InterruptedException ex) {
            Logger.getLogger(BootPlayer.class.getName()).log(Level.SEVERE, null, ex);
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