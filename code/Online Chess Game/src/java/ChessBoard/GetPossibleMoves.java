/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Piece;
import GameClasses.OnlineChessGame.Position;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "GetPossibleMoves", urlPatterns = {"/GetPossibleMoves"})
public class GetPossibleMoves extends HttpServlet {

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
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetPossibleMoves</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetPossibleMoves at " + request.getContextPath() + "</h1>");
            */
            String userId = request.getParameter("cookie");
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            String piece = request.getParameter("piece");
            String team = request.getParameter("team");
            Piece[] pieces = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber].getChessPlayBoard().getPiece();
            JSONObject nextPossibleMoves = new JSONObject();
            
            switch(piece){
                case "ALL":
                    if(team.equals("pink")){
                        int pinkStart = 16;
                        int pinkEnd = 31;
                        for(int i=pinkStart; i<=pinkEnd; i++){
                            nextPossibleMoves.put(pieces[i].getName(), pieces[i].getNextPossiblePositionsAsStrings());
                        }
                    }else if(team.equals("brown")){
                        int brownStart = 0;
                        int brownEnd = 15;
                        for(int i=brownStart; i<=brownEnd; i++){
                            nextPossibleMoves.put(pieces[i].getName(), pieces[i].getNextPossiblePositionsAsStrings());
                        }
                    }
                 
                    break;
                default:
                    nextPossibleMoves.put(piece, nextPossiblePositions(piece, team, pieces));
            }
            
            out.print(nextPossibleMoves);
            /*
            out.println("</body>");
            out.println("</html>");
            */
        }
    }
    
    
    private ArrayList<String> nextPossiblePositions(String piece, String team, Piece[] pieces){
    
        ArrayList<String> nextPossiblePositions = null;
        switch (team) {
            case "pink":
                int pinkStart = 16;
                int pinkEnd = 31;
                for(int i=pinkStart; i<=pinkEnd; i++){
                    if(pieces[i].getName().equals(piece)){
                        nextPossiblePositions = pieces[i].getNextPossiblePositionsAsStrings();
                    }
                }   break;
            case "brown":
                int brownStart = 0;
                int brownEnd = 15;
                for(int i=brownStart; i<=brownEnd; i++){
                    if(pieces[i].getName().equals(piece)){
                        nextPossiblePositions = pieces[i].getNextPossiblePositionsAsStrings();
                    }
                }   break;
        }
        
        return nextPossiblePositions;
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
            Logger.getLogger(GetPossibleMoves.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GetPossibleMoves.class.getName()).log(Level.SEVERE, null, ex);
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
