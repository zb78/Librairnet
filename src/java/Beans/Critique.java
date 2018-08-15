package Beans;

import Exception.LibException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cdi314
 */
public class Critique implements Serializable {

    private String nom;
    private String commentaire;
    private Integer note;

    private boolean videCommentaire = true;
    private boolean videNote = true;

    public Critique() {
        
    }

    
    
    public Critique(String commentaire) {
        this.setCommentaire(commentaire);
        this.videNote = false;
    }

    public Critique(int note) {
        this.setNote(note);
        this.videCommentaire = false;
    }

    public Critique(String commentaire, int note, String nom) {
        setCommentaire(commentaire);
        setNote(note);
        setNom(nom);
        this.videNote = false;
        this.videCommentaire = false;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
        this.videCommentaire = false;
    }

    public int getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
        this.videNote = false;
    }

    public boolean isVideCommentaire() {
        return videCommentaire;
    }

    public void setVideCommentaire(boolean videCommentaire) {
        this.videCommentaire = videCommentaire;
    }

    public boolean getVideCommentaire() {
        return this.videCommentaire;
    }

    public boolean isVideNote() {
        return videNote;
    }

    public void setVideNote(boolean videNote) {
        this.videNote = videNote;
    }

    public void insertCritique(int cliId, String isbn, int ligId, String commentaire, int note, String ip, int statut, Connexion con) {

        String query = "INSERT INTO Critique(cliId,livIsbn,ligId,criCommentaire,criNote,criIp,criStatut) values (?,?,?,?,?,?,?)";

        Connection c = con.getConnection();

        try {
            PreparedStatement pst = c.prepareStatement(query);

            pst.setInt(1, cliId);
            pst.setString(2, isbn);
            pst.setInt(3, ligId);
            pst.setString(4, commentaire);
            pst.setInt(5, note);
            pst.setString(6, ip);
            pst.setInt(7, statut);

            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertCritique(int cliId, String isbn, String ip, int statut, Connexion con) throws LibException {

        int ligId = achatExist(isbn, cliId, con);
        if (ligId != 0) {

            String query = "INSERT INTO Critique(cliId,livIsbn,ligId,criCommentaire,criNote,criIp,criStatut) values (?,?,?,?,?,?,?)";

            Connection c = con.getConnection();

            try {

                PreparedStatement pst = c.prepareStatement(query);

                pst.setInt(1, cliId);
                pst.setString(2, isbn);
                pst.setInt(3, ligId);
                pst.setString(4, this.commentaire);
                pst.setInt(5, this.note);
                pst.setString(6, ip);
                pst.setInt(7, statut);

                pst.executeUpdate();
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new LibException("Vous n'avez pas encore acheté ce Livre");
        }
    }

    /**
     *
     * @param isbn
     * @param idClient
     * @param con
     * @return id de la ligne de commande si elle existe. sinon 0
     */
    public int achatExist(String isbn, int idClient, Connexion con) {

        int resultat = 0;
        String query = "  SELECT distinct ligId FROM LigneCommande lig "
                + "  JOIN Commande com "
                + "  ON lig.comNum = com.comNum"
                + "  JOIN Client cli"
                + "  ON com.cliId = cli.cliId"
                + "  WHERE lig.livIsbn = ? AND com.cliId = ?";

        Connection c = con.getConnection();

        try {

            PreparedStatement pst = c.prepareStatement(query);

            pst.setString(1, isbn);
            pst.setInt(2, idClient);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                resultat = rs.getInt("ligId");
            }

            rs.close();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Critique.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultat;
    }
}
