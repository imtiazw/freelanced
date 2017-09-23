/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.ChessBoard;
import GameClasses.OnlineChessGame.ChessBoardMessage;
import GameClasses.OnlineChessGame.ChessBoardMessageWindow;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Player;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALI
 */
@WebServlet(name = "SendMessageChessBoard", urlPatterns = {"/SendMessageChessBoard"})
public class SendMessageChessBoard extends HttpServlet {

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
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendMessageChessBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendMessageChessBoard at " + request.getContextPath() + "</h1>");
            */
            String userId = request.getParameter("userId");
            String message = request.getParameter("message");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            ChessBoard chessBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber];
            if(chessBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true ||
                    ((chessBoard.getChessBoardPlayerListWindow().getPlayer() != null) &&
                        chessBoard.getChessBoardPlayerListWindow().getPlayer().getUserId().equals(userId) == true) == true){
                ChessBoardMessageWindow chessBoardMessageWindow = chessBoard.getChessBoardMessageWindow();

                if(chessBoardMessageWindow.isEnable() == true){
                    chessBoardMessageWindow.getOtherPlayersMessage(userId).add(new ChessBoardMessage(message));
                }
                if(chessBoardMessageWindow.isEnable() == false){
                    message = "<span class='.disabledChatMessage' style='color: red; font-style: italic; font-size: 14px; font-family: sans-serif;'>Chat is Disabled by the other player. Messages are undelived.</span>";
                    Player nativePlayer = ((GameRoom) getServletContext().getAttribute("gameRoom")).getGameRoomPlayerListWindow().getPlayer(userId);
                    chessBoardMessageWindow.appendMessageToThisPlayerMessage(nativePlayer, message);
                }
                /*
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
        processRequest(request, response);
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
