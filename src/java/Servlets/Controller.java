package Servlets;

import Beans.BeanCatalogue;
import Beans.BeanConnexion;
import Beans.Client;
import Beans.Commande;
import Beans.Connexion;
import Beans.Critique;
import Beans.LigneCommande;
import Beans.Livre;
import Beans.Search;
import Classes.LibExceptions;
import Exception.LibException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("isconnected") == null) {
            session.setAttribute("isconnected", false);
        }
        Connexion con = (Connexion) this.getServletContext().getAttribute("connection");
        if (con == null) {
            con = new Connexion();
            ServletContext application = this.getServletContext();
            application.setAttribute("connection", con);
        }

        ServletContext application = this.getServletContext();
        BeanConnexion bcApplication = (BeanConnexion) application.getAttribute("beanApplication");
        if (bcApplication == null) {
            bcApplication = new BeanConnexion();
            application.setAttribute("beanApplication", bcApplication);
        }

        //----------JULIEN-------------------//
        String url = "/WEB-INF/index.jsp";

        if (request.getParameter("index") != null) {
            url = "/WEB-INF/index.jsp";
        }

        if (session.getAttribute("page") == null) {
            session.setAttribute("page", "/WEB-INF/index.jsp");
        }
        if (session.getAttribute("page") == "/WEB-INF/identification.jsp") {
            session.setAttribute("page", "/WEB-INF/index.jsp");
        }

        // String url = "WEB-INF/Catalogue.jsp";
        if ("catalogue".equals(request.getParameter("section"))) {
            url = "/WEB-INF/Catalogue.jsp";
            session.setAttribute("page", url);
        }
        Livre monLivre = (Livre) request.getAttribute("livre");
        LigneCommande maLigne = (LigneCommande) request.getAttribute("ligne");
        Commande panier = (Commande) session.getAttribute("panier");
        BeanCatalogue catalogue = (BeanCatalogue) request.getAttribute("catalogue");
        if (monLivre == null) {
            monLivre = new Livre();
            request.setAttribute("livre", monLivre);
        }
        if (maLigne == null) {
            maLigne = new LigneCommande();
            request.setAttribute("ligneCommande", maLigne);
        }
        if (panier == null) {
            panier = new Commande();
            session.setAttribute("panier", panier);
        }
        if (request.getParameter("add") != null) {
            monLivre = new Livre(request.getParameter("add"), con);
            maLigne.add(monLivre);
            //maLigne = new LigneCommande(request.getParameter("livre"), monLivre.getPrix(), monLivre.getEvt().getEveRemise(), monLivre.getTaux().getTva());
            panier.add(maLigne);
            session.setAttribute("panier", panier);
        }

        if ("panier".equals(request.getParameter("section"))) {
            url = "/WEB-INF/Panier.jsp";
            session.setAttribute("page", url);
        }

        if ("paiement".equals(request.getParameter("section"))) {
            url = "/WEB-INF/Paiement.jsp";
            session.setAttribute("page", url);
        }

        if (request.getParameter("plus") != null) {
            maLigne = panier.getLigneCommande(request.getParameter("isbnLigne"));
            panier.add(maLigne);
        }
        if (request.getParameter("moins") != null) {
            maLigne = panier.getLigneCommande(request.getParameter("isbnLigne"));
            panier.less(maLigne);
        }
        if (request.getParameter("suppr") != null) {
            maLigne = panier.getLigneCommande(request.getParameter("isbnLigne"));
            panier.delete(maLigne);
        }
        if (request.getParameter("clear") != null) {
            panier.clear();
        }
        request.setAttribute("isEmpty", panier.isEmpty());
        request.setAttribute("listePanier", panier.getPanier());
        request.setAttribute("listeLivres", panier.listerLivrePanier(catalogue));
        request.setAttribute("nbarticle", panier.quantiteTotal());
        request.setAttribute("totHT", panier.prixTotalHt());
        request.setAttribute("totTTC", panier.prixTotalTtc());
        request.setAttribute("totRemise", panier.getRemiseTot());

        if (request.getParameter("valider") != null) {

        }

        //-----------------------FIN JULIEN------------//
        //-----------------------YAVUZ------------//
        //url = "/WEB-INF/index.jsp";
//        if (request.getParameter("search") != null) {
//            url = "/WEB-INF/search.jsp";
//
//        }
        if (request.getParameter("all") != null) {
            url = "/WEB-INF/all.jsp";
            session.setAttribute("page", url);
            Search sr = new Search();
            //ServletContext application = this.getServletContext();
            //Connexion con = (Connexion) application.getAttribute("connection");
            sr.setSearch(Search.ALL, con);
            request.setAttribute("search", sr.getSearch());
        }

        if (request.getParameter("recherche") != null) {
            url = "/WEB-INF/all.jsp";
            session.setAttribute("page", url);
            Search sr = new Search();
            //ServletContext application = this.getServletContext();
            //Connexion con = (Connexion) application.getAttribute("connection");
            if ("tout".equals(request.getParameter("select"))) {
                sr.setSearch(Search.ALL, con, request.getParameter("mot"));
            }

            if ("theme".equals(request.getParameter("select"))) {
                sr.setSearch(Search.THEME, con, request.getParameter("mot"));
            }
            if ("motscle".equals(request.getParameter("select"))) {
                sr.setSearch(Search.MOTCLE, con, request.getParameter("mot"));
            }
            if ("auteur".equals(request.getParameter("select"))) {
                sr.setSearch(Search.AUTEUR, con, request.getParameter("mot"));
            }
            if ("isbn".equals(request.getParameter("select"))) {
                sr.setSearch(Search.ISBN, con, request.getParameter("mot"));
            }
            if ("titre".equals(request.getParameter("select"))) {
                sr.setSearch(Search.TITRE, con, request.getParameter("mot"));
            }

            request.setAttribute("search", sr.getSearch());

        }

        if (request.getParameter("identification") != null) {
            url = "/WEB-INF/Identification.jsp";
        }

        if (request.getParameter("critique") != null) {
            System.out.println("dans critique");
            Livre livreACritique = new Livre(request.getParameter("isbn"), con);
            session.setAttribute("lac", livreACritique);
            Boolean clientisconnect = (Boolean) session.getAttribute("isconnected");

            if (clientisconnect == false) {
                session.setAttribute("fromurl", "/WEB-INF/Commentaire.jsp");
                url = "/WEB-INF/Commentaire.jsp";
            } else {
                //Critique critique = new Critique(commentaire, note, nomClient);
                url = "/WEB-INF/Identification.jsp";
                session.setAttribute("achete", true);
                Critique critique = new Critique();
                Client client = (Client) session.getAttribute("client");

                int ligId = critique.achatExist(livreACritique.getIsbn(), client.getCliId(), con);

                if (ligId == 0) {
                    session.setAttribute("achete", false);
                }

                if (request.getParameter("critiquebouton") != null) {
                    session.removeAttribute("achete");
                    critique.setCommentaire(request.getParameter("commentaire"));
                    critique.setNote(Integer.valueOf(request.getParameter("note")));

                    try {
                        critique.insertCritique(client.getCliId(), livreACritique.getIsbn(), request.getRemoteAddr(), 253, con);
                        session.removeAttribute("formurl");
                    } catch (LibException ex) {
                        session.setAttribute("erreur", ex.getMessage());
                    }
                }
            }
        }

        if (request.getParameter("mesCritiques") != null) {
            url = "/WEB-INF/MesCritiques.jsp";
            Search mesLivreCommente = new Search();
            Client client = (Client) session.getAttribute("client");
            if(client != null){
                mesLivreCommente.getLivresClient(client.getCliId(), con);
            }
            mesLivreCommente.getSearch();
        }

        //-----------------------FIN YAVUZ------------//
        if (request.getParameter("connection") != null) {
            url = "/WEB-INF/Identification.jsp";
        }
        //-----------------------MOMO-----------------//
//        if (request.getParameter("connection") != null) {
//            url = "/WEB-INF/Identification.jsp";
//        }

        String password = request.getParameter("password");
        Connection conect = getinstance();
        int i = 1;
        Client cl = new Client();
//            int i = cl.LoginValide(login,password, conect);
        String message = null;
        String welcome = null;
        String fatalerror = null;
        String login = "";
        if (login != null && password != null) {
            i = cl.LoginValide(request.getParameter("login"), request.getParameter("password"), conect);
            System.out.println("cl = " + cl);
            session.setAttribute("client", cl);
        }

//            Beans.Singleton.getinstance();
//            Cookie cccc = getCookie(request.getCookies(), "try");
//        if ("login".equals(request.getParameter("section"))) {
        if (request.getParameter("doIt") != null) {
            session.setAttribute("isconnected", false);
            if (i == 0) {
                System.out.println("page vaut " + session.getAttribute("page"));
                url = session.getAttribute("page").toString();
                session.setAttribute("isconnected", true);
                welcome = request.getParameter("login");
                request.setAttribute("welcome", welcome);
                Cookie c = new Cookie("login", welcome);
                response.addCookie(c);
                c = new Cookie("try", "");
                c.setMaxAge(0);
                response.addCookie(c);

                //Yavuz Le code ci-dessous sert a rediriger vert la page de critique après connection
                // ca n'interfert pas. Si je vient de la page critique ça renvoi
                String fromUrl = (String) session.getAttribute("fromurl");
                if (fromUrl != null) {
                    url = fromUrl;

                }

            } else {
                url = "/WEB-INF/Identification.jsp";
                request.setAttribute("user", request.getParameter("login"));
                request.setAttribute("message", "Erreur Login /Mot de passe invalide!!!");
                Cookie ccc = getCookie(request.getCookies(), "try");
                if (ccc == null) {
                    ccc = new Cookie("try", "*");
                } else {
                    ccc = new Cookie("try", ccc.getValue() + "*");
                }

                if (ccc.getValue().length() > 3) {

                    ccc.setMaxAge(1 * 60);
                    url = "/WEB-INF/jspFatalError.jsp";
                    session.setAttribute("page", url);
                    request.setAttribute("fatalerror", "Erreur trop de tentatives de connexion !!!");
                }
                response.addCookie(ccc);
            }
        }
        Cookie cc = getCookie(request.getCookies(), "login");
        if (cc != null) {

        }
        Cookie cccc = getCookie(request.getCookies(), "try");
        if (cccc != null) {
            if (cccc.getValue().length() > 3) {
                url = "/WEB-INF/jspFatalError.jsp";
                session.setAttribute("page", url);
                request.setAttribute("fatalerror", "Erreur trop de tentatives de connexion !!!!");
            }
        }
        if (request.getParameter("deconnect") != null) {
            session.removeAttribute("client");
            session.setAttribute("isconnected", false);
            url = session.getAttribute("page").toString();
            if (cc != null) {
                login = cc.getValue();
            }
            cc = new Cookie("login", "");
            cc.setMaxAge(0);
            response.addCookie(cc);
        }
        if (conect != null) {
            try {
                conect.close();
            } catch (SQLException ex) {
                System.out.println("Oops 4 sql : " + ex.getMessage());

            }
        }
        if (request.getParameter("inscription") != null) {
            url = "/WEB-INF/Inscription.jsp";
            session.setAttribute("page", url);
        }
        if (request.getParameter("regdoIt") != null) {
            url = "/WEB-INF/Inscription.jsp";
            session.setAttribute("page", url);
            Client ins = new Client();
            String nom = request.getParameter("nom");
            try {
                cl.setCliNom(nom);
                request.setAttribute("nom", nom);
            } catch (LibExceptions e) {
//                     nom = "<s>"+nom+"</s>";
//                     request.setAttribute("nom",nom);
                request.setAttribute("Nom", e.getMessage());
            }
            String prenom = request.getParameter("prenom");
            try {
                cl.setCliPrenom(prenom);
                request.setAttribute("prenom", prenom);
            } catch (LibExceptions e) {
                request.setAttribute("Prenom", e.getMessage());
            }
            String bir = request.getParameter("naissance");
            try {
                cl.setCliNaissance(bir);
                request.setAttribute("naissance", bir);
            } catch (LibExceptions e) {
                request.setAttribute("bir", e.getMessage());
            }
            String email = request.getParameter("mail");
            try {
                cl.setCliMail(bir);
                request.setAttribute("mail", email);
            } catch (LibExceptions e) {
                request.setAttribute("email", e.getMessage());
            }
            String user = request.getParameter("login");
            try {
                cl.setCliLogin(user);
                request.setAttribute("login", user);
            } catch (LibExceptions e) {
                request.setAttribute("user", e.getMessage());
            }
            String pass = request.getParameter("pwd");
            try {
                cl.setCliPwd(pass);
                request.setAttribute("pwd", pass);
            } catch (LibExceptions e) {
                request.setAttribute("Pwd", e.getMessage());
            }
        }

        //----------------------FIN MOMO--------------//
        if (request.getParameter("validCom") != null) {
            if (session.getAttribute("client") != null) {
                url = "/WEB-INF/Paiement.jsp";
                session.setAttribute("page", url);
            } else {
                url = "/WEB-INF/Identification.jsp";
                request.setAttribute("message", "Veuillez vous connecter pour valider votre panier");
            }
        }
        //System.out.println("page vaut " + session.getAttribute("page"));
        System.out.println(url);
        request.getRequestDispatcher(url).include(request, response);

    }

    private Connection getinstance() {
        DataSource ds = null;
        Connection connexion = null;
        try {
            InitialContext context = new InitialContext();
            ds = (DataSource) context.lookup("jdbc/Librairnet");
        } catch (NamingException ex) {
//                message = "Problème de base de données";
            System.out.println("Oops 1 Naming:" + ex.getMessage());

        }
        if (connexion == null) {
            try {

                connexion = ds.getConnection();

            } catch (SQLException ex) {
//            message = "Problème de base de données";
                System.out.println("Oops1 Connection:" + ex.getMessage());

            }

        }

        return connexion;

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
