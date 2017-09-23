/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChessBoard;

import DataAccessClasses.GamesDAO;
import DataAccessClasses.PersonDAO;
import GameClasses.LastMove.Castle;
import GameClasses.LastMove.Check;
import GameClasses.LastMove.EnPassantGiven;
import GameClasses.LastMove.EnPassantPlayed;
import GameClasses.LastMove.LastMove;
import GameClasses.LastMove.PawnUpgrade;
import GameClasses.LastMove.PieceBeaten;
import GameClasses.LastMove.SimpleMove;
import GameClasses.LastMove.SpecialMove;
import GameClasses.OnlineChessGame.ChessPlayBoard;
import GameClasses.OnlineChessGame.GameRoom;
import GameClasses.OnlineChessGame.Person;
import GameClasses.OnlineChessGame.Player;
import com.google.gson.Gson;
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ALI
 */
@WebServlet(name = "PlayerMove", urlPatterns = {"/PlayerMove"})
public class PlayerMove extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.json.JSONException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException, SQLException, ClassNotFoundException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlayerMove</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlayerMove at " + request.getContextPath() + "</h1>");
            */
            String userId = request.getParameter("userId");
            String lastMoveString = request.getParameter("lastMove");
            System.out.print(lastMoveString);
            int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
            String team = getTeam(request.getParameter("team"));
            LastMove lastMove = new Gson().fromJson(lastMoveString, LastMove.class);
            System.out.print(lastMove);
            
            JSONObject result = new JSONObject();
            
            ChessPlayBoard chessPlayBoard = ((GameRoom) getServletContext().getAttribute("gameRoom")).getChessBoardList().getChessBoard()[tableNumber].getChessPlayBoard();
            if( chessPlayBoard.getChessBoardPlayerListWindow().getPlayer() != null){
                if(chessPlayBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId().equals(userId) == true ||
                        chessPlayBoard.getChessBoardPlayerListWindow().getPlayer().getUserId().equals(userId) == true ){
                    lastMove.fromShallowStructureToDeepStrcture(chessPlayBoard);   //converting shallow to deep structure
                    chessPlayBoard.setLastMove(lastMove);

                    if(chessPlayBoard.isGameStarted() == true){
                        if(chessPlayBoard.getCurrentPlayerControl().equals(userId) == true){
                            if(chessPlayBoard.getMaximumGameTime() > 0){
                                if(chessPlayBoard.getTimePerMove() > 0){
                                    if(lastMove.isMoveSkipped() == false){

                                        if(lastMove.issImpleMove() == true){
                                            if(lastMove.getSimpleMove().getPieceMoved().isValidMove(lastMove.getSimpleMove().getCurrentPosition()) == true){
                                                SimpleMove simpleMove = lastMove.getSimpleMove();
                                                chessPlayBoard.movePiece(simpleMove.getPieceMoved(), simpleMove.getPreviousPosition(), simpleMove.getCurrentPosition());
                                                chessPlayBoard.recalculatePositionsAfterMovePlayed(team, simpleMove);
                                                chessPlayBoard.findIfCheckGiven(lastMove.getSimpleMove().getPieceMoved(), team);
                                            }
                                        }

                                        if(lastMove.ispIeceBeaten() == true){
                                            if(lastMove.issPecialMove() == false){
                                                PieceBeaten pieceBeaten = lastMove.getPieceBeaten();
                                                if(pieceBeaten.getBeatenBy().isValidMove(pieceBeaten.getBeatingPieceCurrentPosition()) == true){
                                                    chessPlayBoard.removePiece(pieceBeaten.getPieceBeaten());
                                                    chessPlayBoard.movePiece(pieceBeaten.getBeatenBy(), pieceBeaten.getBeatingPiecePreviousPosition(), pieceBeaten.getBeatingPieceCurrentPosition());
                                                    chessPlayBoard.recalculatePositionsAfterMovePlayed(team, pieceBeaten);
                                                    chessPlayBoard.findIfCheckGiven(pieceBeaten.getBeatenBy(), team);
                                                }
                                            }
                                        }

                                        if(lastMove.issPecialMove() == true){
                                            SpecialMove specialMove = lastMove.getSpecialMove();
                                            if(specialMove.iscAstle() == true){
                                                if(lastMove.ispIeceBeaten() == false){
                                                    Castle castle = specialMove.getCastle();
                                                    if(castle.getKingName().isValidMove(castle.getKingCurrentPosition()) == true){
                                                        chessPlayBoard.movePiece(castle.getKingName(), castle.getKingPreviousPosition(), castle.getKingCurrentPosition());
                                                        chessPlayBoard.movePiece(castle.getRockName(), castle.getRockPreviousPosition(), castle.getRockCurrentPosition());
                                                        chessPlayBoard.recalculatePositionsAfterMovePlayed(team, specialMove);                                           
                                                        chessPlayBoard.findIfCheckGiven(castle.getRockName(), team);
                                                    }
                                                }
                                            }

                                            if(specialMove.ispAwnUpgrade() == true){
                                                PawnUpgrade pawnUpgrade = specialMove.getPawnUpgrade();
                                                if(pawnUpgrade.getPawnName().isValidMove(pawnUpgrade.getPawnCurrentPosition()) == true){
                                                    if(lastMove.ispIeceBeaten() == true){
                                                        chessPlayBoard.removePiece(lastMove.getPieceBeaten().getPieceBeaten());
                                                    }
                                                    chessPlayBoard.movePiece(pawnUpgrade.getPawnName(), pawnUpgrade.getPawnPreviousPosition(), pawnUpgrade.getPawnCurrentPosition());
                                                    chessPlayBoard.replacePiece(pawnUpgrade.getPawnName(), pawnUpgrade.getUpgradedPiece());
                                                    chessPlayBoard.getLastMove().getSpecialMove().getPawnUpgrade().setUpgradedPiece(chessPlayBoard.getPiece(pawnUpgrade.getUpgradedPiece().getName()));
                                                    chessPlayBoard.recalculatePositionsAfterMovePlayed(team, specialMove);
                                                    chessPlayBoard.findIfCheckGiven(pawnUpgrade.getUpgradedPiece(), team);                                           
                                                }
                                            }

                                            if(specialMove.iseNPassantGiven() == true){
                                                EnPassantGiven enPassantGiven = specialMove.getEnPassantGiven();
                                                if(enPassantGiven.getNativeEnPassantPiece().isValidMove(enPassantGiven.getNativePieceCurrentPosition())){
                                                    chessPlayBoard.movePiece(enPassantGiven.getNativeEnPassantPiece(), enPassantGiven.getNativePiecePreviousPosition(), enPassantGiven.getNativePieceCurrentPosition());
                                                    chessPlayBoard.recalculatePositionsAfterMovePlayed(team, specialMove);
                                                    chessPlayBoard.findIfCheckGiven(enPassantGiven.getNativeEnPassantPiece(), team);
                                                }
                                            }

                                            if(specialMove.iseNPassantPlayed() == true){
                                                EnPassantPlayed enPassantPlayed = specialMove.getEnPassantPlayed();
                                                if(enPassantPlayed.getBeatingPawnName().isValidMove(enPassantPlayed.getBeatingPawnCurrentPosition()) == true){
                                                    chessPlayBoard.removePiece(enPassantPlayed.getBeatenPawnName());
                                                    chessPlayBoard.movePiece(enPassantPlayed.getBeatingPawnName(), enPassantPlayed.getBeatingPawnPreviousPosition(), enPassantPlayed.getBeatingPawnCurrentPosition());
                                                    chessPlayBoard.recalculatePositionsAfterMovePlayed(team, specialMove);
                                                    chessPlayBoard.findIfCheckGiven(enPassantPlayed.getBeatingPawnName(), team);
                                                }
                                            }
                                        }

                                        if(lastMove.iscHeck() == true){
                                            Check check = lastMove.getCheck();
                                            if(check.isDoubleCheck() == true){
                                                check.removePiecesOtherThanKingInAllowedPieces();
                                            }
                                            if(check.isNativeAllowedPieceNull() == true){
                                                lastMove.setcHeckMate(true);
                                                lastMove.setCheckMateData();
                                                updateRatings(lastMove.getPlayedBy(), lastMove.getToBeRecievedBy(), chessPlayBoard);
                                                //save game record to database table need to be implemented
                                            }
                                        }                                
                                    }                    
                                }    
                                chessPlayBoard.switchPlayer();
                                chessPlayBoard.resetTimePerMoveValue();
                                result.put("currentPlayerControl", chessPlayBoard.getCurrentPlayerControl());  
                                result.put("timePerMove", chessPlayBoard.getTimePerMove());
                            }//else part of this if will draw the game as total time for this game has been expired
                        }
                    }
                    result.put("lastMove", new Gson().toJson(chessPlayBoard.getLastMove(), LastMove.class));
                    out.print(result);
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
        try {
            try {
                processRequest(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(PlayerMove.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(PlayerMove.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PlayerMove.class.getName()).log(Level.SEVERE, null, ex);
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
//the teams used in client end are brown and pink whereas the team used in server end are host and player. To convert from clinet to server following method is used
    private String getTeam(String team) {
        String result = "";
        if(team.equals("brown") == true){
            result = "host";
        }else if(team.equals("pink") == true){
            result = "player";
        }
        
        return result;
    }

    private void updateRatings(Person playedBy, Person toBeRecievedBy, ChessPlayBoard chessPlayBoard) throws ClassNotFoundException, SQLException {
        playedBy.getRating().setRating(playedBy.getRating().getRating() + 20);
        toBeRecievedBy.getRating().setRating(toBeRecievedBy.getRating().getRating() - 20);

        Player boardHost = ((GameRoom) getServletContext().getAttribute("gameRoom")).getGameRoomPlayerListWindow().getPlayer(chessPlayBoard.getChessBoardPlayerListWindow().getBoardHost().getUserId());
        if(boardHost.getUserId().equals(playedBy.getUserId()) == true){
            boardHost.getRating().setRating(boardHost.getRating().getRating() + 20);
        }else if(boardHost.getUserId().equals(toBeRecievedBy.getUserId()) == true){
            boardHost.getRating().setRating(boardHost.getRating().getRating() - 20);
        }
        
        new PersonDAO().newRating(playedBy.getUserId(), playedBy.getRating().getRating());
        new PersonDAO().newRating(toBeRecievedBy.getUserId(), toBeRecievedBy.getRating().getRating());
        new GamesDAO().addGameRecord(playedBy.getUserId(), toBeRecievedBy.getUserId());
    }

}
